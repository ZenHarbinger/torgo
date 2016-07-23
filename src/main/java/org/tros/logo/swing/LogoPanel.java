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
package org.tros.logo.swing;

import org.tros.torgo.swing.BufferedImageProvider;
import org.tros.logo.LogoCanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.event.EventListenerSupport;
import org.tros.torgo.TorgoScreen;

import org.tros.torgo.TorgoTextConsole;
import org.tros.torgo.swing.DrawListener;
import org.tros.torgo.swing.Drawable;

public class LogoPanel extends JPanel implements TorgoScreen, LogoCanvas, BufferedImageProvider, Drawable {

    private Color penColor;
    private Font font;
    private boolean penup;
    private boolean showTurtle;
    private double angle;
    private double penX;
    private double penY;
    private final TorgoTextConsole console;
    private BufferedImage turtle;

    private final ArrayList<Drawable> queuedCommands = new ArrayList<>();
    private final ArrayList<Drawable> commands = new ArrayList<>();
    protected final EventListenerSupport<DrawListener> listeners
            = EventListenerSupport.create(DrawListener.class);

    /**
     * Constructor.
     *
     * @param textOutput
     */
    public LogoPanel(TorgoTextConsole textOutput) {
        console = textOutput;
        penup = false;
        showTurtle = true;
        URL resource = ClassLoader.getSystemClassLoader().getResource("turtle.png");
        try {
            if (resource != null) {
                turtle = ImageIO.read(resource);
            }
        } catch (IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoPanel.class).fatal(null, ex);
        }
    }

    @Override
    public void addListener(DrawListener listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeListener(DrawListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Paint.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
//        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2 = (Graphics2D) buffer.createGraphics();

        draw(g2d);

//        g2d.drawImage(buffer, 0, 0, null);
        if (showTurtle && turtle != null) {
            double x = penX - (turtle.getWidth() / 2.0);
            double y = penY - (turtle.getHeight() / 2.0);
            AffineTransform translateInstance = AffineTransform.getRotateInstance(angle + (Math.PI / 2.0), penX, penY);
            AffineTransform saveXform = g2d.getTransform();
            g2d.transform(translateInstance);
            g2d.drawImage(turtle, (int) x, (int) y, null);
            g2d.setTransform(saveXform);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (g2d == null) {
            return;
        }

        //since this list can be written to, do not swith to for-each
        for (int ii = 0; ii < queuedCommands.size(); ii++) {
            queuedCommands.get(ii).draw(g2d);
            listeners.fire().drawn(this);
        }
    }

    @Override
    public Drawable cloneDrawable() {
        Drawable d = new Drawable() {

            private final ArrayList<Drawable> queuedCommandsCopy = new ArrayList<>(queuedCommands);
            protected final EventListenerSupport<DrawListener> listenersCopy
                    = EventListenerSupport.create(DrawListener.class);

            @Override
            public void draw(Graphics2D g2d) {
                //since this list can be written to, do not swith to for-each
                for (int ii = 0; ii < queuedCommandsCopy.size(); ii++) {
                    queuedCommandsCopy.get(ii).draw(g2d);
                    listenersCopy.fire().drawn(this);
                }
            }

            @Override
            public void addListener(DrawListener listener) {
                listenersCopy.addListener(listener);
            }

            @Override
            public void removeListener(DrawListener listener) {
                listenersCopy.removeListener(listener);
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        return d;
    }

    @Override
    public void pause(final int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoPanel.class).fatal(null, ex);
        }
    }

    @Override
    public void forward(final double distance) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                double newx = penX + (distance * Math.cos(angle));
                double newy = penY + (distance * Math.sin(angle));

                if (!penup) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.draw(new Line2D.Double(penX, penY, newx, newy));
                }

                penX = newx;
                penY = newy;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    private void submitCommand(Drawable command) {
        queuedCommands.add(command);
    }

    @Override
    public void backward(final double distance) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                double newx = penX - (distance * Math.cos(angle));
                double newy = penY - (distance * Math.sin(angle));

                if (!penup) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.draw(new Line2D.Double(penX, penY, newx, newy));
                }

                penX = newx;
                penY = newy;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void left(final double angle) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                LogoPanel.this.angle -= Math.PI * angle / 180.0;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void right(final double angle) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                LogoPanel.this.angle += Math.PI * angle / 180.0;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void setXY(final double x, final double y) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                double x2 = (getWidth()) / 2.0 + x;
                double y2 = (getHeight()) / 2.0 + y;

                if (!penup) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.draw(new Line2D.Double(penX, penY, x2, y2));
                }
                penX = x2;
                penY = y2;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void penUp() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                penup = true;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void penDown() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                penup = false;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void clear() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g) {
                try {
                    g.setColor(Color.white);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    penColor = Color.black;
                    g.setColor(penColor);

                    font = new Font(null, 0, 12);

                    g.setFont(font);
                } catch (Exception ex) {

                }
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void home() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                penX = getWidth() / 2.0;
                penY = getHeight() / 2.0;
                angle = -1.0 * (Math.PI / 2.0);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void canvascolor(final String color) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                Color canvasColor = getColorByName(color);

                g2.setColor(canvasColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(penColor);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    private void canvascolor(final Color color) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                g2.setColor(color);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(penColor);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    private void pencolor(final Color color) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                penColor = color;
                g2.setColor(penColor);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void pencolor(final String color) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                penColor = getColorByName(color);
                g2.setColor(penColor);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    private Color getColorByName(String color) {
        color = color.toLowerCase();

        try {
            Field field = Color.class
                    .getField(color);
            return (Color) field.get(
                    null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
        }
        if (null != color) {
            switch (color) {
                case "darkgray":
                    return (Color.darkGray);
                case "lightgray":
                    return (Color.lightGray);
                default:
                    if (!color.startsWith("#") || !color.startsWith(color)) {
                        color = "#" + color;
                    }
                    Color c = java.awt.Color.decode(color);
                    return c == null ? Color.black : c;
            }
        }
        return Color.black;
    }

    @Override
    public void drawString(final String message) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                if (!penup) {
                    AffineTransform saveXform = g2.getTransform();
                    //double offsetAngle = (Math.PI / 2.0);
                    double offsetAngle = 0;
                    g2.setTransform(AffineTransform.getRotateInstance(angle + offsetAngle, penX, penY));
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                    g2.drawString(message, (int) penX, (int) penY);
                    g2.setTransform(saveXform);
                }
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void fontSize(final int size) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                String fontName = font.getFontName();
                int style = font.getStyle();

                font = new Font(fontName, style, size);

                g2.setFont(font);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void fontName(final String fontFace) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                int style = font.getStyle();
                int size = font.getSize();

                font = new Font(fontFace, style, size);

                g2.setFont(font);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void fontStyle(final int style) {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                String fontName = font.getFontName();
                int size = font.getSize();

                font = new Font(fontName, style, size);

                g2.setFont(font);
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public BufferedImage getBufferedImage() {
        BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) buffer.createGraphics();
        draw(g2d);
        return buffer;
    }

    @Override
    public void hideTurtle() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                showTurtle = false;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void showTurtle() {
        Drawable command = new Drawable() {

            @Override
            public void draw(Graphics2D g2) {
                showTurtle = true;
            }

            @Override
            public void addListener(DrawListener listener) {
            }

            @Override
            public void removeListener(DrawListener listener) {
            }

            @Override
            public Drawable cloneDrawable() {
                return this;
            }
        };
        submitCommand(command);
    }

    @Override
    public void message(String message) {
        message = message.trim();
        console.appendToOutputTextArea(message + System.getProperty("line.separator"));
    }

    @Override
    public void warning(String message) {
        message = message.trim();
        console.appendToOutputTextArea(">> " + message + System.getProperty("line.separator"));
    }

    @Override
    public double getTurtleX() {
        return penX;
    }

    @Override
    public double getTurtleY() {
        return penX;
    }

    @Override
    public double getTurtleAngle() {
        return angle;
    }

    @Override
    final public void reset() {
        penup = false;
        showTurtle = true;
        queuedCommands.clear();
        commands.clear();
        clear();
        home();
        repaint();
    }

    @Override
    public void repaint() {
        if (SwingUtilities.isEventDispatchThread()) {
            LogoPanel.super.repaint();
        } else {
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(LogoMenuBar.class);
            if (prefs.getBoolean(LogoMenuBar.WAIT_FOR_REPAINT, true)) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        @Override
                        public void run() {
                            LogoPanel.super.repaint();
                        }
                    });
                } catch (InterruptedException | InvocationTargetException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(LogoPanel.class).fatal(null, ex);
                }
            } else {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        LogoPanel.super.repaint();
                    }
                });
            }
        }
    }

    @Override
    public void pencolor(int red, int green, int blue, int alpha) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        Color canvasColor = new Color(red, green, blue, alpha);
        pencolor(canvasColor);
    }

    @Override
    public void canvascolor(int red, int green, int blue) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        Color canvasColor = new Color(red, green, blue);
        canvascolor(canvasColor);
    }

    @Override
    public Component getComponent() {
        return this;
    }
}
