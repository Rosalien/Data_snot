/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import com.Ostermiller.util.CSVParser;
import java.time.LocalDateTime;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.extraction.exception.NoExtractionResultException;
import org.cnrs.osuc.snot.dataset.IDeleteRecord;
import org.cnrs.osuc.snot.dataset.IProcessRecord;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesSnot;
import org.cnrs.osuc.snot.dataset.ITestFormat;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


/**
 * 
 * @author ptcherniati
 */
public class SNOTRecorderTest {

    private static final SimpleTimeZone SIMPLE_TIME_ZONE_UTC = new SimpleTimeZone(0, DateUtil.GMT);

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

    private MockUtils m;
    @Mock
    IDeleteRecord deleteRecord;
    @Mock
    IProcessRecord processRecord;
    @Mock
    ITestFormat testFormat;
    @Spy
    SNOTRecorder instance;
    @Mock
    IRequestProperties requestProperties;
    @Mock
    IRequestPropertiesSnot requestPropertiesSnot;
    @Mock
    ILocalizationManager localizationManager;

    /**
     *
     */
    public SNOTRecorderTest() {}

    /**
     *
     */
    @Before
    public void setUp() {MockitoAnnotations.initMocks(this);
        this.m = MockUtils.getInstance();
        SNOTRecorder.setStaticLocalizationManager(this.m.localizationManager);
        this.instance.setDeleteRecord(this.deleteRecord);
        this.instance.setProcessRecord(this.processRecord);
        this.instance.setTestFormat(this.testFormat);
        this.instance.setDatasetDescriptor(this.m.datasetDescriptor);
}

    /**
     *
     */
    @After
    public void tearDown() {}

    /**
     * Test of getDateTimeStringFromDateStringaAndTimeString method, of class SNOTRecorder.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDateTimeStringFromDateStringaAndTimeString() throws Exception {
        String dateString = "25/12/2012";
        String[] times = new String[]{"23:53", "23:53:00", "23:53:25"};
        String[] results = new String[]{"25/12/2012 23:53:00", "25/12/2012 23:53:00", "25/12/2012 23:53:25"};
        for (int i = 0; i < times.length; i++) {
            String timeString = times[i];
            String expectedResult = results[i];
            TimeZone tz = TimeZone.getDefault();
            LocalDateTime result = SNOTRecorder.getDateTimeStringFromDateStringaAndTimeString(dateString, timeString);
            assertEquals(DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM_SS, expectedResult), result);

        }
    }

    /**
     * Test of getSnotMessage method, of class SNOTRecorder.
     */
@Test
public void testGetSnotMessage() {String key = "PROPERTY_MSG_MISMACH_COLUMN_HEADER";
String expResult = "Ligne %d : L'intitulé de la colonne %d est \"%s\", alors que \"%s\" est attendu";
String result = SNOTRecorder.getSnotMessage(key);
assertEquals(expResult, result);
}

