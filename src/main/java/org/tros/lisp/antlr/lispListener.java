// Generated from lisp.g4 by ANTLR 4.5
package org.tros.lisp.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link lispParser}.
 */
public interface lispListener extends ParseTreeListener {

    /**
     * Enter a parse tree produced by {@link lispParser#program}.
     *
     * @param ctx the parse tree
     */
    void enterProgram(lispParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link lispParser#program}.
     *
     * @param ctx the parse tree
     */
    void exitProgram(lispParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link lispParser#symExpr}.
     *
     * @param ctx the parse tree
     */
    void enterSymExpr(lispParser.SymExprContext ctx);

    /**
     * Exit a parse tree produced by {@link lispParser#symExpr}.
     *
     * @param ctx the parse tree
     */
    void exitSymExpr(lispParser.SymExprContext ctx);

    /**
     * Enter a parse tree produced by {@link lispParser#symbol}.
     *
     * @param ctx the parse tree
     */
    void enterSymbol(lispParser.SymbolContext ctx);

    /**
     * Exit a parse tree produced by {@link lispParser#symbol}.
     *
     * @param ctx the parse tree
     */
    void exitSymbol(lispParser.SymbolContext ctx);
}
