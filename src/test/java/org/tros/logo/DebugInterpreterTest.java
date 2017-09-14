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
package org.tros.logo;

import org.tros.torgo.viz.TraceLoggerTest;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.logo.swing.LogoMenuBar;
import org.tros.torgo.TorgoInfo;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class DebugInterpreterTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(DebugInterpreterTest.class.getName());
    }

    public DebugInterpreterTest() {
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
     * Test of create method, of class TraceLogger.
     */
    @Test
    public void testCreate() {
        LOGGER.info("debugTest");
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        boolean checked = prefs.getBoolean("wait-for-repaint", true);
        prefs.putBoolean("wait-for-repaint", true);
        DynamicLogoController controller = (DynamicLogoController) TorgoToolkit.getController("dynamic-logo");
        controller.run();
        controller.newFile();
        assertEquals("dynamic-logo", controller.getLang());
        String[] files = new String[]{"logo/examples/antlr/octagon.txt"};

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(TraceLoggerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }

        robot.delay(3000);
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        for (String file : files) {
            System.out.println(file);
            Logger.getLogger(LogoControllerTest.class.getName()).log(Level.INFO, file);
            controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));
            controller.disable("TraceLogger");

            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            final AtomicBoolean started = new AtomicBoolean(false);
            final AtomicBoolean finished = new AtomicBoolean(false);
            InterpreterListener listener = new InterpreterListener() {
                @Override
                public void started() {
                    started.set(true);
                }

                @Override
                public void finished() {
                    finished.set(true);
                }

                @Override
                public void error(Exception e) {
                }

                @Override
                public void message(String msg) {
                }

                @Override
                public void currStatement(CodeBlock block, Scope scope) {
                }
            };
            controller.addInterpreterListener(listener);
            controller.debugInterpreter();

            try {
                while (!finished.get()) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertTrue(started.get());
            assertTrue(finished.get());
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        controller.close();
        prefs.putBoolean("wait-for-repaint", checked);
    }

    void pressKey(Robot robot, int[] keys, int delay) {
        for (int key : keys) {
            robot.keyPress(key);
            robot.delay(delay);
        }
        robot.delay(delay);
        for (int key : keys) {
            robot.keyRelease(key);
            robot.delay(delay);
        }
        robot.delay(delay);
    }
}
