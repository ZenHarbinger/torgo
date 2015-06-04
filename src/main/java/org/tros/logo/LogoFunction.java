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
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.CodeFunction;
import org.tros.torgo.InterpreterValue;
import org.tros.torgo.ReturnValue;
import org.tros.torgo.ReturnValue.ProcessResult;
import org.tros.torgo.Scope;

/**
 * Supports functions with parameters.
 *
 * @author matta
 */
class LogoFunction extends LogoBlock implements CodeFunction {

    private final String funcitonName;
    private static final Log logger = LogFactory.getLog(LogoFunction.class);

    /**
     * Constructor
     *
     * @param functionName
     * @param ctx
     */
    protected LogoFunction(String functionName, ParserRuleContext ctx) {
        super(ctx);
        this.funcitonName = functionName;
    }

    /**
     * Get the function name
     *
     * @return
     */
    @Override
    public String getFunctionName() {
        return funcitonName;
    }

    /**
     * Process the function.
     *
     * @param scope
     * @return
     */
    @Override
    public ReturnValue process(Scope scope, Map<String, InterpreterValue> params) {
        logger.trace(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()));
        scope.push(this);

        for (String key : params.keySet()) {
            scope.setNew(key, params.get(key));
        }

        listeners.fire().currStatement(this, scope);

        ReturnValue ret = null;
        try {
            ret = super.process(scope);
        } catch (Exception ex) {
            logger.warn(MessageFormat.format("{0} -> {1}", ex.getClass().getName(), ex.getMessage()), ex);
        }
        if (ret != null && ret.getResult() != ProcessResult.HALT) {
            //NOT HALT!
            ret = new ReturnValue(ret.getType(), ret.getValue(), ProcessResult.SUCCESS);
        } else {
            //HALT or ERROR (possible null)!
            ret = ReturnValue.HALT;
        }
        scope.pop();

        return ret;
    }
}
