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

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
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
public class MailboxTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(MailboxTest.class.getName());
    }

    public MailboxTest() {
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
     * Test of size method, of class Mailbox.
     */
    @Test
    public void testSize() {
        LOGGER.info("size");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());
        ints.halt();
    }

    /**
     * Test of halt method, of class Mailbox.
     */
    @Test
    public void testHalt() {
        LOGGER.info("halt");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        ints.halt();
    }

    /**
     * Test of addMessage method, of class Mailbox.
     */
    @Test
    public void testAddMessage() {
        LOGGER.info("addMessage");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());
        ints.halt();
    }

    /**
     * Test of getMessage method, of class Mailbox.
     */
    @Test
    public void testGetMessage() {
        LOGGER.info("getMessage");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        for (int ii = 0; ii < size; ii++) {
            assertEquals(ii, ints.getMessage().intValue());
        }

        ints.halt();
    }

    /**
     * Test of getMessages method, of class Mailbox.
     */
    @Test
    public void testGetMessages() {
        LOGGER.info("getMessages");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        Collection<Integer> messages = ints.getMessages();
        assertEquals(size, messages.size());

        ints.halt();
    }

    /**
     * Test of isHalted method, of class Mailbox.
     */
    @Test
    public void testIsHalted() {
        LOGGER.info("isHalted");
        Mailbox<Integer> ints = new Mailbox<>();
        int size = 10;
        for (int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        Collection<Integer> messages = ints.getMessages();
        assertEquals(size, messages.size());

        ints.halt();
        ints.halt();
        assertTrue(ints.isHalted());

        ints.addMessage(4);

        messages = ints.getMessages();
        assertNull(messages);
        Integer message = ints.getMessage();
        assertNull(message);
    }

    @Test
    public void testThreadedGetMessages() {
        LOGGER.info("threaded getMessages()");
        Mailbox<Integer> ints = new Mailbox<>();
        AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean go = new AtomicBoolean(true);
        Thread write = new Thread(() -> {
            while (go.get()) {
                ints.addMessage(counter.incrementAndGet());
            }
        });
        Thread read = new Thread(() -> {
            while (go.get()) {
                ints.getMessages();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        read.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        write.start();
        try {
            Thread.sleep(1000);

            go.set(false);

            read.join();
            write.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testThreadedGetMessage() {
        LOGGER.info("threaded getMessage()");
        Mailbox<Integer> ints = new Mailbox<>();
        AtomicInteger counter = new AtomicInteger(0);
        AtomicBoolean go = new AtomicBoolean(true);
        Thread write = new Thread(() -> {
            while (go.get()) {
                ints.addMessage(counter.incrementAndGet());
            }
        });
        Thread read = new Thread(() -> {
            while (go.get()) {
                ints.getMessage();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        read.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        write.start();
        try {
            Thread.sleep(1000);

            go.set(false);

            read.join();
            write.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void haltTest() {
        LOGGER.info("threaded haltTest");
        final Mailbox<Integer> ints = new Mailbox<>();
        Thread read = new Thread(() -> {
            Integer message = ints.getMessage();
            assertNull(message);
        });
        read.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ints.halt();

        try {
            read.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        final Mailbox<Integer> ints2 = new Mailbox<>();
        read = new Thread(() -> {
            Collection<Integer> messages = ints2.getMessages();
            assertNull(messages);
        });
        read.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ints2.halt();

        try {
            read.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void haltTest2() {
        LOGGER.info("threaded haltTest2");
        final Mailbox<Integer> ints = new Mailbox<>();
        AtomicBoolean wa = new AtomicBoolean(false);
        Thread read = new Thread(() -> {
            Integer message = ints.getMessage();
            assertNull(message);
            wa.set(true);
        });
        read.start();
        read.interrupt();
        while (!wa.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        AtomicBoolean wa2 = new AtomicBoolean(false);
        Mailbox<Integer> ints2 = new Mailbox<>();
        Thread read2 = new Thread(() -> {
            Collection<Integer> messages = ints2.getMessages();
            assertNull(messages);
            wa2.set(true);
        });
        read2.start();
        read2.interrupt();
        while (!wa2.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailboxTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
