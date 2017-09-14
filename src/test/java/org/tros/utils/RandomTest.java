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
package org.tros.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tros.torgo.TorgoToolkit;
import org.tros.utils.logging.Logging;

/**
 *
 * @author matta
 */
public class RandomTest {

    private final static Logger LOGGER;

    static {
        Logging.initLogging(TorgoToolkit.getBuildInfo());
        LOGGER = Logger.getLogger(RandomTest.class.getName());
    }

    public RandomTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Random.
     */
    @Test
    public void testGetInstance() {
        LOGGER.info("getInstance");
        Object key = new Object();
        Random result = org.tros.utils.Random.getInstance(key);
        Random expResult = org.tros.utils.Random.getInstance(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of reset method, of class Random.
     */
    @Test
    public void testReset_0args() {
        LOGGER.info("reset");
        org.tros.utils.Random.reset();
    }

    /**
     * Test of reset method, of class Random.
     */
    @Test
    public void testReset_Class_long() {
        LOGGER.info("reset");
        Class<?> c = RandomTest.class;
        long value = 0L;
        org.tros.utils.Random.reset(c, value);
    }

    /**
     * Test of reset method, of class Random.
     */
    @Test
    public void testReset_boolean() {
        LOGGER.info("reset");
        org.tros.utils.Random.reset(true);
        org.tros.utils.Random.reset(false);
    }

    /**
     * Test of getRandomName method, of class Random.
     */
    @Test
    public void testGetRandomName() {
        LOGGER.info("getRandomName");
        Class<?> c = RandomTest.class;
        String result = org.tros.utils.Random.getRandomName(c);
        assertNotNull(result);
    }

    /**
     * Test of getPUID method, of class Random.
     */
    @Test
    public void testGetPUID_Class_RandomUuidIncrementType() {
        LOGGER.info("getPUID");
        Class<?> c = RandomTest.class;
        org.tros.utils.Random.UuidIncrementType type = org.tros.utils.Random.UuidIncrementType.useClass;
        String result = org.tros.utils.Random.getPUID(c, type);
        assertNotNull(result);
        type = org.tros.utils.Random.UuidIncrementType.useInstance;
        result = org.tros.utils.Random.getPUID(c, type);
        assertNotNull(result);
        type = org.tros.utils.Random.UuidIncrementType.usePackage;
        result = org.tros.utils.Random.getPUID(c, type);
        assertNotNull(result);
    }

    /**
     * Test of getPUID method, of class Random.
     */
    @Test
    public void testGetPUID_Class() {
        LOGGER.info("getPUID");
        Class<?> c = RandomTest.class;
        String result = org.tros.utils.Random.getPUID(c);
        assertNotNull(result);
    }

    /**
     * Test of getPUID method, of class Random.
     */
    @Test
    public void testGetPUID_Class_int() {
        LOGGER.info("getPUID");
        Class<?> c = RandomTest.class;
        int strength = 4;
        String result = org.tros.utils.Random.getPUID(c, strength);
        assertNotNull(result);
    }

    /**
     * Test of nextBoolean method, of class Random.
     */
    @Test
    public void testNextBoolean_Random() {
        LOGGER.info("nextBoolean");
        Random random = new Random();
        boolean result1 = org.tros.utils.Random.nextBoolean(random);
        boolean result2 = org.tros.utils.Random.nextBoolean(random);
        while (result1 == result2) {
            result2 = org.tros.utils.Random.nextBoolean(random);
        }
    }

    /**
     * Test of nextBoolean method, of class Random.
     */
    @Test
    public void testNextBoolean_0args() {
        LOGGER.info("nextBoolean");
        boolean result1 = org.tros.utils.Random.nextBoolean();
        boolean result2 = org.tros.utils.Random.nextBoolean();
        while (result1 == result2) {
            result2 = org.tros.utils.Random.nextBoolean();
        }
    }

    /**
     * Test of nextTriState method, of class Random.
     */
    @Test
    public void testNextTriState_Random() {
        LOGGER.info("nextTriState");
        Random random = new Random();
        org.tros.utils.Random.TriState expResult = org.tros.utils.Random.TriState.FALSE;
        org.tros.utils.Random.TriState result = org.tros.utils.Random.nextTriState(random);
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState(random);
        }
        expResult = org.tros.utils.Random.TriState.TRUE;
        result = org.tros.utils.Random.nextTriState(random);
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState(random);
        }
        expResult = org.tros.utils.Random.TriState.MAYBE;
        result = org.tros.utils.Random.nextTriState(random);
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState(random);
        }
    }

    /**
     * Test of nextTriState method, of class Random.
     */
    @Test
    public void testNextTriState_0args() {
        LOGGER.info("nextTriState");
        org.tros.utils.Random.TriState expResult = org.tros.utils.Random.TriState.FALSE;
        org.tros.utils.Random.TriState result = org.tros.utils.Random.nextTriState();
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState();
        }
        expResult = org.tros.utils.Random.TriState.TRUE;
        result = org.tros.utils.Random.nextTriState();
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState();
        }
        expResult = org.tros.utils.Random.TriState.MAYBE;
        result = org.tros.utils.Random.nextTriState();
        while (result != expResult) {
            result = org.tros.utils.Random.nextTriState();
        }
    }

    /**
     * Test of nextDouble method, of class Random.
     */
    @Test
    public void testNextDouble_Random() {
        LOGGER.info("nextDouble");
        int seed = 22;
        Random random = new Random(seed);
        double result1 = org.tros.utils.Random.nextDouble(random);
        random = new Random(seed);
        double result2 = org.tros.utils.Random.nextDouble(random);
        assertEquals(result2, result1, 0.0);
    }

    /**
     * Test of nextDouble method, of class Random.
     */
    @Test
    public void testNextDouble_0args() {
        LOGGER.info("nextDouble");
        org.tros.utils.Random.nextDouble();
        org.tros.utils.Random.nextDouble();
        org.tros.utils.Random.nextDouble();
        org.tros.utils.Random.nextDouble();
        org.tros.utils.Random.nextDouble();
    }

    /**
     * Test of nextFloat method, of class Random.
     */
    @Test
    public void testNextFloat_Random() {
        LOGGER.info("nextFloat");
        int seed = 22;
        Random random = new Random(seed);
        float result1 = org.tros.utils.Random.nextFloat(random);
        random = new Random(seed);
        float result2 = org.tros.utils.Random.nextFloat(random);
        assertEquals(result2, result1, 0.0);
    }

    /**
     * Test of nextFloat method, of class Random.
     */
    @Test
    public void testNextFloat_0args() {
        LOGGER.info("nextFloat");
        org.tros.utils.Random.nextFloat();
        org.tros.utils.Random.nextFloat();
        org.tros.utils.Random.nextFloat();
        org.tros.utils.Random.nextFloat();
        org.tros.utils.Random.nextFloat();
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_Random() {
        LOGGER.info("nextInt");
        int seed = 22;
        Random random = new Random(seed);
        int result1 = org.tros.utils.Random.nextInt(random);
        random = new Random(seed);
        int result2 = org.tros.utils.Random.nextInt(random);
        assertEquals(result2, result1);
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_0args() {
        LOGGER.info("nextInt");
        org.tros.utils.Random.nextInt();
        org.tros.utils.Random.nextInt();
        org.tros.utils.Random.nextInt();
        org.tros.utils.Random.nextInt();
        org.tros.utils.Random.nextInt();
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_Random_int() {
        LOGGER.info("nextInt");
        int seed = 22;
        int n = 10;
        Random random = new Random(seed);
        int result1 = org.tros.utils.Random.nextInt(random, n);
        random = new Random(seed);
        int result2 = org.tros.utils.Random.nextInt(random, n);
        assertEquals(result2, result1);
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_int() {
        LOGGER.info("nextInt");
        int n = 10;
        org.tros.utils.Random.nextInt(n);
        org.tros.utils.Random.nextInt(n);
        org.tros.utils.Random.nextInt(n);
        org.tros.utils.Random.nextInt(n);
        org.tros.utils.Random.nextInt(n);
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_3args() {
        LOGGER.info("nextInt");
        int seed = 22;
        int min = 5;
        int max = 50;
        Random random = new Random(seed);
        int result1 = org.tros.utils.Random.nextInt(random, min, max);
        random = new Random(seed);
        int result2 = org.tros.utils.Random.nextInt(random, min, max);
        assertEquals(result2, result1);
        try {
            org.tros.utils.Random.nextInt(random, max, min);
        } catch (IllegalArgumentException ex) {

        }
    }

    /**
     * Test of nextInt method, of class Random.
     */
    @Test
    public void testNextInt_int_int() {
        LOGGER.info("nextInt");
        int min = 5;
        int max = 50;
        org.tros.utils.Random.nextInt(min, max);
        org.tros.utils.Random.nextInt(min, max);
        org.tros.utils.Random.nextInt(min, max);
        org.tros.utils.Random.nextInt(min, max);
        org.tros.utils.Random.nextInt(min, max);
        try {
            org.tros.utils.Random.nextInt(max, min);
        } catch (IllegalArgumentException ex) {

        }
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_Random() {
        LOGGER.info("nextLong");
        int seed = 22;
        int n = 10;
        Random random = new Random(seed);
        long result1 = org.tros.utils.Random.nextLong(random, n);
        random = new Random(seed);
        long result2 = org.tros.utils.Random.nextLong(random, n);
        assertEquals(result2, result1);
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_0args() {
        LOGGER.info("nextLong");
        org.tros.utils.Random.nextLong();
        org.tros.utils.Random.nextLong();
        org.tros.utils.Random.nextLong();
        org.tros.utils.Random.nextLong();
        org.tros.utils.Random.nextLong();
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_Random_long() {
        LOGGER.info("nextLong");
        int seed = 22;
        int n = 10;
        Random random = new Random(seed);
        long result1 = org.tros.utils.Random.nextLong(random, n);
        random = new Random(seed);
        long result2 = org.tros.utils.Random.nextLong(random, n);
        assertEquals(result2, result1);
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_long() {
        LOGGER.info("nextLong");
        int max = 5;
        org.tros.utils.Random.nextLong(max);
        org.tros.utils.Random.nextLong(max);
        org.tros.utils.Random.nextLong(max);
        org.tros.utils.Random.nextLong(max);
        org.tros.utils.Random.nextLong(max);
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_3args() {
        LOGGER.info("nextLong");
        int seed = 22;
        int min = 5;
        int max = 50;
        Random random = new Random(seed);
        long result1 = org.tros.utils.Random.nextLong(random, min, max);
        random = new Random(seed);
        long result2 = org.tros.utils.Random.nextLong(random, min, max);
        assertEquals(result2, result1);
        try {
            org.tros.utils.Random.nextLong(random, max, min);
        } catch (IllegalArgumentException ex) {

        }
    }

    /**
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_long_long() {
        LOGGER.info("nextLong");
        int min = 5;
        int max = 50;
        org.tros.utils.Random.nextLong(min, max);
        org.tros.utils.Random.nextLong(min, max);
        org.tros.utils.Random.nextLong(min, max);
        org.tros.utils.Random.nextLong(min, max);
        org.tros.utils.Random.nextLong(min, max);
        try {
            org.tros.utils.Random.nextLong(max, min);
        } catch (IllegalArgumentException ex) {

        }
    }

    /**
     * Test of nextGaussian method, of class Random.
     */
    @Test
    public void testNextGaussian() {
        LOGGER.info("nextGaussian");
        Object key = new Object();
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
        org.tros.utils.Random.nextGaussian(key);
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_Collection_Collection() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        ArrayList<Integer> pulled = new ArrayList<>();
        while (pulled.size() != coll.size()) {
            Integer result = org.tros.utils.Random.getRandom(coll);
            if (!pulled.contains(result)) {
                pulled.add(result);
            }
        }
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_3args_1() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            coll.add(ii);
        }
        Integer random = org.tros.utils.Random.getRandom(new Random(), coll, new Integer(1));
        assertEquals(0, random.intValue());
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_Collection_3args_1() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        Collection<Integer> result = org.tros.utils.Random.getRandom(new Random(), coll, 5);
        assertEquals(5, result.size());
        coll.clear();
        for (int ii = 0; ii < 4; ii++) {
            coll.add(ii);
        }
        result = org.tros.utils.Random.getRandom(new Random(), coll, 5);
        assertEquals(4, result.size());
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testDiscreteDouble() {
        LOGGER.info("discrete double[]");

        double[] discrete1 = new double[]{0.1, 0.2, 0.3, 0.4};
        double[] discrete2 = new double[]{-0.1, 0.2, 0.3, 0.4};
        double[] discrete3 = new double[]{0.1, 0.2, 0.3, 0.4, 0.5};

        org.tros.utils.Random.discrete(discrete1);
        boolean thrown = false;
        try {
            org.tros.utils.Random.discrete(discrete2);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.discrete(discrete3);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testDiscreteInt() {
        LOGGER.info("discrete int[]");

        int[] discrete1 = new int[]{1, 2, 3, 4};
        int[] discrete2 = new int[]{0};
        int[] discrete3 = new int[]{Integer.MAX_VALUE, 1};
        int[] discrete4 = new int[]{-1, 1, 2};

        org.tros.utils.Random.discrete(discrete1);
        boolean thrown = false;
        try {
            org.tros.utils.Random.discrete(discrete2);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.discrete(discrete3);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.discrete(discrete4);
        } catch (IllegalArgumentException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_int() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        Collection<Integer> result = org.tros.utils.Random.getRandom(coll, 5);
        assertEquals(5, result.size());
        coll.clear();
        for (int ii = 0; ii < 4; ii++) {
            coll.add(ii);
        }
        result = org.tros.utils.Random.getRandom(coll, 5);
        assertEquals(4, result.size());
    }

    /**
     * Test of getRandomNotInList method, of class Random.
     */
    @Test
    public void testGetRandomNotInList_Collection_Collection() {
        LOGGER.info("getRandomNotInList");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        ArrayList<Integer> not = new ArrayList<>();
        for (int ii = 0; ii < 50; ii++) {
            not.add(ii);
        }

        ArrayList<Integer> pulled = new ArrayList<>();
        while (pulled.size() < coll.size() - not.size()) {
            Integer randomNotInList = org.tros.utils.Random.getRandomNotInList(coll, not);
            if (!pulled.contains(randomNotInList)) {
                pulled.add(randomNotInList);
            }
        }
    }

    /**
     * Test of getRandomNotInList method, of class Random.
     */
    @Test
    public void testGetRandomNotInList_3args() {
        LOGGER.info("getRandomNotInList");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        ArrayList<Integer> not = new ArrayList<>();
        for (int ii = 0; ii < 50; ii++) {
            not.add(ii);
        }

        ArrayList<Integer> pulled = new ArrayList<>();
        while (pulled.size() < coll.size() - not.size()) {
            Integer randomNotInList = org.tros.utils.Random.getRandomNotInList(new Random(), coll, not);
            if (!pulled.contains(randomNotInList)) {
                pulled.add(randomNotInList);
            }
        }
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_Collection_Not() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            coll.add(ii);
        }
        Integer result = org.tros.utils.Random.getRandom(coll, new Integer(0));
        assertEquals(1, result.intValue());
    }

    /**
     * Test of getRandom method, of class Random.
     */
    @Test
    public void testGetRandom_Random_Collection() {
        LOGGER.info("getRandom");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        Collection<Integer> result = org.tros.utils.Random.getRandom(coll, 5);
        assertEquals(5, result.size());
        coll.clear();
        for (int ii = 0; ii < 4; ii++) {
            coll.add(ii);
        }
        result = org.tros.utils.Random.getRandom(coll, 5);
        assertEquals(4, result.size());
    }

    /**
     * Test of isSeeded method, of class Random.
     */
    @Test
    public void testIsSeeded() {
        LOGGER.info("isSeeded");
        boolean expResult = false;
        org.tros.utils.Random.setSeeded(expResult);
        assertNotNull(org.tros.utils.Random.getInstance(new Object()));
        boolean result = org.tros.utils.Random.isSeeded();
        assertEquals(expResult, result);

        expResult = true;
        org.tros.utils.Random.setSeeded(expResult);
        result = org.tros.utils.Random.isSeeded();
        assertNotNull(org.tros.utils.Random.getInstance(new Object()));
        assertEquals(expResult, result);
    }

    /**
     * Test of setSeeded method, of class Random.
     */
    @Test
    public void testSetSeeded() {
        LOGGER.info("setSeeded");
        boolean expResult = false;
        org.tros.utils.Random.setSeeded(expResult);
        boolean result = org.tros.utils.Random.isSeeded();
        assertEquals(expResult, result);

        expResult = true;
        org.tros.utils.Random.setSeeded(expResult);
        result = org.tros.utils.Random.isSeeded();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSeed method, of class Random.
     */
    @Test
    public void testGetSeed() {
        LOGGER.info("getSeed");
        int expResult = 51;
        org.tros.utils.Random.setSeed(expResult);
        int result = org.tros.utils.Random.getSeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSeed method, of class Random.
     */
    @Test
    public void testSetSeed() {
        LOGGER.info("setSeed");
        int expResult = 42;
        org.tros.utils.Random.setSeed(expResult);
        int result = org.tros.utils.Random.getSeed();
        assertEquals(expResult, result);
    }

    /**
     * Test of nextDouble method, of class Random.
     */
    @Test
    public void testNextDouble_double_double() {
        LOGGER.info("nextDouble");
        int min = 5;
        int max = 50;
        org.tros.utils.Random.nextDouble(min, max);
        org.tros.utils.Random.nextDouble(min, max);
        org.tros.utils.Random.nextDouble(min, max);
        org.tros.utils.Random.nextDouble(min, max);
        org.tros.utils.Random.nextDouble(min, max);

        try {
            org.tros.utils.Random.nextDouble(max, min);
        } catch (IllegalArgumentException ex) {

        }
    }

    /**
     * Test of bernoulli method, of class Random.
     */
    @Test
    public void testBernoulli_double() {
        LOGGER.info("bernoulli");

        try {
            org.tros.utils.Random.bernoulli(-0.1);
        } catch (IllegalArgumentException ex) {

        }
        try {
            org.tros.utils.Random.bernoulli(1.1);
        } catch (IllegalArgumentException ex) {

        }
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
        org.tros.utils.Random.bernoulli(0.75);
    }

    /**
     * Test of bernoulli method, of class Random.
     */
    @Test
    public void testBernoulli_0args() {
        LOGGER.info("bernoulli");
        org.tros.utils.Random.bernoulli();
        org.tros.utils.Random.bernoulli();
        org.tros.utils.Random.bernoulli();
        org.tros.utils.Random.bernoulli();
        org.tros.utils.Random.bernoulli();
    }

    /**
     * Test of gaussian method, of class Random.
     */
    @Test
    public void testGaussian_0args() {
        LOGGER.info("gaussian");
        org.tros.utils.Random.gaussian();
        org.tros.utils.Random.gaussian();
        org.tros.utils.Random.gaussian();
        org.tros.utils.Random.gaussian();
        org.tros.utils.Random.gaussian();
    }

    /**
     * Test of gaussian method, of class Random.
     */
    @Test
    public void testGaussian_double_double() {
        LOGGER.info("gaussian");
        double mu = 10.0;
        double sigma = Math.PI;
        org.tros.utils.Random.gaussian(mu, sigma);
        org.tros.utils.Random.gaussian(mu, sigma);
        org.tros.utils.Random.gaussian(mu, sigma);
        org.tros.utils.Random.gaussian(mu, sigma);
        org.tros.utils.Random.gaussian(mu, sigma);
    }

    /**
     * Test of geometric method, of class Random.
     */
    @Test
    public void testGeometric() {
        LOGGER.info("geometric");
        try {
            org.tros.utils.Random.geometric(-0.1);
        } catch (IllegalArgumentException ex) {

        }
        try {
            org.tros.utils.Random.geometric(1.1);
        } catch (IllegalArgumentException ex) {

        }
        org.tros.utils.Random.geometric(0.5);
        org.tros.utils.Random.geometric(0.5);
        org.tros.utils.Random.geometric(0.5);
        org.tros.utils.Random.geometric(0.5);
        org.tros.utils.Random.geometric(0.5);
    }

    /**
     * Test of poisson method, of class Random.
     */
    @Test
    public void testPoisson() {
        LOGGER.info("poisson");
        try {
            org.tros.utils.Random.poisson(-1);
        } catch (IllegalArgumentException ex) {

        }
        try {
            org.tros.utils.Random.poisson(Double.POSITIVE_INFINITY);
        } catch (IllegalArgumentException ex) {

        }
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
        org.tros.utils.Random.poisson(10);
    }

    /**
     * Test of pareto method, of class Random.
     */
    @Test
    public void testPareto_0args() {
        LOGGER.info("pareto");
        org.tros.utils.Random.pareto();
        org.tros.utils.Random.pareto();
        org.tros.utils.Random.pareto();
        org.tros.utils.Random.pareto();
        org.tros.utils.Random.pareto();
    }

    /**
     * Test of pareto method, of class Random.
     */
    @Test
    public void testPareto_double() {
        LOGGER.info("pareto");
        double alpha = -1;
        try {
            org.tros.utils.Random.pareto(alpha);
        } catch (IllegalArgumentException ex) {

        }
        alpha = 0.5;
        org.tros.utils.Random.pareto(alpha);
        org.tros.utils.Random.pareto(alpha);
        org.tros.utils.Random.pareto(alpha);
        org.tros.utils.Random.pareto(alpha);
        org.tros.utils.Random.pareto(alpha);
    }

    /**
     * Test of cauchy method, of class Random.
     */
    @Test
    public void testCauchy() {
        LOGGER.info("cauchy");
        org.tros.utils.Random.cauchy();
        org.tros.utils.Random.cauchy();
        org.tros.utils.Random.cauchy();
        org.tros.utils.Random.cauchy();
        org.tros.utils.Random.cauchy();
    }

//    /**
//     * Test of discrete method, of class Random.
//     */
//    @Test
//    public void testDiscrete_doubleArr() {
//        LOGGER.info("discrete");
//        double[] probabilities = null;
//        int expResult = 0;
//        int result = org.tros.utils.Random.discrete(probabilities);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of discrete method, of class Random.
//     */
//    @Test
//    public void testDiscrete_intArr() {
//        LOGGER.info("discrete");
//        int[] frequencies = null;
//        int expResult = 0;
//        int result = org.tros.utils.Random.discrete(frequencies);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of exp method, of class Random.
     */
    @Test
    public void testExp() {
        LOGGER.info("exp");
        double lambda = -1.0;
        try {
            org.tros.utils.Random.exp(lambda);
        } catch (IllegalArgumentException ex) {
        }
        lambda = 1.0;
        org.tros.utils.Random.exp(lambda);
        org.tros.utils.Random.exp(lambda);
        org.tros.utils.Random.exp(lambda);
        org.tros.utils.Random.exp(lambda);
        org.tros.utils.Random.exp(lambda);
    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_ObjectArr() {
        LOGGER.info("shuffle Object[]");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        Object[] a = (Object[]) coll.toArray(new Integer[]{});
        org.tros.utils.Random.shuffle(a);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((Object[]) null);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_doubleArr() {
        LOGGER.info("shuffle double[]");
        double[] arr = new double[100];
        for (int ii = 0; ii < 100; ii++) {
            arr[ii] = ii;
        }
        org.tros.utils.Random.shuffle(arr);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((double[]) null);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_intArr() {
        LOGGER.info("shuffle int[]");
        int[] arr = new int[100];
        for (int ii = 0; ii < 100; ii++) {
            arr[ii] = ii;
        }
        org.tros.utils.Random.shuffle(arr);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((int[]) null);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_3args_1() {
        LOGGER.info("shuffle Object[] 3");
        ArrayList<Integer> coll = new ArrayList<>();
        for (int ii = 0; ii < 100; ii++) {
            coll.add(ii);
        }
        Object[] a = (Object[]) coll.toArray(new Integer[]{});
        org.tros.utils.Random.shuffle(a, 25, 75);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((Object[]) null, 0, 1);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            org.tros.utils.Random.shuffle(a, -1, a.length - 1);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(a, a.length - 1, 0);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(a, 0, a.length);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_3args_2() {
        LOGGER.info("shuffle double[] 3");
        double[] arr = new double[100];
        for (int ii = 0; ii < 100; ii++) {
            arr[ii] = ii;
        }
        org.tros.utils.Random.shuffle(arr, 25, 75);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((double[]) null, 0, 1);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);


        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, -1, arr.length - 1);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, arr.length - 1, 0);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, 0, arr.length);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Test of shuffle method, of class Random.
     */
    @Test
    public void testShuffle_3args_3() {
        LOGGER.info("shuffle int[] 3");
        int[] arr = new int[100];
        for (int ii = 0; ii < 100; ii++) {
            arr[ii] = ii;
        }
        org.tros.utils.Random.shuffle(arr, 25, 75);

        boolean thrown = false;
        try {
            org.tros.utils.Random.shuffle((int[]) null, 0, 1);
        } catch (NullPointerException npe) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, -1, arr.length - 1);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, arr.length - 1, 0);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            org.tros.utils.Random.shuffle(arr, 0, arr.length);
        } catch (IndexOutOfBoundsException npe) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
