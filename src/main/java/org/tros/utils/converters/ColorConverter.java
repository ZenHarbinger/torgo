/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.utils.TypeHandler;

/**
 *
 * @author matta
 */
public class ColorConverter implements Converter, ConverterRegister {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (type == String.class) {
            if (value.getClass() == Color.class) {
                Color c = (Color) value;
                return (T) String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
            } else {
                return (T) value;
            }
        } else {
            try {
                return (T) Color.class.getField(value.toString()).get(null);
            } catch (NoSuchFieldException ex) {
                return (T) Color.decode(value.toString());
            } catch (SecurityException ex) {
            } catch (IllegalArgumentException ex) {
            } catch (IllegalAccessException ex) {
            }
            return (T) Color.decode(value.toString());
        }
    }

    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(String.class);
        convertUtilsBean.deregister(Color.class);
        convertUtilsBean.register(this, String.class);
        convertUtilsBean.register(this, Color.class);
    }

    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        ArrayList<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<ImmutablePair<Class<?>, Class<?>>>();
        ret.add(new ImmutablePair<Class<?>, Class<?>>(String.class, Color.class));
        ret.add(new ImmutablePair<Class<?>, Class<?>>(Color.class, String.class));
        return ret;
    }

    public static void main(String[] args) {
        Converter lookup = UtilsBeanFactory.getConverter(String.class, Color.class);
        String hex = "0x0dff00";
        Color convert = lookup.convert(Color.class, hex);
        System.out.println(convert.toString());
        convert = lookup.convert(Color.class, "blue");
        System.out.println(convert.toString());

        System.out.println(TypeHandler.fromString(Color.class, "0x0dff00").toString());
        System.out.println(TypeHandler.fromString(Color.class, "blue").toString());
        System.out.println(TypeHandler.colorToHex(Color.red));

        lookup = UtilsBeanFactory.getConverter(Color.class, String.class);
        hex = lookup.convert(String.class, convert);
        System.out.println(hex);
    }
}
