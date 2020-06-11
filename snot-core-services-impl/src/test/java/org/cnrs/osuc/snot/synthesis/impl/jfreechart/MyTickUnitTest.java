/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.MyTickUnit;
import org.cnrs.osuc.snot.synthesis.impl.jfreechart.AbstractOrdonnee;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class MyTickUnitTest {

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

    MyTickUnit instance;
    @Mock
    List<AbstractOrdonnee> ordonnees;
    @Mock
    AbstractOrdonnee ordonnee1;
    @Mock
    AbstractOrdonnee ordonnee2;
    @Mock
    AbstractOrdonnee ordonnee3;
    List<AbstractOrdonnee> ordonneesList;

    /**
     *
     */
    public MyTickUnitTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new MyTickUnit(12.3D);
        ordonneesList = Arrays.asList(new AbstractOrdonnee[]{ordonnee1, ordonnee2, ordonnee3});
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getOrdonnees method, of class MyTickUnit.
     */
    @Test
    public void testGetOrdonnees() {
        instance.setOrdonnees(ordonnees);
        assertEquals(ordonnees, instance.getOrdonnees());
    }

    /**
     * Test of valueToString method, of class MyTickUnit.
     */
    @Test
    public void testValueToString() {
        Mockito.when(ordonnee3.getSonOrdonnee()).thenReturn(12);
        instance.setOrdonnees(ordonneesList);
        String result = instance.valueToString(12);
        Mockito.verify(ordonnee1, Mockito.never()).getOrdonneeReelle();
        Mockito.verify(ordonnee2, Mockito.never()).getOrdonneeReelle();
        Mockito.verify(ordonnee3).getOrdonneeReelle();
    }

}
