grammar lisp;

@parser::header {
package org.tros.lisp.antlr;
}
@lexer::header {
package org.tros.lisp.antlr;
}

program : (symExpr '\n')+;

symExpr : symbol 
        | '(' (symExpr)* ')' 
        ;

symbol : OP     
       | ID     
       | NUMBER 
       ;

// lexer
OP : ('+'|'-'|'*'|'%'|'/'|'&'|'|'|'<'|'>'|'='|'?'|'!')+;
ID : LETTER (LETTER | DIGIT | '_')*;
NUMBER : DIGIT+;

fragment DIGIT : '0'..'9';
fragment LETTER: 'a'..'z'|'A'..'Z';

WS : (' '|'\t'|'\r')+ { skip(); };