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
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.InterpreterListener;
import org.tros.torgo.TorgoCanvas;
import org.tros.torgo.Scope;
import org.tros.utils.IHaltMonitor;

/**
 * Base component of Logo. This is a grouping of commands to run. A LogoBlock
 * can contain more LogoBlocks.
 *
 * @author matta
 */
public class LogoBlock {

    public enum ProcessResult {

        SUCCESS,
        HALT,
        RETURN
    }

    protected final ParserRuleContext ctx;
    private final ArrayList<LogoBlock> commands = new ArrayList<>();
    private final HashMap<String, LogoFunction> functions = new HashMap<>();
    private IHaltMonitor monitor;
    protected final ArrayList<InterpreterListener> listeners = new ArrayList<>();

    public void addInterpreterListener(InterpreterListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeInterpreterListener(InterpreterListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Set the halt monitor. This is currently only used on the First element in
     * the stack so it does not need to be passed to every LogoBlock instance.
     *
     * @param monitor
     */
    protected void setHaltMonitor(IHaltMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * Get the halt monitor.
     *
     * @return
     */
    protected IHaltMonitor haltMonitor() {
        return this.monitor;
    }

    /**
     * Constructor
     *
     * @param ctx
     */
    public LogoBlock(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Add a command to the list.
     *
     * @param command
     */
    public final void addCommand(LogoBlock command) {
        if (!commands.contains(command)) {
            commands.add(command);
        }
    }

    /**
     * Add a collection of commands to the list.
     *
     * @param commands
     */
    public final void addCommand(Collection<LogoBlock> commands) {
        this.commands.addAll(commands);
    }

    /**
     * Get the commands to interpret.
     *
     * @return
     */
    public final LogoBlock[] getCommands() {
        return commands.toArray(new LogoBlock[]{});
    }

    /**
     * Is the current block halted.
     *
     * @return true if halted, false if the monitor is null or the monitor is
     * not halted.
     */
    public boolean isHalted() {
        return monitor != null ? monitor.isHalted() : false;
    }

    /**
     * Process the statement(s)
     *
     * @param scope
     * @param canvas
     * @param currentContext
     * @param stack
     * @return true if we should continue, false otherwise
     */
    public ProcessResult process(Scope scope, TorgoCanvas canvas, ParserRuleContext currentContext, Stack<LogoBlock> stack) {
        AtomicBoolean success = new AtomicBoolean(true);
        AtomicBoolean stop = new AtomicBoolean(false);
        stack.push(this);
        commands.stream().forEach((lc) -> {
            if (success.get() && !stop.get()) {
                ProcessResult pr = lc.process(scope, canvas, ctx, stack);
                if (pr == ProcessResult.HALT) {
                    success.set(false);
                } else if (pr == ProcessResult.RETURN) {
                    stop.set(true);
                }
            }
        });
        stack.pop();
        return success.get() ? (stop.get() ? ProcessResult.RETURN : ProcessResult.SUCCESS) : ProcessResult.HALT;
    }

    /**
     * Debugging use only.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        commands.stream().forEach((lc) -> {
            sb.append(lc.toString());
        });
        return sb.toString();
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @param callstack
     * @return
     */
    protected LogoFunction getFunction(String name, Stack<LogoBlock> callstack) {
        LogoFunction ret = null;
        if (!hasFunction(name)) {
            for (LogoBlock lb : callstack) {
                if (lb.hasFunction(name)) {
                    ret = lb.getFunction(name);
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @return
     */
    protected boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    /**
     * Look for a function with the specified name.
     *
     * @param name
     * @return
     */
    protected LogoFunction getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Add a function to the current object. This allows for local declaration
     * of functions.
     *
     * @param function
     */
    protected void addFunction(LogoFunction function) {
        functions.put(function.getFunctionName(), function);
    }
}
