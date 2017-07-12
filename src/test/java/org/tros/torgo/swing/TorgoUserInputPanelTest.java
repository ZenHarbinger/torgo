/*
 * Copyright 2015-2017 Matthew Aguirre Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.tros.torgo.swing;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.tros.torgo.TorgoInfo;
import org.tros.utils.logging.Logging;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.logo.DynamicLogoController;

/**
 * @author Samuel Washburn
 */
public class TorgoUserInputPanelTest
{
    private final static Logger LOGGER;

    static
    {
        Logging.initLogging(TorgoInfo.INSTANCE);
        LOGGER = Logger.getLogger(TorgoToolBarTest.class.getName());
    }


    public TorgoUserInputPanelTest()
    {
    }


    @BeforeClass
    public static void setUpClass()
    {
    }


    @AfterClass
    public static void tearDownClass()
    {
    }


    @Before
    public void setUp()
    {
    }


    @After
    public void tearDown()
    {
    }


    @Test
    public void testZoomIn()
    {
        TorgoUserInputPanel panel = new TorgoUserInputPanel(
            new DynamicLogoController(), "Logo", false, "text/logo");
        assertEquals(panel.getInputTextAreaSize(), 5);
        panel.zoomInTest();
        assertEquals(panel.getInputTextAreaSize(), 6);
        panel.zoomInTest();
        assertEquals(panel.getInputTextAreaSize(), 7);
        for (int i = 0; i < 50; i++)
        {
            panel.zoomInTest();
        }
        assertEquals(panel.getInputTextAreaSize(), 50);

    }


    @Test
    public void testZoomOut()
    {
        TorgoUserInputPanel panel = new TorgoUserInputPanel(
            new DynamicLogoController(), "Logo", false, "text/logo");
        assertEquals(panel.getInputTextAreaSize(), 12);
        panel.zoomOutTest();
        assertEquals(panel.getInputTextAreaSize(), 11);
        for (int i = 0; i < 10; i++)
        {
            panel.zoomOutTest();
        }
        assertEquals(panel.getInputTextAreaSize(), 5);
    }


    @Test
    public void testZoomReset()
    {
        TorgoUserInputPanel panel = new TorgoUserInputPanel(
            new DynamicLogoController(), "Logo", false, "text/logo");
        panel.zoomResetTest();
        assertEquals(panel.getInputTextAreaSize(), 12);
    }


    @Test
    public void testAppendToSource()
    {
        TorgoUserInputPanel panel = new TorgoUserInputPanel(
            new DynamicLogoController(), "Logo", true, "text/logo");
        assertEquals(panel.getInputTextArea().getText().equals("test"), false);
        panel.appendToSource("test");
        assertTrue(panel.getInputTextArea().getText().contains("test"));
        TorgoUserInputPanel pan = new TorgoUserInputPanel(
            new DynamicLogoController(), "Logo", false, "text/logo");
        assertEquals(pan.getInputTextArea().getText().contains("test"), false);
        pan.appendToSource("test");
        assertEquals(pan.getInputTextArea().getText().contains("test"), true);
    }
}
