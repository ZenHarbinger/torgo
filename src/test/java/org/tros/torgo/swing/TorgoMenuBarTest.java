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

import org.tros.torgo.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.logo.LexicalLogoController;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class TorgoMenuBarTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(TorgoMenuBarTest.class.getName());
    }
    
    public TorgoMenuBarTest() {
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
    public void torgoMenuBar() {
        LOGGER.info("torgoMenuBar");
        LexicalLogoController controller = (LexicalLogoController) TorgoToolkit.getController("lexical-logo");
        controller.run();
        controller.newFile();
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(TorgoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }

        File t = new File("t.logo");
        File b = new File("b.logo");

        if (t.isFile()) {
            t.delete();
        }
        if (b.isFile()) {
            b.delete();
        }

        robot.delay(3000);
        LOGGER.info("new");
        //new
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.info("trace");
        //trace
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.info("reset trace");
        //reset trace
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.warning("TODO: save");
//        //save
//        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_L}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        LOGGER.warning("TODO: save as");
//        //save as
//        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_B}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_L}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        LOGGER.warning("open");
//        //open
//        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_B}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_L}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_O}, 100);
//        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.info("about");
        //view about
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_RIGHT}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.info("close about");
        //close about
        robot.delay(200);
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F4}, 100);
        robot.delay(200);

        LOGGER.info("view log");
        //view log console
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_DOWN}, 100);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        LOGGER.info("close log");
        //close log console
        robot.delay(200);
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_F4}, 100);
        robot.delay(200);

        if (t.isFile()) {
            t.delete();
        }
        if (b.isFile()) {
            b.delete();
        }

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
