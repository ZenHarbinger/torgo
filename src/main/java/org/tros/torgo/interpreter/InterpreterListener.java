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
package org.tros.torgo.interpreter;

/**
 * Listener for interpreter events.
 *
 * @author matta
 */
public interface InterpreterListener {

    /**
     * Signal that the interpreter has started.
     */
    void started();

    /**
     * Signal that the interpreter has finished.
     */
    void finished();

    /**
     * Signal that there was an error with the interpreter.
     *
     * @param e the error to handle.
     */
    void error(Exception e);

    /**
     * Signal a message from the interpreter. This could be a print statement.
     *
     * @param msg the message to print.
     */
    void message(String msg);

    /**
     * The current block of code executing with the current state.
     *
     * @param block the block being processed.
     * @param scope the current scope of the program.
     */
    void currStatement(CodeBlock block, Scope scope);
}
