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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tros.utils.HaltMonitor;
import org.tros.utils.logging.LogConsole;

/**
 * Interpreter Thread Interface
 * @author matta
 */
public abstract class InterpreterThread extends Thread {

    private final HaltMonitor monitor;
    private CodeBlock script;
    private final String source;
    private final ArrayList<InterpreterListener> listeners = new ArrayList<>();

    /**
     * Constructor
     *
     * @param source
     */
    public InterpreterThread(String source) {
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
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a specified listener.
     *
     * @param listener
     */
    public final void removeInterpreterListener(InterpreterListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }
    
    protected abstract LexicalAnalyzer getLexicalAnalysis(String source);

    /**
     * Threaded function.
     */
    @Override
    public final void run() {
        listeners.stream().forEach((l) -> {
            l.started();
        });
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

                @Override
                public void currStatement(String statement, int line, int start, int end) {
                    listeners.stream().forEach((l) -> {
                        l.currStatement(statement, line, start, end);
                    });
                }
            };
            l.getCodeBlocks().stream().forEach((cb) -> {
                cb.addInterpreterListener(listener);
                monitor.addListener(cb);
            });
            //interpret the script
            process(script);
        } catch (Exception ex) {
            listeners.stream().forEach((l) -> {
                l.error(ex);
                Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
            });
            Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
            LogConsole.CONSOLE.setVisible(true);
        }
        listeners.stream().forEach((l) -> {
            l.finished();
        });
    }

    protected abstract void process(CodeBlock entryPoint);

    public final void waitForTermination() {
        try {
            join();
        } catch (InterruptedException ex) {
            Logger.getLogger(InterpreterThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
