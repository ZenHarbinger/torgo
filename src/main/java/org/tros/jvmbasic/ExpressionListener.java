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
import org.tros.torgo.interpreter.types.BooleanType;
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

    private InterpreterValue mathExpression(InterpreterValue val1, InterpreterValue val2, String op) {
        double num1 = ((Number) val1.getValue()).doubleValue();
        double num2 = ((Number) val2.getValue()).doubleValue();
        if (null != op) {
            switch (op) {
                case "-":
                    num1 = num1 - num2;
                    break;
                case "+":
                    num1 = num1 + num2;
                    break;
                case "*":
                    num1 = num1 * num2;
                    break;
                case "%":
                    num1 = num1 % num2;
                    break;
                case "/":
                    num1 = num1 / num2;
                    break;
                case "\\":
                    num1 = (int) (num1 / num2);
                    break;
                case "^":
                    num1 = Math.pow(num1, num2);
                    break;
                default:
                    break;
            }
        }
        return new InterpreterValue(NumberType.INSTANCE, num1);
    }

    private InterpreterValue relOp(InterpreterValue val1, InterpreterValue val2, String op) {
        double num1 = ((Number) val1.getValue()).doubleValue();
        double num2 = ((Number) val2.getValue()).doubleValue();
        boolean ret = false;
        if (null != op) {
            switch (op) {
                case "=>":  //GTE
                case ">=":  //GTE
                case ">: ": //GTE
                    ret = num1 >= num2;
                    break;
                case "<=":  //LTE
                case "=<":  //LTE
                case "<: ": //LTE
                    ret = num1 <= num2;
                    break;
                case "<":   //lt
                    ret = num1 < num2;
                    break;
                case ">":   //gt
                    ret = num1 > num2;
                    break;
                case "=":   //eq
                    ret = num1 == num2;
                    break;
                case "<>":  //neq
                    ret = num1 != num2;
                    break;
                default:
                    //fail...
                    break;
            }
        }
        return new InterpreterValue(BooleanType.INSTANCE, ret);
    }

    @Override
    public void enterExpression(jvmBasicParser.ExpressionContext ctx) {
    }

    @Override
    public void exitExpression(jvmBasicParser.ExpressionContext ctx) {
    }

    @Override
    public void enterNumber(jvmBasicParser.NumberContext ctx) {
        if (ctx.NUMBER() != null) {
            InterpreterValue v = new InterpreterValue(NumberType.INSTANCE, Integer.parseInt(ctx.getText()));
            value.peek().add(v);
        } else if (ctx.FLOAT() != null) {
            InterpreterValue v = new InterpreterValue(NumberType.INSTANCE, Float.parseFloat(ctx.getText()));
            value.peek().add(v);
        }
    }

    @Override
    public void enterRelationalExpression(jvmBasicParser.RelationalExpressionContext ctx) {
        value.push(new ArrayList<InterpreterValue>());
    }

    @Override
    public void exitRelationalExpression(jvmBasicParser.RelationalExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, relOp(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterAddingExpression(jvmBasicParser.AddingExpressionContext ctx) {
        value.push(new ArrayList<InterpreterValue>());
    }

    @Override
    public void exitAddingExpression(jvmBasicParser.AddingExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterMultiplyingExpression(jvmBasicParser.MultiplyingExpressionContext ctx) {
        value.push(new ArrayList<InterpreterValue>());
    }

    @Override
    public void exitMultiplyingExpression(jvmBasicParser.MultiplyingExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterExponentExpression(jvmBasicParser.ExponentExpressionContext ctx) {
        value.push(new ArrayList<InterpreterValue>());
    }

    @Override
    public void exitExponentExpression(jvmBasicParser.ExponentExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void exitSignExpression(jvmBasicParser.SignExpressionContext ctx) {
        String x = ctx.getChild(0).getText();
        ArrayList<InterpreterValue> peek = this.value.peek();
        int index = peek.size() - 1;
        if (peek.get(index).getType().equals(NumberType.INSTANCE)) {
            InterpreterValue val = peek.remove(index);
            Object o = val.getValue();
            double n = ((Number) val.getValue()).doubleValue();
            if ("-".equals(x)) {
                n *= -1;
            }
            peek.add(index, new InterpreterValue(NumberType.INSTANCE, n));
        }
    }

    @Override
    public void enterFunc(jvmBasicParser.FuncContext ctx) {
        if (ctx.STRINGLITERAL() != null) {
            InterpreterValue v = new InterpreterValue(StringType.INSTANCE, ctx.getText());
            value.peek().add(v);
        } else {
        }
    }

    @Override
    public void exitFunc(jvmBasicParser.FuncContext ctx) {
    }

    public InterpreterValue getValue() {
        return value.peek().get(0);
    }
}
