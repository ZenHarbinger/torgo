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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.InterpreterValue;
import org.tros.torgo.ReturnValue;
import org.tros.torgo.ReturnValue.ProcessResult;
import org.tros.torgo.Scope;

/**
 *
 * @author matta
 */
class LogoRepeat extends LogoBlock {

    /**
     * This variable name is used to set the repcount value, The name is such
     * that it should be be used within logo and so it cannot be over-written or
     * declared.
     */
    public static final String REPCOUNT_VAR = "1_repcount%";
    private final static Logger logger = Logger.getLogger(LogoRepeat.class.getName());

    /**
     * Constructor
     *
     * @param ctx
     */
    protected LogoRepeat(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * Process the repeat
     *
     * @param scope
     * @param canvas
     * @return
     */
    @Override
    public ReturnValue process(Scope scope) {
        logger.log(Level.FINEST, "[{0}]: Line: {1}, Start: {2}, End: {3}", new Object[]{ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()});
        scope.push(this);
        listeners.stream().forEach((l) -> {
            l.currStatement(this, scope);
        });
 
        int repeat = ((Number) ExpressionListener.evaluate(scope, ((logoParser.RepeatContext) ctx).expression()).getValue()).intValue();
        ReturnValue success = ReturnValue.SUCCESS;
        for (int ii = 0; ii < repeat && success.getResult() == ProcessResult.SUCCESS; ii++) {
            //this sets the repcount variable for dereferencing in the block.
            scope.setNew(REPCOUNT_VAR, new InterpreterValue(Type.NUMBER, ii + 1));
            success = super.process(scope);
        }

        scope.pop();
        return success;
    }
}
