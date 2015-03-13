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
import org.tros.torgo.Scope;

/**
 * Evaluates expressions.  Builds a stack/tree of expressions and evaluates them
 * As the tree walker visits/exists nodes in the parse tree.  The scope is passed
 * in to allow for variable dereferencing.
 * @author matta
 */
public class ExpressionListener extends logoBaseListener {

    private final Scope scope;
    private final Stack<ArrayList<Double>> value = new Stack<>();

    /**
     * Evaluate an expression as defined in the logo.g4 grammar.
     * @param scope
     * @param ctx
     * @return 
     */
    public static Double evaluateDouble(Scope scope, ParseTree ctx) {
        ExpressionListener el = new ExpressionListener(scope);
        ParseTreeWalker.DEFAULT.walk(el, ctx);
        return el.getValue();
    }

    /**
     * Hidden constructor, forces use of "evaluateDouble" method.
     * @param scope 
     */
    private ExpressionListener(Scope scope) {
        this.scope = scope;
        value.push(new ArrayList<>());
    }

    private double mathExpression(Double val1, Double val2, String op) {
        switch (op) {
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
        }
        return val1;
    }

    @Override
    public void enterExpression(logoParser.ExpressionContext ctx) {
        value.push(new ArrayList<>());
    }

    @Override
    public void exitExpression(logoParser.ExpressionContext ctx) {
        ArrayList<Double> values = value.pop();
        for (int ii = 1; ii < ctx.getChildCount(); ii += 2) {
            values.add(0, mathExpression(values.remove(0), values.remove(0), ctx.getChild(ii).getText()));
        }
        value.peek().add(values.get(0));
    }

    @Override
    public void enterDeref(logoParser.DerefContext ctx) {
        value.peek().add(scope.get(ctx.name().STRING().getText()));
    }

    @Override
    public void enterNumber(logoParser.NumberContext ctx) {
        value.peek().add(Double.parseDouble(ctx.NUMBER().getSymbol().getText()));
    }

    @Override
    public void enterMultiplyingExpression(logoParser.MultiplyingExpressionContext ctx) {
        value.push(new ArrayList<>());
    }

    @Override
    public void exitMultiplyingExpression(logoParser.MultiplyingExpressionContext ctx) {
        ArrayList<Double> values = value.pop();
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
            ArrayList<Double> values = value.pop();
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
        ArrayList<Double> values = value.pop();
        value.peek().add(((double) org.tros.utils.Random.nextInt(values.get(0).intValue())));
    }

    @Override
    public void exitSignExpression(logoParser.SignExpressionContext ctx) {
        String x = ctx.getChild(0).getText();
        ArrayList<Double> peek = this.value.peek();
        int index = peek.size() - 1;
        double val = peek.remove(index);
        if ("-".equals(x)) {
            val *= -1;
        }
        peek.add(index, val);
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
            //set value to string literal...
        }
    }

    public Double getValue() {
        return value.peek().get(0);
    }
}
