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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.tros.torgo.Main;
import org.tros.torgo.TorgoToolkit;
import org.tros.torgo.UpdateChecker;
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
     * Release address.
     */
    public static final String RELEASE_ADDRESS = UpdateChecker.UPDATE_ADDRESS;

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
        apacheButton.addActionListener((ActionEvent ae) -> {
            goToURI(APACHE_LICENSE_ADDRESS);
        });
        //create torgo/source link button
        JLinkButton torgoButton = new JLinkButton();
        torgoButton.addActionListener((ActionEvent ae) -> {
            goToURI(TORGO_ADDRESS);
        });

        //Set window properties.
        this.setTitle(Localization.getLocalizedString("HelpAbout"));
        this.setSize(670, 225);//Size of JFrame

        //load the window icon
        Main.loadIcon((Window) this);

        //load the icon for display
        JPanel imagePanel = new JPanel();
        ImageIcon icon = ImageUtils.createImageIcon("torgo-192x192.png", "Torgo Icon");
        icon.setImage(ImageUtils.makeColorTransparent(ImageUtils.imageToBufferedImage(icon.getImage()), java.awt.Color.WHITE));
        imagePanel.add(new JLabel(icon));
        container.add(imagePanel, BorderLayout.LINE_START);

        //init the text portion of the window.
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7, 1));

        BuildInfo toroInfo = TorgoToolkit.getBuildInfo();
        torgoButton.setText(String.format("Torgo %s", toroInfo.getVersion()));
        torgoButton.setBorderPainted(false);
        torgoButton.setOpaque(false);
        torgoButton.setToolTipText(TORGO_ADDRESS);
        textPanel.add(torgoButton);

        JLabel l = new JLabel();
        l.setText(String.format("%s: Java (%s) %s", Localization.getLocalizedString("AboutPlatform"), System.getProperty("java.runtime.name"), System.getProperty("java.version")));//, System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")));
        l.setToolTipText(l.getText());
        textPanel.add(l);

        l = new JLabel();
        l.setText(String.format("OS: %s %s %s", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")));
        l.setToolTipText(l.getText());
        textPanel.add(l);

        apacheButton.setText(TorgoToolkit.getBuildInfo().getCopy());
        apacheButton.setBorderPainted(false);
        apacheButton.setOpaque(false);
        apacheButton.setToolTipText(APACHE_LICENSE_ADDRESS);
        textPanel.add(apacheButton);

        JTextArea ta = new JTextArea();
        ta.setText(TorgoToolkit.getBuildInfo().getAbout());
        ta.setEditable(false);
        textPanel.add(ta);

        //add update alert area
        JPanel updateArea = new JPanel();
        updateArea.setLayout(new GridLayout(1, 2));
        final UpdateChecker uc = new UpdateChecker();
        final JCheckBox checkForUpdate = new JCheckBox(Localization.getLocalizedString("HelpCheckForUpdate"));
        final JLinkButton button = new JLinkButton("");
        checkForUpdate.setSelected(uc.getCheckForUpdate());
        checkForUpdate.addActionListener((ActionEvent e) -> {
            uc.setCheckForUpdate(checkForUpdate.isSelected());
            boolean enabled1 = uc.getCheckForUpdate() && uc.hasUpdate();
            button.setEnabled(enabled1);
            if (button.isEnabled()) {
                button.setText(Localization.getLocalizedString("AboutUpdateAvailable"));
            }
        });
        updateArea.add(checkForUpdate);

        final Thread t = new Thread(() -> {
            final boolean enabled1 = uc.getCheckForUpdate() && uc.hasUpdate();
            SwingUtilities.invokeLater(() -> {
                button.setEnabled(enabled1);
                if (button.isEnabled()) {
                    button.setText(Localization.getLocalizedString("AboutUpdateAvailable"));
                }
            } /**
             * Once the value has been received from on-line, update the UI.
             */
            );
        } /**
         * Put the check in a new thread to avoid blocking UI.
         */
        );
        t.setDaemon(true);

        /**
         * Avoids the "invoking thread in constructor" warning.
         */
        this.addWindowListener(new WindowListener() {
            /**
             * Start the update check thread when the window is opened.
             *
             * @param e
             */
            @Override
            public void windowOpened(WindowEvent e) {
                t.start();
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        updateArea.add(button);
        button.addActionListener((ActionEvent e) -> {
            goToURI(UpdateChecker.UPDATE_ADDRESS);
        });

        textPanel.add(updateArea);

        container.add(textPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setModal(true);
    }

    public static final void goToURI(String address) {
        try {
            URI uri = new URI(address);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(AboutWindow.class).warn(null, ex);
        } catch (UnsupportedOperationException ex) {
            ProcessBuilder pb = new ProcessBuilder("xdg-open", address);
            try {
                pb.start();
            } catch (IOException ex1) {
                org.tros.utils.logging.Logging.getLogFactory().getLogger(AboutWindow.class).warn(null, ex1);
            }
        }
    }
}
