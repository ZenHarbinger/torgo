/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author matta
 */
public final class TypeHandler {

    public static final FastDateFormat DEFAULT_DATE_FORMAT = DateFormatUtils.ISO_DATETIME_FORMAT;

    private static final java.util.Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static final java.util.HashMap<Class<?>, Class<?>> WRAPPER_LOOKUP = getWrapperTypes2();

    private TypeHandler() {
    }

    public static boolean isWrapperType(final Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static Class<?> getWrapperType(final Class<?> clazz) {
        if (WRAPPER_LOOKUP.containsKey(clazz)) {
            return WRAPPER_LOOKUP.get(clazz);
        }
        return null;
    }

    private static java.util.Set<Class<?>> getWrapperTypes() {
        java.util.Set<Class<?>> ret = new java.util.HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
    }

    private static java.util.HashMap<Class<?>, Class<?>> getWrapperTypes2() {
        java.util.HashMap<Class<?>, Class<?>> ret = new java.util.HashMap<Class<?>, Class<?>>();
        ret.put(boolean.class, Boolean.class);
        ret.put(char.class, Character.class);
        ret.put(byte.class, Byte.class);
        ret.put(short.class, Short.class);
        ret.put(int.class, Integer.class);
        ret.put(long.class, Long.class);
        ret.put(float.class, Float.class);
        ret.put(double.class, Double.class);
        ret.put(void.class, Void.class);
        return ret;
    }

    public static boolean isPrimitive(final Class<?> type) {
        return WRAPPER_LOOKUP.containsKey(type);
    }

    public static String dateToString(final java.util.Calendar value) {
        return DEFAULT_DATE_FORMAT.format(value.getTime());
    }

    public static String dateToString(final java.util.Date value) {
        return DEFAULT_DATE_FORMAT.format(value);
    }

    public static java.util.Date dateFromString(String value) throws ParseException {
        return DEFAULT_DATE_FORMAT.parse(value);
    }

    public static java.util.Calendar calendarFromString(final String value) throws ParseException {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(dateFromString(value));
        return c;
    }

    public static String colorToHex(final java.awt.Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Object fromString(final Class<?> type, final String val) {
        //handle/pass-through string
        if (type == String.class) {
            return val;
        }

        //handle Calendar
        if (type == java.util.Calendar.class) {
            java.util.Calendar inst = java.util.Calendar.getInstance();
            try {
                inst.setTime(DEFAULT_DATE_FORMAT.parse(val));
                return inst;
            } catch (ParseException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
        } else if (type == java.util.Date.class) {
            try {
                java.util.Date inst = DEFAULT_DATE_FORMAT.parse(val);
                return inst;
            } catch (ParseException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
        } else if (type == java.awt.Color.class) {
            try {
                return (java.awt.Color) java.awt.Color.class.getField(val).get(null);
            } catch (NoSuchFieldException ex) {
                return java.awt.Color.decode(val);
                //Logger.getLogger(TypeHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            } catch (IllegalArgumentException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            } catch (IllegalAccessException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
            return java.awt.Color.decode(val);
        } else if (type == Class.class) {
            try {
                return Class.forName(val);
            } catch (ClassNotFoundException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
        }

        Class<?> wrap = getWrapperType(type);
        if (wrap == null) {
            wrap = type;
        }

        String name = wrap.getSimpleName();
        java.lang.reflect.Method method = null;
        try {
            method = wrap.getMethod("parse" + name, String.class);
        } catch (NoSuchMethodException ex) {
            try {
                //HACK: Integer isn't parseInteger, it's parseInt
                method = wrap.getMethod("parseInt", String.class);
            } catch (NoSuchMethodException ex1) {
//                Logger.getLogger(TypeHandler.class.getName()).log(Level.FINEST, null, ex1);
            } catch (SecurityException ex1) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
        } catch (SecurityException ex) {
            LogFactory.getLog(TypeHandler.class).fatal(null, ex);
        }
        if (method != null) {
            try {
                return method.invoke(null, val);
            } catch (IllegalAccessException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
                LogFactory.getLog(TypeHandler.class).fatal(null, ex);
            }
        } else {
            try {
                return type.getConstructor(String.class).newInstance(val);
            } catch (NoSuchMethodException ex) {
            } catch (SecurityException ex) {
            } catch (InstantiationException ex) {
            } catch (IllegalAccessException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(TypeHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            return Class.forName(val).newInstance();
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
            //Logger.getLogger(TypeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
