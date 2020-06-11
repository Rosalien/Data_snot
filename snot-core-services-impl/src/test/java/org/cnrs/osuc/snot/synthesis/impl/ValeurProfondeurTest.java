/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl;

import org.cnrs.osuc.snot.synthesis.impl.ValeurProfondeur;
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
public class ValeurProfondeurTest {

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
    ValeurProfondeur instance;

    /**
     *
     */
    public ValeurProfondeurTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        instance=new ValeurProfondeur();
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getRepetition method, of class ValeurProfondeur.
     */
    @Test
    public void testGetRepetition() {
        instance.setRepetition(12);
        int result = instance.getRepetition();
        assertTrue(12==result);
    }

    /**
     * Test of getValeur method, of class ValeurProfondeur.
     */
    @Test
    public void testGetValeur() {
        instance.setValeur(13.2F);
        float result = instance.getValeur();
        assertEquals(13.2F, result, 0.0);
        assertTrue(13.2F==result);
    }

    /**
     * Test of getProfondeur method, of class ValeurProfondeur.
     */
    @Test
    public void testGetProfondeur() {
        instance.setProfondeur(15);
        assertTrue(15==instance.getProfondeur());
    }
    
}
