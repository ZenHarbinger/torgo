#!/bin/bash

if [ -d $2 ]; then
	echo "java -jar $2/launch4j.jar $1"
    java -jar $2/launch4j.jar $1
elif [ -d '/opt/launch4j/' ]; then
	echo "java -jar /opt/launch4j/launch4j.jar $1"
    java -jar /opt/launch4j/launch4j.jar $1
else
    echo "/opt/launch4j not found"
fi
