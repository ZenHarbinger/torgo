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
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.Scope;

/**
 * Represents the entrypoint of execution for the Logo script.
 *
 * @author matta
 */
class LogoProg extends LogoBlock {

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoProg.class);

    /**
     * Constructor.
     *
     * @param ctx the parser context from antlr.
     */
    protected LogoProg(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * This will process the 'prog' element. The prog element is the entry point
     * to the program and thus differs from a 'LogoBlock' by needing to do an
     * initial scope.push().
     *
     * @param scope the current scope of the program.
     * @return the result of processing the block.
     */
    @Override
    public ReturnValue process(Scope scope) {
        LOGGER.verbose(MessageFormat.format("[{0}]: Line: {1}, Start: {2}, End: {3}", ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()));
        scope.push(this);
        super.variables.add(0, new HashMap<>());
        listeners.fire().currStatement(this, scope);

        ReturnValue success = super.process(scope);

        super.variables.remove(0);
        scope.pop();
        return success;
    }

}
