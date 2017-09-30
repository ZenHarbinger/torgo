/*
 * Copyright 2015-2017 Matthew Aguirre
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
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.swing.TorgoMenuBar;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.CachedImageHandlerPNGEncoder;
import org.apache.batik.svggen.GenericImageHandler;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.Document;
import org.tros.utils.GifSequenceWriter;

/**
 * Sets up a menu bar for the Logo application.
 *
 * @author matta
 */
public final class LogoMenuBar extends TorgoMenuBar {

    protected static final String WAIT_FOR_REPAINT = "wait-for-repaint";

    private final LogoCanvas canvas;

    private JMenuItem toolsPenColorChooser;
    private JMenuItem toolsCanvasColorChooser;

    /**
     * Constructor.
     *
     * @param parent the parent component.
     * @param controller the controller.
     * @param canvas the canvas.
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
        speedMenu.addActionListener((ActionEvent e) -> {
            prefs.putBoolean(WAIT_FOR_REPAINT, speedMenu.isSelected());
        });
        menu.add(speedMenu);
        add(menu);
    }

    /**
     * Create a SVG image. The image handler will write all images files to
     * "res/images".
     *
     * @param p the drawable component.
     * @param outStream the the output stream.
     * @throws UnsupportedEncodingException error on unsupported encoding.
     * @throws SVGGraphics2DIOException error on error to conversion.
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

        TurtleState ts = new TurtleState();

        p.draw(svgGenerator, ts);
        // Create the SVG DOM tree.
        Writer out = new OutputStreamWriter(outStream, "UTF-8");
        svgGenerator.stream(out, true);
    }

    /**
     * Create an animated GIF.
     *
     * @param p the drawable component.
     * @param canvas the canvas.
     * @param filename the file to write to.
     * @throws UnsupportedEncodingException error on unsupported encoding.
     * @throws SVGGraphics2DIOException error on error to conversion.
     * @throws IOException error writing to file.
     */
    private void generateGIF(final Drawable p, final BufferedImageProvider canvas, String filename) throws UnsupportedEncodingException, SVGGraphics2DIOException, IOException {
        final ImageOutputStream output = new FileImageOutputStream(new File(filename));
        final GifSequenceWriter writer = new GifSequenceWriter(output, canvas.getBufferedImage().getType(), 1, true);

        final BufferedImage image = canvas.getBufferedImage();

        final Graphics2D g2d = image.createGraphics();
        DrawListener dl = (Drawable sender) -> {
            try {
                writer.writeToSequence(image);
            } catch (IOException ex) {
            }
        };
        p.addListener(dl);

        TurtleState ts = new TurtleState();
        ts.width = image.getWidth();
        ts.height = image.getHeight();

        p.draw(g2d, ts);
        writer.close();

        p.removeListener(dl);
        org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).info("{0} animation is complete!", filename);
        JOptionPane.showMessageDialog(parent, MessageFormat.format("{0} animation is complete!", filename), "Animated GIF Sequence Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Set up the menus for examples.
     *
     * @param name the name.
     * @param base the base.
     * @return
     */
    private JMenu setupMenu(String name, String base) {
        JMenu samplesMenu = new JMenu(name);
        try {
            InputStream resourceAsStream = TorgoToolkit.getDefaultResourceAccessor().open(base + "/resource.manifest");
            List<String> readLines = IOUtils.readLines(resourceAsStream, "utf-8");
            Collections.sort(readLines);
            readLines.stream().map((line) -> new JMenuItem(base + "/" + line)).filter((jmi) -> (!jmi.getText().endsWith("manifest"))).map((JMenuItem jmi) -> {
                samplesMenu.add(jmi);
                return jmi;
            }).forEachOrdered((JMenuItem jmi) -> {
                jmi.addActionListener((ActionEvent e) -> {
                    String val = e.getActionCommand();
                    URL resource = ClassLoader.getSystemClassLoader().getResource(val);
                    LogoMenuBar.this.controller.openFile(resource);
                });
            });
        } catch (IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).fatal(null, ex);
        }
        return samplesMenu;
    }

    private void generatePNG(BufferedImageProvider bip, String filename) {
        BufferedImage bi = bip.getBufferedImage();
        File outputfile = new File(filename);
        try {
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
        }
    }

