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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.utils.TypeHandler;

/**
 *
 * @author matta
 */
public class DateConverter implements Converter, ConverterRegister {

    public DateConverter() {
    }

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
                Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (Date.class.isAssignableFrom(type)) {
            String val = (String) value;
            try {
                return (T) TypeHandler.dateFromString(val);
            } catch (ParseException ex) {
                Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (T) value.toString();
    }

    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        ArrayList<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<ImmutablePair<Class<?>, Class<?>>>();
        ret.add(new ImmutablePair<Class<?>, Class<?>>(String.class, Date.class));
        ret.add(new ImmutablePair<Class<?>, Class<?>>(Date.class, String.class));
        ret.add(new ImmutablePair<Class<?>, Class<?>>(String.class, Calendar.class));
        ret.add(new ImmutablePair<Class<?>, Class<?>>(Calendar.class, String.class));
        return ret;
    }

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
