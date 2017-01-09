/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.logging;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import org.tros.torgo.Main;

/**
 * Singleton GUI log console for viewing log output.
 *
 * @author matta
 */
public final class LogConsole extends JFrame {

    private final JCheckBoxMenuItem scrollLock_menu;
    private final JCheckBoxMenuItem pause_menu;
    private final SwingComponentHandler sch;
    public static final LogConsole CONSOLE = new LogConsole();

    /**
     * Constructor.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    private LogConsole() {
        this(Level.FINER);
        Main.loadIcon(this);
    }

    /**
     * Constructor.
     *
     * @param level
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    private LogConsole(Level level) {
        Main.loadIcon((JFrame) this);
        JMenuBar main_menu = new JMenuBar();
        this.setJMenuBar(main_menu);

        sch = new SwingComponentHandler();
        sch.setLevel(level);
        Logger.getLogger("").addHandler(sch);

        JMenu file_menu = new JMenu("File");
        pause_menu = new JCheckBoxMenuItem("Pause Console");
        scrollLock_menu = new JCheckBoxMenuItem("Scroll Lock");
        scrollLock_menu.setEnabled(false);
        JMenuItem close_menu = new JMenuItem("Close");

        pause_menu.addActionListener((ActionEvent ae) -> {
            SwingComponentHandler.pause();
        });

        close_menu.addActionListener((ActionEvent ae) -> {
            setVisible(false);
        });

        file_menu.add(pause_menu);
        file_menu.add(scrollLock_menu);
        file_menu.add(close_menu);

        main_menu.add(file_menu);

        this.setTitle("Log Console");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        int height = 400;
        int width = (int) (height * 1.61803398875);
        this.setSize(width, height);

        this.add(new JScrollPane(SwingComponentHandler.getComponent()));

        final Preferences prefs = Preferences.userNodeForPackage(LogConsole.class);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent ce) {
                prefs.putInt(LogConsole.class.getName() + "Width", getWidth());
                prefs.putInt(LogConsole.class.getName() + "Height", getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent ce) {
                prefs.putInt(LogConsole.class.getName() + "X", getX());
                prefs.putInt(LogConsole.class.getName() + "Y", getY());
            }

            @Override
            public void componentShown(ComponentEvent ce) {
            }

            @Override
            public void componentHidden(ComponentEvent ce) {
            }
        });
        width = prefs.getInt(LogConsole.class.getName() + "Width", getWidth());
        height = prefs.getInt(LogConsole.class.getName() + "Height", getHeight());

        int x = prefs.getInt(LogConsole.class.getName() + "X", getX());
        int y = prefs.getInt(LogConsole.class.getName() + "Y", getY());

        this.setSize(width, height);
        this.setLocation(x, y);
    }
}
