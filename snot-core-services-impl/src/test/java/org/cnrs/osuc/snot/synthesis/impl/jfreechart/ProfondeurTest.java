/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.Profondeur;
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
public class ProfondeurTest {

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

    Profondeur instance;
    Profondeur profondeur1;
    Profondeur profondeur2;
    
    /**
     *
     */
    public ProfondeurTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        instance = new Profondeur();
        profondeur1 = new Profondeur(12, 15);
        profondeur2 = new Profondeur(13, 17);
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getProfondeurReelle method, of class Profondeur.
     */
    @Test
    public void testGetProfondeurReelle() {
        instance.setProfondeurReelle(12);
        assertTrue(12 == instance.getProfondeurReelle());
    }

    /**
     * Test of getProfondeurFictive method, of class Profondeur.
     */
    @Test
    public void testGetProfondeurFictive() {
        instance.setProfondeurFictive(12);
        assertTrue(12 == instance.getProfondeurFictive());
    }

    /**
     * Test of comparer method, of class Profondeur.
     */
    @Test
    public void testComparer() {
        assertEquals(-1, profondeur1.comparer(profondeur2));
        assertEquals(0, profondeur1.comparer(profondeur1));
        assertEquals(1, profondeur2.comparer(profondeur1));
    }

    /**
     * Test of getSonOrdonnee method, of class Profondeur.
     */
    @Test
    public void testGetSonOrdonnee() {
        instance.setProfondeurFictive(12);
        assertTrue(12 == instance.getSonOrdonnee());
    }

    /**
     * Test of compareTo method, of class Profondeur.
     */
    @Test
    public void testCompareTo() {
        assertEquals(-1, profondeur1.compareTo(profondeur2));
        assertEquals(0, profondeur1.compareTo(profondeur1));
        assertEquals(1, profondeur2.compareTo(profondeur1));
    }

    /**
     * Test of uneOrdonneeVaut method, of class Profondeur.
     */
    @Test
    public void testUneOrdonneeVaut() {
        assertTrue(profondeur1.uneOrdonneeVaut("12"));
        assertTrue(profondeur2.uneOrdonneeVaut("13"));
    }

    /**
     * Test of getOrdonneeReelle method, of class Profondeur.
     */
    @Test
    public void testGetOrdonneeReelle() {
        assertEquals("12",profondeur1.getOrdonneeReelle());
        assertEquals("13",profondeur2.getOrdonneeReelle());
    }
    
}
