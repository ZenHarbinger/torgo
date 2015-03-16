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

import org.tros.torgo.ProcessResult;
import org.tros.torgo.CodeFunction;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ParserRuleContext;
import org.tros.logo.antlr.logoParser;
import org.tros.logo.antlr.logoParser.ProcedureInvocationContext;
import org.tros.torgo.TorgoCanvas;
import org.tros.torgo.Scope;

/**
 * Supports functions with parameters.
 *
 * @author matta
 */
class LogoFunction extends LogoBlock implements CodeFunction {

    private final String funcitonName;

    /**
     * Constructor
     *
     * @param functionName
     * @param ctx
     */
    protected LogoFunction(String functionName, ParserRuleContext ctx) {
        super(ctx);
        this.funcitonName = functionName;
    }

    /**
     * Get the function name
     *
     * @return
     */
    @Override
    public String getFunctionName() {
        return funcitonName;
    }

    /**
     * Debugging use.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("to ").append(funcitonName).append(System.getProperty("line.separator"));
        sb.append(super.toString());
        sb.append("end").append(System.getProperty("line.separator"));
        return sb.toString();
    }

    /**
     * Process the function.
     *
     * @param scope
     * @param canvas
     * @return
     */
    @Override
    public ProcessResult process(Scope scope, TorgoCanvas canvas) {
        ProcedureInvocationContext context = (ProcedureInvocationContext) scope.peek().getParserRuleContext();

        logoParser.ProcedureDeclarationContext funct = (logoParser.ProcedureDeclarationContext) ctx;
        ArrayList<String> paramNames = new ArrayList<>();
        ArrayList<Double> paramValues = new ArrayList<>();

        funct.parameterDeclarations().stream().forEach((param) -> {
            paramNames.add(param.getText().substring(1));
        });

        context.expression().stream().map((exp) -> {
            return ExpressionListener.evaluateDouble(scope, exp);
        }).forEach((el) -> {
            paramValues.add(el);
        });

        if (paramNames.size() == paramValues.size()) {
            for (int ii = 0; ii < paramNames.size(); ii++) {
                scope.setNew(paramNames.get(ii), paramValues.get(ii));
            }
        }

        ProcessResult ret = ProcessResult.SUCCESS;
        try {
            Logger.getLogger(LogoFunction.class.getName()).log(Level.FINEST, "function: {0}", new Object[]{funcitonName});
            for (int ii = 0; ii < paramNames.size(); ii++) {
                String name = paramNames.get(ii);
                Double value = paramValues.get(ii);
                Logger.getLogger(LogoFunction.class.getName()).log(Level.FINEST, "param: {0} -> {1}", new Object[]{name, value});
            }
            ret = super.process(scope, canvas);
        } catch (Exception ex) {
            Logger.getLogger(LogoFunction.class.getName()).log(Level.WARNING, "{0} -> {1}", new Object[]{ex.getClass().getName(), ex.getMessage()});
        }
        if (ret == ProcessResult.RETURN) {
            ret = ProcessResult.SUCCESS;
        }

        return ret;
    }
}
