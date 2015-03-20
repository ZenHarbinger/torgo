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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.swing.Localization;
import org.tros.torgo.swing.SwingTextConsole;
import org.tros.torgo.swing.TorgoWindow;
import org.tros.utils.AutoResetEvent;

/**
 * The main application. Controls GUI and interpreting process.
 *
 * @author matta
 */
public abstract class ControllerBase implements Controller {

    private JFrame window;
    protected TorgoScreen torgoCanvas;
    protected SwingTextConsole torgoPanel;
    private InterpreterThread interp;
    private String filename;
    protected final AutoResetEvent step;
    protected final AtomicBoolean isStepping;

    protected final ArrayList<InterpreterListener> listeners = new ArrayList<>();

    /**
     * Add a listener
     *
     * @param listener
     */
    @Override
    public void addInterpreterListener(InterpreterListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a listener
     *
     * @param listener
     */
    @Override
    public void removeInterpreterListener(InterpreterListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Hidden Constructor.
     */
    protected ControllerBase() {
        step = new AutoResetEvent(false);
        isStepping = new AtomicBoolean(false);
    }

    protected abstract SwingTextConsole createConsole(Controller app);

    protected abstract TorgoScreen createCanvas(TorgoTextConsole console);

    protected abstract InterpreterThread createInterpreterThread(String source);

    private void initSwing() {
        this.torgoPanel = createConsole((Controller) this);
        this.torgoCanvas = createCanvas(torgoPanel);

        //init the GUI w/ the components...
        Container contentPane = window.getContentPane();
        JToolBar tb = createToolBar();
        if (tb != null) {
            contentPane.add(tb, BorderLayout.NORTH);
        }

        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoWindow.class);
        if (torgoCanvas != null) {
            final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, torgoCanvas.getComponent(), torgoPanel);
            int dividerLocation = prefs.getInt(ControllerBase.class.getName() + "divider-location", window.getWidth() - 200);
            splitPane.setDividerLocation(dividerLocation);
            splitPane.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                prefs.putInt(ControllerBase.class.getName() + "divider-location", splitPane.getDividerLocation());
            });

