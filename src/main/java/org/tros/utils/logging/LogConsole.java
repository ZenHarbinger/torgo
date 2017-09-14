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
import org.tros.torgo.swing.Localization;

/**
 * Singleton GUI log console for viewing log output.
 *
 * @author matta
 */
public final class LogConsole extends JFrame {

    public static final LogConsole CONSOLE = new LogConsole();
    private final JCheckBoxMenuItem scrollLockMenu;
    private final JCheckBoxMenuItem pauseMenu;
    private final SwingComponentHandler sch;

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
     * @param level the level to log at.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    private LogConsole(Level level) {
        Main.loadIcon((JFrame) this);
        JMenuBar mainMenu = new JMenuBar();
        this.setJMenuBar(mainMenu);

        sch = new SwingComponentHandler();
        sch.setLevel(level);
        Logger.getLogger("").addHandler(sch);

        pauseMenu = new JCheckBoxMenuItem(Localization.getLocalizedString("LogConsolePause"));
        scrollLockMenu = new JCheckBoxMenuItem(Localization.getLocalizedString("LogConsoleScrollLock"));
        scrollLockMenu.setEnabled(false);
        JMenuItem closeMenu = new JMenuItem(Localization.getLocalizedString("LogConsoleClose"));

        pauseMenu.addActionListener((ActionEvent ae) -> {
            SwingComponentHandler.pause();
        });

        closeMenu.addActionListener((ActionEvent ae) -> {
            setVisible(false);
        });

        JMenu fileMenu = new JMenu(Localization.getLocalizedString("LogConsoleFile"));
        fileMenu.add(pauseMenu);
        fileMenu.add(scrollLockMenu);
        fileMenu.add(closeMenu);

        mainMenu.add(fileMenu);

        this.setTitle(Localization.getLocalizedString("LogConsoleTitle"));
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
