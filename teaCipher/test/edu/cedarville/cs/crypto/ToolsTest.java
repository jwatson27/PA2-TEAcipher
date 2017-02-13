/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cedarville.cs.crypto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jwatson27
 */
public class ToolsTest {
    
    public ToolsTest() {
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
     * Test of convertFromBytesToInts method, of class Tools.
     */
    @Test
    public void testConvertFromBytesToInts() {
        System.out.println("convertFromBytesToInts");
        byte[] bs = null;
        Integer[] expResult = null;
        Integer[] result = Tools.convertFromBytesToInts(bs);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromHexStringToInts method, of class Tools.
     */
    @Test
    public void testConvertFromHexStringToInts() {
        System.out.println("convertFromHexStringToInts");
        String s = "";
        Integer[] expResult = null;
        Integer[] result = Tools.convertFromHexStringToInts(s);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromIntsToBytes method, of class Tools.
     */
    @Test
    public void testConvertFromIntsToBytes() {
        System.out.println("convertFromIntsToBytes");
        Integer[] ints = null;
        byte[] expResult = null;
        byte[] result = Tools.convertFromIntsToBytes(ints);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromIntsToHexString method, of class Tools.
     */
    @Test
    public void testConvertFromIntsToHexString() {
        System.out.println("convertFromIntsToHexString");
        Integer[] ints = null;
        String expResult = "";
        String result = Tools.convertFromIntsToHexString(ints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
