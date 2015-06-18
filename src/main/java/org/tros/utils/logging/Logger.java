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
public interface Logger {

    void warn(String message);

    void debug(String message);

    void error(String message);

    void info(String message);

    void verbose(String message);
    
    void fatal(String message);

    void warn(String format, Object... objs);

    void debug(String format, Object... objs);

    void error(String format, Object... objs);

    void info(String format, Object... objs);

    void verbose(String format, Object... objs);

    void fatal(String format, Object... objs);

    void warn(String message, Throwable thrw);

    void debug(String message, Throwable thrw);

    void error(String message, Throwable thrw);

    void info(String message, Throwable thrw);

    void verbose(String message, Throwable thrw);

    void fatal(String message, Throwable thrw);
}
