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

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tros.logo.antlr.LogoLexer;
import org.tros.logo.antlr.LogoParser;
import org.tros.logo.swing.LogoPanel;
import org.tros.logo.swing.LogoMenuBar;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.interpreter.LexicalAnalyzer;
import org.tros.torgo.TorgoScreen;
import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.swing.TorgoToolBar;
import org.tros.torgo.swing.TorgoUserInputPanel;

/**
 * The Logo factory/controller.
 *
 * @author matta
 */
public abstract class LogoController extends ControllerBase {

    private LogoPanel canvas;
    private TorgoUserInputPanel panel;

    /**
     * Constructor, must be public for the ServiceLoader. Only initializes basic
     * object needs.
     */
    public LogoController() {
    }

    @Override
    protected String getWindowName() {
        return LogoController.class.getName();
    }

    @Override
    protected TorgoTextConsole createConsole(Controller app) {
        if (panel == null) {
            panel = new TorgoUserInputPanel(app, "Logo", false, "text/logo");
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

    protected abstract Scope createScope();

    /**
     * Get an interpreter thread.
     *
     * @param source the string representation of the program to interpret.
     * @return a thread ready to interpret the source.
     */
    @Override
    protected InterpreterThread createInterpreterThread(String source) {
        return new InterpreterThread(source, createScope()) {

            @Override
            protected LexicalAnalyzer getLexicalAnalysis(String source) {
                if (canvas != null) {
                    canvas.reset();
                }
                //lexical analysis and parsing with ANTLR
                LogoLexer lexer = new LogoLexer(CharStreams.fromString(source));
                LogoParser parser = new LogoParser(new CommonTokenStream(lexer));
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
}
