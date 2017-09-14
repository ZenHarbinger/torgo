/*
 * Copyright 2015-2017 Matthew Aguirre
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

    /**
     * Singleton Instance.
     */
//    public static final TorgoInfo INSTANCE = new TorgoInfo();

    private String version;
    private String buildDate;
    private String builder;
    private String company;
    private String applicationName;

    /**
     * Version Accessor.
     *
     * @return the major.minor version.
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * Version Mutator.
     *
     * @param value the major.minor version.
     */
    @Override
    public void setVersion(String value) {
        version = value;
    }

    /**
     * Build Time Accessor.
     *
     * @return the build time.
     */
    @Override
    public String getBuildtime() {
        return buildDate;
    }

    /**
     * Build Time Mutator.
     *
     * @param value the build time.
     */
    @Override
    public void setBuildtime(String value) {
        buildDate = value;
    }

    /**
     * Get the user that built the last instance.
     *
     * @return the builder.
     */
    @Override
    public String getBuilder() {
        return builder;
    }

    /**
     * Set the user that built the last instance.
     *
     * @param value the builder.
     */
    @Override
    public void setBuilder(String value) {
        builder = value;
    }

    /**
     * Get the application name.
     *
     * @return the application name.
     */
    @Override
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Set the application name.
     *
     * @param value the application name.
     */
    @Override
    public void setApplicationName(String value) {
        applicationName = value;
    }

    /**
     * Get the company name.
     *
     * @return the company name.
     */
    @Override
    public String getCompany() {
        return company;
    }

    /**
     * Set the company name.
     *
     * @param value the company name.
     */
    @Override
    public void setCompany(String value) {
        company = value;
    }

    /**
     * To string, return version.
     *
     * @return the major.minor version.
     */
    @Override
    public String toString() {
        return version;
    }

    @Override
    public String getCopy() {
        return "© 2015-2017 Matthew Aguirre, Apache License 2.0";
    }

    @Override
    public String getAbout() {
        return "Torgo is a flexible interpreter written in Java.";
    }
}
