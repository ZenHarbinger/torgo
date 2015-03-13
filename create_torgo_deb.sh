#!/bin/bash

ANT_OPTS=
COPY_LIBS_JAR=lib/netbeans/org-netbeans-modules-java-j2seproject-copylibstask.jar
SVN=0
BUILD=0
VERSION=1.0
DATE=`date -R`

print_changelog() {
    echo "torgo ($3-$1.$2) unstable; urgency=low" > debian/changelog
    echo "  * Debian Package" >> debian/changelog
    echo " -- Matthew Aguirre <matt.aguirre@gmail.com>  $DATE" >> debian/changelog
}

print_rules() {
    echo "#!/usr/bin/make -f" > debian/rules
    echo "%: ; dh $@" >> debian/rules
    echo "" >> debian/rules
    echo "override_dh_auto_build: ; dh_auto_build -- $ANT_OPTS -Dsvn=$1 -Dbuild=$2 -Dversion=$3 clean jar" >> debian/rules
    chmod 755 debian/rules
}

print_install() {
    echo "dist/* usr/share/torgo" > debian/torgo.install
    #echo "App.ico usr/share/torgo" >> debian/torgo.install
    #echo "torgo.desktop usr/share/applications" >> debian/torgo.install
}

while getopts "c:s:b:v:" opt; do
    case $opt in
        c)
            COPY_LIBS_JAR=$OPTARG
            ;;
        s)
            SVN=$OPTARG
            ;;
        b)
            BUILD=$OPTARG
            ;;
        v)
            VERSION=$OPTARG
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            ;;
    esac
done

if [[ -f $COPY_LIBS_JAR ]]; then
    ANT_OPTS=$ANT_OPTS" -Dlibs.CopyLibs.classpath="$COPY_LIBS_JAR
fi

print_changelog $SVN $BUILD $VERSION
#print_rules $SVN $BUILD $VERSION
#print_install

rm -rf debian/torgo
rm -rf torgo*.deb
rm -rf debian/torgo.debhelper.log
rm -rf debian/torgo.substvars

./debian/rules
fakeroot dh binary
mv ../*deb .

rm -rf debian/torgo
rm -rf debian/torgo.substvars
rm -rf debian/torgo.debhelper.log
rm -rf debian/files
