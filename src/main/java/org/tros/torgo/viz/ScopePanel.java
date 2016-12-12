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
package org.tros.torgo.viz;

import org.tros.torgo.interpreter.CodeBlock;

/**
 *
 * @author matta
 */
public class ScopePanel {

    final private CodeBlock cb;
    
    public ScopePanel(CodeBlock cb) {
        super();
        this.cb = cb;
    }

    @Override
    public String toString() {
        return cb.getClass().getName();
    }
    
}
