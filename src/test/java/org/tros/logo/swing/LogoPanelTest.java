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
package org.tros.logo.swing;

import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.logo.DebugInterpreterTest;
import org.tros.utils.logging.Logging;
import static org.junit.Assert.*;
import org.tros.torgo.Controller;
import java.awt.Font;
import org.tros.torgo.TorgoToolkit;

/**
 *
 * @author matta
 */
public class LogoPanelTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(DebugInterpreterTest.class.getName());
    }

    public LogoPanelTest() {
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

    private static class DrawListenerImpl implements DrawListener {

        @Override
        public void drawn(Drawable sender) {
        }
    }

    /**
     * Test of addListener method, of class LogoPanel.
     */
    @Test
    public void testAddRemoveListener() {
        LogoPanel panel = new LogoPanel(null);
        DrawListener listener = new DrawListenerImpl();
        panel.addListener(listener);
        panel.removeListener(listener);
    }

    /**
     * Test of zoomIn and zoomOut methods, of class LogoPanel.
     */
    @Test
    public void testZoomInOut() {
        LogoPanel panel = new LogoPanel(null);
        panel.testZoom();  
    }

    @Test
    public void testCloneDrawable() {
        LogoPanel panel = new LogoPanel(null);
        assertNotNull(panel.cloneDrawable().cloneDrawable());
    }

    @Test
    public void testMessage() {
        Controller controller = null;
        //LogoPanel panel = new LogoPanel(new TorgoTextConsole());
        //LogoPanel panel = new LogoPanel(null);
        //panel.message("test");
    }

    @Test
    public void testForward() {
        LogoPanel panel = new LogoPanel(null);
        DrawListener listener = new DrawListenerImpl();
        panel.forward(0);
    }

    @Test
    public void testPause() throws Exception {
        LogoPanel panel = new LogoPanel(null);
        
        Thread t = new Thread() {
            public void run () {
            panel.pause(10000);
            }
        };
            t.start();
            Thread.sleep(100); // let the other thread start
            t.interrupt();
    } 
    
    @Test
    public void testRight() {
        LogoPanel panel = new LogoPanel(null);
        panel.right(100);
        panel.left(100);
    }

    /**
     * Test of methods within nested Drawable classes.
     */
    @Test
    public void testNestedDrawable() {
        LogoPanel panel = new LogoPanel(null);
        //forward
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.forward(0);
        assertTrue(panel.getTestCheck());
        //backward
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.backward(0);
        assertTrue(panel.getTestCheck());
        //left
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.left(0);
        assertTrue(panel.getTestCheck());
        //right
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.right(0);
        assertTrue(panel.getTestCheck());
        //setXY
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.setXY(0, 0);
        assertTrue(panel.getTestCheck());
        //penUp
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.penUp();
        assertTrue(panel.getTestCheck());
        //penDown
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.penDown();
        assertTrue(panel.getTestCheck());
        //clear
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.clear();
        assertTrue(panel.getTestCheck());
        //home
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.home();
        assertTrue(panel.getTestCheck());
        //canvascolor
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.canvascolor("blue");
        assertTrue(panel.getTestCheck());
        //pencolor
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.pencolor("blue");
        assertTrue(panel.getTestCheck());
        //drawString
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.drawString("test");
        assertTrue(panel.getTestCheck());
        //fontSize
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.fontSize(5);
        assertTrue(panel.getTestCheck());
        //fontName
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.fontName(Font.DIALOG);
        assertTrue(panel.getTestCheck());
        //fontStyle
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.fontStyle(Font.BOLD);
        assertTrue(panel.getTestCheck());
        //hideTurtle
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.hideTurtle();
        assertTrue(panel.getTestCheck());
        //showTurtle
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.showTurtle();
        assertTrue(panel.getTestCheck());
        //private methods
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.testCanvasColor();
        assertTrue(panel.getTestCheck());
        panel.setTesting();
        assertFalse(panel.getTestCheck());
        panel.testPenColor();
        assertTrue(panel.getTestCheck());
    }
    
    /**
     * Test of exception handling.
     */
    @Test
    public void testExceptions() {
        //LogoPanel panel = new LogoPanel(null);
        //panel.setTestingEx();
        //panel = new LogoPanel(null);
        //assertTrue(panel.getTestCheck());
        
        LogoPanel panel2 = new LogoPanel(null);
        panel2.setTestingEx();
        assertFalse(panel2.getTestCheck());
        panel2.clear();
        assertTrue(panel2.getTestCheck());
        
        LogoPanel panel3 = new LogoPanel(null);
        panel3.setTestingEx();
        panel3.repaint();
        assertTrue(panel3.getTestCheck());
    }
    
    @Test
    public void testDrawListenerImpl() {
        LogoPanel panel = new LogoPanel(null);
        panel.testDrawListener();
    }
}
