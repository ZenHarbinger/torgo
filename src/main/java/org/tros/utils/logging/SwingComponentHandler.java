/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.tros.utils.TypeHandler;

/**
 *
 * @author matta
 */
public final class SwingComponentHandler extends Handler {

    private static final JTextPane TEXT_AREA;
    private static final DefaultStyledDocument DOC;
    private static final HashMap<java.util.logging.Level, Style> STYLE_MAP;
    private static final Style DEFAULT_STYLE;// = TEXT_AREA.addStyle(java.util.logging.Level.WARNING.toString(), null);
    private static final String FORMAT;
    private static boolean paused;

    private final ArrayList<LogRecord> records;
    private final java.util.Timer timer;
    private int maxSize;
    private int timeField;
    private boolean test = false;

    static {
        STYLE_MAP = new HashMap<>();

        StyleContext sc = new StyleContext();
        DOC = new DefaultStyledDocument(sc);
        TEXT_AREA = new JTextPane(DOC);
        TEXT_AREA.setEditable(false);

        LogManager manager = LogManager.getLogManager();
        String cname = SwingComponentHandler.class.getName();

        String def = manager.getProperty(cname + ".default");

        String format = manager.getProperty(cname + ".format");
        FORMAT = format == null ? "" : format;

        Color defColor = def != null ? (Color) TypeHandler.fromString(Color.class, def) : Color.BLACK;
        defColor = defColor == null ? Color.BLACK : defColor;

        String warning = manager.getProperty(cname + ".warning");
        Color warnColor = warning != null ? (Color) TypeHandler.fromString(Color.class, warning) : defColor;
        Style warning2 = TEXT_AREA.addStyle(java.util.logging.Level.WARNING.toString(), null);
        StyleConstants.setForeground(warning2, warnColor == null ? defColor : warnColor);
        STYLE_MAP.put(Level.WARNING, warning2);

        String severe = manager.getProperty(cname + ".severe");
        Color severeColor = severe != null ? (Color) TypeHandler.fromString(Color.class, severe) : defColor;
        Style severe2 = TEXT_AREA.addStyle(java.util.logging.Level.SEVERE.toString(), null);
        StyleConstants.setForeground(severe2, severeColor == null ? defColor : severeColor);
        STYLE_MAP.put(Level.SEVERE, severe2);

        String info = manager.getProperty(cname + ".info");
        Color infoColor = info != null ? (Color) TypeHandler.fromString(Color.class, info) : defColor;
        Style info2 = TEXT_AREA.addStyle(java.util.logging.Level.INFO.toString(), null);
        StyleConstants.setForeground(info2, infoColor == null ? defColor : infoColor);
        STYLE_MAP.put(Level.INFO, info2);

        String all = manager.getProperty(cname + ".all");
        Color allColor = all != null ? (Color) TypeHandler.fromString(Color.class, all) : defColor;
        Style all2 = TEXT_AREA.addStyle(java.util.logging.Level.ALL.toString(), null);
        StyleConstants.setForeground(all2, allColor == null ? defColor : allColor);
        STYLE_MAP.put(Level.ALL, all2);

        String config = manager.getProperty(cname + ".config");
        Color configColor = config != null ? (Color) TypeHandler.fromString(Color.class, config) : defColor;
        Style config2 = TEXT_AREA.addStyle(java.util.logging.Level.CONFIG.toString(), null);
        StyleConstants.setForeground(config2, configColor == null ? defColor : configColor);
        STYLE_MAP.put(Level.CONFIG, config2);

        String fine = manager.getProperty(cname + ".fine");
        Color fineColor = fine != null ? (Color) TypeHandler.fromString(Color.class, fine) : defColor;
        Style fine2 = TEXT_AREA.addStyle(java.util.logging.Level.FINE.toString(), null);
        StyleConstants.setForeground(fine2, fineColor == null ? defColor : fineColor);
        STYLE_MAP.put(Level.FINE, fine2);

        String finer = manager.getProperty(cname + ".finer");
        Color finerColor = finer != null ? (Color) TypeHandler.fromString(Color.class, finer) : defColor;
        Style finer2 = TEXT_AREA.addStyle(java.util.logging.Level.FINER.toString(), null);
        StyleConstants.setForeground(finer2, finerColor == null ? defColor : finerColor);
        STYLE_MAP.put(Level.FINER, finer2);

        String finest = manager.getProperty(cname + ".finest");
        Color finestColor = finest != null ? (Color) TypeHandler.fromString(Color.class, finest) : defColor;
        Style finest2 = TEXT_AREA.addStyle(java.util.logging.Level.FINEST.toString(), null);
        StyleConstants.setForeground(finest2, finestColor == null ? defColor : finestColor);
        STYLE_MAP.put(Level.FINEST, finest2);

        String off = manager.getProperty(cname + ".off");
        Color offColor = off != null ? (Color) TypeHandler.fromString(Color.class, off) : defColor;
        Style off2 = TEXT_AREA.addStyle(java.util.logging.Level.OFF.toString(), null);
        StyleConstants.setForeground(off2, offColor == null ? defColor : offColor);
        STYLE_MAP.put(Level.OFF, off2);

        DEFAULT_STYLE = TEXT_AREA.addStyle(java.util.logging.Level.ALL.toString(), null);
        StyleConstants.setForeground(DEFAULT_STYLE, defColor);
    }

