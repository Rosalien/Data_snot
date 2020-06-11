package org.cnrs.osuc.snot.filecomp;

import com.google.common.base.Strings;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import org.concordion.api.Unimplemented;
import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.filecomp.entity.FileComp;
import org.cnrs.osuc.snot.SnotTransactionalTestFixtureExecutionListener;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.FileWithFolderCreator;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class LoadFilesFixture extends org.inra.ecoinfo.extraction.AbstractExtractionFixture {

    /**
     *
     */
    public LoadFilesFixture() {
        super();
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     * @param titleFile
     * @param fileTypeName
     * @param fromValid
     * @param toValid
     * @param comment
     * @param path
     * @return
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    public String loadFile(String titleFile, String fileTypeName, String fromValid, String toValid, String comment, String path) throws BusinessException {
    	String[] fileNames;
        LocalDateTime startDate, endDate;
        comment = comment == null ? "defaultComment" : comment;
        try {
            fileNames = this.loadFile(titleFile, fileTypeName, path);
            startDate = Strings.isNullOrEmpty(fromValid) ? null : DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, fromValid);
            endDate = Strings.isNullOrEmpty(toValid) ? null : DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, toValid);
            List<String> descriptions = new LinkedList<>();
            descriptions.add(comment);
            descriptions.add(comment);
            //this.insertFile(fileNames, fileTypeName, startDate, endDate, new File(path).length(), descriptions);
        } catch (BusinessException | DateTimeParseException e) {
            return Boolean.FALSE.toString();
        }
        FileComp fileComp = SnotTransactionalTestFixtureExecutionListener.fileCompManager.getFileCompByCode(fileTypeName + "@" + Utils.createCodeFromString(titleFile))
                .orElseThrow(BusinessException::new);
        assertEquals(titleFile, fileComp.getFileName());
        assertEquals(fileComp.getFileType().getCode(), fileTypeName);
        assertEquals(startDate, fileComp.getDateDebutPeriode());
        assertEquals(endDate, fileComp.getDateFinPeriode());
        assertEquals(LocalDateTime.now(), fileComp.getCreateDate());
        assertEquals((int) new File(path).length(), fileComp.getSize());
        assertEquals(comment, fileComp.getDescription());
        assertNotNull(new File(String.format("%s/%s/%s/%s", SnotTransactionalTestFixtureExecutionListener.fileCompConfiguration.getRepositoryFiles(), fileTypeName, Utils.createCodeFromString(titleFile), titleFile)));
        return Boolean.TRUE.toString();
    }

    /**
     *
     * @return
     */
    public String checkConforme() {
        return Boolean.TRUE.toString();
    }

    /*private void  insertFile(String[] fileNames, String fileTypeName, LocalDateTime fromValid, LocalDateTime toValid, Long size, List<String> descriptions) throws PersistenceException, BusinessException {
        SnotTransactionalTestFixtureExecutionListener.fileCompManager.insertFile(fileNames, fileTypeName, fromValid, toValid, size, descriptions, false);
    }*/

    private String[] loadFile(String titleFile, String typeFile, String path) throws BusinessException {
        String[] fileNames = new String[2];
        String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());
        String filePath;
        filePath = String.format("%s/%s/%s", SnotTransactionalTestFixtureExecutionListener.fileCompConfiguration.getRepositoryFiles(), typeFile, fileName);
        File fileUploadPath = FileWithFolderCreator.createFile(filePath);
        fileNames[0] = fileName;
        fileNames[1] = fileUploadPath.getName();
        byte[] byteToCopy;
        File file = new File(path);
        try (InputStream is = new FileInputStream(file)) {
            byteToCopy = new byte[(int) file.length()];
            is.read(byteToCopy);
        } catch (IOException e) {
            throw new BusinessException("impossible de télécharger le fichier", e);
        }
        //SnotTransactionalTestFixtureExecutionListener.fileCompManager.copyFile(fileUploadPath, byteToCopy, fileName);
        return fileNames;
    }
}
