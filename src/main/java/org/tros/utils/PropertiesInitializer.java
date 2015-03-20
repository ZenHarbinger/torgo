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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tros.torgo.TorgoInfo;

/**
 *
 * @author matta
 */
public abstract class PropertiesInitializer {

    private static Object mapper;
    private static final Object lock = new Object();
    private static boolean _loading = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    protected PropertiesInitializer() {
        synchronized (lock) {
            this.initializeHelper();
            
            String dir = null;
            if (!IBuildInfo.class.isAssignableFrom(this.getClass())) {
                dir = PathUtils.getApplicationConfigDirectory(TorgoInfo.Instance);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.FINER, "com.fasterxml.jackson.databind.ObjectMapper not in CLASSPATH...");
        }
    }

    private Object readValue(InputStream fis, Class<?> clazz) {
        try {
            Method method = mapper.getClass().getMethod("readValue", InputStream.class, Class.class);
            return method.invoke(mapper, fis, clazz);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Object readValue(String fis, Class<?> clazz) {
        try {
            Method method = mapper.getClass().getMethod("readValue", String.class, Class.class);
            return method.invoke(mapper, fis, clazz);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.FINEST, null, ex);
            } catch (IntrospectionException ex) {
                Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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
            urls.stream().forEach((url) -> {
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
                    Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.FINEST, null, ex);
                }
            });
        } catch (IOException | IntrospectionException ex) {
            Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
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
                                    //Logger.getLogger(ConfigBase.class.getName()).log(Level.SEVERE, null, ex);
                                    Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.WARNING, null, ex);
                                    Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.WARNING, "PropertyName: {0}", new Object[]{val});
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
                propKeys.stream().forEach((key) -> {
                    String value = prop.getProperty(key);
                    setNameValuePair(key, value);
                });
            } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.FINEST, null, ex);
            } catch (IntrospectionException ex) {
                Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

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
            urls.stream().forEach((url) -> {
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
                                        //Logger.getLogger(ConfigBase.class.getName()).log(Level.SEVERE, null, ex);
                                        Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.WARNING, null, ex);
                                        Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.WARNING, "PropertyName: {0}", new Object[]{val});
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
                    propKeys.stream().forEach((key) -> {
                        String value = prop.getProperty(key);
                        setNameValuePair(key, value);
                    });
                } catch (NullPointerException | IOException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
                    Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException | IntrospectionException ex) {
            Logger.getLogger(PropertiesInitializer.class.getName()).log(Level.SEVERE, null, ex);
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
