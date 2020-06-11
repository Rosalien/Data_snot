/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.FabriqueDatasetInfraJournalier;
import java.util.List;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
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
public class FabriqueDatasetInfraJournalierTest {
    
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
    public FabriqueDatasetInfraJournalierTest() {
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
     * Test of createDatasetRepartitionTimeVariable method, of class FabriqueDatasetInfraJournalier.
     */
    @Test
    public void testCreateDatasetRepartitionTimeVariable() {
        List<GenericSynthesisValue> synthesisVariables = null;
        XYLineAndShapeRenderer renderer = null;
        FabriqueDatasetInfraJournalier instance = new FabriqueDatasetInfraJournalier();
        XYDataset expResult = null;
        XYDataset result = instance.createDatasetRepartitionTimeVariable(synthesisVariables, renderer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createDatasetValuesVariable method, of class FabriqueDatasetInfraJournalier.
     */
    @Test
    public void testCreateDatasetValuesVariable() {
        List<GenericSynthesisValue> synthesisValues = null;
        XYLineAndShapeRenderer renderer = null;
        FabriqueDatasetInfraJournalier instance = new FabriqueDatasetInfraJournalier();
        XYDataset expResult = null;
        XYDataset result = instance.createDatasetValuesVariable(synthesisValues, renderer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
