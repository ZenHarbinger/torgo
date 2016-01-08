/*
 * Copyright 2015 ArtisTech, Inc.
 */
package org.tros.utils.logging;

/**
 *
 * @author matta
 */

public class ConsoleLogFormatter extends ShortenedNameFormatter {

    private static final String DEFAULT_FORMAT = "%5$s %6$s%n";

    public ConsoleLogFormatter() {
        super(DEFAULT_FORMAT);
    }    
}
