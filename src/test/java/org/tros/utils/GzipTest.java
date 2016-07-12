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
import org.tros.torgo.TorgoInfo;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class GzipTest {
    
    private static Logger LOGGER;

    public GzipTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Logging.initLogging(TorgoInfo.INSTANCE);
        LOGGER = Logger.getLogger(GzipTest.class.getName());
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
        LOGGER.info("gunzip");
        Gzip gz = new Gzip();
        assertNotNull(gz);
    }

    /**
     * Test of GunzipFile method, of class Gzip.
     */
    @Test
    public void testGunzipFile() throws Exception {
        LOGGER.warning("TODO: GunzipFile");
    }
    
}
