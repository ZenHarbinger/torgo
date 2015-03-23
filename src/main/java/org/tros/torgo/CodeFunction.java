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
import java.util.Map;

/**
 * Function representation of a CodeBlock
 *
 * @author matta
 */
public interface CodeFunction extends CodeBlock {

    /**
     * The name of the function.
     *
     * @return
     */
    String getFunctionName();

    @Override
    default ReturnValue process(Scope scope) {
        return process(scope, new HashMap<>());
    }

    ReturnValue process(Scope scope, Map<String, InterpreterValue> params);
}
