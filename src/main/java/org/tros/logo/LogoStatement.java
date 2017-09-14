/*
 * Copyright 2015-2017 Matthew Aguirre
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tros.logo;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.LogoParser;
import org.tros.torgo.interpreter.CodeFunction;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.types.NumberType;

/**
 * This is perhaps the most trickily named class. This inherits from LogoBlock,
 * but is in-fact only a single statement or command. In effect, this is the
 * terminal node of the call tree.
 *
 * @author matta
 */
class LogoStatement extends LogoBlock {

    public static final String TURTLE_X_VAR = "1_turtlex%";
    public static final String TURTLE_Y_VAR = "1_turtley%";
    public static final String TURTLE_ANGLE_VAR = "1_turtlea%";

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoStatement.class);
    private final String command;
    private final LogoCanvas canvas;

    /**
     * Constructor.
     *
     * @param command the logo command.
     * @param ctx the parser context from antlr.
     */
    protected LogoStatement(String command, ParserRuleContext ctx, LogoCanvas canvas) {
        super(ctx);
        this.canvas = canvas;
        this.command = command.trim();
        super.addCommand((LogoBlock) this);
    }

    /**
     * Get the command.
     *
     * @return the logo command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Process the statement.
     *
     * @param scope the current scope of the application.
     * @return the success of evaluating the command.
     */
    @Override
    public ReturnValue process(Scope scope) {
        //if the thread has halted, don't process and pop up the stack.
        if (isHalted()) {
            return ReturnValue.HALT;
        }

        LOGGER.verbose(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex()));

        //we don't do scope.push(this) here because of the chance we will
        //be doing a variable creation (localmake) and so it is possible
        //that we would push onto the stack, create, and the pop the new
        //value right off of the stack again.
        listeners.fire().currStatement(this, scope);

        scope.setGlobal(TURTLE_X_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleX()));
        scope.setGlobal(TURTLE_Y_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleY()));
        scope.setGlobal(TURTLE_ANGLE_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleAngle()));

        ReturnValue success = ReturnValue.SUCCESS;
        if (null != command) {
            switch (command) {
                case "fd":
                    canvas.forward(((Number) ExpressionListener.evaluate(scope, ((LogoParser.FdContext) ctx).expression()).getValue()).doubleValue());
                    break;
                case "bk":
                    canvas.backward(((Number) ExpressionListener.evaluate(scope, ((LogoParser.BkContext) ctx).expression()).getValue()).doubleValue());
                    break;
                case "lt":
                    canvas.left(((Number) ExpressionListener.evaluate(scope, ((LogoParser.LtContext) ctx).expression()).getValue()).doubleValue());
                    break;
                case "rt":
                    canvas.right(((Number) ExpressionListener.evaluate(scope, ((LogoParser.RtContext) ctx).expression()).getValue()).doubleValue());
                    break;
                case "setxy":
                    LogoParser.SetxyContext setxy = (LogoParser.SetxyContext) ctx;
                    double x = ((Number) ExpressionListener.evaluate(scope, setxy.expression(0)).getValue()).doubleValue();
                    double y = ((Number) ExpressionListener.evaluate(scope, setxy.expression(1)).getValue()).doubleValue();
                    canvas.setXY(x, y);
                    break;
                case "pd":
                    canvas.penDown();
                    break;
                case "pu":
                    canvas.penUp();
                    break;
                case "stop":
                    //note, this is the one time false is returned (except thread halting).
                    //this is used to break out of functions.
                    success = ReturnValue.RETURN;
                    break;
                case "pc":
                    LogoParser.PcContext pc = (LogoParser.PcContext) ctx;
                    if (pc.expression().size() >= 3) {
                        int a = 255;
                        int r = ((Number) ExpressionListener.evaluate(scope, pc.expression(0)).getValue()).intValue();
                        int g = ((Number) ExpressionListener.evaluate(scope, pc.expression(1)).getValue()).intValue();
                        int b = ((Number) ExpressionListener.evaluate(scope, pc.expression(2)).getValue()).intValue();
                        if (pc.expression().size() > 3) {
                            a = ((Number) ExpressionListener.evaluate(scope, pc.expression(3)).getValue()).intValue();
                        }
                        canvas.pencolor(r, g, b, a);
                    } else if (pc.hexcolor() != null) {
                        canvas.pencolor(pc.hexcolor().HEX().toString());
                    } else {
                        String name = pc.name().STRING().getText();
                        canvas.pencolor(name);
                    }
                    break;
                case "cc":
                    LogoParser.CcContext cc = (LogoParser.CcContext) ctx;
                    if (cc.expression().size() == 3) {
                        int r = ((Number) ExpressionListener.evaluate(scope, cc.expression(0)).getValue()).intValue();
                        int g = ((Number) ExpressionListener.evaluate(scope, cc.expression(1)).getValue()).intValue();
                        int b = ((Number) ExpressionListener.evaluate(scope, cc.expression(2)).getValue()).intValue();
                        canvas.canvascolor(r, g, b);
                    } else if (cc.hexcolor() != null) {
                        canvas.canvascolor(cc.hexcolor().HEX().toString());
                    } else {
                        String name = cc.name().STRING().getText();
                        canvas.canvascolor(name);
                    }
                    break;
                case "ds":
                    LogoParser.DsContext ds = (LogoParser.DsContext) ctx;
                    String str = null;
                    if (ds.value().STRINGLITERAL() != null) {
                        str = ds.value().STRINGLITERAL().getText().substring(1);
                    } else if (ds.value().deref() != null) {
                        LOGGER.info("ds()");
                        //this doesn't seem to be called during dref, instead it is
                        //evaluated as an expression...
//                    String n = fd.value().deref().name().STRING().toString();
//                    str = Double.toString(scope.get(n));
                    } else if (ds.value().expression() != null) {
                        str = ExpressionListener.evaluate(scope, ds).toString();
                    }
                    if (str != null) {
                        canvas.drawString(str);
                    }
                    break;
                case "fontsize":
                    canvas.fontSize(((Number) ExpressionListener.evaluate(scope, ((LogoParser.FontsizeContext) ctx).expression()).getValue()).intValue());
                    break;
                case "fontstyle":
                    LogoParser.FontstyleContext fontstyle = (LogoParser.FontstyleContext) ctx;
                    String styleString = fontstyle.style().getText();
                    if (null != styleString) {
                        switch (styleString) {
                            case "bold":
                                canvas.fontStyle(1);
                                break;
                            case "italic":
                                canvas.fontStyle(2);
                                break;
                            case "bold_italic":
                                canvas.fontStyle(3);
                                break;
                            case "plain":
                            default:
                                canvas.fontStyle(0);
                                break;
                        }
                    }
                    break;
                case "fontname":
                    LogoParser.FontnameContext fontname = (LogoParser.FontnameContext) ctx;
                    String name = fontname.name().STRING().getText();
                    canvas.fontName(name);
                    break;
                case "pause":
                    canvas.pause(((Number) ExpressionListener.evaluate(scope, ((LogoParser.PauseContext) ctx).expression()).getValue()).intValue());
                    break;
                case "cs":
                    canvas.clear();
                    break;
                case "home":
                    canvas.home();
                    break;
                case "ht":
                    canvas.hideTurtle();
                    break;
                case "st":
                    canvas.showTurtle();
                    break;
                case "make":
                    String make = ctx.getChild(1).getText().substring(1);
                    scope.set(make, ExpressionListener.evaluate(scope, ctx.getChild(2)));
                    break;
                case "localmake":
                    //this is the statement that is why we don't do a scope.push() at the
                    //beginning of this method.
                    String localmake = ctx.getChild(1).getText().substring(1);
                    scope.setNew(localmake, ExpressionListener.evaluate(scope, ctx.getChild(2)));
                    break;
                case "print":
                    //will need to support strings...
                    InterpreterValue evaluate = ExpressionListener.evaluate(scope, ctx.getChild(1));
//                    canvas.message(this.getClass().getName() + " -> " + evaluate.getValue().toString());
                    super.listeners.fire().message(evaluate.getValue().toString());
                    break;
                default:
                    //if it is not a known value form above, it is probably a funciton,
                    //get the function by name and invoke.
                    CodeFunction lf = getFunction(command, scope);
                    if (lf != null) {
                        //get the procedure declaration so we can get the parameter names to set to values from the invocation.
                        LogoParser.ProcedureDeclarationContext funct = (LogoParser.ProcedureDeclarationContext) lf.getParserRuleContext();
                        ArrayList<String> paramNames = new ArrayList<>();
                        HashMap<String, InterpreterValue> paramValues = new HashMap<>();

                        //get the parameter names
                        for (LogoParser.ParameterDeclarationsContext param : funct.parameterDeclarations()) {
                            paramNames.add(param.getText().substring(1));
                        }

                        LogoParser.ProcedureInvocationContext context = (LogoParser.ProcedureInvocationContext) ctx;

                        //get the paremeter values
                        for (int ii = 0; ii < paramNames.size(); ii++) {
                            paramValues.put(paramNames.get(ii), ExpressionListener.evaluate(scope, context.expression(ii)));
                        }

                        //Invoke the procedure w/ the parameters
                        success = lf.process(scope, paramValues);
                    } else {
                        //no function by that name was found.
                        //halt interpreting.
                        success = ReturnValue.HALT;
                        super.listeners.fire().error(new Exception(MessageFormat.format("process(): UNKNOWN -> {0}", command)));
                        canvas.warning(this.getClass().getName() + "process(): UNKNOWN -> " + command);
                        LOGGER.warn(MessageFormat.format("process(): UNKNOWN -> {0}", command));
                    }
                    break;
            }
        }
        canvas.repaint();

        return success;
    }
}
