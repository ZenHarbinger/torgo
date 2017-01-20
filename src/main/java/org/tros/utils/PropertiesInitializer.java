/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 *
 * @author matta
 */
public abstract class PropertiesInitializer {

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(PropertiesInitializer.class);

    protected PropertiesInitializer() {
        this.initializeFromProperties();
    }

    /**
     * Initialize from properties file if possible.
     */
    private void initializeFromProperties() {
        String name = this.getClass().getCanonicalName();
        name = name.replace(".", "/");
        ResourceBundle resources = ResourceBundle.getBundle(name);
        try {
            PropertyDescriptor[] thisProps = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
            for(PropertyDescriptor pd : thisProps) {
                 if(resources.containsKey(pd.getName())) {
                     Method writeMethod = pd.getWriteMethod();
                     if(writeMethod != null) {
                         writeMethod.invoke(this, resources.getString(pd.getName()));
                     }
                 }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOGGER.warn(null, ex);
        }
    }

}
