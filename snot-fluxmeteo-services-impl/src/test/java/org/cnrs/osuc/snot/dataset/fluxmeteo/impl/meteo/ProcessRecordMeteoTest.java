/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo;
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
public class ProcessRecordMeteoTest {

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

    ProcessRecordMeteoImpl instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    IMesureMeteoDAO mesureMeteoDAO;
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
    public ProcessRecordMeteoTest() {
    }

    /**
     *
     * @throws ParseException
     */
    @Before
    public void setUp() throws ParseException {
        MockitoAnnotations.initMocks(this);
        time = DateUtil.readLocalTimeFromText(DateUtil.HH_MM,"23:30");
        instance = new ProcessRecordMeteoImpl();
        instance.setMesureMeteoDAO(mesureMeteoDAO);
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
     * Test of rangerEnBase method, of class ProcessRecordMeteo.
     * @throws java.lang.Exception
     */
    @Test
    public void testRangerEnBase() throws Exception {
        List<ValeurImpl> valeurs = new LinkedList<>();
        when(mesure.getValeurs()).thenReturn(valeurs);
        instance.rangerEnBase(line, m.versionFile);
        verify(mesureMeteoDAO).saveOrUpdate(mesure);
        verify(valeur, times(2)).setMesure(mesure);
        assertEquals(valeur, valeurs.get(0));
        assertEquals(valeur, valeurs.get(1));
    }

    /**
     *
     */
    public class ValeurImpl extends ValeurMeteo<MesureImpl> {

        MesureImpl mesureImpl;

    }

    /**
     *
     */
    public class MesureImpl extends MesureMeteo<ValeurImpl> {

        List<ValeurImpl> valeurs = new LinkedList();

        @Override
        public List<ValeurImpl> getValeurs() {
            return valeurs;
        }

        /**
         *
         * @param valeursMeteo
         */
        @Override
        public void setValeurs(List<ValeurImpl> valeursMeteo) {
            this.valeurs = valeursMeteo;
        }

    }

    /**
     *
     */
    public class ProcessRecordMeteoImpl extends ProcessRecordMeteo<MesureImpl, ValeurImpl> {

        @Override
        public ValeurImpl getNewValeurMeteo(Float value, MesureImpl mesureMeteo, RealNode realNode, QualityClass qualityClass) {
            return valeur;
        }

        @Override
        public MesureImpl getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
            return mesure;
        }
    }

}
