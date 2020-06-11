
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.AbstractVerificateurEntete;
import com.Ostermiller.util.CSVParser;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ExpectedColumn;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesIntervalValue;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author tcherniatinsky
 */
public class AbstractVerificateurEnteteTest {

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

    AbstractVerificateurEntete instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    DatasetDescriptor datasetDescriptor;
    @Mock
    IRequestProperties requestProperties;
    List<Column> columns = new LinkedList();
    @Mock
    Column column1;
    @Mock
    Column column2;
    @Mock
    Column column3;

    /**
     *
     */
    public AbstractVerificateurEnteteTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new AbstractVerificateurEnteteImpl();
        instance.setLocalizationManager(MockUtils.localizationManager);
        instance.setSiteSnotDAO(m.siteDAO);
        instance.setDatatypeDAO(m.datatypeDAO);
        instance.setThemeDAO(m.themeDAO);
        instance.setUniteDAO(m.uniteDAO);
        instance.setVariableDAO(m.variableDAO);
        instance.setUtilisateurDAO(m.utilisateurDAO);
        instance.setDatatypeVariableUniteSnotDAO(m.datatypeVariableUniteDAO);
        instance.setDatasetConfiguration(m.datasetConfiguration);

        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        when(datasetDescriptor.getColumns()).thenReturn(columns);
        when(column1.getName()).thenReturn("column 1");
        when(column2.getName()).thenReturn("column 2");
        when(column3.getName()).thenReturn("column 3");
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of estLigneVide method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testEstLigneVide() {
        String[] values = new String[]{};
        Boolean result = instance.estLigneVide(values);
        assertTrue(result);
        values = new String[]{"", "", "", ""};
        result = instance.estLigneVide(values);
        assertTrue(result);
        values = new String[]{"chat", "", "", ""};
        result = instance.estLigneVide(values);
        assertFalse(result);
        values = new String[]{"", "", "", "chat"};
        result = instance.estLigneVide(values);
        assertFalse(result);
    }

    /**
     * Test of getFormatDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testGetFormatDate() {
        String result = instance.getFormatDate(m.versionFile);
        assertEquals("dd/MM/yyyy", result);
    }

    /**
     * Test of testHeaders method, of class AbstractVerificateurEntete.
     * @throws java.lang.Exception
     */
    @Test
    public void testTestHeaders() throws Exception {
        CSVParser parser = null;
        VersionFile versionFile = null;
        IRequestProperties requestProperties = null;
        String encoding = "";
        BadsFormatsReport badsFormatsReport = null;
        DatasetDescriptor datasetDescriptor = null;
        AbstractVerificateurEntete instance = new AbstractVerificateurEnteteImpl();
        long expResult = 0L;
        long result = instance.testHeaders(parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor);
        assertEquals(0, result);
    }

