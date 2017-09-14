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
package org.tros.utils.logging;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.swing.TorgoToolBarTest;
import java.util.logging.LogRecord;
import java.util.logging.Level;
import java.lang.reflect.Method;
import org.tros.torgo.TorgoToolkit;
/**
 *
 * @author Samuel Washburn
 */
public class SwingComponentHandlerTest {
    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(TorgoToolBarTest.class.getName());
    }

    public SwingComponentHandlerTest() {
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
    public void testGetLimit() {
        SwingComponentHandler handler = new SwingComponentHandler();
        handler.setLimit(10);
        assertEquals(handler.getLimit(), 10);
    }
    
    @Test
    public void testPaused() {
        SwingComponentHandler handler = new SwingComponentHandler();
        assertFalse(handler.isPaused());
        handler.pause();
        assertTrue(handler.isPaused());
        handler.pause();
        assertFalse(handler.isPaused());
    }

    @Test
    public void testFlush() {
        SwingComponentHandler handler = new SwingComponentHandler();
        handler.flush();
    }
    
    @Test
    public void testPublish() {
        SwingComponentHandler handler = new SwingComponentHandler();
        LogRecord record = new LogRecord(handler.getLevel(), "test");
        LogRecord record2 = new LogRecord(Level.FINEST, "test2");
        handler.publish(record);
        assertTrue(handler.getRecords().contains(record));
        handler.publish(record2);
        assertFalse(handler.getRecords().contains(record2));
    }

    @Test
    public void testTimer() throws Exception {
        SwingComponentHandler handler = new SwingComponentHandler();
        Class c = handler.getClass();
        Method method = c.getDeclaredMethod("timer", (Class[]) null);
        method.setAccessible(true);
        method.invoke(handler, (Object[]) null);
        handler.pause();
        assertTrue(handler.isPaused());
        method.invoke(handler, (Object[]) null);
    }
    
    @Test
    public void testPrivateMethods() {
        SwingComponentHandler handler = new SwingComponentHandler();
        handler.testConfigure(1);
        assertFalse(handler.checkTesting());
        handler.testConfigure(2);
        assertFalse(handler.checkTesting());
        handler.testConfigure(3);
        assertFalse(handler.checkTesting());
        handler.testConfigure(4);
        assertFalse(handler.checkTesting());
        handler.testConfigure(5);
        assertFalse(handler.checkTesting());
    }
}
