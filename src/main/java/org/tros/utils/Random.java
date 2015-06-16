/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This is a utility class for accessing random members of a collection and also
 * generating random numbers/values.
 *
 * In this class, there are PUID values which is for pseudo-unique-ID values.
 * These values are unique within a given simulation, but can be duplicated for
 * repeatability.
 *
 * @author matta
 */
public final class Random {

    private final static boolean _legacy;

    /**
     * Enumeration for a 3 state system
     */
    public enum TriState {

        TRUE,
        FALSE,
        MAYBE
    }

    /**
     * Enumeration for how the UUID values should be incremented
     */
    public enum UuidIncrementType {

        useInstance,
        useClass,
        usePackage
    }

    private final static HashMap<Thread, java.util.Random> _randoms;
    private final static HashMap<Object, java.util.Random> _specificRandoms;
    private static UuidIncrementType _incrementType = UuidIncrementType.useClass;
    private static boolean _doSeed;
    private static int _seedValue;
    private final static HashMap<String, AtomicLong> _counters;
    private static final String DEFAULT_KEY = "puid";

    private Random() {
    }

    static {
        ServiceLoader<ResourceAccessor> accessors = ServiceLoader.load(ResourceAccessor.class);
        ResourceAccessor accessor = accessors.iterator().next();
        boolean legacy = false;

        _counters = new HashMap<String, AtomicLong>();
        Properties prop = new Properties();
        String prop_file = org.tros.utils.Random.class.getCanonicalName().replace('.', '/') + ".properties";
        try {
            prop.load(accessor.open(prop_file));
            _incrementType = UuidIncrementType.valueOf(prop.getProperty("uuidIncrementType"));
            _doSeed = Boolean.parseBoolean(prop.getProperty("doSeed"));
            _seedValue = Integer.parseInt(prop.getProperty("seedValue"));
            legacy = Boolean.parseBoolean(prop.getProperty("useLegacy"));
        } catch (NullPointerException ex) {
            LogFactory.getLog(Random.class).warn(null, ex);
        } catch (IOException ex) {
            LogFactory.getLog(Random.class).warn(null, ex);
        }
        _legacy = legacy;
        _randoms = new HashMap<Thread, java.util.Random>();
        _specificRandoms = new HashMap<Object, java.util.Random>();
    }

    private static java.util.Random getInstance() {
        Thread curr = Thread.currentThread();
        if (!_randoms.containsKey(curr)) {
            if (_doSeed) {
                _randoms.put(curr, new java.util.Random(_seedValue));
            } else {
                _randoms.put(curr, new java.util.Random());
            }
        }
        return _randoms.get(curr);
    }

    private static java.util.Random getInstance(final Object key) {
        if (!_specificRandoms.containsKey(key)) {
            if (_doSeed) {
                _specificRandoms.put(key, new java.util.Random(_seedValue));
            } else {
                _specificRandoms.put(key, new java.util.Random());
            }
        }
        return _specificRandoms.get(key);
    }

    /**
     * Reset the random object to initial state (only useful if the random
     * object is seeded). This will clear the PUID counters.
     */
    public synchronized static void reset() {
        reset(true);
    }

    public synchronized static void reset(Class<?> c, long value) {
        String key = DEFAULT_KEY;
        switch (_incrementType) {
            case useClass:
                key = c.getName();
                break;
            case usePackage:
                key = c.getPackage().getName();
                break;
        }
        if (!_counters.containsKey(key)) {
            _counters.put(key, new AtomicLong(1));
        }
        AtomicLong l = _counters.get(key);
        l.set(value + 1);
    }

    /**
     * Reset the random object to initial state (only useful if the random
     * object is seeded) However can be specified to leave the PUID counters
     * alone.
     *
     * @param clear_count specify if we want to clear the UUID values.
     */
    public synchronized static void reset(final boolean clear_count) {
        _randoms.clear();
        _specificRandoms.clear();
        if (clear_count) {
            _counters.clear();
        }
    }

    /**
     * get a new PUID value for a specified class type.
     *
     * @param c the class type
     * @return a new PUID value
     */
    public static String getRandomName(final Class<?> c) {
        return getPUID(c);
    }

    public synchronized static String getPUID(final Class<?> c, UuidIncrementType type) {
        String key = DEFAULT_KEY;
        switch (type) {
            case useClass:
                key = c.getName();
                break;
            case usePackage:
                key = c.getPackage().getName();
                break;
        }
        if (!_counters.containsKey(key)) {
            _counters.put(key, new AtomicLong(1));
        }
        AtomicLong l = _counters.get(key);

        Long l2 = l.getAndIncrement();
        return key + "-" + l2.toString();
    }

    /**
     * get a new PUID value for a specified class type.
     *
     * @param c the class type
     * @return a new PUID value
     */
    public synchronized static String getPUID(final Class<?> c) {
        return getPUID(c, _incrementType);
    }

    /**
     *
     * get a new PUID value for a specified class type.
     *
     * @param c the class type
     * @param strength the strength of the PUID (unused for now)
     * @return a new PUID value
     */
    public synchronized static String getPUID(final Class<?> c, final int strength) {
        return getPUID(c);
    }

    /**
     * Return a random boolean value
     *
     * @return a random true/false value
     */
    public synchronized static boolean nextBoolean() {
        return getInstance().nextBoolean();
    }

    /**
     * return a random tri-state value
     *
     * @return a random tri-state value TRUE/FALSE/MAYBE
     */
    public synchronized static TriState nextTriState() {
        double d = getInstance().nextDouble();
        if (d < (1.0 / 3.0)) {
            return TriState.FALSE;
        } else if (d <= (2.0 / 3.0)) {
            return TriState.MAYBE;
        } else {
            return TriState.TRUE;
        }
    }

