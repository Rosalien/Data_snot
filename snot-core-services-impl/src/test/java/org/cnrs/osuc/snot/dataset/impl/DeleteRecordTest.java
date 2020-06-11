/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.cnrs.osuc.snot.dataset.impl.DeleteRecord;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.cnrs.osuc.snot.dataset.ILocalPublicationDAO;
import org.cnrs.osuc.snot.dataset.test.utils.MockUtils;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;


/**
 * 
 * @author ptcherniati
 */
public class DeleteRecordTest {

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

    DeleteRecord instance;
    MockUtils m;
    @Mock
    ILocalPublicationDAO publicationDAO;
    @Mock
    IVersionFileDAO versionFileDAO;

    /**
     *
     */
    public DeleteRecordTest() {}

    /**
     *
     * @throws PersistenceException
     */
    @Before
    public void setUp() throws PersistenceException {MockitoAnnotations.initMocks(this);
        this.m = MockUtils.getInstance();
        this.instance = new DeleteRecord();
        this.instance.setPublicationDAO(this.publicationDAO);
        this.instance.setVersionFileDAO(versionFileDAO);
        when(versionFileDAO.merge(m.versionFile)).thenReturn(m.versionFile);
}

    /**
     *
     */
    @After
    public void tearDown() {}

    /**
     * Test of deleteRecord method, of class DeleteRecord.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteRecord() throws Exception {
        this.instance.deleteRecord(this.m.versionFile);
        Mockito.verify(this.publicationDAO).removeVersion(this.m.versionFile.getId());
    }


}
