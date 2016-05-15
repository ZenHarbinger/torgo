/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tros.torgo;

import org.tros.utils.BuildInfo;
import org.tros.utils.PropertiesInitializer;
import java.io.Serializable;

/**
 * Stores application info.
 *
 * @author matta
 */
public class TorgoInfo extends PropertiesInitializer implements BuildInfo, Serializable {

    private String _version;
    private String _build_date;
    private String _builder;
    private String _company;
    private String _applicationName;
    private boolean _snapd;

    /**
     * Singleton Instance.
     */
    public final static TorgoInfo INSTANCE = new TorgoInfo();

    /**
     * Get if the app is being run from within snap. This value is able to be
     * read from two different places. The properties file set at build time as
     * well as on the command line at run time using -Dsnapd=true when invoking
     * java.
     *
     * @return
     */
    public boolean isSnapd() {
        String snapd = System.getProperty("snapd");
        boolean ret = _snapd;
        if (snapd != null) {
            ret = ret || Boolean.parseBoolean(snapd);
        }
        return ret;
    }

    /**
     * Set if the app is being run from within snap. This value is read in from
     * the properties file and so is optimally set at build time using
     * -Dsnapd=true when invoking maven.
     *
     * @param value
     */
    public void setSnapd(boolean value) {
        _snapd = value;
    }

    /**
     * Version Accessor.
     *
     * @return
     */
    @Override
    public String getVersion() {
        return _version;
    }

    /**
     * Version Mutator.
     *
     * @param value
     */
    @Override
    public void setVersion(String value) {
        _version = value;
    }

    /**
     * Build Time Accessor.
     *
     * @return
     */
    @Override
    public String getBuildtime() {
        return _build_date;
    }

    /**
     * Build Time Mutator.
     *
     * @param value
     */
    @Override
    public void setBuildtime(String value) {
        _build_date = value;
    }

    /**
     * Get the user that built the last instance.
     *
     * @return
     */
    @Override
    public String getBuilder() {
        return _builder;
    }

    /**
     * Set the user that built the last instance.
     *
     * @param value
     */
    @Override
    public void setBuilder(String value) {
        _builder = value;
    }

    /**
     * Get the application name.
     *
     * @return
     */
    @Override
    public String getApplicationName() {
        return _applicationName;
    }

    /**
     * Set the application name.
     *
     * @param value
     */
    @Override
    public void setApplicationName(String value) {
        _applicationName = value;
    }

    /**
     * Get the company name.
     *
     * @return
     */
    @Override
    public String getCompany() {
        return _company;
    }

    /**
     * Set the company name.
     *
     * @param value
     */
    @Override
    public void setCompany(String value) {
        _company = value;
    }

    /**
     * To string, return version.
     *
     * @return
     */
    @Override
    public String toString() {
        return _version;
    }
}
