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
package org.tros.torgo.viz;

import java.text.MessageFormat;
import java.util.Calendar;
import org.tros.torgo.interpreter.CodeBlock;
import org.tros.torgo.Controller;
import org.tros.torgo.interpreter.InterpreterListener;
import org.tros.torgo.interpreter.InterpreterThread;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.InterpreterVisualization;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.ScopeListener;
import org.tros.utils.TypeHandler;

/**
 * Does a detailed log trace of execution and events.
 *
 * @author matta
 */
public class TraceLogger implements InterpreterVisualization {

    private static final org.tros.utils.logging.Logger LOGGER = org.tros.utils.logging.Logging.getLogFactory().getLogger(TraceLogger.class);
    private InterpreterThread interpreter;

    /**
     * Constructor.
     *
     */
    public TraceLogger() {
    }

    /**
     * Create a new instance.
     *
     * @return a new trace logger.
     */
    @Override
    public InterpreterVisualization create() {
        return new TraceLogger();
    }

    /**
     * Get the name of the type (for display).
     *
     * @return the trace logger class name.
     */
    @Override
    public String getName() {
        return TraceLogger.class.getSimpleName();
    }

    /**
     * Do the work.
     *
     * @param name the name.
     * @param controller the controller.
     * @param interpreter the interpreter.
     */
    @Override
    public void watch(final String name, final Controller controller, final InterpreterThread interpreter) {
        this.interpreter = interpreter;
        this.interpreter.addInterpreterListener(new InterpreterListener() {

            @Override
            public void started() {
                LOGGER.info(MessageFormat.format("Started [{0}]: {1}", new Object[]{controller.getLang(), TypeHandler.dateToString(Calendar.getInstance())}));
            }

            @Override
            public void finished() {
                LOGGER.info(MessageFormat.format("Finished [{0}]: {1}", new Object[]{controller.getLang(), TypeHandler.dateToString(Calendar.getInstance())}));
            }

            @Override
            public void error(Exception e) {
                LOGGER.info(MessageFormat.format("Error [{0}]: {1}", new Object[]{controller.getLang(), TypeHandler.dateToString(Calendar.getInstance())}));
                LOGGER.info(null, e);
            }

            @Override
            public void message(String msg) {
                LOGGER.info(MessageFormat.format("Message [{0}]: {1}", new Object[]{controller.getLang(), msg}));
            }

            /**
             * This is where the bulk of the code will go.
             *
             * @param block the block.
             * @param scope the scope.
             */
            @Override
            public void currStatement(CodeBlock block, Scope scope) {
                LOGGER.info(MessageFormat.format("Curr Statement: {0}", new Object[]{block.getParserRuleContext().getClass().getName()}));
            }
        });

        this.interpreter.addScopeListener(new ScopeListener() {

            @Override
            public void scopePopped(Scope scope, CodeBlock block) {
                LOGGER.info(MessageFormat.format("Scope Popped: {0}", new Object[]{block.getClass().getName()}));
            }

            @Override
            public void scopePushed(Scope scope, CodeBlock block) {
                LOGGER.info(MessageFormat.format("Scope Pushed: {0}", new Object[]{block.getClass().getName()}));
            }

            @Override
            public void variableSet(Scope scope, String name, InterpreterValue value) {
                if (!name.contains("%")) {
                    LOGGER.info(MessageFormat.format("Variable Set: {0} -> {1}", new Object[]{name, value.toString()}));
                }
            }
        });
    }
}
