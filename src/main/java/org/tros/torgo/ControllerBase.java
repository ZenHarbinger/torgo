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
package org.tros.torgo;

import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.util.xml.XElement;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.interpreter.Scope;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.torgo.swing.AboutWindow;
import org.tros.torgo.swing.Localization;
import org.tros.torgo.swing.TorgoMenuBar;
import org.tros.utils.swing.NamedWindow;
import org.tros.utils.AutoResetEvent;
import org.tros.utils.CoverageIgnore;
import org.tros.utils.ImageUtils;
import org.tros.utils.PathUtils;

/**
 * The main application. Controls GUI and interpreting process.
 *
 * @author matta
 */
public abstract class ControllerBase implements Controller {

    public static final String ABOUT_MENU_TORGO_ICON = "torgo-16x16.png";

    protected final EventListenerSupport<InterpreterListener> listeners
            = EventListenerSupport.create(InterpreterListener.class);
    protected final EventListenerSupport<ControllerListener> controllerListeners
            = EventListenerSupport.create(ControllerListener.class);
    protected final AutoResetEvent step;
    protected final AtomicBoolean isStepping;

    protected TorgoScreen torgoCanvas;
    protected TorgoTextConsole torgoPanel;

    private final ArrayList<JCheckBoxMenuItem> viz = new ArrayList<>();
    private InterpreterThread interp;
    private String filename;
    private CControl dockControl;
    private JFrame window;

    /**
     * Hidden Constructor.
     */
    protected ControllerBase() {
        step = new AutoResetEvent(false);
        isStepping = new AtomicBoolean(false);
    }

