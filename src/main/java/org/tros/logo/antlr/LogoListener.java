// Generated from Logo.g4 by ANTLR 4.7

package org.tros.logo.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LogoParser}.
 */
public interface LogoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LogoParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LogoParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LogoParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(LogoParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(LogoParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(LogoParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(LogoParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void enterProcedureInvocation(LogoParser.ProcedureInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#procedureInvocation}.
	 * @param ctx the parse tree
	 */
	void exitProcedureInvocation(LogoParser.ProcedureInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#procedureDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDeclaration(LogoParser.ProcedureDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#procedureDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDeclaration(LogoParser.ProcedureDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#parameterDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclarations(LogoParser.ParameterDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#parameterDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclarations(LogoParser.ParameterDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(LogoParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(LogoParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#repeat}.
	 * @param ctx the parse tree
	 */
	void enterRepeat(LogoParser.RepeatContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#repeat}.
	 * @param ctx the parse tree
	 */
	void exitRepeat(LogoParser.RepeatContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LogoParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LogoParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#ife}.
	 * @param ctx the parse tree
	 */
	void enterIfe(LogoParser.IfeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#ife}.
	 * @param ctx the parse tree
	 */
	void exitIfe(LogoParser.IfeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(LogoParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(LogoParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(LogoParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(LogoParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#make}.
	 * @param ctx the parse tree
	 */
	void enterMake(LogoParser.MakeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#make}.
	 * @param ctx the parse tree
	 */
	void exitMake(LogoParser.MakeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#localmake}.
	 * @param ctx the parse tree
	 */
	void enterLocalmake(LogoParser.LocalmakeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#localmake}.
	 * @param ctx the parse tree
	 */
	void exitLocalmake(LogoParser.LocalmakeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#print_command}.
	 * @param ctx the parse tree
	 */
	void enterPrint_command(LogoParser.Print_commandContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#print_command}.
	 * @param ctx the parse tree
	 */
	void exitPrint_command(LogoParser.Print_commandContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#quotedstring}.
	 * @param ctx the parse tree
	 */
	void enterQuotedstring(LogoParser.QuotedstringContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#quotedstring}.
	 * @param ctx the parse tree
	 */
	void exitQuotedstring(LogoParser.QuotedstringContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(LogoParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(LogoParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(LogoParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(LogoParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#parenExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(LogoParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#parenExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(LogoParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#signExpression}.
	 * @param ctx the parse tree
	 */
	void enterSignExpression(LogoParser.SignExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#signExpression}.
	 * @param ctx the parse tree
	 */
	void exitSignExpression(LogoParser.SignExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#powerExpression}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpression(LogoParser.PowerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#powerExpression}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpression(LogoParser.PowerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#multiplyingExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplyingExpression(LogoParser.MultiplyingExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#multiplyingExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplyingExpression(LogoParser.MultiplyingExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(LogoParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(LogoParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#deref}.
	 * @param ctx the parse tree
	 */
	void enterDeref(LogoParser.DerefContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#deref}.
	 * @param ctx the parse tree
	 */
	void exitDeref(LogoParser.DerefContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#fd}.
	 * @param ctx the parse tree
	 */
	void enterFd(LogoParser.FdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#fd}.
	 * @param ctx the parse tree
	 */
	void exitFd(LogoParser.FdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#bk}.
	 * @param ctx the parse tree
	 */
	void enterBk(LogoParser.BkContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#bk}.
	 * @param ctx the parse tree
	 */
	void exitBk(LogoParser.BkContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#rt}.
	 * @param ctx the parse tree
	 */
	void enterRt(LogoParser.RtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#rt}.
	 * @param ctx the parse tree
	 */
	void exitRt(LogoParser.RtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#lt}.
	 * @param ctx the parse tree
	 */
	void enterLt(LogoParser.LtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#lt}.
	 * @param ctx the parse tree
	 */
	void exitLt(LogoParser.LtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#cs}.
	 * @param ctx the parse tree
	 */
	void enterCs(LogoParser.CsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#cs}.
	 * @param ctx the parse tree
	 */
	void exitCs(LogoParser.CsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#pu}.
	 * @param ctx the parse tree
	 */
	void enterPu(LogoParser.PuContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#pu}.
	 * @param ctx the parse tree
	 */
	void exitPu(LogoParser.PuContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#pd}.
	 * @param ctx the parse tree
	 */
	void enterPd(LogoParser.PdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#pd}.
	 * @param ctx the parse tree
	 */
	void exitPd(LogoParser.PdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#ht}.
	 * @param ctx the parse tree
	 */
	void enterHt(LogoParser.HtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#ht}.
	 * @param ctx the parse tree
	 */
	void exitHt(LogoParser.HtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#st}.
	 * @param ctx the parse tree
	 */
	void enterSt(LogoParser.StContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#st}.
	 * @param ctx the parse tree
	 */
	void exitSt(LogoParser.StContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#home}.
	 * @param ctx the parse tree
	 */
	void enterHome(LogoParser.HomeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#home}.
	 * @param ctx the parse tree
	 */
	void exitHome(LogoParser.HomeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#stop}.
	 * @param ctx the parse tree
	 */
	void enterStop(LogoParser.StopContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#stop}.
	 * @param ctx the parse tree
	 */
	void exitStop(LogoParser.StopContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#setxy}.
	 * @param ctx the parse tree
	 */
	void enterSetxy(LogoParser.SetxyContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#setxy}.
	 * @param ctx the parse tree
	 */
	void exitSetxy(LogoParser.SetxyContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#random}.
	 * @param ctx the parse tree
	 */
	void enterRandom(LogoParser.RandomContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#random}.
	 * @param ctx the parse tree
	 */
	void exitRandom(LogoParser.RandomContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#getangle}.
	 * @param ctx the parse tree
	 */
	void enterGetangle(LogoParser.GetangleContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#getangle}.
	 * @param ctx the parse tree
	 */
	void exitGetangle(LogoParser.GetangleContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#getx}.
	 * @param ctx the parse tree
	 */
	void enterGetx(LogoParser.GetxContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#getx}.
	 * @param ctx the parse tree
	 */
	void exitGetx(LogoParser.GetxContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#gety}.
	 * @param ctx the parse tree
	 */
	void enterGety(LogoParser.GetyContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#gety}.
	 * @param ctx the parse tree
	 */
	void exitGety(LogoParser.GetyContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#repcount}.
	 * @param ctx the parse tree
	 */
	void enterRepcount(LogoParser.RepcountContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#repcount}.
	 * @param ctx the parse tree
	 */
	void exitRepcount(LogoParser.RepcountContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#fore}.
	 * @param ctx the parse tree
	 */
	void enterFore(LogoParser.ForeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#fore}.
	 * @param ctx the parse tree
	 */
	void exitFore(LogoParser.ForeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#pc}.
	 * @param ctx the parse tree
	 */
	void enterPc(LogoParser.PcContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#pc}.
	 * @param ctx the parse tree
	 */
	void exitPc(LogoParser.PcContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#cc}.
	 * @param ctx the parse tree
	 */
	void enterCc(LogoParser.CcContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#cc}.
	 * @param ctx the parse tree
	 */
	void exitCc(LogoParser.CcContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#hexcolor}.
	 * @param ctx the parse tree
	 */
	void enterHexcolor(LogoParser.HexcolorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#hexcolor}.
	 * @param ctx the parse tree
	 */
	void exitHexcolor(LogoParser.HexcolorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#pause}.
	 * @param ctx the parse tree
	 */
	void enterPause(LogoParser.PauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#pause}.
	 * @param ctx the parse tree
	 */
	void exitPause(LogoParser.PauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#ds}.
	 * @param ctx the parse tree
	 */
	void enterDs(LogoParser.DsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#ds}.
	 * @param ctx the parse tree
	 */
	void exitDs(LogoParser.DsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#fontname}.
	 * @param ctx the parse tree
	 */
	void enterFontname(LogoParser.FontnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#fontname}.
	 * @param ctx the parse tree
	 */
	void exitFontname(LogoParser.FontnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#fontsize}.
	 * @param ctx the parse tree
	 */
	void enterFontsize(LogoParser.FontsizeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#fontsize}.
	 * @param ctx the parse tree
	 */
	void exitFontsize(LogoParser.FontsizeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#fontstyle}.
	 * @param ctx the parse tree
	 */
	void enterFontstyle(LogoParser.FontstyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#fontstyle}.
	 * @param ctx the parse tree
	 */
	void exitFontstyle(LogoParser.FontstyleContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#style}.
	 * @param ctx the parse tree
	 */
	void enterStyle(LogoParser.StyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#style}.
	 * @param ctx the parse tree
	 */
	void exitStyle(LogoParser.StyleContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(LogoParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(LogoParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link LogoParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(LogoParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link LogoParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(LogoParser.CommentContext ctx);
}