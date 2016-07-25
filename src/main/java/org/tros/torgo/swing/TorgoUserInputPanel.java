/*
 * Copyright 2016 Matthew Aguirre
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.LayeredHighlighter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.TorgoTextConsole;

public abstract class TorgoUserInputPanel extends JPanel implements TorgoTextConsole {

    protected final RSyntaxTextArea inputTextArea;
    protected final RTextScrollPane scrollPane;
    protected final Gutter gutter;

    private final JConsole outputTextArea;
    private final JSplitPane splitPane;
    private final JPanel inputTab;
    private final JTabbedPane tabs;

    public static final String DEBUG_ICON = "debugging/breakpointsView/Breakpoint.png";
    protected final Controller controller;
    
    private final LayeredHighlighter.LayerPainter defaultHighlighter;    
    /**
     * Constructor.
     *
     * @param controller
     * @param name
     * @param editable
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoUserInputPanel(Controller controller, String name, boolean editable) {
        this.controller = controller;
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        defaultHighlighter = DefaultHighlighter.DefaultPainter;

        //SOURCE
        inputTab = new JPanel();
        BorderLayout intputLayout = new BorderLayout();
        inputTab.setLayout(intputLayout);

        inputTextArea = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        inputTextArea.setAntiAliasingEnabled(true);
        inputTextArea.setCodeFoldingEnabled(true);
        scrollPane = new org.fife.ui.rtextarea.RTextScrollPane(inputTextArea);
        scrollPane.setIconRowHeaderEnabled(true);
        gutter = scrollPane.getGutter();
        try {
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(DEBUG_ICON);
            ImageIcon imageIcon = new ImageIcon(resources.nextElement());
            gutter.setBookmarkIcon(imageIcon);
        } catch (IOException ex) {
            Logger.getLogger(TorgoUserInputPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        gutter.setBookmarkingEnabled(true);

        inputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        inputTab.add(scrollPane, BorderLayout.CENTER);

        //TABS
        tabs = new JTabbedPane();
        tabs.add(name, inputTab);

        JPanel output = new JPanel();
        BorderLayout outputLayout = new BorderLayout();
        output.setLayout(outputLayout);

        outputTextArea = new JConsole();
//        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputTextArea.setEditable(editable);
//        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        JLabel messagesLabel = new JLabel(Localization.getLocalizedString("MessagesLabel"));
        JPanel messages = new JPanel();
        messages.setLayout(new BorderLayout());
        messages.add(messagesLabel, BorderLayout.PAGE_START);
        messages.add(outputTextArea, BorderLayout.CENTER);
        output.add(messages, BorderLayout.CENTER);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabs, output);
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoUserInputPanel.class);
        int dividerLocation = prefs.getInt(TorgoUserInputPanel.class.getName() + "divider-location", 0);
        dividerLocation = dividerLocation == 0 ? 400 : dividerLocation;
        splitPane.setDividerLocation(dividerLocation);
        splitPane.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                prefs.putInt(TorgoUserInputPanel.class.getName() + "divider-location", splitPane.getDividerLocation());
            }
        });
        this.add(splitPane);

        controller.addInterpreterListener(new InterpreterListener() {

            /**
             * Clears the highlighted areas.
             */
            @Override
            public void started() {
                Highlighter hl = inputTextArea.getHighlighter();
                inputTextArea.setEditable(false);
                hl.removeAllHighlights();
            }

            /**
             * Clears the highlighted areas.
             */
            @Override
            public void finished() {
                Highlighter hl = inputTextArea.getHighlighter();
                inputTextArea.setEditable(true);
                hl.removeAllHighlights();
            }

            /**
             * Clears the highlighted areas.
             */
            @Override
            public void error(Exception e) {
                Highlighter hl = inputTextArea.getHighlighter();
                inputTextArea.setEditable(true);
                hl.removeAllHighlights();
            }

            /**
             * Append a message to the output area.
             *
             * @param msg
             */
            @Override
            public void message(String msg) {
            }

            @Override
            public void currStatement(CodeBlock block, Scope scope) {
            }
        });
    }

    /**
     * Reset the control to initial state.
     */
    @Override
    public void reset() {
        clearSource();
        clearOutputTextArea();
        gutter.removeAllTrackingIcons();
    }

    /**
     * Clear the output text area.
     */
    @Override
    public void clearOutputTextArea() {
        outputTextArea.clearScreen();
    }

    /**
     * Append text to the output text area.
     *
     * @param what
     */
    @Override
    public void appendToOutputTextArea(String what) {
        what = what.trim();
        outputTextArea.println(what);
    }

    /**
     * Get the source to interpret.
     *
     * @return
     */
    @Override
    public String getSource() {
        return (inputTextArea.getText());
    }

    /**
     * Set the source to interpret.
     *
     * @param source
     */
    @Override
    public void setSource(String source) {
        source = source.replace("\r", "");
        inputTextArea.setText(source);
    }

    /**
     * Append a string to the source.
     *
     * @param source
     */
    @Override
    public void appendToSource(String source) {
        if (inputTextArea.isEditable()) {
            inputTextArea.setText(inputTextArea.getText() + System.getProperty("line.separator") + source);
        }
    }

    /**
     * Insert a string into the source at the cursor.
     *
     * @param source
     */
    @Override
    public void insertIntoSource(String source) {
        if (inputTextArea.isEditable()) {
            int c = 0;
            boolean success = false;
            try {
                if (inputTextArea.getClass().getMethod("getCaretOffsetFromLineStart") != null) {
                    c = ((Integer) inputTextArea.getClass().getMethod("getCaretOffsetFromLineStart").invoke(inputTextArea));
                    success = true;
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).fatal(null, ex);
            }

            if (success) {
                if (c != 0) {
                    source = System.getProperty("line.separator") + source;
                }
                inputTextArea.insert(source, inputTextArea.getCaretPosition());
            } else {
                appendToSource(source);
            }
        }
    }

    /**
     * Clear the source.
     */
    @Override
    public void clearSource() {
        if (inputTextArea.isEditable()) {
            setSource("");
        }
    }

    /**
     * To to a position in the source.
     *
     * @param position
     */
    @Override
    public void gotoPosition(int position) {
        inputTextArea.setCaretPosition(position);
    }

    /**
     * Highlight a section of the source.
     *
     * @param line
     * @param startChar
     * @param endChar
     */
    @Override
    public void highlight(int line, int startChar, int endChar) {
        if (line > 0) {
            Highlighter hl = inputTextArea.getHighlighter();
            hl.removeAllHighlights();
            try {
                hl.addHighlight(startChar, endChar + 1, defaultHighlighter);
                inputTextArea.setCaretPosition(startChar);
            } catch (BadLocationException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).fatal(null, ex);
            }
        }
        for(GutterIconInfo gii : gutter.getBookmarks()) {
            int offset = 0;
            try {
                //Not sure why a -1 here works, needs more testing.
                offset = inputTextArea.getLineStartOffset(line - 1);
            } catch (BadLocationException ex) {
            }
            if (gii.getMarkedOffset() == offset) {
                controller.pauseInterpreter();
                inputTextArea.setCaretPosition(startChar);
                Highlighter hl = inputTextArea.getHighlighter();
                hl.removeAllHighlights();
                try {
                    hl.addHighlight(startChar, endChar + 1, new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
                } catch (BadLocationException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).fatal(null, ex);
                }
            }
        }
    }

    /**
     * Get the swing component of the object.
     *
     * @return
     */
    @Override
    public Component getComponent() {
        return this;
    }
}
