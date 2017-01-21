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
 * Events for the scope.
 *
 * @author matta
 */
public interface ScopeListener {

    /**
     * Scope was popped.
     *
     * @param scope
     * @param block
     */
    void scopePopped(Scope scope, CodeBlock block);

    /**
     * Scope was pushed.
     *
     * @param scope
     * @param block
     */
    void scopePushed(Scope scope, CodeBlock block);

    /**
     * Variable was set.
     *
     * @param scope
     * @param name
     * @param value
     */
    void variableSet(Scope scope, String name, InterpreterValue value);
}
