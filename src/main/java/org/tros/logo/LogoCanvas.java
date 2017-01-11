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
package org.tros.logo;

/**
 * Canvas/Drawing Interface
 *
 * @author matta
 */
public interface LogoCanvas {

    void backward(double distance);

    void canvascolor(int red, int green, int blue);

    void canvascolor(String color);

    void clear();

    void drawString(String message);

    void fontName(String fontFace);

    void fontSize(int size);

    void fontStyle(int style);

    void forward(double distance);

    void hideTurtle();

    void home();

    void left(double angle);

    void pause(int time);

    void penDown();

    void penUp();

    void pencolor(int red, int green, int blue, int alpha);

    void pencolor(String color);

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
