/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.MyTimeSeries;
import java.awt.Color;
import org.jfree.data.time.TimeSeries;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author tcherniatinsky
 */
public class MyTimeSeriesTest {

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
    MyTimeSeries instance;

    /**
     *
     */
    public MyTimeSeriesTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        instance = new MyTimeSeries();
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getTimeSeries method, of class MyTimeSeries.
     */
    @Test
    public void testGetCouleur() {
        Color c = mock(Color.class);
        instance.setCouleur(c);
        assertEquals(c, instance.getCouleur());
    }

    /**
     * Test of getCouleur method, of class MyTimeSeries.
     */
    @Test
    public void testGetTimeSeries() {
        TimeSeries ts = mock(TimeSeries.class);
        instance.setTimeSeries(ts);
        assertEquals(ts, instance.getTimeSeries());
    }
    
}
