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

/**
 * Convert Color to/from String.
 *
 * @author matta
 */
public class ColorConverter implements Converter, ConverterRegister {

    /**
     * Converts a string to color and vice versa.
     *
     * @param <T> type
     * @param type the target type
     * @param value the current value
     * @return new instance of a class.
     */
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
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            }
            return (T) Color.decode(value.toString());
        }
    }

    /**
     * Register the conversion types.
     *
     * @param convertUtilsBean registrar.
     */
    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(String.class);
        convertUtilsBean.deregister(Color.class);
        convertUtilsBean.register(this, String.class);
        convertUtilsBean.register(this, Color.class);
    }

    /**
     * Get provided conversions.
     *
     * @return list of supported conversions.
     */
    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        List<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(String.class, Color.class));
        ret.add(new ImmutablePair<>(Color.class, String.class));
        return ret;
    }

}
