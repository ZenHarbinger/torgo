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
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.tros.torgo.TorgoInfo;

/**
 *
 * @author matta
 */
public abstract class PropertiesInitializer {

    private static final Object MAPPER;
    private static final Object LOCK = new Object();
    private static boolean _loading = false;
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(PropertiesInitializer.class);

    static {
        Object m = null;
        try {
            Class<?> forName = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            m = forName.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOGGER.debug("com.fasterxml.jackson.databind.ObjectMapper not in CLASSPATH...");
        }
        MAPPER = m;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    protected PropertiesInitializer() {
        synchronized (LOCK) {
            this.initializeHelper();

            String dir = null;
            if (!BuildInfo.class.isAssignableFrom(this.getClass())) {
                dir = PathUtils.getApplicationConfigDirectory(TorgoInfo.INSTANCE);
            }

            if (!_loading) {
                _loading = true;
                this.initializeFromJson();
                this.initializeFromJson(dir);
                _loading = false;
            }
            this.initializeFromProperties();
            this.initializeFromProperties(dir);
        }
    }

    public static boolean canCopy() {
        return MAPPER != null;
    }

    /**
     * Create a copy of the current object.
     *
     * @return
     */
    public PropertiesInitializer copy() {
        java.io.StringWriter sw = new java.io.StringWriter();
        writeValue(sw, this);

        java.io.StringReader sr = new StringReader(sw.toString());
        PropertiesInitializer c2 = null;
        try {
            c2 = (PropertiesInitializer) readValue(IOUtils.toString(sr), this.getClass());
        } catch (IOException ex) {
            LOGGER.warn(null, ex);
        }

        return c2;
    }

    /**
     * Copy values from the specified object.
     *
     * @param cb
     */
    public void copy(PropertiesInitializer cb) {
        try {
            PropertyDescriptor[] thisProps = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
            PropertyDescriptor[] cbProps = Introspector.getBeanInfo(cb.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor thisP : thisProps) {
                for (PropertyDescriptor cbP : cbProps) {
                    if (thisP.getName().equals(cbP.getName())
                            && thisP.getPropertyType().equals(cbP.getPropertyType())
                            && thisP.getWriteMethod() != null && cbP.getReadMethod() != null) {
                        thisP.getWriteMethod().invoke(this, cbP.getReadMethod().invoke(cb));
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.warn(null, ex);
        }
    }

    private Object readValue(InputStream fis, Class<?> clazz) {
        if (MAPPER != null) {
            try {
                Method method = MAPPER.getClass().getMethod("readValue", InputStream.class, Class.class);
                return method.invoke(MAPPER, fis, clazz);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.warn(null, ex);
            }
        }
        return null;
    }

    private Object readValue(String fis, Class<?> clazz) {
        if (MAPPER != null) {
            try {
                Method method = MAPPER.getClass().getMethod("readValue", String.class, Class.class);
                return method.invoke(MAPPER, fis, clazz);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.warn(null, ex);
            }
        }
        return null;
    }

    private Object writeValue(Writer fis, Object obj) {
        if (MAPPER != null) {
            try {
                Method method = MAPPER.getClass().getMethod("writeValue", Writer.class, Object.class);
                return method.invoke(MAPPER, fis, obj);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOGGER.warn(null, ex);
            }
        }
        return null;
    }

    /**
     * Initialize from JSON if possible.
     *
     * @param dir
     */
    private void initializeFromJson(String dir) {
        if (dir == null) {
            return;
        }
        String propFile = dir + '/' + this.getClass().getSimpleName() + ".properties";
        File f = new File(propFile);
        if (f.exists()) {

            try (FileInputStream fis = new FileInputStream(f)) {
                PropertyDescriptor[] props = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
                PropertiesInitializer obj = (PropertiesInitializer) readValue(fis, this.getClass());

                for (PropertyDescriptor p : props) {
                    if (p.getWriteMethod() != null
                            && p.getReadMethod() != null
                            && p.getReadMethod().getDeclaringClass() != Object.class) {
                        Object val = p.getReadMethod().invoke(obj);
                        if (val != null) {
                            p.getWriteMethod().invoke(this, val);
                        }
                    }
                }
            } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                LOGGER.debug(null, ex);
            } catch (IntrospectionException ex) {
                LOGGER.warn(null, ex);
            }
        }
    }

    /**
     * Initialize from JSON if possible.
     */
    private void initializeFromJson() {
        try {
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader()
                    .getResources(this.getClass().getCanonicalName().replace('.', '/') + ".json");
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
            for (URL url : urls) {
                try {
                    PropertiesInitializer obj = (PropertiesInitializer) readValue(url.openStream(), this.getClass());

                    for (PropertyDescriptor p : props) {
                        if (p.getWriteMethod() != null
                                && p.getReadMethod() != null
                                && p.getReadMethod().getDeclaringClass() != Object.class) {
                            Object val = p.getReadMethod().invoke(obj);
                            if (val != null) {
                                p.getWriteMethod().invoke(this, val);
                            }
                        }
                    }
                } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                    LOGGER.debug(null, ex);
                }
            }
        } catch (IOException ex) {
        } catch (IntrospectionException ex) {
            LOGGER.warn(null, ex);
        }
    }

    /**
     * Initialize from properties file if possible.
     *
     * @param dir
     */
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

                ArrayList<String> propKeys = new ArrayList<>(prop.stringPropertyNames());
                for (PropertyDescriptor p : props) {
                    if (p.getWriteMethod() != null
                            && p.getReadMethod() != null
                            && p.getReadMethod().getDeclaringClass() != Object.class) {
                        boolean success = false;
                        String val = prop.getProperty(p.getName());
                        if (val != null) {
                            Object o = TypeHandler.fromString(p.getPropertyType(), val);
                            if (o == null) {
                                try {
                                    o = readValue(val, p.getPropertyType());
                                } catch (Exception ex) {
                                    o = null;
                                    LOGGER.warn(null, ex);
                                    LOGGER.warn(MessageFormat.format("PropertyName: {0}", new Object[]{val}));
                                }
                            }
                            if (o != null) {
                                p.getWriteMethod().invoke(this, o);
                                success = true;
                            }
                        }
                        if (!success && val != null) {
//                            if (TypeHandler.isEnumeratedType(p)) {
////                                setEnumerated(p, val);
//                            } else {
                            setValueHelper(p, val);
//                            }
                        }
                    }
                }
                for (String key : propKeys) {
                    String value = prop.getProperty(key);
                    setNameValuePair(key, value);
                }
            } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException ex) {
            } catch (IllegalAccessException ex) {
                LOGGER.debug(null, ex);
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
            for (URL url : urls) {
                try {
                    prop.load(url.openStream());
                    ArrayList<String> propKeys = new ArrayList<>(prop.stringPropertyNames());

                    for (PropertyDescriptor p : props) {
                        if (p.getWriteMethod() != null
                                && p.getReadMethod() != null
                                && p.getReadMethod().getDeclaringClass() != Object.class) {
                            boolean success = false;
                            String val = prop.getProperty(p.getName());
                            if (val != null) {
                                Object o = TypeHandler.fromString(p.getPropertyType(), val);
                                if (o == null) {
                                    try {
                                        o = readValue(val, p.getPropertyType());
                                    } catch (Exception ex) {
                                        o = null;
                                        LOGGER.warn(null, ex);
                                        LOGGER.warn(MessageFormat.format("PropertyName: {0}", new Object[]{val}));
                                    }
                                }
                                if (o != null) {
                                    p.getWriteMethod().invoke(this, o);
                                    success = true;
                                }
                            }
                            if (!success && val != null) {
//                                if (TypeHandler.isEnumeratedType(p)) {
////                                    success = setEnumerated(p, val);
//                                } else {
                                success = setValueHelper(p, val);
//                                }
                            }
                            if (success) {
                                propKeys.remove(p.getName());
                            }
                        }
                    }
                    for (String key : propKeys) {
                        String value = prop.getProperty(key);
                        setNameValuePair(key, value);
                    }
                } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException ex) {
                } catch (IllegalAccessException ex) {
                    LOGGER.warn(null, ex);
                }
            }
        } catch (IOException ex) {
        } catch (IntrospectionException ex) {
            LOGGER.warn(null, ex);
        }
    }

    /**
     * Set values.
     *
     * @param p
     * @param value
     * @return
     */
    protected boolean setValueHelper(PropertyDescriptor p, String value) {
        return false;
    }

    /**
     * Set name value.
     *
     * @param name
     * @param value
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

    /**
     * Get a display name.
     *
     * @return
     */
    public String displayName() {
        return this.getClass().getCanonicalName();
    }

}
