![](http://tros.org/torgo/tros-images/torgo-orange-and-green.svg)  

[![Build Status](https://travis-ci.org/ZenHarbinger/torgo.svg?branch=master)](https://travis-ci.org/ZenHarbinger/torgo)
[![Coverage Status](https://coveralls.io/repos/github/ZenHarbinger/torgo/badge.svg)](https://coveralls.io/github/ZenHarbinger/torgo)
[![release](http://github-release-version.herokuapp.com/github/ZenHarbinger/torgo/release.svg?style=flat)](https://github.com/ZenHarbinger/torgo/releases/latest)
[![Join the chat at https://gitter.im/ZenHarbinger/torgo](https://badges.gitter.im/ZenHarbinger/torgo.svg)](https://gitter.im/ZenHarbinger/torgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
## Logo Interpreter in Java

Torgo is a Logo interpreter written in Java.  It uses [ANTLR](http://www.antlr.org/) as a language parser and for lexical analysis.  The parsed scripts are then walked to interpret the commands.

Torgo is built using Java6.

Some goals for Torgo are:
* to learn to program
* to learn how machines execute programs
* to modify/add languages to change behaviors

This project was inspired by [Tortue](http://tortue.sourceforge.net/).

## Languages
Torgo currently supports logo; jvmBasic and lisp are in development.

## Compile
  1. Check out code
  2. `mvn clean package`

## Run
  1. `java -jar target/torgo-1.0.7.jar`
