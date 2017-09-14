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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
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
public class ManualResetEventTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(ManualResetEventTest.class.getName());
    }

    public ManualResetEventTest() {
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
     * Test of waitOne method, of class ManualResetEvent.
     */
    @Test
    public void testNoBlock() {
        LOGGER.info("noBlock");
        final ManualResetEvent instance = new ManualResetEvent(true);
        assertTrue(instance.isOpen());

        final ArrayList<Thread> threads = new ArrayList<>();
        final AtomicInteger counter = new AtomicInteger(0);

        for (int ii = 0; ii < 10; ii++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        assertTrue(instance.waitOne(1));
                        counter.incrementAndGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            t.start();
            threads.add(t);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(threads.size(), counter.get());

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of waitOne method, of class ManualResetEvent.
     */
    @Test
    public void testWaitOne_0args() {
        LOGGER.info("waitOne");
        final ManualResetEvent instance = new ManualResetEvent(true);
        assertTrue(instance.isOpen());
        instance.reset();
        assertFalse(instance.isOpen());

        final ArrayList<Thread> threads = new ArrayList<>();
        final AtomicInteger counter = new AtomicInteger(0);

        for (int ii = 0; ii < 10; ii++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        instance.waitOne();
                        counter.incrementAndGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            t.start();
            threads.add(t);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.set();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(threads.size(), counter.get());

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of waitOne method, of class ManualResetEvent.
     */
    @Test
    public void testWaitOne_long() {
        LOGGER.info("waitOne");
        final ManualResetEvent instance = new ManualResetEvent(true);
        assertTrue(instance.isOpen());
        instance.reset();
        assertFalse(instance.isOpen());

        final ArrayList<Thread> threads = new ArrayList<>();

        for (int ii = 0; ii < 10; ii++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        assertFalse(instance.waitOne(1));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            t.start();
            threads.add(t);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ManualResetEventTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
