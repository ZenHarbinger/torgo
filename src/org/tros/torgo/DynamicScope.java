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
package org.tros.torgo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements a dynamic scope which just looks down the call stack for
 * an instance of the named variable.
 * @author matta
 */
public class DynamicScope implements Scope {

    private final ArrayList<HashMap<String, Double>> scope = new ArrayList<>();
    private final ArrayList<CodeBlock> callStack = new ArrayList<>();

    /**
     * Constructor
     */
    public DynamicScope() {
        scope.add(new HashMap<>());
    }

    /**
     * Push a new level onto the scope.
     * @param block
     */
    @Override
    public void push(CodeBlock block) {
        scope.add(0, new HashMap<>());
        callStack.add(0, block);
    }

    /**
     * Pop back to the previous scope level.
     */
    @Override
    public void pop() {
        //do not remove the last scope...
        if (scope.size() > 1) {
            scope.remove(0);
            callStack.remove(0);
        }
    }

    /**
     * Get the value of a variable at the current scoping level.
     * @param name
     * @return 
     */
    @Override
    public double get(String name) {
        Double ret = null;

        for (HashMap<String, Double> map : scope) {
            if (map.containsKey(name)) {
                ret = map.get(name);
                break;
            }
        }

        if (ret == null) {
            try {
                ret = Double.parseDouble(name);
            } catch (Exception ex) {
                ret = 0.0;
            }
        }

        return ret;
    }

    /**
     * Set the value of a variable at a current scoping level.
     * @param name
     * @param value 
     */
    @Override
    public void set(String name, double value) {
        boolean found = false;
        for (HashMap<String, Double> scope1 : scope) {
            if (scope1.containsKey(name)) {
                scope1.put(name, value);
                found = true;
                break;
            }
        }
        if (!found) {
            scope.get(0).put(name, value);
        }
    }

    /**
     * Check to see if the variable exists at the current scoping level.
     * @param name
     * @return 
     */
    @Override
    public boolean has(String name) {
        boolean ret = false;

        for (HashMap<String, Double> map : scope) {
            if (map.containsKey(name)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    /**
     * Defines a new variable at the top level of the scope.
     * Once the current level of the scope is popped off, it will
     * no longer be available.
     * @param name
     * @param value 
     */
    @Override
    public void setNew(String name, double value) {
        scope.get(0).put(name, value);
    }

    @Override
    public CodeFunction getFunction(String name) {
        for(CodeBlock cb : callStack) {
            if (cb.hasFunction(name)) {
                return cb.getFunction(name);
            }
        }
        return null;
    }

    @Override
    public boolean hasFunction(String name) {
        return callStack.stream().anyMatch((cb) -> (cb.hasFunction(name)));
    }

    @Override
    public CodeBlock peek() {
        return callStack.get(0);
    }
    
    @Override
    public CodeBlock peek(int val) {
        return callStack.get(val);
    }
    
    @Override
    public int size() {
        return callStack.size();
    }
}
