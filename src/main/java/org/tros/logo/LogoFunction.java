/*
 * Copyright 2015-2016 Matthew Aguirre
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
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.interpreter.CodeFunction;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.ReturnValue.ProcessResult;
import org.tros.torgo.interpreter.Scope;

/**
 * Supports functions with parameters.
 *
 * @author matta
 */
class LogoFunction extends LogoBlock implements CodeFunction {

    private final String funcitonName;
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoFunction.class);

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
        LOGGER.verbose(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()));
        scope.push(this);
        
        super.variables.add(0, new HashMap<>());

        params.keySet().forEach((key) -> {
            scope.setNew(key, params.get(key));
        });

        listeners.fire().currStatement(this, scope);

        ReturnValue ret = null;
        try {
            ret = super.process(scope);
        } catch (Exception ex) {
            LOGGER.warn(MessageFormat.format("{0} -> {1}", ex.getClass().getName(), ex.getMessage()), ex);
        }
        if (ret != null && ret.getResult() != ProcessResult.HALT) {
            //NOT HALT!
            ret = new ReturnValue(ret.getType(), ret.getValue(), ProcessResult.SUCCESS);
        } else {
            //HALT or ERROR (possible null)!
            ret = ReturnValue.HALT;
        }

        super.variables.remove(0);

        scope.pop();

        return ret;
    }
}
