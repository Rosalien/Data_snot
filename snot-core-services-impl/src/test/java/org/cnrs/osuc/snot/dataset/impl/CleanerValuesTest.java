/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.CleanerValues;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author tcherniatinsky
 */
public class CleanerValuesTest {
    private static final String STRING_1 = "string 1";
    private static final String STRING_2 = "string 2";
    private static final String[] stringArray = new String[]{STRING_1, STRING_2};
    
    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }
    CleanerValues instance;

    /**
     *
     */
    public CleanerValuesTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of currentToken method, of class CleanerValues.
     */
    @Test
    public void testCurrentToken() {
        instance = new CleanerValues(stringArray);
        String expResult = STRING_1;
        String result = instance.currentToken();
        assertEquals(expResult, result);
    }

    /**
     * Test of currentTokenIndex method, of class CleanerValues.
     */
    @Test
    public void testCurrentTokenIndex() {
        instance = new CleanerValues(stringArray);
        int result = instance.currentTokenIndex();
        assertTrue(0 == result);
    }

    /**
     * Test of nextToken method, of class CleanerValues.
     */
    @Test
    public void testNextToken() {
        instance = new CleanerValues(stringArray);
        String result = instance.nextToken();
        assertEquals(STRING_1, result);
        assertTrue(1 == instance.currentTokenIndex());
        assertEquals(STRING_2, instance.currentToken());
    }
    
}
