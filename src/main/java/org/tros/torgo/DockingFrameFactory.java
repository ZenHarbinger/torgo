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

import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.mode.ExtendedMode;
import bibliothek.util.xml.XElement;
import bibliothek.util.xml.XIO;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.tros.utils.PathUtils;

/**
 *
 * @author matta
 */
public final class DockingFrameFactory {

    private DockingFrameFactory() {
    }

    /**
     * This method simulates the creation of a layout.
     *
     * @param display the component to display
     * @param input array list
     * @return element
     */
    public static XElement createLayout(Component display, ArrayList<ImmutablePair<String, Component>> input) {
        /* This method simulates the creation of a layout */
        CControl control = new CControl();
        control.getContentArea();

        CGrid grid = new CGrid(control);

        DefaultSingleCDockable displayDock = display != null ? new ControllerBase.TorgoSingleDockable("Display", display) : null;
        if (displayDock != null) {
            grid.add(0, 0, 10, 10, displayDock);
            displayDock.setLocation(CLocation.base().minimalWest());
            displayDock.setExtendedMode(ExtendedMode.NORMALIZED);
        }

        int count = 1;
        for (ImmutablePair<String, Component> key : input) {
            DefaultSingleCDockable dock = new ControllerBase.TorgoSingleDockable(key.left, key.right);
            grid.add(10, 0, 6, count, dock);
            dock.setExtendedMode(ExtendedMode.NORMALIZED);
            count += 1;
        }

        control.getContentArea().deploy(grid);

        XElement root = new XElement("root");
        control.writeXML(root);
        control.destroy();
        return root;
    }

    /**
     * Try to read in the layout config from various locations.
     *
     * @param lang interpreter language
     * @return element
     */
    public static XElement read(String lang) {
        //read from saved file:
        String layoutFileName = PathUtils.getApplicationConfigDirectory(TorgoToolkit.getBuildInfo()) + java.io.File.separatorChar + lang + "-layout.xml";
        File layoutFile = new File(layoutFileName);
        if (layoutFile.exists()) {
            try {
                XElement elem = XIO.readUTF(new FileInputStream(layoutFile));
                return elem;
            } catch (FileNotFoundException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).warn(null, ex);
            } catch (IOException ex) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).warn(null, ex);
            }
        }

        //read from class resource:
        try {
            java.util.Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("layouts/" + lang + "-layout.xml");
            XElement elem = XIO.readUTF(resources.nextElement().openStream());
            return elem;
        } catch (IOException | java.util.NoSuchElementException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(ControllerBase.class).warn("Layout Error: Auto-generating: {0}", ex.getMessage());
        }

        //no apparent layout:
        return null;
    }
}
