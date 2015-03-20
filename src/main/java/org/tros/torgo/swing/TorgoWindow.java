/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tros.torgo.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import org.tros.torgo.Controller;
import org.tros.torgo.Main;

public class TorgoWindow extends JFrame {

    public TorgoWindow(Controller controller) {
        final java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(TorgoWindow.class);

        int width = prefs.getInt(TorgoWindow.class.getName() + "-window-width", 640);
        int height = prefs.getInt(TorgoWindow.class.getName() + "-window-height", 480);

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

        x = prefs.getInt(TorgoWindow.class.getName() + "-window-x", x);
        y = prefs.getInt(TorgoWindow.class.getName() + "-window-y", y);

        setSize(width, height);
        setLocation(x, y);
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                prefs.putInt(TorgoWindow.class.getName() + "-window-x", getX());
                prefs.putInt(TorgoWindow.class.getName() + "-window-y", getY());
                prefs.putInt(TorgoWindow.class.getName() + "-window-width", getWidth());
                prefs.putInt(TorgoWindow.class.getName() + "-window-height", getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                prefs.putInt(TorgoWindow.class.getName() + "-window-x", getX());
                prefs.putInt(TorgoWindow.class.getName() + "-window-y", getY());
                prefs.putInt(TorgoWindow.class.getName() + "-window-width", getWidth());
                prefs.putInt(TorgoWindow.class.getName() + "-window-height", getHeight());
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main.loadIcon((JFrame) this);
    }
}
