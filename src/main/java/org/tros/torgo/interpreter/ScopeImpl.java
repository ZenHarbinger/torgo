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

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.event.EventListenerSupport;

/**
 * Basic scope implementation.
 *
 * @author matta
 */
abstract class ScopeImpl implements Scope {

    protected final HashMap<String, InterpreterValue> globals = new HashMap<>();
    protected final ArrayList<CodeBlock> stack = new ArrayList<>();
    private final EventListenerSupport<ScopeListener> listeners
            = EventListenerSupport.create(ScopeListener.class);

    /**
     * Look at the current code block.
     *
     * @return
     */
    @Override
    public CodeBlock peek() {
        return stack.get(0);
    }

    /**
     * Look at an inner code block.
     *
     * @param val
     * @return
     */
    @Override
    public CodeBlock peek(int val) {
        return stack.get(val);
    }

    /**
     * The size of the scope.
     *
     * @return
     */
    @Override
    public int size() {
        return stack.size();
    }

    /**
     * Add a scope listener.
     *
     * @param listener
     */
    @Override
    public void addScopeListener(ScopeListener listener) {
        listeners.addListener(listener);
    }

    /**
     * Remove a scope listener.
     *
     * @param listener
     */
    @Override
    public void removeScopeListener(ScopeListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Fire the scopePopped event.
     *
     * @param block
     */
    protected final void firePopped(CodeBlock block) {
        listeners.fire().scopePopped(this, block);
    }

    /**
     * Fire the scopePushed event.
     *
     * @param block
     */
    protected final void firePushed(CodeBlock block) {
        listeners.fire().scopePushed(this, block);
    }

    /**
     * Fire the variableSet event.
     *
     * @param block
     */
    protected final void fireVariableSet(String name, InterpreterValue value) {
        listeners.fire().variableSet(this, name, value);
    }

    /**
     * Set a global value.
     *
     * @param name
     * @param value
     */
    @Override
    public void setGlobal(String name, InterpreterValue value) {
        globals.put(name, value);
    }

    @Override
    public InterpreterValue get(String name) {
        if (this.globals.containsKey(name)) {
            return this.globals.get(name);
        }
        return InterpreterValue.NULL;
    }

}
