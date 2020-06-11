/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoBoolean;
import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoReel;
import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoEntier;
import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoListe;
import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoChaine;
import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.FabriqueInfoComplementaire;
import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementBooleanDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementChaineDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementEntierDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementItemListeDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementReelDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationBoolean;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationChaine;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationComplementaire;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationEntier;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationListe;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationReel;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class FabriqueInfoComplementaireTest {

    /**
     *
     */
    @Mock
    protected static FabriqueInfoBoolean fabriqueBoolean;

    /**
     *
     */
    @Mock
    protected static FabriqueInfoChaine fabriqueChaine;

    /**
     *
     */
    @Mock
    protected static FabriqueInfoEntier fabriqueEntier;

    /**
     *
     */
    @Mock
    protected static FabriqueInfoListe fabriqueListe;

    /**
     *
     */
    @Mock
    protected static FabriqueInfoReel fabriqueReel;

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
    FabriqueInfoComplementaire instance;
    @Mock
    AbstractValeur valeurDecoree;
    @Mock
    ValeurInformationBoolean vicb;
    @Mock
    ValeurInformationChaine vicc;
    @Mock
    ValeurInformationEntier vice;
    @Mock
    ValeurInformationReel vicr;
    @Mock
    ValeurInformationListe vicl;

    /**
     *
     */
    public FabriqueInfoComplementaireTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        instance = new FabriqueInfoComplementaire();
        MockitoAnnotations.initMocks(this);
        FabriqueInfoComplementaire.setFabriqueBoolean(fabriqueBoolean);
        FabriqueInfoComplementaire.setFabriqueChaine(fabriqueChaine);
        FabriqueInfoComplementaire.setFabriqueEntier(fabriqueEntier);
        FabriqueInfoComplementaire.setFabriqueReel(fabriqueReel);
        FabriqueInfoComplementaire.setFabriqueListe(fabriqueListe);
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_String_boolean() throws Exception {
        //avec chaine
        doReturn(vicb).when(fabriqueBoolean).construire(any(ValeurInformationBoolean.MaFabrique.class), any(ValeurComplementBooleanDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", true);
        verify(fabriqueBoolean).construire(any(ValeurInformationBoolean.MaFabrique.class), any(ValeurComplementBooleanDTO.class));
        assertEquals(vicb, result);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_3args_1() throws Exception {
        //avec chaine
        doReturn(vicb).when(fabriqueBoolean).construire(any(ValeurInformationBoolean.MaFabrique.class), any(ValeurComplementBooleanDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", true, valeurDecoree);
        verify(fabriqueBoolean).construire(any(ValeurInformationBoolean.MaFabrique.class), any(ValeurComplementBooleanDTO.class));
        assertEquals(vicb, result);
        verify(vicb).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicb);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_String_double() throws Exception {
        //avec chaine
        doReturn(vicr).when(fabriqueReel).construire(any(ValeurInformationReel.MaFabrique.class), any(ValeurComplementReelDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", 12.5);
        verify(fabriqueReel).construire(any(ValeurInformationReel.MaFabrique.class), any(ValeurComplementReelDTO.class));
        assertEquals(vicr, result);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_3args_2() throws Exception {
        //avec chaine
        doReturn(vicr).when(fabriqueReel).construire(any(ValeurInformationReel.MaFabrique.class), any(ValeurComplementReelDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", 13.2, valeurDecoree);
        verify(fabriqueReel).construire(any(ValeurInformationReel.MaFabrique.class), any(ValeurComplementReelDTO.class));
        assertEquals(vicr, result);
        verify(vicr).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicr);
        verify(vicr).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicr);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_String_int() throws Exception {
        //avec chaine
        doReturn(vice).when(fabriqueEntier).construire(any(ValeurInformationEntier.MaFabrique.class), any(ValeurComplementEntierDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", 125);
        verify(fabriqueEntier).construire(any(ValeurInformationEntier.MaFabrique.class), any(ValeurComplementEntierDTO.class));
        assertEquals(vice, result);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_3args_3() throws Exception {
        //avec chaine
        doReturn(vice).when(fabriqueEntier).construire(any(ValeurInformationEntier.MaFabrique.class), any(ValeurComplementEntierDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", 125, valeurDecoree);
        verify(fabriqueEntier).construire(any(ValeurInformationEntier.MaFabrique.class), any(ValeurComplementEntierDTO.class));
        assertEquals(vice, result);
        verify(vice).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vice);
        verify(vice).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vice);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_String_String() throws Exception {
        //avec chaine
        doReturn(vicc).when(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", "chaine");
        verify(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        assertEquals(vicc, result);
        //avec item
        doReturn(vicc).when(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        when(fabriqueChaine.isAInfoListe("nom")).thenReturn(true);
        result = FabriqueInfoComplementaire.fabriquer("nom", "chaine");
        verify(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        assertEquals(vicc, result);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_3args_4() throws Exception {
        //avec chaine
        doReturn(vicc).when(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", "12", valeurDecoree);
        verify(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        assertEquals(vicc, result);
        verify(vicc).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicc);
        //avec item
        when(fabriqueChaine.isAInfoListe("nom")).thenReturn(true);
        doReturn(vicc).when(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        result = FabriqueInfoComplementaire.fabriquer("nom", "12", valeurDecoree);
        verify(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        assertEquals(vicc, result);
        verify(vicc, times(2)).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree, times(2)).ajouterInfoComplementaire(vicc);
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_String_ItemListe() throws Exception {
        ItemListe itemListe = mock(ItemListe.class);
        doReturn(vicl).when(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", itemListe);
        assertEquals(vicl, result);
        verify(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
    }

    /**
     * Test of fabriquer method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquer_3args_5() throws Exception {
        doReturn(vicc).when(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("chaine", "string");
        assertEquals(vicc, result);
        verify(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
    }

    /**
     * Test of fabriquerAvecChaine method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquerAvecChaine() throws Exception {
        doReturn(vicc).when(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", "chaine", valeurDecoree);
        assertEquals(vicc, result);
        verify(fabriqueChaine).construire(any(ValeurInformationChaine.MaFabrique.class), any(ValeurComplementChaineDTO.class));
        verify(vicc).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicc);
    }

    /**
     * Test of fabriquerAvecItem method, of class FabriqueInfoComplementaire.
     * @throws java.lang.Exception
     */
    @Test
    public void testFabriquerAvecItem() throws Exception {
        ItemListe itemListe = mock(ItemListe.class);
        doReturn(vicl).when(fabriqueListe).construire(any(ValeurInformationListe.MaFabrique.class), any(ValeurComplementItemListeDTO.class));
        ValeurInformationComplementaire result = FabriqueInfoComplementaire.fabriquer("nom", itemListe, valeurDecoree);
        assertEquals(vicl, result);
        verify(vicl).setValeurDecoree(valeurDecoree);
        verify(valeurDecoree).ajouterInfoComplementaire(vicl);
    }

}
