---
layout: page
title: About
permalink: /about/
author_profile: true
---

[![Build Status](https://travis-ci.org/ZenHarbinger/torgo.svg?branch=master)](https://travis-ci.org/ZenHarbinger/torgo)<br>
[![Join the chat at https://gitter.im/ZenHarbinger/torgo](https://badges.gitter.im/ZenHarbinger/torgo.svg)](https://gitter.im/ZenHarbinger/torgo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# Logo Interpreter in Java

Torgo is a Logo interpreter written in Java. It uses [ANTLR](http://www.antlr.org/) as a language parser and for lexical analysis. The parsed scripts are then walked to interpret the commands.

Torgo is built using Java6.

Some goals for Torgo are:

- to learn to program
- to learn how machines execute programs
- to modify/add languages to change behaviors

This project was inspired by [Tortue](http://tortue.sourceforge.net/).

# Languages

Torgo currently supports logo; jvmBasic and lisp are in development.

# Install on Recent Linux Distros

`sudo snap install torgo`

# Download

- git clone: `git clone https://github.com/ZenHarbinger/torgo.git`
- [tarbal](https://github.com/ZenHarbinger/torgo/tarball/master)
- [zipball](https://github.com/ZenHarbinger/torgo/zipball/master)

# Compile

```sh
git clone https://github.com/ZenHarbinger/torgo.git
cd torgo
mvn clean package
```

# Run

```sh
java -jar target/torgo-1.0.6.jar
```
