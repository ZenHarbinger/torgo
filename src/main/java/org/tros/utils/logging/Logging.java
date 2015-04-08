/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import org.tros.utils.BuildInfo;
import static org.tros.utils.PathUtils.getApplicationEtcDirectory;
import static org.tros.utils.PathUtils.getLogDirectory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author matta
 */
public final class Logging {

    private Logging() {
    }

    public static void initLogging(BuildInfo binfo) {
        initLogging(binfo, Logging.class);
    }

    public static void initLogging(BuildInfo binfo, Class init) {
        try {
            //hack to get this logger to shut up
            Class<?> forName = Class.forName("org.reflections.Reflections");
            Field f = forName.getField("log");
            f.set(null, null);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            LogFactory.getLog(Logging.class).debug("org.reflections.Reflections not in CLASSPATH...");
        }

        //make logs directory
        getLogDirectory(binfo);

        //init logger
        String dir = getApplicationEtcDirectory(binfo) + "/logging.properties";
        File logProp = new File(dir);
        if (!logProp.exists()) {
            try {
                String prop_file = init.getCanonicalName().replace('.', '/') + ".properties";
                java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader()
                        .getResources(prop_file);
                if (resources.hasMoreElements()) {
                    URL to_use = resources.nextElement();
                    try (FileOutputStream fis = new FileOutputStream(logProp)) {
                        IOUtils.copy(to_use.openStream(), fis);
                    } catch (FileNotFoundException ex) {
                        LogFactory.getLog(Logging.class).warn(null, ex);
                    } catch (IOException ex) {
                        LogFactory.getLog(Logging.class).warn(null, ex);
                    }
                }
            } catch (IOException ex) {
                LogFactory.getLog(Logging.class).warn(null, ex);
            }
        }

        if (logProp.exists()) {
            String definedLogFile = System.getProperty("torgo.logfile");
            StringBuilder sb = new StringBuilder();
            try {
                final Scanner scanner = new Scanner(logProp);
                boolean lookForFile = false;
                while (scanner.hasNextLine()) {
                    String lineFromFile = scanner.nextLine();
                    if (lineFromFile.startsWith("handlers")
                            && lineFromFile.contains("java.util.logging.FileHandler")) {
                        lookForFile = true;
                    }
                    if (lookForFile
                            && definedLogFile != null
                            && lineFromFile.contains("java.util.logging.FileHandler.pattern")) {
                        lineFromFile = "java.util.logging.FileHandler.pattern = " + definedLogFile;
                    }
                    sb.append(lineFromFile).append(System.getProperty("line.separator"));
                }

                try (BufferedInputStream fis = new BufferedInputStream(IOUtils.toInputStream(sb.toString(), "UTF-8"))) {
                    LogManager.getLogManager().readConfiguration(fis);
                } catch (FileNotFoundException ex) {
                    LogFactory.getLog(Logging.class).warn(null, ex);
                } catch (IOException | SecurityException ex) {
                    LogFactory.getLog(Logging.class).warn(null, ex);
                }
            } catch (FileNotFoundException ex) {
                LogFactory.getLog(Logging.class).warn(null, ex);
            }
        }

        //Small hack to close SwingComponentHandler which should only be used by a GUI
        //however, if the logging.properties file is already set with this handler, remove
        //it and then the GUI will manually re-add it in the LogConsole constructor.
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
        for (Handler h : logger.getHandlers()) {
            if (SwingComponentHandler.class.isAssignableFrom(h.getClass())) {
                logger.removeHandler(h);
                h.close();
            }
        }
    }
}