    /**
     * Set up the export menu.
     *
     * @return a new menu.
     */
    private JMenu setupExportMenu() {
        JMenu exportMenu = new JMenu(Localization.getLocalizedString("ExportMenu"));

        JMenuItem exportGif = new JMenuItem(Localization.getLocalizedString("ExportGIF"));
        JMenuItem exportPng = new JMenuItem(Localization.getLocalizedString("ExportPNG"));
        JMenuItem exportSvg = new JMenuItem(Localization.getLocalizedString("ExportSVG"));

        exportSvg.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Scalable Vector Graphic", "svg"));
            chooser.setMultiSelectionEnabled(false);
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

            chooser.setVisible(true);
            int result = chooser.showSaveDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getPath();
                String extension = ".svg";
                if (!filename.endsWith(extension)) {
                    filename = filename + extension;
                }
                prefs.put("export-directory", chooser.getSelectedFile().getParent());
                if (Drawable.class.isAssignableFrom(canvas.getClass())) {
                    try (FileOutputStream fos = new FileOutputStream(new File(filename))) {
                        generateSVG((Drawable) canvas, fos);
                        fos.flush();
                    } catch (IOException ex) {
                        org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                    }
                }
            }
        });

        exportGif.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Animated GIF Image", "gif"));
            chooser.setMultiSelectionEnabled(false);
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

            chooser.setVisible(true);
            int result = chooser.showSaveDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename2 = chooser.getSelectedFile().getPath();
                String extension = ".gif";
                if (!filename2.endsWith(extension)) {
                    filename2 = filename2 + extension;
                }
                final String filename = filename2;
                prefs.put("export-directory", chooser.getSelectedFile().getParent());
                Thread t = new Thread(() -> {
                    if (Drawable.class.isAssignableFrom(canvas.getClass())
                            && BufferedImageProvider.class.isAssignableFrom((canvas.getClass()))) {
                        try {
                            generateGIF(((Drawable) canvas).cloneDrawable(), (BufferedImageProvider) canvas, filename);
                        } catch (SVGGraphics2DIOException ex) {
                            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                        } catch (IOException ex) {
                            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoMenuBar.class).warn(null, ex);
                        }
                    }
                });
                t.setDaemon(true);
                t.start();
            }
        });
        exportPng.addActionListener((ActionEvent ae) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
            chooser.setMultiSelectionEnabled(false);
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            chooser.setCurrentDirectory(new File(prefs.get("export-directory", ".")));

            chooser.setVisible(true);
            int result = chooser.showSaveDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename2 = chooser.getSelectedFile().getPath();
                String extension = ".png";
                if (!filename2.endsWith(extension)) {
                    filename2 = filename2 + extension;
                }
                final String filename = filename2;
                prefs.put("export-directory", chooser.getSelectedFile().getParent());
                // retrieve image
                if (BufferedImageProvider.class.isAssignableFrom(canvas.getClass())) {
                    generatePNG((BufferedImageProvider) canvas, filename);
                }
            }
        });

        exportMenu.add(exportSvg);
        exportMenu.add(exportGif);
        exportMenu.add(exportPng);
        exportMenu.setMnemonic('X');
        exportSvg.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        exportGif.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        exportPng.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        return (exportMenu);
    }

    /**
     * Set up the tools menu.
     *
     * @return a new menu.
     */
    private JMenu setupToolsMenu() {
        toolsPenColorChooser = new JMenuItem(Localization.getLocalizedString("ToolsPenColorChooser"));
        toolsCanvasColorChooser = new JMenuItem(Localization.getLocalizedString("ToolsCanvasColorChooser"));

        toolsPenColorChooser.setMnemonic('P');
        toolsCanvasColorChooser.setMnemonic('C');

        toolsPenColorChooser.addActionListener((ActionEvent ae) -> {
            Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
            if (selected != null) {
                int red = selected.getRed();
                int green = selected.getGreen();
                int blue = selected.getBlue();
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                controller.insertCommand("pencolor" + " " + hex);
            }
        });
        toolsCanvasColorChooser.addActionListener((ActionEvent ae) -> {
            Color selected = JColorChooser.showDialog(parent, Localization.getLocalizedString("ColorChooser"), null);
            if (selected != null) {
                int red = selected.getRed();
                int green = selected.getGreen();
                int blue = selected.getBlue();
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                controller.insertCommand("canvascolor" + " " + hex);
            }
        });

        JMenu toolsMenu = new JMenu(Localization.getLocalizedString("ToolsMenu"));
        toolsMenu.add(toolsPenColorChooser);
        toolsMenu.add(toolsCanvasColorChooser);

        toolsMenu.setMnemonic('T');

        return (toolsMenu);
    }
}
