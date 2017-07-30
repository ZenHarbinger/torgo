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
import org.tros.torgo.TorgoInfo;
import org.tros.utils.logging.Logging;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoTextConsole;
import org.tros.logo.LogoController;
import org.tros.torgo.Controller;

/**
 *
 * @author matta
 */
public class LogoPanelTest {
    
    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoInfo.INSTANCE);
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
        LogoPanel panel = new LogoPanel(null);
        
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
    
}