    public SwingComponentHandler() {
        configure();
        paused = false;
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                timer();
            }
        }, timeField, timeField);
        records = new ArrayList<>();
        Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.FINE, "Started...");
    }

    public boolean checkTesting() {
        return test;
    }

    public void testConfigure(int which) {
        test = true;
        switch (which) {
            case 1:
                configSize();
            case 2:
                configTime();
            case 3:
                configLevel();
            case 4:
                configFormat();
            case 5:
                configFilter();
        }
    }

    private void configure() {
        configSize();
        configTime();
        configLevel();
        configFormat();
        configFilter();
    }

    private void configSize() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        String size = manager.getProperty(cname + ".limit");
        int s = 50000;
        if (test) {
            size = "5";
        }
        if (size != null) {
            try {
                test = false;
                s = Integer.parseInt(size);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLimit(s);
    }

    private void configTime() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        String timerProp = manager.getProperty(cname + ".timer");
        int time = 500;
        if (timer != null || test) {
            try {
                test = false;
                time = Integer.parseInt(timerProp);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.timeField = time;
    }

    private void configLevel() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        String level = manager.getProperty(cname + ".level");
        Level def = Level.INFO;
        if (test) {
            level = "five";
        }
        if (level != null) {
            try {
                test = false;
                def = Level.parse(level);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLevel(def);
    }

    private void configFormat() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        String formatter = manager.getProperty(cname + ".formatter");
        Formatter fmmt = null;
        if (test) {
            formatter = "test";
        }
        if (formatter != null) {
            try {
                test = false;
                fmmt = (Formatter) Class.forName(formatter).getConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fmmt == null) {
            fmmt = new SimpleFormatter();
        }
        this.setFormatter(fmmt);
    }

    private void configFilter() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        String filter = manager.getProperty(cname + ".filter");
        Filter filt = null;
        if (test) {
            filter = "test";
        }
        if (filter != null) {
            try {
                test = false;
                filt = (Filter) Class.forName(filter).getConstructor().newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setFilter(filt);
    }

    public void setLimit(int value) {
        maxSize = value;
    }

    public int getLimit() {
        return maxSize;
    }

    @Override
    public void publish(final LogRecord record) {
        if (record.getLevel().intValue() >= this.getLevel().intValue()) {
            synchronized (records) {
                records.add(record);
            }
        }
    }

    public static JComponent getComponent() {
        return TEXT_AREA;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
        timer.purge();
        timer.cancel();
    }

    public static void pause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    private void timer() {
        if (paused) {
            return;
        }

        final ArrayList<LogRecord> rec = new ArrayList<>();
        synchronized (records) {
            rec.addAll(records);
            records.clear();
        }

        final java.util.logging.Formatter f = getFormatter();
        final Date dat = new Date();

        SwingUtilities.invokeLater(() -> {
            rec.forEach((record) -> {
                try {
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
                    StringBuilder nameBuilder = new StringBuilder();

                    String[] names = record.getLoggerName().split("\\.");
                    for (int ii = 0; ii < names.length - 1; ii++) {
                        nameBuilder.append(names[ii].charAt(0));
                    }
                    nameBuilder.append(".").append(names[names.length - 1]);

                    String throwable = "";
                    if (record.getThrown() != null) {
                        StringWriter sw = new StringWriter();
                        try (PrintWriter pw = new PrintWriter(sw)) {
                            pw.println();
                            record.getThrown().printStackTrace(pw);
                        }
                        throwable = sw.toString();
                    }
                    String toInsert = String.format(FORMAT,
                            dat,
                            source,
                            nameBuilder.toString(),
                            record.getLevel().getLocalizedName(),
                            record.getMessage(),
                            throwable);

                    Style s = STYLE_MAP.containsKey(record.getLevel()) ? STYLE_MAP.get(record.getLevel()) : DEFAULT_STYLE;
                    StyleConstants.setBold(s, true);
                    DOC.insertString(DOC.getLength(), toInsert + " ", s);
                    StyleConstants.setBold(s, false);
                    DOC.insertString(DOC.getLength(), f.format(record), s);
                } catch (BadLocationException ex) {
                    Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TEXT_AREA.select(DOC.getLength(), DOC.getLength());
        });
    }

    /**
     * For testing of publish method.
     *
     * @return the list of records to process.
     */
    public List<LogRecord> getRecords() {
        return records;
    }
}
