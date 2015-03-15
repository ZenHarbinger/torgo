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

/**
 * Scoping interface.  Not sure that this is sufficient to describe lexical
 * scoping, but works for dynamic and global for now.
 * @author matta
 */
public interface Scope {

    double get(String name);

    boolean has(String name);

    void pop();

    void push(CodeBlock block);

    void set(String name, double value);

    void setNew(String name, double value);

    CodeFunction getFunction(String name);
    
    boolean hasFunction(String name);
}
