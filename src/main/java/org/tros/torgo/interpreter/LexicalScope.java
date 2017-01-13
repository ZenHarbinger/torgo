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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author matta
 */
public class LexicalScope extends ScopeImpl implements Scope {

    /**
     * Looks in the lexical stack for variables to use.
     *
     * @param name
     * @return
     */
    @Override
    public InterpreterValue get(String name) {
        CodeBlock p = stack.get(0);
        while (p != null) {
            if (p.hasVariable(name)) {
                return p.getVariable(name);
            }
            p = p.getParent();
        }
        return super.get(name);
    }

    /**
     * Pop the current code block.
     */
    @Override
    public CodeBlock pop() {
        CodeBlock ret = stack.remove(0);
        firePopped(ret);
        return ret;
    }

    /**
     * Push a new code block.
     *
     * @param block
     */
    @Override
    public void push(CodeBlock block) {
        stack.add(0, block);
        firePushed(block);
    }

    /**
     * Set the value of a variable in the scope.
     *
     * @param name
     * @param value
     */
    @Override
    public void set(String name, InterpreterValue value) {
        CodeBlock p = stack.get(0);
        while (p != null) {
            if (p.hasVariable(name)) {
                p.setVariable(name, value);
                break;
            }
            p = p.getParent();
        }
        if (p == null) {
            stack.get(0).setVariable(name, value);
        }
        fireVariableSet(name, value);
    }

    /**
     * Set the value of a variable in the current block.
     *
     * @param name
     * @param value
     */
    @Override
    public void setNew(String name, InterpreterValue value) {
        stack.get(0).setVariable(name, value);
        fireVariableSet(name, value);
    }

    /**
     * Get a function available in the scope.
     *
     * @param name
     * @return
     */
    @Override
    public CodeFunction getFunction(String name) {
        CodeBlock cb = stack.get(0);
        while (cb != null) {
            if (cb.hasFunction(name)) {
                return cb.getFunction(name);
            }
            cb = cb.getParent();
        }
        return null;
    }

    /**
     * Get the names of variables.
     *
     * @return
     */
    @Override
    public Collection<String> variables() {
        HashSet<String> keys = new HashSet<>();
        CodeBlock cb = stack.get(0);
        while (cb != null) {
            keys.addAll(cb.localVariables());
            cb = cb.getParent();
        }
        return keys;
    }

    /**
     * Get the names of variables.
     *
     * @param value
     * @return
     */
    @Override
    public Map<String, InterpreterValue> variablesPeek(int value) {
        HashMap<String, InterpreterValue> keys = new HashMap<>();
        CodeBlock cb = stack.get(stack.size() - value - 1);
        while (cb != null) {
            for (String v : cb.localVariables()) {
                keys.put(v, cb.getVariable(v));
            }
            cb = cb.getParent();
        }
        return keys;
    }

}
