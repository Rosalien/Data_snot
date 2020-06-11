/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import org.cnrs.osuc.snot.synthesis.impl.jfreechart.MyTimeSeries;
import org.cnrs.osuc.snot.synthesis.impl.jfreechart.AbstractOrdonnee;
import org.cnrs.osuc.snot.synthesis.impl.jfreechart.FabriqueDatasetOrdonnees;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.synthesis.Facteur;
import org.cnrs.osuc.snot.synthesis.IGraphPresenceAbsence;
import org.cnrs.osuc.snot.synthesis.ITypeGraphiqueSynthese;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.inra.ecoinfo.utils.DateUtil;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

/**
 *
 * @author tcherniatinsky
 */
@Ignore
public class FabriqueDatasetOrdonneesTest {

    /**
     *
     */
    public static final String TOUT = "MSG_TOUT";

    /**
     *
     */
    public static final String AUCUN = "MSG_AUCUNE";

    /**
     *
     */
    public static final String PARTIE = "MSG_PARTIEL";

    /**
     *
     */
    public static final String TITRE = "TITRE";

    /**
     *
     */
    public static final String PROFONDEUR = "PROFONDEUR";
    private static final float ABSENT = -9999f;

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

    FabriqueDatasetOrdonnees instance;
    List<GenericSynthesisValue> synthesisValues = new LinkedList();
    List<LocalDateTime> dates = new LinkedList();

    /**
     *
     */
    public FabriqueDatasetOrdonneesTest() {
    }

