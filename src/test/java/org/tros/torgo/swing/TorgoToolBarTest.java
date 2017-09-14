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
package org.tros.torgo.swing;

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
import org.tros.logo.LexicalLogoController;
import org.tros.torgo.MainTest;
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
public class TorgoToolBarTest {

    private final static Logger LOGGER;
    
    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(TorgoToolBarTest.class.getName());
    }

    public TorgoToolBarTest() {
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

    @Test
    public void testTorgoToolBar() {
        LOGGER.info("torgoToolBar");
        LexicalLogoController controller = (LexicalLogoController) TorgoToolkit.getController("lexical-logo");
        controller.run();
        controller.newFile();
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
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }

        robot.delay(3000);
        LOGGER.info("new");
        //new
        pressKey(robot, new int[]{KeyEvent.VK_SPACE}, 100);

        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        robot.delay(500);

        LOGGER.info("debug");
        pressKey(robot, new int[]{KeyEvent.VK_F5}, 100);

        while (!finished.get()) {
            robot.delay(100);
        }

        //close app
        controller.close();
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
