/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.impl;

import com.Ostermiller.util.CSVParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.ExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.DatasetDescriptorFluxMeteo;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.cnrs.osuc.snot.dataset.fluxmeteo.request.RequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.impl.AbstractProcessRecordSnotTest;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class AbstractProcessRecordFluxMeteoTest extends AbstractProcessRecordSnotTest {

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
    AbstractProcessRecordFluxMeteo instance;
    ErrorsReport errorsReport = new ErrorsReport();
    @Mock
    RequestPropertiesFluxMeteo requestProperties;

    /**
     *
     */
    public AbstractProcessRecordFluxMeteoTest() {
    }

    /**
     *
     */
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.instance = new AbstractProcessRecordFluxMeteoImpl();
        this.instance.setVariableDAO(this.m.variableDAO);
        this.instance.setVersionFileDAO(this.m.versionFileDAO);
        this.instance.setLocalizationManager(MockUtils.localizationManager);
        this.instance.setDatatypeVariableUniteSnotDAO(this.m.datatypeVariableUniteDAO);
        this.instance.setTraitementDAO(this.m.traitementDAO);
        super.datasetDescriptor = Mockito.mock(DatasetDescriptorFluxMeteo.class, "DatasetDescriptorFluxMeteo");
        initColumns();
        when(m.variableDAO.getByCode("colonne variable")).thenReturn(Optional.of(m.variable));
        Mockito.when(((DatasetDescriptorFluxMeteo) datasetDescriptor).getEnTete()).thenReturn(2);
    }

    /**
     *
     */
    @After
    @Override
    public void tearDown() {
    }

    /**
     * Test of processRecord method, of class AbstractProcessRecordFluxMeteo.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testProcessRecord() throws Exception {
        StringReader in = new StringReader("");
        CSVParser parser = new CSVParser(in);
        instance = new InstanceTest((PersistenceException) null);
        try {
            instance.processRecord(parser, m.versionFile, requestProperties, "utf-8", datasetDescriptor);
            assertTrue(((InstanceTest) instance).passage == 15);
        } catch (BusinessException e) {
            fail();
        }
        instance = new InstanceTest(new PersistenceException("erreur persitence"));
        try {
            instance.processRecord(parser, m.versionFile, requestProperties, "utf-8", datasetDescriptor);
            fail();
        } catch (BusinessException e) {
            assertEquals("org.inra.ecoinfo.utils.exceptions.PersistenceException: erreur persitence", e.getMessage());
        }
        instance = new InstanceTest(new IOException("erreur IO"));
        try {
            instance.processRecord(parser, m.versionFile, requestProperties, "utf-8", datasetDescriptor);
            fail();
        } catch (BusinessException e) {
            assertEquals("java.io.IOException: erreur IO", e.getMessage());
        }
    }

    /**
     * Test of rangerEnBase method, of class AbstractProcessRecordFluxMeteo.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRangerEnBase() throws Exception {
        Line ligne = Mockito.mock(Line.class);
        StringReader in = new StringReader("");
        CSVParser parser = new CSVParser(in);
        ExpertColonne expertColonne = Mockito.mock(ExpertColonne.class);
        Mockito.doReturn(expertColonne).when(requestProperties).getExpertColonne();
        Mockito.doReturn(columns).when(expertColonne).getColonnesDansOrdreFichier();
        instance.rangerEnBase(ligne, m.versionFile);
    }

    /**
     * Test of buildVariableHeaderAndSkipHeader method, of class
     * AbstractProcessRecordFluxMeteo.
     *
     * @throws java.lang.Exception
     */
    @Test
    @Override
    public void testBuildVariablesHeaderAndSkipHeader() throws Exception {
        Map<Variable, RealNode> realNodes = new HashMap<>();
        realNodes.put(variable4, rnVariable4);
        doReturn(realNodes).when(m.datatypeVariableUniteDAO).getRealNodesVariables(m.datatypeRealNode);
        CSVParser parser = Mockito.mock(CSVParser.class);
        ExpertColonne expertColonne = Mockito.mock(ExpertColonne.class);
        Mockito.doReturn(expertColonne).when(requestProperties).getExpertColonne();
        Mockito.doReturn(columns).when(expertColonne).getColonnesDansOrdreFichier();
        List<RealNode> result = instance.buildVariablesHeaderAndSkipHeader(parser, requestProperties, datasetDescriptor, m.datatypeRealNode);
        Mockito.verify(parser, times(2)).getLine();
        assertTrue(1 == result.size());
        assertEquals(rnVariable4, result.get(0));
    }

    @Override
    public void testGetDateTime() {
        
    }

    /**
     * Test of traiterLesDonnees method, of class
     * AbstractProcessRecordFluxMeteo.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testTraiterLesDonnees() throws Exception {
        StringReader in = new StringReader("ligne 1;valeur 1;valeur 2\nligne 2;valeur 1;valeur 2");
        CSVParser parser = new CSVParser(in, ';');
        Line ligne = Mockito.mock(Line.class);
        List<RealNode> dbrealNode = Mockito.mock(List.class);
        ErrorsReport errorsReport = new ErrorsReport();
        instance = new InstanceTest(dbrealNode, ligne, true);
        List<Line> result = instance.traiterLesDonnees(parser, requestProperties, dbrealNode, datasetDescriptor, errorsReport);
        assertTrue(((InstanceTest) instance).passage == 2);
        assertTrue(2 == result.size());
        assertEquals(ligne, result.get(0));
        assertEquals(ligne, result.get(1));
    }

    /**
     * Test of traiterDate method, of class AbstractProcessRecordFluxMeteo.
     */
    @Test
    public void testTraiterDate() {
        String[] values = new String[]{m.DATE_DEBUT, "2:45"};
        ErrorsReport errorsReport = new ErrorsReport();
        //extractio nde la date (datasetDescriptor, values, 4L, "date", errorsReport)
        Line result = instance.traiterDate(values, 4L, datasetDescriptor, errorsReport);
        assertEquals(m.dateDebut.toLocalDate(), result.getDate());
        assertEquals("02:45", DateUtil.getUTCDateTextFromLocalDateTime(result.getTime(), DateUtil.HH_MM));
        assertTrue(4L == result.getOriginalLineNumber());
        assertFalse(errorsReport.hasErrors());
        //erreur de format
        values = new String[]{"aujourd'hui", "midi"};
        result = instance.traiterDate(values, 4L, datasetDescriptor, errorsReport);
        assertTrue(errorsReport.hasErrors());
        assertEquals("-\"aujourd'hui\" n'est pas un format de date valide à la ligne 4 colonne 1 (double). La date doit-être au format \"dd/MM/yyyy\"\n"
                + "-\"midi\" n'est pas un format d'heure valide à la ligne 4 colonne 2 (time). L'heure doit-être au format \"HH:mm\"\n", errorsReport.getErrorsMessages());

    }

    /**
     * Test of traiterLesValeurs method, of class
     * AbstractProcessRecordFluxMeteo.
     */
    @Test
    public void testTraiterLesValeurs() {
        ExpertColonne expertColonne = Mockito.mock(ExpertColonne.class);
        Mockito.doReturn(expertColonne).when(requestProperties).getExpertColonne();
        when(requestProperties.getExpertColonne()).thenReturn(expertColonne);
        columns.clear();
        columns.add(columnDate);
        columns.add(columnTime);
        columns.add(columnStar);
        columns.add(columnQualityClass);
        columns.add(columnVariable);
        columns.add(columnVariable);
        columns.add(columnVariable);
        when(expertColonne.getColonnesDansOrdreFichier()).thenReturn(columns);
        Line uneLigne = Mockito.mock(Line.class);
        String[] values = new String[]{"date", "time", "star", "qc", "", "12.5", "13.4"};
        List<RealNode> dbRealNodes = Mockito.mock(List.class);
        when(dbRealNodes.get(0)).thenReturn(rnVariable4);
        when(dbRealNodes.get(1)).thenReturn(rnVariable5);
        List<VariableValue> variableValues = new LinkedList();
        when(uneLigne.getVariablesValues()).thenReturn(variableValues);
        VariableValue variableValue1 = Mockito.mock(VariableValue.class);
        VariableValue variableValue2 = Mockito.mock(VariableValue.class);
        VariableValue variableValueError = Mockito.mock(VariableValue.class);
        instance = spy(instance);
        Mockito.doReturn(variableValueError).when(instance).preparerValeur(any(), anyInt(), any(), anyInt(), any(), any());
        Mockito.doReturn(variableValue1).when(instance).preparerValeur(columnVariable, 5, values, 0, requestProperties, dbRealNodes);
        Mockito.doReturn(variableValue2).when(instance).preparerValeur(columnVariable, 6, values, 1, requestProperties, dbRealNodes);
        instance.traiterLesValeurs(uneLigne, values, requestProperties, dbRealNodes);
        assertTrue(2 == variableValues.size());
        assertEquals(variableValue1, variableValues.get(0));
        assertEquals(variableValue2, variableValues.get(1));
    }

    /**
     * Test of preparerValeur method, of class AbstractProcessRecordFluxMeteo.
     */
    @Test
    public void testPreparerValeur() {
        String[] values = new String[]{"12.5", "2"};
        int noVariable = 0;
        ExpertColonne expertColonne = Mockito.mock(ExpertColonne.class);
        List<RealNode> dbRealNodes = Mockito.mock(List.class);
        when(requestProperties.getExpertColonne()).thenReturn(expertColonne);
        when(expertColonne.getColonnesDansOrdreFichier()).thenReturn(columns);
        when(dbRealNodes.get(Matchers.anyInt())).thenReturn(m.variableRealNode);
        when(expertColonne.getNoQualityColumn("colonne1")).thenReturn(1);
        VariableValue result = instance.preparerValeur(m.column, 0, values, noVariable, requestProperties, dbRealNodes);
        assertTrue(2 == result.getQualityClass().getNumero());
        assertTrue(12.5 == result.getValue());
        assertEquals(m.variableRealNode, result.getRealNode());
    }

    /**
     * Test of faireRapport method, of class AbstractProcessRecordFluxMeteo.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testFaireRapport() throws Exception {
        List<Line> ligneEnErreur = null;
        ErrorsReport erreurs = new ErrorsReport();
        //null list
        try {
            instance.faireRapport(ligneEnErreur, erreurs);
            assertFalse(erreurs.hasErrors());
        } catch (PersistenceException e) {
            fail();
        }
        //empty list
        ligneEnErreur = new LinkedList();
        try {
            instance.faireRapport(ligneEnErreur, erreurs);
            assertFalse(erreurs.hasErrors());
            assertFalse(erreurs.hasErrors());
        } catch (PersistenceException e) {
            fail();
        }
        //no error
        Line ligne = Mockito.mock(Line.class);
        ligneEnErreur.add(ligne);
        try {
            instance.faireRapport(ligneEnErreur, erreurs);
            fail();
        } catch (PersistenceException e) {
            assertTrue(erreurs.hasErrors());
            assertEquals("-Une erreur s'est produite lors de l'insertion de la ligne 0 \n", erreurs.getErrorsMessages());
        }

    }

    /**
     * Test of mettreEnBase method, of class AbstractProcessRecordFluxMeteo.
     *
     * @throws org.inra.ecoinfo.utils.exceptions.PersistenceException
     */
    @Test
    public void testMettreEnBase() throws PersistenceException {
        Line ligne = Mockito.mock(Line.class);
        List<Line> mesures = new LinkedList();
        mesures.add(ligne);
        instance = spy(instance);
        List<Line> result = instance.mettreEnBase(mesures, m.versionFile);
        verify(instance).rangerEnBase(ligne, m.versionFile);
    }

    private void InstanceTest(List<VariableSnot> dbVariables, Line ligne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    public class AbstractProcessRecordFluxMeteoImpl extends AbstractProcessRecordFluxMeteo {

        @Override
        public void rangerEnBase(Line ligne, VersionFile versionFile) throws PersistenceException {
        }
    }

    /**
     *
     */
    public class InstanceTest extends AbstractProcessRecordFluxMeteo {

        int bvash = 1;
        int tld = 2;
        int meb = 4;
        int fr = 8;
        int passage = 0;
        PersistenceException pe;
        IOException ioe;
        Line ligne;
        List<RealNode> dbRealNodes;
        private boolean swithTraiterLesDonnees;

        List<Line> traiterLesDonnees = new LinkedList();
        List<Line> lignesEnErreur = new LinkedList();
        List<RealNode> buildVariableHeaderAndSkipHeader = new LinkedList();

        /**
         *
         * @param e
         */
        public InstanceTest(PersistenceException e) {
            this.pe = e;
        }

        /**
         *
         * @param e
         */
        public InstanceTest(IOException e) {
            this.ioe = e;
        }

        private InstanceTest(List<RealNode> dbRealNodes, Line ligne, boolean switchTLD) {
            this.ligne = ligne;
            this.dbRealNodes = dbRealNodes;
            this.swithTraiterLesDonnees = switchTLD;
        }

        @Override
        protected void traiterLesValeurs(Line uneLigne, String[] values, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, List<RealNode> dbRealNodes) {
            passage++;
            assertEquals(this.ligne, ligne);
            switch (passage) {
                case 1:
                    assertEquals("ligne 1", values[0]);
                    assertEquals("valeur 1", values[1]);
                    assertEquals("valeur 2", values[2]);
                    break;
                case 2:
                    assertEquals("ligne 2", values[0]);
                    assertEquals("valeur 1", values[1]);
                    assertEquals("valeur 2", values[2]);
                    break;
                default:
                    fail();
            }
        }

        @Override
        protected Line traiterDate(String[] values, long noLigne, DatasetDescriptor datasetDescriptor, ErrorsReport errorsReport) {
            switch ((int) noLigne) {
                case 1:
                    assertEquals("ligne 1", values[0]);
                    assertEquals("valeur 1", values[1]);
                    assertEquals("valeur 2", values[2]);
                    break;
                case 2:
                    assertEquals("ligne 2", values[0]);
                    assertEquals("valeur 1", values[1]);
                    assertEquals("valeur 2", values[2]);
                    break;
                default:
                    fail();
            }
            return ligne;
        }

        /**
         *
         * @param mesures
         * @param versionFile
         * @return
         */
        @Override
        protected List<Line> mettreEnBase(List<Line> mesures, VersionFile versionFile) {
            passage = passage + meb;
            assertEquals(traiterLesDonnees, mesures);
            return lignesEnErreur;
        }

        /**
         *
         * @param ligneEnErreur
         * @param erreurs
         * @throws PersistenceException
         */
        @Override
        protected void faireRapport(List<Line> ligneEnErreur, ErrorsReport erreurs) throws PersistenceException {
            passage = passage + fr;
            assertEquals(this.lignesEnErreur, ligneEnErreur);
            if (pe != null) {
                throw pe;
            }

        }

        @Override
        protected List<Line> traiterLesDonnees(CSVParser parser, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, List<RealNode> dbrealNodes, DatasetDescriptor datasetDescriptor, ErrorsReport errorsReport) throws IOException {
            if (swithTraiterLesDonnees) {
                return super.traiterLesDonnees(parser, requestPropertiesFluxMeteo, dbrealNodes, datasetDescriptor, errorsReport);
            }
            assertEquals(buildVariableHeaderAndSkipHeader, dbrealNodes);
            if (ioe != null) {
                throw ioe;
            }
            passage = passage + tld;
            return traiterLesDonnees;
        }

        /**
         *
         * @param parser
         * @param requestPropertiesFluxMeteo
         * @param datasetDescriptor
         * @param realNode
         * @return
         * @throws PersistenceException
         * @throws IOException
         */
        @Override
        protected List<RealNode> buildVariablesHeaderAndSkipHeader(CSVParser parser, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, DatasetDescriptor datasetDescriptor, RealNode realNode) throws PersistenceException, IOException {
            passage = passage + bvash;
            return buildVariableHeaderAndSkipHeader;
        }

        @Override
        public void rangerEnBase(Line ligne, VersionFile versionFile) throws PersistenceException {
        }
    }

}
