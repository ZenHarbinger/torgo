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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
//import sun.util.logging.LoggingSupport;

public final class SimpleLogFormatter extends Formatter {

    private final String format;
    private final Date dat = new Date();

    public SimpleLogFormatter() {
        String property = LogManager.getLogManager().getProperty(SimpleLogFormatter.class.getName() + ".format");
        format = property == null ? "[%1$tc] %4$s: %2$s - %5$s %6$s%n" : property;
    }
    
    @Override
    public String format(LogRecord record) {
        StringBuilder nameBuilder = new StringBuilder();

        String[] names = record.getLoggerName().split("\\.");
        for (int ii = 0; ii < names.length - 1; ii++) {
            nameBuilder.append(names[ii].charAt(0));
        }
        nameBuilder.append(".").append(names[names.length - 1]);

        dat.setTime(record.getMillis());
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(format,
                dat,
                source,
                nameBuilder.toString(),
                record.getLevel().getLocalizedName(),
                message,
                throwable);
    }
}
