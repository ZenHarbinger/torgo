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

import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import javax.swing.JDialog;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class AboutWindowTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(AboutWindowTest.class.getName());
    }

    public AboutWindowTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        LOGGER.info("aboutWindow");
        final AboutWindow aw = new AboutWindow();
        aw.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                aw.dispatchEvent(new WindowEvent(aw, WindowEvent.WINDOW_CLOSING));
            }
        }, 500);

        aw.setVisible(true);
    }

    @Test
    public void test() {
        LOGGER.info("goToURI");
        AboutWindow.goToURI(AboutWindow.APACHE_LICENSE_ADDRESS);
        AboutWindow.goToURI("not a valid uri");
    }

}
