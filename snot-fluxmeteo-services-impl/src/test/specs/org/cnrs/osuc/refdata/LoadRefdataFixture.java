package org.cnrs.osuc.refdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.inra.ecoinfo.AbstractTestFixture;
import org.inra.ecoinfo.ConcordionSpringJunit4ClassRunner;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import static org.inra.ecoinfo.TransactionalTestFixtureExecutionListener.applicationContext;
import org.inra.ecoinfo.notifications.exceptions.UpdateNotificationException;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.IMetadataManager;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
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
@Transactional(rollbackFor = Exception.class, readOnly = false, transactionManager = "transactionManager")
@TestExecutionListeners(listeners = {TransactionalTestFixtureExecutionListener.class})
public class LoadRefdataFixture extends AbstractTestFixture {

    private static final String ERROR_MESSAGE = "Line %d register : %s \t->\t expected : %s%n";

    /**
     *
     */
    public LoadRefdataFixture() {
        super();
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     * @param metadata
     * @param path
     * @return
     * @throws BusinessException
     * @throws IOException
     * @throws org.inra.ecoinfo.notifications.exceptions.UpdateNotificationException
     */
    public String uploadMetadata(String metadata, String path) throws BusinessException, IOException, UpdateNotificationException {
        File fileToUpload;
        fileToUpload = new File(path);
        if (!fileToUpload.exists()) {
            return "Le fichier " + path + " n'existe pas.";
        }
        try {
            getMetadataManager().uploadMetadatas(fileToUpload, metadata, fileToUpload.getName(), getUtilisateurConnected());
        } catch (BusinessException e) {
            return PersistenceException.getLastCauseExceptionMessage(e);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "true";
    }

    /**
     *
     * @return
     */
    public IMetadataManager getMetadataManager() {
        return ((IMetadataManager) applicationContext.getBean("metadataManager"));
    }

    /**
     *
     * @param metadata
     * @param fileToComparePath
     * @return
     */
    public String isConformeFile(String metadata, String fileToComparePath) {
        byte[] byteToCompare = null;
        String stringInDb = null, stringToCompare = null;
        List<String> listeInDB, listToCOmpare;
        try {
              stringInDb = getMetadataManager().buildModelGridMetadata(Utils.createCodeFromString(metadata)).formatAsCSV();

        } catch (IOException | BusinessException e) {
            return e.getMessage();
        }
        listeInDB = Arrays.asList(stringInDb.split("\n"));
        Collections.sort(listeInDB);
        File fileToCompare = new File(fileToComparePath);
        if (!fileToCompare.exists()) {
            return "Le fichier " + fileToComparePath + " n'existe pas.";
        }
        try (InputStream inputStream = new FileInputStream(fileToCompare)) {
            byteToCompare = new byte[(int) fileToCompare.length()];
            inputStream.read(byteToCompare);
        } catch (Exception e) {
            return "Le fichier " + fileToComparePath + " n'existe pas.";
        }
        try {
            stringToCompare = new String(org.inra.ecoinfo.utils.Utils.convertEncoding(byteToCompare, org.inra.ecoinfo.utils.Utils.ENCODING_UTF8));
        } catch (IOException e) {
            return e.getMessage();
        }
        listToCOmpare = Arrays.asList(stringToCompare.split("\n"));
        Collections.sort(listToCOmpare);
        if (listeInDB.size() == listToCOmpare.size() && listeInDB.equals(listToCOmpare)) {
            return "true";
        }

        StringBuilder errorToReturn = new StringBuilder();
        for (int i = 0; i < listeInDB.size(); i++) {
            final String dbString = listeInDB.get(i);
            final String compareString = listToCOmpare.get(i);
            if (!dbString.equals(compareString)) {
                errorToReturn.append(String.format(ERROR_MESSAGE, i, listeInDB.get(i), listToCOmpare.get(i) == null ? "" : listToCOmpare.get(i)));
            }
        }
        return errorToReturn.toString();

    }

    /**
     *
     * @param metadata
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String getFirstLine(String metadata) {
        try {
            ModelGridMetadata model = getMetadataManager().buildModelGridMetadata(Utils.createCodeFromString(metadata));

            String line = "";
            for (ColumnModelGridMetadata column : model.getLineModelGridMetadataAt(0).getColumnsModelGridMetadatas()) {
                String value = column.getValue();
                if ("Version de traitement".equals(metadata) && value.contains(",")) {
                    line += " ";
                    String[] values = value.split(",");
                    Arrays.sort(values);
                    for (int i = 0; i < values.length; i++) {
                        line += values[i];
                        if (i != values.length - 1) {
                            line += ",";
                        }
                    }
                } else {
                    line += " " + value;
                }
            }
            return line;
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}
