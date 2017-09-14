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
     * @param listener a listener to add.
     */
    void addScopeListener(ScopeListener listener);

    /**
     * Remove a listener for scope events.
     *
     * @param listener a listener to remove.
     */
    void removeScopeListener(ScopeListener listener);

    /**
     * Get the value of a specified variable by name.
     *
     * @param name a value to get.
     * @return the value.
     */
    InterpreterValue get(String name);

    /**
     * Pop a code block off of the scope.
     *
     * @return the popped block.
     */
    CodeBlock pop();

    /**
     * Push a code block onto the scope.
     *
     * @param block the block to push.
     */
    void push(CodeBlock block);

    /**
     * Set a name value pair in the scope.
     *
     * @param name the name.
     * @param value the value.
     */
    void set(String name, InterpreterValue value);

    /**
     * Set a name value pair in the scope.
     *
     * @param name the name.
     * @param value the value.
     */
    void setGlobal(String name, InterpreterValue value);

    /**
     * Set a name value pair in the scope at the top level.
     *
     * @param name the name.
     * @param value the value.
     */
    void setNew(String name, InterpreterValue value);

    /**
     * Get a function that is in the scope.
     *
     * @param name the name.
     * @return the function.
     */
    CodeFunction getFunction(String name);

    /**
     * Get the names of variables.
     *
     * @return the names of variables.
     */
    Collection<String> variables();

    /**
     * Get the names of variables.
     *
     * @param val the depth of the scope to peek at.
     * @return a map of all variables available at that depth.
     */
    Map<String, InterpreterValue> variablesPeek(int val);
}
