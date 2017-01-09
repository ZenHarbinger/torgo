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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.tros.torgo.TorgoToolkit;

/**
 * This is a utility class for accessing random members of a collection and also
 * generating random numbers/values.
 *
 * In this class, there are PUID values which is for pseudo-unique-ID values.
 * These values are unique within a given simulation, but can be duplicated for
 * repeatability
 *
 * @author matta
 */
public final class Random {

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

    private final static HashMap<Thread, java.util.Random> RANDOMS;
    private final static HashMap<Object, java.util.Random> SPECIFIC_RANDOMS;
    private static UuidIncrementType _incrementType = UuidIncrementType.useClass;
    private static boolean _doSeed;
    private static int _seedValue;
    private final static HashMap<String, AtomicLong> COUNTERS;
    private static final String DEFAULT_KEY = "puid";

    /**
     * Hidden Constructor.
     */
    private Random() {
    }

    /**
     * Static Constructor.
     */
    static {
        COUNTERS = new HashMap<>();
        Properties prop = new Properties();
        String prop_file = Random.class.getCanonicalName().replace('.', '/') + ".properties";
        try {
            prop.load(TorgoToolkit.getDefaultResourceAccessor().open(prop_file));
            _incrementType = UuidIncrementType.valueOf(prop.getProperty("uuidIncrementType"));
            _doSeed = Boolean.parseBoolean(prop.getProperty("doSeed"));
            _seedValue = Integer.parseInt(prop.getProperty("seedValue"));
        } catch (NullPointerException | IOException ex) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Random.class).fatal(null, ex);
        }
        RANDOMS = new HashMap<>();
        SPECIFIC_RANDOMS = new HashMap<>();
    }

    /**
     * Get Instance.
     *
     * @return
     */
    private static java.util.Random getInstance() {
        Thread curr = Thread.currentThread();
        if (!RANDOMS.containsKey(curr)) {
            if (_doSeed) {
                RANDOMS.put(curr, new java.util.Random(_seedValue));
            } else {
                RANDOMS.put(curr, new java.util.Random());
            }
        }
        return RANDOMS.get(curr);
    }

    /**
     * Get Instance.
     *
     * @param key
     * @return
     */
    public static java.util.Random getInstance(final Object key) {
        if (!SPECIFIC_RANDOMS.containsKey(key)) {
            if (_doSeed) {
                SPECIFIC_RANDOMS.put(key, new java.util.Random(_seedValue));
            } else {
                SPECIFIC_RANDOMS.put(key, new java.util.Random());
            }
        }
        return SPECIFIC_RANDOMS.get(key);
    }

    /**
     * Reset the random object to initial state (only useful if the random
     * object is seeded). This will clear the PUID counters.
     */
    public synchronized static void reset() {
        reset(true);
    }

    /**
     * Reset the specified counter to the specified value.
     *
     * @param c
     * @param value
     */
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
        if (!COUNTERS.containsKey(key)) {
            COUNTERS.put(key, new AtomicLong(1));
        }
        AtomicLong l = COUNTERS.get(key);
        l.set(value + 1);
    }

    /**
     * Reset the random object to initial state (only useful if the random
     * object is seeded). However can be specified to leave the PUID counters
     * alone.
     *
     * @param clear_count specify if we want to clear the UUID values.
     */
    public synchronized static void reset(final boolean clear_count) {
        RANDOMS.clear();
        SPECIFIC_RANDOMS.clear();
        if (clear_count) {
            COUNTERS.clear();
        }
    }

    /**
     * Get a new PUID value for a specified class type.
     *
     * @param c the class type
     * @return a new PUID value
     */
    public static String getRandomName(final Class<?> c) {
        return getPUID(c);
    }

    /**
     * Get a new puid.
     *
     * @param c
     * @param type
     * @return
     */
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
        if (!COUNTERS.containsKey(key)) {
            COUNTERS.put(key, new AtomicLong(1));
        }
        AtomicLong l = COUNTERS.get(key);

        Long l2 = l.getAndIncrement();
        return key + "-" + l2.toString();
    }

    /**
     * Get a new PUID value for a specified class type.
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
     * @param random
     * @return a random true/false value
     */
    public synchronized static boolean nextBoolean(java.util.Random random) {
        return random.nextBoolean();
    }

    /**
     * Return a random boolean value
     *
     * @return a random true/false value
     */
    public synchronized static boolean nextBoolean() {
        return nextBoolean(getInstance());
    }

    /**
     * return a random tri-state value
     *
     * @param random
     * @return a random tri-state value TRUE/FALSE/MAYBE
     */
    public synchronized static TriState nextTriState(java.util.Random random) {
        double d = random.nextDouble();
        if (d < (1.0 / 3.0)) {
            return TriState.FALSE;
        } else if (d <= (2.0 / 3.0)) {
            return TriState.MAYBE;
        } else {
            return TriState.TRUE;
        }
    }

    /**
     * return a random tri-state value
     *
     * @return a random tri-state value TRUE/FALSE/MAYBE
     */
    public synchronized static TriState nextTriState() {
        return nextTriState(getInstance());
    }

    /**
     * Returns a new double value from 0.0 inclusive to 1.0 exclusive.
     *
     * @param random
     * @return a new double value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static double nextDouble(java.util.Random random) {
        return random.nextDouble();
    }

    /**
     * Returns a new double value from 0.0 inclusive to 1.0 exclusive.
     *
     * @return a new double value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static double nextDouble() {
        return nextDouble(getInstance());
    }

    /**
     * Returns a new float value from 0.0 inclusive to 1.0 exclusive.
     *
     * @param random
     * @return a new float value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static float nextFloat(java.util.Random random) {
        return random.nextFloat();
    }

    /**
     * Returns a new float value from 0.0 inclusive to 1.0 exclusive.
     *
     * @return a new float value from 0.0 inclusive to 1.0 exclusive.
     */
    public synchronized static float nextFloat() {
        return nextFloat(getInstance());
    }

    /**
     * Returns a random integer
     *
     * @param random
     * @return a random integer
     */
    public synchronized static int nextInt(java.util.Random random) {
        return random.nextInt();
    }

    /**
     * Returns a random integer
     *
     * @return a random integer
     */
    public synchronized static int nextInt() {
        return nextInt(getInstance());
    }

    /**
     * Returns a random integer less than the specified value
     *
     * @param random
     * @param n the specified value
     * @return a random integer >= 0 and < n
     */
    public synchronized static int nextInt(java.util.Random random, final int n) {
        return random.nextInt(Math.max(1, n));
    }

    /**
     * Returns a random integer less than the specified value
     *
     * @param n the specified value
     * @return a random integer >= 0 and < n
     */
    public synchronized static int nextInt(final int n) {
        return nextInt(getInstance(), Math.max(1, n));
    }

    /**
     * Returns a random integer within the specified range.
     *
     * @param random
     * @param min the min value (inclusive)
     * @param max the max value (exclusive)
     * @return a random integer within the specified range.
     */
    public synchronized static int nextInt(java.util.Random random, final int min, final int max) {
        return (random.nextInt(Math.max(1, max - min)) + min);
    }

    /**
     * Returns a random integer within the specified range.
     *
     * @param min the min value (inclusive)
     * @param max the max value (exclusive)
     * @return a random integer within the specified range.
     */
    public synchronized static int nextInt(final int min, final int max) {
        return nextInt(getInstance(), min, max);
    }

    /**
     * Returns a random long value.
     *
     * @param random
     * @return a random long value.
     */
    public synchronized static long nextLong(java.util.Random random) {
        return random.nextLong();
    }

    /**
     * Returns a random long value.
     *
     * @return a random long value.
     */
    public synchronized static long nextLong() {
        return nextLong(getInstance());
    }

    /**
     * Returns a random long value.
     *
     * @param random
     * @param max
     * @return
     */
    public synchronized static long nextLong(java.util.Random random, final long max) {
        // error checking and 2^x checking removed for simplicity.
        return nextLong(random, 0, Math.max(max, 1));
    }

    /**
     * Returns a random long value.
     *
     * @param max
     * @return
     */
    public synchronized static long nextLong(final long max) {
        // error checking and 2^x checking removed for simplicity.
        return nextLong(getInstance(), max);
    }

    /**
     * Returns a random long value.
     *
     * @param random
     * @param min
     * @param max
     * @return
     */
    public synchronized static long nextLong(java.util.Random random, final long min, final long max) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        java.util.Random rng = random;
        long max2 = Math.abs(max - min);
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % max2;
        } while (bits - val + (max2 - 1) < 0L);
        return val + min;
    }

    /**
     * Returns a random long value.
     *
     * @param min
     * @param max
     * @return
     */
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
        if (count <= 0) {
            return new ArrayList<>();
        }

        if (list.size() > count && count > 0) {
            final int list_count = list.size();
            final int max_stride = list_count / count;
            java.util.Random instance = getInstance();
            final int start = Random.nextInt(instance, list_count % count);
            final ArrayList<T> retVal = new ArrayList<>();
            final ArrayList<Integer> ints = new ArrayList<>();

            for (int ii = start; ints.size() < count; ii += max_stride) {
                if (count - 1 == retVal.size()) {
                    ints.add(Random.nextInt(instance, ii, list_count));
                } else {
                    ints.add(Random.nextInt(instance, ii, ii + max_stride));
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
            return new ArrayList<>(list);
        }
    }

    /**
     * Returns a new collection that has the specified number of items in the
     * list.
     *
     * @param <T> the type
     * @param random
     * @param list the collection to choose random objects from
     * @param count the number of items to select
     * @return a new collection
     */
    public static <T> Collection<T> getRandom(java.util.Random random, final Collection<T> list, final int count) {
        if (count <= 0) {
            return new ArrayList<>();
        }

        if (list.size() > count && count > 0) {
            final int list_count = list.size();
            final int max_stride = list_count / count;
            java.util.Random instance = random;
            final int start = Random.nextInt(instance, list_count % count);
            final ArrayList<T> retVal = new ArrayList<>();
            final ArrayList<Integer> ints = new ArrayList<>();

            for (int ii = start; ints.size() < count; ii += max_stride) {
                if (count - 1 == retVal.size()) {
                    ints.add(Random.nextInt(instance, ii, list_count));
                } else {
                    ints.add(Random.nextInt(instance, ii, ii + max_stride));
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
            return new ArrayList<>(list);
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
        final T elem = getRandom(list);
        if (elem != null && elem.equals(not)) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Random.class).debug("Creating array copy for finding random item.");
            ArrayList<T> l = new ArrayList<>(list);
            l.remove(not);
            return getRandom(l);
        }
        return elem;
    }

    /**
     * Gets a random item from a collection that is NOT equal to the specified
     * object
     *
     * @param <T> the type
     * @param random
     * @param list the collection to select from
     * @param not the object which we do not want a duplicate selection of
     * @return a new randomly selected object which is not equal to the
     * specified value
     */
    public static <T> T getRandom(java.util.Random random, final Collection<T> list, final T not) {
        if (list.isEmpty()) {
            return null;
        }
        final T elem = getRandom(random, list);
        if (elem != null && elem.equals(not)) {
            org.tros.utils.logging.Logging.getLogFactory().getLogger(Random.class).debug("Creating array copy for finding random item.");
            ArrayList<T> l = new ArrayList<>(list);
            l.remove(not);
            return getRandom(random, l);
        }
        return elem;
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
        return getRandom(org.apache.commons.collections4.CollectionUtils.subtract(list, not));
    }

    /**
     * Gets a random item from a collection that is NOT equal to the specified
     * collection
     *
     * @param <T> the type
     * @param random
     * @param list the collection to select from
     * @param not the collection from which we do not want a duplicate selection
     * of
     * @return a new randomly selected object which is not equal to the
     * specified value
     */
    public static <T> T getRandomNotInList(java.util.Random random, final Collection<T> list, final Collection<T> not) {
        return getRandom(random, org.apache.commons.collections4.CollectionUtils.subtract(list, not));
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

        java.util.Random instance = getInstance();
        final int index = Random.nextInt(instance, list.size());
        return org.apache.commons.collections4.IterableUtils.get(list, index);
    }

    /**
     * Get a random item from this list.
     *
     * @param <T>
     * @param random
     * @param list
     * @return
     */
    public static <T> T getRandom(java.util.Random random, final Collection<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        final int index = Random.nextInt(random, list.size());
        return org.apache.commons.collections4.IterableUtils.get(list, index);
    }

    /**
     * Is the random object specified to be seeded for repeatability.
     *
     * @return Is the random object specified to be seeded for repeatability.
     */
    public static boolean isSeeded() {
        return _doSeed;
    }

    /**
     * Set if the random system is to be seeded.
     *
     * @param value
     */
    public static void setSeeded(boolean value) {
        _doSeed = value;
    }

    /**
     * Get the seed.
     *
     * @return
     */
    public static int getSeed() {
        return _seedValue;
    }

    /**
     * Set the seed.
     *
     * @param value
     */
    public static void setSeed(int value) {
        _seedValue = value;
    }

    /**
     * Returns a random real number uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless <tt>a < b</tt>
     */
    public static double nextDouble(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("Invalid range");
        }
        return a + nextDouble() * (b - a);
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability <em>p</em>.
     *
     * @param p the probability of returning <tt>true</tt>
     * @return <tt>true</tt> with probability <tt>p</tt> and
     * <tt>false</tt> with probability <tt>p</tt>
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <=
     * 1.0</tt>
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        return nextDouble() < p;
    }

    /**
     * Returns a random boolean from a Bernoulli distribution with success
     * probability 1/2.
     *
     * @return <tt>true</tt> with probability 1/2 and
     * <tt>false</tt> with probability 1/2
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Returns a random real number from a standard Gaussian distribution.
     *
     * @return a random real number from a standard Gaussian distribution (mean
     * 0 and standard deviation 1).
     */
    public synchronized static double gaussian() {
        // use the polar form of the Box-Muller transform
        double r, x, y;
        do {
            x = nextDouble(-1.0, 1.0);
            y = nextDouble(-1.0, 1.0);
            r = x * x + y * y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // Remark:  y * Math.sqrt(-2 * Math.log(r) / r)
        // is an independent random gaussian
    }

    /**
     * Returns a random real number from a Gaussian distribution with mean &mu;
     * and standard deviation &sigma;.
     *
     * @param mu the mean
     * @param sigma the standard deviation
     * @return a real number distributed according to the Gaussian distribution
     * with mean <tt>mu</tt> and standard deviation <tt>sigma</tt>
     */
    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    /**
     * Returns a random integer from a geometric distribution with success
     * probability <em>p</em>.
     *
     * @param p the parameter of the geometric distribution
     * @return a random integer from a geometric distribution with success
     * probability <tt>p</tt>
     * @throws IllegalArgumentException unless <tt>p >= 0.0</tt> and <tt>p <=
     * 1.0</tt>
     */
    public static int geometric(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        // using algorithm given by Knuth
        return (int) Math.ceil(Math.log(nextDouble()) / Math.log(1.0 - p));
    }

    /**
     * Returns a random integer from a Poisson distribution with mean &lambda;.
     *
     * @param lambda the mean of the Poisson distribution
     * @return a random integer from a Poisson distribution with mean
     * <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt> and not
     * infinite
     */
    public static int poisson(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("Parameter lambda must be positive");
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("Parameter lambda must not be infinite");
        }
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= nextDouble();
        } while (p >= L);
        return k - 1;
    }

    /**
     * Returns a random real number from the standard Pareto distribution.
     *
     * @return a random real number from the standard Pareto distribution
     */
    public static double pareto() {
        return pareto(1.0);
    }

    /**
     * Returns a random real number from a Pareto distribution with shape
     * parameter &alpha;.
     *
     * @param alpha shape parameter
     * @return a random real number from a Pareto distribution with shape
     * parameter <tt>alpha</tt>
     * @throws IllegalArgumentException unless <tt>alpha > 0.0</tt>
     */
    public static double pareto(double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("Shape parameter alpha must be positive");
        }
        return Math.pow(1 - nextDouble(), -1.0 / alpha) - 1.0;
    }

    /**
     * Returns a random real number from the Cauchy distribution.
     *
     * @return a random real number from the Cauchy distribution.
     */
    public static double cauchy() {
        return Math.tan(Math.PI * (nextDouble() - 0.5));
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param probabilities the probability of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * <tt>i</tt> with probability <tt>probabilities[i]</tt>
     * @throws NullPointerException if <tt>probabilities</tt> is <tt>null</tt>
     * @throws IllegalArgumentException if sum of array entries is not (very
     * nearly) equal to <tt>1.0</tt>
     * @throws IllegalArgumentException unless <tt>probabilities[i] >= 0.0</tt>
     * for each index <tt>i</tt>
     */
    public synchronized static int discrete(double[] probabilities) {
        if (probabilities == null) {
            throw new NullPointerException("argument array is null");
        }
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + probabilities[i]);
            }
            sum += probabilities[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) {
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);
        }

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = nextDouble();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    /**
     * Returns a random integer from the specified discrete distribution.
     *
     * @param frequencies the frequency of occurrence of each integer
     * @return a random integer from a discrete distribution:
     * <tt>i</tt> with probability proportional to <tt>frequencies[i]</tt>
     * @throws NullPointerException if <tt>frequencies</tt> is <tt>null</tt>
     * @throws IllegalArgumentException if all array entries are <tt>0</tt>
     * @throws IllegalArgumentException if <tt>frequencies[i]</tt> is negative
     * for any index <tt>i</tt>
     * @throws IllegalArgumentException if sum of frequencies exceeds
     * <tt>Integer.MAX_VALUE</tt> (2<sup>31</sup> - 1)
     */
    public static int discrete(int[] frequencies) {
        if (frequencies == null) {
            throw new NullPointerException("argument array is null");
        }
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + frequencies[i]);
            }
            sum += frequencies[i];
        }
        if (sum == 0) {
            throw new IllegalArgumentException("at least one array entry must be positive");
        }
        if (sum >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("sum of frequencies overflows an int");
        }

        // pick index i with probabilitity proportional to frequency
        double r = nextInt((int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) {
                return i;
            }
        }

        // can't reach here
        assert false;
        return -1;
    }

    /**
     * Returns a random real number from an exponential distribution with rate
     * &lambda;.
     *
     * @param lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with rate
     * <tt>lambda</tt>
     * @throws IllegalArgumentException unless <tt>lambda > 0.0</tt>
     */
    public static double exp(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("Rate lambda must be positive");
        }
        return -Math.log(1 - nextDouble()) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public synchronized static void shuffle(Object[] a) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + nextInt(n - i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public synchronized static void shuffle(double[] a) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + nextInt(n - i);     // between i and n-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param a the array to shuffle
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     */
    public synchronized static void shuffle(int[] a) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + nextInt(n - i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random
     * order.
     *
     * @param a the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) &&
     * (hi < a.length)</tt>
     *
     */
    public synchronized static void shuffle(Object[] a, int lo, int hi) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + nextInt(hi - i + 1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random
     * order.
     *
     * @param a the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) &&
     * (hi < a.length)</tt>
     */
    public synchronized static void shuffle(double[] a, int lo, int hi) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + nextInt(hi - i + 1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random
     * order.
     *
     * @param a the array to shuffle
     * @param lo the left endpoint (inclusive)
     * @param hi the right endpoint (inclusive)
     * @throws NullPointerException if <tt>a</tt> is <tt>null</tt>
     * @throws IndexOutOfBoundsException unless <tt>(0 <= lo) && (lo <= hi) &&
     * (hi < a.length)</tt>
     */
    public synchronized static void shuffle(int[] a, int lo, int hi) {
        if (a == null) {
            throw new NullPointerException("argument array is null");
        }
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + nextInt(hi - i + 1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
