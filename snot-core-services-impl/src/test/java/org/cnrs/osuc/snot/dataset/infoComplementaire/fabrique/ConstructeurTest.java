/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique.Constructeur;
import java.util.Optional;
import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationComplementaire;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.IInformationComplementaireDAO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ConstructeurTest {

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

    ConstructeurR instance;
    MockUtils m = MockUtils.getInstance();
    @Mock
    IFabrique<R> fabrique;
    @Mock
    ValeurComplementDTO valeurComplementDTO;
    @Mock
    AbstractValeur valeur;
    @Mock
    IInformationComplementaireDAO informationComplementaireDAO;
    @Mock
    InformationComplementaire informationComplementaire;
    R r;

    /**
     *
     */
    public ConstructeurTest() {
    }

    /**
     *
     * @throws PersistenceException
     */
    @Before
    public void setUp() throws PersistenceException {
        instance = new ConstructeurR();
        MockitoAnnotations.initMocks(this);
        instance.setInfoComplementaireDAO(informationComplementaireDAO);
        instance.setLocalizationManager(m.localizationManager);
        doReturn(Optional.of(informationComplementaire)).when(informationComplementaireDAO).getByNom("nom");
        when(valeurComplementDTO.getNom()).thenReturn("nom");
        r = new R(null);
        doReturn(r).when(fabrique).fabrique();
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of construire method, of class Constructeur.
     * @throws org.inra.ecoinfo.utils.exceptions.PersistenceException
     */
    @Test
    public void testConstruire_IFabrique_ValeurComplementDTO() throws PersistenceException {
        ValeurInformationComplementaire result;
        try {
            result = instance.construire(fabrique, valeurComplementDTO);
            assertEquals(informationComplementaire, result.getIdentite());
            assertEquals(valeurComplementDTO, ((R) result).dto);
        } catch (BusinessException e) {
            fail();
        }
    }

    /**
     * Test of construire method, of class Constructeur.
     * @throws java.lang.Exception
     */
    @Test
    public void testConstruire_3args() throws Exception {
        ValeurInformationComplementaire result;
        try {
            result = instance.construire(fabrique, valeurComplementDTO, valeur);
            assertEquals(informationComplementaire, result.getIdentite());
            assertEquals(valeurComplementDTO, ((R) result).dto);
        } catch (BusinessException e) {
            fail();
        }
    }

    /**
     *
     */
    public class R extends ValeurInformationComplementaire {

        ValeurComplementDTO dto;

        /**
         *
         * @param dto
         */
        public R(ValeurComplementDTO dto) {
            this.dto = dto;
        }

        @Override
        public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
            this.dto = dto;
        }

        @Override
        public String valeurToString() {
            return "string";
        }

    }

    /**
     *
     */
    public class ConstructeurR extends Constructeur<R> {

        /**
         *
         */
        public ConstructeurR() {
        }

    }

}
