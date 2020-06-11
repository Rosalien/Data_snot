package org.cnrs.osuc.snot.dataset.impl.fileNameCheckers;

import org.cnrs.osuc.snot.dataset.impl.fileNameCheckers.AbstractSnotFileNameChecker;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.inra.ecoinfo.config.ICoreConfiguration;
import org.inra.ecoinfo.dataset.exception.InvalidFileNameException;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.DateUtil;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author ptcherniati
 */
public class AbstractSnotFileNameCheckerTest {

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
    AbstractSnotFileNameCheckerImpl instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    ICoreConfiguration configuration;

    /**
     *
     */
    public AbstractSnotFileNameCheckerTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        instance = new AbstractSnotFileNameCheckerImpl();
        MockitoAnnotations.initMocks(this);
        instance.setDatasetConfiguration(m.datasetConfiguration);
        instance.setConfiguration(configuration);
        instance.setDatatypeDAO(m.datatypeDAO);
        instance.setSiteDAO(m.siteDAO);
        initDataset();
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getFilePath method, of class AbstractSnotFileNameChecker.
     */
    @Test
    public void testGetFilePath() {
        m.dataset.setDateDebutPeriode(m.dateDebut);
        m.dataset.setDateFinPeriode(m.dateFin);
        when(configuration.getSiteSeparatorForFileNames()).thenReturn("-");
        String result = instance.getFilePath(m.versionFile);
        assertEquals("parent-site-enfant_datatype_01-01-2012_31-12-2012#V0#.csv", result);
    }

    /**
     * Test of isValidFileName method, of class AbstractSnotFileNameChecker.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testIsValidFileName() throws Exception {
        when(m.siteDAO.getAll()).thenReturn(Arrays.asList(new Site[]{m.site}));
        doReturn("parent-site-enfant_datatype_01-01-2012_31-12-2012.csv").when(m.dataset).buildDownloadFilename(m.datasetConfiguration);
        boolean result = instance.isValidFileName("parent-site-enfant_datatype_01-01-2012_31-12-2012#V1#.csv", m.versionFile);
        assertTrue(result);
        try {
            result = instance.isValidFileName("parent-site_datatype_01-01-2012_31-12-2012#V1#.csv", m.versionFile);
            fail("no exception");
        } catch (InvalidFileNameException e) {
            //doNothing
        }
    }

    /**
     * Test of getDateForString method, of class AbstractSnotFileNameChecker.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDateForString() throws Exception {
        final String fileName = "parent-site-enfant_datatype_01-01-2012_31-12-2012.csv";
        Matcher splitFilename = Pattern.compile(".*_(.*?)_(.*?).csv").matcher(fileName);
        LocalDateTime result = instance.getDateForString(splitFilename, 1);
        assertEquals(m.dateDebut, result);
        result = instance.getDateForString(splitFilename, 2);
        assertEquals(m.dateFin, result);
    }

    /**
     * Test of setDatasetConfiguration method, of class
     * AbstractSnotFileNameChecker.
     */
    @Test
    public void testSetDatasetConfiguration() {
        assertEquals(m.datasetConfiguration, instance.datasetConfiguration);
    }

    /**
     * Test of getDatePattern method, of class AbstractSnotFileNameChecker.
     */
    @Test
    public void testGetDatePattern() {
        String result = instance.getDatePattern();
        assertEquals(DateUtil.DD_MM_YYYY_FILE, result);
    }

    /**
     * Test of testDates method, of class AbstractSnotFileNameChecker.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testTestDates() throws Exception {
        Matcher splitFilename = Pattern.compile("(.*)_(.*)_(.*)_(.*).csv").matcher("parent-site-enfant_datatype_01-01-2012_31-12-2012.csv");
        instance.testDates(m.versionFile, MockUtils.SITE_CODE, MockUtils.DATATYPE, splitFilename);
    }

    private void initDataset() {
        doCallRealMethod().when(m.dataset).buildDownloadFilename(m.datasetConfiguration);
        doCallRealMethod().when(m.dataset).getDateDebutPeriode();
        doCallRealMethod().when(m.dataset).getDateFinPeriode();
        doCallRealMethod().when(m.dataset).setDateDebutPeriode(any(LocalDateTime.class));
        doCallRealMethod().when(m.dataset).setDateFinPeriode(any(LocalDateTime.class));
    }

    /**
     *
     */
    public class AbstractSnotFileNameCheckerImpl extends AbstractSnotFileNameChecker {

        /**
         *
         * @return
         */
        @Override
        public String getDatePattern() {
            return DateUtil.DD_MM_YYYY_FILE;
        }
    }

}
