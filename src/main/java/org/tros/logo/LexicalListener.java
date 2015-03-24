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

import org.tros.torgo.CodeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.logo.antlr.logoBaseListener;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.LexicalAnalyzer;

/**
 * Gets a list of commands to execute.  This does not execute then, but
 * instead builds a tree of commands to run.  Once this tree is built,
 * it will be interpreted.  This is for the Logo language only.
 * @author matta
 */
class LexicalListener extends logoBaseListener implements LexicalAnalyzer{

    private final Stack<CodeBlock> stack = new Stack<>();
    private final ArrayList<CodeBlock> blocks = new ArrayList<>();
    private final LogoCanvas canvas;

    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     */
    private LexicalListener(LogoCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Walk the parse tree and build a command structure to interpret.
     * @param tree
     * @return 
     */
    protected static LexicalAnalyzer lexicalAnalysis(ParseTree tree, LogoCanvas canvas) {
        LexicalListener cl = new LexicalListener(canvas);
        ParseTreeWalker.DEFAULT.walk(cl, tree);
        return cl;
    }

    @Override
    public void enterProcedureDeclaration(logoParser.ProcedureDeclarationContext ctx) {
        LogoFunction lf = new LogoFunction(ctx.name().getText(), ctx);
        blocks.add(lf);
        stack.peek().addFunction(lf);
        lf.setParent(stack.peek());
        stack.push(lf);
    }

    @Override
    public void exitProcedureDeclaration(logoParser.ProcedureDeclarationContext ctx) {
        stack.pop();
    }

    @Override
    public void enterDs(logoParser.DsContext ctx) {
        LogoStatement logoStatement = new LogoStatement("ds", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterCc(logoParser.CcContext ctx) {
        LogoStatement logoStatement = new LogoStatement("cc", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPc(logoParser.PcContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pc", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontname(logoParser.FontnameContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontname", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontstyle(logoParser.FontstyleContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontstyle", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFontsize(logoParser.FontsizeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fontsize", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterProg(logoParser.ProgContext ctx) {
        stack.push(new LogoProg(ctx));
    }

    @Override
    public void enterPrint(logoParser.PrintContext ctx) {
        LogoStatement logoStatement = new LogoStatement("print", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFd(logoParser.FdContext ctx) {
        LogoStatement logoStatement = new LogoStatement("fd", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterBk(logoParser.BkContext ctx) {
        LogoStatement logoStatement = new LogoStatement("bk", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterRt(logoParser.RtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("rt", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterLt(logoParser.LtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("lt", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPu(logoParser.PuContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pu", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterPd(logoParser.PdContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pd", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterCs(logoParser.CsContext ctx) {
        LogoStatement logoStatement = new LogoStatement("cs", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterHt(logoParser.HtContext ctx) {
        LogoStatement logoStatement = new LogoStatement("ht", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterSt(logoParser.StContext ctx) {
        LogoStatement logoStatement = new LogoStatement("st", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterHome(logoParser.HomeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("home", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterSetxy(logoParser.SetxyContext ctx) {
        LogoStatement logoStatement = new LogoStatement("setxy", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterProcedureInvocation(logoParser.ProcedureInvocationContext ctx) {
        LogoStatement logoStatement = new LogoStatement(ctx.name().getText(), ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterMake(logoParser.MakeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("make", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterLocalmake(logoParser.LocalmakeContext ctx) {
        LogoStatement logoStatement = new LogoStatement("localmake", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterIfe(logoParser.IfeContext ctx) {
        //LogoIf ife = new LogoIf(ctx.comparison());
        LogoIf lc = new LogoIf(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void exitIfe(logoParser.IfeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterStop(logoParser.StopContext ctx) {
        LogoStatement logoStatement = new LogoStatement("stop", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void enterFore(logoParser.ForeContext ctx) {
        LogoFor lc = new LogoFor(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void exitFore(logoParser.ForeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterRepeat(logoParser.RepeatContext ctx) {
        LogoRepeat lc = new LogoRepeat(ctx);
        blocks.add(lc);
        stack.peek().addCommand(lc);
        lc.setParent(stack.peek());
        stack.push(lc);
    }

    @Override
    public void enterPause(logoParser.PauseContext ctx) {
        LogoStatement logoStatement = new LogoStatement("pause", ctx, canvas);
        blocks.add(logoStatement);
        stack.peek().addCommand(logoStatement);
    }

    @Override
    public void exitRepeat(logoParser.RepeatContext ctx) {
        stack.pop();
    }

    /**
     * Gets the CodeBlock object that is the beginning of the program.
     * @return 
     */
    @Override
    public CodeBlock getEntryPoint() {
        return stack.peek();
    }

    /**
     * Gets all of the CodeBlock objects defined by the program.
     * @return 
     */
    @Override
    public Collection<CodeBlock> getCodeBlocks() {
        return blocks;
    }
}
