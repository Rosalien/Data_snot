
package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementItemListeDTO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;
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
public class ValeurInformationListeTest {

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
    ValeurInformationListe instance;
    @Mock
    InformationComplementaire informationComplementaire;
    @Mock
    AbstractValeur valeurDecoree ;
    @Mock
    ValeurComplementItemListeDTO dto;
    @Mock
    ILocalizationManager localizationManager;
    @Mock
    ItemListe liste;
    
    /**
     *
     */
    public ValeurInformationListeTest() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instance = new ValeurInformationListe.MaFabrique().fabrique();
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
     * Test of getValeur method, of class ValeurInformationListe.
     */
    @Test
    public void testGetValeur() {
        instance.setValeur(liste);
        ItemListe result = instance.getValeur();
        assertEquals(liste, result);
    }

    /**
     * Test of rangerComplementDTO method, of class ValeurInformationListe.
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
     * Test of valeurToString method, of class ValeurInformationListe.
     */
    @Test
    public void testValeurToString() {
        instance.setValeur(liste);
        when(liste.getNote()).thenReturn("note");
        String result = instance.valeurToString();
        assertEquals("note", result);
    }
    
}
