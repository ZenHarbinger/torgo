/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

/**
 * This is a wrapper class around an object's wait and notify methods. This is
 * used as a class for familiarity with .NET's event classes.
 *
 * @author matta
 */
public class AutoResetEvent {

    private final Object monitor = new Object();
    private volatile boolean isOpen = false;

    /**
     * Constructor.
     *
     * @param open default value for the reset event.
     */
    public AutoResetEvent(boolean open) {
        isOpen = open;
    }

    /**
     * Wait until signaled.
     *
     * @throws InterruptedException if halted during wait.
     */
    public void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (!isOpen) {
                monitor.wait();
            }
            isOpen = false;
        }
    }

    /**
     * Wait until signaled.
     *
     * @param timeout time to wait.
     * @return true if active.
     * @throws InterruptedException if halted during wait.
     */
    public boolean waitOne(long timeout) throws InterruptedException {
        boolean ret = true;
        synchronized (monitor) {
            long t = System.currentTimeMillis();
            while (!isOpen) {
                monitor.wait(timeout);
                // Check for timeout
                if (System.currentTimeMillis() - t >= timeout) {
                    ret = false;
                    break;
                }
            }
            isOpen = false;
        }
        return ret;
    }

    /**
     * Signal.
     */
    public void set() {
        synchronized (monitor) {
            isOpen = true;
            monitor.notify();
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        isOpen = false;
    }
}
