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
package org.tros.utils.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import org.apache.commons.beanutils.Converter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.TypeHandler;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class UtilsBeanFactoryTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(UtilsBeanFactoryTest.class.getName());
    }

    public UtilsBeanFactoryTest() {
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
    
    public static class Date2 extends Date{
        
    }

    /**
     * Test of getConverter method, of class UtilsBeanFactory.
     */
    @Test
    public void testGetConverter() {
        System.out.println("getConverter");
        Class from = TypeHandler.class;
        Class to = Integer.class;
        Converter expResult = null;
        Converter result = UtilsBeanFactory.getConverter(from, to);
        assertNotNull(result);

        result = UtilsBeanFactory.getConverter(Date2.class, String.class);
        String res = result.convert(String.class, Calendar.getInstance().getTime());
        assertNotNull(result);

        result = UtilsBeanFactory.getConverter(Date2.class, UtilsBeanFactoryTest.class);
        assertNull(result);
    }
    
}
