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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.tros.utils.HaltMonitor;

/**
 * Interpreter Thread Interface.
 *
 * @author matta
 */
public abstract class InterpreterThread extends Thread {

    protected final Scope scope;

    private final HaltMonitor monitor;
    private final String source;
    private final EventListenerSupport<InterpreterListener> listeners
            = EventListenerSupport.create(InterpreterListener.class);
    private CodeBlock script;

    /**
     * Constructor.
     *
     * @param source the source to interpret.
     * @param scope the scope to use.
     */
    public InterpreterThread(String source, Scope scope) {
        this.scope = scope;
        this.source = source;
        this.monitor = new HaltMonitor();
    }

    /**
     * Check to see if the thread is halted.
     *
     * @return if the thread is halted.
     */
    public final boolean isHalted() {
        return monitor.isHalted();
    }

    /**
     * Halt the thread.
     */
    public final void halt() {
        monitor.halt();
    }

    /**
     * Add a specified listener.
     *
     * @param listener the listener to add.
     */
    public final void addInterpreterListener(InterpreterListener listener) {
        listeners.addListener(listener);
    }

    /**
     * Remove a specified listener.
     *
     * @param listener the listener to remove.
     */
    public final void removeInterpreterListener(InterpreterListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Get the lexical analysis results from the source. Uses ANTLR to parse and
     * set up for interpreting.
     *
     * @param source the source to process.
     * @return a lexical analyzer.
     */
    protected abstract LexicalAnalyzer getLexicalAnalysis(String source);

    /**
     * Threaded function.
     */
    @Override
    public final void run() {
        listeners.fire().started();
        try {
            //walk the parse tree and build the execution map
            LexicalAnalyzer l = getLexicalAnalysis(source);

            script = l.getEntryPoint();
            InterpreterListener listener = new InterpreterListener() {

                @Override
                public void started() {
                }

                @Override
                public void finished() {
                }

                @Override
                public void error(Exception e) {
                    listeners.fire().error(e);
                }

                @Override
                public void message(String msg) {
                    listeners.fire().message(msg);
                }

                /**
                 * Pass on the currStatement event to any listeners.
                 *
                 * @param block the current block.
                 * @param scope the current scope of the program.
                 */
                @Override
                public void currStatement(CodeBlock block, Scope scope) {
                    listeners.fire().currStatement(block, scope);
                }
            };
            for (CodeBlock cb : l.getCodeBlocks()) {
                cb.addInterpreterListener(listener);
                monitor.addHaltListener(cb);
            }
            //interpret the script
            process(script);
        } catch (Exception ex) {
            processException(ex);
        }
        listeners.fire().finished();
    }

    /**
     * Process an exception during execution.
     *
     * @param ex the exception.
     */
    protected final void processException(Exception ex) {
        listeners.fire().error(ex);
        org.tros.utils.logging.Logging.getLogFactory().getLogger(InterpreterThread.class).fatal(null, ex);
        processExceptionHelper(ex);
    }

    /**
     * Allow derived classes to change/enhance this.
     *
     * @param ex the exception.
     */
    protected void processExceptionHelper(Exception ex) {
        try {
            Class<?> lc = Class.forName("org.tros.utils.logging.LogConsole");
            Field field = lc.getField("CONSOLE");
            java.lang.reflect.Method m = field.getType().getMethod("setVisible", boolean.class);
            Object fieldInstance = field.get(null);
            m.invoke(fieldInstance, true);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
        }
    }

    /**
     * Start the processing of the script.
     *
     * @param entryPoint the entry point to start at.
     */
    protected abstract void process(CodeBlock entryPoint);

    public final void addScopeListener(ScopeListener listener) {
        scope.addScopeListener(listener);
    }

    public final void removeScopeListener(ScopeListener listener) {
        scope.removeScopeListener(listener);
    }
}
