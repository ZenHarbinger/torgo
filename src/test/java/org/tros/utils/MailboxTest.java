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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matta
 */
public class MailboxTest {
    
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
        System.out.println("size");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
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
        System.out.println("halt");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        ints.halt();
    }

    /**
     * Test of addMessage method, of class Mailbox.
     */
    @Test
    public void testAddMessage() {
        System.out.println("addMessage");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
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
        System.out.println("getMessage");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        for(int ii = 0; ii < size; ii++) {
            assertEquals(ii, ints.getMessage().intValue());
        }

        ints.halt();
    }

    /**
     * Test of getMessages method, of class Mailbox.
     */
    @Test
    public void testGetMessages() {
        System.out.println("getMessages");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        ArrayList<Integer> messages = ints.getMessages();
        assertEquals(size, messages.size());

        ints.halt();
    }

    /**
     * Test of isHalted method, of class Mailbox.
     */
    @Test
    public void testIsHalted() {
        System.out.println("isHalted");
        Mailbox<Integer> ints = new Mailbox<Integer>();
        int size = 10;
        for(int ii = 0; ii < size; ii++) {
            ints.addMessage(ii);
        }
        assertEquals(size, ints.size());

        ArrayList<Integer> messages = ints.getMessages();
        assertEquals(size, messages.size());

        ints.halt();
        assertTrue(ints.isHalted());
    }
    
}
