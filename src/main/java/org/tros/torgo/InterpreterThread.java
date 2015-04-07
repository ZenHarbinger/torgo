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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.tros.utils.HaltMonitor;
import org.tros.utils.logging.LogConsole;

/**
 * Interpreter Thread Interface
 *
 * @author matta
 */
public abstract class InterpreterThread extends Thread {

    private final HaltMonitor monitor;
    private CodeBlock script;
    private final String source;
    private final EventListenerSupport<InterpreterListener> listeners
            = EventListenerSupport.create(InterpreterListener.class);
    protected final Scope scope;

    /**
     * Constructor
     *
     * @param source
     * @param scope
     */
    public InterpreterThread(String source, Scope scope) {
        this.scope = scope;
        this.source = source;
        this.monitor = new HaltMonitor();
    }

    /**
     * Check to see if the thread is halted.
     *
     * @return
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
     * @param listener
     */
    public final void addInterpreterListener(InterpreterListener listener) {
        listeners.addListener(listener);
    }

    /**
     * Remove a specified listener.
     *
     * @param listener
     */
    public final void removeInterpreterListener(InterpreterListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Get the lexical analysis results from the source. Uses ANTLR to parse and
     * set up for interpreting.
     *
     * @param source
     * @return
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
                }

                @Override
                public void message(String msg) {
                }

                /**
                 * Pass on the currStatement event to any listeners.
                 *
                 * @param block
                 * @param scope
                 */
                @Override
                public void currStatement(CodeBlock block, Scope scope) {
                    listeners.fire().currStatement(block, scope);
                }
            };
            l.getCodeBlocks().stream().forEach((cb) -> {
                cb.addInterpreterListener(listener);
                monitor.addListener(cb);
            });
            //interpret the script
            process(script);
        } catch (Exception ex) {
            listeners.fire().error(ex);
            Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
            LogConsole.CONSOLE.setVisible(true);
        }
        listeners.fire().finished();
    }

    /**
     * Start the processing of the script.
     *
     * @param entryPoint
     */
    protected abstract void process(CodeBlock entryPoint);

    /**
     * Wait for the interpreter thread to terminate.
     */
    public final void waitForTermination() {
        try {
            join();
        } catch (InterruptedException ex) {
            Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void addScopeListener(ScopeListener listener) {
        scope.addScopeListener(listener);
    }

    public final void removeScopeListener(ScopeListener listener) {
        scope.removeScopeListener(listener);
    }
}
