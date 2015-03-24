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

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tros.logo.antlr.logoLexer;
import org.tros.logo.antlr.logoParser;
import org.tros.logo.swing.LogoPanel;
import org.tros.logo.swing.LogoMenuBar;
import org.tros.logo.swing.LogoUserInputPanel;
import org.tros.torgo.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.DynamicScope;
import org.tros.torgo.InterpreterThread;
import org.tros.torgo.LexicalAnalyzer;
import org.tros.torgo.TorgoScreen;
import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.swing.TorgoToolBar;

/**
 * The Logo factory/controller.
 *
 * @author matta
 */
public final class LogoController extends ControllerBase {

    private LogoPanel canvas;
    private LogoUserInputPanel panel;

    /**
     * Constructor, must be public for the ServiceLoader. Only initializes basic
     * object needs.
     */
    public LogoController() {
    }

    @Override
    protected TorgoTextConsole createConsole(Controller app) {
        if (panel == null) {
            panel = new LogoUserInputPanel(app);
        }

        return panel;
    }

    @Override
    protected TorgoScreen createCanvas(TorgoTextConsole console) {
        if (canvas == null) {
            canvas = new LogoPanel(console);
        }

        return canvas;
    }

    @Override
    protected JToolBar createToolBar() {
        return new TorgoToolBar(super.getWindow(), (Controller) this);
    }

    @Override
    protected JMenuBar createMenuBar() {
        return new LogoMenuBar(super.getWindow(), (Controller) this, canvas);
    }

    /**
     * Run, this is the main entry point.
     */
    @Override
    public void runHelper() {
    }

    /**
     * Get an interpreter thread.
     *
     * @param source
     * @return
     */
    @Override
    protected InterpreterThread createInterpreterThread(String source) {
        return new InterpreterThread(source, new DynamicScope()) {

            @Override
            protected LexicalAnalyzer getLexicalAnalysis(String source) {
                //lexical analysis and parsing with ANTLR
                logoLexer lexer = new logoLexer(new ANTLRInputStream(source));
                logoParser parser = new logoParser(new CommonTokenStream(lexer));
                //get the prog element from the parse tree
                //the prog element is the root element defined in the logo.g4 grammar.
                return LexicalListener.lexicalAnalysis(parser.prog(), canvas);
            }

            @Override
            protected void process(CodeBlock entryPoint) {
                entryPoint.process(scope);
            }
        };
    }

    /**
     * Get the supported language.
     *
     * @return
     */
    @Override
    public String getLang() {
        return "logo";
    }
}
