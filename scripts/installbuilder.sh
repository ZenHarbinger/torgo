#!/bin/bash
echo "/opt/installbuilder-8.6.0/bin/builder TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2"

if [ -d '/opt/installbuilder-8.6.0' ]; then
    /opt/installbuilder-8.6.0/bin/builder build TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2
else
    echo '/opt/installbuilder-8.6.0 not found'
fi
