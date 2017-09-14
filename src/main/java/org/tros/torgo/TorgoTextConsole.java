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

import java.awt.Component;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * I/O interface. Get source and print messages.
 *
 * @author matta
 */
public interface TorgoTextConsole {

    /**
     * Append to the output text area.
     *
     * @param message the message to put in the output text area.
     */
    void appendToOutputTextArea(String message);

    /**
     * Clear the output text area.
     */
    void clearOutputTextArea();

    /**
     * Get the source.
     *
     * @return the the source that is being interpreter.
     */
    String getSource();

    /**
     * Set the source.
     *
     * @param source set the source to be interpreted.
     */
    void setSource(String source);

    /**
     * Clear the source.
     */
    void clearSource();

    /**
     * Append a string onto the source.
     *
     * @param source source to append.
     */
    void appendToSource(String source);

    /**
     * Insert a string into the source.
     *
     * @param source source to insert.
     */
    void insertIntoSource(String source);
//    default void insertIntoSource(String source) {
//        appendToSource(source);
//    }

    /**
     * Go to position on the console.
     *
     * @param position the position/line number.
     */
    void gotoPosition(int position);

    /**
     * Highlight a section of text on the console.
     *
     * @param line the line.
     * @param startChar the start position.
     * @param endChar the end position.
     */
    void highlight(int line, int startChar, int endChar);

    /**
     * Reset the component to initial conditions.
     */
    void reset();

    /**
     * Return the Swing component(s) for this control.
     *
     * @return the swing component(s) for this control.
     */
    ArrayList<ImmutablePair<String, Component>> getTorgoComponents();
}
