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
package org.tros.utils.converters;

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
public class ClassConverterTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(ClassConverterTest.class.getName());
    }

    public ClassConverterTest() {
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
     * Test of convert method, of class ClassConverter.
     */
    @Test
    public void testConvert() {
        LOGGER.info("class convert test");
        Converter lookup = UtilsBeanFactory.getConverter(String.class, Class.class);
        String hex = ClassConverter.class.getName();
        Class convert = lookup.convert(Class.class, hex);
        assertNotNull(convert);
        assertEquals(hex, convert.getName());
        Class fromString = (Class) TypeHandler.fromString(Class.class, hex);
        assertNotNull(fromString);
        assertEquals(hex, fromString.getName());
        fromString = (Class) TypeHandler.fromString(Class.class, "no.such.class");
        assertNull(fromString);
    }
}
