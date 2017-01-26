/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import org.apache.commons.lang3.event.EventListenerSupport;

/**
 * Can be used to monitor when a thread is halted/exits.
 *
 * @author matta
 */
public final class HaltMonitor implements ImmutableHaltMonitor {

    private boolean halted;
    private final EventListenerSupport<HaltListener> listeners
            = EventListenerSupport.create(HaltListener.class);
    private String name;

    /**
     * Initialize a new monitor.
     */
    public HaltMonitor() {
        halted = false;
        name = "";
    }

    /**
     * Get the name of the monitor.
     *
     * @return the name of the monitor.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of the monitor.
     *
     * @param value the name of the monitor.
     */
    public void setName(final String value) {
        name = value;
    }

    /**
     * Add a new listener.
     *
     * @param listener The listener to add.
     */
    public synchronized void addHaltListener(final HaltListener listener) {
        listeners.addListener(listener);
    }

    /**
     * Remove a listener.
     *
     * @param listener The listener to remove.
     */
    public synchronized void removeHaltListener(final HaltListener listener) {
        listeners.removeListener(listener);
    }

    /**
     * Is the thread halted?
     *
     * @return Is the thread halted?
     */
    @Override
    public boolean isHalted() {
        return halted;
    }

    /**
     * Halt the thread.
     */
    public synchronized void halt() {
        halted = true;
        listeners.fire().halted(this);
        for (HaltListener l : listeners.getListeners()) {
            listeners.removeListener(l);
        }
    }
}
