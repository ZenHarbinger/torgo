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

import org.tros.torgo.swing.BufferedImageProvider;
import org.tros.logo.LogoCanvas;
import org.tros.torgo.swing.Localization;
import org.tros.torgo.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.swing.TorgoMenuBar;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.svggen.*;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.tros.torgo.swing.DrawListener;
import org.w3c.dom.Document;
import org.tros.torgo.swing.Drawable;
import org.tros.utils.GifSequenceWriter;

/**
 * Sets up a menu bar for the Logo application.
 *
 * @author matta
 */
public final class LogoMenuBar extends TorgoMenuBar {

    private JMenuItem toolsPenColorChooser;
    private JMenuItem toolsCanvasColorChooser;

    private final LogoCanvas canvas;
    protected static final String WAIT_FOR_REPAINT = "wait-for-repaint";

    /**
     * Constructor.
     *
     * @param parent
     * @param controller
     * @param canvas
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LogoMenuBar(Component parent, Controller controller, LogoCanvas canvas) {
        super(parent, controller);
        this.canvas = canvas;

        add(setupExportMenu());
        add(setupToolsMenu());
        JMenu menu = new JMenu("Logo Options");
        menu.add(setupMenu("Examples From Tortue", "logo/examples/tortue"));
        menu.add(setupMenu("Examples From ANTLR", "logo/examples/antlr"));

        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
        final JCheckBoxMenuItem speedMenu = new JCheckBoxMenuItem("Wait for Repaint");
        boolean checked = prefs.getBoolean(WAIT_FOR_REPAINT, true);
        speedMenu.setSelected(checked);
        speedMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                prefs.putBoolean(WAIT_FOR_REPAINT, speedMenu.isSelected());
            }
        });
        menu.add(speedMenu);
        add(menu);
    }

    /**
     * Create a SVG image. The image handler will write all images files to
     * "res/images".
     *
     * @param p
     * @param outStream
     * @throws UnsupportedEncodingException
     * @throws SVGGraphics2DIOException
     */
    private void generateSVG(Drawable p, OutputStream outStream) throws UnsupportedEncodingException, SVGGraphics2DIOException {
        DOMImplementation domImpl
                = GenericDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document myFactory = domImpl.createDocument(svgNS, "svg", null);
        SVGGeneratorContext ctx
                = SVGGeneratorContext.createDefault(myFactory);
        GenericImageHandler ihandler = new CachedImageHandlerPNGEncoder("res/images", null);
        ctx.setGenericImageHandler(ihandler);

        SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, false);

        p.draw(svgGenerator);
        // Create the SVG DOM tree.
        Writer out = new OutputStreamWriter(outStream, "UTF-8");
        svgGenerator.stream(out, true);
    }

    /**
     * Create an animated GIF.
     *
     * @param p
     * @param canvas
     * @param filename
     * @throws UnsupportedEncodingException
     * @throws SVGGraphics2DIOException
     * @throws IOException
     */
    private void generateGIF(final Drawable p, final BufferedImageProvider canvas, String filename) throws UnsupportedEncodingException, SVGGraphics2DIOException, IOException {
        final ImageOutputStream output = new FileImageOutputStream(new File(filename));
        final GifSequenceWriter writer = new GifSequenceWriter(output, canvas.getBufferedImage().getType(), 1, true);

        final BufferedImage image = canvas.getBufferedImage();

        final Graphics2D g2d = image.createGraphics();
        DrawListener dl = new DrawListener() {
            @Override
            public void drawn(Drawable sender) {
                try {
                    writer.writeToSequence(image);
                } catch (IOException ex) {
                }
            }
        };
        p.addListener(dl);

        p.draw(g2d);
        writer.close();

        p.removeListener(dl);
    }

    /**
     * Export the canvas as an image of the specified format. GIF will generate
     * an animated gif. Other formats are generated using ImageIO.write(...)
     *
     * @param format
     * @param filename
     */
    public void exportCanvas(final String format, final String filename) {
        if (JFrame.class.isAssignableFrom(super.parent.getClass())) {
            final JFrame frame = (JFrame) super.parent;
            frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (format) {
                        case "svg":
                            if (Drawable.class.isAssignableFrom(canvas.getClass())) {
                                try (FileOutputStream fos = new FileOutputStream(new File(filename))) {
                                    generateSVG((Drawable) canvas, fos);
                                    fos.flush();
                                } catch (IOException ex) {
                                    org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                                }
                            }
                            break;
                        case "gif":
                            if (Drawable.class.isAssignableFrom(canvas.getClass())
                                    && BufferedImageProvider.class.isAssignableFrom((canvas.getClass()))) {
                                try {
                                    generateGIF((Drawable) canvas, (BufferedImageProvider) canvas, filename);
                                } catch (SVGGraphics2DIOException ex) {
                                    org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                                } catch (IOException ex) {
                                    org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                                }
                            }
                            break;
                        default:
                            try {
                                // retrieve image
                                if (BufferedImageProvider.class.isAssignableFrom(canvas.getClass())) {
                                    BufferedImageProvider bip = (BufferedImageProvider) canvas;
                                    BufferedImage bi = bip.getBufferedImage();
                                    File outputfile = new File(filename);
                                    ImageIO.write(bi, format, outputfile);
                                }
                            } catch (IOException ex) {
                                org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                            }
                            break;
                    }
                } catch (Exception ex) {
                } finally {
                    if (JFrame.class.isAssignableFrom(LogoMenuBar.super.parent.getClass())) {
                        JFrame frame = (JFrame) LogoMenuBar.super.parent;
                        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        });
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
                            LogoMenuBar.this.controller.openFile(resource);
                        }
                    });
                }
            }
        } catch (IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).fatal(null, ex);
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
        JMenuItem exportSvg = new JMenuItem(Localization.getLocalizedString("ExportSVG"));

        exportSvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(false);
                java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
                chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

                chooser.setVisible(true);
                int result = chooser.showSaveDialog(parent);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String filename = chooser.getSelectedFile().getPath();
                    prefs.put("export-directory", chooser.getSelectedFile().getParent());
                    exportCanvas("svg", filename);
                }
            }
        });

        exportGif.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
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
            }
        });
        exportPng.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
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
            }
        });

        exportMenu.add(exportSvg);
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

        toolsPenColorChooser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
                if (selected != null) {
                    int red = selected.getRed();
                    int green = selected.getGreen();
                    int blue = selected.getBlue();
                    String hex = String.format("#%02x%02x%02x", red, green, blue);
                    controller.insertCommand("pencolor" + " " + hex);
                }
            }
        });
        toolsCanvasColorChooser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
                if (selected != null) {
                    int red = selected.getRed();
                    int green = selected.getGreen();
                    int blue = selected.getBlue();
                    String hex = String.format("#%02x%02x%02x", red, green, blue);
                    controller.insertCommand("canvascolor" + " " + hex);
                }
            }
        });

        toolsMenu.add(toolsPenColorChooser);
        toolsMenu.add(toolsCanvasColorChooser);

        toolsMenu.setMnemonic('T');

        return (toolsMenu);
    }
}
