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

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class InterpreterThreadTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(InterpreterThreadTest.class.getName());
    }

    public InterpreterThreadTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private static class ScopeListenerImpl implements ScopeListener {

        @Override
        public void scopePopped(Scope scope, CodeBlock block) {
        }

        @Override
        public void scopePushed(Scope scope, CodeBlock block) {
        }

        @Override
        public void variableSet(Scope scope, String name, InterpreterValue value) {
        }

    }

    private static class InterpreterListenerImpl implements InterpreterListener {

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
        public void currStatement(CodeBlock block, Scope scope) {
        }

    }

    /**
     * Test of isHalted method, of class InterpreterThread.
     */
    @Test
    public void testProcessException() {
        System.out.println("processException");
        InterpreterThread instance = new InterpreterThreadImpl(new DynamicScope());
        try {
            Object f = null;
            f.toString();
        } catch (Exception ex) {
            instance.processException(ex);
        }
    }

    /**
     * Test of isHalted method, of class InterpreterThread.
     */
    @Test
    public void testIsHalted() {
        System.out.println("isHalted");
        InterpreterThread instance = new InterpreterThreadImpl(new DynamicScope());
        assertFalse(instance.isHalted());
        instance.halt();
        assertTrue(instance.isHalted());
    }

    /**
     * Test of addInterpreterListener method, of class InterpreterThread.
     */
    @Test
    public void testAddInterpreterListener() {
        System.out.println("addInterpreterListener");
        InterpreterListener listener = new InterpreterListenerImpl();
        InterpreterThread instance = new InterpreterThreadImpl(new DynamicScope());
        instance.addInterpreterListener(listener);
        instance.removeInterpreterListener(listener);
        instance = new InterpreterThreadImpl(new LexicalScope());
        instance.addInterpreterListener(listener);
        instance.removeInterpreterListener(listener);
    }

    /**
     * Test of addScopeListener method, of class InterpreterThread.
     */
    @Test
    public void testAddScopeListener() {
        System.out.println("addScopeListener");
        ScopeListener listener = new ScopeListenerImpl();
        InterpreterThread instance = new InterpreterThreadImpl(new DynamicScope());
        instance.addScopeListener(listener);
        instance.removeScopeListener(listener);
        instance = new InterpreterThreadImpl(new LexicalScope());
        instance.addScopeListener(listener);
        instance.removeScopeListener(listener);
    }

    public class InterpreterThreadImpl extends InterpreterThread {

        public InterpreterThreadImpl(Scope scope) {
            super("", scope);
        }

        @Override
        public LexicalAnalyzer getLexicalAnalysis(String source) {
            return null;
        }

        @Override
        public void process(CodeBlock entryPoint) {
        }
    }

}