            window.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    stopInterpreter();
                }

                @Override
                public void windowClosed(WindowEvent e) {
                }

                @Override
                public void windowIconified(WindowEvent e) {
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                }

                @Override
                public void windowActivated(WindowEvent e) {
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            });
            contentPane.add(splitPane);
        } else {
            contentPane.add(torgoPanel);
        }

        JMenuBar mb = createMenuBar();
        if (mb != null) {
            window.setJMenuBar(mb);
        }
    }

    /**
     * Create a tool bar for the application.
     *
     * @return
     */
    protected abstract JToolBar createToolBar();

    /**
     * Create a menu bar for the application.
     *
     * @return
     */
    protected abstract JMenuBar createMenuBar();

    /**
     * Create a new window.
     */
    @Override
    public void newFile() {
        if (this.window.isVisible()) {
            filename = null;
            window.setTitle("Torgo - " + Localization.getLocalizedString("UntitledLabel"));

            init();
        }
    }

    /**
     * Sets up the environment.
     */
    @Override
    public final void run() {
        this.window = new TorgoWindow((Controller) this);
        initSwing();
        this.window.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            newFile();
            runHelper();
        });
    }

    /**
     * Helper method called during run().
     */
    protected void runHelper() {
    }

    /**
     * Initialize the GUI back to init state.
     */
    private void init() {
        stopInterpreter();
        torgoPanel.reset();
        if (torgoCanvas != null) {
            torgoCanvas.reset();
        }
    }

    /**
     * Open a file based on URL.
     *
     * @param file
     */
    @Override
    public void openFile(URL file) {
        try {
            init();
            StringWriter writer = new StringWriter();
            IOUtils.copy(file.openStream(), writer);
            this.setSource(writer.toString());
            //handle windows, jar, and linux path.  Not sure if necessary, but should work.
            String toSplit = file.getFile().replace("/", "|").replace("\\", "|");//.split("|");
            String[] split = toSplit.split("\\|");
            this.window.setTitle("Torgo - " + split[split.length - 1]);
        } catch (IOException ex) {
            init();
            Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Open a file.
     */
    @Override
    public void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(ControllerBase.class);
        chooser.setCurrentDirectory(new File(prefs.get(ControllerBase.class.getName() + "-working-directory", ".")));

        if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getPath();
            prefs.put(ControllerBase.class.getName() + "-working-directory", chooser.getSelectedFile().getParent());
            openFile(chooser.getSelectedFile());
        }
    }

    /**
     * Save the script as a new file.
     */
    @Override
    public void saveFileAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(ControllerBase.class);
        chooser.setCurrentDirectory(new File(prefs.get(ControllerBase.class.getName() + "-working-directory", ".")));

        int result = chooser.showSaveDialog(window);

        if (result == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getPath();
            prefs.put(ControllerBase.class.getName() + "-working-directory", chooser.getSelectedFile().getParent());
            saveFile();
        }
    }

    /**
     * Save the script.
     */
    @Override
    public void saveFile() {
        if (filename == null) {
            saveFileAs();
            return;
        }

        try (FileOutputStream out = new FileOutputStream(filename)) {
            String source = torgoPanel.getSource();
            byte[] sourceArray = new byte[source.length()];

            for (int i = 0; i < source.length(); i++) {
                sourceArray[i] = (byte) source.charAt(i);
            }

            out.write(sourceArray);
            window.setTitle("Torgo - " + filename);
        } catch (Exception e) {
            Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Print (unused)
     */
    @Override
    public void printCanvas() {
        Logger.getLogger(ControllerBase.class.getName()).log(Level.FINER, "printCanvas() called");
        /*
         PrinterJob printJob = PrinterJob.getPrinterJob();
         PageFormat pageFormat = printJob.defaultPage();
         printJob.setPrintable(torgoCanvas, pageFormat);
         */
    }

    /**
     * Instantiate/Run the interpreter.
     */
    @Override
    public void startInterpreter() {
        String source = torgoPanel.getSource();
        interp = createInterpreterThread(source);

        interp.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
                listeners.stream().forEach((l) -> {
                    l.started();
                });
            }

            @Override
            public void finished() {
                listeners.stream().forEach((l) -> {
                    l.finished();
                });
                torgoPanel.highlight(-1, 0, 0);
            }

            @Override
            public void error(Exception e) {
                listeners.stream().forEach((l) -> {
                    l.error(e);
                });
                Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, e);
            }

            @Override
            public void message(String msg) {
                listeners.stream().forEach((l) -> {
                    l.message(msg);
                });
            }

            @Override
            public void currStatement(String statement, int line, int start, int end) {
            }
        });
        interp.start();
    }

    /**
     * Instantiate an interpreter in debug mode.
     */
    @Override
    public void debugInterpreter() {
        String source = torgoPanel.getSource();
        interp = createInterpreterThread(source);
        step.reset();

        interp.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
                listeners.stream().forEach((l) -> {
                    l.started();
                });
            }

            @Override
            public void finished() {
                listeners.stream().forEach((l) -> {
                    l.finished();
                });
                torgoPanel.highlight(-1, 0, 0);
            }

            @Override
            public void error(Exception e) {
                listeners.stream().forEach((l) -> {
                    l.error(e);
                });
                Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, e);
            }

            @Override
            public void message(String msg) {
                listeners.stream().forEach((l) -> {
                    l.message(msg);
                });
            }

            @Override
            public void currStatement(String statement, int line, int start, int end) {
                try {
                    if (isStepping.get()) {
                        step.waitOne();
                    }
                    //TODO: this needs to be configurable
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, ex);
                }
                torgoPanel.highlight(line, start, end);
            }
        });
        interp.start();
    }

    /**
     * Allows stepping through interpreter commands one-at-a-time.
     */
    @Override
    public void stepOver() {
        step.set();
    }

    @Override
    public void pauseInterpreter() {
        isStepping.set(true);
    }

    @Override
    public void resumeInterpreter() {
        isStepping.set(false);
        step.set();
    }

    /**
     * Stop the interpreter
     */
    @Override
    public void stopInterpreter() {
        if (interp != null) {
            interp.halt();
            step.set();
        }
    }

    /**
     * Close the application.
     */
    @Override
    public void close() {
        window.dispose();
    }

    /**
     * Insert a command into the input pane.
     *
     * @param command
     */
    @Override
    public void insertCommand(String command) {
        torgoPanel.insertIntoSource(command);
    }

    /**
     * Set the source of the input pane.
     *
     * @param src
     */
    @Override
    public void setSource(String src) {
        torgoPanel.setSource(src);
    }

    /**
     * Return the current GUI window.
     *
     * @return
     */
    protected final JFrame getWindow() {
        return this.window;
    }
}
