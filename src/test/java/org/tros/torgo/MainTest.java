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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author matta
 */
public class MainTest {

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
        System.out.println("main");
        String[] args = new String[]{"-i"};
        Main.main(args);
    }

    @Test
    public void testMainNewAndClose() {
        System.out.println("main");
        String[] args = new String[]{"-l", "dynamic-logo"};
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

        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F);
        robot.delay(100);
//        robot.keyPress(KeyEvent.VK_DOWN);
//        robot.delay(100);
//        robot.keyRelease(KeyEvent.VK_DOWN);
//        robot.delay(100);
//        robot.delay(100);
//        robot.keyPress(KeyEvent.VK_DOWN);
//        robot.delay(100);
//        robot.keyRelease(KeyEvent.VK_DOWN);
//        robot.delay(100);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_F);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(100);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(100);
    }
}
