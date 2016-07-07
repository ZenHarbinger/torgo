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
package org.tros.jvmbasic;

import org.tros.jvmbasic.antlr.jvmBasicBaseListener;

/**
 * Gets a list of commands to execute. This does not execute then, but instead
 * builds a tree of commands to run. Once this tree is built, it will be
 * interpreted.
 *
 * @author matta
 */
class LexcialListener extends jvmBasicBaseListener {

//    private final Stack<LogoBlock> stack = new Stack<>();
    /**
     * Hidden constructor, force use of "lexicalAnalysis" method.
     */
    private LexcialListener() {
    }

//    /**
//     * Walk the parse tree and build a command structure to interpret.
//     * @param tree
//     * @return 
//     */
//    public static LogoBlock lexicalAnalysis(ParseTree tree) {
//        CommandListener cl = new CommandListener();
//        ParseTreeWalker.DEFAULT.walk(cl, tree);
//        return cl.getLogoCommands();
//    }
}
