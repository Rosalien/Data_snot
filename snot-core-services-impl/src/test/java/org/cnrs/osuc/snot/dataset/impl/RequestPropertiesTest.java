/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.RequestProperties;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.cnrs.osuc.snot.dataset.ITestDuplicates;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;


/**
 * 
 * @author ptcherniati
 */
public class RequestPropertiesTest {

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

    @Mock
    ITestDuplicates testDuplicates;
    @Spy
    RequestProperties instance;
    MockUtils m = MockUtils.getInstance();

    /**
     *
     */
    public RequestPropertiesTest() {}

    /**
     *
     */
    @Before
    public void setUp() {this.instance = new RequestProperties();
        this.instance.setTestDuplicateName("testDuplicates");
        this.instance.setDatasetConfiguration(this.m.datasetConfiguration);
        this.instance.setFrequence("journalier");
}

    /**
     *
     */
    @After
    public void tearDown() {}

    /**
     * Test of addDate method, of class RequestProperties.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddDate_Date_int() throws Exception {
        LocalDateTime date1 = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM, "01/01/2010 00:30");
        LocalDateTime date2 = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM, "02/01/2010 00:30");
        LocalDateTime date3 = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM, "03/01/2010 00:30");
        LocalDateTime date4 = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM, "04/01/2010 00:30");
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[0]);
        this.instance.setDateDeDebut(this.m.dateDebutComplete);
        this.instance.setDateDeFin(this.m.dateFinComplete);
        // nominal case
        this.instance.addDate(this.m.dateDebut, 1);
        try {
            this.instance.addDate(date1, 1);
            fail("date is not valid");
        }catch (BadExpectedValueException e) {
            assertEquals("Ligne 1 : la valeur de la date 01/01/2010 00:30 est hors limite pour la période du 01/01/2012 au 31/12/2012", e.getMessage());
        }
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[1]);
        try {
            this.instance.addDate(date2, 2);
            fail("date is not valid");
        }catch (BadExpectedValueException e) {
            assertEquals("Ligne 2 : la valeur de la date 02/01/2010 est hors limite pour la période du 01/01/2012 au 31/12/2012", e.getMessage());
        }
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[2]);
        try {
            this.instance.addDate(date3, 3);
            fail("date is not valid");
        }catch (BadExpectedValueException e) {
            assertEquals("Ligne 3 : la valeur de la date 01/2010 est hors limite pour la période du 01/2012 au 12/2012", e.getMessage());
        }
        this.instance.setFrequence(null);
        this.instance.addDate(date4, 4);
        assertTrue(17_568 == this.instance.getDates().size());
        assertTrue(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(this.m.dateDebut, Constantes.SORTEABLE_DATE_FORMAT)));
        assertFalse(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(this.m.dateFin, Constantes.SORTEABLE_DATE_FORMAT)));
        assertNull(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(date1, Constantes.SORTEABLE_DATE_FORMAT)));
        assertNull(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(date2, Constantes.SORTEABLE_DATE_FORMAT)));
        assertNull(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(date3, Constantes.SORTEABLE_DATE_FORMAT)));
        assertNull(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(date4, Constantes.SORTEABLE_DATE_FORMAT)));
    }

    /**
     * Test of getCommentaire method, of class RequestProperties.
     */
    @Test
    public void testGetCommentaire() {String expResult = "comment";
        this.instance.setCommentaire(expResult);
        String result = this.instance.getCommentaire();
        assertEquals(expResult, result);
}

    /**
     * Test of getDatedeDebut method, of class RequestProperties.
     */
    @Test
    public void testGetDatedeDebut() {
        this.instance.setDateDeDebut(this.m.dateDebut);
        LocalDateTime result = this.instance.getDatedeDebut();
        assertEquals(this.m.dateDebut, result);

    }

    /**
     * Test of getDateDeFin method, of class RequestProperties.
     */
    @Test
    public void testGetDateDeFin() {
        this.instance.setDateDeFin(this.m.dateFin);
        LocalDateTime result = this.instance.getDateDeFin();
        assertEquals(this.m.dateFin,result);
    }

    /**
     * Test of getDateFormat method, of class RequestProperties.
     */
    @Test
    public void testGetDateFormat() {
        this.instance.setDateFormat(DateUtil.DD_MM_YYYY);
        String result = this.instance.getDateFormat();
        assertEquals(DateUtil.DD_MM_YYYY, result);
    }

