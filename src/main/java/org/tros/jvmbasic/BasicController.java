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

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tros.jvmbasic.antlr.jvmBasicLexer;
import org.tros.jvmbasic.antlr.jvmBasicParser;
import org.tros.jvmbasic.swing.BasicMenuBar;
import org.tros.jvmbasic.swing.BasicUserInputPanel;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.Controller;
import org.tros.torgo.TorgoScreen;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.LexicalAnalyzer;
import org.tros.torgo.interpreter.LexicalScope;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.swing.TorgoToolBar;

/**
 * This is going to take more work than I first though since I will have to come
 * up with an interpreter type to represent the list data structures.
 *
 * @author matta
 */
public class BasicController extends ControllerBase {

    private BasicUserInputPanel panel;

    @Override
    protected TorgoTextConsole createConsole(Controller app) {
        if (panel == null) {
            panel = new BasicUserInputPanel(app);
        }

        return panel;
    }

    @Override
    protected JToolBar createToolBar() {
        return new TorgoToolBar(super.getWindow(), (Controller) this);
    }

    @Override
    protected JMenuBar createMenuBar() {
        return new BasicMenuBar(super.getWindow(), (Controller) this);
    }

    /**
     * Run, this is the main entry point.
     */
    @Override
    public void runHelper() {
    }

    protected Scope createScope() {
        return new LexicalScope();
    }

    /**
     * Get an interpreter thread.
     *
     * @param source
     * @return
     */
    @Override
    protected InterpreterThread createInterpreterThread(String source) {
        return new InterpreterThread(source, createScope()) {

            @Override
            protected LexicalAnalyzer getLexicalAnalysis(String source) {
                //lexical analysis and parsing with ANTLR
                jvmBasicLexer lexer = new jvmBasicLexer(new ANTLRInputStream(source));
                jvmBasicParser parser = new jvmBasicParser(new CommonTokenStream(lexer));
                //get the prog element from the parse tree
                //the prog element is the root element defined in the lisp.g4 grammar.
                return LexicalListener.lexicalAnalysis(parser.prog());
            }

            @Override
            protected void process(CodeBlock entryPoint) {
                entryPoint.process(scope);
            }
        };
    }

    @Override
    public void openFile(File file) {
        try {
            openFile(file.toURI().toURL());
        } catch (MalformedURLException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(BasicController.class).fatal(null, ex);
        }
    }

    @Override
    protected TorgoScreen createCanvas(TorgoTextConsole console) {
        return null;
    }

    @Override
    public String getLang() {
        return "jvmBasic";
    }
}
