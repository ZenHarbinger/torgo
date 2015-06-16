/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils.swing;

import java.io.IOException;
import java.io.InputStream;
import org.tros.utils.ResourceAccessor;

/**
 *
 * @author matta
 */
public class JavaResourceAccessor implements ResourceAccessor {

    @Override
    public InputStream open(String name) throws IOException {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }

    @Override
    public String getName() {
        return "java/swing";
    }

}
