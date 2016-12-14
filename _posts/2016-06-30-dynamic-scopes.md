---
layout: post
title: Scopes
date: '2016-06-30 12:05:00 -0500'
categories: dev
tags:
  - example
  - logo
author: Matthew Aguirre
---

Welcome the the first of my documentation series.  This brief article will describe the ability to specify scoping within the interpreter at design time.

When programming, variables are [scoped](http://en.wikipedia.org/wiki/Scope_%28computer_science%29#Lexical_scoping_vs._dynamic_scoping). Most people are used to lexical scoping which is used in C/C++, Java, Python, etc. However Logo was originally designed to use dynamic scoping, which I have implemented as the default.

Scoping is a module within Torgo and can be set to be used within the interpreter using either the static or the dynamic scoping implementation. At the moment, this cannot be changed at runtime.  Custom scoping can be implemented by implementing the [Scope interface](https://github.com/ZenHarbinger/torgo/blob/master/src/main/java/org/tros/torgo/interpreter/Scope.java).

# LogoController.java

```java
/**
 * Get an interpreter thread.
 *
 * @param source
 * @return
 */
@Override
protected InterpreterThread createInterpreterThread(String source) {
    return new InterpreterThread(source, new DynamicScope()) {

        @Override
        protected LexicalAnalyzer getLexicalAnalysis(String source) {
            if (canvas != null) {
                canvas.reset();
            }
            //lexical analysis and parsing with ANTLR
            logoLexer lexer = new logoLexer(new ANTLRInputStream(source));
            logoParser parser = new logoParser(new CommonTokenStream(lexer));
            //get the prog element from the parse tree
            //the prog element is the root element defined in the logo.g4 grammar.
            return LexicalListener.lexicalAnalysis(parser.prog(), canvas);
        }

        @Override
        protected void process(CodeBlock entryPoint) {
            entryPoint.process(scope);
        }
    };
}
```

Essentially, simply changing the above to pass in a new instance of LexicalScope will change the behavior of the interpreter.

## Example

```logo
make "x 1
to g
    print :x
    make "x 2
end
to f
    localmake "x 3
    g
end
f
print :x
```

### Using Dynamic Scoping:

tros.org.logo.LogoStatement -> 3.0<br>
tros.org.logo.LogoStatement -> 1.0

### Using Lexical Scoping:

tros.org.logo.LogoStatement -> 1.0<br>
tros.org.logo.LogoStatement -> 2.0

## From the Wiki:

If the language of this program is one that uses lexical scoping, then g prints and modifies the global variable x (because g is defined outside f), so the program prints 1 and then 2. By contrast, if this language uses dynamic scoping, then g prints and modifies f's local variable x (because g is called from within f), so the program prints 3 and then 1.
