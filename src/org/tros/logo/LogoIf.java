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
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.TorgoCanvas;
import org.tros.torgo.Scope;

/**
 * Supports if statements/expressions.
 * @author matta
 */
public class LogoIf extends LogoBlock {

    private static final Logger logger = Logger.getLogger(LogoIf.class.getName());
    
    /**
     * Constructor
     * @param ctx 
     */
    protected LogoIf(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * Debugging use.
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder bi = new StringBuilder();
        bi.append("if ");//.append(val1).append(" ").append(condition).append(" ").append(val2).append(System.getProperty("line.separator"));
        bi.append(super.toString());
        bi.append("end if").append(System.getProperty("line.separator"));
        return bi.toString();
    }

    /**
     * Process the if statement.
     * @param scope
     * @param canvas
     * @param context
     * @param stack
     * @return 
     */
    @Override
    public ProcessResult process(Scope scope, TorgoCanvas canvas, ParserRuleContext context, Stack<CodeBlock> stack) {
        scope.push();

        logger.log(Level.FINEST, "[{0}]: Line: {1}, Start: {2}, End: {3}", new Object[]{ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()});
        listeners.stream().forEach((l) -> {
            l.currStatement("if", ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex());
        });

        //evaluate the 2 expressions.
        logoParser.IfeContext ct = (logoParser.IfeContext) ctx;
        double val1 = ExpressionListener.evaluateDouble(scope, ct.comparison().expression(0));
        double val2 = ExpressionListener.evaluateDouble(scope, ct.comparison().expression(1));
        String comparator = ct.comparison().comparisonOperator().getText();

        ProcessResult success = ProcessResult.SUCCESS;

        //evaluate the if condition, if it is satisfied, evaluate the if block.
        stack.push(this);
        switch (comparator) {
            case ">":
                if (val1 > val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
            case "<":
                if (val1 < val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
            case ">=":
                if (val1 >= val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
            case "<=":
                if (val1 <= val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
            case "=":
            case "==":
                if (val1 == val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
            case "<>":
            case "!=":
            case "!":
                if (val1 != val2) {
                    success = super.process(scope, canvas, ctx, stack);
                }
                break;
        }
        stack.pop();
        scope.pop();
        return success;
    }
}
