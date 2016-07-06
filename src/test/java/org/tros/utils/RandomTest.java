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

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matta
 */
public class RandomTest {

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
        System.out.println("getInstance");
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
        System.out.println("reset");
        org.tros.utils.Random.reset();
    }

    /**
     * Test of reset method, of class Random.
     */
    @Test
    public void testReset_Class_long() {
        System.out.println("reset");
        Class<?> c = RandomTest.class;
        long value = 0L;
        org.tros.utils.Random.reset(c, value);
    }

    /**
     * Test of reset method, of class Random.
     */
    @Test
    public void testReset_boolean() {
        System.out.println("reset");
        org.tros.utils.Random.reset(true);
        org.tros.utils.Random.reset(false);
    }

    /**
     * Test of getRandomName method, of class Random.
     */
    @Test
    public void testGetRandomName() {
        System.out.println("getRandomName");
        Class<?> c = RandomTest.class;
        String result = org.tros.utils.Random.getRandomName(c);
        assertNotNull(result);
    }

    /**
     * Test of getPUID method, of class Random.
     */
    @Test
    public void testGetPUID_Class_RandomUuidIncrementType() {
        System.out.println("getPUID");
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
        System.out.println("getPUID");
        Class<?> c = RandomTest.class;
        String result = org.tros.utils.Random.getPUID(c);
        assertNotNull(result);
    }

    /**
     * Test of getPUID method, of class Random.
     */
    @Test
    public void testGetPUID_Class_int() {
        System.out.println("getPUID");
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
        System.out.println("nextBoolean");
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
        System.out.println("nextBoolean");
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
        System.out.println("nextTriState");
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
        System.out.println("nextTriState");
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
        System.out.println("nextDouble");
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
        System.out.println("nextDouble");
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
        System.out.println("nextFloat");
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
        System.out.println("nextFloat");
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
        System.out.println("nextInt");
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
        System.out.println("nextInt");
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
        System.out.println("nextInt");
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
        System.out.println("nextInt");
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
        System.out.println("nextInt");
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
        System.out.println("nextInt");
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
        System.out.println("nextLong");
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
        System.out.println("nextLong");
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
        System.out.println("nextLong");
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
        System.out.println("nextLong");
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
     * Test of nextLong method, of class Random.
     */
    @Test
    public void testNextLong_3args() {
        System.out.println("nextLong");
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
        System.out.println("nextLong");
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
        System.out.println("nextGaussian");
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

//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_Collection_int() {
//        System.out.println("getRandom");
//        Collection expResult = null;
//        Collection result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_3args_1() {
//        System.out.println("getRandom");
//        Collection expResult = null;
//        Collection result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_Collection_GenericType() {
//        System.out.println("getRandom");
//        Object expResult = null;
//        Object result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_3args_2() {
//        System.out.println("getRandom");
//        Object expResult = null;
//        Object result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
////    /**
////     * Test of getRandomNotInList method, of class Random.
////     */
////    @Test
////    public void testGetRandomNotInList_Collection_Collection() {
////        System.out.println("getRandomNotInList");
////        Object expResult = null;
////        Object result = org.tros.utils.Random.getRandomNotInList(null);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getRandomNotInList method, of class Random.
////     */
////    @Test
////    public void testGetRandomNotInList_3args() {
////        System.out.println("getRandomNotInList");
////        Object expResult = null;
////        Object result = org.tros.utils.Random.getRandomNotInList(null);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//
//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_Collection() {
//        System.out.println("getRandom");
//        Object expResult = null;
//        Object result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRandom method, of class Random.
//     */
//    @Test
//    public void testGetRandom_Random_Collection() {
//        System.out.println("getRandom");
//        Object expResult = null;
//        Object result = org.tros.utils.Random.getRandom(null);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of isSeeded method, of class Random.
     */
    @Test
    public void testIsSeeded() {
        System.out.println("isSeeded");
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
        System.out.println("setSeeded");
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
        System.out.println("getSeed");
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
        System.out.println("setSeed");
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
        System.out.println("nextDouble");
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
        System.out.println("bernoulli");

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
        System.out.println("bernoulli");
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
        System.out.println("gaussian");
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
        System.out.println("gaussian");
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
        System.out.println("geometric");
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
        System.out.println("poisson");
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
        System.out.println("pareto");
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
        System.out.println("pareto");
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
        System.out.println("cauchy");
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
//        System.out.println("discrete");
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
//        System.out.println("discrete");
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
        System.out.println("exp");
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

//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_ObjectArr() {
//        System.out.println("shuffle");
//        Object[] a = null;
//        org.tros.utils.Random.shuffle(a);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_doubleArr() {
//        System.out.println("shuffle");
//        double[] a = null;
//        org.tros.utils.Random.shuffle(a);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_intArr() {
//        System.out.println("shuffle");
//        int[] a = null;
//        org.tros.utils.Random.shuffle(a);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_3args_1() {
//        System.out.println("shuffle");
//        Object[] a = null;
//        int lo = 0;
//        int hi = 0;
//        org.tros.utils.Random.shuffle(a, lo, hi);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_3args_2() {
//        System.out.println("shuffle");
//        double[] a = null;
//        int lo = 0;
//        int hi = 0;
//        org.tros.utils.Random.shuffle(a, lo, hi);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class Random.
//     */
//    @Test
//    public void testShuffle_3args_3() {
//        System.out.println("shuffle");
//        int[] a = null;
//        int lo = 0;
//        int hi = 0;
//        org.tros.utils.Random.shuffle(a, lo, hi);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
