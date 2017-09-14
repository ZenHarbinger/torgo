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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.logo.DynamicLogoController;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.TorgoInfo;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class LogoMenuBarTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(LogoMenuBarTest.class.getName());
    }

    public LogoMenuBarTest() {
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
     * Test of exportCanvas method, of class LogoMenuBar.
     */
    @Test
    public void testExportCanvas() {
        LOGGER.info("testExportCanvas");
        DynamicLogoController controller = (DynamicLogoController) TorgoToolkit.getController("dynamic-logo");
        controller.run();
        controller.newFile();
        JFrame window = null;

        try {
            Method method = ControllerBase.class.getDeclaredMethod("getWindow");
            method.setAccessible(true);
            Object invoke = method.invoke(controller);
            if (invoke != null && JFrame.class.isAssignableFrom(invoke.getClass())) {
                window = (JFrame) invoke;
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (robot == null) {
            return;
        }

        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        File f = null;
        try {
            f = File.createTempFile("pre", "suf");
        } catch (IOException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        robot.delay(3000);
        controller.openFile(ClassLoader.getSystemClassLoader().getResource("logo/examples/antlr/fractal.txt"));
        controller.startInterpreter();
        robot.delay(3000);

        System.out.println("exportSVG - ext");
        exportSVG(robot, true);
        System.out.println("exportSVG - no ext");
        exportSVG(robot, false);
        System.out.println("exportPNG - ext");
        exportPNG(robot, true);
        System.out.println("exportPNG - no ext");
        exportPNG(robot, false);
        System.out.println("exportGIF - ext");
        exportGIF(robot, true);
        System.out.println("exportGIF - no ext");
        exportGIF(robot, false);

        controller.close();
    }

    private void exportSVG(Robot robot, boolean ext) {
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_V}, 100);
        robot.delay(100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        if (ext) {
            pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_S}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_V}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
        }
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        java.nio.file.Path currentRelativePath = java.nio.file.Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File t = new File(s + File.separator + "t.svg");
//        assertTrue(t.isFile());
        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportGIF(Robot robot, boolean ext) {
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_G}, 100);
        robot.delay(100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        if (ext) {
            pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_I}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_F}, 100);
        }
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        robot.delay(10000);

        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);
        java.nio.file.Path currentRelativePath = java.nio.file.Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File t = new File(s + File.separator + "t.gif");
//        assertTrue(t.isFile());
        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportPNG(Robot robot, boolean ext) {
        pressKey(robot, new int[]{KeyEvent.VK_ALT, KeyEvent.VK_P}, 100);
        robot.delay(100);
        pressKey(robot, new int[]{KeyEvent.VK_T}, 100);
        if (ext) {
            pressKey(robot, new int[]{KeyEvent.VK_PERIOD}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_P}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_N}, 100);
            pressKey(robot, new int[]{KeyEvent.VK_G}, 100);
        }
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        java.nio.file.Path currentRelativePath = java.nio.file.Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File t = new File(s + File.separator + "t.png");
//        assertTrue(t.isFile());
        if (t.isFile()) {
            t.delete();
        }
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
