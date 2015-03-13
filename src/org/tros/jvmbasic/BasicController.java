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
package org.tros.jvmbasic;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.tros.torgo.ControllerBase;
import org.tros.torgo.Controller;
import org.tros.torgo.InterpreterThread;
import org.tros.torgo.TorgoCanvas;
import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.swing.SwingCanvas;
import org.tros.torgo.swing.SwingTextConsole;

/**
 *
 * @author matta
 */
public class BasicController extends ControllerBase {

    @Override
    protected SwingTextConsole createConsole(Controller app) {
        return null;
    }

    @Override
    protected SwingCanvas createCanvas(TorgoTextConsole console) {
        return null;
    }

    @Override
    protected InterpreterThread createInterpreterThread(String source, TorgoCanvas canvas) {
        return null;
    }

    @Override
    protected JToolBar createToolBar() {
        return null;
    }

    @Override
    protected JMenuBar createMenuBar() {
        return null;
    }

    @Override
    public String getLang() {
        return "jvmBasic";
    }
}
