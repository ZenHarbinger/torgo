/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tros.logo.rsyntax;

import org.fife.ui.rsyntaxtextarea.TokenMakerRegistration;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;

/**
 *
 * @author matta
 */
public class LogoTokenRegistration implements TokenMakerRegistration {

    public static final String SYNTAX_STYLE	= "text/logo";

    @Override
    public String getLanguage() {
        return SYNTAX_STYLE;
    }

    @Override
    public String getTokenMaker() {
        return LogoTokenMaker.class.getName();
    }
    
    @Override
    public FoldParser getFoldParser() {
        return null;
    }
}