    /**
     * Test of getFrequence method, of class RequestProperties.
     */
    @Test
    public void testGetFrequence() {
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[0]);
        String result = this.instance.getFrequence();
        assertEquals(Constantes.FREQUENCE_NAME[0], result);
    }

    /**
     * Test of getSite method, of class RequestProperties.
     */
    @Test
    public void testGetSite() {
        this.instance.setSite(this.m.site);
        SiteSnot result = this.instance.getSite();
        assertEquals(this.m.site, result);
    }

    /**
     * Test of initDate method, of class RequestProperties.
     */
    @Test
    public void testInitDate() {
        this.instance.getDates().put(DateUtil.getUTCDateTextFromLocalDateTime(m.dateDebut, Constantes.SORTEABLE_DATE_FORMAT), Boolean.TRUE);
        assertTrue(1 == this.instance.getDates().size());
        this.instance.initDate();
        assertTrue(this.instance.getDates().isEmpty());
    }

    /**
     * Test of setDateDeDebut method, of class RequestProperties.
     */
    @Test
    public void testSetDateDeDebut() {
        this.instance.getDates().put(DateUtil.getUTCDateTextFromLocalDateTime(m.dateFin, Constantes.SORTEABLE_DATE_FORMAT), Boolean.TRUE);
        assertFalse(this.instance.getDates().isEmpty());
        this.instance.setDateDeDebut(this.m.dateDebut);
        assertEquals(this.m.dateDebut, this.instance.getDatedeDebut());
        assertTrue(this.instance.getDates().isEmpty());
    }

    /**
     * Test of setDateDeFin method, of class RequestProperties.
     */
    @Test
    public void testSetDateDeFin() {
        // just setting
        this.instance.setFrequence("journalier");
        this.instance.getDates().put(DateUtil.getUTCDateTextFromLocalDateTime(m.dateFin, Constantes.SORTEABLE_DATE_FORMAT), Boolean.TRUE);
        assertFalse(this.instance.getDates().isEmpty());
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(this.instance.getDates().isEmpty());
        assertEquals(this.m.dateFin, this.instance.getDateDeFin());
        // no frequence
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertFalse(this.instance.getDates().get(DateUtil.getUTCDateTextFromLocalDateTime(m.dateDebut, Constantes.SORTEABLE_DATE_FORMAT)));
        // half-hourly frequence
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[0]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(366 * 48 == this.instance.getDates().size());
        // daily frequence
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[1]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(366 == this.instance.getDates().size());
        // montly frequence
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[2]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(12 == this.instance.getDates().size());
    }

    /**
     * Test of testDates method, of class RequestProperties.
     */
    @Test
    public void testTestDates() {
        String[] errors;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Err :");
        // without init
        this.instance.testDates(badsFormatsReport);
        assertFalse(badsFormatsReport.hasErrors());
        // half-hourly frequence
        // half-hourly frequence
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[0]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(366 * 48 == this.instance.getDates().size());
        this.instance.testDates(badsFormatsReport);
        errors = badsFormatsReport.getMessages().split("-");
        assertTrue(366 * 48 + 1 == errors.length);
        assertEquals(" La valeur de la date 01/01/2012 00:00 n'est pas définie pour la période du 01/01/2012 au 31/12/2012 ", errors[1]);
        assertEquals(" La valeur de la date 31/12/2012 23:30 n'est pas définie pour la période du 01/01/2012 au 31/12/2012 ", errors[errors.length - 1]);
        // daily frequence
        badsFormatsReport = new BadsFormatsReport("Err :");
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[1]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(366 == this.instance.getDates().size());
        this.instance.testDates(badsFormatsReport);
        errors = badsFormatsReport.getMessages().split("-");
        assertTrue(366 + 1 == errors.length);
        assertEquals(" La valeur de la date 01/01/2012 n'est pas définie pour la période du 01/01/2012 au 31/12/2012 ", errors[1]);
        assertEquals(" La valeur de la date 31/12/2012 n'est pas définie pour la période du 01/01/2012 au 31/12/2012 ", errors[errors.length - 1]);
        // half-hourly frequence
        badsFormatsReport = new BadsFormatsReport("Err :");
        this.setUp();
        assertTrue(this.instance.getDates().isEmpty());
        this.instance.setFrequence(Constantes.FREQUENCE_NAME[2]);
        this.instance.setDateDeDebut(this.m.dateDebut);
        this.instance.setDateDeFin(this.m.dateFin);
        assertTrue(12 == this.instance.getDates().size());
        this.instance.testDates(badsFormatsReport);
        errors = badsFormatsReport.getMessages().split("-");
        assertTrue(12 + 1 == errors.length);
        assertEquals(" La valeur de la date 01/2012 n'est pas définie pour la période du 01/2012 au 12/2012 ", errors[1]);
        assertEquals(" La valeur de la date 12/2012 n'est pas définie pour la période du 01/2012 au 12/2012 ", errors[errors.length - 1]);
    }

    /**
     * Test of testNomFichier method, of class RequestProperties.
     */
    /*
     * @Test public void testTestNomFichier() { System.out.println("testNomFichier"); String originalNomFichier = ""; BadsFormatsReport badsFormatsReport = new BadsFormatsReport("Err ");
     * System.out.println(m.dataset.buildDownloadFilename(m.datasetConfiguration)); }
     */
    /**
     * Test of getColumnNames method, of class RequestProperties.
     */
    @Test
    public void testGetColumnNames() {
        RequestProperties instance = new RequestProperties();
        String[] expResult = null;
        String[] result = instance.getColumnNames();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setColumnNames method, of class RequestProperties.
     */
    @Test
    public void testSetColumnNames() {
        String[] columnNames = new String[]{"column1", "column2", "column3"};
        List<Column> columns = new LinkedList();
        columns.add(this.m.column);
        columns.add(this.m.column);
        columns.add(this.m.column);
        Mockito.when(this.m.column.getName()).thenReturn("column3");
        this.instance.setColumnNames(columnNames, columns);
        assertNull(this.instance.getColumn(0));
        assertNull(this.instance.getColumn(1));
        assertEquals(this.m.column, this.instance.getColumn(2));
        assertNull(this.instance.getColumn(3));
    }

    /**
     * Test of getuniteNames method, of class RequestProperties.
     */
    @Test
    public void testGetuniteNames() {
        final String[] unitesNames = new String[]{"uniteName1", "uniteName2", "uniteName3"};
        this.instance.setUniteNames(unitesNames);
        String[] result = this.instance.getuniteNames();
        assertArrayEquals(unitesNames, result);
    }


}
