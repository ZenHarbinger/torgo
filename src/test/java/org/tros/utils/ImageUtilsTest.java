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
package org.tros.utils;

import java.awt.Image;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.Main;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class ImageUtilsTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(ImageUtilsTest.class.getName());
    }

    public ImageUtilsTest() {
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
     * Test of createImageIcon method, of class ImageUtils.
     */
    @Test
    public void testCreateImageIcon() {
        ImageIcon createImageIcon = ImageUtils.createImageIcon(Main.IMAGE_ICON_CLASS_PATH, Main.IMAGE_ICON_CLASS_PATH);
        assertNotNull(createImageIcon);
        createImageIcon = ImageUtils.createImageIcon("no-such-img", "no-such-img");
        assertNull(createImageIcon);
    }

    /**
     * Test of createImage method, of class ImageUtils.
     */
    @Test
    public void testCreateImage() {
        Image createImage = ImageUtils.createImage(Main.IMAGE_ICON_CLASS_PATH);
        assertNotNull(createImage);
    }

    /**
     * Test of imageToBufferedImage method, of class ImageUtils.
     */
    @Test
    public void testImageToBufferedImage() {
    }

    /**
     * Test of makeColorTransparent method, of class ImageUtils.
     */
    @Test
    public void testMakeColorTransparent() {
    }
    
}
