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
package org.tros.lisp;

import org.tros.torgo.interpreter.CodeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.tros.lisp.antlr.lispBaseListener;
import org.tros.lisp.antlr.lispParser;
import org.tros.torgo.interpreter.LexicalAnalyzer;

/**
 * Gets a list of commands to execute. This does not execute then, but instead
 * builds a tree of commands to run. Once this tree is built, it will be
 * interpreted. This is for the Logo language only.
 *
 * @author matta
 */
final class LexicalListener extends lispBaseListener implements LexicalAnalyzer {

    private final Stack<CodeBlock> stack = new Stack<>();
    private final ArrayList<CodeBlock> blocks = new ArrayList<>();

    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     */
    private LexicalListener() {
    }

    /**
     * Walk the parse tree and build a command structure to interpret.
     *
     * @param tree
     * @return
     */
    protected static LexicalAnalyzer lexicalAnalysis(ParseTree tree) {
        LexicalListener cl = new LexicalListener();
        ParseTreeWalker.DEFAULT.walk(cl, tree);
        return cl;
    }

    @Override
    public void enterSexpr(lispParser.SexprContext ctx) {
        LispProgram lispProgram = new LispProgram(ctx);
        stack.push(lispProgram);
    }

    @Override
    public void enterAtom(lispParser.AtomContext ctx) {
        System.out.println("ATOM: " + ctx.getText());
        super.enterAtom(ctx);
    }

    @Override
    public void enterItem(lispParser.ItemContext ctx) {
        System.out.println("ITEM: " + ctx.getText());
        super.enterItem(ctx);
    }

    @Override
    public void enterList(lispParser.ListContext ctx) {
        System.out.println("LIST: " + ctx.getText());
        try {
            //try to get the first item in the list, if the item is not a terminal SYMBOL, keep going.
            TerminalNode symbol = ctx.seq().item().atom().SYMBOL();
            System.out.println("EXPR: " + symbol.getText());
            LispStatement lispStatement = new LispStatement(symbol.getText(), ctx);
            blocks.add(lispStatement);
            stack.peek().addCommand(lispStatement);
        } catch (Exception ex) {
            //the first item is NOT a terminal SYMBOL
        }
        super.enterList(ctx);
    }

    @Override
    public void enterSeq(lispParser.SeqContext ctx) {
        System.out.println("SEQ: " + ctx.getText());
        super.enterSeq(ctx);
    }

//    @Override
//    public void enterCar(lispParser.CarContext ctx) {
//        LispStatement lispStatement = new LispStatement("car", ctx);
//        blocks.add(lispStatement);
//        stack.peek().addCommand(lispStatement);
//    }
//
//    @Override
//    public void enterCdr(lispParser.CdrContext ctx) {
//        LispStatement lispStatement = new LispStatement("cdr", ctx);
//        blocks.add(lispStatement);
//        stack.peek().addCommand(lispStatement);
//    }
//
//    @Override
//    public void enterCons(lispParser.ConsContext ctx) {
//        LispStatement lispStatement = new LispStatement("cons", ctx);
//        blocks.add(lispStatement);
//        stack.peek().addCommand(lispStatement);
//    }
    /**
     * Gets the CodeBlock object that is the beginning of the program.
     *
     * @return
     */
    @Override
    public CodeBlock getEntryPoint() {
        return stack.peek();
    }

    /**
     * Gets all of the CodeBlock objects defined by the program.
     *
     * @return
     */
    @Override
    public Collection<CodeBlock> getCodeBlocks() {
        return blocks;
    }
}
