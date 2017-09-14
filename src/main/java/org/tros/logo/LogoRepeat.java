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
import java.util.HashMap;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.LogoParser;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.ReturnValue.ProcessResult;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.types.NumberType;

/**
 * Represents a repeating block in Logo.
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
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoRepeat.class);

    /**
     * Constructor.
     *
     * @param ctx the parser context from antlr.
     */
    protected LogoRepeat(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * Process the repeat.
     *
     * @param scope the current scope of the program.
     * @return success if all repeated blocks are successful.
     */
    @Override
    public ReturnValue process(Scope scope) {
        LOGGER.verbose(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()));
        scope.push(this);
        super.variables.add(0, new HashMap<>());
        listeners.fire().currStatement(this, scope);

        ReturnValue success = ReturnValue.SUCCESS;
        int repeat = ((Number) ExpressionListener.evaluate(scope, ((LogoParser.RepeatContext) ctx).expression()).getValue()).intValue();
        for (int ii = 0; ii < repeat && success.getResult() == ProcessResult.SUCCESS; ii++) {
            //this sets the repcount variable for dereferencing in the block.
            scope.setNew(REPCOUNT_VAR, new InterpreterValue(NumberType.INSTANCE, ii + 1));
            success = super.process(scope);
        }

        super.variables.remove(0);
        scope.pop();
        return success;
    }
}
