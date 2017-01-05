![](http://tros.org/torgo/tros-images/torgo-orange-and-green.svg)  

[![Build Status](https://travis-ci.org/ZenHarbinger/torgo.svg?branch=master)](https://travis-ci.org/ZenHarbinger/torgo)
[![Coverage Status](https://coveralls.io/repos/github/ZenHarbinger/torgo/badge.svg)](https://coveralls.io/github/ZenHarbinger/torgo)
[![release](http://github-release-version.herokuapp.com/github/ZenHarbinger/torgo/release.svg?style=flat)](https://github.com/ZenHarbinger/torgo/releases/latest)
[![Join the chat at https://gitter.im/ZenHarbinger/torgo](https://badges.gitter.im/ZenHarbinger/torgo.svg)](https://gitter.im/ZenHarbinger/torgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# Logo Interpreter in Java

Torgo is a Logo interpreter written in Java. It uses [ANTLR](http://www.antlr.org/) as a language parser and for lexical analysis. The parsed scripts are then walked to interpret the commands.

Torgo is built using Java7.

Some goals for Torgo are:

- to allow people to learn to program
- to show how machines execute programs
- to allow modifications and add to languages to change behaviors

This project was inspired by [Tortue](http://tortue.sourceforge.net/).

## Languages

Torgo currently supports logo; jvmBasic and lisp are in development.

## Features

- Customizable GUI with [DockingFrames](https://github.com/Benoker/DockingFrames)
- 'Debugging' a script
    - Allows pause/resume/stop
    - Can see the current operation highlighted in the script window
- Trace Logging of execution
- Call-Stack Watch
- Variable Watch
- Breakpoints
- Export to various image types:
    - PNG
    - SVG
    - Animated GIF
- Switch between any available languages
    - dynamic-logo
    - lexical-logo
    - *jvmBasic*
    - *lisp*

## Future

- jvmBasic and Lisp
- Register with file/mime types (.logo)

## Download and Install

- [Download Installer](https://github.com/ZenHarbinger/torgo/releases/latest)
- `sudo snap install torgo`

## Download Snapshot

- git clone: `git clone https://github.com/ZenHarbinger/torgo.git`
- [tarball](https://github.com/ZenHarbinger/torgo/tarball/master)
- [zipball](https://github.com/ZenHarbinger/torgo/zipball/master)

## Compile

```sh
git clone https://github.com/ZenHarbinger/torgo.git
cd torgo
mvn -DskipTests clean package
```

## Run

```sh
java -jar target/torgo-1.4.2.jar
```
