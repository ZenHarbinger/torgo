/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

/**
 * Interface for build info.
 *
 * @author matta
 */
public interface BuildInfo {

    String getBuilder();

    String getBuildtime();

    String getVersion();

    void setBuilder(String value);

    void setBuildtime(String value);

    void setVersion(String value);

    String getApplicationName();

    void setApplicationName(String value);

    String getCompany();

    void setCompany(String value);

    String getCopy();

    String getAbout();
}
