/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.utils.TypeHandler;
import org.tros.utils.logging.Logger;
import org.tros.utils.logging.Logging;

/**
 * Convert Date/Calendar to/from String.
 *
 * @author matta
 */
public class DateConverter implements Converter, ConverterRegister {

    private static final Logger LOGGER = Logging.getLogFactory().getLogger(DateConverter.class);

    /**
     * Constructor.
     */
    public DateConverter() {
    }

    /**
     * Converts a string to date or calendar and vice versa.
     *
     * @param <T> type
     * @param type the target type
     * @param value the current value
     * @return new instance of a class.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (value == null) {
            return null;
        }
        if (Date.class.isAssignableFrom(value.getClass())) {
            Date val = (Date) value;
            return (T) TypeHandler.dateToString(val);
        } else if (Calendar.class.isAssignableFrom(value.getClass())) {
            Calendar val = (Calendar) value;
            return (T) TypeHandler.dateToString(val);
        } else if (Calendar.class.isAssignableFrom(type)) {
            String val = (String) value;
            try {
                return (T) TypeHandler.calendarFromString(val);
            } catch (ParseException ex) {
                LOGGER.fatal(null, ex);
            }
        } else if (Date.class.isAssignableFrom(type)) {
            String val = (String) value;
            try {
                return (T) TypeHandler.dateFromString(val);
            } catch (ParseException ex) {
                LOGGER.fatal(null, ex);
            }
        }
        return (T) value.toString();
    }

    /**
     * Get provided conversions.
     *
     * @return list of supported conversions.
     */
    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        List<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(String.class, Date.class));
        ret.add(new ImmutablePair<>(Date.class, String.class));
        ret.add(new ImmutablePair<>(String.class, Calendar.class));
        ret.add(new ImmutablePair<>(Calendar.class, String.class));
        return ret;
    }

    /**
     * Register the conversion types.
     *
     * @param convertUtilsBean registrar.
     */
    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(String.class);
        convertUtilsBean.deregister(Calendar.class);
        convertUtilsBean.deregister(Date.class);
        convertUtilsBean.register(this, String.class);
        convertUtilsBean.register(this, Calendar.class);
        convertUtilsBean.register(this, Date.class);
    }
}
