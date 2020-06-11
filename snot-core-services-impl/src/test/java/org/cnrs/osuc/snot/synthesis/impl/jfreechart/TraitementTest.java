/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.Traitement;
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
public class TraitementTest {

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
    Traitement instance;
    Traitement traitement1;
    Traitement traitement2;
    
    /**
     *
     */
    public TraitementTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        instance = new Traitement();
        traitement1 = new  Traitement(1, "traitement 1");
        traitement2 = new  Traitement(2, "traitement 2");
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getOrdonnee method, of class Traitement.
     */
    @Test
    public void testGetOrdonnee() {
        instance.setOrdonnee(15);
        assertTrue(15==instance.getOrdonnee());
    }

    /**
     * Test of getSonNom method, of class Traitement.
     */
    @Test
    public void testGetSonNom() {
        instance.setSonNom("nom");
        assertEquals("nom", instance.getSonNom());
    }

    /**
     * Test of comparer method, of class Traitement.
     */
    @Test
    public void testComparer() {
        assertTrue(-1==traitement1.comparer(traitement2));
        assertTrue(0==traitement2.comparer(traitement2));
        assertTrue(0==traitement1.comparer(traitement1));
        assertTrue(1==traitement2.comparer(traitement1));
    }

    /**
     * Test of getSonOrdonnee method, of class Traitement.
     */
    @Test
    public void testGetSonOrdonnee() {
        assertTrue(1==traitement1.getSonOrdonnee());
        assertTrue(2==traitement2.getSonOrdonnee());
    }

    /**
     * Test of compareTo method, of class Traitement.
     */
    @Test
    public void testCompareTo() {
        assertTrue(-1==traitement1.compareTo(traitement2));
        assertTrue(0==traitement2.compareTo(traitement2));
        assertTrue(0==traitement1.compareTo(traitement1));
        assertTrue(1==traitement2.compareTo(traitement1));
    }

    /**
     * Test of uneOrdonneeVaut method, of class Traitement.
     */
    @Test
    public void testUneOrdonneeVaut() {
        assertEquals(Boolean.FALSE, traitement1.uneOrdonneeVaut("traitement 2"));
        assertEquals(Boolean.TRUE, traitement1.uneOrdonneeVaut("traitement 1"));
        assertEquals(Boolean.TRUE, traitement2.uneOrdonneeVaut("traitement 2"));
        assertEquals(Boolean.FALSE, traitement2.uneOrdonneeVaut("traitement 1"));
    }

    /**
     * Test of getOrdonneeReelle method, of class Traitement.
     */
    @Test
    public void testGetOrdonneeReelle() {
        assertEquals("traitement 1", traitement1.getOrdonneeReelle());
        assertEquals("traitement 2", traitement2.getOrdonneeReelle());
    }
    
}
