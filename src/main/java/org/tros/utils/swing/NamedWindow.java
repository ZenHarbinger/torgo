/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

/**
 * This is a JFrame that will remember position, hight, and width.
 *
 * @author matta
 */
public class NamedWindow extends JFrame {

    public static final int DEFAULT_WIDTH = 1152;
    public static final int DEFAULT_HEIGHT = 864;
    private boolean checkTesting = false;
    private boolean testing = false;
    private NamedWindow window;

    public NamedWindow(final String name) {
        this(name, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor.
     *
     * @param name the name of the window.
     * @param width the width of the window.
     * @param height the height of the window.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NamedWindow(final String name, int width, int height) {
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(this.getClass());

        width = prefs.getInt(name + "-window-width", width);
        height = prefs.getInt(name + "-window-height", height);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        if (width > screenSize.getWidth()) {
            width = (int) screenSize.getWidth();
        }
        if (height > screenSize.getHeight()) {
            height = (int) screenSize.getHeight();
        }

        int x = (int) ((screenSize.getWidth() - width) / 2);
        int y = (int) ((screenSize.getHeight() - height) / 2);

        x = prefs.getInt(name + "-window-x", x);
        y = prefs.getInt(name + "-window-y", y);

        setSize(width, height);
        setLocation(x, y);
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                prefs.putInt(name + "-window-x", getX());
                prefs.putInt(name + "-window-y", getY());
                prefs.putInt(name + "-window-width", getWidth());
                prefs.putInt(name + "-window-height", getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                prefs.putInt(name + "-window-x", getX());
                prefs.putInt(name + "-window-y", getY());
                prefs.putInt(name + "-window-width", getWidth());
                prefs.putInt(name + "-window-height", getHeight());
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void testOverrideMethods() {
        if (testing) {
            window = new NamedWindow("Yes", 100, 100);
            ComponentEvent event = new ComponentEvent(window, 10);
            CompListener listener = new CompListener();
            listener.componentShown(event);
            listener.componentMoved(event);
            listener.componentResized(event);
            listener.componentHidden(event);
            testing = false;
            checkTesting = true;
        }
    }

    public void setTesting() {
        testing = true;
        checkTesting = false;
    }

    public boolean getTestCheck() {
        return checkTesting;
    }

    public boolean getTest() {
        return testing;
    }

    private static class CompListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
        }

        @Override
        public void componentMoved(ComponentEvent e) {
        }

        @Override
        public void componentShown(ComponentEvent e) {
        }

        @Override
        public void componentHidden(ComponentEvent e) {
        }
    }
}