    /**
     * Test of getSnotMessageWithBundle method, of class SNOTRecorder.
     */
    @Test
    public void testGetSnotMessageWithBundle() {
        String PROPERTY_MSG_ERROR = "PROPERTY_MSG_ERROR";
        String expResult = "Erreur";
        String result = SNOTRecorder.getSnotMessageWithBundle(NoExtractionResultException.BUNDLE_SOURCE_PATH, PROPERTY_MSG_ERROR);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntervalDateLocaleForFile method, of class SNOTRecorder.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIntervalDateLocaleForFile() throws Exception {
        String beginDateddmmyyhhmmss = "25-12-2014-235312";
        String endDateddmmyyhhmmss = "01-01-2015-000101";
        IntervalDate result = SNOTRecorder.getIntervalDateLocaleForFile(beginDateddmmyyhhmmss, endDateddmmyyhhmmss);
        assertEquals(beginDateddmmyyhhmmss, result.getBeginDateToString());
        assertEquals(endDateddmmyyhhmmss, result.getEndDateToString());
//        assertEquals(TimeZone.getDefault(), result.getDateFormat().getTimeZone());
    }

    /**
     * Test of getIntervalDateUTCForFile method, of class SNOTRecorder.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetIntervalDateUTCForFile() throws Exception {
        String beginDateddmmyyhhmmss = "25-12-2014-235312";
        String endDateddmmyyhhmmss = "01-01-2015-000101";
        IntervalDate result = SNOTRecorder.getIntervalDateUTCForFile(beginDateddmmyyhhmmss, endDateddmmyyhhmmss);
        assertEquals(beginDateddmmyyhhmmss, result.getBeginDateToString());
        assertEquals(endDateddmmyyhhmmss, result.getEndDateToString());
//        assertEquals(new SimpleTimeZone(0, DateUtil.GMT), result.getDateFormat().getTimeZone());
    }

    /**
     * Test of setStaticLocalizationManager method, of class SNOTRecorder.
     * 
     * /** Test of deleteRecords method, of class SNOTRecorder.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteRecords() throws Exception {
        VersionFile versionFile = this.m.versionFile;
        this.instance.deleteRecords(versionFile);
        Mockito.verify(this.deleteRecord).deleteRecord(versionFile);
    }

    /**
     * Test of processRecord method, of class SNOTRecorder.
     * @throws java.lang.Exception
     */
    @Test
    public void testProcessRecord() throws Exception {
        VersionFile versionFile = this.m.versionFile;
        String fileEncoding = "UTF8";
        Mockito.when(this.m.versionFile.getData()).thenReturn("data;autre data".getBytes());
        this.instance.setDatasetDescriptor(this.m.datasetDescriptor);
        doReturn(this.requestProperties).when(this.instance).getRequestProperties();
        doReturn(this.localizationManager).when(this.instance).getLocalizationManager();
        Mockito.when(this.localizationManager.getMessage(SNOTRecorder.DATASET_DESCRIPTOR_PATH_PATTERN, SNOTRecorder.PROPERTY_MSG_ERROR_BAD_FORMAT)).thenReturn("message");
        ArgumentCaptor<CSVParser> parser = ArgumentCaptor.forClass(CSVParser.class);
        ArgumentCaptor<VersionFile> vf = ArgumentCaptor.forClass(VersionFile.class);
        ArgumentCaptor<IRequestProperties> sp = ArgumentCaptor.forClass(IRequestProperties.class);
        ArgumentCaptor<String> fe = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DatasetDescriptor> ds = ArgumentCaptor.forClass(DatasetDescriptor.class);
        Mockito.doNothing().when(this.instance).processRecord(parser.capture(), vf.capture(), sp.capture(), fe.capture(), ds.capture());
        this.instance.record(versionFile, fileEncoding);
        // Mockito.verify(processRecord).processRecord(parser.capture(), vf.capture(), sp.capture(), fe.capture(), ds.capture());
        assertEquals("data", parser.getValue().nextValue());
        assertEquals("autre data", parser.getValue().nextValue());
        assertEquals(this.m.versionFile, vf.getValue());
        assertEquals(this.requestProperties, sp.getValue());
        assertEquals("UTF8", fe.getValue());
        assertEquals("datasetDescriptorName", ds.getValue().getName());
    }

    /**
     * Test of setLocalizationManager method, of class SNOTRecorder.
     */
    @Test
    public void testSetLocalizationManager() {
        SNOTRecorder instance = new SNOTRecorder();
        instance.setLocalizationManager(this.localizationManager);
        assertEquals(this.localizationManager, instance.getLocalizationManager());
    }

    /**
     * Test of setDeleteRecord method, of class SNOTRecorder.
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Test
    public void testSetDeleteRecord() throws BusinessException {
        VersionFile versionFile = this.m.versionFile;
        this.instance.deleteRecords(versionFile);
        Mockito.verify(this.deleteRecord).deleteRecord(versionFile);
    }


}
