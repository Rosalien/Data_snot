package org.cnrs.osuc.snot;

import java.io.IOException;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.inra.ecoinfo.TransactionalTestFixtureExecutionListener;
import org.inra.ecoinfo.dataset.IDatasetManager;
import org.inra.ecoinfo.dataset.versioning.IDatasetDAO;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.extraction.IExtractionManager;
import org.inra.ecoinfo.filecomp.IFileCompManager;
import org.inra.ecoinfo.filecomp.config.impl.IFileCompConfiguration;
import org.cnrs.osuc.snot.refdata.variable.IVariableSnotDAO;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.notifications.INotificationsManager;
import org.inra.ecoinfo.refdata.IMetadataManager;
import org.inra.ecoinfo.refdata.sitethemedatatype.ISiteThemeDatatypeDAO;
import org.inra.ecoinfo.refdata.variable.IVariableDAO;
import org.springframework.test.context.TestContext;
import org.springframework.transaction.annotation.Transactional;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;

/**
 *
 * @author ptcherniati
 */
public class SnotTransactionalTestFixtureExecutionListener extends TransactionalTestFixtureExecutionListener {

    /**
     *
     */
    public static ISiteSnotDAO siteSnotDAO = null;

    /**
     *
     */
    public static IVariableSnotDAO variableSnotDAO = null;
    public static IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO = null;

    /**
     *
     */
    public static IMetadataManager metadataManager;

    /**
     *
     */
    public static IDatasetManager datasetManager;

    /**
     *
     */
    public static IDatasetDAO datasetDAO;

    /**
     *
     */
    public static IVersionFileDAO versionFileDAO;

    /**
     *
     */
    public static IVariableDAO variableDAO;

    /**
     *
     */
    public static ILocalizationManager localizationManager;

    /**
     *
     */
    public static IExtractionManager extractionManager;

    /**
     *
     */
    public static INotificationsManager notificationsManager;

    /**
     *
     */
    public static IFileCompManager fileCompManager;

    /**
     *
     */
    public static IFileCompConfiguration fileCompConfiguration;

    /**
     *
     */
    public static ISiteThemeDatatypeDAO siteThemeDatatypeDAO;
    private static String testName = "";

    /**
     * Before test class.
     *
     * @param testContext
     * @link(TestContext) the test context
     * @throws Exception the exception @see
     * org.springframework.test.context.support.AbstractTestExecutionListener
     * #beforeTestClass(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestClass(final TestContext testContext) throws Exception {
        super.beforeTestClass(testContext);
        SnotTransactionalTestFixtureExecutionListener.metadataManager = (IMetadataManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("metadataManager");
        SnotTransactionalTestFixtureExecutionListener.datasetManager = (IDatasetManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("datasetManager");
        SnotTransactionalTestFixtureExecutionListener.datasetDAO = (IDatasetDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("datasetDAO");
        SnotTransactionalTestFixtureExecutionListener.versionFileDAO = (IVersionFileDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("versionFileDAO");
        SnotTransactionalTestFixtureExecutionListener.variableDAO = (IVariableDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("variableDAO");
        SnotTransactionalTestFixtureExecutionListener.localizationManager = (ILocalizationManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("localizationManager");
        SnotTransactionalTestFixtureExecutionListener.extractionManager = (IExtractionManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("extractionManager");
        SnotTransactionalTestFixtureExecutionListener.notificationsManager = (INotificationsManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("notificationsManager");
        SnotTransactionalTestFixtureExecutionListener.fileCompManager = (IFileCompManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("fileCompManager");
        //SnotTransactionalTestFixtureExecutionListener.associateFileCompManager = (IAssociateFileCompManager) TransactionalTestFixtureExecutionListener.applicationContext.getBean("associateFileCompManager");
        SnotTransactionalTestFixtureExecutionListener.fileCompConfiguration = (IFileCompConfiguration) TransactionalTestFixtureExecutionListener.applicationContext.getBean("fileCompConfiguration");
        //SnotTransactionalTestFixtureExecutionListener.associateDAO = (IAssociateDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("associateDAO");
        SnotTransactionalTestFixtureExecutionListener.siteThemeDatatypeDAO = (ISiteThemeDatatypeDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("siteThemeDatatypeDAO");
        SnotTransactionalTestFixtureExecutionListener.siteSnotDAO = (ISiteSnotDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("siteSnotDAO");
        SnotTransactionalTestFixtureExecutionListener.variableSnotDAO = (IVariableSnotDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("variableDAO");
        SnotTransactionalTestFixtureExecutionListener.datatypeVariableUniteDAO = (IDatatypeVariableUniteSnotDAO) TransactionalTestFixtureExecutionListener.applicationContext.getBean("datatypeVariableUniteSnotDAO");

        if (testName.isEmpty()) {
            testName = "../target/concordion/" + testContext.getTestClass().getName().replaceAll(".*\\.(.*?)Fixture", "$1");
        }
    }

    /**
     * Clean tables.
     *
     * @throws SQLException the sQL exception
     */
    @Override
    protected void cleanTables() throws SQLException {
        getDatasourceConnexion()
                .getConnection()
                .prepareStatement(
                        "delete from group_utilisateur cascade;")
                .execute();
        getDatasourceConnexion()
                .getConnection()
                .prepareStatement(
                        "delete from utilisateur cascade;")
                .execute();
        getDatasourceConnexion()
                .getConnection()
                .prepareStatement(
                        "delete from groupe where group_name!='public';")
                .execute();
    }

    /**
     * After super class.
     *
     * @param testContext
     * @link(TestContext) the test context
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DataSetException the data set exception
     * @throws DatabaseUnitException the database unit exception
     * @throws SQLException the sQL exception
     * @throws Exception the exception
     * @link(TestContext) the test context
     */
    @Transactional(rollbackFor = Exception.class)
    void afterSuperClass(final TestContext testContext) throws IOException, DataSetException, DatabaseUnitException, SQLException, Exception {
        super.afterTestClass(testContext);
    }
}
