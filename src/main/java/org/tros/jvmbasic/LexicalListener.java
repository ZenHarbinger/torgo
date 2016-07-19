/*
 * Copyright 2015-2016 Matthew Aguirre
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
package org.tros.jvmbasic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.jvmbasic.antlr.jvmBasicBaseListener;
import org.tros.jvmbasic.antlr.jvmBasicParser;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.LexicalAnalyzer;

/**
 * Gets a list of commands to execute. This does not execute then, but instead
 * builds a tree of commands to run. Once this tree is built, it will be
 * interpreted.
 *
 * @author matta
 */
class LexicalListener extends jvmBasicBaseListener implements LexicalAnalyzer  {

    private final Stack<CodeBlock> stack = new Stack<>();
    private final ArrayList<CodeBlock> blocks = new ArrayList<>();

    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     */
    private LexicalListener() {
    }

    /**
     * Walk the parse tree and build a command structure to interpret.
     * @param tree
     * @return 
     */
    public static LexicalAnalyzer lexicalAnalysis(ParseTree tree) {
        LexicalListener cl = new LexicalListener();
        ParseTreeWalker.DEFAULT.walk(cl, tree);
        return cl;
    }

    @Override
    public void enterProg(jvmBasicParser.ProgContext ctx) {
        stack.push(new BasicProg(ctx));
    }
    
    

    @Override
    public CodeBlock getEntryPoint() {
        return stack.peek();
    }

    @Override
    public Collection<CodeBlock> getCodeBlocks() {
        return blocks;
    }

    @Override
    public void enterPrintstmt1(jvmBasicParser.Printstmt1Context ctx) {
        BasicStatement basicStatement = new BasicStatement("print", ctx);
        blocks.add(basicStatement);
        stack.peek().addCommand(basicStatement);
    }

    @Override
    public void enterPrstmt(jvmBasicParser.PrstmtContext ctx) {
        super.enterPrstmt(ctx); //To change body of generated methods, choose Tools | Templates.
    }
}
