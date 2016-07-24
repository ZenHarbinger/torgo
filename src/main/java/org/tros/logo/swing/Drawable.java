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
package org.tros.logo.swing;

import java.awt.Graphics2D;
import org.tros.logo.swing.LogoPanel.TurtleState;

/**
 *
 * @author matta
 */
public interface Drawable {

    void draw(Graphics2D g, TurtleState turtleState);

    void addListener(DrawListener listener);

    void removeListener(DrawListener listener);
    
    public Drawable cloneDrawable();
}
