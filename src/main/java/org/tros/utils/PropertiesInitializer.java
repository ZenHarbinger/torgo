/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import org.tros.torgo.TorgoToolkit;

/**
 *
 * @author matta
 */
public abstract class PropertiesInitializer {

    private static final Object LOCK = new Object();
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(PropertiesInitializer.class);

    @SuppressWarnings("OverridableMethodCallInConstructor")
    protected PropertiesInitializer() {
        synchronized (LOCK) {
            this.initializeHelper();

            String dir = null;
            if (!BuildInfo.class.isAssignableFrom(this.getClass())) {
                dir = PathUtils.getApplicationConfigDirectory(TorgoToolkit.getBuildInfo());
            }

            this.initializeFromProperties();
            this.initializeFromProperties(dir);
        }
    }

    /**
     * Initialize from properties file if possible.
     *
     * @param dir the directory to search for properties files.
     */
    @CoverageIgnore
    private void initializeFromProperties(String dir) {
        if (dir == null) {
            return;
        }
        Properties prop = new Properties();

        String propFile = dir + '/' + this.getClass().getSimpleName() + ".properties";
        File f = new File(propFile);
        if (f.exists()) {

            try (FileInputStream fis = new FileInputStream(f)) {
                PropertyDescriptor[] props = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
                prop.load(fis);
                loadFromProperties(props, prop);
            } catch (IOException ex) {
            } catch (IntrospectionException ex) {
                LOGGER.warn(null, ex);
            }
        }
    }

    /**
     * Initialize from properties file if possible.
     */
    private void initializeFromProperties() {
        try {
            Properties prop = new Properties();

            String propFile = this.getClass().getPackage().getName().replace('.', '/') + '/' + this.getClass().getSimpleName() + ".properties";
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(propFile);
            ArrayList<URL> urls = new ArrayList<>();

            //HACK: semi sort classpath to put "files" first and "jars" second.
            //this has an impact once we are workin in tomcat where
            //the classes in tomcat are not stored in a jar.
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url.toString().startsWith("file:")) {
                    urls.add(0, url);
                } else {
                    boolean added = false;
                    for (int ii = 0; !added && ii < urls.size(); ii++) {
                        if (!urls.get(ii).toString().startsWith("file:")) {
                            urls.add(ii, url);
                            added = true;
                        }
                    }
                    if (!added) {
                        urls.add(url);
                    }
                }
            }

            //reverse the list, so that the item found first in the
            //classpath will be the last one run though and thus
            //be the one to set the final value
            Collections.reverse(urls);

            PropertyDescriptor[] props = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
            urls.forEach((url) -> {
                try {
                    prop.load(url.openStream());
                    loadFromProperties(props, prop);
                } catch (IOException | IllegalArgumentException ex) {
                }
            });
        } catch (IOException ex) {
        } catch (IntrospectionException ex) {
            LOGGER.warn(null, ex);
        }
    }

    private void loadFromProperties(PropertyDescriptor[] props, Properties prop) {
        try {
            ArrayList<String> propKeys = new ArrayList<>(prop.stringPropertyNames());
            for (PropertyDescriptor p : props) {
                if (p.getWriteMethod() != null
                        && p.getReadMethod() != null
                        && p.getReadMethod().getDeclaringClass() != Object.class) {
                    boolean success = false;
                    String val = prop.getProperty(p.getName());
                    if (val != null) {
                        Object o = TypeHandler.fromString(p.getPropertyType(), val);
                        if (o != null) {
                            p.getWriteMethod().invoke(this, o);
                            success = true;
                        }
                    }
                    if (!success && val != null) {
                        setValueHelper(p, val);
                    }
                }
            }
            propKeys.forEach((key) -> {
                String value = prop.getProperty(key);
                setNameValuePair(key, value);
            });
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
            LOGGER.warn(null, ex);
        }
    }

    /**
     * Set values.
     *
     * @param p the property descriptor.
     * @param value the value.
     * @return true if set, false otherwise.
     */
    @CoverageIgnore
    protected boolean setValueHelper(PropertyDescriptor p, String value) {
        return false;
    }

    /**
     * Set name value.
     *
     * @param name the name.
     * @param value the value.
     */
    protected void setNameValuePair(String name, String value) {
    }

    /**
     * helper method for initialization.
     *
     * Called during constructor. Derived classes should not initialize anything
     * in their constructor as that will over-write any values read in from
     * file.
     */
    protected void initializeHelper() {
    }

}
