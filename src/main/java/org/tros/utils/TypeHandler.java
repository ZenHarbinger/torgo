/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.text.ParseException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.tros.utils.converters.UtilsBeanFactory;

/**
 * Provides conversion from one object type to another.
 *
 * Often this is date to/from String. Color to/from String. But bean utils is
 * used to do just about anything under the sun.
 *
 * @author matta
 */
public final class TypeHandler {

    public static final FastDateFormat DEFAULT_DATE_FORMAT = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

    /**
     * Calendar to string.
     *
     * @param value
     * @return
     */
    public static String dateToString(final java.util.Calendar value) {
        return DEFAULT_DATE_FORMAT.format(value.getTime());
    }

    /**
     * Date to string.
     *
     * @param value
     * @return
     */
    public static String dateToString(final java.util.Date value) {
        return DEFAULT_DATE_FORMAT.format(value);
    }

    /**
     * Date from string.
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public static java.util.Date dateFromString(String value) throws ParseException {
        return DEFAULT_DATE_FORMAT.parse(value);
    }

    /**
     * String to calendar.
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public static java.util.Calendar calendarFromString(final String value) throws ParseException {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(dateFromString(value));
        return c;
    }

    /**
     * Color to hex string.
     *
     * @param color
     * @return
     */
    public static String colorToHex(final java.awt.Color color) {
        return toString(color);
    }

    /**
     * Convert string to specified object type.
     *
     * @param <T>
     * @param type
     * @param val
     * @return
     */
    public static <T> T fromString(final Class<T> type, final String val) {
        T o = convert(type, val);
        if (o == null) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Random.class).warn("Cannot convert {0} to {1}", new Object[]{val, type.getName()});
        }
        return o;
    }

    /**
     * Convert object to String.
     *
     * @param val
     * @return
     */
    public static String toString(final Object val) {
        String o = (String) convert(String.class, val);
        if (o == null) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Random.class).warn("Cannot convert {0} to {1}", new Object[]{val, String.class.getName()});
        }
        return o;
    }

    /**
     * Convert an object to a specified type.
     *
     * @param <T>
     * @param to
     * @param val
     * @return
     */
    public static <T> T convert(Class<T> to, Object val) {
        Converter lookup = UtilsBeanFactory.getConverter(val.getClass(), to);
        if (lookup != null) {
            return lookup.convert(to, val);
        }

        return null;
    }
}
