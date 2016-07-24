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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import org.tros.utils.logging.LogConsole;

/**
 * Builds the base menu.
 *
 * @author matta
 */
public class TorgoMenuBar extends JMenuBar {

    protected final Controller controller;

    private JMenuItem fileNew;
    private JMenuItem fileOpen;
    private JMenuItem fileClose;
    private JMenuItem fileSave;
    private JMenuItem fileSaveAs;
    private JMenuItem fileQuit;
    private JMenuItem logConsole;

    protected final Component parent;

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TorgoMenuBar(Component parent, Controller controller) {
        this.controller = controller;
        this.parent = parent;

        add(setupFileMenu());
    }

    /**
     * Initializer.
     *
     * @return
     */
    private JMenu setupFileMenu() {
        LogConsole.CONSOLE.setVisible(false);
        JMenu fileMenu = new JMenu(Localization.getLocalizedString("FileMenu"));

        fileNew = new JMenuItem(Localization.getLocalizedString("FileNew"));
        fileOpen = new JMenuItem(Localization.getLocalizedString("FileOpen"));
        fileClose = new JMenuItem(Localization.getLocalizedString("FileClose"));
        fileSave = new JMenuItem(Localization.getLocalizedString("FileSave"));
        fileSaveAs = new JMenuItem(Localization.getLocalizedString("FileSaveAs"));
        fileQuit = new JMenuItem(Localization.getLocalizedString("FileQuit"));

        logConsole = new JMenuItem("View Log Console");

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
}
