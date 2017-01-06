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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.logo.DynamicLogoController;
import org.tros.logo.LogoCanvas;
import org.tros.logo.LogoController;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.TorgoInfo;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.swing.BufferedImageProvider;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class LogoMenuBarTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoInfo.INSTANCE);
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
//        prefs.put("export-directory", f.getParent());
//        DynamicLogoController controller = (DynamicLogoController) TorgoToolkit.getController("dynamic-logo");
//        controller.run();
//        assertEquals("dynamic-logo", controller.getLang());
//        String[] files gi= new String[]{
//            "logo/examples/antlr/fractal.txt"
//        };
        robot.delay(3000);
        controller.openFile(ClassLoader.getSystemClassLoader().getResource("logo/examples/antlr/fractal.txt"));
        controller.startInterpreter();
        robot.delay(3000);

        System.out.println("exportSVG");
        exportSVG(robot, window, controller);
        System.out.println("exportPNG");
        exportPNG(robot, window, controller);
        System.out.println("exportGIF");
        exportGIF(robot, window, controller);

        controller.close();
    }

    private void exportSVG(Robot robot, JFrame window, DynamicLogoController controller) {
        final JFrame f = new JFrame();
        final LogoMenuBar lmb = (LogoMenuBar) window.getJMenuBar();
        LogoCanvas lc2 = null;
        try {
            //        LogoMenuBar lmb = new LogoMenuBar(window, controller, canvas);
            Field declaredField = LogoController.class.getDeclaredField("canvas");
            declaredField.setAccessible(true);
            Object get = declaredField.get(controller);
            if (get != null && LogoCanvas.class.isAssignableFrom(get.getClass())) {
                lc2 = (LogoCanvas) get;
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tmpDir = System.getProperty("java.io.tmpdir");
        final File t = new File(tmpDir + System.getProperty("file.separator") + "t.png");
        final LogoCanvas lc = lc2;
        if (t.isFile()) {
            t.delete();
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Method generateSVG = LogoMenuBar.class.getDeclaredMethod("generateSVG", Drawable.class, OutputStream.class);
                    generateSVG.setAccessible(true);
                    FileOutputStream fos = new FileOutputStream(t);
                    generateSVG.invoke(lmb, (Drawable) lc, fos);
                    fos.close();
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | FileNotFoundException ex) {
                    Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        th.start();

        robot.delay(5000);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        assertTrue(t.isFile());
        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportGIF(Robot robot, JFrame window, DynamicLogoController controller) {
        final JFrame f = new JFrame();
        final LogoMenuBar lmb = (LogoMenuBar) window.getJMenuBar();
        LogoCanvas lc2 = null;
        try {
            //        LogoMenuBar lmb = new LogoMenuBar(window, controller, canvas);
            Field declaredField = LogoController.class.getDeclaredField("canvas");
            declaredField.setAccessible(true);
            Object get = declaredField.get(controller);
            if (get != null && LogoCanvas.class.isAssignableFrom(get.getClass())) {
                lc2 = (LogoCanvas) get;
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tmpDir = System.getProperty("java.io.tmpdir");
        final File t = new File(tmpDir + System.getProperty("file.separator") + "t.gif");
        final LogoCanvas lc = lc2;
        if (t.isFile()) {
            t.delete();
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Method generateGIF = LogoMenuBar.class.getDeclaredMethod("generateGIF", Drawable.class, BufferedImageProvider.class, String.class);
                    generateGIF.setAccessible(true);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    generateGIF.invoke(lmb, (Drawable) lc, (BufferedImageProvider) lc, t.getPath());
//            outputStream.close();
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        th.start();

        robot.delay(5000);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        assertTrue(t.isFile());
        if (t.isFile()) {
            t.delete();
        }
    }

    private void exportPNG(Robot robot, JFrame window, DynamicLogoController controller) {
        final JFrame f = new JFrame();
        final LogoMenuBar lmb = (LogoMenuBar) window.getJMenuBar();
        LogoCanvas lc2 = null;
        try {
            //        LogoMenuBar lmb = new LogoMenuBar(window, controller, canvas);
            Field declaredField = LogoController.class.getDeclaredField("canvas");
            declaredField.setAccessible(true);
            Object get = declaredField.get(controller);
            if (get != null && LogoCanvas.class.isAssignableFrom(get.getClass())) {
                lc2 = (LogoCanvas) get;
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tmpDir = System.getProperty("java.io.tmpdir");
        final File t = new File(tmpDir + System.getProperty("file.separator") + "t.png");
        final LogoCanvas lc = lc2;
        if (t.isFile()) {
            t.delete();
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Method generatePNG = LogoMenuBar.class.getDeclaredMethod("generatePNG", BufferedImageProvider.class, String.class);
                    generatePNG.setAccessible(true);
                    generatePNG.invoke(lmb, (Drawable) lc, t.getPath());
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(LogoMenuBarTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        th.start();

        robot.delay(5000);
        pressKey(robot, new int[]{KeyEvent.VK_ENTER}, 100);

        assertTrue(t.isFile());
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
