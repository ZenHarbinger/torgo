/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 *
 * @author matta
 */
public class ClassConverter implements Converter, ConverterRegister {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        try {
            return (T) Class.forName(value.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(Class.class);
        convertUtilsBean.register(this, Class.class);
    }

    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        ArrayList<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<ImmutablePair<Class<?>, Class<?>>>();
        ret.add(new ImmutablePair<Class<?>, Class<?>>(String.class, Class.class));
        return ret;
    }

//    public static void main(String[] args) {
//        Converter lookup = UtilsBeanFactory.getConverter(String.class, Class.class);
//        String hex = ClassConverter.class.getName();
//        Class convert = lookup.convert(Class.class, hex);
//        System.out.println(convert.getName());
//        Class fromString = (Class) TypeHandler.fromString(Class.class, hex);
//        System.out.println(fromString.getName());
//    }
}
