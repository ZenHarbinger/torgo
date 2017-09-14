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
package org.tros.logo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.CodeFunction;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.ReturnValue;
import org.tros.torgo.interpreter.ReturnValue.ProcessResult;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.types.NullType;
import org.tros.utils.ImmutableHaltMonitor;

/**
 * Base component of Logo. This is a grouping of commands to run. A LogoBlock
 * can contain more LogoBlocks.
 *
 * @author matta
 */
abstract class LogoBlock implements CodeBlock {

    protected final ParserRuleContext ctx;
    protected final EventListenerSupport<InterpreterListener> listeners
            = EventListenerSupport.create(InterpreterListener.class);
    protected final ArrayList<HashMap<String, InterpreterValue>> variables = new ArrayList<>();

    private final ArrayList<CodeBlock> commands = new ArrayList<>();
    private final HashMap<String, CodeFunction> functions = new HashMap<>();
    private final AtomicBoolean halted = new AtomicBoolean(false);
    private CodeBlock parent;

    /**
     * Constructor.
     *
     * @param ctx the parser context from antlr.
     */
    protected LogoBlock(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void addInterpreterListener(InterpreterListener listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeInterpreterListener(InterpreterListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Add a command to the list.
     *
     * @param command the command to add to the list.
     */
    @Override
    public final void addCommand(CodeBlock command) {
        if (!commands.contains(command)) {
            commands.add(command);
        }
    }

    /**
     * Add a collection of commands to the list.
     *
     * @param commands the commands to add to the list.
     */
    @Override
    public final void addCommand(Collection<CodeBlock> commands) {
        this.commands.addAll(commands);
    }

    /**
     * Get the commands to interpret.
     *
     * @return the commands at the current block.
     */
    @Override
    public final CodeBlock[] getCommands() {
        return commands.toArray(new CodeBlock[]{});
    }

    /**
     * Is the current block halted.
     *
     * @return true if halted, false if the monitor is null or the monitor is
     * not halted.
     */
    @Override
    public boolean isHalted() {
        return halted.get();
    }

    /**
     * Process the statement(s).
     *
     * @param scope the current scope of the program.
     * @return true if we should continue, false otherwise
     */
    @Override
    public ReturnValue process(Scope scope) {
        AtomicBoolean success = new AtomicBoolean(true);
        AtomicBoolean stop = new AtomicBoolean(false);

        commands.stream().filter((lc) -> (success.get() && !stop.get())).map((lc) -> lc.process(scope)).forEachOrdered((pr) -> {
            if (pr.getResult() == ReturnValue.ProcessResult.HALT) {
                success.set(false);
            } else if (pr.getResult() == ProcessResult.RETURN) {
                stop.set(true);
            }
        });

        ReturnValue.ProcessResult res = success.get() ? (stop.get() ? ReturnValue.ProcessResult.RETURN : ReturnValue.ProcessResult.SUCCESS) : ReturnValue.ProcessResult.HALT;
        return new ReturnValue(NullType.INSTANCE, null, res);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name the name of the function.
     * @param scope the current scope of the program.
     * @return a function from the scope.
     */
    public CodeFunction getFunction(String name, Scope scope) {
        return scope.getFunction(name);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name the name of the function.
     * @return a function from the block.
     */
    @Override
    public CodeFunction getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name the name of the function.
     * @return if the block has the function.
     */
    @Override
    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    /**
     * Add a function to the current object. This allows for local declaration
     * of functions.
     *
     * @param function the function to add.
     */
    @Override
    public void addFunction(CodeFunction function) {
        functions.put(function.getFunctionName(), function);
    }

    /**
     * Called when the halt monitor is halted.
     *
     * @param monitor the monitor to check for a halted status.
     */
    @Override
    public void halted(ImmutableHaltMonitor monitor) {
        halted.set(monitor.isHalted());
    }

    @Override
    public ParserRuleContext getParserRuleContext() {
        return ctx;
    }

    /**
     * Check for a variable in the block.
     *
     * @param name the name of a variable to check for.
     * @return if the block has the variable.
     */
    @Override
    public boolean hasVariable(String name) {
        return variables.stream().anyMatch((item) -> (item.containsKey(name)));
    }

    /**
     * Set a variable in the block.
     *
     * @param name the name of the variable.
     * @param value the value of the variable
     */
    @Override
    public void setVariable(String name, InterpreterValue value) {
        variables.get(0).put(name, value);
    }

    /**
     * Get the value of a variable in the block.
     *
     * @param name the name of the variable.
     * @return the value of the variable.
     */
    @Override
    public InterpreterValue getVariable(String name) {
        for (int ii = 0; ii <= variables.size(); ii++) {
            HashMap<String, InterpreterValue> item = variables.get(ii);
            if (item.containsKey(name)) {
                return item.get(name);
            }
        }
        return InterpreterValue.NULL;
    }

    /**
     * Get the lexical parent.
     *
     * @return the lexical parent block.
     */
    @Override
    public CodeBlock getParent() {
        return parent;
    }

    /**
     * Set the lexical parent.
     *
     * @param value the lexical parent block.
     */
    protected void setParent(CodeBlock value) {
        this.parent = value;
    }

    /**
     * Get the names of local variables.
     *
     * @return a collection of variable names.
     */
    @Override
    public Collection<String> localVariables() {
        if (variables.isEmpty()) {
            return new ArrayList<>();
        }
        return variables.get(0).keySet();
    }

    /**
     * Get the list of commands.
     *
     * @return the list of commands.
     */
    public List<CodeBlock> getCommandsList() {
        return commands;
    }
}
