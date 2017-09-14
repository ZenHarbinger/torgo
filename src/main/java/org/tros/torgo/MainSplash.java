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
package org.tros.torgo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author matta
 */
public final class MainSplash {

    static double numSteps = 1;
    static double currStep = 0;

    private static SplashScreen mySplash;
    private static Graphics2D splashGraphics;
    private static Image splashImage;
    private static Font font;
    private static Dimension splashDimension;
    private static double currPercent = 0;

    /**
     * Hidden constructor.
     */
    private MainSplash() {
    }

    /**
     * Initialize the splash graphic.
     */
    protected static void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
            try {
                // if there are any problems displaying the splash this will be null
                splashDimension = mySplash.getSize();
                splashImage = ImageIO.read(mySplash.getImageURL());

                // create the Graphics environment for drawing status info
                splashGraphics = mySplash.createGraphics();
                font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
                splashGraphics.setFont(font);
                splashGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

                // initialize the status info
                splashText("Starting");
            } catch (IOException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(MainSplash.class).warn(null, ex);
            }
        }
    }

    /**
     * Display text in status area of Splash. Note: no validation it will fit.
     *
     * @param str text to be displayed
     */
    public static void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {
            splashGraphics.drawImage(splashImage, 0, 0, null);

            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, 27, (int) (splashDimension.getHeight() - 12));
            splashProgress(currPercent);

            mySplash.update();
        }
    }

    /**
     * Set the number of steps to be completed.
     *
     * This number can be updated.
     *
     * @param steps the number of steps to fill in.
     */
    public static void setNumSteps(int steps) {
        if (steps > 0) {
            numSteps = steps;
        }
    }

    /**
     * Perform a step.
     */
    public static void splashStep() {
        currStep += 1.0;
        currStep = Math.min(currStep, numSteps);
        splashProgress(currStep / numSteps);
    }

    /**
     * Add a specified number of steps to the completed.
     *
     * @param steps the number of steps to add.
     */
    public static void addSteps(int steps) {
        if (steps > 0) {
            numSteps += steps;
        }
    }

    /**
     * Update splash progress.
     *
     * @param percent the percent to fill in.
     */
    private static void splashProgress(double percent) {
        if (mySplash != null && mySplash.isVisible()) {
            currPercent = percent;
            splashGraphics.setColor(Color.RED);
            splashGraphics.fillRect(0, splashDimension.height - 5, (int) (splashDimension.getWidth() * percent), 5);
            mySplash.update();
        }
    }
}
