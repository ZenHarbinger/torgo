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

import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.logo.antlr.logoBaseListener;
import org.tros.logo.antlr.logoParser;

/**
 * Gets a list of commands to execute.  This does not execute then, but
 * instead builds a tree of commands to run.  Once this tree is built,
 * it will be interpreted.
 * @author matta
 */
public class CommandListener extends logoBaseListener {

    private final Stack<LogoBlock> stack = new Stack<>();

    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     */
    private CommandListener() {
    }

    /**
     * Walk the parse tree and build a command structure to interpret.
     * @param tree
     * @return 
     */
    public static LogoBlock lexicalAnalysis(ParseTree tree) {
        CommandListener cl = new CommandListener();
        ParseTreeWalker.DEFAULT.walk(cl, tree);
        return cl.getLogoCommands();
    }

    @Override
    public void enterProcedureDeclaration(logoParser.ProcedureDeclarationContext ctx) {
        LogoFunction lf = new LogoFunction(ctx.name().getText(), ctx);
        stack.peek().addFunction(lf);
        stack.push(lf);
    }

    @Override
    public void exitProcedureDeclaration(logoParser.ProcedureDeclarationContext ctx) {
        stack.pop();
    }

    @Override
    public void enterDs(logoParser.DsContext ctx) {
        stack.peek().addCommand(new LogoStatement("ds", ctx));
    }

    @Override
    public void enterCc(logoParser.CcContext ctx) {
        stack.peek().addCommand(new LogoStatement("cc", ctx));
    }

    @Override
    public void enterPc(logoParser.PcContext ctx) {
        stack.peek().addCommand(new LogoStatement("pc", ctx));
    }

    @Override
    public void enterFontname(logoParser.FontnameContext ctx) {
        stack.peek().addCommand(new LogoStatement("fontname", ctx));
    }

    @Override
    public void enterFontstyle(logoParser.FontstyleContext ctx) {
        stack.peek().addCommand(new LogoStatement("fontstyle", ctx));
    }

    @Override
    public void enterFontsize(logoParser.FontsizeContext ctx) {
        stack.peek().addCommand(new LogoStatement("fontsize", ctx));
    }

    @Override
    public void enterProg(logoParser.ProgContext ctx) {
        stack.push(new LogoBlock(ctx));
    }

    @Override
    public void enterPrint(logoParser.PrintContext ctx) {
        stack.peek().addCommand(new LogoStatement("print", ctx));
    }

    @Override
    public void enterFd(logoParser.FdContext ctx) {
        stack.peek().addCommand(new LogoStatement("fd", ctx));
    }

    @Override
    public void enterBk(logoParser.BkContext ctx) {
        stack.peek().addCommand(new LogoStatement("bk", ctx));
    }

    @Override
    public void enterRt(logoParser.RtContext ctx) {
        stack.peek().addCommand(new LogoStatement("rt", ctx));
    }

    @Override
    public void enterLt(logoParser.LtContext ctx) {
        stack.peek().addCommand(new LogoStatement("lt", ctx));
    }

    @Override
    public void enterPu(logoParser.PuContext ctx) {
        stack.peek().addCommand(new LogoStatement("pu", ctx));
    }

    @Override
    public void enterPd(logoParser.PdContext ctx) {
        stack.peek().addCommand(new LogoStatement("pd", ctx));
    }

    @Override
    public void enterCs(logoParser.CsContext ctx) {
        stack.peek().addCommand(new LogoStatement("cs", ctx));
    }

    @Override
    public void enterHt(logoParser.HtContext ctx) {
        stack.peek().addCommand(new LogoStatement("ht", ctx));
    }

    @Override
    public void enterSt(logoParser.StContext ctx) {
        stack.peek().addCommand(new LogoStatement("st", ctx));
    }

    @Override
    public void enterHome(logoParser.HomeContext ctx) {
        stack.peek().addCommand(new LogoStatement("home", ctx));
    }

    @Override
    public void enterLabel(logoParser.LabelContext ctx) {
        stack.peek().addCommand(new LogoStatement("label", ctx));
    }

    @Override
    public void enterSetxy(logoParser.SetxyContext ctx) {
        stack.peek().addCommand(new LogoStatement("setxy", ctx));
    }

    @Override
    public void enterProcedureInvocation(logoParser.ProcedureInvocationContext ctx) {
        stack.peek().addCommand(new LogoStatement(ctx.name().getText(), ctx));
    }

    @Override
    public void enterMake(logoParser.MakeContext ctx) {
        stack.peek().addCommand(new LogoStatement("make", ctx));
    }

    @Override
    public void enterLocalmake(logoParser.LocalmakeContext ctx) {
        stack.peek().addCommand(new LogoStatement("localmake", ctx));
    }

    @Override
    public void enterIfe(logoParser.IfeContext ctx) {
        //LogoIf ife = new LogoIf(ctx.comparison());
        LogoIf lc = new LogoIf(ctx);
        stack.peek().addCommand(lc);
        stack.push(lc);
    }

    @Override
    public void exitIfe(logoParser.IfeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterStop(logoParser.StopContext ctx) {
        stack.peek().addCommand(new LogoStatement("stop", ctx));
    }

    @Override
    public void enterFore(logoParser.ForeContext ctx) {
        LogoFor lc = new LogoFor(ctx);
        stack.peek().addCommand(lc);
        stack.push(lc);
    }

    @Override
    public void exitFore(logoParser.ForeContext ctx) {
        stack.pop();
    }

    @Override
    public void enterRepeat(logoParser.RepeatContext ctx) {
        LogoRepeat lc = new LogoRepeat(ctx);
        stack.peek().addCommand(lc);
        stack.push(lc);
    }

    @Override
    public void enterPause(logoParser.PauseContext ctx) {
        stack.peek().addCommand(new LogoStatement("pause", ctx));
    }

    @Override
    public void exitRepeat(logoParser.RepeatContext ctx) {
        stack.pop();
    }

    public LogoBlock getLogoCommands() {
        return stack.peek();
    }
}
