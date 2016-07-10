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
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

/**
 *
 * @author matta
 */
public final class TorgoToolkit {

    private static final HashMap<String, Controller> CONTROLLER_MAP;
    private static final ServiceLoader<Controller> CONTROLLERS;

    private static final HashMap<String, InterpreterVisualization> VIZ_MAP;
    private static final ServiceLoader<InterpreterVisualization> VIZUALIZERS;

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoToolkit.class);

    /**
     * Static constructor.
     */
    static {
        CONTROLLER_MAP = new HashMap<>();
        CONTROLLERS = ServiceLoader.load(Controller.class);
        try {
            for (Controller controller : CONTROLLERS) {
                LOGGER.info(MessageFormat.format("Loaded: {0}", controller.getClass().getName()));
                CONTROLLER_MAP.put(controller.getLang(), controller);
            }
        } catch (ServiceConfigurationError serviceError) {
            LOGGER.warn(null, serviceError);
        }
        VIZ_MAP = new HashMap<>();
        VIZUALIZERS = ServiceLoader.load(InterpreterVisualization.class);
        try {
            for (InterpreterVisualization viz : VIZUALIZERS) {
                LOGGER.info(MessageFormat.format("Loaded: {0}", viz.getClass().getName()));
                VIZ_MAP.put(viz.getName(), viz);
            }
        } catch (ServiceConfigurationError serviceError) {
            LOGGER.warn(null, serviceError);
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
     * @param name The desired language controller to get.
     * @return Controller of the desired type.
     */
    public static Controller getController(String name) {
        return CONTROLLER_MAP.get(name);
    }

    /**
     * Get the name of all available controllers.
     *
     * @return Get a list of controllers available.
     */
    public static Set<String> getToolkits() {
        return CONTROLLER_MAP.keySet();
    }

    /**
     * Get the name of all available visualizers.
     *
     * @return Get a list of visualizers available.
     */
    public static Set<String> getVisualizers() {
        return VIZ_MAP.keySet();
    }

    /**
     * Get a desired visualizer.
     *
     * @param name The desired visualizer to get.
     * @return The desired visualizer.
     */
    public static InterpreterVisualization getVisualization(String name) {
        return VIZ_MAP.get(name);
    }
}
