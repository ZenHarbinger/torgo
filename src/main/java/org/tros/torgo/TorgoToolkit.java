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
    
    private static final HashMap<String, Controller> map;
    private static final ServiceLoader<Controller> loader;
    private static final Logger logger = Logger.getLogger(TorgoToolkit.class.getName());
    
    static {
        map = new HashMap<>();
        loader = ServiceLoader.load(Controller.class);
        try {
            Iterator<Controller> controllers = loader.iterator();
            while (controllers.hasNext()) {
                Controller c = controllers.next();
                logger.log(Level.INFO, "Loaded: {0}", c.getClass().getName());
                map.put(c.getLang(), c);
            }
        } catch (ServiceConfigurationError serviceError) {
            logger.log(Level.WARNING, null, serviceError);
        }

    }
    
    private TorgoToolkit() {
        
    }
    
    public static Controller getController(String name) {
        return map.get(name);
    }
    
    public static Set<String> getToolkits() {
        return map.keySet();
    }
}
