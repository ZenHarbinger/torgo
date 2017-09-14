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

/**
 *
 * @author matta
 */
public interface LexicalAnalyzer {

    /**
     * Code block representing the entry point to the interpreted script.
     *
     * @return the starting point for the interpreted code.
     */
    CodeBlock getEntryPoint();

    /**
     * A collection of all code blocks defined in the script.
     *
     * @return the collection of all blocks defined in the script.
     */
    Collection<CodeBlock> getCodeBlocks();
}
