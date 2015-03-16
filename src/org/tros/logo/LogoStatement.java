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

import org.tros.torgo.ProcessResult;
import org.tros.torgo.CodeBlock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.TorgoCanvas;
import org.tros.torgo.Scope;

/**
 * This is perhaps the most trickily named class. This inherits from LogoBlock,
 * but is in-fact only a single statement or command. In effect, this is the
 * terminal node of the call tree.
 *
 * @author matta
 */
public class LogoStatement extends LogoBlock {
    public static final String TURTLE_X_VAR = "1_turtlex%";
    public static final String TURTLE_Y_VAR = "1_turtley%";
    public static final String TURTLE_ANGLE_VAR = "1_turtlea%";

    private static final Logger logger = Logger.getLogger(LogoStatement.class.getName());
    private final String command;

    /**
     * Constructor
     *
     * @param command
     * @param ctx
     */
    protected LogoStatement(String command, ParserRuleContext ctx) {
        super(ctx);

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
     * Debugging use.
     *
     * @return
     */
    @Override
    public String toString() {
        return command + System.getProperty("line.separator");
    }

    /**
     * Process the statement.
     *
     * @param scope
     * @param canvas
     * @return
     */
    @Override
    public ProcessResult process(Scope scope, TorgoCanvas canvas) {
        //if the thread has halted, don't process and pop up the stack.
        if (isHalted()) {
            return ProcessResult.HALT;
        }

        logger.log(Level.FINEST, "[{0}]: Line: {1}, Start: {2}, End: {3}", new Object[]{ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex()});

        listeners.stream().forEach((l) -> {
            l.currStatement(command, ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
        });
        
        scope.set(TURTLE_X_VAR, canvas.getTurtleX());
        scope.set(TURTLE_Y_VAR, canvas.getTurtleY());
        scope.set(TURTLE_ANGLE_VAR, canvas.getTurtleAngle());
        
        ProcessResult success = ProcessResult.SUCCESS;
        switch (command) {
            case "fd":
                canvas.forward(ExpressionListener.evaluateDouble(scope, ((logoParser.FdContext) ctx).expression()));
                break;
            case "bk":
                canvas.backward(ExpressionListener.evaluateDouble(scope, ((logoParser.BkContext) ctx).expression()));
                break;
            case "lt":
                canvas.left(ExpressionListener.evaluateDouble(scope, ((logoParser.LtContext) ctx).expression()));
                break;
            case "rt":
                canvas.right(ExpressionListener.evaluateDouble(scope, ((logoParser.RtContext) ctx).expression()));
                break;
            case "setxy": {
                logoParser.SetxyContext fd = (logoParser.SetxyContext) ctx;
                double x = ExpressionListener.evaluateDouble(scope, fd.expression(0));
                double y = ExpressionListener.evaluateDouble(scope, fd.expression(1));
                canvas.setXY(x, y);
                break;
            }
            case "pd":
                canvas.penDown();
                break;
            case "pu":
                canvas.penUp();
                break;
            case "stop":
                //note, this is the one time false is returned (except thread halting).
                //this is used to break out of functions.
                success = ProcessResult.RETURN;
                break;
            case "pc": {
                logoParser.PcContext fd = (logoParser.PcContext) ctx;
                if (fd.expression().size() >= 3) {
                    Double a = 255.0;
                    Double r = ExpressionListener.evaluateDouble(scope, fd.expression(0));
                    Double g = ExpressionListener.evaluateDouble(scope, fd.expression(1));
                    Double b = ExpressionListener.evaluateDouble(scope, fd.expression(2));
                    if (fd.expression().size() > 3) {
                        a = ExpressionListener.evaluateDouble(scope, fd.expression(3));
                    }
                    canvas.pencolor(r.intValue(), g.intValue(), b.intValue(), a.intValue());
                } else if (fd.hexcolor() != null) {
                    canvas.pencolor(fd.hexcolor().HEX().toString());
                } else {
                    String name = fd.name().STRING().getText();
                    canvas.pencolor(name);
                }
                break;
            }
            case "cc": {
                logoParser.CcContext fd = (logoParser.CcContext) ctx;
                if (fd.expression().size() == 3) {
                    Double r = ExpressionListener.evaluateDouble(scope, fd.expression(0));
                    Double g = ExpressionListener.evaluateDouble(scope, fd.expression(1));
                    Double b = ExpressionListener.evaluateDouble(scope, fd.expression(2));
                    canvas.canvascolor(r.intValue(), g.intValue(), b.intValue());
                } else if (fd.hexcolor() != null) {
                    canvas.canvascolor(fd.hexcolor().HEX().toString());
                } else {
                    String name = fd.name().STRING().getText();
                    canvas.canvascolor(name);
                }
                break;
            }
            case "ds": {
                logoParser.DsContext fd = (logoParser.DsContext) ctx;
                String str = null;
                if (fd.value().STRINGLITERAL() != null) {
                    str = fd.value().STRINGLITERAL().getText().substring(1);
                } else if (fd.value().deref() != null) {
                    //this doesn't seem to be called during dref, instead it is
                    //evaluated as an expression...
                    String n = fd.value().deref().name().STRING().toString();
                    str = Double.toString(scope.get(n));
                } else if (fd.value().expression() != null) {
                    str = ExpressionListener.evaluateDouble(scope, fd).toString();
                }
                if (str != null) {
                    canvas.drawString(str);
                }
                break;
            }
            case "fontsize":
                canvas.fontSize(ExpressionListener.evaluateDouble(scope, ((logoParser.FontsizeContext) ctx).expression()).intValue());
                break;
            case "fontstyle": {
                logoParser.FontstyleContext fd = (logoParser.FontstyleContext) ctx;
                String styleString = fd.style().getText();
                switch (styleString) {
                    case "bold":
                        canvas.fontStyle(1);
                        break;
                    case "italic":
                        canvas.fontStyle(2);
                        break;
                    case "plain":
                        canvas.fontStyle(0);
                        break;
                    default:
                        logger.log(Level.WARNING, "Unknown {0}: {1}", new Object[]{"fontstyle", styleString});
                        break;
                }
                break;
            }
            case "fontname": {
                logoParser.FontnameContext fd = (logoParser.FontnameContext) ctx;
                String name = fd.name().STRING().getText();
                canvas.fontName(name);
                break;
            }
            case "pause":
                canvas.pause(ExpressionListener.evaluateDouble(scope, ((logoParser.PauseContext) ctx).expression()).intValue());
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
            case "make": {
                String var = ctx.getChild(1).getText().substring(1);
                scope.set(var, ExpressionListener.evaluateDouble(scope, ctx.getChild(2)));
                break;
            }
            case "localmake": {
                String var = ctx.getChild(1).getText().substring(1);
                scope.setNew(var, ExpressionListener.evaluateDouble(scope, ctx.getChild(2)));
                break;
            }
            case "print":
                //will need to support strings...
                canvas.message(this.getClass().getName() + " -> " + ExpressionListener.evaluateDouble(scope, ctx.getChild(1)).toString());
                break;
            default:
                CodeBlock lf = getFunction(command, scope);
                if (lf != null) {
                    scope.push(this);
                    success = lf.process(scope, canvas);
                    scope.pop();
                } else {
                    success = ProcessResult.HALT;
                    canvas.warning(this.getClass().getName() + "process(): UNKNOWN -> " + command);
                    logger.log(Level.WARNING, "process(): UNKNOWN -> {0}", new Object[]{command});
                }
                break;
        }
        canvas.repaint();

        return success;
    }
}
