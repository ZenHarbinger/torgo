/*
 * Copyright 2016 Matthew Aguirre
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
package org.tros.jvmbasic;

import java.text.MessageFormat;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tros.jvmbasic.antlr.jvmBasicParser;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.types.StringType;

/**
 *
 * @author matta
 */
public class BasicStatement extends BasicBlock {

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(BasicStatement.class);
    private final String command;

    public BasicStatement(String command, ParserRuleContext ctx) {
        super(ctx);
        this.command = command;
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
        ReturnValue success = ReturnValue.SUCCESS;

        if (null != command) {
            switch (command) {
                case "print": {
                    InterpreterValue evaluate = org.tros.jvmbasic.ExpressionListener.evaluate(scope, ctx.getChild(1));
                    super.listeners.fire().message(evaluate.getValue().toString());
                }
                break;
                case "variableassignment": {
                    jvmBasicParser.VariableassignmentContext var = (jvmBasicParser.VariableassignmentContext) ctx;
                    InterpreterValue evaluate = org.tros.jvmbasic.ExpressionListener.evaluate(scope, var.exprlist());
                    String varname = var.vardecl().var().getText();
                    TerminalNode DOLLAR = var.vardecl().var().varsuffix() != null ? var.vardecl().var().varsuffix().DOLLAR() : null;
                    TerminalNode PERCENT = var.vardecl().var().varsuffix() != null ? var.vardecl().var().varsuffix().PERCENT() : null;
                    if (DOLLAR != null && evaluate.getType().equals(StringType.INSTANCE)) {
                        //a string
                        scope.set(varname, evaluate);
                    } else if (DOLLAR == null && !evaluate.getType().equals(StringType.INSTANCE)){
                        //not a string
                        scope.set(varname, evaluate);
                    }
                }
                break;
                default:
                    break;
            }
        }

        return success;
    }
}
