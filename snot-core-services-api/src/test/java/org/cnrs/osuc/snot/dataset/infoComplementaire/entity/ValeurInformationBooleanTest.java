/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementBooleanDTO;
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
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ValeurInformationBooleanTest {

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
    ValeurInformationBoolean instance;
    @Mock
    InformationComplementaire informationComplementaire;
    @Mock
    AbstractValeur valeurDecoree ;
    @Mock
    ValeurComplementBooleanDTO dto;
    @Mock
    ILocalizationManager localizationManager;
    
    /**
     *
     */
    public ValeurInformationBooleanTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new ValeurInformationBoolean.MaFabrique().fabrique();
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
     * Test of getValeur method, of class ValeurInformationBoolean.
     */
    @Test
    public void testGetValeur() {
        instance.setValeur(true);
        boolean result = instance.getValeur();
        assertTrue(result);
        instance.setValeur(false);
        result = instance.getValeur();
        assertFalse(result);
    }

    /**
     * Test of rangerComplementDTO method, of class ValeurInformationBoolean.
     */
    @Test
    public void testRangerComplementDTO() {
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
     * Test of valeurToString method, of class ValeurInformationBoolean.
     */
    @Test
    public void testValeurToString() {
        instance.setValeur(true);
        assertEquals("true", instance.valeurToString());
        instance.setValeur(false);
        assertEquals("false", instance.valeurToString());
    }
    
}
