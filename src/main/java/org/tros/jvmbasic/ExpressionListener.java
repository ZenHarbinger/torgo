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
package org.tros.jvmbasic;

import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.jvmbasic.antlr.jvmBasicBaseListener;
import org.tros.jvmbasic.antlr.jvmBasicParser;
import org.tros.torgo.interpreter.InterpreterValue;
import org.tros.torgo.interpreter.Scope;
import org.tros.torgo.interpreter.types.NumberType;
import org.tros.torgo.interpreter.types.StringType;

/**
 * Evaluates expressions. Builds a stack/tree of expressions and evaluates them
 * As the tree walker visits/exists nodes in the parse tree. The scope is passed
 * in to allow for variable dereferencing.
 *
 * @author matta
 */
class ExpressionListener extends jvmBasicBaseListener {

    private final Scope scope;
    private final Stack<ArrayList<InterpreterValue>> value = new Stack<>();

    /**
     * Evaluate an expression as defined in the jvmBasic.g4 grammar.
     *
     * @param scope
     * @param ctx
     * @return
     */
    public static InterpreterValue evaluate(Scope scope, ParseTree ctx) {
        org.tros.jvmbasic.ExpressionListener el = new org.tros.jvmbasic.ExpressionListener(scope);
        ParseTreeWalker.DEFAULT.walk(el, ctx);
        return el.getValue();
    }

    /**
     * Hidden constructor, forces use of "evaluateDouble" method.
     *
     * @param scope
     */
    private ExpressionListener(Scope scope) {
        this.scope = scope;
        value.push(new ArrayList<InterpreterValue>());
    }

    private double mathExpression(Double val1, Double val2, String op) {
        if (null != op) switch (op) {
            case "-":
                val1 = val1 - val2;
                break;
            case "+":
                val1 = val1 + val2;
                break;
            case "*":
                val1 = val1 * val2;
                break;
            case "%":
                val1 = val1 % val2;
                break;
            case "/":
                val1 = val1 / val2;
                break;
            case "\\":
                val1 = new Double((int) (val1 / val2));
                break;
            case "^":
                val1 = Math.pow(val1, val2);
                break;
            default:
                break;
        }
        return val1;
    }

    @Override
    public void enterExpression(jvmBasicParser.ExpressionContext ctx) {
        super.enterExpression(ctx);
    }

    @Override
    public void exitExpression(jvmBasicParser.ExpressionContext ctx) {
        super.exitExpression(ctx);
    }

    @Override
    public void enterRelationalExpression(jvmBasicParser.RelationalExpressionContext ctx) {
        super.enterRelationalExpression(ctx);
    }

    @Override
    public void exitRelationalExpression(jvmBasicParser.RelationalExpressionContext ctx) {
        super.exitRelationalExpression(ctx);
    }

    @Override
    public void enterAddingExpression(jvmBasicParser.AddingExpressionContext ctx) {
        super.enterAddingExpression(ctx);
    }

    @Override
    public void exitAddingExpression(jvmBasicParser.AddingExpressionContext ctx) {
        super.exitAddingExpression(ctx);
    }

    @Override
    public void enterMultiplyingExpression(jvmBasicParser.MultiplyingExpressionContext ctx) {
        super.enterMultiplyingExpression(ctx);
    }

    @Override
    public void exitMultiplyingExpression(jvmBasicParser.MultiplyingExpressionContext ctx) {
        super.exitMultiplyingExpression(ctx);
    }

    @Override
    public void enterExponentExpression(jvmBasicParser.ExponentExpressionContext ctx) {
        super.enterExponentExpression(ctx);
    }

    @Override
    public void exitExponentExpression(jvmBasicParser.ExponentExpressionContext ctx) {
        super.exitExponentExpression(ctx);
    }

    @Override
    public void enterSignExpression(jvmBasicParser.SignExpressionContext ctx) {
        super.enterSignExpression(ctx);
    }

    @Override
    public void exitSignExpression(jvmBasicParser.SignExpressionContext ctx) {
        super.exitSignExpression(ctx);
    }
    
    @Override
    public void enterFunc(jvmBasicParser.FuncContext ctx) {
        jvmBasicParser.NumberContext ctx2 = ctx.number();
        if (ctx2 != null && ctx2.NUMBER() != null) {
            InterpreterValue v = new InterpreterValue(NumberType.INSTANCE, Integer.parseInt(ctx.getText()));
            value.peek().add(v);
        } else if (ctx2 != null && ctx2.FLOAT() != null) {
            InterpreterValue v = new InterpreterValue(NumberType.INSTANCE, Float.parseFloat(ctx.getText()));
            value.peek().add(v);
        } else if (ctx.STRINGLITERAL() != null) {
            InterpreterValue v = new InterpreterValue(StringType.INSTANCE, ctx.getText());
            value.peek().add(v);
        } else {
        }
        super.enterFunc(ctx);
    }

    @Override
    public void exitFunc(jvmBasicParser.FuncContext ctx) {
        super.exitFunc(ctx);
    }

    public InterpreterValue getValue() {
        return value.peek().get(0);
    }
}
