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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import org.apache.commons.logging.LogFactory;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.tros.torgo.TorgoScreen;

import org.tros.torgo.TorgoTextConsole;

public class LogoPanel extends JPanel implements TorgoScreen, LogoCanvas, BufferedImageProvider {

    private BufferedImage buffer;
    private Graphics2D g2;

    private Color penColor;
    private Font font;
    private boolean penup;
    private boolean showTurtle;
    private double angle;
    private double penX;
    private double penY;
    private final TorgoTextConsole console;
    private BufferedImage turtle;

    /**
     * Create a copy of the buffered image object.
     *
     * @param bi
     * @return
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

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
            LogFactory.getLog(LogoPanel.class).fatal(null, ex);
        }
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                if (buffer != null) {
                    BufferedImage bi = LogoPanel.deepCopy(buffer);
                    clear();
                    g2.drawImage(bi, 0, 0, null);
                }
                repaint();
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
        });
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

        g2d.drawImage(buffer, 0, 0, null);

        if (g2 != null && showTurtle && turtle != null) {
            double x = penX - (turtle.getWidth() / 2.0);
            double y = penY - (turtle.getHeight() / 2.0);
            AffineTransform translateInstance = AffineTransform.getRotateInstance(angle + (Math.PI / 2.0), penX, penY);
            AffineTransform saveXform = g2.getTransform();
            g2d.transform(translateInstance);
            g2d.drawImage(turtle, (int) x, (int) y, null);
            g2d.setTransform(saveXform);
        }
    }

    @Override
    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            LogFactory.getLog(LogoPanel.class).fatal(null, ex);
        }
    }

    @Override
    public void forward(double distance) {
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
    public void backward(double distance) {
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
    public void left(double angle) {
        this.angle -= Math.PI * angle / 180.0;
    }

    @Override
    public void right(double angle) {
        this.angle += Math.PI * angle / 180.0;
    }

    @Override
    public void setXY(double x, double y) {
        x = (getWidth()) / 2.0 + x;
        y = (getHeight()) / 2.0 + y;

        if (!penup) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.draw(new Line2D.Double(penX, penY, x, y));
        }
        penX = x;
        penY = y;
    }

    @Override
    public void penUp() {
        this.penup = true;
    }

    @Override
    public void penDown() {
        this.penup = false;
    }

    @Override
    public void clear() {
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) buffer.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());
        penColor = Color.black;
        g2.setColor(penColor);

        font = new Font(null, 0, 12);

        g2.setFont(font);
    }

    @Override
    public void home() {
        penX = getWidth() / 2.0;
        penY = getHeight() / 2.0;
        angle = -1.0 * (Math.PI / 2.0);
    }

    @Override
    public void canvascolor(String color) {
        Color canvasColor = getColorByName(color);

        g2.setColor(canvasColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(penColor);
    }

    private void canvascolor(Color color) {
        g2.setColor(color);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(penColor);
    }

    private void pencolor(Color color) {
        penColor = color;
        g2.setColor(penColor);
    }

    @Override
    public void pencolor(String color) {
        penColor = getColorByName(color);
        g2.setColor(penColor);
    }

    private Color getColorByName(String color) {
        color = color.toLowerCase();
        try {
            Field field = Color.class.getField(color);
            return (Color) field.get(null);
        } catch (NoSuchFieldException ex) {
        } catch (SecurityException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (IllegalAccessException ex) {
        }
        if ("darkgray".equals(color)) {
            return (Color.darkGray);
        } else if ("lightgray".equals(color)) {
            return (Color.lightGray);
        } else {
            try {
                if (!color.startsWith("#") || !color.startsWith(color)) {
                    color = "#" + color;
                }
                Color c = java.awt.Color.decode(color);
                return c == null ? Color.black : c;
            } catch (Exception ex) {
                return Color.black;
            }
        }
    }

    @Override
    public void drawString(String message) {
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
    public void fontSize(int size) {
        String fontName = font.getFontName();
        int style = font.getStyle();

        font = new Font(fontName, style, size);

        g2.setFont(font);
    }

    @Override
    public void fontName(String fontFace) {
        int style = font.getStyle();
        int size = font.getSize();

        font = new Font(fontFace, style, size);

        g2.setFont(font);
    }

    @Override
    public void fontStyle(int style) {
        String fontName = font.getFontName();
        int size = font.getSize();

        font = new Font(fontName, style, size);

        g2.setFont(font);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return buffer;
    }

    @Override
    public void hideTurtle() {
        showTurtle = false;
    }

    @Override
    public void showTurtle() {
        showTurtle = true;
    }

    @Override
    public void message(String message) {
        console.appendToOutputTextArea(message + System.getProperty("line.separator"));
    }

    @Override
    public void warning(String message) {
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
    public void reset() {
        clear();
        home();
        repaint();
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
