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
package org.tros.logo;

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tros.logo.antlr.logoLexer;
import org.tros.logo.antlr.logoParser;
import org.tros.logo.swing.LogoPanel;
import org.tros.logo.swing.LogoMenuBar;
import org.tros.logo.swing.LogoUserInputPanel;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.DynamicScope;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.interpreter.LexicalAnalyzer;
import org.tros.torgo.TorgoScreen;
import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.swing.TorgoToolBar;

/**
 * The Logo factory/controller.
 *
 * @author matta
 */
public final class DynamicLogoController extends LogoController {

    /**
     * Constructor, must be public for the ServiceLoader. Only initializes basic
     * object needs.
     */
    public DynamicLogoController() {
    }

    /**
     * Get the supported language.
     *
     * @return
     */
    @Override
    public String getLang() {
        return "dynamic-logo";
    }

    @Override
    protected Scope createScope() {
        return new DynamicScope();
    }
}
