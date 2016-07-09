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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoInfo;

/**
 *
 * @author matta
 */
public class PathUtilsTest {
    
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
     * Test of getHomeDir method, of class PathUtils.
     */
    @Test
    public void testGetHomeDir() {
        System.out.println("getHomeDir");
        String result = PathUtils.getHomeDir();
        assertNotNull(result);
    }

    /**
     * Test of getTempDir method, of class PathUtils.
     */
    @Test
    public void testGetTempDir() {
        System.out.println("getTempDir");
        String result = PathUtils.getTempDir();
        assertNotNull(result);

    }

    /**
     * Test of getApplicationDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationDirectory() {
        System.out.println("getApplicationDirectory");
        String result = PathUtils.getApplicationDirectory(TorgoInfo.INSTANCE);
        assertNotNull(result);
    }

    /**
     * Test of getApplicationEtcDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationEtcDirectory() {
        System.out.println("getApplicationEtcDirectory");
        String result = PathUtils.getApplicationEtcDirectory(TorgoInfo.INSTANCE);
        assertNotNull(result);
    }

    /**
     * Test of getApplicationLibDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationLibDirectory() {
        System.out.println("getApplicationLibDirectory");
        String result = PathUtils.getApplicationLibDirectory(TorgoInfo.INSTANCE);
        assertNotNull(result);
    }

    /**
     * Test of getApplicationConfigDirectory method, of class PathUtils.
     */
    @Test
    public void testGetApplicationConfigDirectory() {
        System.out.println("getApplicationConfigDirectory");
        String result = PathUtils.getApplicationConfigDirectory(TorgoInfo.INSTANCE);
        assertNotNull(result);
    }

    /**
     * Test of getLogDirectory method, of class PathUtils.
     */
    @Test
    public void testGetLogDirectory() {
        System.out.println("getLogDirectory");
        String result = PathUtils.getLogDirectory(TorgoInfo.INSTANCE);
        assertNotNull(result);
    }   
}
