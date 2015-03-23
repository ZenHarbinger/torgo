/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
*/
package org.tros.utils;

import java.util.ArrayList;

/**
 * Can be used to monitor when a thread is halted/exits
 * @author matta
 */
public final class HaltMonitor implements IHaltMonitor {

    private boolean _halted;
    private final ArrayList<IHaltListener> _listeners;
    private String _name;

    /**
     * Initialize a new monitor.
     */
    public HaltMonitor() {
        _halted = false;
        _listeners = new ArrayList<>();
        _name = "";
    }

    /**
     * Get the name of the monitor.
     * @return the name of the monitor.
     */
    @Override
    public String getName() {
        return _name;
    }

    /**
     * Set the name of the monitor.
     * @param value the name of the monitor.
     */
    public void setName(final String value) {
        _name = value;
    }

    /**
     * Add a new listener.
     * @param listener The listener to add.
     */
    public synchronized void addListener(final IHaltListener listener) {
        if (!_halted && !_listeners.contains(listener)) {
            _listeners.add(listener);
        }
    }

    /**
     * Remove a listener.
     * @param listener The listener to remove.
     */
    public synchronized void removeListener(final IHaltListener listener) {
        if (!_halted && _listeners.contains(listener)) {
            _listeners.remove(listener);
        }
    }

    /**
     * Is the thread halted?
     * @return Is the thread halted?
     */
    @Override
    public boolean isHalted() {
        return _halted;
    }

    /**
     * Halt the thread
     */
    public synchronized void halt() {
        _halted = true;
        ArrayList<IHaltListener> list = new ArrayList<>(_listeners);
        _listeners.clear();
        list.stream().forEach((listener) -> {
            listener.halted(this);
        });
    }
}
