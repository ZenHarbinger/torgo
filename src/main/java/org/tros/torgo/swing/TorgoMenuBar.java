/*
 * Copyright 2015-2016 Matthew Aguirre
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

import org.tros.torgo.Controller;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.tros.torgo.ControllerListener;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import org.tros.utils.logging.LogConsole;

/**
 * TODO: detect "pause" and "debug" across controllers...
 *
 * @author matta
 */
public class TorgoMenuBar extends JMenuBar implements ControllerListener {

    private final JMenu interpreterMenu;
    private final JMenuItem fileStart;
    private final JMenuItem fileStop;
    private final JMenuItem fileDebug;
    private final JMenuItem filePause;
    private final JMenuItem fileStep;

    private final JMenu languagesMenu;

    private final JMenu fileMenu;
    private final JMenuItem fileNew;
    private final JMenuItem fileOpen;
    private final JMenuItem fileClose;
    private final JMenuItem fileSave;
    private final JMenuItem fileSaveAs;
    private final JMenuItem fileQuit;
    private final JMenuItem logConsole;

    protected final Controller controller;
    protected final Component parent;

    private final AtomicBoolean paused;
    private final AtomicBoolean debugging;

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoMenuBar(Component parent, final Controller controller) {
        this.controller = controller;
        this.parent = parent;
        this.controller.addControllerListener((ControllerListener) this);

        interpreterMenu = new JMenu("Interpreter");
        fileStart = new JMenuItem("Start");
        fileStop = new JMenuItem("Stop");
        fileDebug = new JMenuItem("Debug");
        filePause = new JMenuItem("Pause");
        fileStep = new JMenuItem("Step");

        languagesMenu = new JMenu("Switch Language");
        for (final String lang : TorgoToolkit.getToolkits()) {
            if (!lang.equals(controller.getLang())) {
                JMenuItem langItem = new JMenuItem(lang);
                languagesMenu.add(langItem);
                langItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoToolkit.class);
                        prefs.put("lang", lang);
                        controller.close();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Controller controller = TorgoToolkit.getController(lang);
                                if (controller != null) {
                                    controller.run();
                                    controller.newFile();
                                }
                            }
                        });
                    }
                });
            }
        }

        fileMenu = new JMenu(Localization.getLocalizedString("FileMenu"));
        fileNew = new JMenuItem(Localization.getLocalizedString("FileNew"));
        fileClose = new JMenuItem(Localization.getLocalizedString("FileClose"));
        fileSave = new JMenuItem(Localization.getLocalizedString("FileSave"));
        fileSaveAs = new JMenuItem(Localization.getLocalizedString("FileSaveAs"));
        fileOpen = new JMenuItem(Localization.getLocalizedString("FileOpen"));
        fileQuit = new JMenuItem(Localization.getLocalizedString("FileQuit"));
        logConsole = new JMenuItem("View Log Console");

        debugging = new AtomicBoolean(false);
        paused = new AtomicBoolean(false);

        try {
            add(setupFileMenu());
            add(setupInterpreterMenu());
        } catch (IOException ex) {
            Logger.getLogger(TorgoMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private JMenu setupInterpreterMenu() throws IOException {
        java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("projectui/runProject.png");
        ImageIcon ico = new ImageIcon(resources.nextElement());
        fileStart.setIcon(ico);

        fileStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (paused.get()) {
                    controller.resumeInterpreter();
                } else {
                    controller.startInterpreter();
                }
            }
        });

        resources = ClassLoader.getSystemClassLoader().getResources("projectui/stop.png");
        ico = new ImageIcon(resources.nextElement());
        fileStop.setIcon(ico);
        fileStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.stopInterpreter();
            }
        });

        resources = ClassLoader.getSystemClassLoader().getResources("debugging/debugProject.png");
        ico = new ImageIcon(resources.nextElement());
        fileDebug.setIcon(ico);
        fileDebug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.debugInterpreter();
            }
        });

        resources = ClassLoader.getSystemClassLoader().getResources("debugging/actions/Pause.png");
        ico = new ImageIcon(resources.nextElement());
        filePause.setIcon(ico);
        filePause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pauseInterpreter();
            }
        });

        resources = ClassLoader.getSystemClassLoader().getResources("debugging/actions/StepOver.png");
        ico = new ImageIcon(resources.nextElement());
        fileStep.setIcon(ico);
        fileStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stepOver();
            }
        });

        if (languagesMenu.getItemCount() > 0) {
            interpreterMenu.add(languagesMenu);
            interpreterMenu.addSeparator();
        }
        interpreterMenu.add(fileStart);
        interpreterMenu.add(fileStop);
        interpreterMenu.add(fileDebug);
        interpreterMenu.add(filePause);
        interpreterMenu.add(fileStep);

        fileStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
