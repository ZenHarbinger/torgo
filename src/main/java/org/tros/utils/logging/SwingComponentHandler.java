/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
*/
package org.tros.utils.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author matta
 */
public final class SwingComponentHandler extends Handler {

    private static final JTextArea textArea = new JTextArea(50, 50);
    private int _maxSize;
    private static final String nl = System.getProperty("line.separator");
    private final ArrayList<LogRecord> records;
    private final java.util.Timer timer;
    private static boolean paused;

    static {
        textArea.setEditable(false);
    }

    public SwingComponentHandler() {
        _maxSize = 50000;
        paused = false;
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                timer();
            }
        }, 500, 500);
        records = new ArrayList<>();
        Logger.getLogger(SwingComponentHandler.class.getName()).log(Level.FINE, "Started...");
    }

    @Override
    public void setFormatter(Formatter frmtr) throws SecurityException {
        super.setFormatter(frmtr);
    }

    @Override
    public Formatter getFormatter() {
        return super.getFormatter(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void setLevel(Level level) throws SecurityException {
        super.setLevel(level); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized Level getLevel() {
        return super.getLevel(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setLimit(int value) {
        _maxSize = value;
    }

    public int getLimit() {
        return _maxSize;
    }

    @Override
    public void publish(final LogRecord record) {
        synchronized (records) {
            records.add(record);
        }
    }

    public static JTextArea getTextArea() {
        return textArea;
    }

    @Override
    public void flush() {
    }

    private boolean halt = false;

    @Override
    public void close() throws SecurityException {
        halt = true;
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

        final ArrayList<LogRecord> rec = new ArrayList<>();
        synchronized (records) {
            rec.addAll(records);
            records.clear();
        }
        java.util.logging.Formatter formatter = this.getFormatter();
        if (formatter == null) {
            this.setFormatter(new org.tros.utils.logging.SimpleLogFormatter());
        }
        final java.util.logging.Formatter f = getFormatter();

        SwingUtilities.invokeLater(() -> {
            StringWriter text = new StringWriter();
            PrintWriter out = new PrintWriter(text);
            int lc = textArea.getLineCount();
            if (lc + rec.size() > _maxSize) {
                if (_maxSize - rec.size() > 0) {
                    String[] lines = textArea.getText().split(nl);
                    String[] output = new String[_maxSize - rec.size()];
                    System.arraycopy(lines, lines.length - _maxSize + rec.size(), output, 0, output.length);
                    text = new StringWriter();
                    out = new PrintWriter(text);
                    for (String output1 : output) {
                        out.printf(output1 + nl);
                    }
                    for (LogRecord record : rec) {
                        out.printf(f.format(record));
                    }
                } else {
                    for (LogRecord record : rec) {
                        out.printf(f.format(record));
                    }
                }
            } else {
                out.printf(textArea.getText());
                for (LogRecord record : rec) {
                    out.printf(f.format(record));
                }
            }

            textArea.setText(text.toString());
        });
    }
}
