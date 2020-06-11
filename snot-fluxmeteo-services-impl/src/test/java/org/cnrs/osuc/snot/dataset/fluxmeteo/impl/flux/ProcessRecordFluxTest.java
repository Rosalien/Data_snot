package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.flux;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.DateUtil;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ProcessRecordFluxTest {

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

    ProcessRecordFluxImpl instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    IMesureFluxDAO mesureFluxDAO;
    @Mock
    Line line;
    @Mock
    MesureImpl mesure;
    @Mock
    ValeurImpl valeur;
    @Mock
    VariableValue variableValue;
    List<VariableValue> variableValues = new LinkedList<>();
    LocalTime time;

    /**
     *
     */
    public ProcessRecordFluxTest() {
    }

    /**
     *
     * @throws ParseException
     */
    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        time = DateUtil.readLocalTimeFromText(DateUtil.HH_MM, "23:30");
        instance = new ProcessRecordFluxImpl();
        instance.setMesureFluxDAO(mesureFluxDAO);
        variableValues.add(variableValue);
        variableValues.add(variableValue);
        when(line.getVariablesValues()).thenReturn(variableValues);
        when(variableValue.getRealNode()).thenReturn(m.variableRealNode);
        when(line.getDate()).thenReturn(m.dateDebut.toLocalDate());
        when(line.getTime()).thenReturn(time);
        when(line.getOriginalLineNumber()).thenReturn(2L);
        when(variableValue.getValue()).thenReturn(12.4F);
        when(variableValue.getQualityClass()).thenReturn(QualityClass.MOYEN);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of rangerEnBase method, of class ProcessRecordFlux.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRangerEnBase() throws Exception {
        List<ValeurImpl> valeurs = new LinkedList<>();
        when(mesure.getValeursFluxTour()).thenReturn(valeurs);
        instance.rangerEnBase(line, m.versionFile);
        verify(mesureFluxDAO).saveOrUpdate(mesure);
        verify(valeur, times(2)).setMesure(mesure);
        assertEquals(valeur, valeurs.get(0));
        assertEquals(valeur, valeurs.get(1));
    }

    /**
     *
     */
    public class ValeurImpl extends ValeurFluxTour<MesureImpl> {

        MesureImpl mesureImpl;

        @Override
        public void setMesure(MesureImpl mesureFlux) {
            this.mesureImpl = mesureFlux;
        }

        @Override
        public MesureImpl getMesure() {
            return mesureImpl;
        }

    }

    /**
     *
     */
    public class MesureImpl extends MesureFlux<ValeurImpl> {

        List<ValeurImpl> valeurs = new LinkedList();

        /**
         *
         * @return
         */
        @Override
        public List<ValeurImpl> getValeursFluxTour() {
            return valeurs;
        }

        /**
         *
         * @param valeursFluxTour
         */
        @Override
        public void setValeursFluxTour(List<ValeurImpl> valeursFluxTour) {
            this.valeurs = valeursFluxTour;
        }
    }

    /**
     *
     */
    public class ProcessRecordFluxImpl extends ProcessRecordFlux<MesureImpl, ValeurImpl> {

        @Override
        public ValeurImpl getNewValeurFlux(Float value, MesureImpl mesureFlux, RealNode realNode, QualityClass qualityClass) {
            return valeur;
        }

        @Override
        public MesureImpl getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
            return mesure;
        }
    }

}
