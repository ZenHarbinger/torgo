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
package org.tros.torgo.viz;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.logo.LogoControllerTest;
import static org.junit.Assert.*;
import org.tros.logo.LogoController;
import org.tros.logo.swing.LogoMenuBar;
import org.tros.torgo.TorgoInfo;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.viz.StackView.SliceWatchFrame;
import org.tros.torgo.viz.StackView.StackViewWindow;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class StackViewTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(StackViewTest.class.getName());
    }

    public StackViewTest() {
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
    public void stackViewLoggerTest() {
        stackViewLoggerTest("lexical-logo");
        stackViewLoggerTest("dynamic-logo");
    }

    /**
     * Test of create method, of class TraceLogger.
     *
     * @param lang
     */
    public void stackViewLoggerTest(String lang) {
        LOGGER.info("stackViewLoggerTest");
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        boolean checked = prefs.getBoolean("wait-for-repaint", true);
        prefs.putBoolean("wait-for-repaint", true);
        LogoController controller = (LogoController) TorgoToolkit.getController(lang);
        controller.run();
        controller.newFile();
        assertEquals(lang, controller.getLang());

        controller.enable("StackView");

        String[] files = new String[]{
            "logo/examples/antlr/tree2.txt"
        };

        final AtomicBoolean foundStackWindow = new AtomicBoolean(false);
        final AtomicBoolean bool2 = new AtomicBoolean(false);
        final AtomicBoolean started = new AtomicBoolean(false);
        final AtomicBoolean finished = new AtomicBoolean(false);

        for (String file : files) {
            Logger.getLogger(LogoControllerTest.class.getName()).log(Level.INFO, file);
            controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));

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
                    Window[] windows = Window.getWindows();
                    for (Window w : windows) {
                        if (!bool2.get() && StackViewWindow.class.isAssignableFrom(w.getClass())) {
                            StackViewWindow svw = (StackViewWindow) w;
                            JRootPane jrp = (JRootPane) svw.getComponent(0);
                            JPanel contentPane = (JPanel) jrp.getContentPane();
                            JPanel contentPane2 = (JPanel) contentPane.getComponent(0);
                            for (Component c : contentPane2.getComponents()) {
                                if (JScrollPane.class.isAssignableFrom(c.getClass())) {
                                    JScrollPane jsp = (JScrollPane) c;
                                    JViewport component = (JViewport) jsp.getComponent(0);
                                    JList jlist = (JList) component.getComponent(0);
                                    DefaultListModel model = (DefaultListModel) jlist.getModel();
                                    if (model.size() >= 2) {
                                        foundStackWindow.set(true);
                                        try {
                                            Point locationOnScreen = jlist.getLocationOnScreen();
                                            System.out.println(locationOnScreen);
                                            Robot robot = new Robot();
                                            robot.mouseMove(locationOnScreen.x + 10, locationOnScreen.y + 30);
                                            robot.mousePress(InputEvent.BUTTON1_MASK);
                                            robot.delay(10);
                                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                            robot.delay(20);
                                            robot.mousePress(InputEvent.BUTTON1_MASK);
                                            robot.delay(10);
                                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                                            robot.mouseMove(locationOnScreen.x + 100, locationOnScreen.y + 60);
                                            robot.mouseMove(locationOnScreen.x - 10000, locationOnScreen.y - 10000);
                                        } catch (AWTException ex) {
                                            Logger.getLogger(StackViewTest.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            }
                        }
                        if (!bool2.get() && SliceWatchFrame.class.isAssignableFrom(w.getClass())) {
                            try {
                                SliceWatchFrame swf = (SliceWatchFrame) w;
                                System.out.println(swf.item.getToolTip());
                                bool2.set(true);
                            } catch(Exception ex) {
                            }
                        }
                    }
                }
            });

            controller.startInterpreter();

            try {
                while (!foundStackWindow.get() || !bool2.get()) {
                    Thread.sleep(10);
                }
                controller.stopInterpreter();
                while (!finished.get()) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertTrue(started.get());
            assertTrue(finished.get());
            assertTrue(foundStackWindow.get());
            assertTrue(bool2.get());
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
