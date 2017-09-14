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
public class PathUtilsTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(PathUtilsTest.class.getName());
    }

    public PathUtilsTest() {
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
     * Test of hidden constructor.
     */
    @Test
    public void testConstructor() {
//        PathUtils pu = new PathUtils();
//        assertNotNull(pu);
    }

    /**
     * Test of getHomeDir method, of class PathUtils.
     */
    @Test
    public void testGetHomeDir() {
        LOGGER.info("getHomeDir");
        String result = PathUtils.getHomeDir();
        assertNotNull(result);
    }

    /**
     * Test of getTempDir method, of class PathUtils.
     */
    @Test
    public void testGetTempDir() {
        LOGGER.info("getTempDir");
        String result = PathUtils.getTempDir();
        assertNotNull(result);
        String result2 = PathUtils.getTempDir(TorgoToolkit.getBuildInfo());
        assertNotNull(result2);
        assertNotEquals(result, result2);
    }

    /**
     * Test of getApplicationDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationDirectory() {
        LOGGER.info("getApplicationDirectory");
        String result = PathUtils.getApplicationDirectory(TorgoToolkit.getBuildInfo());
        assertNotNull(result);
    }

    /**
     * Test of getApplicationEtcDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationEtcDirectory() {
        LOGGER.info("getApplicationEtcDirectory");
        String result = PathUtils.getApplicationEtcDirectory(TorgoToolkit.getBuildInfo());
        assertNotNull(result);
    }

    /**
     * Test of getApplicationLibDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationLibDirectory() {
        LOGGER.info("getApplicationLibDirectory");
        String result = PathUtils.getApplicationLibDirectory(TorgoToolkit.getBuildInfo());
        assertNotNull(result);
    }

    /**
     * Test of getApplicationConfigDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationConfigDirectory() {
        LOGGER.info("getApplicationConfigDirectory");
        String result = PathUtils.getApplicationConfigDirectory(TorgoToolkit.getBuildInfo());
        assertNotNull(result);
    }

    /**
     * Test of getLogDirectory method, of class PathUtils.
     */
    @Test
    public void testGetLogDirectory() {
        LOGGER.info("getLogDirectory");
        String result = PathUtils.getLogDirectory(TorgoToolkit.getBuildInfo());
        assertNotNull(result);
    }
}
