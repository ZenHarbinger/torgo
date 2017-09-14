/*
 * Copyright 2015-2017 Matthew Aguirre
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
import org.tros.utils.BuildInfo;
import org.tros.utils.ResourceAccessor;

/**
 * @author matta
 */
public final class TorgoToolkit {

    private static final TorgoInfo BUILD_INFO;

    private static final HashMap<String, Controller> CONTROLLER_MAP;
    private static final ServiceLoader<Controller> CONTROLLERS;

    private static final HashMap<String, InterpreterVisualization> VIZ_MAP;
    private static final ServiceLoader<InterpreterVisualization> VIZUALIZERS;

    private static final HashMap<String, ResourceAccessor> RESOURCE_MAP;
    private static final ServiceLoader<ResourceAccessor> RESOURCES;

    private static final org.tros.utils.logging.Logger LOGGER
            = org.tros.utils.logging.Logging.getLogFactory().getLogger(TorgoToolkit.class);

    private static String defaultResourceAccessor;

    private static boolean configExcep1 = false;
    private static boolean configExcep2 = false;
    private static boolean configExcep3 = false;

    /**
     * Static constructor.
     */
    static {
        BUILD_INFO = new TorgoInfo();
        CONTROLLER_MAP = new HashMap<>();
        CONTROLLERS = ServiceLoader.load(Controller.class);
        initController();
        VIZ_MAP = new HashMap<>();
        VIZUALIZERS = ServiceLoader.load(InterpreterVisualization.class);
        initInterpreterVis();
        RESOURCE_MAP = new HashMap<>();
        RESOURCES = ServiceLoader.load(ResourceAccessor.class);
        initResAccessor();
    }

    /**
     * Hidden constructor.
     */
    protected TorgoToolkit() {
    }

    public static void initController() {
        try {
            if (configExcep1) {
                throw new ServiceConfigurationError("test");
            }
            for (Controller controller : CONTROLLERS) {
                LOGGER.info(MessageFormat.format("Loaded: {0}", controller.getClass().getName()));
                CONTROLLER_MAP.put(controller.getLang(), controller);
            }
        } catch (ServiceConfigurationError serviceError) {
            configExceptionTest1();
            LOGGER.warn(null, serviceError);
        }
    }

    public static void initInterpreterVis() {
        try {
            if (configExcep2) {
                throw new ServiceConfigurationError("test");
            }
            for (InterpreterVisualization viz : VIZUALIZERS) {
                LOGGER.info(MessageFormat.format("Loaded: {0}", viz.getClass().getName()));
                VIZ_MAP.put(viz.getName(), viz);
            }
        } catch (ServiceConfigurationError serviceError) {
            configExceptionTest2();
            LOGGER.warn(null, serviceError);
        }
    }

    public static void initResAccessor() {
        boolean set = false;
        try {
            if (configExcep3) {
                throw new ServiceConfigurationError("test");
            }
            for (ResourceAccessor ressource : RESOURCES) {
                LOGGER.info(MessageFormat.format("Loaded: {0}", ressource.getClass().getName()));
                RESOURCE_MAP.put(ressource.getName(), ressource);
                if (!set) {
                    defaultResourceAccessor = ressource.getName();
                    set = true;
                }
            }
        } catch (ServiceConfigurationError serviceError) {
            configExceptionTest3();
            LOGGER.warn(null, serviceError);
        }
    }

    public static BuildInfo getBuildInfo() {
        return BUILD_INFO;
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

    /**
     * Get the name of all available resource accessors.
     *
     * @return Get a list of resource accessors available.
     */
    public static Set<String> getResourceAccessors() {
        return RESOURCE_MAP.keySet();
    }

    /**
     * Get a desired resource accessor.
     *
     * @param name The desired resource accessor to get.
     * @return The desired resource accessor.
     */
    public static ResourceAccessor getResourceAccessor(String name) {
        return RESOURCE_MAP.get(name);
    }

    /**
     * Get a default resource accessor.
     *
     * @return The default resource accessor.
     */
    public static ResourceAccessor getDefaultResourceAccessor() {
        return getResourceAccessor(defaultResourceAccessor);
    }

    public static void configExceptionTest1() {
        configExcep1 = !configExcep1;
    }

    public static boolean getConfigExcep1() {
        return configExcep1;
    }

    public static void configExceptionTest2() {
        configExcep2 = !configExcep2;
    }

    public static boolean getConfigExcep2() {
        return configExcep2;
    }

    public static void configExceptionTest3() {
        configExcep3 = !configExcep3;
    }

    public static boolean getConfigExcep3() {
        return configExcep3;
    }
}
