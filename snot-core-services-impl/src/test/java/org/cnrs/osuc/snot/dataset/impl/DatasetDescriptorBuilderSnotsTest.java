/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.DatasetDescriptorBuilderSnots;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * 
 * @author ptcherniati
 */
public class DatasetDescriptorBuilderSnotsTest {

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {}

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {}

    /**
     *
     */
    public DatasetDescriptorBuilderSnotsTest() {}

    /**
     *
     */
    @Before
    public void setUp() {}

    /**
     *
     */
    @After
    public void tearDown() {}

    /**
     * Test of buildDescriptor method, of class DatasetDescriptorBuilderSnots.
     * @throws java.lang.Exception
     */
    @Test
    public void testBuildDescriptor_Class_File() throws Exception {
        InputStream datasetDescriptorResource = DatasetDescriptorBuilderSnots.class.getClassLoader().getResourceAsStream("org/cnrs/osuc/snot/dataset/impl/dataset-descriptor-test.xml");
        DatasetDescriptorTest result = DatasetDescriptorBuilderSnots.buildDescriptor(DatasetDescriptorTest.class, datasetDescriptorResource);
        assertEquals("test", result.getName());
        assertEquals("10", result.getEntete());
        assertEquals("freq", result.getFrequence());
        assertEquals("sites", result.getSites());
        assertTrue(2 == result.getUndefinedColumn());
        assertNull(result.getVariableName());

        assertNotNull("test", result.getColumns());
        assertTrue(2 == result.getColumns().size());
        assertEquals("date", result.getColumns().get(0).getName());
        assertTrue(result.getColumns().get(0).isNullable());
        assertFalse(result.getColumns().get(0).isVariable());
        assertTrue(result.getColumns().get(0).isFlag());
        assertEquals("date", result.getColumns().get(0).getFlagType());
        assertEquals("ref", result.getColumns().get(0).getRefVariableName());
        assertEquals("dd/MM/yyyy", result.getColumns().get(0).getFormatType());
        assertEquals("date", result.getColumns().get(0).getValueType());
        assertEquals("CO2", result.getColumns().get(1).getName());
        assertFalse(result.getColumns().get(1).isNullable());
        assertTrue(result.getColumns().get(1).isVariable());
        assertFalse(result.getColumns().get(1).isFlag());
        assertEquals("variable", result.getColumns().get(1).getFlagType());
        assertEquals(null, result.getColumns().get(1).getRefVariableName());
        assertEquals(null, result.getColumns().get(1).getFormatType());
        assertEquals("float", result.getColumns().get(1).getValueType());
    }

    /**
     * Test of buildDescriptorWithVariableNamesAndUndefinedColumns method, of class DatasetDescriptorBuilderSnots.
     * @throws java.lang.Exception
     */
    @Test
    public void testBuildDescriptorWithVariableNamesAndUndefinedColumns() throws Exception {
        InputStream datasetDescriptorResource = DatasetDescriptorBuilderSnots.class.getClassLoader().getResourceAsStream("org/cnrs/osuc/snot/dataset/impl/dataset-descriptor-test.xml");
        DatasetDescriptorTest result = DatasetDescriptorBuilderSnots.buildDescriptorWithVariableNamesAndUndefinedColumns(DatasetDescriptorTest.class, datasetDescriptorResource);
        assertEquals("test", result.getName());
        assertEquals("10", result.getEntete());
        assertEquals("freq", result.getFrequence());
        assertEquals("sites", result.getSites());
        assertTrue(2 == result.getUndefinedColumn());
        assertEquals("variable_name", result.getVariableName());
        assertEquals("variable_name", result.getVariableName());

        assertNotNull("test", result.getColumns());
        assertTrue(2 == result.getColumns().size());
        assertEquals("date", result.getColumns().get(0).getName());
        assertTrue(result.getColumns().get(0).isNullable());
        assertFalse(result.getColumns().get(0).isVariable());
        assertTrue(result.getColumns().get(0).isFlag());
        assertEquals("date", result.getColumns().get(0).getFlagType());
        assertEquals("ref", result.getColumns().get(0).getRefVariableName());
        assertEquals("dd/MM/yyyy", result.getColumns().get(0).getFormatType());
        assertEquals("date", result.getColumns().get(0).getValueType());
        assertEquals("CO2", result.getColumns().get(1).getName());
        assertFalse(result.getColumns().get(1).isNullable());
        assertTrue(result.getColumns().get(1).isVariable());
        assertFalse(result.getColumns().get(1).isFlag());
        assertEquals("variable", result.getColumns().get(1).getFlagType());
        assertEquals(null, result.getColumns().get(1).getRefVariableName());
        assertEquals(null, result.getColumns().get(1).getFormatType());
        assertEquals("float", result.getColumns().get(1).getValueType());
    }
}
