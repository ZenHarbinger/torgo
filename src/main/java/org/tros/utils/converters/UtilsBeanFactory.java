/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Custom wrapper that allows a component wise conversion of types.
 *
 * A single object does not have to know how to do all conversions concerning a
 * type, they can be added piece-by-piece.
 *
 * @author matta
 */
public final class UtilsBeanFactory {

    private static final Map<Class<?>, ArrayList<ImmutablePair<Class<?>, BeanUtilsBean>>> MAP;
    private static final boolean TWO_PARAM_LOOKUP;

    private UtilsBeanFactory() {
    }

    /**
     * Static Constructor.
     */
    static {
        MAP = new HashMap<>();

        ServiceLoader<ConverterRegister> crs = ServiceLoader.load(ConverterRegister.class);
        for (ConverterRegister cr : crs) {
            ConvertUtilsBean cub = new ConvertUtilsBean();
            BeanUtilsBean bub = new BeanUtilsBean(cub, new PropertyUtilsBean());
            cr.register(cub);
            List<ImmutablePair<Class<?>, Class<?>>> pairs = cr.getConversions();
            for (ImmutablePair<Class<?>, Class<?>> pair : pairs) {
                if (!MAP.containsKey(pair.getLeft())) {
                    MAP.put(pair.getLeft(), new ArrayList<>());
                }
                ArrayList<ImmutablePair<Class<?>, BeanUtilsBean>> list = MAP.get(pair.getLeft());
                list.add(new ImmutablePair<>(pair.getRight(), bub));
            }
        }
        boolean lookup = true;
        Class<?> c = BeanUtilsBean.getInstance().getConvertUtils().getClass();
        try {
            c.getMethod("lookup", Class.class, Class.class);
        } catch (NoSuchMethodException | SecurityException ex) {
            lookup = false;
        }
        TWO_PARAM_LOOKUP = lookup;
    }

    /**
     * Get a converter that goes from From to To.
     *
     * @param from the original type
     * @param to the target type
     * @return a converter that will go from the 'from' to 'to'.
     */
    public static Converter getConverter(Class<?> from, Class<?> to) {
        if (TWO_PARAM_LOOKUP) {
            if (MAP.containsKey(from)) {
                ArrayList<ImmutablePair<Class<?>, BeanUtilsBean>> m2 = MAP.get(from);
                for (ImmutablePair<Class<?>, BeanUtilsBean> p : m2) {
                    if (p.getLeft() == to) {
                        return p.getRight().getConvertUtils().lookup(from, to);
                    }
                }
            } else {
                for (Class<?> c : MAP.keySet()) {
                    if (c.isAssignableFrom(from)) {
                        ArrayList<ImmutablePair<Class<?>, BeanUtilsBean>> m2 = MAP.get(c);
                        for (ImmutablePair<Class<?>, BeanUtilsBean> p : m2) {
                            if (p.getLeft() == to) {
                                return p.getRight().getConvertUtils().lookup(from, to);
                            }
                        }
                    }
                }
            }
            return BeanUtilsBean.getInstance().getConvertUtils().lookup(from, to);
        } else {
            return BeanUtilsBean.getInstance().getConvertUtils().lookup(from);
        }
    }
}
