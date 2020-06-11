/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.colonne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author tcherniatinsky
 */
public class ExpertColonneTest {

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

    ExpertColonne instance;
    MockUtils m = MockUtils.getInstance();
    private List<Column> colonnesDansOrdreFichier = new ArrayList<>();
    private List<Column> columns = new ArrayList<>();
    @Mock
    Column column1;
    @Mock
    Column column2;
    @Mock
    Column column3;
    @Mock
    Column descriptorColumn1;
    @Mock
    Column descriptorColumn2;
    @Mock
    Column descriptorColumn3;
    @Mock
    DatasetDescriptor datasetDescriptor;
    private List<Column> colonnesDansDescripteur;

    /**
     *
     */
    public ExpertColonneTest() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
        instance = new ExpertColonne(columns);
        MockitoAnnotations.initMocks(this);
        instance.setColonnesDansOrdreFichier(colonnesDansOrdreFichier);
        colonnesDansDescripteur = Arrays.asList(new Column[]{descriptorColumn1, descriptorColumn2, descriptorColumn3});
        instance.setColonnesDansDescripteur(colonnesDansDescripteur);

    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of existeColoneVariable method, of class ExpertColonne.
     */
    @Test
    public void testExisteColoneVariable() {
        String[] nomsColonnes = new String[]{"column1", "column2", "column3"};
        // nominal
        boolean result = instance.existeColoneVariable(1, nomsColonnes);
        assertTrue(result);
        // nom de colonne null
        result = instance.existeColoneVariable(1, null);
        assertFalse(result);
        // nom de colonnes vide
        result = instance.existeColoneVariable(1, new String[]{});
        assertFalse(result);
        // manque des colonnes obligatoires
        result = instance.existeColoneVariable(4, nomsColonnes);
        assertFalse(result);

    }

    /**
     * Test of getColonnesDansDescripteur method, of class ExpertColonne.
     */
    @Test
    public void testGetColonnesDansDescripteur() {
        List<Column> expResult = colonnesDansDescripteur;
        List<Column> result = instance.getColonnesDansDescripteur();
        assertEquals(expResult, result);
    }

    /**
     * Test of getColonnesDansOrdreFichier method, of class ExpertColonne.
     */
    @Test
    public void testGetColonnesDansOrdreFichier() {
        List<Column> result = instance.getColonnesDansOrdreFichier();
        assertEquals(colonnesDansOrdreFichier, result);
    }

    /**
     * Test of getColumn method, of class ExpertColonne.
     */
    @Test
    public void testGetColumn_String() {
        String nom = "";
        when(descriptorColumn2.getName()).thenReturn("colonnne");
        Column result = instance.getColumn("Colonnne ");
        assertEquals(descriptorColumn2, result);
        result = instance.getColumn("Colonnne 3");
        assertEquals(null, result);
    }

    /**
     * Test of getColumn method, of class ExpertColonne.
     */
    @Test
    public void testGetColumn_String_List() {
        String nom = "Colonnne  ";
        List<Column> colonnes = colonnesDansDescripteur;
        when(descriptorColumn2.getName()).thenReturn("colonnne");
        int result = instance.getColumn(nom, colonnes);
        assertTrue(1==result);
        result = instance.getColumn("getColumn", colonnes);
        assertTrue(-1==result);
    }

    /**
     * Test of getNoQualityColumn method, of class ExpertColonne.
     */
    @Test
    public void testGetNoQualityColumn() {
        String nom = "Colonne ";
        instance.setColonnesDansOrdreFichier(colonnesDansDescripteur);
        when(descriptorColumn3.getRefVariableName()).thenReturn("colonne");
        int result = instance.getNoQualityColumn(nom);
        assertTrue(2==result);
        result = instance.getNoQualityColumn("otherColumn");
        assertTrue(-1==result);
    }

    /**
     * Test of getReferencedColumn method, of class ExpertColonne.
     */
    @Test
    public void testGetReferencedColumn() {
        instance.setColonnesDansOrdreFichier(colonnesDansDescripteur);
        when(descriptorColumn3.getRefVariableName()).thenReturn("colonne");
        when(descriptorColumn1.getName()).thenReturn("colonne");
        int result = instance.getReferencedColumn(2);
        assertTrue(0==result);
        when(descriptorColumn1.getName()).thenReturn("colonne 4");
        when(descriptorColumn2.getName()).thenReturn("Colonne");
        result = instance.getReferencedColumn(2);
        assertTrue(1==result);
        when(descriptorColumn3.getRefVariableName()).thenReturn("colonne 3");
        result = instance.getReferencedColumn(2);
        assertTrue(-1==result);
    }

