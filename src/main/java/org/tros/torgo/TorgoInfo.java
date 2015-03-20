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

import org.tros.utils.IBuildInfo;
import org.tros.utils.PropertiesInitializer;
import java.io.Serializable;

/**
 * Stores application info.
 * @author matta
 */
public class TorgoInfo extends PropertiesInitializer implements IBuildInfo, Serializable {

//    private String _svn;
//    private String _build;
    private String _version;
    private String _build_date;
    private String _builder;
    private String _company;
    private String _applicationName;
    
    public final static TorgoInfo Instance = new TorgoInfo();

//    @Override
//    public String getSvn() {
//        return _svn;
//    }
//
//    @Override
//    public void setSvn(String value) {
//        _svn = value;
//    }
//
//    @Override
//    public String getBuild() {
//        return _build;
//    }
//
//    @Override
//    public void setBuild(String value) {
//        _build = value;
//    }

    @Override
    public String getVersion() {
        return _version;
    }

    @Override
    public void setVersion(String value) {
        _version = value;
    }

    @Override
    public String getBuildtime() {
        return _build_date;
    }

    @Override
    public void setBuildtime(String value) {
        _build_date = value;
    }

    @Override
    public String getBuilder() {
        return _builder;
    }

    @Override
    public void setBuilder(String value) {
        _builder = value;
    }

    @Override
    public String getApplicationName() {
        return _applicationName;
    }

    @Override
    public void setApplicationName(String value) {
        _applicationName = value;
    }

    @Override
    public String getCompany() {
        return _company;
    }

    @Override
    public void setCompany(String value) {
        _company = value;
    }

    @Override
    public String toString() {
        return _version;// + "." + _svn + "." + _build;
    }
}
