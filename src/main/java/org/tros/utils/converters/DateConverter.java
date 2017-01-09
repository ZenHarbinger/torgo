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

/**
 * Convert Date/Calendar to/from String.
 *
 * @author matta
 */
public class DateConverter implements Converter, ConverterRegister {

    /**
     * Constructor.
     */
    public DateConverter() {
    }

    /**
     * Convert.
     *
     * @param <T>
     * @param type
     * @param value
     * @return
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
                org.tros.utils.logging.Logging.getLogFactory().getLogger(DateConverter.class).fatal(null, ex);
            }
        } else if (Date.class.isAssignableFrom(type)) {
            String val = (String) value;
            try {
                return (T) TypeHandler.dateFromString(val);
            } catch (ParseException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(DateConverter.class).fatal(null, ex);
            }
        }
        return (T) value.toString();
    }

    /**
     * Get provided conversions.
     *
     * @return
     */
    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        ArrayList<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(String.class, Date.class));
        ret.add(new ImmutablePair<>(Date.class, String.class));
        ret.add(new ImmutablePair<>(String.class, Calendar.class));
        ret.add(new ImmutablePair<>(Calendar.class, String.class));
        return ret;
    }

    /**
     * Register conversion types.
     *
     * @param convertUtilsBean
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

//    public static void main(String[] args) {
//        Converter lookup = UtilsBeanFactory.getConverter(Date.class, String.class);
//        String hex = TypeHandler.dateToString(Calendar.getInstance());
//
//        Date convert = lookup.convert(Date.class, hex);
//        System.out.println(convert.toString());
//    }
}