    /**
     * Returns a new double value from 0.0 inclusive to 1.0 exclusive.
     *
     * @return a new double value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static double nextDouble() {
        return getInstance().nextDouble();
    }

    /**
     * Returns a new float value from 0.0 inclusive to 1.0 exclusive.
     *
     * @return a new float value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static float nextFloat() {
        return getInstance().nextFloat();
    }

    /**
     * Returns a random integer
     *
     * @return a random integer
     */
    public synchronized static int nextInt() {
        return getInstance().nextInt();
    }

    /**
     * Returns a random integer less than the specified value
     *
     * @param n the specified value
     * @return a random integer >= 0 and < n
     */
    public synchronized static int nextInt(final int n) {
        return getInstance().nextInt(Math.max(1, n));
    }

    /**
     * Returns a random integer within the specified range.
     *
     * @param min the min value (inclusive)
     * @param max the max value (exclusive)
     * @return a random integer within the specified range.
     */
    public synchronized static int nextInt(final int min, final int max) {
        return (getInstance().nextInt(Math.max(1, max - min)) + min);
    }

    /**
     * Returns a random long value.
     *
     * @return a random long value.
     */
    public synchronized static long nextLong() {
        return getInstance().nextLong();
    }

    public synchronized static long nextLong(final long max) {
        // error checking and 2^x checking removed for simplicity.
        return nextLong(0, Math.max(max, 1));
    }

    public synchronized static long nextLong(final long min, final long max) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        java.util.Random rng = getInstance();
        long max2 = Math.abs(max - min);
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % max2;
        } while (bits - val + (max2 - 1) < 0L);
        return val + min;
    }

    /**
     * Returns the next value in a Gaussian series.
     *
     * @param key the key for the Guassian series.
     * @return a random value with a Guassian distribution.
     */
    public synchronized static double nextGaussian(final Object key) {
        return getInstance(key).nextGaussian();
    }

    /**
     * Returns a new collection that has the specified number of items in the
     * list.
     *
     * @param <T> the type
     * @param list the collection to choose random objects from
     * @param count the number of items to select
     * @return a new collection
     */
    public static <T> Collection<T> getRandom(final Collection<T> list, final int count) {
        if (list.size() > count && count > 0) {
            final int list_count = list.size();
            final int max_stride = list_count / count;
            final int start = org.tros.utils.Random.nextInt(list_count % count);
            final ArrayList<T> retVal = new ArrayList<T>();
            final ArrayList<Integer> ints = new ArrayList<Integer>();

            for (int ii = start; ints.size() < count; ii += max_stride) {
                if (count - 1 == retVal.size()) {
                    ints.add(org.tros.utils.Random.nextInt(ii, list_count));
                } else {
                    ints.add(org.tros.utils.Random.nextInt(ii, ii + max_stride));
                }
            }

            Iterator<T> it = list.iterator();
            Integer c = 0;
            while (it.hasNext()) {
                T ret = it.next();
                if (ints.contains(c)) {
                    retVal.add(ret);
                }
                c++;
            }
            return retVal;
        } else {
            return new ArrayList<T>(list);
        }
    }

    /**
     * Gets a random item from a collection that is NOT equal to the specified
     * object
     *
     * @param <T> the type
     * @param list the collection to select from
     * @param not the object which we do not want a duplicate selection of
     * @return a new randomly selected object which is not equal to the
     * specified value
     */
    public static <T> T getRandom(final Collection<T> list, final T not) {
        if (list.isEmpty()) {
            return null;
        }
        if (_legacy) {
            Collection<T> not_list = new ArrayList<T>();
            not_list.add(not);
            return getRandomNotInList(list, not_list);
        } else {
            final T elem = getRandom(list);
            if (elem != null && elem.equals(not)) {
                LogFactory.getLog(Random.class).debug("Creating array copy for finding random item.");
                ArrayList<T> l = new ArrayList<T>(list);
                l.remove(not);
                return getRandom(l);
            }
            return elem;
        }
    }

    /**
     * Gets a random item from a collection that is NOT equal to the specified
     * collection
     *
     * @param <T> the type
     * @param list the collection to select from
     * @param not the collection from which we do not want a duplicate selection
     * of
     * @return a new randomly selected object which is not equal to the
     * specified value
     */
    public static <T> T getRandomNotInList(final Collection<T> list, final Collection<T> not) {
        if (_legacy) {
            ArrayList<T> l = new ArrayList<T>(list);
            l.removeAll(not);
            return getRandom(l);
        } else {
            return getRandom(org.apache.commons.collections4.CollectionUtils.subtract(list, not));
        }
    }

    /**
     * Gets a random item from the specified collection
     *
     * @param <T> the type
     * @param list the specified collection
     * @return a randomly selected object from the collection
     */
    public static <T> T getRandom(final Collection<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        if (_legacy) {
            int index = org.tros.utils.Random.nextInt(list.size());
            Iterator<T> it = list.iterator();
            int count = 0;
            while (it.hasNext()) {
                T ret = it.next();
                if (count == index) {
                    return ret;
                }
                count++;
            }
            return null;
        } else {
            final int index = org.tros.utils.Random.nextInt(list.size());
            return org.apache.commons.collections4.CollectionUtils.get(list, index);
        }
    }

    /**
     * Is the random object specified to be seeded for repeatability.
     *
     * @return Is the random object specified to be seeded for repeatability.
     */
    public static boolean isSeeded() {
        return _doSeed;
    }

    public static void setSeeded(boolean value) {
        _doSeed = value;
    }

    public static int getSeed() {
        return _seedValue;
    }

    public static void setSeed(int value) {
        _seedValue = value;
    }
}
