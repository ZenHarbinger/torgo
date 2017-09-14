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
package org.tros.utils.logging;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoToolkit;

/**
 *
 * @author matta
 */
public class LoggingTest {
    
    public LoggingTest() {
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
     * Test of initLogging method, of class Logging.
     */
    @Test
    public void testInitLogging_BuildInfo() {
        System.out.println("initLogging");
        Logging.initLogging(TorgoToolkit.getBuildInfo());
    }

    /**
     * Test of initLogging method, of class Logging.
     */
    @Test
    public void testInitLogging_BuildInfo_Class() {
        System.out.println("initLogging");
        Logging.initLogging(TorgoToolkit.getBuildInfo(), Logging.class);
    }

    /**
     * Test of getLogFactory method, of class Logging.
     */
    @Test
    public void testGetLogFactory() {
        System.out.println("getLogFactory");
        Logging.initLogging(TorgoToolkit.getBuildInfo(), Logging.class);
        LogFactory result = Logging.getLogFactory();
        assertNotNull(result);
    }
    
}
