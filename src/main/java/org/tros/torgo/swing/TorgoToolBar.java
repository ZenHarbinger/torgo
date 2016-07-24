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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.Scope;

/**
 * Creates a base Toolbar.
 *
 * @author matta
 */
public class TorgoToolBar extends JToolBar {

    private final ToolBarAction stepOverAction;
    private final ToolBarAction pauseAction;
    private final ToolBarAction runAction;

    private boolean paused;
    private boolean debugging;

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoToolBar(final Component parent, final Controller controller) {
        paused = false;
        debugging = false;

        setOrientation(SwingConstants.HORIZONTAL);

        final ToolBarAction newAction = new ToolBarAction(Localization.getLocalizedString("FileNew"), "projectui/newFile.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.newFile();
            }
        };

        final ToolBarAction openAction = new ToolBarAction(Localization.getLocalizedString("FileOpen"), "projectui/open.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.openFile();
            }
        };

        runAction = new ToolBarAction(Localization.getLocalizedString("RunLabel"), "projectui/runProject.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (paused) {
                    controller.resumeInterpreter();
                } else {
                    controller.startInterpreter();
                }
                if (debugging) {
                    pauseAction.setEnabled(true);
                    runAction.setEnabled(false);
                }
                stepOverAction.setEnabled(false);
            }
        };

        final ToolBarAction saveAction = new ToolBarAction(Localization.getLocalizedString("FileSave"), "actions/save.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.saveFile();
            }
        };

        final ToolBarAction saveAsAction = new ToolBarAction(Localization.getLocalizedString("FileSaveAs"), "profiler/actions/icons/saveAs.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.saveFileAs();
            }
        };

        final ToolBarAction stopAction = new ToolBarAction(Localization.getLocalizedString("StopLabel"), "projectui/stop.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.stopInterpreter();
            }
        };

        //debug actions:
        final ToolBarAction debugAction = new ToolBarAction("Debug", "debugging/debugProject.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                pauseAction.setEnabled(true);
                debugging = true;
                controller.debugInterpreter();
            }
        };

        stepOverAction = new ToolBarAction("Step Over", "debugging/actions/StepOver.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                controller.stepOver();
            }
        };

        final ToolBarAction stepIntoAction = new ToolBarAction("Step Into", "debugging/actions/StepInto.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
            }
        };

        final ToolBarAction stepOutAction = new ToolBarAction("Step Out", "debugging/actions/StepOut.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
            }
        };

        pauseAction = new ToolBarAction("Pause", "debugging/actions/Pause.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
                paused = true;
                runAction.setEnabled(true);
                pauseAction.setEnabled(false);
                stepOverAction.setEnabled(true);
                controller.pauseInterpreter();
            }
        };

        final ToolBarAction runToCursorAction = new ToolBarAction("Run To Cursor", "debugging/actions/RunToCursor.png") {
            @Override
            public void actionPerformed(ActionEvent event) {
            }
        };

        stopAction.setEnabled(false);
        stepOverAction.setEnabled(false);
        stepIntoAction.setEnabled(false);
        stepOutAction.setEnabled(false);
        pauseAction.setEnabled(false);
        runToCursorAction.setEnabled(false);

        add(newAction);
        add(openAction);
        add(saveAction);
        add(saveAsAction);
        addSeparator();
        add(runAction);
        add(stopAction);
        addSeparator();

        //todo: add all debug actions...
        add(debugAction);
        add(pauseAction);
//        add(runToCursorAction);
        add(stepOverAction);
//        add(stepIntoAction);
//        add(stepOutAction);

        controller.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
                debugAction.setEnabled(false);
                runAction.setEnabled(false);
                stopAction.setEnabled(true);
                paused = false;
            }

            @Override
            public void finished() {
                controller.resumeInterpreter();
                stepOverAction.setEnabled(false);
                debugAction.setEnabled(true);
                runAction.setEnabled(true);
                stopAction.setEnabled(false);
                paused = false;
                debugging = false;
                pauseAction.setEnabled(false);
            }

            @Override
            public void error(Exception e) {
                controller.resumeInterpreter();
                stepOverAction.setEnabled(false);
                runAction.setEnabled(true);
                stopAction.setEnabled(false);
                paused = false;
                debugging = false;
            }

            @Override
            public void message(String msg) {
            }

            @Override
            public void currStatement(CodeBlock block, Scope scope) {
            }
        });
    }

    /**
     * Inner abstract class that initializes the icons.
     */
    protected abstract class ToolBarAction extends AbstractAction {

        public ToolBarAction(String name, String iconPath) {
            this(name, iconPath, name);
        }

        @SuppressWarnings("OverridableMethodCallInConstructor")
        public ToolBarAction(String name, String iconPath, String toolTip) {
            URL url = ClassLoader.getSystemClassLoader().getResource(iconPath);
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, new ImageIcon(url));
            putValue(Action.SHORT_DESCRIPTION, toolTip);
        }
    }
}
