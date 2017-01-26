/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
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
