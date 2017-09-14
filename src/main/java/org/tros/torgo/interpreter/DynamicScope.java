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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
     * @param block the block to push.
     */
    @Override
    public void push(CodeBlock block) {
        scope.add(0, new HashMap<>());
        stack.add(0, block);
        firePushed(block);
    }

    /**
     * Pop back to the previous scope level.
     *
     * @return the popped block.
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
     * @param name the value to get.
     * @return the value.
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

        if (ret == InterpreterValue.NULL) {
            return super.get(name);
        }
        return ret;
    }

    /**
     * Set the value of a variable at a current scoping level.
     *
     * @param name the name to set.
     * @param value the value to set.
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
     * Defines a new variable at the top level of the scope. Once the current
     * level of the scope is popped off, it will no longer be available.
     *
     * @param name the name to set.
     * @param value the value to set.
     */
    @Override
    public void setNew(String name, InterpreterValue value) {
        scope.get(0).put(name, value);
        fireVariableSet(name, value);
    }

    /**
     * Get a function in the scope.
     *
     * @param name the name of the function.
     * @return the function.
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
     * Get the names of variables.
     *
     * @return a collection of all variables.
     */
    @Override
    public Collection<String> variables() {
        HashSet<String> keys = new HashSet<>();
        scope.forEach((slice) -> {
            keys.addAll(slice.keySet());
        });
        return keys;
    }

    /**
     * Get the names of variables.
     *
     * @param value the depth of the stack to check.
     * @return a map of all variables at that depth.
     */
    @Override
    public Map<String, InterpreterValue> variablesPeek(int value) {
        HashMap<String, InterpreterValue> keys = new HashMap<>();
        for (int ii = scope.size() - 1; ii >= 0 && value >= 0; ii--, value--) {
            HashMap<String, InterpreterValue> slice = scope.get(ii);
            slice.keySet().forEach((key) -> {
                keys.put(key, slice.get(key));
            });//            keys.addAll(slice.keySet());
        }
        return keys;
    }
}
