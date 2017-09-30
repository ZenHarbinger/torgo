/*
 * Copyright 2015-2017 Matthew Aguirre
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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import org.tros.utils.logging.Logger;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public abstract class ZoomableComponent {

    private static final Logger LOGGER = Logging.getLogFactory().getLogger(ZoomableComponent.class);

    public ZoomableComponent(JComponent component) {
        this(component, component);
    }

    public ZoomableComponent(JComponent component, JComponent popupMenuContainer) {
        Runnable increase = () -> {
            LOGGER.verbose("increase");
            zoomIn();
        };

        Runnable decrease = () -> {
            LOGGER.verbose("decrease");
            zoomOut();
        };

        Runnable reset = () -> {
            LOGGER.verbose("reset");
            zoomReset();
        };

        component.addMouseWheelListener((MouseWheelEvent mwe) -> {
            if (mwe.isControlDown()) {
                if (mwe.getPreciseWheelRotation() < 0) {
                    increase.run();
                } else {
                    decrease.run();
                }
            }
        });
        JMenuItem jmi1 = new JMenuItem("Zoom In");
        jmi1.addActionListener((ActionEvent ae) -> {
            increase.run();
        });
        JMenuItem jmi2 = new JMenuItem("Zoom Out");
        jmi2.addActionListener((ActionEvent ae) -> {
            decrease.run();
        });
        JMenuItem jmi3 = new JMenuItem("Zoom Reset");
        jmi3.addActionListener((ActionEvent ae) -> {
            reset.run();
        });
        jmi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
        jmi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        jmi3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
        final AtomicBoolean added = new AtomicBoolean(false);
        final Runnable r = () -> {

            if (!added.get()) {
                added.set(true);
                JPopupMenu popupMenu;
                try {
                    //HACK: for RSyntaxTextArea
                    Method method = popupMenuContainer.getClass().getMethod("getPopupMenu");
                    popupMenu = (JPopupMenu) method.invoke(popupMenuContainer);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    popupMenu = popupMenuContainer.getComponentPopupMenu();
                }
                popupMenu = popupMenu == null ? new JPopupMenu() : popupMenu;
                popupMenu.add(jmi1);
                popupMenu.add(jmi2);
                popupMenu.add(jmi3);
                popupMenuContainer.setComponentPopupMenu(popupMenu);
            }
        };
        popupMenuContainer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                r.run();
                component.removeMouseListener(this);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                r.run();
                component.removeMouseListener(this);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                r.run();
                component.removeMouseListener(this);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                r.run();
                component.removeMouseListener(this);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                r.run();
                component.removeMouseListener(this);
            }
        });
    }

    protected abstract void zoomIn();

    protected abstract void zoomOut();

    protected abstract void zoomReset();
}
