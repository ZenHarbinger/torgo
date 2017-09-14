/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.util.List;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 *
 * @author matta
 */
public interface ConverterRegister {

    /**
     * Register the conversion types.
     *
     * @param convertUtilsBean registrar.
     */
    void register(ConvertUtilsBean convertUtilsBean);

    /**
     * Get a list of conversions From one class type (Left) to another (Right).
     *
     * @return list of supported conversions.
     */
    List<ImmutablePair<Class<?>, Class<?>>> getConversions();

}
