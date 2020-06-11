/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.dataset.impl.RequestProperties;
import org.cnrs.osuc.snot.dataset.impl.GenericTestValues;
import com.Ostermiller.util.CSVParser;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesIntervalValue;
import org.cnrs.osuc.snot.dataset.ITestDuplicates;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;

/**
 *
 * @author ptcherniati
 */
public class GenericTestValuesTest {

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

    MockUtils m;
    @Spy
    GenericTestValues instance = new GenericTestValues();
    @Mock
    DatasetDescriptor datasetDescriptor;
    @Mock
    IDatatypeVariableUniteSnotDAO datatypeUniteVariableDAO;
    @Mock
    IRequestProperties requestProperties;
    @Mock
    SpecificRequestPropertiesTest requestPropertiesIntervalValue;
    @Mock
    Map<String, DatatypeVariableUniteSnot> variablesTypeDonnees;
    @Mock
    ITestDuplicates testDuplicates;
    @Mock
    DatatypeVariableUniteSnot datatypeUniteVariable;
    @Mock
    Column column;
    String datatypeName = "datatype";
    List<Column> columns = new LinkedList();
    @Mock
    Column columnDate;

    @Mock
    Column columntime;

    /**
     *
     */
    public GenericTestValuesTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.m = MockUtils.getInstance();
        this.instance.setDatatypeVariableUniteSnotDAO(this.datatypeUniteVariableDAO);
        Mockito.doReturn(this.testDuplicates).when(this.instance).getTestDuplicates(this.requestProperties);
        Mockito.when(this.datatypeUniteVariableDAO.getAllVariableTypeDonneesByDataType(MockUtils.SITE_CODE, this.datatypeName)).thenReturn(this.variablesTypeDonnees);
        Mockito.when(this.variablesTypeDonnees.containsKey("colonne")).thenReturn(true);
        Mockito.when(this.variablesTypeDonnees.get("colonne")).thenReturn(this.datatypeUniteVariable);
        SNOTRecorder.setStaticLocalizationManager(this.m.localizationManager);
        this.columns.add(this.columnDate);
        this.columns.add(this.columntime);
        Mockito.when(this.columnDate.getFormatType()).thenReturn("dd/MM/yyyy");
        Mockito.when(this.columnDate.getFlagType()).thenReturn(Constantes.PROPERTY_CST_DATE_TYPE);
        Mockito.when(this.columntime.getFormatType()).thenReturn("HH:mm");
        Mockito.when(this.columntime.getFlagType()).thenReturn(Constantes.PROPERTY_CST_TIME_TYPE);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of checkDateTypeValue method, of class GenericTestValues.
     *
     * @throws java.text.ParseException
     */
    @Test
    public void testCheckDateTypeValue() throws ParseException {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 2;
        String value = "25/12/2012";
        Column column = new Column();
        String format = "dd/MM/yyyy";
        column.setFormatType(format);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = null;
        DatasetDescriptor datasetDescriptor = null;
        RequestProperties requestProperties = null;
        LocalDate expResult = DateUtil.readLocalDateFromText(DateUtil.DD_MM_YYYY, value);
        LocalDate result = this.instance.checkDateTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        format = SNOTRecorder.DD_MM_YYYY_HHMMSS_FILE;
        column.setFormatType(format);
        value = "25-12-1965-233015";
        expResult = DateUtil.readLocalDateFromText(SNOTRecorder.DD_MM_YYYY_HHMMSS_FILE, value);
        result = this.instance.checkDateTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "25/12/2012";
        result = this.instance.checkDateTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertTrue(1 == badsFormatsReport.getErrorsMessages().size());
        assertEquals("Msg : :- \"25/12/2012\" n'est pas un format de date valide à la ligne 1 colonne 3 (colonne). La date doit-être au format \"dd-MM-yyyy-HHmmss\" ", badsFormatsReport.getMessages());
    }

