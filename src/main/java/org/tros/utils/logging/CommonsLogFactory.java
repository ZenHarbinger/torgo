/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import java.util.HashMap;

/**
 *
 * @author matta
 */
public final class CommonsLogFactory implements LogFactory {

    private static final HashMap<String, Logger> instances = new HashMap<String, Logger>();

    @Override
    public Logger getLogger(Class<?> c) {
        return getLogger(c.getName());
    }

    @Override
    public Logger getLogger(String name) {
        synchronized (instances) {
            if (!instances.containsKey(name)) {
                instances.put(name, new CommonsLogger(org.apache.commons.logging.LogFactory.getLog(name)));
            }
        }
        return instances.get(name);
    }

}
