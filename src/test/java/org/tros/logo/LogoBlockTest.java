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

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.utils.logging.Logging;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import java.util.ArrayList;
import org.tros.torgo.TorgoToolkit;

/**
 *
 * @author Samuel Washburn
 */
public class LogoBlockTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(LogoBlockTest.class.getName());
    }

    public LogoBlockTest() {
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
    
    @Test
    public void testRemoveInterpreterListener() {
        LogoFunction block = new LogoFunction("test", new ParserRuleContext());
        
        final AtomicBoolean started = new AtomicBoolean(false);
        final AtomicBoolean finished = new AtomicBoolean(false);
        InterpreterListener listener = new InterpreterListener() {
                @Override
                public void started() {
                    started.set(true);
                }

                @Override
                public void finished() {
                    finished.set(true);
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
            };
        block.addInterpreterListener(listener);
        block.removeInterpreterListener(listener);
    }
    
    @Test
    public void testAddCommand() {
        LogoFunction block1 = new LogoFunction("test1", new ParserRuleContext());
        LogoFunction block2 = new LogoFunction("test2", new ParserRuleContext());
        LogoFunction block3 = new LogoFunction("test3", new ParserRuleContext());
        assertFalse(block1.getCommandsList().contains(block2));
        block1.addCommand(block2);
        assertTrue(block1.getCommandsList().contains(block2));
        block1.addCommand(block2);
        
        ArrayList<CodeBlock> commands = new ArrayList<CodeBlock>();
        commands.add(block3);
        block1.addCommand(commands);
        
    }
    
    @Test
    public void testGetCommands() {
        LogoFunction block1 = new LogoFunction("test1", new ParserRuleContext());
        LogoFunction block2 = new LogoFunction("test2", new ParserRuleContext());
        
        ArrayList<CodeBlock> commands = new ArrayList<CodeBlock>();
        commands.add(block2);
        block1.addCommand(commands);
        assertNotNull(block1.getCommands());
    }
}
