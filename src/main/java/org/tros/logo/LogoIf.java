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
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.Scope;

/**
 * Supports if statements/expressions.
 *
 * @author matta
 */
class LogoIf extends LogoBlock {

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoIf.class);

    /**
     * Constructor
     *
     * @param ctx
     */
    protected LogoIf(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * Process the if statement.
     *
     * @param scope
     * @return
     */
    @Override
    public ReturnValue process(Scope scope) {
        LOGGER.verbose(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()));
        scope.push(this);
        listeners.fire().currStatement(this, scope);

        //evaluate the 2 expressions.
        logoParser.IfeContext ct = (logoParser.IfeContext) ctx;
        double val1 = ((Number) ExpressionListener.evaluate(scope, ct.comparison().expression(0)).getValue()).doubleValue();
        double val2 = ((Number) ExpressionListener.evaluate(scope, ct.comparison().expression(1)).getValue()).doubleValue();
        String comparator = ct.comparison().comparisonOperator().getText();

        ReturnValue success = ReturnValue.SUCCESS;

        if (null != comparator) //evaluate the if condition, if it is satisfied, evaluate the if block.
        switch (comparator) {
            case ">":
                if (val1 > val2) {
                    success = super.process(scope);
                }   break;
            case "<":
                if (val1 < val2) {
                    success = super.process(scope);
                }   break;
            case ">=":
                if (val1 >= val2) {
                    success = super.process(scope);
                }   break;
            case "<=":
                if (val1 <= val2) {
                    success = super.process(scope);
                }   break;
            case "=":
            case "==":
                if (val1 == val2) {
                    success = super.process(scope);
                }   break;
            case "<>":
            case "!=":
            case "!":
                //TODO: this ! operator should probably be unary.
                if (val1 != val2) {
                    success = super.process(scope);
                }   break;
            default:
                break;
        }
        scope.pop();
        return success;
    }
}
