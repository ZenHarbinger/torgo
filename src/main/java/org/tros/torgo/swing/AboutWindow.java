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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.tros.torgo.Main;
import org.tros.torgo.TorgoInfo;
import org.tros.utils.BuildInfo;
import org.tros.utils.ImageUtils;
import org.tros.utils.swing.JLinkButton;

/**
 * Dialog window displaying relevant 'about' information.
 *
 * @author matta
 */
public class AboutWindow extends JDialog {

    /**
     * TORGO web address.
     */
    public static final String TORGO_ADDRESS = "http://tros.org/torgo/";

    /**
     * License Address.
     */
    public static final String APACHE_LICENSE_ADDRESS = "http://www.apache.org/licenses/LICENSE-2.0";

    /**
     * Constructor.
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AboutWindow() {
        //no resize.
        this.setResizable(false);

        //create container.
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        //create copyright/apache link button
        JLinkButton apacheButton = new JLinkButton();
        apacheButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    URI uri = new URI(APACHE_LICENSE_ADDRESS);
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(AboutWindow.class).warn(null, ex);
                }
            }
        });
        //create torgo/source link button
        JLinkButton torgoButton = new JLinkButton();
        torgoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    URI uri = new URI(TORGO_ADDRESS);
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException ex) {
                } catch (IOException ex) {
                    org.tros.utils.logging.Logging.getLogFactory().getLogger(AboutWindow.class).warn(null, ex);
                }
            }
        });

        //Set window properties.
        this.setTitle("About Torgo");
        this.setSize(670, 225);//Size of JFrame

        //load the window icon
        Main.loadIcon((Window) this);

        //load the icon for display
        JPanel image_panel = new JPanel();
        ImageIcon icon = ImageUtils.createImageIcon("torgo-192x192.png", "Torgo Icon");
        icon.setImage(ImageUtils.makeColorTransparent(ImageUtils.imageToBufferedImage(icon.getImage()), java.awt.Color.WHITE));
        image_panel.add(new JLabel(icon));
        container.add(image_panel, BorderLayout.LINE_START);

        //init the text portion of the window.
        JPanel text_panel = new JPanel();
        text_panel.setLayout(new GridLayout(6, 1));

        BuildInfo toroInfo = TorgoInfo.INSTANCE;
        torgoButton.setText(String.format("Torgo %s", toroInfo.getVersion()));
        torgoButton.setBorderPainted(false);
        torgoButton.setOpaque(false);
        torgoButton.setToolTipText(TORGO_ADDRESS);
        text_panel.add(torgoButton);

        JLabel l = new JLabel();
        l.setText(String.format("Platform: Java (%s) %s", System.getProperty("java.runtime.name"), System.getProperty("java.version")));//, System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")));
        l.setToolTipText(l.getText());
        text_panel.add(l);

        l = new JLabel();
        l.setText(String.format("OS: %s %s %s", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")));
        l.setToolTipText(l.getText());
        text_panel.add(l);

        apacheButton.setText("© 2015-2016 Matthew Aguirre, Apache License 2.0.");
        apacheButton.setBorderPainted(false);
        apacheButton.setOpaque(false);
        apacheButton.setToolTipText(APACHE_LICENSE_ADDRESS);
        text_panel.add(apacheButton);

        JTextArea ta = new JTextArea();
        ta.setText("Torgo is a flexible interpreter written in Java.");
        ta.setEditable(false);
        text_panel.add(ta);

        container.add(text_panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setModal(true);
    }
}
