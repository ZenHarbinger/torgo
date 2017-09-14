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

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.utils.logging.Logging;
import java.net.MalformedURLException;
import java.io.IOException;
//import static org.easymock.EasyMock.*;
import org.easymock.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author matta
 */
public class UpdateCheckerTest {
    private UpdateChecker checker;

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(UpdateCheckerTest.class.getName());
    }

    public UpdateCheckerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        checker = new UpdateChecker();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUpdateVersion method, of class UpdateChecker.
     */
    @Test
    public void testGetUpdateVersion() {
        checker.setCheckForUpdate(false);
        assertFalse(checker.getCheckForUpdate());
        checker.hasUpdate();
        checker.setCheckForUpdate(true);
        assertTrue(checker.getCheckForUpdate());
        checker.hasUpdate();
        assertNotNull(checker.getUpdateVersion());
    }

    @Test
    public void testHasUpdate() {
        checker.urlExceptionTest();
        assertTrue(checker.getURLException());
        checker.hasUpdate();
        assertFalse(checker.getURLException());
        
        checker.ioExceptionTest();
        assertTrue(checker.getIOException());
        checker.hasUpdate();
        assertFalse(checker.getIOException());
    }
}
