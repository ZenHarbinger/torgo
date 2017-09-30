/*
 * Copyright 2015-2017 Matthew Aguirre
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
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
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
import org.tros.utils.ImageUtils;
import org.tros.utils.logging.LogConsole;

/**
 *
 * @author matta
 */
public class TorgoMenuBar extends JMenuBar implements ControllerListener {

    protected final Controller controller;
    protected final Component parent;

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

    private final AtomicBoolean paused;
    private final AtomicBoolean debugging;

    /**
     * Constructor.
     *
     * @param parent the parent.
     * @param controller the controller.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoMenuBar(Component parent, final Controller controller) {
        this.controller = controller;
        this.parent = parent;
        this.controller.addControllerListener((ControllerListener) this);

        interpreterMenu = new JMenu(Localization.getLocalizedString("InterpreterMenu"));
        fileStart = new JMenuItem(Localization.getLocalizedString("InterpreterStart"));
        fileStop = new JMenuItem(Localization.getLocalizedString("InterpreterStop"));
        fileDebug = new JMenuItem(Localization.getLocalizedString("InterpreterDebug"));
        filePause = new JMenuItem(Localization.getLocalizedString("InterpreterPause"));
        fileStep = new JMenuItem(Localization.getLocalizedString("InterpreterStep"));

        languagesMenu = new JMenu(Localization.getLocalizedString("InterpreterSwitchLanguage"));
        TorgoToolkit.getToolkits().stream().filter((lang) -> (!lang.equals(controller.getLang()))).forEachOrdered((lang) -> {
            JMenuItem langItem = new JMenuItem(lang);
            languagesMenu.add(langItem);
            langItem.addActionListener((ActionEvent ae) -> {
                final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoToolkit.class);
                prefs.put("lang", lang);
                controller.close();
                SwingUtilities.invokeLater(() -> {
                    Controller controller1 = TorgoToolkit.getController(lang);
                    if (controller1 != null) {
                        controller1.run();
                        controller1.newFile();
                        if (controller.getFile() != null) {
                            controller1.openFile(controller.getFile());
                        }
                        controller1.setSource(controller.getSource());
                    }
                });
            });
        });

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
            org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoMenuBar.class).warn(null, ex);
        }
    }

    private JMenu setupInterpreterMenu() throws IOException {
        fileStart.setIcon(ImageUtils.getIcon("projectui/runProject.png"));

        fileStart.addActionListener((ActionEvent e) -> {
            if (paused.get()) {
                controller.resumeInterpreter();
            } else {
                controller.startInterpreter();
            }
        });

        fileStop.setIcon(ImageUtils.getIcon("projectui/stop.png"));
        fileStop.addActionListener((ActionEvent event) -> {
            controller.stopInterpreter();
        });

        fileDebug.setIcon(ImageUtils.getIcon("debugging/debugProject.png"));
        fileDebug.addActionListener((ActionEvent e) -> {
            controller.debugInterpreter();
        });

        filePause.setIcon(ImageUtils.getIcon("debugging/actions/Pause.png"));
        filePause.addActionListener((ActionEvent e) -> {
            controller.pauseInterpreter();
        });

        fileStep.setIcon(ImageUtils.getIcon("debugging/actions/StepOver.png"));
        fileStep.addActionListener((ActionEvent e) -> {
            controller.stepOver();
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
     * @return a new menu.
     */
    private JMenu setupFileMenu() throws IOException {
        LogConsole.CONSOLE.setVisible(false);

        fileNew.setIcon(ImageUtils.getIcon("projectui/newFile.png"));
        fileOpen.setIcon(ImageUtils.getIcon("projectui/open.png"));
        fileSave.setIcon(ImageUtils.getIcon("actions/save.png"));
        fileSaveAs.setIcon(ImageUtils.getIcon("profiler/actions/icons/saveAs.png"));

        fileNew.addActionListener((ActionEvent ae) -> {
            controller.newFile();
        });
        fileOpen.addActionListener((ActionEvent ae) -> {
            controller.openFile();
        });
        fileClose.addActionListener((ActionEvent ae) -> {
            controller.close();
        });
        fileSave.addActionListener((ActionEvent ae) -> {
            controller.saveFile();
        });
        fileSaveAs.addActionListener((ActionEvent ae) -> {
            controller.saveFileAs();
        });
        fileQuit.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });

        logConsole.addActionListener((ActionEvent ae) -> {
            LogConsole.CONSOLE.setVisible(true);
        });

        fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        fileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        logConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

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
