/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.io.File;

/**
 *
 * @author matta
 */
public final class PathUtils {

    @CoverageIgnore
    private PathUtils() {
    }

    /**
     * Get the specified home directory. This value is read from the System
     * property "tros.home" first. If this value is not specified, it uses
     * "user.home". If this value cannot be written to (in the case of being
     * used by tomcat), then the "java.io.tmpdir" value is used.
     *
     * @return A string value for a path that can be written to.
     */
    public static String getHomeDir() {
        return getDir("tros.home");
    }

    /**
     * Get the specified property directory.
     *
     * If the directory us not writable, the temp directory is returned.
     *
     * @param prop the property to get a directory for.
     * @return a directory path for the specified property.
     */
    public static String getDir(String prop) {
        String dir = System.getProperty(prop);
        if (dir == null) {
            dir = System.getProperty("user.home");
        }
        java.io.File f = new java.io.File(dir);
        if (!f.canWrite()) {
            dir = System.getProperty("java.io.tmpdir");
        }
        return dir;
    }

    /**
     * Get the temp dir.
     *
     * @return the temp dir.
     */
    public static String getTempDir() {
        String dir = System.getProperty("torgo.temp");
        if (dir == null) {
            dir = System.getProperty("java.io.tmpdir");
        }
        return dir;
    }

    /**
     * Get the temp dir.
     *
     * @param bi Application description.
     * @return the path to the temp dir.
     */
    public static String getTempDir(BuildInfo bi) {
        String home = System.getProperty("torgo.temp");
        String ret;
        if (home == null) {
            home = getApplicationDirectory(bi);
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%stmp", home, sep);
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

    /**
     * Get the specified Application directory. This directory is based on the
     * getHomeDir() return value.
     *
     * @param bi Application description.
     * @return A string value for a path that can be written to for application
     * specific data.
     */
    public static String getApplicationDirectory(BuildInfo bi) {
        String home = System.getProperty("torgo.home");
        String ret;
        if (home == null) {
            home = getHomeDir();
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%s.%s%s%s", home, sep, bi.getCompany(), sep, bi.getApplicationName());
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

    /**
     * Get the etc dir.
     *
     * @param bi Application description.
     * @return A string value for a path that can be read containing config
     * files.
     */
    public static String getApplicationEtcDirectory(BuildInfo bi) {
        String home = System.getProperty("torgo.etc");
        String ret;
        if (home == null) {
            home = getHomeDir();
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%s.%s%s%s", home, sep, bi.getCompany(), sep, bi.getApplicationName());
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

    /**
     * Get the lib dir.
     *
     * @param bi Application description.
     * @return A string value for a path that can be read containing plugin jar
     * files.
     */
    public static String getApplicationLibDirectory(BuildInfo bi) {
        String home = System.getProperty("torgo.lib");
        String ret;
        if (home == null) {
            home = getApplicationEtcDirectory(bi);
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%slib", home, sep);
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

    /**
     * Get the Conf directory. This directory is based on the
     * getApplicationDir() return value.
     *
     * @param bi Application description.
     * @return A string value for a path that can be written to for log files.
     */
    public static String getApplicationConfigDirectory(BuildInfo bi) {
        String home = System.getProperty("torgo.conf");
        String ret;
        if (home == null) {
            home = getApplicationEtcDirectory(bi);
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%sconf", home, sep);
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

    /**
     * Get the Log directory. This directory is based on the getApplicationDir()
     * return value.
     *
     * @param bi Application description.
     * @return A string value for a path that can be written to for log files.
     */
    public static String getLogDirectory(BuildInfo bi) {
        String home = System.getProperty("torgo.log");
        String ret;
        if (home == null) {
            home = getApplicationDirectory(bi);
            String sep = File.separator;
            //this should come out to something like: /home/matta/.ArtisTech/AlgoLink
            ret = String.format("%s%slog", home, sep);
        } else {
            ret = home;
        }
        java.io.File dir = new java.io.File(ret);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return ret;
    }

}
