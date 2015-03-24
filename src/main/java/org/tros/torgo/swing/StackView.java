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
package org.tros.torgo.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import org.tros.torgo.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.InterpreterListener;
import org.tros.torgo.InterpreterThread;
import org.tros.torgo.InterpreterValue;
import org.tros.torgo.Scope;
import org.tros.torgo.ScopeListener;

/**
 * Allows viewing a call stack w/ variables as code is executed.
 *
 * @author matta
 */
public class StackView extends TorgoWindow implements InterpreterListener {

    private boolean isFinished;
    private final InterpreterThread interpreter;

    /**
     * Constructor.
     *
     * @param controller
     * @param interpreter
     */
    public StackView(Controller controller, InterpreterThread interpreter) {
        super(controller);
        this.interpreter = interpreter;

        this.interpreter.addScopeListener(new ScopeListener() {

            @Override
            public void scopePopped(Scope scope, CodeBlock block) {
            }

            @Override
            public void scopePushed(Scope scope, CodeBlock block) {
            }

            @Override
            public void variableSet(Scope scope, String name, InterpreterValue value) {
            }
        });

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        isFinished = false;

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                if (isFinished) {
                    dispose();
                }
            }

            @Override
            public void windowClosed(WindowEvent we) {
            }

            @Override
            public void windowIconified(WindowEvent we) {
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
            }

            @Override
            public void windowActivated(WindowEvent we) {
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    @Override
    public void started() {
        this.setVisible(true);
    }

    @Override
    public void finished() {
        isFinished = true;
    }

    @Override
    public void error(Exception e) {
    }

    @Override
    public void message(String msg) {
    }

    /**
     * This is where the bulk of the code will go.
     *
     * @param block
     * @param scope
     */
    @Override
    public void currStatement(CodeBlock block, Scope scope) {
    }
}
