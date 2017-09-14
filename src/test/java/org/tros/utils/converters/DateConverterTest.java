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
public class DateConverterTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(DateConverterTest.class.getName());
    }

    public DateConverterTest() {
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
     * Test of convert method, of class DateConverter.
     */
    @Test
    public void testConvert() {
        LOGGER.info("date convert test");
        Calendar d = Calendar.getInstance();
        d.set(Calendar.MILLISECOND, 0);
        String hex = TypeHandler.dateToString(d);

        Converter lookup = UtilsBeanFactory.getConverter(Calendar.class, String.class);
        Calendar convert = lookup.convert(Calendar.class, hex);
        String hex2 = TypeHandler.dateToString(convert);

        assertEquals(hex, hex2);
        assertEquals(d, convert);

        lookup = UtilsBeanFactory.getConverter(Date.class, String.class);
        Date convert2 = lookup.convert(Date.class, hex);
        String hex3 = TypeHandler.dateToString(convert2);

        lookup = UtilsBeanFactory.getConverter(String.class, Date.class);
        String convert3 = lookup.convert(String.class, convert2);
        assertEquals(convert3, hex);

        lookup = UtilsBeanFactory.getConverter(String.class, Calendar.class);
        String convert4 = lookup.convert(String.class, convert);
        assertEquals(convert4, hex2);

        assertNull(lookup.convert(String.class, null));
        assertEquals(lookup.convert(DateConverterTest.class, "demo"), "demo");

        assertEquals(hex, hex3);

        lookup = UtilsBeanFactory.getConverter(Calendar.class, String.class);
        boolean thrown = false;
        try {
            Calendar x = lookup.convert(Calendar.class, "no.such.value");
        } catch (ClassCastException ex) {
            thrown = true;
        }
        assertTrue(thrown);
        lookup = UtilsBeanFactory.getConverter(Date.class, String.class);
        thrown = false;
        try {
            Date x = lookup.convert(Date.class, "no.such.value");
        } catch (ClassCastException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
