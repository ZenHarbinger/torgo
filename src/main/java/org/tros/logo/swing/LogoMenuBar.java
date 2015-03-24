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

import org.tros.logo.LogoCanvas;
import org.tros.torgo.swing.Localization;
import org.tros.torgo.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.swing.TorgoMenuBar;

/**
 * Sets up a menu bar for the Logo application.
 *
 * @author matta
 */
public final class LogoMenuBar extends TorgoMenuBar {

    private JMenuItem toolsPenColorChooser;
    private JMenuItem toolsCanvasColorChooser;

    private final LogoCanvas canvas;

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     * @param canvas
     */
    public LogoMenuBar(Component parent, Controller controller, LogoCanvas canvas) {
        super(parent, controller);
        this.canvas = canvas;

        add(setupExportMenu());
        add(setupToolsMenu());
        JMenu menu = new JMenu("Examples");
        menu.add(setupMenu("From Tortue", "logo/examples/tortue"));
        menu.add(setupMenu("From ANTLR", "logo/examples/antlr"));
        add(menu);
    }

    /**
     * Export the canvas as an image of the specified format. GIF will generate
     * an animated gif. Other formats are generated using ImageIO.write(...)
     *
     * @param format
     * @param filename
     */
    public void exportCanvas(String format, String filename) {
//        if (format.equals("gif")) {
//            //export animated gif
//            try {
//                ImageOutputStream output = new FileImageOutputStream(new File(filename));
//                GifSequenceWriter writer = new GifSequenceWriter(output, torgoCanvas.getImage().getType(), 1, true);
//
//                String source = torgoPanel.getSource();
//                interp = createInterpreterThread(source);
//
//                interp.addInterpreterListener(new InterpreterListener() {
//
//                    @Override
//                    public void started() {
//                        listeners.stream().forEach((l) -> {
//                            l.started();
//                        });
//                    }
//
//                    @Override
//                    public void finished() {
//                        listeners.stream().forEach((l) -> {
//                            l.finished();
//                        });
//                        torgoPanel.highlight(-1, 0, 0);
//                        try {
//                            writer.close();
//                        } catch (IOException ex) {
//                            Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//
//                    @Override
//                    public void error(Exception e) {
//                        listeners.stream().forEach((l) -> {
//                            l.error(e);
//                        });
//                        Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, e);
//                    }
//
//                    @Override
//                    public void message(String msg) {
//                        listeners.stream().forEach((l) -> {
//                            l.message(msg);
//                        });
//                    }
//
//                    @Override
//                    public void currStatement(String statement, int line, int start, int end) {
//                        try {
//                            writer.writeToSequence(SwingCanvas.deepCopy(torgoCanvas.getImage()));
//                        } catch (IOException ex) {
//                            Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                });
//                interp.start();
//            } catch (IOException ex) {
//                Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
        try {
            // retrieve image
            BufferedImage bi = canvas.getImage();
            File outputfile = new File(filename);
            ImageIO.write(bi, format, outputfile);
        } catch (IOException e) {
            Logger.getLogger(ControllerBase.class.getName()).log(Level.SEVERE, null, e);
        }
//        }
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
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(base + "/resource.manifest");
            List<String> readLines = IOUtils.readLines(resourceAsStream);
            Collections.sort(readLines);
            readLines.stream().map((line) -> new JMenuItem(base + "/" + line)).forEach((jmi) -> {
                if (!jmi.getText().endsWith("manifest")) {
                    samplesMenu.add(jmi);
                    jmi.addActionListener((ActionEvent e) -> {
                        String val = e.getActionCommand();
                        URL resource = ClassLoader.getSystemClassLoader().getResource(val);
                        this.controller.openFile(resource);
                    });
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LogoMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return samplesMenu;
    }

    /**
     * Set up the export menu.
     *
     * @return
     */
    private JMenu setupExportMenu() {
        JMenu exportMenu = new JMenu(Localization.getLocalizedString("ExportMenu"));

        JMenuItem exportGif = new JMenuItem(Localization.getLocalizedString("ExportGIF"));
        JMenuItem exportPng = new JMenuItem(Localization.getLocalizedString("ExportPNG"));

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
                exportCanvas("gif", filename);
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
                exportCanvas("png", filename);
            }
        });

        exportMenu.add(exportGif);
        exportMenu.add(exportPng);

        return (exportMenu);
    }

    /**
     * Set up the tools menu.
     *
     * @return
     */
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
