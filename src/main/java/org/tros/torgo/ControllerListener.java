/*
 * Copyright 2015-2016 Matthew Aguirre
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

/**
 *
 * @author matta
 */
public interface ControllerListener {
//
//    /**
//     * Initialize the GUI back to reset state.
//     */
//    void onNewFile();
//
//    /**
//     * Open a file.
//     */
//    void onOpenFile();
//
//    /**
//     * Open a specified file.
//     *
//     * @param file
//     */
//    void onOpenFile(File file);
//
//    /**
//     * Open a specified location based on URL.
//     *
//     * @param file
//     */
//    void onOpenFile(URL file);
//
//    /**
//     * Print canvas.
//     */
//    void onPrintCanvas();
//
//    /**
//     * Save file.
//     */
//    void onSaveFile();
//
//    /**
//     * Save file as.
//     */
//    void onSaveFileAs();

    /**
     * Start Interpreter.
     */
    void onStartInterpreter();

    /**
     * Stop Interpreter.
     */
    void onStopInterpreter();

    /**
     * Debug interpreter. Defaults to just start the interpreter.
     */
    void onDebugInterpreter();

    /**
     * Step over a statement; default is empty.
     */
    void onStepOver();

    /**
     * Pause interpreter; default is empty.
     */
    void onPauseInterpreter();

    /**
     * Resume interpreter; default is empty.
     */
    void onResumeInterpreter();

//    /**
//     * Close the application/controller.
//     */
//    void onClose();
}
