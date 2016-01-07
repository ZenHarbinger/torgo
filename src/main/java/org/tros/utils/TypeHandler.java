/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.tros.utils.converters.UtilsBeanFactory;

/**
 *
 * @author matta
 */
public final class TypeHandler {

    public static final FastDateFormat DEFAULT_DATE_FORMAT = DateFormatUtils.ISO_DATETIME_FORMAT;

    private static final java.util.Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
    private static final java.util.HashMap<Class<?>, Class<?>> WRAPPER_LOOKUP = getWrapperTypes2();
    
    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(TypeHandler.class);


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
        return toString(color);
    }

    public static Object fromString(final Class<?> type, final String val) {
        Object o = convert(type, val);
        if (o == null) {
            Logger.getLogger(TypeHandler.class.getName()).log(Level.WARNING, "Cannot convert {0} to {1}", new Object[]{val, type.getName()});
        }
        return o;
    }

    public static String toString(final Object val) {
        String o = (String) convert(String.class, val);
        if (o == null) {
            Logger.getLogger(TypeHandler.class.getName()).log(Level.WARNING, "Cannot convert {0} to {1}", new Object[]{val, String.class.getName()});
        }
        return o;
    }

    public static <T> T convert(Class<T> to, Object val) {
        Converter lookup = UtilsBeanFactory.getConverter(val.getClass(), to);
        if (lookup != null) {
            return lookup.convert(to, val);
        }

        return null;
    }
}
