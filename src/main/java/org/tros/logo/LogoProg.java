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
import org.tros.torgo.ReturnValue;
import org.tros.torgo.Scope;

/**
 * Represents the entrypoint of execution for the Logo script.
 *
 * @author matta
 */
class LogoProg extends LogoBlock {

    private static final Logger logger = Logger.getLogger(LogoProg.class.getName());

    /**
     * Constructor.
     *
     * @param ctx
     */
    protected LogoProg(ParserRuleContext ctx) {
        super(ctx);
    }

    /**
     * This will process the 'prog' element. The prog element is the entry point
     * to the program and thus differs from a 'LogoBlock' by needing to do an
     * initial scope.push().
     *
     * @param scope
     * @return
     */
    @Override
    public ReturnValue process(Scope scope) {
        logger.log(Level.FINEST, "[{0}]: Line: {1}, Start: {2}, End: {3}", new Object[]{ctx.getClass().getName(), ctx.getStart().getLine(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex()});
        scope.push(this);
        listeners.stream().forEach((l) -> {
            l.currStatement(this, scope);
        });

        ReturnValue success = super.process(scope);

        scope.pop();
        return success;
    }

}
