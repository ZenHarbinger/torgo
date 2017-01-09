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
package org.tros.torgo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main entry point for torgo
 *
 * @author matta
 */
public class Main {

    private static SplashScreen mySplash;
    private static Graphics2D splashGraphics;
    private static Image splashImage;
    private static Font font;
    private static Dimension splashDimension;
    private static double currPercent = 0;

    /**
     * Initialize the splash graphic.
     */
    private static void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
            try {
                // if there are any problems displaying the splash this will be null
                splashDimension = mySplash.getSize();
                splashImage = ImageIO.read(mySplash.getImageURL());

                // create the Graphics environment for drawing status info
                splashGraphics = mySplash.createGraphics();
                font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
                splashGraphics.setFont(font);
                splashGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

                // initialize the status info
                splashText("Starting");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Display text in status area of Splash. Note: no validation it will fit.
     *
     * @param str - text to be displayed
     */
    public static void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {
            splashGraphics.drawImage(splashImage, 0, 0, null);

            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, 27, (int) (splashDimension.getHeight() - 12));
            splashProgress(currPercent);

            mySplash.update();
        }
    }

    public static double numSteps = 1;
    public static double currStep = 0;

    /**
     * Set the number of steps to be completed.
     *
     * This number can be updated.
     *
     * @param steps
     */
    public static void setNumSteps(int steps) {
        if (steps > 0) {
            numSteps = steps;
        }
    }

    /**
     * Perform a step.
     */
    public static void splashStep() {
        currStep += 1.0;
        currStep = Math.min(currStep, numSteps);
        splashProgress(currStep / numSteps);
    }

    /**
     * Add a specified number of steps to the completed.
     *
     * @param steps
     */
    public static void addSteps(int steps) {
        if (steps > 0) {
            numSteps += steps;
        }
    }

    /**
     * Update splash progress.
     *
     * @param percent
     */
    private static void splashProgress(double percent) {
        if (mySplash != null && mySplash.isVisible()) {
            currPercent = percent;
            splashGraphics.setColor(Color.RED);
            splashGraphics.fillRect(0, splashDimension.height - 5, (int) (splashDimension.getWidth() * percent), 5);
            mySplash.update();
        }
    }

    /**
     * Entry Point
     *
     * @param args
     */
    public static void main(String[] args) {
        //initialize the logging
        splashInit();
        org.tros.utils.logging.Logging.initLogging(TorgoInfo.INSTANCE);
        Options options = new Options();
        options.addOption("l", "lang", true, "Open using the desired language. [default is 'logo']");
        options.addOption("i", "list", false, "List available languages.");
        String lang = "dynamic-logo";
        final String fileArgument = args.length - 1 >= 0 ? args[args.length - 1] : null;
        String ext = null;
        boolean quit = false;
        if (fileArgument != null) {
            int index = fileArgument.lastIndexOf('.');
            if (index >= 0) {
                ext = fileArgument.substring(index + 1);
            }
        }

        boolean customLangUsed = false;
        try {
            CommandLineParser parser = new org.apache.commons.cli.DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("lang") || cmd.hasOption("l")) {
                lang = cmd.getOptionValue("lang");
                customLangUsed = true;
            } else if (cmd.hasOption("i") || cmd.hasOption("list")) {
                Set<String> toolkits = TorgoToolkit.getToolkits();
                toolkits.forEach((name) -> {
                    System.out.println(name);
                });
                //will force an exit
                lang = null;
                quit = true;
            }
        } catch (ParseException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Main.class).fatal(null, ex);
        }

        //currently commented out for working with snapd
        if (System.getProperty("swing.defaultlaf") == null) {
            try {
                //set look and feel (laf) to that of the system.
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(Main.class).fatal(null, ex);
            }
        }

        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoToolkit.class);
        if (!customLangUsed) {
            lang = prefs.get("lang", lang);
        }
        if (ext != null && TorgoToolkit.getToolkits().contains(ext)) {
            lang = ext;
        }
        final String controlLang = lang;

        if (!quit) {
            prefs.put("lang", lang);
            SwingUtilities.invokeLater(() -> {
                Controller controller = TorgoToolkit.getController(controlLang);
                if (controller != null) {
                    controller.run();
                    if (fileArgument != null) {
                        controller.openFile(new File(fileArgument));
                    } else {
                        controller.newFile();
                    }
                }
            });
        }
    }

    public static final String IMAGE_ICON_CLASS_PATH = "torgo-48x48.png";

    public static void loadIcon(Window frame) {
        frame.setIconImage(getIcon().getImage());
    }

    public static ImageIcon getIcon() {
        return getIcon(IMAGE_ICON_CLASS_PATH);
    }

    public static ImageIcon getIcon(String path) {
        try {
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(path);
            ImageIcon ico = new ImageIcon(resources.nextElement());
            return ico;
        } catch (NoSuchElementException | IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Main.class).warn(null, ex);
        }
        return null;
    }
}