    /**
     * Test of rangerColonnesCommeDansFichier method, of class ExpertColonne.
     */
    @Test
    public void testRangerColonnesCommeDansFichier() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        instance = spy(instance);
        Mockito.doReturn(Boolean.TRUE).when(instance).variablesObligatoiresPresentes(5, badsFormatsReport);
        descriptorColumn1 = new Column();
        descriptorColumn1.setName("Colonne 1");
        descriptorColumn2 = new Column();
        descriptorColumn2.setName("Colonne 2");
        descriptorColumn3 = new Column();
        descriptorColumn3.setName("Colonne 3");
        List<Column> colonnes = new ArrayList();
        colonnes.add(descriptorColumn1);
        colonnes.add(descriptorColumn2);
        colonnes.add(descriptorColumn3);
        instance.setColonnesDansDescripteur(colonnes);
        instance.rangerColonnesCommeDansFichier(new String[]{" Colonne 3", " Colonne 1", " Colonne 2 "}, 5, badsFormatsReport);
        verify(instance).variablesObligatoiresPresentes(5, badsFormatsReport);
        assertTrue(instance.colonnesDansDescripteur.isEmpty());
        assertTrue(descriptorColumn3.equals(instance.colonnesDansOrdreFichier.get(0)));
        assertTrue(descriptorColumn1.equals(instance.colonnesDansOrdreFichier.get(1)));
        assertTrue(descriptorColumn2.equals(instance.colonnesDansOrdreFichier.get(2)));
        assertTrue(descriptorColumn3.equals(instance.colonnesDansOrdreFichier.get(0)));
        assertFalse(badsFormatsReport.hasErrors());
        // null colonne
        badsFormatsReport = new BadsFormatsReport("erreur");
        colonnes.add(descriptorColumn1);
        colonnes.add(descriptorColumn2);
        colonnes.add(descriptorColumn3);
        instance.setColonnesDansDescripteur(colonnes);
        Mockito.doReturn(Boolean.TRUE).when(instance).variablesObligatoiresPresentes(5, badsFormatsReport);
        instance.rangerColonnesCommeDansFichier(new String[]{" Colonne 3", " Colonne 1", " Colonne 2 ", " Colonne 3 "}, 5, badsFormatsReport);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 5 : L'intitulé de la colonne 4  (\" Colonne 3 \") est incorrect </p>", badsFormatsReport.getHTMLMessages());
        // duplicate colonne
        badsFormatsReport = new BadsFormatsReport("erreur");
        colonnes.add(descriptorColumn1);
        colonnes.add(descriptorColumn2);
        colonnes.add(descriptorColumn3);
        instance.setColonnesDansDescripteur(colonnes);
        Mockito.doReturn(Boolean.TRUE).when(instance).variablesObligatoiresPresentes(5, badsFormatsReport);
        instance.rangerColonnesCommeDansFichier(new String[]{" Colonne 3", " Colonne 1", " Colonne 2 ", " Colonne 2 "}, 5, badsFormatsReport);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>- Ligne 5 : L'intitulé de la colonne 4  (\" Colonne 2 \") est incorrect </p>", badsFormatsReport.getHTMLMessages());
        
    }

    /**
     * Test of variablesObligatoiresPresentes method, of class ExpertColonne.
     */
    @Test
    public void testVariablesObligatoiresPresentes() {
        BadsFormatsReport badsFormatsReport = new BadsFormatsReport("erreur");
        when(descriptorColumn1.getFieldName()).thenReturn("colonne 1");
        when(descriptorColumn2.getFieldName()).thenReturn("colonne 2");
        when(descriptorColumn3.getFieldName()).thenReturn("colonne 3");
        boolean result = instance.variablesObligatoiresPresentes(5, badsFormatsReport);
        assertFalse(result);
        assertTrue(badsFormatsReport.hasErrors());
        assertEquals("<p style='color: red'>erreur :</p><p style='text-indent: 30px'>"
                + "- Ligne 5 : la variable colonne 1 est obligatoire. </p><p style='text-indent: 30px'>"
                + "- Ligne 5 : la variable colonne 2 est obligatoire. </p><p style='text-indent: 30px'>"
                + "- Ligne 5 : la variable colonne 3 est obligatoire. </p>", badsFormatsReport.getHTMLMessages());
        //with not mandatory column
        badsFormatsReport = new BadsFormatsReport("erreur");
        colonnesDansDescripteur=Arrays.asList(new Column[]{descriptorColumn2});
        when(descriptorColumn2.isNullable()).thenReturn(Boolean.TRUE); 
        instance.setColonnesDansDescripteur(colonnesDansDescripteur);       
        assertFalse(badsFormatsReport.hasErrors());
    }

}
