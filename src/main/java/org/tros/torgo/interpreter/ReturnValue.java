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
public class ReturnValue extends InterpreterValue {

    /**
     * Singleton RETURN type w/ no value.
     */
    public static final ReturnValue RETURN = new ReturnValue(NullType.INSTANCE, null, ProcessResult.RETURN);

    /**
     * Singleton SUCCESS type w/ no value.
     */
    public static final ReturnValue SUCCESS = new ReturnValue(NullType.INSTANCE, null, ProcessResult.SUCCESS);

    /**
     * Singleton HALT type w/ no value.
     */
    public static final ReturnValue HALT = new ReturnValue(NullType.INSTANCE, null, ProcessResult.HALT);

    /**
     * Return type enum.
     */
    public enum ProcessResult {

        SUCCESS,
        HALT,
        RETURN
    }

    private final ProcessResult result;

    /**
     * Constructor.
     *
     * @param type the interpreter type.
     * @param value the value.
     * @param result the result status.
     */
    public ReturnValue(InterpreterType type, Object value, ProcessResult result) {
        super(type, value);
        this.result = result;
    }

    /**
     * Return the type of the result.
     *
     * @return the result.
     */
    public ProcessResult getResult() {
        return result;
    }
}
