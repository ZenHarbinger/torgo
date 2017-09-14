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
package org.tros.torgo.swing;

import java.util.Locale;
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
public class LocalizationTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(LocalizationTest.class.getName());
    }

    public LocalizationTest() {
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
     * Test of setLang method, of class Localization.
     */
    @Test
    public void testSetLang() {
        Locale lang = Localization.getLang();
        Localization.setLang(new Locale("no-such-lang"));
//        assertEquals(lang, Localization.getLang());
        Localization.setLang(new Locale("fr"));
        Locale lang2 = Localization.getLang();
        assertEquals("fr", lang2.getLanguage());
        Localization.setLang(new Locale("en", "US"));
    }

    /**
     * Test of getLocalizedString method, of class Localization.
     */
    @Test
    public void testGetLocalizedString() {
        String val = Localization.getLocalizedString("no-such-string");
        assertEquals("no-such-string", val);
        val = Localization.getLocalizedString("ExportMenu");
        assertNotEquals("ExportMenu", val);
    }
    
}
