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

import org.tros.torgo.interpreter.CodeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.logo.antlr.LogoBaseListener;
import org.tros.logo.antlr.LogoParser;
import org.tros.torgo.interpreter.LexicalAnalyzer;

/**
 * Gets a list of commands to execute. This does not execute then, but instead
 * builds a tree of commands to run. Once this tree is built, it will be
 * interpreted. This is for the Logo language only.
 *
 * @author matta
 */
final class LexicalListener extends LogoBaseListener implements LexicalAnalyzer {

    private final Stack<CodeBlock> stack = new Stack<>();
    private final ArrayList<CodeBlock> blocks = new ArrayList<>();
    private final LogoCanvas canvas;

    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     *
     * @param canvas the drawing surface for display.
     */
    private LexicalListener(LogoCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Walk the parse tree and build a command structure to interpret.
     *
     * @param tree the parse tree from antlr.
     * @param canvas the drawing surface for display.
     * @return
     */
    protected static LexicalAnalyzer lexicalAnalysis(ParseTree tree, LogoCanvas canvas) {
        LexicalListener cl = new LexicalListener(canvas);
        ParseTreeWalker.DEFAULT.walk(cl, tree);
        return cl;
    }

    @Override
    public void enterProcedureDeclaration(LogoParser.ProcedureDeclarationContext ctx) {
        LogoFunction lf = new LogoFunction(ctx.name().getText(), ctx);
        blocks.add(lf);
        stack.peek().addFunction(lf);
        lf.setParent(stack.peek());
        stack.push(lf);
    }

    @Override
    public void exitProcedureDeclaration(LogoParser.ProcedureDeclarationContext ctx) {
        stack.pop();
    }

    @Override
    public void enterDs(LogoParser.DsContext ctx) {
        LogoStatement logoStatement = new LogoStatement("ds", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterCc(LogoParser.CcContext ctx) {
        LogoStatement logoStatement = new LogoStatement("cc", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPc(LogoParser.PcContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pc", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontname(LogoParser.FontnameContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontname", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontstyle(LogoParser.FontstyleContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontstyle", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontsize(LogoParser.FontsizeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontsize", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterProg(LogoParser.ProgContext ctx) {
        stack.push(new LogoProg(ctx));
    }

    @Override
    public void enterPrint_command(LogoParser.Print_commandContext ctx) {
        LogoStatement logoStatement = new LogoStatement("print", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFd(LogoParser.FdContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fd", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterBk(LogoParser.BkContext ctx) {
        LogoStatement logoStatement = new LogoStatement("bk", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterRt(LogoParser.RtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("rt", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterLt(LogoParser.LtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("lt", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPu(LogoParser.PuContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pu", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPd(LogoParser.PdContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pd", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterCs(LogoParser.CsContext ctx) {
        LogoStatement logoStatement = new LogoStatement("cs", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterHt(LogoParser.HtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("ht", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterSt(LogoParser.StContext ctx) {
        LogoStatement logoStatement = new LogoStatement("st", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterHome(LogoParser.HomeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("home", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterSetxy(LogoParser.SetxyContext ctx) {
        LogoStatement logoStatement = new LogoStatement("setxy", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterProcedureInvocation(LogoParser.ProcedureInvocationContext ctx) {
        LogoStatement logoStatement = new LogoStatement(ctx.name().getText(), ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterMake(LogoParser.MakeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("make", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterLocalmake(LogoParser.LocalmakeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("localmake", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterIfe(LogoParser.IfeContext ctx) {
        //LogoIf ife = new LogoIf(ctx.comparison());
        LogoIf lc = new LogoIf(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void exitIfe(LogoParser.IfeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterStop(LogoParser.StopContext ctx) {
        LogoStatement logoStatement = new LogoStatement("stop", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFore(LogoParser.ForeContext ctx) {
        LogoFor lc = new LogoFor(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void exitFore(LogoParser.ForeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterRepeat(LogoParser.RepeatContext ctx) {
        LogoRepeat lc = new LogoRepeat(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void enterPause(LogoParser.PauseContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pause", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void exitRepeat(LogoParser.RepeatContext ctx) {
        stack.pop();
    }

    /**
     * Gets the CodeBlock object that is the beginning of the program.
     *
     * @return the starting point in the program.
     */
    @Override
    public CodeBlock getEntryPoint() {
        return stack.peek();
    }

    /**
     * Gets all of the CodeBlock objects defined by the program.
     *
     * @return the collection of blocks in the program.
     */
    @Override
    public Collection<CodeBlock> getCodeBlocks() {
        return blocks;
    }
}
