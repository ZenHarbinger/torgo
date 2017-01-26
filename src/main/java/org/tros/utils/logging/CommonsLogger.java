/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import java.text.MessageFormat;

/**
 *
 * @author matta
 */
public final class CommonsLogger implements Logger {

    private final org.apache.commons.logging.Log log;

    protected CommonsLogger(org.apache.commons.logging.Log log) {
        this.log = log;
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void warn(String format, Object... objs) {
        log.warn(MessageFormat.format(format, objs));
    }

    @Override
    public void warn(String message, Throwable thrw) {
        log.warn(message, thrw);
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void debug(String format, Object... objs) {
        log.debug(MessageFormat.format(format, objs));
    }

    @Override
    public void debug(String message, Throwable thrw) {
        log.debug(message, thrw);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String format, Object... objs) {
        log.error(MessageFormat.format(format, objs));
    }

    @Override
    public void error(String message, Throwable thrw) {
        log.error(message, thrw);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void info(String format, Object... objs) {
        log.info(MessageFormat.format(format, objs));
    }

    @Override
    public void info(String message, Throwable thrw) {
        log.info(message, thrw);
    }

    @Override
    public void verbose(String message) {
        log.trace(message);
    }

    @Override
    public void verbose(String format, Object... objs) {
        log.trace(MessageFormat.format(format, objs));
    }


    @Override
    public void verbose(String message, Throwable thrw) {
        log.trace(message, thrw);
    }

    @Override
    public void fatal(String message) {
        log.fatal(message);
    }

    @Override
    public void fatal(String format, Object... objs) {
        log.fatal(MessageFormat.format(format, objs));
    }

    @Override
    public void fatal(String message, Throwable thrw) {
        log.fatal(message, thrw);
    }

}
