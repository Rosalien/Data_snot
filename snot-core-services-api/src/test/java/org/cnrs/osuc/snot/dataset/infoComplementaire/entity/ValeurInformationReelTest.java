/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementReelDTO;
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
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ValeurInformationReelTest {

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
    ValeurInformationReel instance;
    @Mock
    InformationComplementaire informationComplementaire;
    @Mock
    AbstractValeur valeurDecoree ;
    @Mock
    ValeurComplementReelDTO dto;
    @Mock
    ILocalizationManager localizationManager;
    
    /**
     *
     */
    public ValeurInformationReelTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new ValeurInformationReel.MaFabrique().fabrique();
        instance.setLocalizationManager(localizationManager);
        when(localizationManager.getMessage(ValeurInformationComplementaire.BUNDLE_SOURCE_PATH, "TYPE_DTO_INCORRECT")).thenReturn("message");
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getValeur method, of class ValeurInformationReel.
     */
    @Test
    public void testGetValeur() {
        instance.setValeur(12.6D);
        Double result = instance.getValeur();
        assertTrue(12.6D==result);
    }

    /**
     * Test of rangerComplementDTO method, of class ValeurInformationReel.
     * @throws java.lang.Exception
     */
    @Test
    public void testRangerComplementDTO() throws Exception {
        try {
            instance.rangerComplementDTO(dto);
        } catch (BusinessException ex) {
            fail();
        }
        try {
            instance.rangerComplementDTO(new ValeurComplementDTO());
            fail();
        } catch (BusinessException ex) {
            assertEquals("message", ex.getMessage());
        }
    }

    /**
     * Test of valeurToString method, of class ValeurInformationReel.
     */
    @Test
    public void testValeurToString() {
        instance.setValeur(12.6D);
        String result = instance.valeurToString();
        assertEquals("12.6",result);
    }
    
}
