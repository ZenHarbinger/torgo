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

import org.tros.utils.HaltMonitor;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.tros.logo.antlr.logoLexer;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.DynamicScope;
import org.tros.torgo.InterpreterListener;
import org.tros.torgo.InterpreterThread;
import org.tros.torgo.TorgoCanvas;

/**
 * Logo processing thread.
 * @author matta
 */
public class AntlrThread extends Thread implements InterpreterThread {

    private final HaltMonitor monitor;
    private ParseTree tree;
    private LogoBlock script;
    private final String source;
    private final TorgoCanvas canvas;
    private final ArrayList<InterpreterListener> listeners = new ArrayList<>();

    /**
     * Constructor
     * @param source
     * @param canvas 
     */
    public AntlrThread(String source, TorgoCanvas canvas) {
        this.source = source;
        this.canvas = canvas;
        this.monitor = new HaltMonitor();
    }

    /**
     * Check to see if the thread is halted.
     * @return 
     */
    @Override
    public boolean isHalted() {
        return monitor.isHalted();
    }

    /**
     * Halt the thread.
     */
    @Override
    public void halt() {
        monitor.halt();
    }

    /**
     * Add a specified listener.
     * @param listener 
     */
    @Override
    public void addInterpreterListener(InterpreterListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a specified listener.
     * @param listener 
     */
    @Override
    public void removeInterpreterListener(InterpreterListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Threaded function.
     */
    @Override
    public void run() {
        listeners.stream().forEach((l) -> {
            l.started();
        });
        try {
            //lexical analysis and parsing with ANTLR
            logoLexer lexer = new logoLexer(new ANTLRInputStream(source));
            logoParser parser = new logoParser(new CommonTokenStream(lexer));
            //get the prog element from the parse tree
            //the prog element is the root element defined in the logo.g4 grammar.
            tree = parser.prog();
            //walk the parse tree and build the execution map
            script = CommandListener.lexicalAnalysis(tree);
            //set the halt monitor on the root element of the stack
            script.setHaltMonitor(monitor);
            script.addInterpreterListener(new InterpreterListener() {

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
            });
            //interpret the script
            script.process(new DynamicScope(), canvas, null, new Stack<>());
        } catch (Exception ex) {
            listeners.stream().forEach((l) -> {
                l.error(ex);
                Logger.getLogger(AntlrThread.class.getName()).log(Level.SEVERE, null, ex);
            });
            Logger.getLogger(AntlrThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeners.stream().forEach((l) -> {
            l.finished();
        });
    }

    @Override
    public void waitForTermination() {
        try {
            join();
        } catch (InterruptedException ex) {
            Logger.getLogger(AntlrThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
