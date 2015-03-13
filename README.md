# torgo
##Logo Interpreter in Java
Torgo is a Logo interpreter written in Java.  It uses [ANTLR](http://www.antlr.org/) as a language parser and for lexical analysis.  The parsed scripts are then walked to interpret the commands.

Torgo is built using Java8 (use of lamba for personal learning).

Some goals for Torgo are:
* to learn to program
* to learn how machines execute programs
* to modify/add languages to change behaviors

This project was inspired by [Tortue](http://tortue.sourceforge.net/).

##Languages
Torgo currently supports logo; jvmBasic and lisp are in development.

##Requirements
All requirements (and optional jars) for compiling and running are available in the /lib directory of the project.

### Run
* [Java8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) (for lambdas)
* [apache-commons-io](http://commons.apache.org/proper/commons-io/)
* [apache-commons-collections](http://commons.apache.org/proper/commons-collections/)
* [apache-commons-cli](http://commons.apache.org/proper/commons-cli/)
* [ANTLR 4.5](http://www.antlr.org/)

### Optional
* [RSyntaxArea](http://fifesoft.com/rsyntaxtextarea/) - Will compile and run without this jar, but looks nicer with it.

### Building
* org-netbeans-modules-java-j2seproject-copylibstask.jar

##Compile
  1. Check out code
  2. `ant clean jar`

##Run
  1. `java -jar dist/torgo.jar`
