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
package org.tros.logo.swing;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.logo.DynamicLogoController;
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
public class LogoMenuBarTest {

    private static Logger LOGGER;

    public LogoMenuBarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Logging.initLogging(TorgoInfo.INSTANCE);
        LOGGER = Logger.getLogger(LogoMenuBarTest.class.getName());
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
     * Test of exportCanvas method, of class LogoMenuBar.
     */
    @Test
    public void testExportCanvas() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }

        DynamicLogoController controller = (DynamicLogoController) TorgoToolkit.getController("dynamic-logo");
        controller.run();
        assertEquals("dynamic-logo", controller.getLang());
        String[] files = new String[]{
            "logo/examples/antlr/fractal.txt"
        };

        for (String file : files) {
            LOGGER.info(file);
            controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));
            controller.disable("TraceLogger");

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
                LOGGER.log(Level.SEVERE, null, ex);
            }
            assertTrue(started.get());
            assertTrue(finished.get());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            System.out.println("exportSVG");
            exportSVG(robot);
            System.out.println("exportPNG");
            exportPNG(robot);
            System.out.println("exportGIF");
            exportGIF(robot);
        }

        controller.close();
    }

    private void exportPNG(Robot robot) {
        File t = new File("t.png");

        if (t.isFile()) {
            t.delete();
        }

        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
//            pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_M}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_P}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_P}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_N}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);

        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportGIF(Robot robot) {
        File t = new File("t.gif");

        if (t.isFile()) {
            t.delete();
        }

        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
//            pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_M}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_P}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_I}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(5000);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportSVG(Robot robot) {
        File t = new File("t.svg");

        if (t.isFile()) {
            t.delete();
        }

        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
//            pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_M}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_P}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_SLASH}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_S}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_V}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);

        if (t.isFile()) {
            t.delete();
        }
    }

    void pressKey(Robot robot, int[] keys, int delay) {
        for (int key : keys) {
            robot.keyPress(key);
        }
        robot.delay(delay);
        for (int key : keys) {
            robot.keyRelease(key);
        }
        robot.delay(delay);
    }

}
