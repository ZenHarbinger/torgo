#!/bin/bash

if [ -d $3 ]; then
    echo "$3/bin/builder build TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2"
    $3/bin/builder build TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2
elif [ -d '/opt/installbuilder-8.6.0' ]; then
    echo "/opt/installbuilder-8.6.0/bin/builder build TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2"
    /opt/installbuilder-8.6.0/bin/builder build TorgoInstallBuilder.xml windows --setvars project.outputDirectory=$1 project.version=$2
else
    echo '/opt/installbuilder-8.6.0 not found'
fi
