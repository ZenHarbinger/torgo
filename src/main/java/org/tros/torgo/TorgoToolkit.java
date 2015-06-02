/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tros.torgo;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author matta
 */
public final class TorgoToolkit {

    private static final HashMap<String, Controller> controllersMap;
    private static final ServiceLoader<Controller> controllers;

    private static final HashMap<String, InterpreterVisualization> vizMap;
    private static final ServiceLoader<InterpreterVisualization> vizualizers;

    private static final Log logger = LogFactory.getLog(TorgoToolkit.class);

    /**
     * Static constructor.
     */
    static {
        controllersMap = new HashMap<String, Controller>();
        controllers = ServiceLoader.load(Controller.class);
        try {
            for(Controller controller : controllers) {
                logger.info(MessageFormat.format("Loaded: {0}", controller.getClass().getName()));
                controllersMap.put(controller.getLang(), controller);
            }
        } catch (ServiceConfigurationError serviceError) {
            logger.warn(null, serviceError);
        }
        vizMap = new HashMap<String, InterpreterVisualization>();
        vizualizers = ServiceLoader.load(InterpreterVisualization.class);
        try {
            Iterator<InterpreterVisualization> controllers_it = vizualizers.iterator();
            for(InterpreterVisualization viz : vizualizers) {
                logger.info(MessageFormat.format("Loaded: {0}", viz.getClass().getName()));
                vizMap.put(viz.getName(), viz);
            }
        } catch (ServiceConfigurationError serviceError) {
            logger.warn(null, serviceError);
        }
    }

    /**
     * Hidden constructor.
     */
    private TorgoToolkit() {

    }

    /**
     * Get a desired controller.
     *
     * @param name
     * @return
     */
    public static Controller getController(String name) {
        return controllersMap.get(name);
    }

    /**
     * Get the name of all available controllers.
     *
     * @return
     */
    public static Set<String> getToolkits() {
        return controllersMap.keySet();
    }

    /**
     * Get the name of all available visualizers.
     *
     * @return
     */
    public static Set<String> getVisualizers() {
        return vizMap.keySet();
    }

    /**
     * Get a desired visualizer.
     *
     * @param name
     * @return
     */
    public static InterpreterVisualization getVisualization(String name) {
        return vizMap.get(name);
    }
}
