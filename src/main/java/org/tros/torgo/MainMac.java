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

import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import java.io.File;
import javax.swing.SwingUtilities;

/**
 *
 * @author matta
 */
public final class MainMac {

    private MainMac() {
    }

    /**
     * Handle file activation via macOS open-file.
     *
     * @param controller the controller to use.
     */
    public static void handleFileActivation(Controller controller) {
        //First, check for if we are on OS X so that it doesn't execute on
        //other platforms. Note that we are using contains() because it was
        //called Mac OS X before 10.8 and simply OS X afterwards
        if (System.getProperty("os.name").contains("OS X")) {
            final org.tros.utils.logging.Logger logger = org.tros.utils.logging.Logging.getLogFactory().getLogger(MainMac.class);
            Application a = Application.getApplication();
            a.setOpenFileHandler((AppEvent.OpenFilesEvent e) -> {
                e.getFiles().forEach((file2) -> {
                    File file = (File) file2;
                    logger.info("FILE: {0}", file.getAbsolutePath());
                    int index = file.getName().lastIndexOf('.');
                    String lang = "dynamic-logo";
                    String ext;
                    if (index >= 0) {
                        ext = file.getName().substring(index + 1);
                        if (ext != null && TorgoToolkit.getToolkits().contains(ext)) {
                            lang = ext;
                        }
                    }
                    logger.info("LANG: {0}", lang);
                    if (lang.equals(controller.getLang())) {
                        controller.openFile(file);
                    } else {
                        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoToolkit.class);
                        prefs.put("lang", lang);

                        controller.close();
                        Controller controller2 = TorgoToolkit.getController(lang);
                        SwingUtilities.invokeLater(() -> {
                            if (controller2 != null) {
                                controller2.run();
                                controller2.openFile(file);
                            }
                        });
                    }
                });
            });
        }
    }
}
