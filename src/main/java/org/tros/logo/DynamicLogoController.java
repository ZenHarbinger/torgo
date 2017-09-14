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
package org.tros.logo;

import org.tros.torgo.interpreter.DynamicScope;
import org.tros.torgo.interpreter.Scope;

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
     * @return the name of the supported language.
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
