/*
 * Copyright 2015-2017 Matthew Aguirre
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

import java.io.File;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.logo.DynamicLogoController;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class ControllerBaseTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(ControllerBaseTest.class.getName());
    }

    public ControllerBaseTest() {
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
     * Test of removeInterpreterListener method, of class ControllerBase.
     */
    @Test
    public void testGetFilter() {
        DynamicLogoController controller = new DynamicLogoController();
        ControllerBase base = (ControllerBase) controller;
        assertNotNull(base.getFilter());
        assertNull(base.getFile());
    }
    
    @Test
    public void testPrintCanvas() {
        DynamicLogoController controller = new DynamicLogoController();
        ControllerBase base = (ControllerBase) controller;
        base.printCanvas();
    }
    
    @Test
    public void testGetWindowName() {
        DynamicLogoController controller = new DynamicLogoController();
        ControllerBase base = (ControllerBase) controller;
        base.runHelper();
        String nametest = base.getWindowName();
        assertNotNull(nametest);
    }
    
    @Test
    public void testOpenFile() {
        DynamicLogoController controller = new DynamicLogoController();
        ControllerBase base = (ControllerBase) controller;
        
    }
}