    /**
     * Add a listener.
     *
     * @param listener the listener to add.
     */
    @Override
    public void addInterpreterListener(InterpreterListener listener) {
        listeners.addListener(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener the listener to remove.
     */
    @Override
    public void removeInterpreterListener(InterpreterListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Add a listener.
     *
     * @param listener the listener to add.
     */
    @Override
    public void addControllerListener(ControllerListener listener) {
        controllerListeners.addListener(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener the listener to remove.
     */
    @Override
    public void removeControllerListener(ControllerListener listener) {
        controllerListeners.removeListener(listener);
    }

    /**
     * Get the console component for user I/O.
     *
     * @param app the the application.
     * @return the console component for user I/O.
     */
    protected abstract TorgoTextConsole createConsole(Controller app);

    /**
     * Get a canvas for drawing to the screen. This can be null. If this is
     * null, the canvas portion of the window will not be loaded.
     *
     * @param console the console component for user I/O.
     * @return a screen.
     */
    protected abstract TorgoScreen createCanvas(TorgoTextConsole console);

    /**
     * Create an interpreter thread for the desired language.
     *
     * @param source the source to interpret.
     * @return a thread for interpreting source.
     */
    protected abstract InterpreterThread createInterpreterThread(String source);

    /**
     * Wrapper class.
     */
    public static class TorgoSingleDockable extends DefaultSingleCDockable {

        /**
         * Constructor.
         *
         * @param title the title of the window.
         * @param panel the component that goes in the dock.
         */
        public TorgoSingleDockable(String title, final Component panel) {
            super(title);
            super.setTitleText(Localization.getLocalizedString("Docking" + title));
            super.add(panel);
        }
    }

    /**
     * Initialize the window. This is called here from run() and not the
     * constructor so that the Service Provider doesn't load up all of the
     * necessary resources when the application loads.
     */
    private void initSwing() {
        this.torgoPanel = createConsole((Controller) this);
        this.torgoCanvas = createCanvas(torgoPanel);

        //init the GUI w/ the components...
        Container contentPane = window.getContentPane();
        JToolBar tb = createToolBar();
        if (tb != null) {
            contentPane.add(tb, BorderLayout.NORTH);
        }

        dockControl = new CControl(window);
        window.add(dockControl.getContentArea(), BorderLayout.CENTER);
        final ArrayList<String> presetFilter = new ArrayList<>();
        if (torgoCanvas != null) {
            presetFilter.add("Display");
        }
        torgoPanel.getTorgoComponents().forEach((pair) -> {
            presetFilter.add(pair.left);
        });
        bibliothek.util.Filter<String> filter = presetFilter::contains;
        dockControl.addSingleDockableFactory(filter, (String id) -> {
            TorgoSingleDockable ret = null;
            if ("Display".equals(id)) {
                ret = new TorgoSingleDockable(id, torgoCanvas.getComponent());
            } else {
                for (ImmutablePair<String, Component> pair : torgoPanel.getTorgoComponents()) {
                    if (pair.left.equals(id)) {
                        ret = new TorgoSingleDockable(pair.left, pair.right);
                    }
                }
            }
            if (ret != null) {
                ImageIcon icon = ImageUtils.getIcon("layouts/" + ret.getUniqueId().toLowerCase() + "-24x24.png");
                ret.setTitleIcon(icon);
            }
            return ret;
        });

        // Try to load a saved layout.
        // If no layout exists or it fails, load from CLASSPATH/resources.
        // If that fails, dynamically generate something.
        XElement elem = DockingFrameFactory.read(getLang());
        if (elem == null) {
            elem = DockingFrameFactory.createLayout(torgoCanvas != null ? torgoCanvas.getComponent() : null, torgoPanel.getTorgoComponents());
        }
        dockControl.readXML(elem);

        JMenuBar mb = createMenuBar();
        if (mb == null) {
            mb = new TorgoMenuBar(window, this);
        }
        window.setJMenuBar(mb);
        JMenu helpMenu = new JMenu(Localization.getLocalizedString("HelpMenu"));
        JMenuItem aboutMenu = new JMenuItem(Localization.getLocalizedString("HelpAbout"));
        aboutMenu.setIcon(ImageUtils.getIcon(ABOUT_MENU_TORGO_ICON));

        aboutMenu.addActionListener((ActionEvent ae) -> {
            AboutWindow aw = new AboutWindow();
            aw.setVisible(true);
        });
        JMenuItem updateMenu = new JMenuItem(Localization.getLocalizedString("HelpCheckForUpdate"));
        updateMenu.addActionListener((ActionEvent e) -> {
            Thread t = new Thread(() -> {
                final UpdateChecker uc = new UpdateChecker();
                if (uc.hasUpdate()) {
                    SwingUtilities.invokeLater(() -> {
                        ImageIcon ico = Main.getIcon();
                        int showConfirmDialog = JOptionPane.showConfirmDialog(
                                window,
                                MessageFormat.format("{1}:\n{0}\n{2}",
                                        uc.getUpdateVersion(),
                                        Localization.getLocalizedString("AboutUpdateAvailable"),
                                        Localization.getLocalizedString("AboutViewUpdate")),
                                Localization.getLocalizedString("AboutUpdateAvailable"),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                ico);
                        if (showConfirmDialog == JOptionPane.YES_OPTION) {
                            AboutWindow.goToURI(UpdateChecker.UPDATE_ADDRESS);
                        }
                    });
                }
            });
            t.setDaemon(true);
            t.start();
        });

        helpMenu.add(aboutMenu);
        helpMenu.add(updateMenu);

        JMenu vizMenu = new JMenu(Localization.getLocalizedString("VisualizationMenu"));
        TorgoToolkit.getVisualizers().stream().map((name) -> new JCheckBoxMenuItem(name)).map((item) -> {
            viz.add(item);
            return item;
        }).forEachOrdered((item) -> {
            vizMenu.add(item);
        });
        if (vizMenu.getItemCount() > 0) {
            mb.add(vizMenu);
        }

        mb.add(helpMenu);
        window.setJMenuBar(mb);

        window.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            /**
             * We only care if the window is closing so we can kill the
             * interpreter thread and save the layout.
             *
             * @param e the window closing event.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                stopInterpreter();
                try {
                    String layoutFile = PathUtils.getApplicationConfigDirectory(TorgoToolkit.getBuildInfo()) + java.io.File.separatorChar + getLang() + "-layout.xml";
                    dockControl.writeXML(new java.io.File(layoutFile));
                } catch (IOException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).warn(null, ex);
                }
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
    }

    /**
     * Create a tool bar for the application. This can return null. If null is
     * returned, then there is no tool bar added.
     *
     * @return a new JToolBar
     */
    protected abstract JToolBar createToolBar();

    /**
     * Create a menu bar for the application. This can return null. If null is
     * returned, then there is no menu bar added.
     *
     * @return a new JMenuBar
     */
    protected abstract JMenuBar createMenuBar();

    /**
     * Create a new window.
     */
    @Override
    public void newFile() {
        if (this.window.isVisible()) {
            filename = null;
            window.setTitle("Torgo [" + getLang() + "] - " + Localization.getLocalizedString("UntitledLabel"));

            init();
        }
    }

    protected String getWindowName() {
        return this.getClass().getName();
    }

    /**
     * Sets up the environment.
     */
    @Override
    public final void run() {
        this.window = new NamedWindow(getWindowName());
        Main.loadIcon(this.window);

        initSwing();
        this.window.setVisible(true);

        SwingUtilities.invokeLater(() -> {
//            newFile();
            runHelper();
        });
    }

    /**
     * Helper method called during run().
     */
    @CoverageIgnore
    protected void runHelper() {
    }

    /**
     * Initialize the GUI back to initial state.
     */
    private void init() {
        stopInterpreter();
        torgoPanel.reset();
        if (torgoCanvas != null) {
            torgoCanvas.reset();
        }
    }

    /**
     * Get the current open file.
     *
     * @return the file that was opened.
     */
    @Override
    public File getFile() {
        return filename == null ? null : new File(filename);
    }

    protected FileFilter getFilter() {
        return new FileNameExtensionFilter(getLang(), getLang());
    }

    @Override
    public void openFile(File file) {
        try {
            openFile(new URL(file.toString()));
        } catch (MalformedURLException ex) {
            init();
            if (file.exists()) {
                StringWriter writer = new StringWriter();
                try (FileInputStream fis = new FileInputStream(file)) {
                    IOUtils.copy(fis, writer, "utf-8");
                } catch (IOException ex2) {
                }
                this.setSource(writer.toString());
            }
            //handle windows, jar, and linux path.  Not sure if necessary, but should work.
            String toSplit = file.getAbsolutePath().replace("/", "|").replace("\\", "|");//.split("|");
            String[] split = toSplit.split("\\|");
            this.window.setTitle("Torgo [" + getLang() + "] - " + split[split.length - 1]);
            filename = file.getAbsolutePath();
        }
    }

    /**
     * Open a file based on URL.
     *
     * @param file the file to open.
     */
    @Override
    public void openFile(URL file) {
        try {
            init();
            StringWriter writer = new StringWriter();
            IOUtils.copy(file.openStream(), writer, "utf-8");
            this.setSource(writer.toString());
            //handle windows, jar, and linux path.  Not sure if necessary, but should work.
            String toSplit = file.getFile().replace("/", "|").replace("\\", "|");//.split("|");
            String[] split = toSplit.split("\\|");
            this.window.setTitle("Torgo [" + getLang() + "] - " + split[split.length - 1]);
            filename = file.toString();
        } catch (IOException ex) {
            init();
            org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).fatal(null, ex);
        }
    }

    /**
     * Open a file.
     */
    @CoverageIgnore
    @Override
    public void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(getFilter());
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
    @CoverageIgnore
    @Override
    public void saveFileAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(getFilter());
        chooser.setMultiSelectionEnabled(false);
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(ControllerBase.class);
        chooser.setCurrentDirectory(new File(prefs.get(ControllerBase.class.getName() + "-working-directory", ".")));

        int result = chooser.showSaveDialog(window);

        if (result == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getPath();
            String extension = "." + getLang();
            if (!filename.endsWith(extension)) {
                filename = filename + extension;
            }
            prefs.put(ControllerBase.class.getName() + "-working-directory", chooser.getSelectedFile().getParent());
            saveFile();
        }
    }

    /**
     * Save the script.
     */
    @CoverageIgnore
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
            window.setTitle("Torgo [" + getLang() + "] - " + filename);
            out.flush();
        } catch (Exception ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).fatal(null, ex);
        }
    }

    /**
     * Print (unused).
     */
    @Override
    public void printCanvas() {
        org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).debug("printCanvas() called");
        /*
         PrinterJob printJob = PrinterJob.getPrinterJob();
         PageFormat pageFormat = printJob.defaultPage();
         printJob.setPrintable(torgoCanvas, pageFormat);
         */
    }

