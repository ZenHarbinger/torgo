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

import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.tros.logo.antlr.logoBaseListener;
import org.tros.logo.antlr.logoParser;
import org.tros.torgo.InterpreterType;
import org.tros.torgo.InterpreterValue;
import org.tros.torgo.Scope;
import org.tros.torgo.types.NumberType;
import org.tros.torgo.types.StringType;

/**
 * Evaluates expressions. Builds a stack/tree of expressions and evaluates them
 * As the tree walker visits/exists nodes in the parse tree. The scope is passed
 * in to allow for variable dereferencing. This is for the Logo language only.
 *
 * @author matta
 */
class ExpressionListener extends logoBaseListener {

    private final Scope scope;
    private final Stack<ArrayList<InterpreterValue>> value = new Stack<>();

    /**
     * Evaluate an expression as defined in the logo.g4 grammar.
     *
     * @param scope
     * @param ctx
     * @return
     */
    protected static InterpreterValue evaluate(Scope scope, ParseTree ctx) {
        ExpressionListener el = new ExpressionListener(scope);
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
        value.push(new ArrayList<>());
    }

    private InterpreterValue mathExpression(InterpreterValue val1, InterpreterValue val2, String op) {
        double num1 = ((Number) val1.getValue()).doubleValue();
        double num2 = ((Number) val2.getValue()).doubleValue();
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
        }
        return new InterpreterValue(NumberType.Instance, num1);
    }

    @Override
    public void enterExpression(logoParser.ExpressionContext ctx) {
        value.push(new ArrayList<>());
    }

    @Override
    public void exitExpression(logoParser.ExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterDeref(logoParser.DerefContext ctx) {
        InterpreterValue s = scope.get(ctx.name().STRING().getText());
        value.peek().add(s);
    }

    @Override
    public void enterNumber(logoParser.NumberContext ctx) {
        Double d = Double.parseDouble(ctx.NUMBER().getSymbol().getText());
        value.peek().add(new InterpreterValue(NumberType.Instance, d));
    }

    @Override
    public void enterMultiplyingExpression(logoParser.MultiplyingExpressionContext ctx) {
        value.push(new ArrayList<>());
    }

    @Override
    public void exitMultiplyingExpression(logoParser.MultiplyingExpressionContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterPowerExpression(logoParser.PowerExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            value.push(new ArrayList<>());
        }
    }

    @Override
    public void exitPowerExpression(logoParser.PowerExpressionContext ctx) {
        if (ctx.getChildCount() > 1) {
            ArrayList<InterpreterValue> values = value.pop();
            for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
                values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
            }
            value.peek().add(values.get(0));
        }
    }

    @Override
    public void enterRandom(logoParser.RandomContext ctx) {
        value.push(new ArrayList<>());
    }

    @Override
    public void exitRandom(logoParser.RandomContext ctx) {
        ArrayList<InterpreterValue> values = value.pop();
        int max = ((Number) values.get(0).getValue()).intValue();
        InterpreterValue v = new InterpreterValue(NumberType.Instance, org.tros.utils.Random.nextInt(max));
        value.peek().add(v);
    }

    @Override
    public void exitSignExpression(logoParser.SignExpressionContext ctx) {
        String x = ctx.getChild(0).getText();
        ArrayList<InterpreterValue> peek = this.value.peek();
        int index = peek.size() - 1;
        if (peek.get(index).getType().equals(NumberType.Instance)) {
            InterpreterValue val = peek.remove(index);
            Object o = val.getValue();
            double n = ((Number) val.getValue()).doubleValue();
            if ("-".equals(x)) {
                n *= -1;
            }
            peek.add(index, new InterpreterValue(NumberType.Instance, n));
        }
    }

    @Override
    public void enterGetx(logoParser.GetxContext ctx) {
        value.peek().add(scope.get(LogoStatement.TURTLE_X_VAR));
    }

    @Override
    public void enterGety(logoParser.GetyContext ctx) {
        value.peek().add(scope.get(LogoStatement.TURTLE_Y_VAR));
    }

    @Override
    public void enterGetangle(logoParser.GetangleContext ctx) {
        value.peek().add(scope.get(LogoStatement.TURTLE_ANGLE_VAR));
    }

    @Override
    public void enterRepcount(logoParser.RepcountContext ctx) {
        value.peek().add(scope.get(LogoRepeat.REPCOUNT_VAR));
    }

    @Override
    public void enterValue(logoParser.ValueContext ctx) {
        if (ctx.STRINGLITERAL() != null) {
            value.peek().add(new InterpreterValue(StringType.Instance, ctx.STRINGLITERAL().getText().substring(1)));
        }
    }

    public InterpreterValue getValue() {
        return value.peek().get(0);
    }
}
