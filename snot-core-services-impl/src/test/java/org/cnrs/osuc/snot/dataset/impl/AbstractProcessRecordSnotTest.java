/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.CSVParser;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;

/**
 *
 * @author ptcherniati
 */
public class AbstractProcessRecordSnotTest {

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

    MockUtils m = MockUtils.getInstance();

    /**
     *
     */
    protected List<Column> columns = new LinkedList();
    AbstractProcessRecordSnot instance;

    /**
     *
     */
    protected ErrorsReport errorsReport = new ErrorsReport();

    /**
     *
     */
    @Mock
    protected IRequestProperties requestProperties;

    /**
     *
     */
    @Mock
    protected DatasetDescriptor datasetDescriptor;

    /**
     *
     */
    @Mock
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

    /**
     *
     */
    @Mock(name = "variable4")
    protected VariableSnot variable4;

    /**
     *
     */
    @Mock(name = "variable5")
    protected VariableSnot variable5;

    /**
     *
     */
    @Mock(name = "variable6")
    protected VariableSnot variable6;

    /**
     *
     */
    @Mock(name = "variable7")
    protected VariableSnot variable7;

    /**
     *
     */
    @Mock(name = "rnVariable4")
    protected RealNode rnVariable4;

    /**
     *
     */
    @Mock(name = "rnVariable5")
    protected RealNode rnVariable5;

    /**
     *
     */
    @Mock(name = "rnVariable6")
    protected RealNode rnVariable6;

    /**
     *
     */
    @Mock(name = "rnVariable7")
    protected RealNode rnVariable7;

    /**
     *
     */
    @Mock(name = "columnDate")
    protected Column columnDate;

    /**
     *
     */
    @Mock(name = "columnTime")
    protected Column columnTime;

    /**
     *
     */
    @Mock(name = "columnQualityClass")
    protected Column columnQualityClass;

    /**
     *
     */
    @Mock(name = "columnVariable")
    protected Column columnVariable;

    /**
     *
     */
    @Mock(name = "columnListQualitativeValue")
    protected Column columnListQualitativeValue;

    /**
     *
     */
    @Mock(name = "columnQualitativeValue1")
    protected Column columnQualitativeValue1;

    /**
     *
     */
    @Mock(name = "columnQualitativeValue2")
    protected Column columnQualitativeValue2;

    /**
     *
     */
    @Mock(name = "columnQualityClassGeneric")
    protected Column columnQualityClassGeneric;

    /**
     *
     */
    @Mock(name = "columnStar")
    protected Column columnStar;

    /**
     *
     */
    protected Map<VariableSnot, RealNode> dvus = new HashMap();

