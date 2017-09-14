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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.tros.torgo.ControllerListener;
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
public class LogoControllerTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(LogoControllerTest.class.getName());
    }

    public LogoControllerTest() {
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
    public void testLexicalScoping() {
        LOGGER.info("lexicalScopingTest");
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        boolean checked = prefs.getBoolean("wait-for-repaint", true);
        prefs.putBoolean("wait-for-repaint", false);
        LexicalLogoController controller = (LexicalLogoController) TorgoToolkit.getController("lexical-logo");

        ControllerListener clistener = new ControllerListener() {
            @Override
            public void onStartInterpreter() {
            }

            @Override
            public void onStopInterpreter() {
            }

            @Override
            public void onDebugInterpreter() {
            }

            @Override
            public void onStepOver() {
            }

            @Override
            public void onPauseInterpreter() {
            }

            @Override
            public void onResumeInterpreter() {
            }
        };
        controller.addControllerListener(clistener);
        controller.removeControllerListener(clistener);

        controller.run();
        controller.newFile();
        assertEquals("lexical-logo", controller.getLang());
        String[] files = new String[]{
            "no-such-file",
            "logo/examples/antlr/dynamic_scope.txt",
            "logo/examples/antlr/example1.txt",
            "logo/examples/antlr/example2.txt",
            "logo/examples/antlr/example3.txt",
            "logo/examples/antlr/example4.txt",
            "logo/examples/antlr/expression.txt",
            //            "logo/examples/antlr/flower.txt",
            "logo/examples/antlr/fractal.txt",
            "logo/examples/antlr/make.txt",
            "logo/examples/antlr/octagon.txt",
            "logo/examples/antlr/procedure1.txt",
            "logo/examples/antlr/procedure2.txt",
            "logo/examples/antlr/random.txt",
            "logo/examples/antlr/recursive1.txt",
            "logo/examples/antlr/recursive2.txt",
            "logo/examples/antlr/repeat.txt",
            "logo/examples/antlr/spiral2.txt",
            "logo/examples/antlr/spiral.txt",
            //            "logo/examples/antlr/tree2.txt",
            //            "logo/examples/antlr/tree.txt",
            //            "logo/examples/tortue/box.logo",
            //            "logo/examples/tortue/canvas-color-test.logo",
            //            "logo/examples/tortue/fail.logo",
            //            "logo/examples/tortue/fibonacci.logo",
            //            "logo/examples/tortue/flower2.logo",
            //            "logo/examples/tortue/flower.logo",
            //            "logo/examples/tortue/Getriebe.logo",
            //            "logo/examples/tortue/grayscale-fun.logo",
            //            "logo/examples/tortue/iris.logo",
            //            "logo/examples/tortue/MetallRahmen.logo",
            //            "logo/examples/tortue/octagon.logo",
            //            "logo/examples/tortue/pretty.logo",
            //            "logo/examples/tortue/snowflake.logo",
            //            "logo/examples/tortue/spiral.logo",
            //            "logo/examples/tortue/spokes.logo",
            "logo/examples/tortue/test.logo",
            "logo/examples/tortue/tortue-text.logo"};

        for (String file : files) {
            LOGGER.info(file);
            try {
                controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));
            } catch (Exception ex) {
                try {
                    controller.openFile(new URL("http://" + file));
                } catch (MalformedURLException ex2) {
                    controller.openFile(new File(file));
                }
            }
            controller.pauseInterpreter();
            controller.stepOver();
            controller.resumeInterpreter();
            controller.getFile();
            controller.insertCommand(("home\n"));
            assertNotNull(controller.getSource());
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
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            controller.removeInterpreterListener(listener);
        }

        controller.close();
        prefs.putBoolean("wait-for-repaint", checked);
    }

    /**
     * Test of createConsole method, of class LogoController.
     */
    @Test
    public void testCreateConsole() {
        LOGGER.info("dynamicScopingTest");
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        boolean checked = prefs.getBoolean("wait-for-repaint", true);
        prefs.putBoolean("wait-for-repaint", false);
        DynamicLogoController controller = (DynamicLogoController) TorgoToolkit.getController("dynamic-logo");

        ControllerListener clistener = new ControllerListener() {
            @Override
            public void onStartInterpreter() {
            }

            @Override
            public void onStopInterpreter() {
            }

            @Override
            public void onDebugInterpreter() {
            }

            @Override
            public void onStepOver() {
            }

            @Override
            public void onPauseInterpreter() {
            }

            @Override
            public void onResumeInterpreter() {
            }
        };
        controller.addControllerListener(clistener);
        controller.removeControllerListener(clistener);

        controller.run();
        controller.newFile();
        assertEquals("dynamic-logo", controller.getLang());
        String[] files = new String[]{
            "no-such-file",
            "logo/examples/antlr/dynamic_scope.txt",
            "logo/examples/antlr/example1.txt",
            "logo/examples/antlr/example2.txt",
            "logo/examples/antlr/example3.txt",
            "logo/examples/antlr/example4.txt",
            "logo/examples/antlr/expression.txt",
            //            "logo/examples/antlr/flower.txt",
            "logo/examples/antlr/fractal.txt",
            "logo/examples/antlr/make.txt",
            "logo/examples/antlr/octagon.txt",
            "logo/examples/antlr/procedure1.txt",
            "logo/examples/antlr/procedure2.txt",
            "logo/examples/antlr/random.txt",
            "logo/examples/antlr/recursive1.txt",
            "logo/examples/antlr/recursive2.txt",
            "logo/examples/antlr/repeat.txt",
            "logo/examples/antlr/spiral2.txt",
            "logo/examples/antlr/spiral.txt",
            //            "logo/examples/antlr/tree2.txt",
            //            "logo/examples/antlr/tree.txt",
            //            "logo/examples/tortue/box.logo",
            //            "logo/examples/tortue/canvas-color-test.logo",
            //            "logo/examples/tortue/fail.logo",
            //            "logo/examples/tortue/fibonacci.logo",
            //            "logo/examples/tortue/flower2.logo",
            //            "logo/examples/tortue/flower.logo",
            //            "logo/examples/tortue/Getriebe.logo",
            //            "logo/examples/tortue/grayscale-fun.logo",
            //            "logo/examples/tortue/iris.logo",
            //            "logo/examples/tortue/MetallRahmen.logo",
            //            "logo/examples/tortue/octagon.logo",
            //            "logo/examples/tortue/pretty.logo",
            //            "logo/examples/tortue/snowflake.logo",
            //            "logo/examples/tortue/spiral.logo",
            //            "logo/examples/tortue/spokes.logo",
            "logo/examples/tortue/test.logo",
            "logo/examples/tortue/tortue-text.logo"
        };

        for (String file : files) {
            Logger.getLogger(LogoControllerTest.class.getName()).log(Level.INFO, file);
            try {
                controller.openFile(ClassLoader.getSystemClassLoader().getResource(file));
            } catch (Exception ex) {
                try {
                    controller.openFile(new URL("http://" + file));
                } catch (MalformedURLException ex2) {
                    controller.openFile(new File(file));
                }
            }
            controller.pauseInterpreter();
            controller.stepOver();
            controller.resumeInterpreter();
            controller.getFile();
            controller.insertCommand(("home\n"));
            assertNotNull(controller.getSource());
            controller.disable("TraceLogger");

            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(LogoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        controller.close();
        prefs.putBoolean("wait-for-repaint", checked);
    }
    
    
}