    /**
     *
     * @throws ParseException
     */
    @Before
    public void setUp() throws ParseException {
        instance = new FabriqueDatasetOrdonnees();
        dates.add(DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, "01/01/2010"));
        dates.add(DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, "08/01/2010"));
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of createTimeSeriesRepartitionVariable method, of class
     * FabriqueDatasetOrdonnees.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testCreateTimeSeriesRepartitionVariable() throws InterruptedException {
        List<GenericSynthesisValue> synthesisVariables = synthesisValues;
        boolean isSemiHoraire = false;
        List<AbstractOrdonnee> ordonnees = Arrays.asList(new AbstractOrdonnee[]{
            new Ordonnee(10, 5),
            new Ordonnee(25, 5)
        });
    }

    /**
     * Test of ordonnees method, of class FabriqueDatasetOrdonnees.
     */
    @Test
    public void testOrdonnees() {
        List<GenericSynthesisValue> synthesisValues = null;
        FabriqueDatasetOrdonnees instance = new FabriqueDatasetOrdonnees();
        List<AbstractOrdonnee> expResult = null;
        List<AbstractOrdonnee> result = instance.ordonnees(synthesisValues);
        assertEquals(expResult, result);
    }

    /**
     * Test of chercherCorrespondant method, of class FabriqueDatasetOrdonnees.
     */
    @Test
    @Ignore
    public void testChercherCorrespondant() {
        String ordonneeReelle = "";
        List<AbstractOrdonnee> ordonnees = null;
        FabriqueDatasetOrdonnees instance = new FabriqueDatasetOrdonnees();
        Float expResult = null;
        Float result = instance.chercherCorrespondant(ordonneeReelle, ordonnees);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of touteLesRepetitions method, of class FabriqueDatasetOrdonnees.
     */
    @Test
    public void testTouteLesRepetitions() {
        //aucune valeur
        List<Float> value = Arrays.asList(new Float[]{ABSENT, ABSENT, ABSENT, ABSENT});
        int result = instance.touteLesRepetitions(value);
        assertEquals(3, result);
        value = Arrays.asList(new Float[]{ABSENT, 212.0f, ABSENT, ABSENT});
        result = instance.touteLesRepetitions(value);
        assertEquals(2, result);
        value = Arrays.asList(new Float[]{0f, 14.2F, 152.4F, 952.3F});
        result = instance.touteLesRepetitions(value);
        assertEquals(1, result);
    }

    /**
     *
     * @param f
     * @return
     */
    public Facteur buildFacteurFabrique(int f) {
        Facteur facteur = new Facteur(f, true);
        assertEquals(String.valueOf(f), facteur.toString());
        return facteur;
    }

    private void buildSynthesisValue() {
        synthesisValues.clear();
        synthesisValues.add(getMockSynthsis(10, 0, 12.0f));
        synthesisValues.add(getMockSynthsis(10, 0, 14.0f));
        synthesisValues.add(getMockSynthsis(10, 1, -9999f));
        synthesisValues.add(getMockSynthsis(10, 1, -13f));
        synthesisValues.add(getMockSynthsis(25, 0, -9999f));
        synthesisValues.add(getMockSynthsis(25, 0, -9999f));
        synthesisValues.add(getMockSynthsis(25, 1, 25.2f));
        synthesisValues.add(getMockSynthsis(25, 1, 12.3f));
    }

    private GenericSynthesisValue getMockSynthsis(int ordonnee, int date, Float value) {
        SynthesisValue mock = Mockito.mock(SynthesisValue.class);
        when(mock.getOrdonnee()).thenReturn(buildFacteurFabrique(ordonnee));
        when(mock.getValueFloat()).thenReturn(value);
        when(mock.getDate()).thenReturn(dates.get(date));
        return mock;
    }

    private void verifyResult(Map<String, Map<LocalDateTime, List<Float>>> result) {
        assertTrue(12.0f == result.get("10").get(dates.get(0)).get(0));
        assertTrue(14.0f == result.get("10").get(dates.get(0)).get(1));
        assertTrue(-9999f == result.get("10").get(dates.get(1)).get(0));
        assertTrue(-13f == result.get("10").get(dates.get(1)).get(1));
        assertTrue(-9999f == result.get("25").get(dates.get(0)).get(0));
        assertTrue(-9999f == result.get("25").get(dates.get(0)).get(1));
        assertTrue(25.2f == result.get("25").get(dates.get(1)).get(0));
        assertTrue(12.3f == result.get("25").get(dates.get(1)).get(1));
    }

    /**
     * Test of construireTimeSerieRepartition method, of class FabriqueDatasetOrdonnees.
     */
    @Test
    public void testConstruireTimeSerieRepartition() {
        Map<String, Map<LocalDateTime, List<Float>>> donneesTS = null;
        boolean semiHoraire = false;
        List<AbstractOrdonnee> ordonnees = null;
        FabriqueDatasetOrdonnees instance = new FabriqueDatasetOrdonnees();
        List<MyTimeSeries> expResult = null;
        List<MyTimeSeries> result = instance.construireTimeSerieRepartition(donneesTS, semiHoraire, ordonnees);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of construireDonneesTimesSeries method, of class FabriqueDatasetOrdonnees.
     */
    @Test
    public void testConstruireDonneesTimesSeries() {
        List<GenericSynthesisValue> synthesisValues = null;
        FabriqueDatasetOrdonnees instance = new FabriqueDatasetOrdonnees();
        Map<String, Map<LocalDateTime, List<Float>>> expResult = null;
        Map<String, Map<LocalDateTime, List<Float>>> result = instance.construireDonneesTimesSeries(synthesisValues);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    abstract class SynthesisValue extends GenericSynthesisValue implements ITypeGraphiqueSynthese, IGraphPresenceAbsence {

    }


    class Ordonnee extends AbstractOrdonnee {

        int ordonneeReelle;
        int ordonneeFictive;

        private Ordonnee(int ordonneeReelle, int ordonneeFictive) {
            this.ordonneeReelle = ordonneeReelle;
            this.ordonneeFictive = ordonneeFictive;
        }

        @Override
        public Integer getSonOrdonnee() {
            return ordonneeReelle;
        }

        @Override
        public int compareTo(AbstractOrdonnee ordonnee) {
            if (ordonneeReelle == ordonnee.getSonOrdonnee()) {
                return 0;
            }
            return ordonneeReelle > ordonnee.getSonOrdonnee() ? 1 : -1;
        }

        @Override
        public boolean uneOrdonneeVaut(String o) {
            return o.equals(getOrdonneeReelle());
        }

        @Override
        public String getOrdonneeReelle() {
            return String.valueOf(ordonneeReelle);

        }
    }

}
