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
package org.tros.torgo;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Canvas/Drawing Interface
 * @author matta
 */
public interface TorgoCanvas {

    void backward(double distance);

    default void canvascolor(int red, int green, int blue) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        Color canvasColor = new Color(red, green, blue);
        canvascolor(canvasColor);
    }

    void canvascolor(String color);

    void canvascolor(Color color);

    void clear();

    void drawString(String message);

    void fontName(String fontFace);

    void fontSize(int size);

    void fontStyle(int style);

    void forward(double distance);

    BufferedImage getImage();

    void hideTurtle();

    void home();

    void left(double angle);

    void pause(int time);
    
    void penDown();

    void penUp();

    default void pencolor(int red, int green, int blue, int alpha) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        Color canvasColor = new Color(red, green, blue, alpha);
        pencolor(canvasColor);
    }

    void pencolor(String color);

    void pencolor(Color color);

    void repaint();
    
    void message(String message);

    void warning(String message);
    
    void right(double angle);

    void setXY(double x, double y);

    void showTurtle();
    
    double getTurtleX();
    
    double getTurtleY();
    
    double getTurtleAngle();
}
