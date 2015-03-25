/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tros.torgo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.tros.torgo.Main;
import org.tros.torgo.TorgoInfo;
import org.tros.utils.IBuildInfo;
import org.tros.utils.swing.ImageLoader;
import org.tros.utils.swing.JLinkButton;

/**
 *
 * @author matta
 */
public class AboutWindow extends JDialog {

    public static final String TORGO_ADDRESS = "https://github.com/ZenHarbinger/torgo";
    public static final String APACHE_LICENSE_ADDRESS = "http://www.apache.org/licenses/LICENSE-2.0";

    @SuppressWarnings("LeakingThisInConstructor")
    public AboutWindow() {
        super();
        this.setResizable(false);

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JLinkButton apacheButton = new JLinkButton();
        apacheButton.addActionListener((ActionEvent ae) -> {
            try {
                URI uri = new URI(APACHE_LICENSE_ADDRESS);
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(AboutWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JLinkButton torgoButton = new JLinkButton();
        torgoButton.addActionListener((ActionEvent ae) -> {
            try {
                URI uri = new URI(TORGO_ADDRESS);
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(AboutWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.setTitle("About Torgo");
        int height = 225;
        int width = 670;//(int) (height * 1.61803398875);
        this.setSize(width, height);//Size of JFrame
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        Main.loadIcon(this);

        JPanel image_panel = new JPanel();
        ImageIcon icon = ImageLoader.createImageIcon("torgo-192x192.png", "Torgo Icon");

        BufferedImage bi = imageToBufferedImage(icon.getImage());
        Image img = makeColorTransparent(bi, java.awt.Color.WHITE);
        icon.setImage(img);

        JLabel i = new JLabel(icon);
        image_panel.add(i);
        container.add(image_panel, BorderLayout.LINE_START);

        JPanel text_panel = new JPanel();
        text_panel.setLayout(new GridLayout(6, 1));

        IBuildInfo toroInfo = TorgoInfo.Instance;
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

        apacheButton.setText("© 2015 Matthew Aguirre, Apache License 2.0.");
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

    private static BufferedImage imageToBufferedImage(Image image) {

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return bufferedImage;

    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