//        fileStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.VK_SHIFT));
        fileDebug.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        fileStep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

        fileStop.setEnabled(false);
        fileStep.setEnabled(false);
        filePause.setEnabled(false);

        interpreterMenu.setMnemonic('I');

        controller.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
                fileDebug.setEnabled(false);
                fileStart.setEnabled(false);
                fileStop.setEnabled(true);
                paused.set(false);
            }

            @Override
            public void finished() {
                controller.resumeInterpreter();
                fileStep.setEnabled(false);
                fileDebug.setEnabled(true);
                fileStart.setEnabled(true);
                fileStop.setEnabled(false);
                paused.set(false);
                debugging.set(false);
                filePause.setEnabled(false);
            }

            @Override
            public void error(Exception e) {
                controller.resumeInterpreter();
                fileStep.setEnabled(false);
                fileStart.setEnabled(true);
                fileStop.setEnabled(false);
                paused.set(false);
                debugging.set(false);
            }

            @Override
            public void message(String msg) {
            }

            @Override
            public void currStatement(CodeBlock block, Scope scope) {
            }
        });

        return interpreterMenu;
    }

    /**
     * Initializer.
     *
     * @return
     */
    private JMenu setupFileMenu() throws IOException {
        LogConsole.CONSOLE.setVisible(false);

        java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("projectui/newFile.png");
        ImageIcon ico = new ImageIcon(resources.nextElement());
        fileNew.setIcon(ico);

        resources = ClassLoader.getSystemClassLoader().getResources("projectui/open.png");
        ico = new ImageIcon(resources.nextElement());
        fileOpen.setIcon(ico);

        resources = ClassLoader.getSystemClassLoader().getResources("actions/save.png");
        ico = new ImageIcon(resources.nextElement());
        fileSave.setIcon(ico);

        resources = ClassLoader.getSystemClassLoader().getResources("profiler/actions/icons/saveAs.png");
        ico = new ImageIcon(resources.nextElement());
        fileSaveAs.setIcon(ico);

        fileNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.newFile();
            }
        });
        fileOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.openFile();
            }
        });
        fileClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.close();
            }
        });
        fileSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.saveFile();
            }
        });
        fileSaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.saveFileAs();
            }
        });
        fileQuit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        logConsole.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                LogConsole.CONSOLE.setVisible(true);
            }
        });

        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        fileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        logConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));

        fileNew.setMnemonic('N');
        fileOpen.setMnemonic('O');
        fileClose.setMnemonic('C');
        fileSave.setMnemonic('S');
        fileSaveAs.setMnemonic('A');
        fileQuit.setMnemonic('Q');
        logConsole.setMnemonic('L');

        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.addSeparator();
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(logConsole);
        fileMenu.addSeparator();
        fileMenu.add(fileQuit);

        fileMenu.setMnemonic('F');

        return (fileMenu);
    }

    @Override
    public void onStartInterpreter() {
        if (debugging.get()) {
            filePause.setEnabled(true);
            fileStart.setEnabled(false);
        }
        fileStep.setEnabled(false);
    }

    @Override
    public void onStopInterpreter() {
    }

    @Override
    public void onDebugInterpreter() {
        filePause.setEnabled(true);
        debugging.set(true);
    }

    @Override
    public void onStepOver() {
    }

    @Override
    public void onPauseInterpreter() {
        paused.set(true);
        fileStart.setEnabled(true);
        filePause.setEnabled(false);
        fileStep.setEnabled(true);
    }

    @Override
    public void onResumeInterpreter() {
        if (debugging.get()) {
            filePause.setEnabled(true);
            fileStart.setEnabled(false);
        }
        fileStep.setEnabled(false);
    }
}
