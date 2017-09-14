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
package org.tros.torgo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.utils.ImageUtils;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class MainTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(MainTest.class.getName());
    }

    public MainTest() {
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
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        LOGGER.info("main");
        ArrayList<String[]> tests = new ArrayList<>();
        tests.add(new String[]{"-i"});
        tests.add(new String[]{"-list"});
        for (String[] args : tests) {
            Main.main(args);
        }
    }

    @Test
    public void testMainNewAndClose() {
        LOGGER.info("main");
        ArrayList<String[]> tests = new ArrayList<>();
        tests.add(new String[]{"-l", "dynamic-logo"});
        tests.add(new String[]{"-lang", "lexical-logo"});
        tests.add(new String[]{"-lang", "no-such-language"});
        for (String[] args : tests) {
            Main.main(args);
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

            //close app
            pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        }
    }

    @Test
    public void testMainFileAndClose() {
        LOGGER.info("main");
        ArrayList<String[]> tests = new ArrayList<>();
        tests.add(new String[]{});
        tests.add(new String[]{"test.lexical-logo"});
        tests.add(new String[]{"test.no-such-logo"});
        for (String[] args : tests) {
            Main.main(args);
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

            //close app
            pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        }
    }

    @Test
    public void imageIcon() {
        LOGGER.info("main");
        assertNull(ImageUtils.getIcon("no.such.icon"));
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
