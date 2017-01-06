---
layout: page
title: Logo
permalink: /logo/
author_profile: true
---

## ANTLR

Since Torgo uses ANTLR for language parsing, a grammar file is used to specify the structure of the code. While not completely documented, this grammar file can give an insight into how the language behaves.

- [Logo Grammar](https://raw.githubusercontent.com/ZenHarbinger/torgo/master/src/main/java/org/tros/logo/antlr/Logo.g4)

## Supported Logo Commands

Commands in Torgo are case sensitive!

### repeat

Repeat is a command for repeating a block of code a specified number of times.

### fd | forward

Fd is for forward; which tells the turtle to move a specified number of pixels forward.

### bk | backward

Bk is for backward; which tells the turtle to move a specified number of pixel backward.

### rt | right

Rt is for right; which tells the turtle to move a specified number of degrees (NOT radian) to the right.

### lt | left

Lt is for left; which tells the turtle to move a specified number of degrees (NOT radian) to the left.

### cs | cls | clear | clearscreen

Cs is for clear screen. Resets the graphics on the screen.

### pu | penup

Pu is for pen up. This allows movement of the turtle without drawing on the screen.

### pd | pendown

Pd is for pen down. Any movement the turtle makes after pd (until pu is called) will draw on the screen.

### ht | hideturtle

Ht is for hide turtle. Makes it so that the turtle is now shown on the screen.

### st | showturtle

St is for show turtle. Makes it so that you can see the turtle on the screen.

### home

Home moves the turtle to the center of the drawing area and points the turtle facing straight up.

### setxy

Moves the turtle to the specified X Y coordinates.

### make

Set a variable value.

### localmake

Set a variable value at a local level.

### if

If blocks allow condition testing. If the condition is true, then the internal block is executed. Supported conditional testing are:

- '<'
- '>'
- '=='
- '='
- '<='
- '>='
- '!='
- '<>'

### stop

Stop will allow a block of code to exit from a function.

### for

Allow a for loop. For loops initialize a variable and will loop through the desired values. For loops can increment or decrement in value; this behavior is determined by the start/stop values. An optional step value can be specified as a third argument to the for command.

### pc | pencolor

Set the pen color. This can be hex, r g b, or by name.

### cc | canvascolor

Set the canvas color. This can be hex, r g b, or by name.

### pause

Pause execution for a specified amount of time. Time is specified in milli-seconds.

### ds | drawstring

Draws a specified string to the screen.

### fontsize

Set the font-size for drawing strings.

### fontstyle

Set the font-style for drawing strings (italic, bold, plain).

### fontname

Set the font-name for drawing strings.

### print

Print a value to the console.
