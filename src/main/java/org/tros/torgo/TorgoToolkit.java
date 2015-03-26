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

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matta
 */
public final class TorgoToolkit {

    private static final HashMap<String, Controller> controllersMap;
    private static final ServiceLoader<Controller> controllers;

    private static final HashMap<String, InterpreterVisualization> vizMap;
    private static final ServiceLoader<InterpreterVisualization> vizualizers;

    private static final Logger logger = Logger.getLogger(TorgoToolkit.class.getName());

    /**
     * Static constructor.
     */
    static {
        controllersMap = new HashMap<>();
        controllers = ServiceLoader.load(Controller.class);
        try {
            Iterator<Controller> controllers_it = controllers.iterator();
            while (controllers_it.hasNext()) {
                Controller c = controllers_it.next();
                logger.log(Level.INFO, "Loaded: {0}", c.getClass().getName());
                controllersMap.put(c.getLang(), c);
            }
        } catch (ServiceConfigurationError serviceError) {
            logger.log(Level.WARNING, null, serviceError);
        }
        vizMap = new HashMap<>();
        vizualizers = ServiceLoader.load(InterpreterVisualization.class);
        try {
            Iterator<InterpreterVisualization> controllers_it = vizualizers.iterator();
            while (controllers_it.hasNext()) {
                InterpreterVisualization c = controllers_it.next();
                logger.log(Level.INFO, "Loaded: {0}", c.getClass().getName());
                vizMap.put(c.getName(), c);
            }
        } catch (ServiceConfigurationError serviceError) {
            logger.log(Level.WARNING, null, serviceError);
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
