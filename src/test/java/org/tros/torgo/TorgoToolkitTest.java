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
package org.tros.torgo;

import java.util.Set;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.utils.ResourceAccessor;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class TorgoToolkitTest {

    private static Logger LOGGER;

    public TorgoToolkitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Logging.initLogging(TorgoInfo.INSTANCE);
        LOGGER = Logger.getLogger(TorgoToolkitTest.class.getName());
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
     * Test of hidden constructor.
     */
    @Test
    public void testConstructor() {
        LOGGER.info("constructor");
        TorgoToolkit tt = new TorgoToolkit();
        assertNotNull(tt);
    }

    /**
     * Test of getController method, of class TorgoToolkit.
     */
    @Test
    public void testGetController() {
        LOGGER.info("getController");
        Set<String> results = TorgoToolkit.getToolkits();
        for (String name : results) {
            Controller controller = TorgoToolkit.getController(name);
            assertNotNull(controller);
        }
    }

    /**
     * Test of getToolkits method, of class TorgoToolkit.
     */
    @Test
    public void testGetToolkits() {
        LOGGER.info("getToolkits");
        Set<String> result = TorgoToolkit.getToolkits();
        assertNotNull(result);
    }

    /**
     * Test of getVisualizers method, of class TorgoToolkit.
     */
    @Test
    public void testGetVisualizers() {
        LOGGER.info("getVisualizers");
        Set<String> result = TorgoToolkit.getVisualizers();
        assertNotNull(result);
    }

    /**
     * Test of getVisualization method, of class TorgoToolkit.
     */
    @Test
    public void testGetVisualization() {
        LOGGER.info("getVisualization");
        Set<String> viz = TorgoToolkit.getVisualizers();
        for (String name : viz) {
            InterpreterVisualization result = TorgoToolkit.getVisualization(name);
            assertNotNull(result);
        }
    }

    /**
     * Test of getResourceAccessors method, of class TorgoToolkit.
     */
    @Test
    public void testGetResourceAccessors() {
        LOGGER.info("getResourceAccessors");
        Set<String> result = TorgoToolkit.getResourceAccessors();
        assertNotNull(result);
    }

    /**
     * Test of getResourceAccessors method, of class TorgoToolkit.
     */
    @Test
    public void testResourceAccessors() {
        LOGGER.info("getResourceAccessors");
        Set<String> viz = TorgoToolkit.getResourceAccessors();
        for (String name : viz) {
            ResourceAccessor result = TorgoToolkit.getResourceAccessor(name);
            assertNotNull(result);
        }
        assertNotNull(TorgoToolkit.getDefaultResourceAccessor());
    }
}
