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

import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

/**
 *
 * @author matta
 */
public class UpdateChecker {

    public static final String UPDATE_ADDRESS = "https://github.com/ZenHarbinger/torgo/releases/latest";
    private static final org.tros.utils.logging.Logger LOGGER;
    private static final String TAG = "title";
    private static final String SNAPSHOT = "SNAPSHOT";

    private String updateVersion;
    private boolean checkForUpdate;

    private boolean urlExceptionTest = false;
    private boolean ioExceptionTest = false;

    static {
        LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(UpdateChecker.class);
    }

    public UpdateChecker() {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(UpdateChecker.class);
        checkForUpdate = prefs.getBoolean("check-for-update", true);
    }

    public boolean hasUpdate() {
        boolean ret = false;
        try {
            if (urlExceptionTest) {
                throw new MalformedURLException();
            }
            if (ioExceptionTest) {
                throw new IOException();
            }
            if (checkForUpdate) {
                Document doc = org.jsoup.Jsoup.connect(UPDATE_ADDRESS).get();
                Elements elementsByTag = doc.getElementsByTag(TAG);
                updateVersion = elementsByTag.text();
                String version = TorgoToolkit.getBuildInfo().getVersion();
                if (!version.contains(SNAPSHOT)) {
                    ret = !updateVersion.contains(version);
                }
            }
        } catch (MalformedURLException ex) {
            urlExceptionTest();
            LOGGER.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            ioExceptionTest();
            org.tros.utils.logging.Logging.getLogFactory().getLogger(UpdateChecker.class).warn(null, ex);
        }
        return ret;
    }

    public boolean getCheckForUpdate() {
        return checkForUpdate;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setCheckForUpdate(boolean value) {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(UpdateChecker.class);
        prefs.putBoolean("check-for-update", value);
        checkForUpdate = value;
    }

    public void urlExceptionTest() {
        urlExceptionTest = !urlExceptionTest;
    }

    public void ioExceptionTest() {
        ioExceptionTest = !ioExceptionTest;
    }

    public boolean getURLException() {
        return urlExceptionTest;
    }

    public boolean getIOException() {
        return ioExceptionTest;
    }
}
