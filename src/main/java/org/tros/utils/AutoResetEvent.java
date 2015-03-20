/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
*/
package org.tros.utils;

/**
 * This is a wrapper class around an object's wait and notify methods.
 * This is used as a class for familiarity with .NET's event classes.
 * @author matta
 */
public class AutoResetEvent {

    private final Object _monitor = new Object();
    private volatile boolean _isOpen = false;

    public AutoResetEvent(boolean open) {
        _isOpen = open;
    }

    public void waitOne() throws InterruptedException {
        synchronized (_monitor) {
            while (!_isOpen) {
                _monitor.wait();
            }
            _isOpen = false;
        }
    }

    public boolean waitOne(long timeout) throws InterruptedException {
        boolean ret = true;
        synchronized (_monitor) {
            long t = System.currentTimeMillis();
            while (!_isOpen) {
                _monitor.wait(timeout);
                // Check for timeout
                if (System.currentTimeMillis() - t >= timeout) {
                    ret = false;
                    break;
                }
            }
            _isOpen = false;
        }
        return ret;
    }

    public void set() {
        synchronized (_monitor) {
            _isOpen = true;
            _monitor.notify();
        }
    }

    public void reset() {
        _isOpen = false;
    }
}