    /**
     * Test of checkFloatTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckFloatTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1.04";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Float expResult = 1.04F;
        Float result = instance.checkFloatTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        result = instance.checkFloatTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(Float.parseFloat(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE) == result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur flottante est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        result = instance.checkFloatTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(12L == result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkDoubleTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckDoubleTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1.04";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        Double result = instance.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        result = instance.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(Float.parseFloat(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE) == result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur décimale est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        result = instance.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(12L == result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkIntegerTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckIntegerTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Integer expResult = 1;
        Integer result = instance.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        result = instance.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(Float.parseFloat(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE) == result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur entière est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        result = instance.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(12L == result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkIntervalValue method, of class GenericTestValues.
     */
    @Test
    @Ignore
    public void testCheckIntervalValue() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Err :");
        long lineNumber = 1L;
        int index = 1;
        Column column = new Column();
        column.setName("colonne");
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        GenericTestValues instance = new GenericTestValues();
        Mockito.when(this.datatypeUniteVariable.getMin()).thenReturn(12.0F);
        Mockito.when(this.datatypeUniteVariable.getMax()).thenReturn(13.0F);
        // nominal case
        instance.checkIntervalValue(this.requestProperties, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, 12.2F);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        // nominal error case
        instance.checkIntervalValue(this.requestProperties, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, 14.2F);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Err : :- La valeur attendue à la ligne 1, colonne 2 (colonne) est hors limites: la valeur actuelle est \"14,200000\" elle devrait être comprise entre 12,000000 et 13,000000 ", badsFormatsReport.getMessages());
        // cas of instance of IRequestPropertiesIntervalValue with ok interval
        badsFormatsReport = new BadsFormatsReport("Err :");
        Mockito.when(this.requestPropertiesIntervalValue.getValeursMin()).thenReturn(new String[]{"", "12.0"});
        Mockito.when(this.requestPropertiesIntervalValue.getValeursMax()).thenReturn(new String[]{"", "13.0"});
        Mockito.when(this.requestPropertiesIntervalValue.getValeurMinInFile()).thenReturn(new String[]{"", ""});
        Mockito.when(this.requestPropertiesIntervalValue.getValeurMaxInFile()).thenReturn(new String[]{"", ""});
        instance.checkIntervalValue(this.requestPropertiesIntervalValue, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, 12.2F);
        assertFalse(badsFormatsReport.hasErrors());
        // cas of instance of IRequestPropertiesIntervalValue with null valeurMin
        Mockito.when(this.requestPropertiesIntervalValue.getValeursMin()).thenReturn(new String[]{"", null});
        instance.checkIntervalValue(this.requestPropertiesIntervalValue, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, -9999F);
        assertFalse(badsFormatsReport.hasErrors());
        // cas of instance of IRequestPropertiesIntervalValue with null valeurMinInFile
        Mockito.when(this.requestPropertiesIntervalValue.getValeursMin()).thenReturn(new String[]{"", "13.0"});
        Mockito.when(this.requestPropertiesIntervalValue.getValeurMinInFile()).thenReturn(new String[]{"", null});
        instance.checkIntervalValue(this.requestPropertiesIntervalValue, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, -9999F);
        assertFalse(badsFormatsReport.hasErrors());
        // cas of instance of IRequestPropertiesIntervalValue with ok interval
        badsFormatsReport = new BadsFormatsReport("err :");
        instance.checkIntervalValue(this.requestPropertiesIntervalValue, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, 12.2F);
        assertFalse(badsFormatsReport.hasErrors());
        // null badintervalvalue
        column.setFlagType("other");
        instance.checkIntervalValue(this.requestProperties, this.datasetDescriptor, badsFormatsReport, lineNumber, index, column, this.variablesTypeDonnees, 14.2F);
        assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkOtherTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckOtherTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Err :");
        long lineNumber = 0L;
        int index = 0;
        String value = "value";
        Column column = null;
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = null;
        DatasetDescriptor datasetDescriptor = null;
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        instance.checkOtherTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkTimeTypeValue method, of class GenericTestValues.
     *
     * @throws java.text.ParseException
     */
    @Test
    public void testCheckTimeTypeValue() throws ParseException {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 2;
        String value = "12:34";
        Column column = new Column();
        String format = "HH:mm";
        column.setFormatType(format);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = null;
        DatasetDescriptor datasetDescriptor = null;
        RequestProperties requestProperties = null;
        LocalTime expResult = DateUtil.readLocalTimeFromText(DateUtil.HH_MM, value);
        LocalTime result = this.instance.checkTimeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "2:34";
        expResult = DateUtil.readLocalTimeFromText(DateUtil.HH_MM, "02:34");
        result = this.instance.checkTimeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "midi";
        result = this.instance.checkTimeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertTrue(1 == badsFormatsReport.getErrorsMessages().size());
        assertEquals("Msg : :- \"midi\" n'est pas un format d'heure valide à la ligne 1 colonne 3 (colonne). L'heure doit-être au format \"HH:mm\" ", badsFormatsReport.getMessages());
    }

    /**
     * Test of checkValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckValue() {
        BadsFormatsReport badsFormatsReport = null;
        long lineNumber = 0L;
        int index = 0;
        String value = "";
        Column column = null;
        String[] values = null;
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = null;
        DatasetDescriptor datasetDescriptor = null;
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        instance.checkValue(badsFormatsReport, lineNumber, index, value, column, values, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }

    /**
     * Test of getIntervalDateFromVersion method, of class GenericTestValues.
     */
    @Test
    public void testGetIntervalDateFromVersion_VersionFile() {
        VersionFile versionFile = null;
        GenericTestValues instance = new GenericTestValues();
        IntervalDate expResult = null;
        IntervalDate result = instance.getIntervalDateFromVersion(versionFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of setDatatypeVariableUniteSnotDAO method, of class
     * GenericTestValues.
     */
    @Test
    public void testSetDatatypeVariableUniteSnotDAO() {
        IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO = null;
        GenericTestValues instance = new GenericTestValues();
        instance.setDatatypeVariableUniteSnotDAO(datatypeVariableUniteSnotDAO);
    }

    /**
     * Test of testDuplicatesLineWithpublishedVersion method, of class
     * GenericTestValues.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testTestDuplicatesLineWithpublishedVersion() throws Exception {
        long startline = 0L;
        CSVParser parser = null;
        VersionFile versionFile = null;
        IRequestProperties requestProperties = null;
        String encoding = "";
        BadsFormatsReport badsFormatsReport = null;
        DatasetDescriptor datasetDescriptor = null;
        String datatypeName = "";
        GenericTestValues instance = new GenericTestValues();
        instance.testDuplicatesLineWithpublishedVersion(startline, parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor, datatypeName);
    }

    /**
     * Test of testValues method, of class GenericTestValues.
     *
     * @throws java.lang.Exception
     */
    @Test
    @Ignore
    public void testTestValues() throws Exception {
        long startline = 0L;
        CSVParser parser = null;
        VersionFile versionFile = null;
        IRequestProperties requestProperties = null;
        String encoding = "";
        BadsFormatsReport badsFormatsReport = null;
        DatasetDescriptor datasetDescriptor = null;
        String datatypeName = "";
        GenericTestValues instance = new GenericTestValues();
        instance.testValues(startline, parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor, datatypeName);
    }

    /**
     * Test of verifieLine method, of class GenericTestValues.
     *
     * @throws java.text.ParseException
     * @throws org.inra.ecoinfo.utils.exceptions.BadExpectedValueException
     */
    @Test
    public void testVerifieLine() throws ParseException, BadExpectedValueException {
        ArgumentCaptor<LocalDateTime> dateArg = ArgumentCaptor.forClass(LocalDateTime.class);
        Mockito.when(this.datasetDescriptor.getColumns()).thenReturn(this.columns);
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 2;
        String dateValue = "25/12/2012";
        String timeValue = "23:00";
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = null;
        values = new String[]{dateValue, timeValue};
        Mockito.when(this.requestProperties.getFrequence()).thenReturn(Constantes.JOURNALIER);
        this.instance.verifieLine(lineNumber, this.m.versionFile, this.requestProperties, badsFormatsReport, this.datasetDescriptor, this.datatypeName, values);
        assertFalse(badsFormatsReport.hasErrors());
        Mockito.verify(this.requestProperties, Mockito.times(1)).addDate(dateArg.capture(), Matchers.eq(1L));
        assertEquals("2012-12-25T00:00", dateArg.getValue().toString());
        Mockito.when(this.requestProperties.getFrequence()).thenReturn(Constantes.SEMI_HORAIRE);
        this.instance.verifieLine(lineNumber, this.m.versionFile, this.requestProperties, badsFormatsReport, this.datasetDescriptor, this.datatypeName, values);
        assertFalse(badsFormatsReport.hasErrors());
        Mockito.verify(this.requestProperties, Mockito.times(2)).addDate(dateArg.capture(), Matchers.eq(1L));
        assertEquals("2012-12-25T00:00", dateArg.getValue().toString());
    }

    /**
     * Test of getIntervalDateFromVersion method, of class GenericTestValues.
     */
    @Test
    public void testGetIntervalDateFromVersion() {
        IntervalDate result = instance.getIntervalDateFromVersion(m.versionFile);
        assertEquals("du 01/01/2012;00:00 au 31/12/2012;00:00", result.toString());
    }

    /**
     * Test of getTestDuplicates method, of class GenericTestValues. /** Test of
     * getTestDuplicates method, of class GenericTestValues.
     */
    /**
     * Test of checkNumeroChambreTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckNumeroChambreTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "13";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        instance.checkNumeroChambreTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        instance.checkNumeroChambreTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur entière est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        instance.checkNumeroChambreTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkDureeMesureTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckDureeMesureTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "12";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        instance.checkDureeMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        instance.checkDureeMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur entière est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        instance.checkDureeMesureTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkEcartTypeTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckEcartTypeTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1.04";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        instance.checkEcartTypeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        instance.checkEcartTypeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur décimale est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        instance.checkEcartTypeTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkNbMesureTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckNbMesureTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1.04";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        instance.checkNbMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        instance.checkNbMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur décimale est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        instance.checkNbMesureTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of checkQualityCodeTypeValue method, of class GenericTestValues.
     */
    @Test
    public void testCheckQualityCodeTypeValue() {
        String[] values = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Msg :");
        long lineNumber = 1L;
        int index = 1;
        String value = "1.04";
        Column column = new Column();
        column.setFlagType(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        column.setName("colonne");
        Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees = new HashMap<>();
        variablesTypesDonnees.put("colonne", null);
        IRequestProperties requestProperties = null;
        GenericTestValues instance = new GenericTestValues();
        Double expResult = 1.04D;
        instance.checkQualityCodeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
        value = "cinquante";
        instance.checkQualityCodeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        assertEquals("Msg : :- Une valeur décimale est attendue à la ligne 1, colonne 2 (colonne): la valeur actuelle est \"cinquante\" ", badsFormatsReport.getMessages());
        column.setFlagType("type");
        badsFormatsReport = new BadsFormatsReport("Msg :");
        instance.checkQualityCodeTypeValue(values, badsFormatsReport, lineNumber, index, "12", column, variablesTypesDonnees, this.datasetDescriptor, requestProperties);
        Assert.assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     *
     */
    public abstract class SpecificRequestPropertiesTest implements IRequestPropertiesIntervalValue, IRequestProperties {
    }
}