    /**
     *
     */
    public AbstractProcessRecordSnotTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.instance = new AbstractProcessRecordSnot();
        this.instance.setVariableDAO(this.m.variableDAO);
        this.instance.setLocalizationManager(MockUtils.localizationManager);
        this.instance.setDatatypeVariableUniteSnotDAO(this.datatypeVariableUniteSnotDAO);
        this.instance.setTraitementDAO(this.m.traitementDAO);
        this.datasetDescriptor = this.m.datasetDescriptor;
        initColumns();
    }

    /**
     *
     */
    protected void initColumns() {
        when(columnDate.getName()).thenReturn("colonne1");
        when(columnTime.getName()).thenReturn("colonne2");
        when(columnQualityClass.getName()).thenReturn("colonne3");
        when(columnVariable.getName()).thenReturn("colonne4");
        when(columnVariable.isVariable()).thenReturn(true);
        when(columnListQualitativeValue.getName()).thenReturn("colonne5");
        when(columnQualitativeValue1.getName()).thenReturn("colonne6");
        when(columnQualitativeValue2.getName()).thenReturn("colonne7");
        when(columnQualityClassGeneric.getName()).thenReturn("colonne8");
        when(columnStar.getName()).thenReturn(Constantes.CST_STAR);
        this.columns.add(columnDate);
        this.columns.add(columnTime);
        this.columns.add(columnQualityClass);
        this.columns.add(columnVariable);
        this.columns.add(columnListQualitativeValue);
        this.columns.add(columnQualitativeValue1);
        this.columns.add(columnQualitativeValue2);
        this.columns.add(columnQualityClassGeneric);
        when(columnDate.getFlagType()).thenReturn(Constantes.PROPERTY_CST_DATE_TYPE);
        when(columnDate.getValueType()).thenReturn(Constantes.PROPERTY_CST_DATE_TYPE);
        when(columnDate.getFormatType()).thenReturn(DateUtil.DD_MM_YYYY);
        when(columnTime.getFlagType()).thenReturn(Constantes.PROPERTY_CST_TIME_TYPE);
        when(columnTime.getValueType()).thenReturn(Constantes.PROPERTY_CST_TIME_TYPE);
        when(columnTime.getFormatType()).thenReturn(DateUtil.HH_MM);
        when(columnQualityClass.getFlagType()).thenReturn(Constantes.PROPERTY_CST_QUALITY_CLASS_TYPE);
        when(columnVariable.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        when(columnListQualitativeValue.getFlagType()).thenReturn(Constantes.PROPERTY_CST_LIST_VALEURS_QUALITATIVES_TYPE);
        when(columnQualitativeValue1.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VALEUR_QUALITATIVE_TYPE);
        when(columnQualitativeValue2.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VALEUR_QUALITATIVE_TYPE);
        when(columnQualityClassGeneric.getFlagType()).thenReturn(Constantes.PROPERTY_CST_QUALITY_CLASS_GENERIC);
        Mockito.when(columnDate.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnTime.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnQualityClass.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnVariable.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnListQualitativeValue.isFlag()).thenReturn(Boolean.FALSE);
        Mockito.when(columnQualitativeValue1.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnQualitativeValue2.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(columnQualityClassGeneric.isFlag()).thenReturn(Boolean.TRUE);
        Mockito.when(this.datasetDescriptor.getColumns()).thenReturn(this.columns);
        when(variable4.getCode()).thenReturn("colonne4");
        when(variable5.getCode()).thenReturn("colonne5");
        when(variable6.getCode()).thenReturn("colonne6");
        when(variable7.getCode()).thenReturn("colonne7");
        dvus.put(variable4, rnVariable4);
        dvus.put(variable5, rnVariable5);
        dvus.put(variable6, rnVariable6);
        dvus.put(variable7, rnVariable7);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of buildVariablesHeaderAndSkipHeader method, of class
     * AbstractProcessRecordSnot.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testBuildVariablesHeaderAndSkipHeader() throws Exception {
        String text = "un text";
        CSVParser parser = new CSVParser(new StringReader(text));
        parser.changeDelimiter(';');
        Mockito.when(this.m.column.getFlagType()).thenReturn(text);
        doReturn(dvus).when(datatypeVariableUniteSnotDAO).getRealNodesVariables(m.datatypeRealNode);
        List<RealNode> result = this.instance.buildVariablesHeaderAndSkipHeader(parser, this.datasetDescriptor, m.datatypeRealNode);
        Assert.assertTrue(result.size() == 3);
        assertTrue(result.contains(rnVariable4));
        assertFalse(result.contains(rnVariable5));
        assertTrue(result.contains(rnVariable6));
        assertTrue(result.contains(rnVariable7));
        verify(columnQualityClassGeneric).isFlag();
    }

    /**
     * Test of getDateTime method, of class AbstractProcessRecordSnot.
     */
    @Test
    public void testGetDateTime() {
        String[] values = new String[]{m.DATE_DEBUT, "2:45"};
        ErrorsReport errorsReport = new ErrorsReport();
        Mockito.when(datasetDescriptor.getColumns()).thenReturn(columns);
        LocalDateTime expResult = null;
        //extractio nde la date
        LocalDate result = instance.getDate(datasetDescriptor, values, 4L, errorsReport);
        assertEquals(m.dateDebut.toLocalDate(), result);
        assertFalse(errorsReport.hasErrors());
        //Extraction de l'heure'
        LocalTime result2 = instance.getTime(datasetDescriptor, values, 4L, errorsReport);
        assertEquals("02:45", DateUtil.getUTCDateTextFromLocalDateTime(result2, DateUtil.HH_MM));
        assertFalse(errorsReport.hasErrors());
        //erreur de format
        values = new String[]{m.DATE_DEBUT, "midi"};
        result2 = instance.getTime(datasetDescriptor, values, 4L, errorsReport);
        assertTrue(errorsReport.hasErrors());
        assertEquals("-\"midi\" n'est pas un format d'heure valide à la ligne 4 colonne 2 (time). L'heure doit-être au format \"HH:mm\"\n", errorsReport.getErrorsMessages());

    }
}
