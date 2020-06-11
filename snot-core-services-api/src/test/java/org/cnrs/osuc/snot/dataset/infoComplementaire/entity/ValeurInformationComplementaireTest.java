/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ValeurInformationComplementaireTest {

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
    ValeurInformationComplementaire instance;
    @Mock
    InformationComplementaire informationComplementaire;
    @Mock
    AbstractValeur valeurDecoree ;
    @Mock
    ValeurComplementDTO dto;
    @Mock
    ILocalizationManager localizationManager;

    /**
     *
     */
    public ValeurInformationComplementaireTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new ValeurInformationComplementaireImpl();
        instance.setLocalizationManager(localizationManager);
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getId_info method, of class ValeurInformationComplementaire.
     */
    @Test
    public void testGetId_info() {
        instance.id_info = 12;
        assertEquals(12L, instance.getId_info());
    }

    /**
     * Test of getIdentite method, of class ValeurInformationComplementaire.
     */
    @Test
    public void testGetIdentite() {
        instance.setIdentite(informationComplementaire);
        assertEquals(informationComplementaire, instance.getIdentite());
    }

    /**
     * Test of setValeurDecoree method, of class ValeurInformationComplementaire.
     */
    @Test
    public void testSetValeurDecoree() {
        instance.setValeurDecoree(valeurDecoree);
        assertEquals(valeurDecoree, instance.valeurDecoree);
    }

    /**
     * Test of rangerComplementDTO method, of class ValeurInformationComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testRangerComplementDTO() throws Exception {
        instance.rangerComplementDTO(dto);
        assertEquals(dto, ((ValeurInformationComplementaireImpl)instance).dto);
    }

    /**
     * Test of setLocalizationManager method, of class ValeurInformationComplementaire.
     */
    @Test
    public void testSetLocalizationManager() {
        assertEquals(localizationManager, instance.localizationManager);
    }

    /**
     * Test of valeurToString method, of class ValeurInformationComplementaire.
     */
    @Test
    public void testValeurToString() {
        assertEquals("valeur", instance.valeurToString());
    }

    /**
     *
     */
    public class ValeurInformationComplementaireImpl extends ValeurInformationComplementaire {

        private static final long serialVersionUID = 1L;

        private ValeurComplementDTO dto;

        @Override
        public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
            this.dto = dto;
        }

        @Override
        public String valeurToString() {
            return "valeur";
        }
    }
    
}
