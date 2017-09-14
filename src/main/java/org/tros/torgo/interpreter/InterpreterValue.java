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
package org.tros.torgo.interpreter;

import org.tros.torgo.interpreter.types.NullType;

/**
 *
 * @author matta
 */
public class InterpreterValue {

    public static final InterpreterValue NULL = new InterpreterValue(NullType.INSTANCE, null);

    private final Object value;
    private final InterpreterType type;

    /**
     * Constructor.
     *
     * @param type the type.
     * @param value the value.
     */
    public InterpreterValue(InterpreterType type, Object value) {
        this.value = value;
        this.type = type;
    }

    /**
     * Get the object value.
     *
     * @return the value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Get the type.
     *
     * @return the type.
     */
    public InterpreterType getType() {
        return type;
    }

    /**
     * To String.
     *
     * @return the value.
     */
    @Override
    public String toString() {
        return value.toString();
    }

}
