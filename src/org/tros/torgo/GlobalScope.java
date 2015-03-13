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

import java.util.HashMap;

/**
 * Implements a global scope where all variables are left with their last value.
 * @author matta
 */
public class GlobalScope implements Scope {

    private final HashMap<String, Double> scope = new HashMap<>();

    /**
     * Constructor
     */
    public GlobalScope() {
    }

    /**
     * Does nothing
     */
    @Override
    public void push() {
    }

    /**
     * Does nothing
     */
    @Override
    public void pop() {
    }

    /**
     * Get the value.
     * @param name
     * @return 
     */
    @Override
    public double get(String name) {
        if (scope.containsKey(name)) {
            return scope.get(name);
        } else {
            try {
                return (double) Double.parseDouble(name);
            } catch (Exception ex) {

            }
        }
        return 0.0;
    }

    /**
     * Set the value.
     * @param name
     * @param value 
     */
    @Override
    public void set(String name, double value) {
        scope.put(name, value);
    }

    /**
     * Check to see if the variable exists.
     * @param name
     * @return 
     */
    @Override
    public boolean has(String name) {
        return scope.containsKey(name);
    }

    /**
     * Same as set()
     * @param name
     * @param value 
     */
    @Override
    public void setNew(String name, double value) {
        set(name, value);
    }
}
