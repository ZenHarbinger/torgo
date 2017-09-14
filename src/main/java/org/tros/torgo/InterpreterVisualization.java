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

import org.tros.torgo.interpreter.InterpreterThread;

/**
 *
 * @author matta
 */
public interface InterpreterVisualization {

    /**
     * Do the visualization.
     *
     * @param name the name of the visualization.
     * @param controller the controller.
     * @param interpreter the interpreter thread.
     */
    void watch(String name, Controller controller, InterpreterThread interpreter);

    /**
     * Abstract Factory Method.
     *
     * @return a new visualization.
     */
    InterpreterVisualization create();

    /**
     * The name of the visualization.
     *
     * @return the name of the visualization.
     */
    String getName();
}
