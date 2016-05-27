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

import java.awt.Color;
import org.apache.commons.beanutils.Converter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.utils.TypeHandler;

/**
 *
 * @author matta
 */
public class ColorConverterTest {
    
    public ColorConverterTest() {
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
     * Test of convert method, of class ColorConverter.
     */
    @Test
    public void testConvert() {
        Converter lookup = UtilsBeanFactory.getConverter(String.class, Color.class);
        String hex = "0x0dff00";
        Color convert = lookup.convert(Color.class, hex);
        Assert.assertNotNull(convert);
        convert = lookup.convert(Color.class, "blue");
        Assert.assertNotNull(convert);
        
        convert = TypeHandler.fromString(Color.class, "0x0dff00");
        Assert.assertNotNull(convert);
        convert = TypeHandler.fromString(Color.class, "blue");
        Assert.assertNotNull(convert);
        String redHex = TypeHandler.colorToHex(Color.red);
        Assert.assertNotNull(redHex);
        Assert.assertEquals("#ff0000", redHex);
        redHex = TypeHandler.toString(Color.red);
        Assert.assertNotNull(redHex);
        Assert.assertEquals("#ff0000", redHex);
    }
}
