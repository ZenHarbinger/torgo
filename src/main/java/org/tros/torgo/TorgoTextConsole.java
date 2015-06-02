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

import java.awt.Component;

/**
 * I/O interface. Get source and print messages.
 *
 * @author matta
 */
public interface TorgoTextConsole {

    /**
     * Append to the output text area.
     *
     * @param message
     */
    void appendToOutputTextArea(String message);

    /**
     * Clear the output text area.
     */
    void clearOutputTextArea();

    /**
     * Get the source.
     *
     * @return
     */
    String getSource();

    /**
     * Set the source.
     *
     * @param source
     */
    void setSource(String source);

    /**
     * Clear the source.
     */
    void clearSource();

    /**
     * Append a string onto the source.
     *
     * @param source
     */
    void appendToSource(String source);

    /**
     * Insert a string into the source.
     *
     * @param source
     */
    void insertIntoSource(String source);
//    default void insertIntoSource(String source) {
//        appendToSource(source);
//    }

    /**
     * Go to position on the console.
     *
     * @param position
     */
    void gotoPosition(int position);

    /**
     * Highlight a section of text on the console.
     *
     * @param line
     * @param startChar
     * @param endChar
     */
    void highlight(int line, int startChar, int endChar);

    /**
     * Reset the component to initial conditions.
     */
    void reset();

    /**
     * Return the Swing component for this control.
     *
     * @return
     */
    Component getComponent();
}
