// http://theory.stanford.edu/~amitp/yapps/yapps-doc/node2.html
// https://github.com/antlr/grammars-v4/blob/master/sexpression/sexpression.g4

grammar lisp;

@parser::header {
package org.tros.lisp.antlr;
}
@lexer::header {
package org.tros.lisp.antlr;
}

sexpr
   : list* EOF
   ;

item
   : atom
   | list
//   | LPAREN literalitem DOT literalitem RPAREN
//   | LITERALSTART literalitem* RPAREN
   ;

//literalitem
//   : atom
//   | literallist
//   | LPAREN literalitem DOT literalitem RPAREN
//   ;

list
   : LPAREN seq RPAREN
   ;

seq
   :
   | item seq
   ;

//literallist
//   : LPAREN literalitem* RPAREN
//   ;

atom
   : STRING
   | SYMBOL
   | NUMBER
//   | DOT
   ;

STRING
   : '"' ('\\' . | ~ ('\\' | '"'))* '"'
   ;


WHITESPACE
   : (' ' | '\n' | '\t' | '\r') + -> skip
   ;


NUMBER
   : ('+' | '-')? (DIGIT) + ('.' (DIGIT) +)?
   ;


SYMBOL
   : SYMBOL_START (SYMBOL_START | DIGIT)*
   ;


LPAREN
   : '('
   ;

LITERALSTART
   : '\'('
   ;


RPAREN
   : ')'
   ;


DOT
   : '.'
   ;


fragment SYMBOL_START
   : ('a' .. 'z') | ('A' .. 'Z') | '+' | '-' | '*' | '/' | '.'
   ;


fragment DIGIT
   : ('0' .. '9')
   ;