    /**
     * Test of setDatatypeName method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testSetDatatypeName() {
        String datatypeName = "datatypeName";
        instance.setDatatypeName(datatypeName);
        assertEquals(datatypeName, instance.datatypeName);
    }

    /**
     * Test of setFormatRegexDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testSetFormatRegexDate() {
        String formatRegexDate = "dd/MM/:YYYY";
        instance.setFormatRegexDate(formatRegexDate);
        assertEquals(formatRegexDate, instance.formatRegexDate);
    }

    /**
     * Test of setFrequenceAttendu method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testSetFrequenceAttendu() {
        String frequenceAttendu = "sh";
        instance.setFrequenceAttendu(frequenceAttendu);
        assertEquals(frequenceAttendu, instance.frequenceAttendu);
    }

    /**
     * Test of setFormatDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testSetFormatDate() {
        String formatDate = "dd/mm/YYYY";
        instance.setFormatDate(formatDate);
        assertEquals(formatDate, instance.formatDate);
    }

    /**
     * Test of jumpLines method, of class AbstractVerificateurEntete.
     * @throws java.io.IOException
     */
    @Test
    public void testJumpLines() throws IOException {
        Reader in = new StringReader("line1;1\nline2;2\nline3;3");
        CSVParser parser = new CSVParser(in, ';');
        long lineNumber = 0L;
        int numberOfJumpedLines = 2;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("error");
        String lineJumpedDescription = "dexription";
        long expResult = 2L;
        long result = instance.jumpLines(parser, lineNumber, numberOfJumpedLines, badsFormatsReport, lineJumpedDescription);
        assertEquals(expResult, result);
        //end of file
        result = instance.jumpLines(parser, lineNumber, numberOfJumpedLines, badsFormatsReport, lineJumpedDescription);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>error :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of getMessageFrequenceErreur method, of class
     * AbstractVerificateurEntete.
     */
    @Test
    public void testGetMessageFrequenceErreur() {
        String[] values = new String[]{"message"};
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        instance.getMessageFrequenceErreur(values, badsFormatsReport, 1);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- message </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of getSite method, of class AbstractVerificateurEntete.
     * @throws org.inra.ecoinfo.utils.exceptions.PersistenceException
     */
    
//JBP @Test
//    public void testGetSite() throws PersistenceException {
//        String[] sites = new String[]{"site:", "parent", "site", "enfant"};
//        when(m.siteParent.getCode()).thenReturn("parent");
//        when(m.site.getCode()).thenReturn("parent/site");
//        when(m.siteDAO.getByCode("parent/site")).thenReturn(Optional.of(m.site));
//        when(m.siteDAO.getByCode("parent/site/enfant")).thenReturn(Optional.of(m.siteEnfant));
//        SiteSnot result = instance.getSite(sites).orElse(null);
//        assertEquals(m.siteEnfant, result);
//        //no site
//        sites = new String[]{"site:", "enfant", "site", "parent"};
//        result = instance.getSite(sites).orElse(null);
//        assertNull(result);
//    }

    /**
     * Test of readCommentaires method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadCommentaires() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        Reader in = new StringReader("commentaire :;comment\ncommentaire :;comment2\n");
        CSVParser parser = new CSVParser(in, ';');
        long lineNumber = 0L;
        long expResult = 0L;
        long result = instance.readCommentaires(badsFormatsReport, parser, lineNumber, requestProperties);
        verify(requestProperties).setCommentaire("comment");
        assertTrue(1L == result);
        result = instance.readCommentaires(badsFormatsReport, parser, result, requestProperties);
        verify(requestProperties).setCommentaire("comment2");
        assertTrue(2L == result);
        // no comment
        result = instance.readCommentaires(badsFormatsReport, parser, result, requestProperties);
        assertTrue(2L == result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of verifieFichierVide method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testGetLine() {
        Reader in = new StringReader("line;chat\ngrenouille");
        CSVParser parser = new CSVParser(in, ';');
        AbstractVerificateurEntete instance = new AbstractVerificateurEnteteImpl();
        String[] line = null;
        try {
            line = instance.getLine(parser);
        } catch (IOException ex) {
            fail("no line");
        }
        assertArrayEquals(new String[]{"line", "chat"}, line);
        try {
            line = instance.getLine(parser);
        } catch (IOException ex) {
            fail("no line");
        }
        assertArrayEquals(new String[]{"grenouille"}, line);
        //empty line 
        try {
            line = instance.getLine(parser);
        } catch (IOException ex) {
            return;
        }
        fail("no error");
    }

    /**
     * Test of readBeginAndEndDates method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadBeginAndEndDates() {
        instance = Mockito.spy(instance);
        instance.formatDate = "dd/MM/yyyy";
        Reader in = new StringReader("date de début;" + m.DATE_DEBUT + "\ndate de fin : ;" + m.DATE_FIN);
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 0L;
        long expResult = 2L;
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateDebut);
        when(requestProperties.getDateDeFin()).thenReturn(m.dateFin);
        long result = instance.readBeginAndEndDates(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        verify(requestProperties).setDateDeDebut(m.dateDebut);
        verify(requestProperties).setDateDeFin(m.dateFin);
        assertEquals(expResult, result);

        //test with missing line
        result = instance.readBeginAndEndDates(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
        assertEquals(expResult, result);
    }

    /**
     * Test of readBeginDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadBeginDate() {
        instance.formatDate = "dd/MM/yyyy";
        Reader in = new StringReader("date de début;" + m.DATE_DEBUT + "\ndate de fin : ;" + m.DATE_FIN);
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        long expResult = 5L;
        long result = instance.readBeginDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertTrue(5L == result);
        verify(requestProperties).setDateDeDebut(m.dateDebut);
        assertEquals(expResult, result);

        //missing date 
        in = new StringReader("date de début;");
        parser = new CSVParser(in, ';');
        result = instance.readBeginDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertTrue(5L == result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de début est manquante à la ligne 4 colonne 2 </p>", badsFormatsReport.getHTMLMessages());

        //invalid date (not a date)
        badsFormatsReport = new BadsFormatsReport("erreur");
        in = new StringReader("date de début;date");
        parser = new CSVParser(in, ';');
        assertTrue(5L == result);
        result = instance.readBeginDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de début spécifiée \"date\" à la ligne 4 colonne 2 est non valide. Le format de date doit être dd/MM/yyyy. </p>", badsFormatsReport.getHTMLMessages());
        //invalid date (bad format)
        badsFormatsReport = new BadsFormatsReport("erreur");
        in = new StringReader("date de début;23/01/15");
        parser = new CSVParser(in, ';');
        assertTrue(5L == result);
        result = instance.readBeginDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de début spécifiée \"23/01/15\" à la ligne 4 colonne 2 est non valide. Le format de date doit être dd/MM/yyyy. </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of readDatatype method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadDatatype() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport(("errror"));
        instance.datatypeName = "datatype";
        Reader in = new StringReader("datatype; datatype");
        CSVParser parser = new CSVParser(in, ';');
        long lineNumber = 3L;
        long expResult = 4L;
        long result = instance.readDatatype(badsFormatsReport, parser, lineNumber);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        //missing line 
        instance.readDatatype(badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
        //missing datatype
        badsFormatsReport = new BadsFormatsReport(("errror"));
        in = new StringReader("datatype; ");
        parser = new CSVParser(in, ';');
        instance.readDatatype(badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Aucun type de données n'a été défini ligne 3 colonne 2. </p>", badsFormatsReport.getHTMLMessages());
        //bad datatype
        badsFormatsReport = new BadsFormatsReport(("errror"));
        in = new StringReader("datatype:;datatop ");
        parser = new CSVParser(in, ';');
        instance.readDatatype(badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Ligne 3 :  le type de donnée n'est pas valide </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of readFrequence method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadFrequence() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport(("errror"));
        instance.frequenceAttendu = "sh";
        Reader in = new StringReader("frequence; sh");
        CSVParser parser = new CSVParser(in, ';');
        long lineNumber = 3L;
        long expResult = 4L;
        long result = instance.readFrequence(requestProperties, badsFormatsReport, parser, lineNumber);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        //missing line 
        instance.readFrequence(requestProperties, badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
        //missing datatype
        badsFormatsReport = new BadsFormatsReport(("errror"));
        in = new StringReader("frequence; ");
        parser = new CSVParser(in, ';');
        instance.readFrequence(requestProperties, badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Ligne 3 : la fréquence est absente </p>", badsFormatsReport.getHTMLMessages());
        //bad datatype
        badsFormatsReport = new BadsFormatsReport(("errror"));
        in = new StringReader("frequence:;j ");
        parser = new CSVParser(in, ';');
        instance.readFrequence(requestProperties, badsFormatsReport, parser, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- frequence: </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of readEmptyLine method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadEmptyLine() {
        Reader in = new StringReader(";;;;");
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        long expResult = 5L;
        long result = instance.readEmptyLine(badsFormatsReport, parser, lineNumber);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        //missing line 
        instance.readEmptyLine(badsFormatsReport, parser, lineNumber);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
        //not empty
        badsFormatsReport = new BadsFormatsReport(("errror"));
        in = new StringReader(";;4;;");
        parser = new CSVParser(in, ';');
        instance.readEmptyLine(badsFormatsReport, parser, lineNumber);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>errror :</p><p style='text-indent: 30px'>- Vous devez laisser la ligne 4 vide. </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of readEndDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadEndDate() {
        instance.formatDate = "dd/MM/yyyy";
        Reader in = new StringReader("date de fin : ;" + m.DATE_FIN);
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        long expResult = 5L;
        long result = instance.readEndDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        verify(requestProperties).setDateDeFin(m.dateFin);
        assertFalse(badsFormatsReport.hasErrors());
        assertEquals(expResult, result);

        //missing date 
        in = new StringReader("date de fin;");
        parser = new CSVParser(in, ';');
        result = instance.readEndDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de fin est manquante à la ligne 4 colonne 2 </p>", badsFormatsReport.getHTMLMessages());
        assertEquals(expResult, result);

        //invalid date (not a date)
        badsFormatsReport = new BadsFormatsReport("erreur");
        in = new StringReader("date de début;date");
        parser = new CSVParser(in, ';');
        result = instance.readEndDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de fin spécifiée \"date\" à la ligne 4 colonne 2 est non valide. Le format de date doit être dd/MM/yyyy. </p>", badsFormatsReport.getHTMLMessages());

        //invalid date (bad format)
        badsFormatsReport = new BadsFormatsReport("erreur");
        in = new StringReader("date de début;23/01/15");
        parser = new CSVParser(in, ';');
        result = instance.readEndDate(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de fin spécifiée \"23/01/15\" à la ligne 4 colonne 2 est non valide. Le format de date doit être dd/MM/yyyy. </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of readLineHeader method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadLineHeader() {
        Reader in = new StringReader("column 1; column 2; column 3");
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        long expResult = 5L;
        long result = instance.readLineHeader(badsFormatsReport, parser, lineNumber, datasetDescriptor, requestProperties);
        assertFalse(badsFormatsReport.hasErrors());
        assertEquals(expResult, result);
        verify(requestProperties).setColumnNames(AdditionalMatchers.aryEq(new String[]{"column 1", "column 2", "column 3"}), eq(this.columns));
        //missing line 
        instance.readLineHeader(badsFormatsReport, parser, lineNumber, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
        //bad columns
        in = new StringReader("column 2; column 2; column 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        instance.readLineHeader(badsFormatsReport, parser, lineNumber, datasetDescriptor, requestProperties);
        assertEquals(expResult, result);
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : L'intitulé de la colonne 1 est \"column 2\", alors que \"column 1\" est attendu </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of readSite method, of class AbstractVerificateurEntete.
     * @throws org.inra.ecoinfo.utils.exceptions.PersistenceException
     */
//JBP    @Test
//    public void testReadSite() throws PersistenceException {
//        Reader in = new StringReader("site:;parent;site;enfant");
//        CSVParser parser = new CSVParser(in, ';');
//        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
//        long lineNumber = 4L;
//        long expResult = 5L;
//        when(m.siteParent.getCode()).thenReturn("parent");
//        when(m.site.getCode()).thenReturn("parent/site");
//        when(m.siteDAO.getByCode("parent/site")).thenReturn(Optional.of(m.site));
//        when(m.siteDAO.getByCode("parent/site/enfant")).thenReturn(Optional.of(m.siteEnfant));
//        when(m.siteEnfant.getPath()).thenReturn("parent/site/enfant");
//        long result = instance.readSite(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
//        verify(requestProperties).setSite(m.siteEnfant);
//        assertFalse(badsFormatsReport.hasErrors());
//        assertEquals(expResult, result);
//        //end of file 
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        result = instance.readSite(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
//        //no site
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        in = new StringReader("site:;");
//        parser = new CSVParser(in, ';');
//        result = instance.readSite(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : aucun site n'a été défini </p>", badsFormatsReport.getHTMLMessages());
//        //bad site
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        in = new StringReader("site:;parent;site;enfant2");
//        parser = new CSVParser(in, ';');
//        result = instance.readSite(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : le site défini n'est pas valide </p>", badsFormatsReport.getHTMLMessages());
//        //site not same as repository site
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        in = new StringReader("site:;parent;site;enfant");
//        SiteSnot otherSite = mock(SiteSnot.class);
//        parser = new CSVParser(in, ';');
//        when(m.site.getPath()).thenReturn(MockUtils.SITE);
//        result = instance.readSite(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : le site défini n'est pas valide </p>", badsFormatsReport.getHTMLMessages());
//    }

    /**
     * Test of readTheme method, of class AbstractVerificateurEntete.
     */
//JBP    @Test
//    public void testReadTheme() {
//        Reader in = new StringReader("theme:;theme");
//        CSVParser parser = new CSVParser(in, ';');
//        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
//        long lineNumber = 4L;
//        long expResult = 5L;
//        long result = instance.readTheme(m.versionFile, badsFormatsReport, parser, lineNumber);
//        assertFalse(badsFormatsReport.hasErrors());
//        assertEquals(expResult, result);
//        //end of file 
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        instance.readTheme(m.versionFile, badsFormatsReport, parser, lineNumber);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Le fichier est incomplet. </p>", badsFormatsReport.getHTMLMessages());
//        //no theme
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        in = new StringReader("theme:;");
//        parser = new CSVParser(in, ';');
//        instance.readTheme(m.versionFile, badsFormatsReport, parser, lineNumber);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- A la ligne 2 colonne 2, le thème doit être flux </p>", badsFormatsReport.getHTMLMessages());
//        //bad site
//        badsFormatsReport = new BadsFormatsReport("erreur");
//        in = new StringReader("site:;parent;site;enfant2");
//        parser = new CSVParser(in, ';');
//        instance.readTheme(m.versionFile, badsFormatsReport, parser, lineNumber);
//        assertEquals(expResult, result);
//        assertTrue(badsFormatsReport.hasErrors());
//        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- A la ligne 2 colonne 2, le thème doit être flux </p>", badsFormatsReport.getHTMLMessages());
//    }

    /**
     * Test of testIntervalDate method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testTestIntervalDate() {
        String dateFormat = DateUtil.DD_MM_YYYY;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 0L;
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateDebut);
        when(requestProperties.getDateDeFin()).thenReturn(m.dateFin);
        instance.testIntervalDate(badsFormatsReport, lineNumber, requestProperties);
        assertFalse(badsFormatsReport.hasErrors());
        //bad interval
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateFin);
        when(requestProperties.getDateDeFin()).thenReturn(m.dateDebut);
        when(requestProperties.getDateFormat()).thenReturn(dateFormat);
        instance.testIntervalDate(badsFormatsReport, lineNumber, requestProperties);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de début \"31/12/2012\" spécifiée à la ligne -1 colonne 1 doit être antérieure ou égale à la date de fin \"01/01/2012\" spécifiée à la ligne 0 colonne 1 </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of getMinMaxFromDatatypeUniteVariableSnot method, of class
 AbstractVerificateurEntete.
     * @throws java.lang.Exception
     */
    @Test
    public void test_getMinMaxFromDatatypeUniteVariableSnot() throws Exception {
        int index = 5;
        String columnName = "column 1";
        String uniteName = "unite";
        String jeuCode = "jeu";
        when(datasetDescriptor.getName()).thenReturn("datatype");
        DatatypeVariableUniteSnot datatypeVariableUniteSnot = Mockito.mock(DatatypeVariableUniteSnot.class);
        when(m.datatypeVariableUniteDAO.getByNKey(jeuCode,MockUtils.SITE_CODE,"datatype", columnName, uniteName)).thenReturn(Optional.of(datatypeVariableUniteSnot));
        String[] valeurs = new String[10];
        when(datatypeVariableUniteSnot.getMax()).thenReturn(200.0F);
        when(datatypeVariableUniteSnot.getMin()).thenReturn(50.0F);
        instance.getMinMaxFromDatatypeUniteVariableSnot(datasetDescriptor, index, jeuCode,MockUtils.SITE_CODE,columnName, uniteName, Boolean.FALSE, valeurs);
        instance.getMinMaxFromDatatypeUniteVariableSnot(datasetDescriptor, index + 1, jeuCode,MockUtils.SITE_CODE,columnName, uniteName, Boolean.TRUE, valeurs);
        assertEquals("200.0", valeurs[5]);
        assertEquals("50.0", valeurs[6]);
    }

    /**
     * Test of minMaxValues method, of class AbstractVerificateurEntete.
     * @throws java.lang.Exception
     */
    @Test
    public void testMinMaxValues() throws Exception {
        IRequestPropertiesIntervalValue requestProperties = Mockito.mock(IRequestPropertiesIntervalValue.class);
        ExpectedColumn ec1 = Mockito.mock(ExpectedColumn.class);
        ExpectedColumn ec2 = Mockito.mock(ExpectedColumn.class);
        ExpectedColumn ec3 = Mockito.mock(ExpectedColumn.class);
        when(ec2.isVariable()).thenReturn(Boolean.TRUE);
        when(ec3.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VARIABLE_GF);
        Map<String, ExpectedColumn> expectedColumns = new HashMap();
        final String column_1 = "column 1";
        final String column_2 = "column 2";
        final String column_3 = "column 3";
        expectedColumns.put(column_2, ec2);
        expectedColumns.put(column_3, ec3);
        final String[] columnNames = new String[]{column_1, column_2, column_3};
        final String[] minValues = new String[]{"min 1", "min 2", "min 3"};
        final String[] minValues2 = new String[]{"min 2", "min 3"};
        final String[] maxValues = new String[]{"max 1", "max 2", "max 3"};
        final String[] maxValues2 = new String[]{"max 2", "max 3"};
        final String[] unitsValues = new String[]{"", "unit 2", "unit 3"};
        when(requestProperties.getColumnNames()).thenReturn(columnNames);
        when(requestProperties.getValeurMinInFile()).thenReturn(minValues);
        when(requestProperties.getValeurMaxInFile()).thenReturn(maxValues);
        when(requestProperties.getuniteNames()).thenReturn(unitsValues);
        when(requestProperties.getSite()).thenReturn(m.site);
        when(requestProperties.getExpectedColumns()).thenReturn(expectedColumns);
        when(datasetDescriptor.getUndefinedColumn()).thenReturn(1);
        instance.minMaxValues(datasetDescriptor, requestProperties);
        verify(requestProperties).setValeursMin(minValues2);
        verify(requestProperties).setValeursMax(maxValues2);

        //test with no value
        when(requestProperties.getValeurMinInFile()).thenReturn(new String[]{null, null, null});
        when(requestProperties.getValeurMaxInFile()).thenReturn(new String[]{null, null, null});
        instance = spy(instance);
        final Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                int index = invocation.getArgumentAt(1, Integer.class);
                boolean isMin = invocation.getArgumentAt(5, Boolean.class);
                String[] min = invocation.getArgumentAt(6, String[].class);
                if (isMin) {
                    min[index] = String.valueOf(index * 2);
                } else {
                    min[index] = String.valueOf(index * 2 + 1);
                }
                return null;
            }
        };
        doAnswer(answer).when(instance).getMinMaxFromDatatypeUniteVariableSnot(
                eq(datasetDescriptor), Matchers.anyInt(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyBoolean(), Matchers.any(String[].class));
        instance.minMaxValues(datasetDescriptor, requestProperties);
        verify(ec2).setMinValue(0F);
        verify(ec2).setMaxValue(1F);
        verify(ec3).setMinValue(2F);
        verify(ec3).setMaxValue(3F);
    }

    /**
     * Test of readValeursMax method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadValeursMax() {
        Reader in = new StringReader("max;3.02;4.01");
        CSVParser parser = new CSVParser(in, ';');
        when(datasetDescriptor.getUndefinedColumn()).thenReturn(1);
        final String column_1 = "column 1";
        final String column_2 = "column 2";
        final String column_3 = "column 3";
        final String[] columnNames = new String[]{column_1, column_2, column_3};
        IRequestPropertiesIntervalValue requestProperties = Mockito.mock(IRequestPropertiesIntervalValue.class);
        when(requestProperties.getColumnNames()).thenReturn(columnNames);
        long lineNumber = 4L;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("error");
        AbstractVerificateurEntete instance = new AbstractVerificateurEnteteImpl();
        long expResult = 5L;
        long result = instance.readValeursMax(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties).setValeurMaxInFile(AdditionalMatchers.aryEq(new String[]{"max", "3.02", "4.01"}));
        verify(requestProperties).setValeursMax(AdditionalMatchers.aryEq(new String[]{"3.02", "4.01"}));
        // ligne ne commence pas par min
        in = new StringReader(";3.02;4.01");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMax(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertEquals("<p style='color: red'>error :</p><p style='text-indent: 30px'>- Ligne 4 : la ligne de valeur maximale est absente </p>", badsFormatsReport.getHTMLMessages());

        // lignes diffèrent en longueur
        badsFormatsReport = new BadsFormatsReport("error");
        in = new StringReader("max;3.02");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMax(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertEquals("<p style='color: red'>error :</p><p style='text-indent: 30px'>- Ligne 4 : Erreur dans la ligne des valeurs maximales. Il manque des colonnes </p>", badsFormatsReport.getHTMLMessages());

        // value empty
        badsFormatsReport = new BadsFormatsReport("error");
        in = new StringReader("max;;4.01");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMax(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties).setValeurMaxInFile(AdditionalMatchers.aryEq(new String[]{"max", "", "4.01"}));
        verify(requestProperties).setValeursMax(AdditionalMatchers.aryEq(new String[]{null, "4.01"}));
        
    }

    /**
     * Test of readValeursMin method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testReadValeursMin() {
        Reader in = new StringReader("min;3.02;4.01");
        CSVParser parser = new CSVParser(in, ';');
        when(datasetDescriptor.getUndefinedColumn()).thenReturn(1);
        final String column_1 = "column 1";
        final String column_2 = "column 2";
        final String column_3 = "column 3";
        final String[] columnNames = new String[]{column_1, column_2, column_3};
        IRequestPropertiesIntervalValue requestProperties = Mockito.mock(IRequestPropertiesIntervalValue.class);
        when(requestProperties.getColumnNames()).thenReturn(columnNames);
        long lineNumber = 4L;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("error");
        AbstractVerificateurEntete instance = new AbstractVerificateurEnteteImpl();
        long expResult = 5L;
        long result = instance.readValeursMin(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties).setValeurMinInFile(AdditionalMatchers.aryEq(new String[]{"min", "3.02", "4.01"}));
        verify(requestProperties).setValeursMin(AdditionalMatchers.aryEq(new String[]{"3.02", "4.01"}));
        // ligne ne commence pas par min
        in = new StringReader(";3.02;4.01");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMin(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertEquals("<p style='color: red'>error :</p><p style='text-indent: 30px'>- Ligne 4 : la ligne de valeur minimale est absente </p>", badsFormatsReport.getHTMLMessages());

        // lignes diffèrent en longueur
        badsFormatsReport = new BadsFormatsReport("error");
        in = new StringReader("min;3.02");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMin(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        Assert.assertTrue(badsFormatsReport.hasErrors());
        Assert.assertEquals("<p style='color: red'>error :</p><p style='text-indent: 30px'>- Ligne 4 : Erreur dans la ligne des valeurs minimales. Il manque des colonnes </p>", badsFormatsReport.getHTMLMessages());

        // value empty
        badsFormatsReport = new BadsFormatsReport("error");
        in = new StringReader("min;;4.01");
        parser = new CSVParser(in, ';');
        result = instance.readValeursMin(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties).setValeurMinInFile(AdditionalMatchers.aryEq(new String[]{"min", "", "4.01"}));
        verify(requestProperties).setValeursMin(AdditionalMatchers.aryEq(new String[]{null, "4.01"}));
        
        
    }

    /**
     * Test of readUnites method, of class AbstractVerificateurEntete.
     * @throws org.inra.ecoinfo.utils.exceptions.PersistenceException
     */
    @Test
    public void testReadUnites() throws PersistenceException {
        when(requestProperties.getColumnNames()).thenReturn(new String[]{"column 1", "dd/yyyy", "column 3"});
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{"column 1", "column 2", "column 3"}));
        when(requestProperties.getSite()).thenReturn(m.site);
        when(column1.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        when(column1.isVariable()).thenReturn(Boolean.TRUE);
        when(column2.isFlag()).thenReturn(Boolean.TRUE);
        when(column2.getFormatType()).thenReturn("dd/yyyy");
        when(column2.getFlagType()).thenReturn(Constantes.PROPERTY_CST_DATE_TYPE);
        when(column3.getFlagType()).thenReturn(Constantes.PROPERTY_CST_VARIABLE_TYPE);
        when(column3.isVariable()).thenReturn(Boolean.TRUE);
        when(datasetDescriptor.getName()).thenReturn(m.DATATYPE);
        when(m.datatypeVariableUniteDAO.getByNKey("jeu",MockUtils.SITE_CODE, m.DATATYPE, "column 1", "unité 1")).thenReturn(Optional.of(m.datatypeVariableUnite));
        when(m.datatypeVariableUniteDAO.getByNKey("jeu",MockUtils.SITE_CODE, m.DATATYPE, "column 2", "unité 2")).thenReturn(Optional.of(m.datatypeVariableUnite));
        when(m.datatypeVariableUniteDAO.getByNKey("jeu",MockUtils.SITE_CODE, m.DATATYPE, "column 3", "unité 3")).thenReturn(Optional.of(m.datatypeVariableUnite));
        Reader in = new StringReader("unité 1;unité 2;unité 3");
        CSVParser parser = new CSVParser(in, ';');
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        long expResult = 5L;
        long result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties, times(1)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        assertFalse(badsFormatsReport.hasErrors());
        //column not in list
        in = new StringReader("unité 1;unité 2;unité 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{"column 1", "column 12", "column 3"}));
        result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        verify(requestProperties, times(2)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        //empty unité
        in = new StringReader("unité 1;;unité 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{"column 1", "column 2", "column 3"}));
        result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        verify(requestProperties, times(2)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        verify(requestProperties, times(1)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "", "unité 3"})));
        //unite and column differs in length
        in = new StringReader("unité 1;unité 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{"column 1", "column 2", "column 3"}));
        result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : Erreur dans la ligne des unités. Il manque des colonnes </p>", badsFormatsReport.getHTMLMessages());
        verify(requestProperties, times(2)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        verify(requestProperties, times(1)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "", "unité 3"})));
        //list datasetdescriptor columns empty
        in = new StringReader("unité 1;unité 2;unité 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{}));
        result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertFalse(badsFormatsReport.hasErrors());
        verify(requestProperties, times(3)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        verify(requestProperties, times(1)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "", "unité 3"})));
        //unite for date
        in = new StringReader("unité 1;dd/MM/yyyy;unité 3");
        parser = new CSVParser(in, ';');
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(column2.isFlag()).thenReturn(Boolean.TRUE);
        when(column2.getFormatType()).thenReturn("dd");
        when(column2.getFlagType()).thenReturn(Constantes.PROPERTY_CST_DATE_TYPE);
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(Arrays.asList(new String[]{"column 1", "dd/yyyy", "column 3"}));
        result = instance.readUnites(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        verify(requestProperties, times(3)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "unité 2", "unité 3"})));
        verify(requestProperties, times(1)).setUniteNames((AdditionalMatchers.aryEq(new String[]{"unité 1", "", "unité 3"})));
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : Erreur de format de date </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of readNomDesColonnes method, of class AbstractVerificateurEntete.
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Test
    public void testReadNomDesColonnes() throws BusinessException {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        Reader in = new StringReader("column 1;column 2;column 3");
        List<String> lstDatasetNameColumns = Arrays.asList(new String[]{"column 1", "column 2", "column 3"});
        CSVParser parser = new CSVParser(in, ';');
        long lineNumber = 4L;
        long expResult = 5L;
        when(datasetDescriptor.getUndefinedColumn()).thenReturn(1);
        when(requestProperties.getLstDatasetNameColumns()).thenReturn(lstDatasetNameColumns);
        long result = instance.readNomDesColonnes(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        verify(requestProperties).setColumnNames(AdditionalMatchers.aryEq(new String[]{"column 1", "column 2", "column 3"}), eq(columns));
        assertEquals(Arrays.asList(new String[]{"column 1", "column 2", "column 3"}), lstDatasetNameColumns);
        //bad generic columns
        in = new StringReader("column 0;column 2;column 3");
        parser = new CSVParser(in, ';');
        result = instance.readNomDesColonnes(m.versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        assertEquals(expResult, result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : L'intitulé de la colonne 1 est \"column 0\", alors que \"column 1\" est attendu </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of testNomColonnes method, of class AbstractVerificateurEntete.
     * @throws java.lang.Exception
     */
    @Test
    public void testTestNomColonnes() throws Exception {
        String[] columnNames = new String[]{"column 1", "column 2", "column 3"};
        int index = 2;
        long lineNumber = 4L;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        boolean result = instance.testNomColonnes(columnNames, index, badsFormatsReport, lineNumber, datasetDescriptor, m.versionFile, requestProperties);
        Assert.assertTrue(result);
        assertFalse(badsFormatsReport.hasErrors());
    }

    /**
     * Test of getDatatypeWithFrequenceCode method, of class
     * AbstractVerificateurEntete.
     */
    @Test
    public void testGetDatatypeWithFrequenceCode() {
        String expResult = "eddycovariance_sh";
        when(datasetDescriptor.getName()).thenReturn(expResult);
        String result = instance.getDatatypeWithFrequenceCode(datasetDescriptor);
        assertEquals(expResult, result);
    }

    /**
     * Test of verifieFirstColonnes method, of class AbstractVerificateurEntete.
     */
    @Test
    public void testVerifieFirstColonnes() {
        String[] columnNames = new String[]{"column 1", "column 2", "column 3"};
        when(datasetDescriptor.getUndefinedColumn()).thenReturn(1);
        List<String> lstDatasetNameColumns = Arrays.asList(new String[]{"column 1", "column 2", "column 3"});
        long lineNumber = 4L;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        instance.verifieFirstColonnes(columnNames, lineNumber, badsFormatsReport, datasetDescriptor, lstDatasetNameColumns);
        // bad column
        columnNames = new String[]{"column 0", "column 2", "column 3"};
        instance.verifieFirstColonnes(columnNames, lineNumber, badsFormatsReport, datasetDescriptor, lstDatasetNameColumns);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : L'intitulé de la colonne 1 est \"column 0\", alors que \"column 1\" est attendu </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     * Test of remplirVariablesTypeDonnees method, of class
     * AbstractVerificateurEntete.
     * @throws java.lang.Exception
     */
    @Test
    public void testRemplirVariablesTypeDonnees() throws Exception {
        when(datasetDescriptor.getName()).thenReturn(m.DATATYPE);
        List<DatatypeVariableUniteSnot> dvus = new LinkedList();
        DatatypeVariableUniteSnot dvu1 = Mockito.mock(DatatypeVariableUniteSnot.class);
        DatatypeVariableUniteSnot dvu2 = Mockito.mock(DatatypeVariableUniteSnot.class);
        ArgumentCaptor<Map> var = ArgumentCaptor.forClass(Map.class);
        VariableSnot variable = Mockito.mock(VariableSnot.class);
        dvus.add(dvu1);
        when(dvu1.getVariable()).thenReturn(variable);
        dvus.add(dvu2);
        when(dvu2.getVariable()).thenReturn(variable);
        when(variable.getName()).thenReturn("column 1", "column 3");
        when(m.datatypeVariableUniteDAO.getByDatatype(m.DATATYPE)).thenReturn(dvus);
        instance.remplirVariablesTypeDonnees(datasetDescriptor, requestProperties);
        verify(requestProperties).setVariableTypeDonnees(var.capture());
        Map capture = var.getValue();
        assertEquals(capture.get("column 1"), dvu1);
        assertEquals(capture.get("column 3"), dvu2);
    }

    /**
     * Test of testAreConsistentDates method, of class
     * AbstractVerificateurEntete.
     */
    @Test
    public void testTestAreConsistentDates() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 2L;
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateDebut);
        when(requestProperties.getDateDeFin()).thenReturn(m.dateFin);
        instance.testAreConsistentDates(badsFormatsReport, m.versionFile, requestProperties, lineNumber, datasetDescriptor);
        assertFalse(badsFormatsReport.hasErrors());
        //bad date de début
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateFin);
        instance.testAreConsistentDates(badsFormatsReport, m.versionFile, requestProperties, lineNumber, datasetDescriptor);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de début spécifiée \"31/12/2012\" à la ligne 2 colonne 1 ne correspond pas à celle du nom de fichier parent-site-enfant_datatype_01-01-2012_31-12-2012.csv </p>", badsFormatsReport.getHTMLMessages());
        //bad date de fin
        badsFormatsReport = new BadsFormatsReport("erreur");
        when(requestProperties.getDatedeDebut()).thenReturn(m.dateDebut);
        when(requestProperties.getDateDeFin()).thenReturn(m.dateDebut);
        instance.testAreConsistentDates(badsFormatsReport, m.versionFile, requestProperties, lineNumber, datasetDescriptor);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- La date de fin spécifiée \"01/01/2012\" à la ligne 2 colonne 2 ne correspond pas à celle du nom de fichier parent-site-enfant_datatype_01-01-2012_31-12-2012.csv </p>", badsFormatsReport.getHTMLMessages());

    }

    /**
     *
     */
    public class AbstractVerificateurEnteteImpl extends AbstractVerificateurEntete {

        @Override
        public void getMessageFrequenceErreur(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber) {
            badsFormatsReport.addException(new Exception(values[0]));
        }
    }

}
