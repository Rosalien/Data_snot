/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.entete;

import com.Ostermiller.util.CSVParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.ExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.request.RequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class AbstractVerificateurEnteteFluxMeteoTest {

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

    AbstractVerificateurEnteteFluxMeteo instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    RequestPropertiesFluxMeteo requestProperties;
    @Mock
    DatasetDescriptor datasetDescriptor;

    /**
     *
     */
    public AbstractVerificateurEnteteFluxMeteoTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new AbstractVerificateurEnteteFluxMeteoImpl();
        instance.setDatasetConfiguration(m.datasetConfiguration);
        instance.setDatatypeDAO(m.datatypeDAO);
        instance.setDatatypeVariableUniteSnotDAO(m.datatypeVariableUniteDAO);
        instance.setLocalizationManager(m.localizationManager);
        instance.setSiteSnotDAO(m.siteDAO);
        instance.setPolicyManager(m.policyManager);
        instance.setThemeDAO(m.themeDAO);
        instance.setUniteDAO(m.uniteDAO);
        instance.setUtilisateurDAO(m.utilisateurDAO);
        instance.setVariableDAO(m.variableDAO);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of testHeaders method, of class AbstractVerificateurEnteteFluxMeteo.
     * @throws java.lang.Exception
     */
    @Test
    public void testTestHeaders() throws Exception {
        CSVParser parser = null;
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        String encoding = "utf-8";
        long result = instance.testHeaders(parser, m.versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor);

    }

    /**
     * Test of setNbColonnesIdentifiantes method, of class
     * AbstractVerificateurEnteteFluxMeteo.
     */
    @Test
    public void testSetNbColonnesIdentifiantes() {
        instance.setNbColonnesIdentifiantes(2);
        assertTrue(2 == instance.nbColonnesIdentifiantes);
    }

    /**
     * Test of getMessageFrequenceErreur method, of class
     * AbstractVerificateurEnteteFluxMeteo.
     */
    @Test
    public void testGetMessageFrequenceErreur() {
        String[] values = new String[]{"colonne 1", "colonne 2"};
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        long lineNumber = 4L;
        instance.getMessageFrequenceErreur(values, badsFormatsReport, lineNumber);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 4 : la fréquence \"colonne 2\" est invalide. Les fréquences valides sont infra-journalier, journalier, mensuel  </p>", badsFormatsReport.getHTMLMessages());
    }

    /**
     * Test of readNomDesColonnes method, of class
     * AbstractVerificateurEnteteFluxMeteo.
     */
    @Test
    public void testReadNomDesColonnes() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        StringReader in = new StringReader("colonne 1;colonne 2;colonne 3");
        List<Column> columns = new ArrayList();
        Column descriptorColumn1 = new Column();
        descriptorColumn1.setName("Colonne 3");
        Column descriptorColumn2 = new Column();
        descriptorColumn2.setName("Colonne 1");
        Column descriptorColumn3 = new Column();
        descriptorColumn3.setName("Colonne 2");
        columns.add(descriptorColumn1);
        columns.add(descriptorColumn2);
        columns.add(descriptorColumn3);
        when(datasetDescriptor.getColumns()).thenReturn(columns);
        CSVParser parser = new CSVParser(in, ';');
        ((AbstractVerificateurEnteteFluxMeteoImpl) instance).jumpReadColumFunction = true;
        ArgumentCaptor<ExpertColonne> expert = ArgumentCaptor.forClass(ExpertColonne.class);
        long result = instance.readNomDesColonnes(m.versionFile, badsFormatsReport, parser, 5L, requestProperties, datasetDescriptor);
        verify(requestProperties).setExpertColonne(expert.capture());
        ExpertColonne capture = expert.getValue();
        assertTrue(capture.getColonnesDansDescripteur().isEmpty());
        assertTrue(6l==result);
        assertTrue(capture.getColonnesDansOrdreFichier().size() == 3);
        assertFalse(badsFormatsReport.hasErrors());
        assertEquals("Colonne 1", capture.getColonnesDansOrdreFichier().get(0).getName());
        assertEquals("Colonne 2", capture.getColonnesDansOrdreFichier().get(1).getName());
        assertEquals("Colonne 3", capture.getColonnesDansOrdreFichier().get(2).getName());
        //with error
        result = instance.readNomDesColonnes(m.versionFile, badsFormatsReport, parser, 5L, requestProperties, datasetDescriptor);
        assertTrue(6l==result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Il n'y a aucune variable dans le fichier. </p>", badsFormatsReport.getHTMLMessages());
    
    }

    /**
     *
     */
    public class AbstractVerificateurEnteteFluxMeteoImpl extends AbstractVerificateurEnteteFluxMeteo {

        Boolean nextLine = false;
        Boolean jumpReadColumFunction = false;

        @Override
        protected long readNomDesColonnes(VersionFile version, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties, DatasetDescriptor datasetDescriptor) {
            if (jumpReadColumFunction) {
                return super.readNomDesColonnes(version, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
            }
            assertTrue(8L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readUnites(VersionFile version, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties, DatasetDescriptor datasetDescriptor) {
            assertTrue(9L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readSite(VersionFile version, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties) {
            assertTrue(0L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readEmptyLine(BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber) {
            assertTrue(6L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readFrequence(IRequestProperties requestProperties, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber) {
            assertTrue(2L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readDatatype(BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber) {
            assertTrue(1L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long readBeginAndEndDates(VersionFile version, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties, DatasetDescriptor datasetDescriptor) {
            assertTrue(3L == lineNumber);
            return lineNumber + 2;
        }

        @Override
        protected long readCommentaires(BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties) {
            assertTrue(5L == lineNumber);
            return lineNumber + 1;
        }

        @Override
        protected long jumpLines(CSVParser parser, long lineNumber, int numberOfJumpedLines, BadsFormatsReport badsFormatsReport, String lineJumpedDescription) {
            assertTrue(7L == lineNumber);
            return lineNumber + numberOfJumpedLines;
        }

        @Override
        protected void remplirVariablesTypeDonnees(DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) throws BusinessException {
            nextLine = !nextLine;
        }
    }

}
