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
package org.tros.jvmbasic.swing;

import org.tros.torgo.Controller;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.swing.TorgoMenuBar;

/**
 * Sets up a menu bar for the Logo application.
 *
 * @author matta
 */
public final class BasicMenuBar extends TorgoMenuBar {

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BasicMenuBar(Component parent, Controller controller) {
        super(parent, controller);
        JMenu menu = new JMenu("Basic Options");
        menu.add(setupMenu("Examples From ANTLR", "basic/examples/antlr"));
        menu.add(setupMenu("Examples From TROS", "basic/examples/tros"));
        add(menu);
    }

    /**
     * Set up the menus for examples.
     *
     * @param name
     * @param base
     * @return
     */
    private JMenu setupMenu(String name, String base) {
        JMenu samplesMenu = new JMenu(name);
        try {
            InputStream resourceAsStream = TorgoToolkit.getDefaultResourceAccessor().open(base + "/resource.manifest");
            List<String> readLines = IOUtils.readLines(resourceAsStream, "utf-8");
            Collections.sort(readLines);
            for (String line : readLines) {
                JMenuItem jmi = new JMenuItem(base + "/" + line);
                if (!jmi.getText().endsWith("manifest")) {
                    samplesMenu.add(jmi);
                    jmi.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String val = e.getActionCommand();
                            URL resource = ClassLoader.getSystemClassLoader().getResource(val);
                            BasicMenuBar.this.controller.openFile(resource);
                        }
                    });
                }
            }
        } catch (IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(BasicMenuBar.class).fatal(null, ex);
        }
        return samplesMenu;
    }
}
