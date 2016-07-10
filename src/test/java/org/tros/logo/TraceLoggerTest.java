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
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;

/**
 *
 * @author matta
 */
public class TraceLoggerTest {

    public TraceLoggerTest() {
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
        System.out.println("createConsole");
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        boolean checked = prefs.getBoolean("wait-for-repaint", true);
        prefs.putBoolean("wait-for-repaint", true);
        LogoController controller = (LogoController) TorgoToolkit.getController("logo");
        controller.run();
        assertEquals("logo", controller.getLang());

            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                Logger.getLogger(TraceLoggerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (robot == null) {
                return;
            }

            robot.delay(100);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_F);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(100);
            
        String[] files = new String[]{
            "logo/examples/tortue/octagon.logo",
            "logo/examples/tortue/pretty.logo",
            "logo/examples/tortue/snowflake.logo",
            "logo/examples/tortue/spokes.logo",
            "logo/examples/tortue/test.logo",
            "logo/examples/tortue/tortue-text.logo"};

        for (String file : files) {
            Logger.getLogger(LogoControllerTest.class.getName()).log(Level.INFO, file);
            controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));

            final AtomicBoolean started = new AtomicBoolean(false);
            final AtomicBoolean finished = new AtomicBoolean(false);
            controller.addInterpreterListener(new InterpreterListener() {
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
            });

            controller.startInterpreter();

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

}
