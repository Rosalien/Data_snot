package org.cnrs.osuc.snot.filecomp;

import org.concordion.api.Unimplemented;
import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import static org.inra.ecoinfo.TransactionalTestFixtureExecutionListener.startRequest;
import org.inra.ecoinfo.filecomp.IFileCompDAO;
import org.cnrs.osuc.snot.SnotTransactionalTestFixtureExecutionListener;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ptcherniati
 */
@RunWith(ConcordionSpringJunit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContextTest.xml"})
@Transactional(rollbackFor = Exception.class)
@TestExecutionListeners(listeners = {SnotTransactionalTestFixtureExecutionListener.class})
@Unimplemented
public class AssociateFilesFixture extends org.inra.ecoinfo.extraction.AbstractExtractionFixture {

    /**
     *
     */
    public AssociateFilesFixture() {
        super();
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     * @param nodePath
     * @param fileType
     * @param fileName
     * @return
     */
    public String associate(String nodePath, String fileType, String fileName) {
        startRequest();
        IFileCompDAO fileCompDAO = (IFileCompDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("fileCompDAO");
        /*IAssociateDAO associateDAO = SnotTransactionalTestFixtureExecutionListener.associateDAO;
        TransactionStatus tr = associateDAO.beginDefaultTransaction();
        String code = fileType.concat("@").concat(Utils.createCodeFromString(fileName));
        FileComp fileComp;
        fileComp = fileCompDAO.getByCode(code);
        Associate associate = null;
        associate = new Associate();
        associate.setFileComp(fileComp);
        associate.setResourcePath(nodePath);
        associateDAO.saveOrUpdate(associate);
        tr.flush();
        associateDAO.commitTransaction(tr);
        endRequest();*/
        return Boolean.TRUE.toString();
    }

    /**
     *
     * @return
     * @throws PersistenceException
     */
    /*public List<Associate> getAssociates() throws PersistenceException {
        startRequest();
        IAssociateDAO associateDAO = SnotTransactionalTestFixtureExecutionListener.associateDAO;
        List<Associate> associates = associateDAO.getAll(Associate.class);
        endRequest();
        return associates;
    }*/
}
