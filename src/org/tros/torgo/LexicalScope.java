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

/**
 *
 * @author matta
 */
public class LexicalScope implements Scope {

    private final ArrayList<CodeBlock> stack = new ArrayList<>();

    @Override
    public double get(String name) {
        CodeBlock p = stack.get(0);
        while(p != null) {
            if (p.hasVariable(name)) {
                return p.getVariable(name);
            }
            p = p.getParent();
        }
        return 0;
    }

    @Override
    public boolean has(String name) {
        CodeBlock p = stack.get(0);
        while(p != null) {
            if (p.hasVariable(name)) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    @Override
    public void pop() {
        stack.remove(0);
    }

    @Override
    public void push(CodeBlock block) {
        stack.add(0, block);
    }

    @Override
    public void set(String name, double value) {
        CodeBlock p = stack.get(0);
        while(p != null) {
            if (p.hasVariable(name)) {
                p.setVariable(name, value);
                break;
            }
            p = p.getParent();
        }
        if (p == null) {
            stack.get(0).setVariable(name, value);
        }
    }

    @Override
    public void setNew(String name, double value) {
        stack.get(0).setVariable(name, value);
    }

    @Override
    public CodeFunction getFunction(String name) {
        CodeBlock cb = stack.get(0);
        while(cb != null) {
            cb = cb.getParent();
            if (cb.hasFunction(name)) {
                return cb.getFunction(name);
            }
        }
        return null;
    }

    @Override
    public boolean hasFunction(String name) {
        CodeBlock cb = stack.get(0);
        while(cb != null) {
            cb = cb.getParent();
            if (cb.hasFunction(name)) {
                return true;
            }
        }
        return false;
    }

}
