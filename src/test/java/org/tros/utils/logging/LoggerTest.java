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

/**
 *
 * @author matta
 */
public class LoggerTest {
    
    public LoggerTest() {
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
     * Test of warn method, of class CommonsLogger.
     */
    @Test
    public void testWarn_String() {
        System.out.println("warn");
        String message = "warn";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.warn(message);
    }

    /**
     * Test of debug method, of class CommonsLogger.
     */
    @Test
    public void testDebug_String() {
        System.out.println("debug");
        String message = "debug";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.debug(message);
    }

    /**
     * Test of error method, of class CommonsLogger.
     */
    @Test
    public void testError_String() {
        System.out.println("error");
        String message = "error";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.error(message);
    }

    /**
     * Test of info method, of class CommonsLogger.
     */
    @Test
    public void testInfo_String() {
        System.out.println("info");
        String message = "info";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.info(message);
    }

    /**
     * Test of verbose method, of class CommonsLogger.
     */
    @Test
    public void testVerbose_String() {
        System.out.println("verbose");
        String message = "verbose";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.verbose(message);
    }

    /**
     * Test of warn method, of class CommonsLogger.
     */
    @Test
    public void testWarn_String_ObjectArr() {
        System.out.println("warn");
        String message = "warn";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.warn("Message: {0}", new Object[]{message});
    }

    /**
     * Test of debug method, of class CommonsLogger.
     */
    @Test
    public void testDebug_String_ObjectArr() {
        System.out.println("debug");
        String message = "debug";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.debug("Message: {0}", new Object[]{message});
    }

    /**
     * Test of error method, of class CommonsLogger.
     */
    @Test
    public void testError_String_ObjectArr() {
        System.out.println("error");
        String message = "error";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.error("Message: {0}", new Object[]{message});
    }

    /**
     * Test of info method, of class CommonsLogger.
     */
    @Test
    public void testInfo_String_ObjectArr() {
        System.out.println("info");
        String message = "info";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.info("Message: {0}", new Object[]{message});
    }

    /**
     * Test of verbose method, of class CommonsLogger.
     */
    @Test
    public void testVerbose_String_ObjectArr() {
        System.out.println("verbose");
        String message = "verbose";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.verbose("Message: {0}", new Object[]{message});
    }

    /**
     * Test of warn method, of class CommonsLogger.
     */
    @Test
    public void testWarn_String_Throwable() {
        System.out.println("warn");
        String message = "warn";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.warn("Message: {0}", new Object[]{message});
    }

    /**
     * Test of debug method, of class CommonsLogger.
     */
    @Test
    public void testDebug_String_Throwable() {
        System.out.println("debug");
        String message = "debug";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.debug("Message: {0}", new Throwable(message));
    }

    /**
     * Test of error method, of class CommonsLogger.
     */
    @Test
    public void testError_String_Throwable() {
        System.out.println("error");
        String message = "error";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.error("Message: {0}", new Throwable(message));
    }

    /**
     * Test of info method, of class CommonsLogger.
     */
    @Test
    public void testInfo_String_Throwable() {
        System.out.println("info");
        String message = "info";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.info("Message: {0}", new Throwable(message));
    }

    /**
     * Test of verbose method, of class CommonsLogger.
     */
    @Test
    public void testVerbose_String_Throwable() {
        System.out.println("verbose");
        String message = "verbose";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.verbose("Message: {0}", new Throwable(message));
    }

    /**
     * Test of fatal method, of class CommonsLogger.
     */
    @Test
    public void testFatal_String() {
        System.out.println("fatal");
        String message = "fatal";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.fatal(message);
    }

    /**
     * Test of fatal method, of class CommonsLogger.
     */
    @Test
    public void testFatal_String_ObjectArr() {
        System.out.println("fatal");
        String message = "fatal";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.fatal("Message: {0}", new Object[]{message});
    }

    /**
     * Test of fatal method, of class CommonsLogger.
     */
    @Test
    public void testFatal_String_Throwable() {
        System.out.println("fatal");
        String message = "fatal";
        Logger instance = Logging.getLogFactory().getLogger(LoggerTest.class);
        instance.fatal("Message: {0}", new Throwable(message));
    }
    
}
