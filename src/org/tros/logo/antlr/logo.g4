/*
BSD License
Copyright (c) 2013, Tom Everett
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. Neither the name of Tom Everett nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

grammar logo;

@parser::header {
package org.tros.logo.antlr;
}
@lexer::header {
package org.tros.logo.antlr;
}

/**--Added lone line detection--**/
prog
    : line | (line? EOL)+ line?
    ;

/**--Added EOL--**/
line
    : cmd+ comment?
    | comment
    | print comment?
    | procedureDeclaration
    | EOL
    ;
     
cmd
    : repeat
    | fd
    | bk
    | rt
    | lt
    | cs
    | pu
    | pd
    | ht
    | st
    | home
    | setxy
    | make
    | localmake
    | procedureInvocation
    | ife
    | stop
    | fore
    | pc
    | cc
    | pause
    | ds
    | fontsize
    | fontstyle
    | fontname
/*-- print was not originally here, so could not print in if/repeat blocks--*/
    | print
    ;

procedureInvocation
    : name expression*
    ;

procedureDeclaration
    : 'to' name parameterDeclarations* EOL? (line? EOL)+ 'end'
    ;

parameterDeclarations
    : ':' name (',' parameterDeclarations)*
    ;

func
    : random
    | repcount
    | getangle
    | getx
    | gety
    ;

/*--made it so that repeating can use an expression--*/
repeat
    : 'repeat' expression block
    ;

/*--Would like to make this be multi-line--*/
block
    : '[' EOL* (cmd EOL*)+ ']'
    ;

ife
    : 'if' comparison block
    ;

comparison
    : expression comparisonOperator expression
    ;

/*--added more compare operators--*/
comparisonOperator
    : '<' 
    | '>' 
    | '='
    | '!'
    | '<='
    | '>='
    | '=='
    | '!='
    | '<>'
    ;

make
    : 'make' STRINGLITERAL value
    ;

localmake
    : 'localmake' STRINGLITERAL value
    ;

print
    : 'print' (value | quotedstring)
    ;

quotedstring
    : '[' (quotedstring | ~']')* ']'
    ;

name
    : STRING
    ;

/*--expression also contains deref, so deref here is unnecessary--*/
value
    : STRINGLITERAL
    | expression
    | deref 
    ;

/**--Added parenthesis for better order of operations control--**/
parenExpression
    : '(' expression ')'
    ;

signExpression 
    : ('+'|'-')? (number | deref | func | parenExpression)
    ;

/**--Added Power/Exponential Expression--**/
powerExpression
    : signExpression ('^' signExpression)?
    ;

/**--Added Integer Divsion Symbol and Modulo--**/
multiplyingExpression
    : powerExpression (('*' | '/' | '\\' | '%') powerExpression)*
    ;

expression 
     : multiplyingExpression (('+'|'-') multiplyingExpression)*
     ;

deref
    : ':' name
    ;

fd
    : ('fd' | 'forward') expression
    ;

bk
    : ('bk' | 'backward' | 'back') expression
    ;

rt
    : ('rt' | 'right') expression
    ;

lt
    : ('lt' | 'left') expression
    ;

cs
    : 'cs' | 'clearscreen' | 'cls' | 'clear'
    ;

pu
    : 'pu' | 'penup'
    ;

pd
    : 'pd' | 'pendown'
    ;

ht
    : 'ht' | 'hideturtle'
    ;

st
    : 'st' | 'showturtle'
    ;

home
    : 'home'
    ;

stop
    : 'stop'
    ;
        
setxy
    : 'setxy' expression expression
    ;

random
    : 'random' expression
    ;

getangle
    : 'getangle'
    ;

getx
    : 'getx'
    ;

gety
    : 'gety'
    ;

/**--This value will tell you which repeat value you are on inside the innermost repeat loop.--**/
/**--This value starts at 1.--**/
/**--If you are not in a repeat loop, it will evaluate to 0.--**/
repcount
    : 'repcount'
    ;
/* --modified to make the step optional-- */
fore
    : 'for' '[' name expression expression expression? ']' block
    ;

/* --custom-- */
/* --change pen color-- */
pc
    : ('pc' | 'pencolor') (name | expression expression expression expression? | hexcolor)
    ;

/* --change canvas color-- */
cc
    : ('cc' | 'canvascolor') (name | expression expression expression | hexcolor)
    ;

hexcolor
    : '#'HEX
    ;
HEX
    : [0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F]
    ;

/* --pause-- */
pause
    : 'pause' expression
    ;

/* --draw a string to the canvas-- */
ds
    : ('ds'|'drawstring' | 'label') value
    ;

fontname
    : 'fontname' name
    ;

fontsize
    : 'fontsize' expression
    ;

fontstyle
    : 'fontstyle' style
    ;

style
    : 'bold' | 'plain' | 'italic'
    ;

number
    : NUMBER
    ;

comment
    : COMMENT
    ;
     
STRINGLITERAL
    : '"' STRING
    ;

STRING
    : [a-zA-Z] [a-zA-Z0-9_]*
    ;
    
NUMBER
    : [0-9]+ ('.'[0-9]+)?
    ;

COMMENT
    : ';' ~[\r\n]*
    ;

EOL
    : '\r'? '\n'
    ;

WS
    : [ \t\r\n]->skip
    ;
