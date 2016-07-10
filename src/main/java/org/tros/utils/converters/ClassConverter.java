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

/**
 *
 * @author matta
 */
public class ClassConverter implements Converter, ConverterRegister {

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
        try {
            return (T) Class.forName(value.toString());
        } catch (ClassNotFoundException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(ClassConverter.class).fatal(null, ex);
        }
        return null;
    }

    /**
     * Get the provided conversions.
     *
     * @param convertUtilsBean
     */
    @Override
    public void register(ConvertUtilsBean convertUtilsBean) {
        convertUtilsBean.deregister(Class.class);
        convertUtilsBean.register(this, Class.class);
    }

    /**
     * Get the conversion types.
     *
     * @return
     */
    @Override
    public List<ImmutablePair<Class<?>, Class<?>>> getConversions() {
        ArrayList<ImmutablePair<Class<?>, Class<?>>> ret = new ArrayList<>();
        ret.add(new ImmutablePair<Class<?>, Class<?>>(String.class, Class.class));
        return ret;
    }
}
