/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by matta on 6/16/15.
 */
public interface ResourceAccessor {

    InputStream open(String name) throws IOException;

    String getName();

}
