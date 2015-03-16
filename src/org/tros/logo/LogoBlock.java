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
package org.tros.logo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.CodeBlock;
import org.tros.torgo.CodeFunction;
import org.tros.torgo.InterpreterListener;
import org.tros.torgo.InterpreterType.Type;
import org.tros.torgo.InterpreterValue;
import org.tros.torgo.ReturnValue;
import org.tros.torgo.ReturnValue.ProcessResult;
import org.tros.torgo.Scope;
import org.tros.torgo.TorgoCanvas;
import org.tros.utils.IHaltMonitor;

/**
 * Base component of Logo. This is a grouping of commands to run. A LogoBlock
 * can contain more LogoBlocks.
 *
 * @author matta
 */
class LogoBlock implements CodeBlock {

    protected final ParserRuleContext ctx;
    private final ArrayList<CodeBlock> commands = new ArrayList<>();
    private final HashMap<String, CodeFunction> functions = new HashMap<>();
    protected final ArrayList<InterpreterListener> listeners = new ArrayList<>();
    private final AtomicBoolean halted = new AtomicBoolean(false);

    private final HashMap<String, InterpreterValue> variables = new HashMap<>();
    private CodeBlock parent;

    @Override
    public void addInterpreterListener(InterpreterListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeInterpreterListener(InterpreterListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Constructor
     *
     * @param ctx
     */
    protected LogoBlock(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Add a command to the list.
     *
     * @param command
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
     * @param commands
     */
    @Override
    public final void addCommand(Collection<CodeBlock> commands) {
        this.commands.addAll(commands);
    }

    /**
     * Get the commands to interpret.
     *
     * @return
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
     * Process the statement(s)
     *
     * @param scope
     * @param canvas
     * @return true if we should continue, false otherwise
     */
    @Override
    public ReturnValue process(Scope scope, TorgoCanvas canvas) {
        AtomicBoolean success = new AtomicBoolean(true);
        AtomicBoolean stop = new AtomicBoolean(false);
        scope.push(this);
        commands.stream().forEach((lc) -> {
            if (success.get() && !stop.get()) {
                ReturnValue pr = lc.process(scope, canvas);
                if (pr.getResult() == ReturnValue.ProcessResult.HALT) {
                    success.set(false);
                } else if (pr.getResult() == ProcessResult.RETURN) {
                    stop.set(true);
                }
            }
        });
        scope.pop();
        ReturnValue.ProcessResult res = success.get() ? (stop.get() ? ReturnValue.ProcessResult.RETURN : ReturnValue.ProcessResult.SUCCESS) : ReturnValue.ProcessResult.HALT;
        return new ReturnValue(Type.NULL, null, res);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @param scope
     * @return
     */
    public CodeBlock getFunction(String name, Scope scope) {
        return scope.getFunction(name);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @return
     */
    @Override
    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @return
     */
    @Override
    public CodeFunction getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Add a function to the current object. This allows for local declaration
     * of functions.
     *
     * @param function
     */
    @Override
    public void addFunction(CodeFunction function) {
        functions.put(function.getFunctionName(), function);
    }

    @Override
    public void halted(IHaltMonitor monitor) {
        halted.set(monitor.isHalted());
    }

    @Override
    public ParserRuleContext getParserRuleContext() {
        return ctx;
    }

    @Override
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    @Override
    public void setVariable(String name, InterpreterValue value) {
        variables.put(name, value);
    }

    @Override
    public InterpreterValue getVariable(String name) {
        return variables.get(name);
    }

    @Override
    public CodeBlock getParent() {
        return parent;
    }

    protected void setParent(CodeBlock value) {
        this.parent = value;
    }

    @Override
    public Type getType() {
        return Type.COMMAND;
    }
}
