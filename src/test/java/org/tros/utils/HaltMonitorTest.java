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
package org.tros.utils;

import java.util.concurrent.atomic.AtomicBoolean;
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
public class HaltMonitorTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(HaltMonitorTest.class.getName());
    }

    public HaltMonitorTest() {
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

    /**
     * Test of getName method, of class HaltMonitor.
     */
    @Test
    public void testGetName() {
        LOGGER.info("getName");
        HaltMonitor instance = new HaltMonitor();
        instance.setName("name");
        String result = instance.getName();
        assertEquals("name", result);
    }

    /**
     * Test of setName method, of class HaltMonitor.
     */
    @Test
    public void testSetName() {
        LOGGER.info("setName");
        HaltMonitor instance = new HaltMonitor();
        instance.setName("name");
        String result = instance.getName();
        assertEquals("name", result);
    }

    /**
     * Test of addHaltListener method, of class HaltMonitor.
     */
    @Test
    public void testAddHaltListener() {
        LOGGER.info("addHaltListener");
        final AtomicBoolean called = new AtomicBoolean(false);
        HaltListener listener = new HaltListener() {
            @Override
            public void halted(ImmutableHaltMonitor monitor) {
                called.set(true);
            }
        };
        HaltMonitor instance = new HaltMonitor();
        instance.addHaltListener(listener);
        instance.halt();
        assertTrue(called.get());
        assertTrue(instance.isHalted());
    }

    /**
     * Test of removeHaltListener method, of class HaltMonitor.
     */
    @Test
    public void testRemoveHaltListener() {
        LOGGER.info("removeHaltListener");
        final AtomicBoolean called = new AtomicBoolean(false);
        HaltListener listener = new HaltListener() {
            @Override
            public void halted(ImmutableHaltMonitor monitor) {
                called.set(true);
            }
        };
        HaltMonitor instance = new HaltMonitor();
        instance.addHaltListener(listener);
        instance.removeHaltListener(listener);
        instance.halt();
        assertFalse(called.get());
        assertTrue(instance.isHalted());

    }

}