    @Override
    public void enable(String name) {
        viz.stream().filter((item) -> (item.getText().equals(name))).forEachOrdered((item) -> {
            item.setState(true);
        });
    }

    @Override
    public void disable(String name) {
        viz.stream().filter((item) -> (item.getText().equals(name))).forEachOrdered((item) -> {
            item.setState(false);
        });
    }

    /**
     * Instantiate/Run the interpreter.
     */
    @Override
    public void startInterpreter() {
        String source = torgoPanel.getSource();
        interp = createInterpreterThread(source);

        viz.stream().filter((item) -> (item.getState())).forEachOrdered((item) -> {
            TorgoToolkit.getVisualization(item.getText()).create().watch(this.getLang(), this, interp);
        });

        for (InterpreterListener l : listeners.getListeners()) {
            interp.addInterpreterListener(l);
        }

        interp.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
            }

            @Override
            public void finished() {
            }

            @Override
            public void error(Exception e) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).fatal(null, e);
            }

            @Override
            public void message(String msg) {
                torgoPanel.appendToOutputTextArea(msg);
            }

            @Override
            public void currStatement(CodeBlock block, Scope scope) {
            }
        });

        controllerListeners.fire().onStartInterpreter();
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

        viz.stream().filter((item) -> (item.getState())).forEachOrdered((item) -> {
            TorgoToolkit.getVisualization(item.getText()).create().watch(this.getLang(), this, interp);
        });

        for (InterpreterListener l : listeners.getListeners()) {
            interp.addInterpreterListener(l);
        }

        interp.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
            }

            @Override
            public void finished() {
                torgoPanel.highlight(-1, 0, 0);
            }

            @Override
            public void error(Exception e) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).fatal(null, e);
            }

            @Override
            public void message(String msg) {
                torgoPanel.appendToOutputTextArea(msg);
            }

            @Override
            public void currStatement(CodeBlock block, Scope scope) {
                try {
                    if (isStepping.get()) {
                        step.waitOne();
                    }
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).fatal(null, ex);
                }
                int line = block.getParserRuleContext().getStart().getLine();
                int start = block.getParserRuleContext().getStart().getStartIndex();
                int end = block.getParserRuleContext().getStart().getStopIndex();
                torgoPanel.highlight(line, start, end);
            }
        });

        controllerListeners.fire().onDebugInterpreter();
        interp.start();
    }

    /**
     * Allows stepping through interpreter commands one-at-a-time.
     */
    @Override
    public void stepOver() {
        controllerListeners.fire().onStepOver();
        step.set();
    }

    /**
     * Pause running the interpreter. Used in debug mode.
     */
    @Override
    public void pauseInterpreter() {
        controllerListeners.fire().onPauseInterpreter();
        isStepping.set(true);
    }

    /**
     * Resume the interpreter. Used in debug mode.
     */
    @Override
    public void resumeInterpreter() {
        controllerListeners.fire().onResumeInterpreter();
        isStepping.set(false);
        step.set();
    }

    /**
     * Stop the interpreter.
     */
    @Override
    public void stopInterpreter() {
        if (interp != null) {
            interp.halt();
            step.set();
            controllerListeners.fire().onStopInterpreter();
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
     * @param command a piece of interpreter source to insert.
     */
    @Override
    public void insertCommand(String command) {
        torgoPanel.insertIntoSource(command);
    }

    /**
     * Set the source of the input pane.
     *
     * @param src the source to interpret.
     */
    @Override
    public void setSource(String src) {
        torgoPanel.setSource(src);
    }

    @Override
    public String getSource() {
        return torgoPanel.getSource();
    }

    /**
     * Return the current GUI window. This is made available for setting parents
     * for dialog windows.
     *
     * @return the current GUI window.
     */
    protected final JFrame getWindow() {
        return this.window;
    }
}
