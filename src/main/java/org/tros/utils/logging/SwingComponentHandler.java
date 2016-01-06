/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
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

/**
 *
 * @author matta
 */
public final class SwingComponentHandler extends Handler {

    private static final JTextPane TEXT_AREA;
    private int _maxSize;
    private final ArrayList<LogRecord> records;
    private final java.util.Timer timer;
    private static boolean paused;
    private static final DefaultStyledDocument DOC;
    private static final HashMap<java.util.logging.Level, Style> STYLE_MAP;
    private static final Style DEFAULT_STYLE;// = TEXT_AREA.addStyle(java.util.logging.Level.WARNING.toString(), null);
    private int time_field;

    static {
        STYLE_MAP = new HashMap<java.util.logging.Level, Style>();

        StyleContext sc = new StyleContext();
        DOC = new DefaultStyledDocument(sc);
        TEXT_AREA = new JTextPane(DOC);
        TEXT_AREA.setEditable(false);

        Style warning = TEXT_AREA.addStyle(java.util.logging.Level.WARNING.toString(), null);
        StyleConstants.setForeground(warning, new Color(255, 102, 0));
        STYLE_MAP.put(Level.WARNING, warning);
        Style severe = TEXT_AREA.addStyle(java.util.logging.Level.SEVERE.toString(), null);
        StyleConstants.setForeground(severe, new Color(102, 0, 0));
        STYLE_MAP.put(Level.SEVERE, severe);

        Style info = TEXT_AREA.addStyle(java.util.logging.Level.INFO.toString(), null);
        StyleConstants.setForeground(info, new Color(48, 80, 32));
        STYLE_MAP.put(Level.INFO, info);

        DEFAULT_STYLE = TEXT_AREA.addStyle(java.util.logging.Level.ALL.toString(), null);
        StyleConstants.setForeground(DEFAULT_STYLE, Color.BLACK);
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
        }, time_field, time_field);
        records = new ArrayList<LogRecord>();
        Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.FINE, "Started...");
    }

    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();

        String level = manager.getProperty(cname + ".level");
        String filter = manager.getProperty(cname + ".filter");
        String formatter = manager.getProperty(cname + ".formatter");
        String size = manager.getProperty(cname + ".limit");
        String timer_prop = manager.getProperty(cname + ".timer");

        int s = 50000;
        if (size != null) {
            try {
                s = Integer.parseInt(size);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLimit(s);

        int time = 500;
        if (timer != null) {
            try {
                time = Integer.parseInt(timer_prop);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.time_field = time;

        Level def = Level.INFO;
        if (level != null) {
            try {
                def = Level.parse(level);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLevel(def);

        Formatter fmmt = null;
        if (formatter != null) {
            try {
                fmmt = (Formatter) Class.forName(formatter).newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fmmt == null) {
            fmmt = new SimpleFormatter();
        }
        this.setFormatter(fmmt);

        Filter filt = null;
        if (filter != null) {
            try {
                filt = (Filter) Class.forName(filter).newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setFilter(filt);
    }

    public void setLimit(int value) {
        _maxSize = value;
    }

    public int getLimit() {
        return _maxSize;
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

    private void timer() {
        if (paused) {
            return;
        }

        final ArrayList<LogRecord> rec = new ArrayList<LogRecord>();
        synchronized (records) {
            rec.addAll(records);
            records.clear();
        }

        final java.util.logging.Formatter f = getFormatter();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                for (LogRecord record : rec) {
                    try {
                        Style s = STYLE_MAP.containsKey(record.getLevel()) ? STYLE_MAP.get(record.getLevel()) : DEFAULT_STYLE;

                        String[] names = record.getLoggerName().split("\\.");
                        String name = names[names.length - 1];
                        StyleConstants.setBold(s, true);
                        DOC.insertString(DOC.getLength(), record.getLevel() + ": [" + name + "] ", s);
                        StyleConstants.setBold(s, false);
                        DOC.insertString(DOC.getLength(), f.format(record), s);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                TEXT_AREA.select(DOC.getLength(), DOC.getLength());
            }

        });
    }
}
