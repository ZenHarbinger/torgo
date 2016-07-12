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
import org.tros.torgo.TorgoInfo;
import org.tros.utils.TypeHandler;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class DateConverterTest {

    private static Logger LOGGER;

    public DateConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Logging.initLogging(TorgoInfo.INSTANCE);
        LOGGER = Logger.getLogger(DateConverterTest.class.getName());
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

        assertEquals(hex, hex3);
    }
}
