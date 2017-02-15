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
        byte[] bs = {(byte) 0x00, (byte) 0xff, (byte) 0x20, (byte) 0x90, 
            (byte) 0x40, (byte) 0x40, (byte) 0x40, (byte) 0x40};
        //byte[] bs = (byte[]) {0, 255, 20, 90, 40, 40, 40, 40};
        Integer[] expResult = {0x00ff2090, 0x40404040};
        Integer[] result = Tools.convertFromBytesToInts(bs);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromHexStringToInts method, of class Tools.
     */
    @Test
    public void testConvertFromHexStringToInts() {
        System.out.println("convertFromHexStringToInts");
        String s = "000000FF0000001";
        Integer[] expResult = {255, 16};
        Integer[] result = Tools.convertFromHexStringToInts(s);
        System.out.println(result[0]);
        System.out.println(result[1]);
        //System.out.println(result[2]);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromIntsToBytes method, of class Tools.
     */
    @Test
    public void testConvertFromIntsToBytes() {
        System.out.println("convertFromIntsToBytes");
        Integer[] ints = {0x00ff2090, 0x40404040};
        byte[] expResult = {(byte) 0x00, (byte) 0xff, (byte) 0x20, (byte) 0x90, 
            (byte) 0x40, (byte) 0x40, (byte) 0x40, (byte) 0x40};
        byte[] result = Tools.convertFromIntsToBytes(ints);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of convertFromIntsToHexString method, of class Tools.
     */
    @Test
    public void testConvertFromIntsToHexString() {
        System.out.println("convertFromIntsToHexString");
        Integer[] ints = {255, 1, 40807654};
        String expResult = "000000ff00000001026eace6";
        String result = Tools.convertFromIntsToHexString(ints);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
