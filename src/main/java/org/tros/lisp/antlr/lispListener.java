// Generated from lisp.g4 by ANTLR 4.5.3

package org.tros.lisp.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link lispParser}.
 */
public interface lispListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link lispParser#sexpr}.
	 * @param ctx the parse tree
	 */
	void enterSexpr(lispParser.SexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#sexpr}.
	 * @param ctx the parse tree
	 */
	void exitSexpr(lispParser.SexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(lispParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(lispParser.ItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(lispParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(lispParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#seq}.
	 * @param ctx the parse tree
	 */
	void enterSeq(lispParser.SeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#seq}.
	 * @param ctx the parse tree
	 */
	void exitSeq(lispParser.SeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(lispParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(lispParser.AtomContext ctx);
}