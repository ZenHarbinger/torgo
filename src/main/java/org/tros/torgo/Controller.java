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

import org.tros.torgo.interpreter.InterpreterListener;
import java.io.File;
import java.net.URL;

/**
 * Interface for the main application.
 *
 * @author matta
 */
public interface Controller {

    /**
     * Initialize the GUI back to reset state.
     */
    void newFile();

    /**
     * Open a file.
     */
    void openFile();

    /**
     * Open a specified file.
     *
     * @param file
     */
    void openFile(File file);

    /**
     * Return the file that is open.  Null if no file open.
     *
     * @return
     */
    File getFile();

    /**
     * Open a specified location based on URL.
     *
     * @param file
     */
    void openFile(URL file);

    /**
     * Print canvas.
     */
    void printCanvas();

    /**
     * Save file.
     */
    void saveFile();

    /**
     * Save file as.
     */
    void saveFileAs();

    /**
     * Start Interpreter.
     */
    void startInterpreter();

    /**
     * Stop Interpreter.
     */
    void stopInterpreter();

    /**
     * Debug interpreter. Defaults to just start the interpreter.
     */
    void debugInterpreter();

    /**
     * Step over a statement; default is empty.
     */
    void stepOver();

    /**
     * Pause interpreter; default is empty.
     */
    void pauseInterpreter();

    /**
     * Resume interpreter; default is empty.
     */
    void resumeInterpreter();

    /**
     * Close the application/controller.
     */
    void close();

    /**
     * Insert a command into the source.
     *
     * @param command
     */
    void insertCommand(String command);

    /**
     * Add a controller listener.
     *
     * @param listener
     */
    void addControllerListener(ControllerListener listener);

    /**
     * Remove Controller Listener.
     *
     * @param listener
     */
    void removeControllerListener(ControllerListener listener);

    /**
     * Add an interpreter listener.
     *
     * @param listener
     */
    void addInterpreterListener(InterpreterListener listener);

    /**
     * Remove an interpreter listener.
     *
     * @param listener
     */
    void removeInterpreterListener(InterpreterListener listener);

    /**
     * Set the source for the interpreter.
     *
     * @param src
     */
    void setSource(String src);

    /**
     * Set the source for the interpreter.
     *
     * @return
     */
    String getSource();

    /**
     * Get the language of the interpreter.
     *
     * @return
     */
    String getLang();

    /**
     * Start up the application/controller.
     */
    void run();

    /**
     * Enable.
     *
     * @param name
     */
    void enable(String name);

    /**
     * Disable.
     *
     * @param name
     */
    void disable(String name);
}
