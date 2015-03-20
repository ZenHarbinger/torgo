#!/bin/bash
echo "java -jar /opt/launch4j/launch4j.jar $1"

if [ -d '/opt/launch4j/' ]; then
    java -jar /opt/launch4j/launch4j.jar $1
else
    echo "/opt/launch4j not found"
fi
