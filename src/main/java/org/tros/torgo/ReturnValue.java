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

import org.tros.torgo.types.NullType;

/**
 *
 * @author matta
 */
public class ReturnValue extends InterpreterValue {

    /**
     * Singleton RETURN type w/ no value.
     */
    public static final ReturnValue RETURN = new ReturnValue(NullType.Instance, null, ProcessResult.RETURN);

    /**
     * Singleton SUCCESS type w/ no value.
     */
    public static final ReturnValue SUCCESS = new ReturnValue(NullType.Instance, null, ProcessResult.SUCCESS);

    /**
     * Singleton HALT type w/ no value.
     */
    public static final ReturnValue HALT = new ReturnValue(NullType.Instance, null, ProcessResult.HALT);

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
     * @param type
     * @param value
     * @param result
     */
    public ReturnValue(InterpreterType type, Object value, ProcessResult result) {
        super(type, value);
        this.result = result;
    }

    /**
     * Return the type of the result.
     *
     * @return
     */
    public ProcessResult getResult() {
        return result;
    }
}
