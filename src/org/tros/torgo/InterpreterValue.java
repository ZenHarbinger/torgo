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

/**
 *
 * @author matta
 */
public class InterpreterValue implements InterpreterType {
    
    public static final InterpreterValue NULL = new InterpreterValue(Type.NULL, null);

    private final Object value;
    private final Type type;

    public InterpreterValue(Type type, Object value) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return type;
    }

//    /**
//     * This function probably needs work.
//     * It allows returning a CodeBlock as a value.  When used as a function
//     * we must remember to push the stack to get the procedure invocation context.
//     * Not sure what we should do for primitive (String/Number) values.
//     * @param scope
//     * @param canvas
//     * @return 
//     */
//    @Override
//    public ReturnValue process(Scope scope, TorgoCanvas canvas) {
//        switch (type) {
//            case COMMAND:
//                return ((CodeBlock) value).process(scope, canvas);
//            case STRING:
//            case NUMBER:
//            case NULL:
//            default:
//                return new ReturnValue(Type.NULL, null, ReturnValue.ProcessResult.HALT);
//        }
//    }
}
