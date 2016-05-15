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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import org.tros.torgo.TorgoInfo;

/**
 *
 * @author matta
 */
public abstract class PropertiesInitializer {

    private static Object mapper;
    private static final Object lock = new Object();
    private static boolean _loading = false;
    private static final org.tros.utils.logging.Logger logger = org.tros.utils.logging.Logging.getLogFactory().getLogger(PropertiesInitializer.class);


    @SuppressWarnings("OverridableMethodCallInConstructor")
    protected PropertiesInitializer() {
        synchronized (lock) {
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
        try {
            Class<?> forName = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            mapper = forName.newInstance();
        } catch (ClassNotFoundException ex) {
            logger.debug("com.fasterxml.jackson.databind.ObjectMapper not in CLASSPATH...");
        } catch (InstantiationException ex) {
            logger.debug("com.fasterxml.jackson.databind.ObjectMapper not in CLASSPATH...");
        } catch (IllegalAccessException ex) {
            logger.debug("com.fasterxml.jackson.databind.ObjectMapper not in CLASSPATH...");
        }
    }

    private Object readValue(InputStream fis, Class<?> clazz) {
        try {
            Method method = mapper.getClass().getMethod("readValue", InputStream.class, Class.class);
            return method.invoke(mapper, fis, clazz);
        } catch (NoSuchMethodException ex) {
            logger.warn(null, ex);
        } catch (SecurityException ex) {
            logger.warn(null, ex);
        } catch (IllegalAccessException ex) {
            logger.warn(null, ex);
        } catch (IllegalArgumentException ex) {
            logger.warn(null, ex);
        } catch (InvocationTargetException ex) {
            logger.warn(null, ex);
        }
        return null;
    }

    private Object readValue(String fis, Class<?> clazz) {
        try {
            Method method = mapper.getClass().getMethod("readValue", String.class, Class.class);
            return method.invoke(mapper, fis, clazz);
        } catch (NoSuchMethodException ex) {
            logger.warn(null, ex);
        } catch (SecurityException ex) {
            logger.warn(null, ex);
        } catch (IllegalAccessException ex) {
            logger.warn(null, ex);
        } catch (IllegalArgumentException ex) {
            logger.warn(null, ex);
        } catch (InvocationTargetException ex) {
            logger.warn(null, ex);
        }
        return null;
    }

    private void initializeFromJson(String dir) {
        if (dir == null) {
            return;
        }
        String propFile = dir + '/' + this.getClass().getSimpleName() + ".properties";
        File f = new File(propFile);
        if (f.exists()) {

            try {
                FileInputStream fis = new FileInputStream(f);
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
                fis.close();
            } catch (NullPointerException ex) {
                logger.debug(null, ex);
            } catch (IOException ex) {
                logger.debug(null, ex);
            } catch (IllegalArgumentException ex) {
                logger.debug(null, ex);
            } catch (InvocationTargetException ex) {
                logger.debug(null, ex);
            } catch (IllegalAccessException ex) {
                logger.debug(null, ex);
            } catch (IntrospectionException ex) {
                logger.warn(null, ex);
            }
        }
    }

    private void initializeFromJson() {
        try {
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader()
                    .getResources(this.getClass().getCanonicalName().replace('.', '/') + ".json");
            ArrayList<URL> urls = new ArrayList<URL>();

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
                } catch (NullPointerException ex) {
                } catch (IOException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (InvocationTargetException ex) {
                } catch (IllegalAccessException ex) {
                    logger.debug(null, ex);
                }
            }
        } catch (IOException ex) {
        } catch (IntrospectionException ex) {
            logger.warn(null, ex);
        }
    }

    private void initializeFromProperties(String dir) {
        if (dir == null) {
            return;
        }
        Properties prop = new Properties();

        String propFile = dir + '/' + this.getClass().getSimpleName() + ".properties";
        File f = new File(propFile);
        if (f.exists()) {

            try {
                FileInputStream fis = new FileInputStream(f);
                PropertyDescriptor[] props = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
                prop.load(fis);

                ArrayList<String> propKeys = new ArrayList<String>(prop.stringPropertyNames());
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
                                    logger.warn(null, ex);
                                    logger.warn(MessageFormat.format("PropertyName: {0}", new Object[]{val}));
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
                fis.close();
            } catch (NullPointerException ex) {
            } catch (IOException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
            } catch (IllegalAccessException ex) {
                logger.debug(null, ex);
            } catch (IntrospectionException ex) {
                logger.warn(null, ex);
            }
        }
    }

    private void initializeFromProperties() {
        try {
            Properties prop = new Properties();

            String propFile = this.getClass().getPackage().getName().replace('.', '/') + '/' + this.getClass().getSimpleName() + ".properties";
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(propFile);
            ArrayList<URL> urls = new ArrayList<URL>();

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
                    ArrayList<String> propKeys = new ArrayList<String>(prop.stringPropertyNames());

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
                                        logger.warn(null, ex);
                                        logger.warn(MessageFormat.format("PropertyName: {0}", new Object[]{val}));
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
                } catch (NullPointerException ex) {
                } catch (IOException ex) {
                } catch (IllegalArgumentException ex) {
                } catch (InvocationTargetException ex) {
                } catch (IllegalAccessException ex) {
                    logger.warn(null, ex);
                }
            }
        } catch (IOException ex) {
        } catch (IntrospectionException ex) {
            logger.warn(null, ex);
        }
    }

    protected boolean setValueHelper(PropertyDescriptor p, String value) {
        return false;
    }

    protected void setNameValuePair(String name, String value) {
    }

    protected void initializeHelper() {
    }

    public String displayName() {
        return this.getClass().getCanonicalName();
    }

}
