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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import org.tros.utils.swing.NamedWindow;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
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

    public interface StackViewFrame {

        void refresh(Scope scope);

        void dispose();
    }

    /**
     * Represents a CodeBlock in the list.
     */
    public class ScopeItem {

        final private CodeBlock cb;
        final private int depth;
        final private Scope scope;
        private SliceWatchFrame swf;

        /**
         * Constructor.
         *
         * @param cb
         * @param depth
         * @param scope
         */
        public ScopeItem(CodeBlock cb, int depth, Scope scope) {
            super();
            this.depth = depth;
            this.cb = cb;
            this.scope = scope;
        }

        /**
         * String displayed in the list.
         *
         * @return
         */
        @Override
        public String toString() {
            if (CodeFunction.class.isAssignableFrom(cb.getClass())) {
                return ((CodeFunction) cb).getFunctionName();
            } else {
                return cb.getClass().getSimpleName();
            }
        }

        /**
         *
         * @return
         */
        public String getToolTip() {
            Map<String, InterpreterValue> vars = scope.variablesPeek(depth);
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            for (String var : vars.keySet()) {
                sb.append(var).append(": ").append(vars.get(var).getValue().toString()).append("<br>");
            }
            sb.append("</html>");
            return sb.toString().trim();
        }

        public SliceWatchFrame createWatchFrame() {
            if (swf == null) {
                swf = new SliceWatchFrame(this);
            }
            return swf;
        }

        public void onPopped() {
            if (swf != null) {
                swf.dispose();
                swf = null;
            }
        }
    }

    public class SliceWatchFrame extends JFrame implements StackViewFrame {

        final private ScopeItem item;

        public SliceWatchFrame(ScopeItem item) {
            this.item = item;
            refresh(item.scope);
            Main.loadIcon((JFrame) this);
            super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        @Override
        final public void refresh(Scope scope) {
            TreeMap<String, InterpreterValue> variables = new TreeMap<>(scope.variablesPeek(item.depth));

            int size = variables.size();

            //Create and populate the panel.
            JPanel panel = new JPanel(new SpringLayout());
            for (String name : variables.keySet()) {
                JLabel l = new JLabel(name, JLabel.TRAILING);
                panel.add(l);
                JTextField textField = new JTextField(10);
                textField.setText(variables.get(name).toString());
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

    /**
     * Class to show values of variables in the interpreted code.
     */
    public class VariableWatchFrame extends NamedWindow implements StackViewFrame {

        /**
         * Constructor.
         *
         * @param scope
         */
        public VariableWatchFrame(Scope scope) {
            super("Variable-Watch");
            super.setTitle("Variable Watch");

            Main.loadIcon((JFrame) this);

            refresh(scope);

            super.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        /**
         * Refresh all values in this frame.
         *
         * @param scope
         */
        @Override
        public final void refresh(Scope scope) {
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
        final ArrayList<StackViewFrame> frames = new ArrayList<>();

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
                synchronized (frames) {
                    for (StackViewFrame frame : frames) {
                        frame.dispose();
                    }
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

        list.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (index > -1) {
                    ScopeItem item = (ScopeItem) listModel.getElementAt(index);
                    String tip = item.toString();
                    list.setToolTipText(item.getToolTip());
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    ScopeItem item = (ScopeItem) listModel.getElementAt(index);
                    SliceWatchFrame swf = item.createWatchFrame();
                    synchronized (frames) {
                        frames.add(swf);
                    }
                    swf.setTitle("Stack - " + item.depth);
                    swf.setVisible(true);
                }
            }
        });

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
                            synchronized (frames) {
                                frames.remove(popped.swf);
                            }
                            popped.onPopped();
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
                synchronized (frames) {
                    if (frames.isEmpty()) {
                        VariableWatchFrame sf = new VariableWatchFrame(scope);
                        sf.setTitle(controller.getLang() + " - Variable View");
                        sf.setVisible(true);
                        frames.add(sf);
                    }
                }
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            ScopeItem sp = new ScopeItem(block, listModel.size(), scope);
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
            public void variableSet(final Scope scope, String name, InterpreterValue value) {
                synchronized (frames) {
                    for (StackViewFrame frame : frames) {
                        frame.refresh(scope);
                    }
                }
            }
        });

        window.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
