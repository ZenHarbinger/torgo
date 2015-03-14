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
package org.tros.logo.swing;

import org.tros.torgo.swing.Localization;
import org.tros.torgo.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.swing.TorgoMenuBar;

public final class LogoMenuBar extends TorgoMenuBar {

    private JMenuItem exportGif;
    private JMenuItem exportPng;

    private JMenuItem toolsPenColorChooser;
    private JMenuItem toolsCanvasColorChooser;


    public LogoMenuBar(Component parent, Controller controller) {
        super(parent, controller);

        add(setupExportMenu());
        add(setupToolsMenu());
        JMenu menu = new JMenu("Examples");
        menu.add(setupMenu("From Tortue", "logo/examples/tortue"));
        menu.add(setupMenu("From ANTLR", "logo/examples/antlr"));
        add(menu);
    }

    private JMenu setupMenu(String name, String base) {
        JMenu samplesMenu = new JMenu(name);
        try {
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(base + "/resource.manifest");
            List<String> readLines = IOUtils.readLines(resourceAsStream);
            Collections.sort(readLines);
            readLines.stream().map((line) -> new JMenuItem(base + "/" + line)).forEach((jmi) -> {
                if (!jmi.getText().endsWith("manifest")) {
                    samplesMenu.add(jmi);
                    jmi.addActionListener((ActionEvent e) -> {
                        String val = e.getActionCommand();
                        ClassLoader cl = ClassLoader.getSystemClassLoader();
                        URL resource = cl.getResource(val);
                        this.controller.openFile(resource);
                    });
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LogoMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return samplesMenu;
    }

    private JMenu setupExportMenu() {
        JMenu exportMenu = new JMenu(Localization.getLocalizedString("ExportMenu"));

        exportGif = new JMenuItem(Localization.getLocalizedString("ExportGIF"));
        exportPng = new JMenuItem(Localization.getLocalizedString("ExportPNG"));

        exportGif.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

            chooser.setVisible(true);
            int result = chooser.showSaveDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getPath();
                prefs.put("export-directory", chooser.getSelectedFile().getParent());
                controller.exportCanvas("gif", filename);
            }
        });
        exportPng.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

            chooser.setVisible(true);
            int result = chooser.showSaveDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getPath();
                prefs.put("export-directory", chooser.getSelectedFile().getParent());
                controller.exportCanvas("png", filename);
            }
        });

        exportMenu.add(exportGif);
        exportMenu.add(exportPng);

        return (exportMenu);
    }

    private JMenu setupToolsMenu() {
        JMenu toolsMenu = new JMenu(Localization.getLocalizedString("ToolsMenu"));

        toolsPenColorChooser = new JMenuItem(Localization.getLocalizedString("ToolsPenColorChooser"));
        toolsCanvasColorChooser = new JMenuItem(Localization.getLocalizedString("ToolsCanvasColorChooser"));

        toolsPenColorChooser.setMnemonic('P');
        toolsCanvasColorChooser.setMnemonic('C');

        toolsPenColorChooser.addActionListener((ActionEvent e) -> {
            Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
            if (selected != null) {
                int red = selected.getRed();
                int green = selected.getGreen();
                int blue = selected.getBlue();
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                controller.insertCommand("pencolor" + " " + hex);
            }
        });
        toolsCanvasColorChooser.addActionListener((ActionEvent e) -> {
            Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
            if (selected != null) {
                int red = selected.getRed();
                int green = selected.getGreen();
                int blue = selected.getBlue();
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                controller.insertCommand("canvascolor" + " " + hex);
            }
        });

        toolsMenu.add(toolsPenColorChooser);
        toolsMenu.add(toolsCanvasColorChooser);

        toolsMenu.setMnemonic('T');

        return (toolsMenu);
    }
}
