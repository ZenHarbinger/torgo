/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
*/
package org.tros.utils.swing;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author matta
 */
public final class ImageLoader {

    private ImageLoader() {

    }

    public static final ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = ImageLoader.class.getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.WARNING, "Couldn''t find file: {0}", path);
            return null;
        }
    }

    public static final Image createImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(path);
    }
}
