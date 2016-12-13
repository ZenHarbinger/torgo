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
package org.tros.torgo.interpreter;

import java.util.Collection;
import java.util.Map;

/**
 * Scoping interface. Not sure that this is sufficient to describe lexical
 * scoping, but works for dynamic and global for now.
 *
 * @author matta
 */
public interface Scope {

    /**
     * Add a listener for scope events.
     *
     * @param listener
     */
    void addScopeListener(ScopeListener listener);

    /**
     * Remove a listener for scope events.
     *
     * @param listener
     */
    void removeScopeListener(ScopeListener listener);

    /**
     * Get the value of a specified variable by name.
     *
     * @param name
     * @return
     */
    InterpreterValue get(String name);

    /**
     * Check to see if the specified variable exists in the current scope.
     *
     * @param name
     * @return
     */
    boolean has(String name);

    /**
     * Pop a code block off of the scope.
     *
     * @return
     */
    CodeBlock pop();

    /**
     * Push a code block onto the scope.
     *
     * @param block
     */
    void push(CodeBlock block);

    /**
     * Set a name value pair in the scope.
     *
     * @param name
     * @param value
     */
    void set(String name, InterpreterValue value);

    /**
     * Set a name value pair in the scope at the top level.
     *
     * @param name
     * @param value
     */
    void setNew(String name, InterpreterValue value);

    /**
     * Get a function that is in the scope.
     *
     * @param name
     * @return
     */
    CodeFunction getFunction(String name);

    /**
     * Check to see if a function exists in the scope.
     *
     * @param name
     * @return
     */
    boolean hasFunction(String name);

    /**
     * Peek at the top level of the scope.
     *
     * @return
     */
    CodeBlock peek();

    /**
     * Peek at an inner level of the scope.
     *
     * @param val
     * @return
     */
    CodeBlock peek(int val);

    /**
     * Get the size of the scope.
     *
     * @return
     */
    int size();

    /**
     * Get the names of local variables.
     *
     * @return
     */
    Collection<String> localVariables();

    /**
     * Get the names of variables.
     *
     * @return
     */
    Collection<String> variables();

    /**
     * Get the names of variables.
     *
     * @param val
     * @return
     */
    Map<String, InterpreterValue> variablesPeek(int val);
}
