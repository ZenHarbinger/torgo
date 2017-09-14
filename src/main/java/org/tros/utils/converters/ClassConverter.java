/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.utils.logging.Logger;
import org.tros.utils.logging.Logging;

/**
 * Converts a string to a Class type.
 *
 * @author matta
 */
public class ClassConverter implements Converter, ConverterRegister {

    private static final Logger LOGGER = Logging.getLogFactory().getLogger(ClassConverter.class);

    /**
     * Convert an object to a class type.
     *
     * The object is created using Class.forName(value.toString());
     *
     * @param <T> type
     * @param type the target type
     * @param value the current value
     * @return new instance of a class.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        try {
            return (T) Class.forName(value.toString());
        } catch (ClassNotFoundException ex) {
            LOGGER.fatal(null, ex);
        }
        return null;
    }

    /**
     * Get the provided conversions.
     *
     * @param convertUtilsBean register conversions.
     */
    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(Class.class);
        convertUtilsBean.register(this, Class.class);
    }

    /**
     * Get the conversion types.
     *
     * @return conversions supported.
     */
    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        List<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<>(String.class, Class.class));
        return ret;
    }
}
