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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.tros.logo.antlr.logoLexer;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.InterpreterThread;
import org.tros.torgo.LexicalAnalyzer;
import org.tros.torgo.TorgoCanvas;

/**
 * Logo specific processing thread.
 *
 * @author matta
 */
public class AntlrThread extends InterpreterThread {

    /**
     * Protected constructor, should only be created within the package.
     * @param source
     * @param canvas 
     */
    protected AntlrThread(String source, TorgoCanvas canvas) {
        super(source, canvas);
    }

    /**
     * Get the parse tree, this should only be used by the following function.
     * @return 
     */
    @Override
    protected ParseTree getParseTree() {
        //lexical analysis and parsing with ANTLR
        logoLexer lexer = new logoLexer(new ANTLRInputStream(source));
        logoParser parser = new logoParser(new CommonTokenStream(lexer));
        //get the prog element from the parse tree
        //the prog element is the root element defined in the logo.g4 grammar.
        return  parser.prog();
    }

    /**
     * Get a generic lexical analysis which returns all code blocks as well
     * as the entry point to the application.
     * @param tree
     * @return 
     */
    @Override
    protected LexicalAnalyzer getLexicalAnalysis(ParseTree tree) {
        return CommandListener.lexicalAnalysis(tree);
    }
}
