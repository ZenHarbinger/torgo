/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mailbox: A synchronized class for high-performance message handling. Since
 * Suspend and Resume are deprecated from the Java API, we need a way to halt a
 * sending/receiving thread without the need for polling. This solves that
 * problem.
 *
 * @param <T> The type of messages being sent.
 */
public class Mailbox<T> {

    private final List<T> messages;
    private boolean halt;

    /**
     * Constructor.
     */
    public Mailbox() {
        messages = new ArrayList<>();
        halt = false;
    }

    /**
     * Get the size.
     *
     * @return the number of messages waiting to be processed.
     */
    public synchronized int size() {
        return messages.size();
    }

    /**
     * Halt the mailbox.
     */
    public synchronized void halt() {
        if (!halt) {
            halt = true;
            notifyAll();
        }
    }

    /**
     * Add a message to the queue, notifying any waiting processes that a
     * message is available.
     *
     * @param inMsg a new message to process.
     */
    public synchronized void addMessage(T inMsg) {
        if (!halt) {
            messages.add(inMsg);
            notifyAll();
        }
    }

    /**
     * Receive a message from the queue.
     *
     * @return the next message to process once one arrives.
     */
    public synchronized T getMessage() {
        if (halt) {
            return null;
        }
        while (messages.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                halt = true;
                return null;
            }
            if (halt) {
                return null;
            }
        }
        T ret = messages.remove(0);
        return ret;
    }

    /**
     * Get all messages in the mailbox.
     *
     * @return all waiting messages to process.
     */
    public synchronized Collection<T> getMessages() {
        if (halt) {
            return null;
        }
        while (messages.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                halt = true;
                return null;
            }
            if (halt) {
                return null;
            }
        }
        Collection<T> ret = new ArrayList<>(messages);
        messages.clear();
        return ret;
    }

    /**
     * Is the mailbox halted?
     *
     * @return true if halted, false otherwise.
     */
    public boolean isHalted() {
        return halt;
    }
}
