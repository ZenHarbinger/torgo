/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tros.logo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.ReturnValue;
import org.tros.torgo.Scope;

/**
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
