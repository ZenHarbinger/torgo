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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.LayeredHighlighter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.TorgoTextConsole;
import org.tros.utils.ImageUtils;

/**
 * Main display of text io.
 *
 * @author matta
 */
public class TorgoUserInputPanel implements TorgoTextConsole {

    public static final String DEBUG_ICON = "debugging/breakpointsView/Breakpoint.png";
    private static final float DEFAULT_FONT_SIZE = 12;
    private static final int FONT_INCREMENT_SIZE = 1;
    private static final int FONT_MAX_SIZE = 50;
    private static final int FONT_MIN_SIZE = 5;

    protected final Controller controller;

    private final RSyntaxTextArea inputTextArea;
    private final Gutter gutter;

    private final JConsole outputTextArea;
    private final JTabbedPane tabs;

    private final LayeredHighlighter.LayerPainter defaultHighlighter;
    private final LayeredHighlighter.LayerPainter breakpointHighlighter;
    private final java.util.prefs.Preferences prefs;
    private final ZoomableMixin zoom;

    private class ZoomableMixin extends ZoomableComponent {

        ZoomableMixin(JComponent component) {
            super(component);
        }

        @Override
        protected void zoomIn() {
            Font font1 = inputTextArea.getFont();
            float size = (float) (font1.getSize2D() + FONT_INCREMENT_SIZE);
            if (size <= FONT_MAX_SIZE) {
                font1 = font1.deriveFont(size);
                inputTextArea.setFont(font1);
                outputTextArea.setFont(font1);
                prefs.putFloat("font-size", size);
            }
        }

        @Override
        protected void zoomOut() {
            Font font1 = inputTextArea.getFont();
            float size = (float) (font1.getSize2D() - FONT_INCREMENT_SIZE);
            if (size >= FONT_MIN_SIZE) {
                font1 = font1.deriveFont(size);
                inputTextArea.setFont(font1);
                outputTextArea.setFont(font1);
                prefs.putFloat("font-size", size);
            }
        }

        @Override
        protected void zoomReset() {
            Font font1 = inputTextArea.getFont();
            font1 = font1.deriveFont(DEFAULT_FONT_SIZE);
            inputTextArea.setFont(font1);
            outputTextArea.setFont(font1);
            prefs.putFloat("font-size", DEFAULT_FONT_SIZE);
        }
    }

    /**
     * Constructor.
     *
     * @param controller the controller.
     * @param name the nae.
     * @param editable if it is editable.
     * @param syntax the syntax.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoUserInputPanel(Controller controller, String name, boolean editable, String syntax) {
        this.controller = controller;
        defaultHighlighter = DefaultHighlighter.DefaultPainter;
        breakpointHighlighter = new DefaultHighlighter.DefaultHighlightPainter(Color.PINK);

        //SOURCE
        JPanel inputTab = new JPanel();
        inputTab.setLayout(new BorderLayout());

        inputTextArea = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        inputTextArea.setAntiAliasingEnabled(true);
        inputTextArea.setCodeFoldingEnabled(true);
        inputTextArea.setSyntaxEditingStyle(syntax);
        RTextScrollPane scrollPane = new org.fife.ui.rtextarea.RTextScrollPane(inputTextArea);
        scrollPane.setIconRowHeaderEnabled(true);
        gutter = scrollPane.getGutter();
        gutter.setBookmarkIcon(ImageUtils.getIcon(DEBUG_ICON));
        gutter.setBookmarkingEnabled(true);

        prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoUserInputPanel.class);

        outputTextArea = new JConsole();
        outputTextArea.setEditable(editable);
        //get default pref
        //update prefs

        Font font = new Font(Font.MONOSPACED, Font.PLAIN, (int) prefs.getFloat("font-size", DEFAULT_FONT_SIZE));
        inputTextArea.setFont(font);
        outputTextArea.setFont(font);
        zoom = new ZoomableMixin(scrollPane);

        inputTab.add(scrollPane, BorderLayout.CENTER);

        //TABS
        tabs = new JTabbedPane();
        tabs.add(name, inputTab);

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

    public int getInputTextAreaSize() {
        return inputTextArea.getFont().getSize();
    }

    public RSyntaxTextArea getInputTextArea() {
        return inputTextArea;
    }

    public void zoomInTest() {
        zoom.zoomIn();
    }

    public void zoomOutTest() {
        zoom.zoomOut();
    }

    public void zoomResetTest() {
        zoom.zoomReset();
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
     * @param what text to append to the output text area.
     */
    @Override
    public void appendToOutputTextArea(String what) {
        what = what.trim();
        outputTextArea.println(what);
    }

    /**
     * Get the source to interpret.
     *
     * @return the source to interpret.
     */
    @Override
    public String getSource() {
        return (inputTextArea.getText());
    }

    /**
     * Set the source to interpret.
     *
     * @param source the source to interpret.
     */
    @Override
    public void setSource(String source) {
        source = source.replace("\r", "");
        inputTextArea.setText(source);
    }

    /**
     * Append a string to the source.
     *
     * @param source text to append to the source to interpret.
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
     * @param source text to insert into the source to interpret.
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
     * @param position position to move to.
     */
    @Override
    public void gotoPosition(int position) {
        try {
            inputTextArea.setCaretPosition(position);
        } catch (Exception ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).warn(null, ex);
        }
    }

    /**
     * Highlight a section of the source. Check for set breakpoints from the
     * RTextScrollPane object and pause there.
     *
     * @param line the line.
     * @param startChar the start line position.
     * @param endChar the end line position.
     */
    @Override
    public void highlight(int line, int startChar, int endChar) {
        boolean paused = false;
        for (GutterIconInfo gii : gutter.getBookmarks()) {
            int offset = 0;
            try {
                //Not sure why a -1 here works, needs more testing.
                offset = inputTextArea.getLineStartOffset(line - 1);
            } catch (BadLocationException ex) {
            }
            if (gii.getMarkedOffset() == offset) {
                controller.pauseInterpreter();
                paused = true;
                gotoPosition(startChar);
                Highlighter hl = inputTextArea.getHighlighter();
                hl.removeAllHighlights();
                try {
                    hl.addHighlight(startChar, endChar + 1, breakpointHighlighter);
                } catch (BadLocationException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).fatal(null, ex);
                }
            }
        }
        if (!paused && line > 0) {
            Highlighter hl = inputTextArea.getHighlighter();
            hl.removeAllHighlights();
            try {
                hl.addHighlight(startChar, endChar + 1, defaultHighlighter);
                gotoPosition(startChar);
            } catch (BadLocationException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoUserInputPanel.class).fatal(null, ex);
            }
        }
    }

    /**
     * Get the swing component(s) of the object.
     *
     * @return the swing component(s) of the object.
     */
    @Override
    public ArrayList<ImmutablePair<String, Component>> getTorgoComponents() {
        ArrayList<ImmutablePair<String, Component>> ret = new ArrayList<>();
        ret.add(new ImmutablePair("Input", tabs));
        ret.add(new ImmutablePair("Output", outputTextArea));
        return ret;
    }
}
