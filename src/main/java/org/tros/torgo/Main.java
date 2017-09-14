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

import java.awt.Window;
import java.io.File;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.tros.utils.ImageUtils;

/**
 * Main entry point for torgo.
 *
 * @author matta
 */
public final class Main {

    public static final String IMAGE_ICON_CLASS_PATH = "torgo-48x48.png";
    private static final String DEFAULT_LANGUAGE = "dynamic-logo";

    /**
     * Hidden utility constructor.
     */
    private Main() {
    }

    /**
     * Entry Point.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        //initialize the logging
        MainSplash.splashInit();
        org.tros.utils.logging.Logging.initLogging(TorgoToolkit.getBuildInfo());
        final org.tros.utils.logging.Logger logger = org.tros.utils.logging.Logging.getLogFactory().getLogger(Main.class);

        Options options = new Options();
        options.addOption("l", "lang", true, "Open using the desired language. [default is 'logo']");
        options.addOption("i", "list", false, "List available languages.");
        String lang = DEFAULT_LANGUAGE;
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
            logger.fatal(null, ex);
        }

        //currently commented out for working with snapd
        if (System.getProperty("swing.defaultlaf") == null) {
            try {
                //set look and feel (laf) to that of the system.
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                logger.fatal(null, ex);
            }
        }

        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoToolkit.class);
        if (!customLangUsed) {
            lang = prefs.get("lang", lang);
        }
        if (ext != null && TorgoToolkit.getToolkits().contains(ext)) {
            lang = ext;
        }
        if (!TorgoToolkit.getToolkits().contains(lang)) {
            logger.warn("Could not load: {0}", lang);
            logger.warn("Loading Default: {0}", DEFAULT_LANGUAGE);
            lang = DEFAULT_LANGUAGE;
        }
        final String controlLang = lang;

        Controller controller = null;
        if (!quit) {
            prefs.put("lang", lang);
            controller = TorgoToolkit.getController(controlLang);
            final Controller ctrl = controller;
            SwingUtilities.invokeLater(() -> {
                if (ctrl != null) {
                    ctrl.run();
                    if (fileArgument != null) {
                        ctrl.openFile(new File(fileArgument));
                    } else {
                        ctrl.newFile();
                    }
                }
            });
        }

        MainMac.handleFileActivation(controller);
    }

    public static void loadIcon(Window frame) {
        frame.setIconImage(getIcon().getImage());
    }

    public static ImageIcon getIcon() {
        return ImageUtils.getIcon(IMAGE_ICON_CLASS_PATH);
    }
}
