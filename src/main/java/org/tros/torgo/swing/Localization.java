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
package org.tros.torgo.swing;

import java.util.Locale;
import java.util.ResourceBundle;
import org.tros.utils.logging.Logger;

/**
 * Localization Strings.
 *
 * @author matta
 */
public final class Localization {

    private static final Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(Localization.class);
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private static ResourceBundle resources;
    private static Locale locale;

    private Localization() {
    }

    static {
        locale = Locale.getDefault();
        resources = null;
        setLang(locale);
        if (resources == null) {
            LOGGER.warn("{0} is not a supported language.", locale.getLanguage());
            LOGGER.warn("Setting language to {0}.", DEFAULT_LOCALE.getLanguage());
            setLang(DEFAULT_LOCALE);
        }
    }

    public static void setLang(Locale locale) {
        try {
            resources = ResourceBundle.getBundle("messages", locale);
            Localization.locale = locale;
        } catch (java.util.MissingResourceException e) {
            LOGGER.warn("Language not supported: {0}", locale.getLanguage());
        }
    }

    public static String getLocalizedString(String key) {
        return resources != null && resources.containsKey(key) ? resources.getString(key) : key;
    }

    public static Locale getLang() {
        return locale;
    }
}
