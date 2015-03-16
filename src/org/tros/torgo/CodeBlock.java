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

import java.util.Collection;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.utils.IHaltListener;

/**
 *
 * @author matta
 */
public interface CodeBlock extends InterpreterType, IHaltListener {

    /**
     * Add a command to the list.
     *
     * @param command
     */
    void addCommand(CodeBlock command);

    /**
     * Add a collection of commands to the list.
     *
     * @param commands
     */
    void addCommand(Collection<CodeBlock> commands);

    void addInterpreterListener(InterpreterListener listener);

    /**
     * Get the commands to interpret.
     *
     * @return
     */
    CodeBlock[] getCommands();

    /**
     * Is the current block halted.
     *
     * @return true if halted, false if the monitor is null or the monitor is
     * not halted.
     */
    boolean isHalted();

    /**
     * Process the statement(s)
     *
     * @param scope
     * @param canvas
     * @return true if we should continue, false otherwise
     */
    @Override
    ReturnValue.ProcessResult process(Scope scope, TorgoCanvas canvas);

    /**
     * Add listener to this object
     * @param listener 
     */
    void removeInterpreterListener(InterpreterListener listener);

    /**
     * Check to see if this code block defines a function.
     * @param name
     * @return 
     */
    boolean hasFunction(String name);

    /**
     * Get a function if it is defined.
     * @param name
     * @return 
     */
    CodeFunction getFunction(String name);

    /**
     * Add a function to this code block.
     * @param function 
     */
    void addFunction(CodeFunction function);

    /**
     * Get the local context of this ANTLR generated parse tree stub.
     * @return 
     */
    ParserRuleContext getParserRuleContext();
    
    boolean hasVariable(String name);
    
    void setVariable(String name, Double value);
    
    Double getVariable(String name);
    
    CodeBlock getParent();
}
