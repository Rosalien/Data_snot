/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;


import org.cnrs.osuc.snot.synthesis.impl.jfreechart.MyTimeSeries;
import org.cnrs.osuc.snot.synthesis.impl.jfreechart.AbstractOrdonnee;
import org.cnrs.osuc.snot.synthesis.impl.jfreechart.ChartFabrique;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author tcherniatinsky
 */
@Ignore
public class ChartFabriqueTest {
    
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

    /**
     *
     */
    public ChartFabriqueTest() {
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
     * Test of buildChartTimeRepartition method, of class ChartFabrique.
     */
    @Test
    public void testBuildChartTimeRepartition() {
        XYDataset dataset = null;
        ChartFabrique instance = new ChartFabrique();
        JFreeChart expResult = null;
        JFreeChart result = instance.buildChartTimeRepartition(dataset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildChartValuesVariable method, of class ChartFabrique.
     */
    @Test
    public void testBuildChartValuesVariable() {
        Locale locale = null;
        XYDataset dataset = null;
        String variableName = "";
        String uniteName = "";
        ChartFabrique instance = new ChartFabrique();
        JFreeChart expResult = null;
        JFreeChart result = instance.buildChartValuesVariable(locale, dataset, variableName, uniteName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildChartTimeRepartitionParProfondeur method, of class ChartFabrique.
     */
    @Test
    public void testBuildChartTimeRepartitionParProfondeur() {
        Locale locale = null;
        List<MyTimeSeries> timeSeries = null;
        XYLineAndShapeRenderer renderer = null;
        List<LocalDateTime> minMax = null;
        List<AbstractOrdonnee> ordonnees = null;
        ChartFabrique instance = new ChartFabrique();
        JFreeChart expResult = null;
        //JFreeChart result = instance.buildChartTimeRepartitionParProfondeur(locale, timeSeries, renderer, minMax, ordonnees);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
