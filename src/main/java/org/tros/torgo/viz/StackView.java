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

import java.awt.BorderLayout;
import org.tros.utils.swing.NamedWindow;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.InterpreterVisualization;
import org.tros.torgo.Main;
import org.tros.torgo.interpreter.CodeFunction;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.ScopeListener;

/**
 * Allows viewing a call stack w/ variables as code is executed.
 *
 * @author matta
 */
public class StackView implements InterpreterVisualization {

    /**
     * Represents a CodeBlock in the list.
     */
    public class ScopeItem {

        final private CodeBlock cb;

        /**
         * Constructor.
         *
         * @param cb
         */
        public ScopeItem(CodeBlock cb) {
            super();
            this.cb = cb;
        }

        /**
         * String displayed in the list.
         *
         * @return
         */
        @Override
        public String toString() {
            if (CodeFunction.class.isAssignableFrom(cb.getClass())) {
                return ((CodeFunction)cb).getFunctionName();
            } else {
                return cb.getClass().getSimpleName();
            }
        }

    }

    /**
     * Class to show values of variables in the interpreted code.
     */
    public class ScopeFrame extends NamedWindow {

        private final Scope scope;

        /**
         * Constructor.
         *
         * @param scope
         */
        public ScopeFrame(Scope scope) {
            super("Variable-Watch");
            super.setTitle("Variable Watch");
            this.scope = scope;

            refresh();

            super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        /**
         * Refresh all values in this frame.
         */
        public final void refresh() {
            TreeMap<String, String> variables = new TreeMap<>();
            for (String name : scope.variables()) {
                variables.put(name, scope.get(name).getValue().toString());
            }

            int size = variables.size();

            //Create and populate the panel.
            JPanel panel = new JPanel(new SpringLayout());
            for (String name : variables.keySet()) {
                JLabel l = new JLabel(name, JLabel.TRAILING);
                panel.add(l);
                JTextField textField = new JTextField(10);
                textField.setText(variables.get(name));
                l.setLabelFor(textField);
                panel.add(textField);
            }

            if (size > 0) {
                //Lay out the panel.
                SpringUtilities.makeCompactGrid(panel,
                        size, 2, //rows, cols
                        6, 6, //initX, initY
                        6, 6);      //xPad, yPad
            }
            super.setContentPane(panel);

            super.pack();
        }

    }

    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;

    private InterpreterThread interpreter;
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(StackView.class);

    private NamedWindow window;

    /**
     * Constructor.
     *
     */
    public StackView() {
    }

    /**
     * Create a new visualization.
     *
     * @return
     */
    @Override
    public InterpreterVisualization create() {
        return new StackView();
    }

    /**
     * Called to get the display name.
     *
     * @return
     */
    @Override
    public String getName() {
        return StackView.class.getSimpleName();
    }

    /**
     * Called when executing.
     *
     * @param name
     * @param controller
     * @param interpreter
     */
    @Override
    public void watch(String name, final Controller controller, InterpreterThread interpreter) {
        this.interpreter = interpreter;
        final ArrayList<ScopeFrame> frames = new ArrayList<>();

        this.interpreter.addInterpreterListener(new InterpreterListener() {

            /**
             * Show the window.
             */
            @Override
            public void started() {
                window.setVisible(true);
            }

            /**
             * Destroy the window.
             */
            @Override
            public void finished() {
                window.dispose();
                for (JFrame frame : frames) {
                    frame.dispose();
                }
            }

            /**
             *
             * @param e
             */
            @Override
            public void error(Exception e) {
            }

            /**
             *
             * @param msg
             */
            @Override
            public void message(String msg) {
            }

            /**
             *
             * @param block
             * @param scope
             */
            @Override
            public void currStatement(CodeBlock block, Scope scope) {
            }
        });

        window = new NamedWindow(name + "-" + this.getClass().getSimpleName(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        window.setTitle(controller.getLang() + " - Stack View");

        window.setLayout(new BorderLayout());

        final DefaultListModel listModel = new DefaultListModel();
        final JList list = new JList(listModel);

        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(-1);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        window.add(contentPane, BorderLayout.CENTER);

        Main.loadIcon(window);
        final Stack<ScopeItem> labels = new Stack<>();

        this.interpreter.addScopeListener(new ScopeListener() {

            /**
             * Populate the list.
             *
             * @param scope
             * @param block
             */
            @Override
            public void scopePopped(Scope scope, final CodeBlock block) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            ScopeItem popped = labels.pop();
                            listModel.removeElement(popped);
                        }
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    LOGGER.warn("Scope Popped: {0}", ex);
                }
            }

            /**
             * Remove from the list.
             *
             * @param scope
             * @param block
             */
            @Override
            public void scopePushed(final Scope scope, final CodeBlock block) {
                if (frames.isEmpty()) {
                    ScopeFrame sf = new ScopeFrame(scope);
                    sf.setTitle(controller.getLang() + " - Variable View");
                    sf.setVisible(true);
                    frames.add(sf);
                }
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            ScopeItem sp = new ScopeItem(block);
                            listModel.addElement(sp);
                            labels.push(sp);
                        }
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    LOGGER.warn("Scope Pushed: {0}", ex);
                }
            }

            /**
             * Update any viz of the scope.
             *
             * @param scope
             * @param name
             * @param value
             */
            @Override
            public void variableSet(Scope scope, String name, InterpreterValue value) {
                //TODO: refresh all frames
                for (ScopeFrame frame : frames) {
                    frame.refresh();
                }
            }
        });

        window.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
