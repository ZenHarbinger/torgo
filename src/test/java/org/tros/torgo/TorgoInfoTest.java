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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.utils.PropertiesInitializer;

/**
 *
 * @author matta
 */
public class TorgoInfoTest {

    private static TorgoInfo INFO;

    public TorgoInfoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        INFO = new TorgoInfo();
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
     * Test of isSnapd method, of class TorgoInfo.
     */
    @Test
    public void testIsSnapd() {
        INFO.setSnapd(true);
        assertTrue(INFO.isSnapd());
        INFO.setSnapd(false);
        assertFalse(INFO.isSnapd());
    }

    /**
     * Test of setSnapd method, of class TorgoInfo.
     */
    @Test
    public void testSetSnapd() {
        INFO.setSnapd(true);
        assertTrue(INFO.isSnapd());
        INFO.setSnapd(false);
        assertFalse(INFO.isSnapd());
    }

    /**
     * Test of getVersion method, of class TorgoInfo.
     */
    @Test
    public void testGetVersion() {
        assertNotNull(INFO.getVersion());
    }

    /**
     * Test of setVersion method, of class TorgoInfo.
     */
    @Test
    public void testSetVersion() {
    }

    /**
     * Test of getBuildtime method, of class TorgoInfo.
     */
    @Test
    public void testGetBuildtime() {
    }

    /**
     * Test of setBuildtime method, of class TorgoInfo.
     */
    @Test
    public void testSetBuildtime() {
    }

    /**
     * Test of getBuilder method, of class TorgoInfo.
     */
    @Test
    public void testGetBuilder() {
        assertNotNull(INFO.getBuilder());
    }

    /**
     * Test of setBuilder method, of class TorgoInfo.
     */
    @Test
    public void testSetBuilder() {
    }

    /**
     * Test of getApplicationName method, of class TorgoInfo.
     */
    @Test
    public void testGetApplicationName() {
        assertEquals("torgo", INFO.getApplicationName());
    }

    /**
     * Test of setApplicationName method, of class TorgoInfo.
     */
    @Test
    public void testSetApplicationName() {
    }

    /**
     * Test of getCompany method, of class TorgoInfo.
     */
    @Test
    public void testGetCompany() {
        assertNotNull(INFO.getCompany());
    }

    /**
     * Test of setCompany method, of class TorgoInfo.
     */
    @Test
    public void testSetCompany() {
    }

    /**
     * Test of toString method, of class TorgoInfo.
     */
    @Test
    public void testToString() {
        assertNotNull(INFO.toString());
    }

    /**
     * This should fail unless the ObjectMapper is in the CLASSPATH.
     */
    @Test
    public void testCopy() {
        PropertiesInitializer pi = INFO.copy();
        if (PropertiesInitializer.canCopy()) {
            assertNotNull(pi);
        } else {
            assertNull(pi);
        }
    }
}
