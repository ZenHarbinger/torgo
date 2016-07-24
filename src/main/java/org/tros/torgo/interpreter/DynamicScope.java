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
import java.util.Collection;
import java.util.HashMap;

/**
 * Implements a dynamic scope which just looks down the call stack for an
 * instance of the named variable.
 *
 * @author matta
 */
public class DynamicScope extends ScopeImpl implements Scope {

    private final ArrayList<HashMap<String, InterpreterValue>> scope = new ArrayList<>();

    /**
     * Constructor.
     */
    public DynamicScope() {
    }

    /**
     * Push a new level onto the scope.
     *
     * @param block
     */
    @Override
    public void push(CodeBlock block) {
        scope.add(0, new HashMap<String, InterpreterValue>());
        stack.add(0, block);
        firePushed(block);
    }

    /**
     * Pop back to the previous scope level.
     */
    @Override
    public CodeBlock pop() {
        //do not remove the last scope...
        scope.remove(0);
        CodeBlock ret = stack.remove(0);
        firePopped(ret);
        return ret;
    }

    /**
     * Get the value of a variable at the current scoping level.
     *
     * @param name
     * @return
     */
    @Override
    public InterpreterValue get(String name) {
        InterpreterValue ret = InterpreterValue.NULL;

        for (HashMap<String, InterpreterValue> map : scope) {
            if (map.containsKey(name)) {
                ret = map.get(name);
                break;
            }
        }

        return ret;
    }

    /**
     * Set the value of a variable at a current scoping level.
     *
     * @param name
     * @param value
     */
    @Override
    public void set(String name, InterpreterValue value) {
        boolean found = false;
        for (HashMap<String, InterpreterValue> scope1 : scope) {
            if (scope1.containsKey(name)) {
                scope1.put(name, value);
                found = true;
                break;
            }
        }
        if (!found) {
            scope.get(0).put(name, value);
        }
        fireVariableSet(name, value);
    }

    /**
     * Check to see if the variable exists at the current scoping level.
     *
     * @param name
     * @return
     */
    @Override
    public boolean has(String name) {
        boolean ret = false;

        for (HashMap<String, InterpreterValue> map : scope) {
            if (map.containsKey(name)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    /**
     * Defines a new variable at the top level of the scope. Once the current
     * level of the scope is popped off, it will no longer be available.
     *
     * @param name
     * @param value
     */
    @Override
    public void setNew(String name, InterpreterValue value) {
        scope.get(0).put(name, value);
        fireVariableSet(name, value);
    }

    /**
     * Get a function in the scope.
     *
     * @param name
     * @return
     */
    @Override
    public CodeFunction getFunction(String name) {
        for (CodeBlock cb : stack) {
            if (cb.hasFunction(name)) {
                return cb.getFunction(name);
            }
        }
        return null;
    }

    /**
     * Check for a function in the scope.
     *
     * @param name
     * @return
     */
    @Override
    public boolean hasFunction(String name) {
        return getFunction(name) != null;
    }

    /**
     * Get the names of local variables.
     *
     * @return
     */
    @Override
    public Collection<String> localVariables() {
        return scope.get(0).keySet();
    }
}
