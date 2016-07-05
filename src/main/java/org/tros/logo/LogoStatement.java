/*
 * Copyright 2015 Matthew Aguirre
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
import org.tros.logo.antlr.logoParser;
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
     * Constructor
     *
     * @param command
     * @param ctx
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
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     * Process the statement.
     *
     * @param scope
     * @return
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

        scope.set(TURTLE_X_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleX()));
        scope.set(TURTLE_Y_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleY()));
        scope.set(TURTLE_ANGLE_VAR, new InterpreterValue(NumberType.INSTANCE, canvas.getTurtleAngle()));

        ReturnValue success = ReturnValue.SUCCESS;
        if ("fd".equals(command)) {
            canvas.forward(((Number) ExpressionListener.evaluate(scope, ((logoParser.FdContext) ctx).expression()).getValue()).doubleValue());
        } else if ("bk".equals(command)) {
            canvas.backward(((Number) ExpressionListener.evaluate(scope, ((logoParser.BkContext) ctx).expression()).getValue()).doubleValue());
        } else if ("lt".equals(command)) {
            canvas.left(((Number) ExpressionListener.evaluate(scope, ((logoParser.LtContext) ctx).expression()).getValue()).doubleValue());
        } else if ("rt".equals(command)) {
            canvas.right(((Number) ExpressionListener.evaluate(scope, ((logoParser.RtContext) ctx).expression()).getValue()).doubleValue());
        } else if ("setxy".equals(command)) {
            logoParser.SetxyContext fd = (logoParser.SetxyContext) ctx;
            double x = ((Number) ExpressionListener.evaluate(scope, fd.expression(0)).getValue()).doubleValue();
            double y = ((Number) ExpressionListener.evaluate(scope, fd.expression(1)).getValue()).doubleValue();
            canvas.setXY(x, y);
        } else if ("pd".equals(command)) {
            canvas.penDown();
        } else if ("pu".equals(command)) {
            canvas.penUp();
        } else if ("stop".equals(command)) {
            //note, this is the one time false is returned (except thread halting).
            //this is used to break out of functions.
            success = ReturnValue.RETURN;
        } else if ("pc".equals(command)) {
            logoParser.PcContext fd = (logoParser.PcContext) ctx;
            if (fd.expression().size() >= 3) {
                int a = 255;
                int r = ((Number) ExpressionListener.evaluate(scope, fd.expression(0)).getValue()).intValue();
                int g = ((Number) ExpressionListener.evaluate(scope, fd.expression(1)).getValue()).intValue();
                int b = ((Number) ExpressionListener.evaluate(scope, fd.expression(2)).getValue()).intValue();
                if (fd.expression().size() > 3) {
                    a = ((Number) ExpressionListener.evaluate(scope, fd.expression(3)).getValue()).intValue();
                }
                canvas.pencolor(r, g, b, a);
            } else if (fd.hexcolor() != null) {
                canvas.pencolor(fd.hexcolor().HEX().toString());
            } else {
                String name = fd.name().STRING().getText();
                canvas.pencolor(name);
            }
        } else if ("cc".equals(command)) {
            logoParser.CcContext fd = (logoParser.CcContext) ctx;
            if (fd.expression().size() == 3) {
                int r = ((Number) ExpressionListener.evaluate(scope, fd.expression(0)).getValue()).intValue();
                int g = ((Number) ExpressionListener.evaluate(scope, fd.expression(1)).getValue()).intValue();
                int b = ((Number) ExpressionListener.evaluate(scope, fd.expression(2)).getValue()).intValue();
                canvas.canvascolor(r, g, b);
            } else if (fd.hexcolor() != null) {
                canvas.canvascolor(fd.hexcolor().HEX().toString());
            } else {
                String name = fd.name().STRING().getText();
                canvas.canvascolor(name);
            }
        } else if ("ds".equals(command)) {
            logoParser.DsContext fd = (logoParser.DsContext) ctx;
            String str = null;
            if (fd.value().STRINGLITERAL() != null) {
                str = fd.value().STRINGLITERAL().getText().substring(1);
            } else if (fd.value().deref() != null) {
                //this doesn't seem to be called during dref, instead it is
                //evaluated as an expression...
//                    String n = fd.value().deref().name().STRING().toString();
//                    str = Double.toString(scope.get(n));
            } else if (fd.value().expression() != null) {
                str = ExpressionListener.evaluate(scope, fd).toString();
            }
            if (str != null) {
                canvas.drawString(str);
            }
        } else if ("fontsize".equals(command)) {
            canvas.fontSize(((Number) ExpressionListener.evaluate(scope, ((logoParser.FontsizeContext) ctx).expression()).getValue()).intValue());
        } else if ("fontstyle".equals(command)) {
            logoParser.FontstyleContext fd = (logoParser.FontstyleContext) ctx;
            String styleString = fd.style().getText();
            if ("bold".equals(styleString)) {
                canvas.fontStyle(1);
            } else if ("italic".equals(styleString)) {
                canvas.fontStyle(2);
            } else if ("plain".equals(styleString)) {
                canvas.fontStyle(0);
            } else {
                LOGGER.warn(MessageFormat.format("Unknown {0}: {1}", "fontstyle", styleString));
            }
        } else if ("fontname".equals(command)) {
            logoParser.FontnameContext fd = (logoParser.FontnameContext) ctx;
            String name = fd.name().STRING().getText();
            canvas.fontName(name);
        } else if ("pause".equals(command)) {
            canvas.pause(((Number) ExpressionListener.evaluate(scope, ((logoParser.PauseContext) ctx).expression()).getValue()).intValue());
        } else if ("cs".equals(command)) {
            canvas.clear();
        } else if ("home".equals(command)) {
            canvas.home();
        } else if ("ht".equals(command)) {
            canvas.hideTurtle();
        } else if ("st".equals(command)) {
            canvas.showTurtle();
        } else if ("make".equals(command)) {
            String var = ctx.getChild(1).getText().substring(1);
            scope.set(var, ExpressionListener.evaluate(scope, ctx.getChild(2)));
        } else if ("localmake".equals(command)) {
            //this is the statement that is why we don't do a scope.push() at the
            //beginning of this method.
            String var = ctx.getChild(1).getText().substring(1);
            scope.setNew(var, ExpressionListener.evaluate(scope, ctx.getChild(2)));
        } else if ("print".equals(command)) {
            //will need to support strings...
            InterpreterValue evaluate = ExpressionListener.evaluate(scope, ctx.getChild(1));
            canvas.message(this.getClass().getName() + " -> " + evaluate.getValue().toString());
        } else {
            //if it is not a known value form above, it is probably a funciton,
            //get the function by name and invoke.
            CodeFunction lf = getFunction(command, scope);
            if (lf != null) {
                //get the procedure declaration so we can get the parameter names to set to values from the invocation.
                logoParser.ProcedureDeclarationContext funct = (logoParser.ProcedureDeclarationContext) lf.getParserRuleContext();
                ArrayList<String> paramNames = new ArrayList<String>();
                HashMap<String, InterpreterValue> paramValues = new HashMap<String, InterpreterValue>();

                //get the parameter names
                for (logoParser.ParameterDeclarationsContext param : funct.parameterDeclarations()) {
                    paramNames.add(param.getText().substring(1));
                }

                logoParser.ProcedureInvocationContext context = (logoParser.ProcedureInvocationContext) ctx;

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
                canvas.warning(this.getClass().getName() + "process(): UNKNOWN -> " + command);
                LOGGER.warn(MessageFormat.format("process(): UNKNOWN -> {0}", command));
            }
        }
        canvas.repaint();

        return success;
    }
}
