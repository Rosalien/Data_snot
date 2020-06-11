package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.BadDelimiterException;
import com.Ostermiller.util.CSVParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDateTime;
import org.inra.ecoinfo.dataset.AbstractRecorder;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IDeleteRecord;
import org.cnrs.osuc.snot.dataset.IProcessRecord;
import org.cnrs.osuc.snot.dataset.IRecorderSnot;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesSnot;
import org.cnrs.osuc.snot.dataset.ITestFormat;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;
import org.cnrs.osuc.snot.refdata.variable.IVariableSnotDAO;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.SnotMessages;
import org.inra.ecoinfo.identification.IUtilisateurDAO;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.unite.IUniteDAO;
import org.inra.ecoinfo.system.Allocator;
import org.inra.ecoinfo.utils.ApplicationContextHolder;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadFormatException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Antoine Schellenberger"
 *
 */
public class SNOTRecorder extends AbstractRecorder implements IRecorderSnot {

    /**
     *
     */
    public static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.messages";

    /**
     *
     */
    public static final String PROPERTY_MSG_SITE_ABSENT = "SITE_ABSENT";

    /**
     *
     */
    public static final String PROPERTY_MSG_BAD_SITE = "BAD_SITE";

    /**
     *
     */
    public static final String PROPERTY_MSG_BAD_DATATYPE = "BAD_DATATYPE";

    /**
     *
     */
    public static final String PROPERTY_MSG_EMPTY_FREQUENCE = "EMPTY_FREQUENCE";

    /**
     *
     */
    public static final String PROPERTY_MSG_BAD_FILE_LENGTH = "EOF";

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(SNOTRecorder.class);

    /**
     * The Constant SNOT_DATASET_BUNDLE_NAME.
     */
    public static final String DATASET_DESCRIPTOR_PATH_PATTERN = "org/cnrs/osuc/snot/dataset/%s";

    /**
     *
     */
    public static final String PATTERN_TIME_SECONDE = "^([0-1][0-9]|2[0-3]):([0-6][0-9]):([0-6][0-9])$|24:00:00";

    /**
     * The Constant PATTERN_VARIABLE_EUROPEAN_NOM.
     */
    public static final String PATTERN_VARIABLE_EUROPEAN_NOM = "^(%s_([0-9]*)_([0-9]*))|(%s)$";

    /**
     * The Constant PROPERTY_MSG_MISMACH_COLUMN_HEADER.
     */
    public static final String PROPERTY_MSG_MISMACH_COLUMN_HEADER = "PROPERTY_MSG_MISMACH_COLUMN_HEADER";

    /**
     * The Constant PROPERTY_MSG_MISMACH_GENERIC_COLUMN_HEADER.
     */
    public static final String PROPERTY_MSG_MISMACH_GENERIC_COLUMN_HEADER = "PROPERTY_MSG_MISMACH_GENERIC_COLUMN_HEADER";

    /**
     * The Constant PROPERTY_MSG_MISMACH_COLUMN_HEADER_EUROPEAN_NOM.
     */
    public static final String PROPERTY_MSG_MISMACH_COLUMN_HEADER_EUROPEAN_NOM = "PROPERTY_MSG_MISMACH_COLUMN_HEADER_EUROPEAN_NOM";

    /**
     * The Constant PROPERTY_MSG_MISSING_EMPTY_LINE.
     */
    public static final String PROPERTY_MSG_MISSING_EMPTY_LINE = "PROPERTY_MSG_MISSING_EMPTY_LINE";

    /**
     * The Constant PROPERTY_MSG_MISSING_SITE.
     */
    public static final String PROPERTY_MSG_MISSING_SITE = "PROPERTY_MSG_MISSING_SITE";

    /**
     * The Constant PROPERTY_MSG_INVALID_SITE.
     */
    public static final String PROPERTY_MSG_INVALID_SITE = "PROPERTY_MSG_INVALID_SITE";

    /**
     * The Constant PROPERTY_MSG_INVALID_DATATYPE.
     */
    public static final String PROPERTY_MSG_INVALID_DATATYPE = "PROPERTY_MSG_INVALID_DATATYPE";

    /**
     *
     */
    public static final String PROPERTY_MSG_LAST_ELEMENT_IN_LIST = "PROPERTY_MSG_LAST_ELEMENT_IN_LIST";

    /**
     * The Constant PROPERTY_MSG_MISSING_DATATYPE.
     */
    public static final String PROPERTY_MSG_MISSING_DATATYPE = "PROPERTY_MSG_MISSING_DATATYPE";

    /**
     * The Constant PROPERTY_MSG_INVALID_SITE_REPOSITORY.
     */
    public static final String PROPERTY_MSG_INVALID_SITE_REPOSITORY = "PROPERTY_MSG_INVALID_SITE_REPOSITORY";

    /**
     * The Constant PROPERTY_MSG_MISSING_PLOT @link(String).
     */
    public static final String PROPERTY_MSG_MISSING_PLOT = "PROPERTY_MSG_MISSING_PLOT";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_PLOT = "PROPERTY_MSG_INVALID_PLOT";

    /**
     * The Constant PROPERTY_MSG_DOUBLON_LINE.
     */
    public static final String PROPERTY_MSG_DOUBLON_LINE = "PROPERTY_MSG_DOUBLON_LINE";

    /**
     * The Constant PROPERTY_MSG_MISSING_MONITORING_PLOT.
     */
    public static final String PROPERTY_MSG_MISSING_MONITORING_PLOT = "PROPERTY_MSG_MISSING_MONITORING_PLOT";

    /**
     * The Constant PROPERTY_MSG_MISSING_MONITORING_PLOT_WITH_LINE_NUMBER.
     */
    public static final String PROPERTY_MSG_MISSING_MONITORING_PLOT_WITH_LINE_NUMBER = "PROPERTY_MSG_MISSING_MONITORING_PLOT_WITH_LINE_NUMBER";

    /**
     *
     */
    public static final String PROPERTY_MSG_BAD_MONITORING_PLOT_WITH_LINE_NUMBER = "PROPERTY_MSG_BAD_MONITORING_PLOT_WITH_LINE_NUMBER";

    /**
     * The Constant PROPERTY_MSG_MISSING_TREATMENT.
     */
    public static final String PROPERTY_MSG_MISSING_TREATMENT = "PROPERTY_MSG_MISSING_TREATMENT";

    /**
     *
     */
    public static final String PROPERTY_MSG_INTITULE_ABSENT = "INTITULE_ABSENT";

    /**
     * The Constant PROPERTY_MSG_INVALID_TRAITEMENT.
     */
    /**
     * The Constant PROPERTY_MSG_MISSING_TREATMENT.
     */
    public static final String PROPERTY_MSG_DONNEE_MANQUANTE = "DONNEE_MANQUANTE";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_TRAITEMENT = "PROPERTY_MSG_INVALID_TRAITEMENT";

    /**
     * The Constant PROPERTY_MSG_INVALID_TRAITEMENT.
     */
    public static final String PROPERTY_MSG_INVALID_SITE_TREATMENT_VERSION = "PROPERTY_MSG_INVALID_SITE_TREATMENT_VERSION";

    /**
     * The Constant PROPERTY_MSG_MISSING_TREATMENT.
     */
    public static final String PROPERTY_MSG_MISSING_VERSION_TREATMENT = "TRAITEMENT_ABSENT";

    /**
     * The Constant PROPERTY_MSG_INVALID_TRAITEMENT.
     */
    public static final String PROPERTY_MSG_INVALID_VERSION_TRAITEMENT = "PROPERTY_MSG_INVALID_VERSION_TRAITEMENT";

    /**
     * The Constant PROPERTY_MSG_MISSING_BEGIN_TREATMENT_DATE.
     */
    public static final String PROPERTY_MSG_MISSING_BEGIN_TREATMENT_DATE = "PROPERTY_MSG_MISSING_BEGIN_TREATMENT_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_BEGIN_TREATMENT_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_BEGIN_TREATMENT_DATE = "PROPERTY_MSG_INVALID_BEGIN_TREATMENT_DATE";

    /**
     * The Constant PROPERTY_MSG_MISSING_END_TREATMENT_DATE.
     */
    public static final String PROPERTY_MSG_MISSING_END_TREATMENT_DATE = "PROPERTY_MSG_MISSING_END_TREATMENT_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_END_TREATMENT_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_END_TREATMENT_DATE = "PROPERTY_MSG_INVALID_END_TREATMENT_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_SPECIES.
     */
    public static final String PROPERTY_MSG_INVALID_SPECIES = "PROPERTY_MSG_INVALID_SPECIES";

    /**
     * The Constant PROPERTY_MSG_MISSING_SPECIES.
     */
    public static final String PROPERTY_MSG_MISSING_SPECIES = "PROPERTY_MSG_MISSING_SPECIES";

    /**
     * The Constant PROPERTY_MSG_MISSING_VERSION.
     */
    public static final String PROPERTY_MSG_MISSING_VERSION = "PROPERTY_MSG_MISSING_VERSION";

    /**
     * The Constant PROPERTY_MSG_MISSING_VERSION_TRAITEMENT.
     */
    public static final String PROPERTY_MSG_MISSING_VERSION_TRAITEMENT = "PROPERTY_MSG_MISSING_VERSION_TRAITEMENT";
    /**
     * The Constant PROPERTY_MSG_INVALID_PLANTING_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_PLANTING_DATE = "PROPERTY_MSG_INVALID_PLANTING_DATE";

    /**
     * The Constant PROPERTY_MSG_MISSING_PLANTING_DATE.
     */
    public static final String PROPERTY_MSG_MISSING_PLANTING_DATE = "PROPERTY_MSG_MISSING_PLANTING_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_DATE_HARVEST.
     */
    public static final String PROPERTY_MSG_INVALID_DATE_HARVEST = "PROPERTY_MSG_INVALID_DATE_HARVEST";

    /**
     * The Constant PROPERTY_MSG_MISSING_BEGIN_DATE.
     */
    public static final String PROPERTY_MSG_MISSING_BEGIN_DATE = "PROPERTY_MSG_MISSING_BEGIN_DATE";

    /**
     * The Constant PROPERTY_MSG_MISSING_END_DATE.
     */
    public static final String PROPERTY_MSG_MISSING_END_DATE = "PROPERTY_MSG_MISSING_END_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_BEGIN_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_BEGIN_DATE = "PROPERTY_MSG_INVALID_BEGIN_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_END_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_END_DATE = "PROPERTY_MSG_INVALID_END_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_INTERVAL_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_INTERVAL_DATE = "PROPERTY_MSG_INVALID_INTERVAL_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_VARIABLE.
     */
    public static final String PROPERTY_MSG_INVALID_VARIABLE = "PROPERTY_MSG_INVALID_VARIABLE";

    /**
     * The Constant PROPERTY_MSG_BAD_MIN_VALUE_FORMAT.
     */
    public static final String PROPERTY_MSG_BAD_MIN_VALUE_FORMAT = "PROPERTY_MSG_BAD_MIN_VALUE_FORMAT";

    /**
     * The Constant PROPERTY_MSG_BAD_MAX_VALUE_FORMAT.
     */
    public static final String PROPERTY_MSG_BAD_MAX_VALUE_FORMAT = "PROPERTY_MSG_BAD_MAX_VALUE_FORMAT";

    /**
     * The Constant PROPERTY_MSG_NO_DATA.
     */
    public static final String PROPERTY_MSG_NO_DATA = "PROPERTY_MSG_NO_DATA";

    /**
     * The Constant PROPERTY_MSG_INVALID_INTERVAL_FLOAT_VALUE.
     */
    public static final String PROPERTY_MSG_INVALID_INTERVAL_FLOAT_VALUE = "PROPERTY_MSG_INVALID_INTERVAL_FLOAT_VALUE";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_INTERVAL_DOUBLE_VALUE = "PROPERTY_MSG_INVALID_INTERVAL_DOUBLE_VALUE";

    /**
     * The Constant PROPERTY_MSG_INVALID_INT_VALUE.
     */
    public static final String PROPERTY_MSG_INVALID_INT_VALUE = "PROPERTY_MSG_INVALID_INT_VALUE";

    /**
     * The Constant PROPERTY_MSG_INVALID_FLOAT_VALUE.
     */
    public static final String PROPERTY_MSG_INVALID_FLOAT_VALUE = "PROPERTY_MSG_INVALID_FLOAT_VALUE";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_DOUBLE_VALUE = "PROPERTY_MSG_INVALID_DOUBLE_VALUE";

    /**
     * The Constant PROPERTY_MSG_INVALID_QUALITY_CLASS.
     */
    public static final String PROPERTY_MSG_INVALID_QUALITY_CLASS = "PROPERTY_MSG_INVALID_QUALITY_CLASS";

    /**
     * The Constant PROPERTY_MSG_INVALID_VALUE_OR_BAD_QUALITY_CLASS.
     */
    public static final String PROPERTY_MSG_INVALID_VALUE_OR_BAD_QUALITY_CLASS = "PROPERTY_MSG_INVALID_VALUE_OR_BAD_QUALITY_CLASS";

    /**
     * The Constant PROPERTY_MSG_MISSING_VALUE.
     */
    public static final String PROPERTY_MSG_MISSING_VALUE = "PROPERTY_MSG_MISSING_VALUE";

    /**
     * The Constant PROPERTY_MSG_INVALID_DATE.
     */
    public static final String PROPERTY_MSG_INVALID_DATE = "PROPERTY_MSG_INVALID_DATE";

    /**
     * The Constant PROPERTY_MSG_INVALID_TIME.
     */
    public static final String PROPERTY_MSG_INVALID_TIME = "PROPERTY_MSG_INVALID_TIME";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_DURATION = "PROPERTY_MSG_INVALID_DURATION";

    /**
     * The Constant PROPERTY_MSG_INVALID_INTERVAL_DATE_TIME.
     */
    public static final String PROPERTY_MSG_INVALID_INTERVAL_DATE_TIME = "PROPERTY_MSG_INVALID_INTERVAL_DATE_TIME";

    /**
     * The Constant PROPERTY_MSG_MISSING_UNIT_FOR_FARIABLE_AND_DATATYPE
     *
     * @link(String).
     */
    public static final String PROPERTY_MSG_MISSING_UNIT_FOR_FARIABLE_AND_DATATYPE = "PROPERTY_MSG_MISSING_UNIT_FOR_FARIABLE_AND_DATATYPE";

    /**
     * The Constant PROPERTY_MSG_EMPTY_FILE.
     */
    public static final String PROPERTY_MSG_EMPTY_FILE = "PROPERTY_MSG_EMPTY_FILE";

    /**
     * The Constant PROPERTY_MSG_DOUBLON_DATE.
     */
    public static final String PROPERTY_MSG_DOUBLON_DATE = "PROPERTY_MSG_DOUBLON_DATE";

    /**
     * The Constant PROPERTY_MSG_DOUBLON_DATE_TIME.
     */
    public static final String PROPERTY_MSG_DOUBLON_DATE_TIME = "PROPERTY_MSG_DOUBLON_DATE_TIME";

    /**
     * The Constant PROPERTY_MSG_DOUBLON_DATE_TIME_FOLLOW_VARIABLE_DEPTH.
     */
    public static final String PROPERTY_MSG_DOUBLON_DATE_TIME_FOLLOW_VARIABLE_DEPTH = "PROPERTY_MSG_DOUBLON_DATE_TIME_FOLLOW_VARIABLE_DEPTH";

    /**
     * The Constant PROPERTY_MSG_ERROR_INSERTION_MEASURE.
     */
    public static final String PROPERTY_MSG_ERROR_INSERTION_MEASURE = "PROPERTY_MSG_ERROR_INSERTION_MEASURE";

    /**
     * The Constant PROPERTY_MSG_CHECKING_FORMAT_FILE.
     */
    public static final String PROPERTY_MSG_CHECKING_FORMAT_FILE = "PROPERTY_MSG_CHECKING_FORMAT_FILE";

    /**
     * The Constant PROPERTY_MSG_INTEGER_VALUE_EXPECTED.
     */
    public static final String PROPERTY_MSG_INTEGER_VALUE_EXPECTED = "PROPERTY_MSG_INTEGER_VALUE_EXPECTED";

    /**
     * The Constant PROPERTY_MSG_DATE_VALUE_EXPECTED.
     */
    public static final String PROPERTY_MSG_DATE_VALUE_EXPECTED = "PROPERTY_MSG_DATE_VALUE_EXPECTED";

    /**
     * The Constant PROPERTY_MSG_FLOAT_VALUE_EXPECTED.
     */
    public static final String PROPERTY_MSG_FLOAT_VALUE_EXPECTED = "PROPERTY_MSG_FLOAT_VALUE_EXPECTED";

    /**
     * The Constant PROPERTY_MSG_VALUE_EXPECTED.
     */
    public static final String PROPERTY_MSG_VALUE_EXPECTED = "PROPERTY_MSG_VALUE_EXPECTED";

    /**
     * The Constant PROPERTY_MSG_ERROR_BAD_FORMAT.
     */
    public static final String PROPERTY_MSG_ERROR_BAD_FORMAT = "PROPERTY_MSG_ERROR_BAD_FORMAT";

    /**
     * The Constant PROPERTY_MSG_UNKNOWN_PUBLISH_PERSISTENCE_EXCEPTION
     *
     * @link(String).
     */
    public static final String PROPERTY_MSG_UNKNOWN_PUBLISH_PERSISTENCE_EXCEPTION = "PROPERTY_MSG_UNKNOWN_PUBLISH_PERSISTENCE_EXCEPTION";

    /**
     *
     */
    public static final String PROPERTY_MSG_IMPROPER_BEGIN_DATE = "PROPERTY_MSG_IMPROPER_BEGIN_DATE";

    /**
     *
     */
    public static final String PROPERTY_MSG_IMPROPER_END_DATE = "PROPERTY_MSG_IMPROPER_END_DATE";

    /**
     *
     */
    public static final String PROPERTY_MSG_IMPROPER_SITE = "PROPERTY_MSG_IMPROPER_SITE";

    /**
     *
     */
    public static final String PROPERTY_MSG_IMPROPER_PLOT = "PROPERTY_MSG_IMPROPER_PLOT";

    /**
     * getSimpleDateFormatDateLocaleForFile 
     * getSimpleDateFormatDateUTCForFile
     */
    public static final String DD_MM_YYYY_HHMMSS_FILE = "dd-MM-yyyy-HHmmss";

    /**
     *
     */
    public static final String DD_MM_YYYY_HHMMSS_DISPLAY = "dd-MM-yyyy-HHmmss";

    /**
     * getSimpleDateTimeFormatTimeLocale 
     * getSimpleDateTimeFormatTimeUTC
     */
    public static final String DD_MM_YYYY_HHMMSS_READ = "dd/MM/yyyyHH:mm:ss";

    /**
     * getSimpleDateFormatDateLocaleForDoublonsFile
     */
    public static final String DD_MM_YYYY_HHMMSS_DOUBLONS_FILE = "dd/MM/yyyy;HH:mm";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_BEGIN_TIME = "PROPERTY_MSG_INVALID_BEGIN_TIME";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_BEGIN_DATE_TIME = "PROPERTY_MSG_INVALID_BEGIN_DATE_TIME";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_END_TIME = "PROPERTY_MSG_INVALID_END_TIME";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_END_DATE_TIME = "PROPERTY_MSG_INVALID_END_DATE_TIME";

    /**
     *
     */
    public static final String PROPERTY_MSG_INCORRECT_VALUE_MIN = "INCORRECT_VALUE_MIN";

    /**
     *
     */
    public static final String PROPERTY_MSG_INCORRECT_VALUE_MIN_DTUV = "INCORRECT_VALUE_MIN_DTUV";

    /**
     *
     */
    public static final String PROPERTY_MSG_INCORRECT_VALUE_MAX = "INCORRECT_VALUE_MAX";

    /**
     *
     */
    public static final String PROPERTY_MSG_INCORRECT_VALUE_MAX_DTUV = "INCORRECT_VALUE_MAX_DTUV";

    /**
     *
     */
    public static final String PROPERTY_MSG_INVALID_DATE_TIME = "PROPERTY_MSG_INVALID_DATE_TIME";

    /**
     * The localization manager @link(ILocalizationManager).
     */
    static volatile ILocalizationManager localizationManager;

    /**
     *
     */
    public static String BAD_FORMAT_EXCEPTION = "BAD_FORMAT_EXCEPTION";

    /**
     *
     * @param dateString
     * @param timeString
     * @return
     * @throws BusinessException
     */
    public static final LocalDateTime getDateTimeStringFromDateStringaAndTimeString(String dateString, String timeString) throws BusinessException {
        timeString = timeString.concat(":00").substring(0, 8);
        return DateUtil.readLocalDateTimeFromLocalDateAndLocaltime(
                        DateUtil.DD_MM_YYYY, dateString, 
                        DateUtil.HH_MM_SS, timeString);

    }

    /**
     * Gets the Snot message.
     *
     * @param key
     * @link(String) the key
     * @return the Snot message from key {@link String} the key
     */
    public static final String getSnotMessage(final String key) {
        return SnotMessages.getSnotMessage(key);
    }

    /**
     * Gets the Snot message.
     *
     * @param bundlePath
     * @link(String) the bundle path
     * @param key
     * @link(String) the key
     * @return the Snot message from key
     * @link(String) the bundle path {@link String} the key
     */
    public static final String getSnotMessageWithBundle(final String bundlePath, final String key) {
        return SnotMessages.getSnotMessageWithBundle(bundlePath, key);
    }

    /**
     *
     * @param beginDateddmmyyhhmmss
     * @param endDateddmmyyhhmmss
     * @return
     * @throws BadExpectedValueException
     * @throws ParseException
     */
    public static IntervalDate getIntervalDateLocaleForFile(final String beginDateddmmyyhhmmss, final String endDateddmmyyhhmmss) throws BadExpectedValueException, ParseException {
        final IntervalDate intervalDate = new IntervalDate(beginDateddmmyyhhmmss, endDateddmmyyhhmmss, SNOTRecorder.DD_MM_YYYY_HHMMSS_FILE);
        return intervalDate;
    }

    /**
     *
     * @param beginDateddmmyyhhmmss
     * @param endDateddmmyyhhmmss
     * @return
     * @throws BadExpectedValueException
     * @throws ParseException
     */
    public static IntervalDate getIntervalDateUTCForFile(final String beginDateddmmyyhhmmss, final String endDateddmmyyhhmmss) throws BadExpectedValueException, ParseException {
        final IntervalDate intervalDate = new IntervalDate(beginDateddmmyyhhmmss, endDateddmmyyhhmmss, SNOTRecorder.DD_MM_YYYY_HHMMSS_FILE);
        return intervalDate;
    }

    /**
     * Sets the localization manager.
     *
     * @param localizationManager the new localization manager
     * @see
     * org.inra.ecoinfo.dataset.AbstractRecorder#setLocalizationManager(org.inra.ecoinfo.localization.ILocalizationManager)
     */
    static void setStaticLocalizationManager(final ILocalizationManager localizationManager) {
        SNOTRecorder.localizationManager = localizationManager;
        SnotMessages.setLocalizationManager(localizationManager);
    }

    /**
     *
     */
    protected IDatatypeDAO datatypeDAO;

    /**
     *
     */
    protected ISiteSnotDAO siteSnotDAO;

    /**
     *
     */
    protected IThemeDAO themeDAO;

    /**
     *
     */
    protected IUniteDAO uniteDAO;

    /**
     *
     */
    protected IVariableSnotDAO variableDAO;

    /**
     *
     */
    protected IUtilisateurDAO utilisateurDAO;

    /**
     *
     */
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

    /**
     *
     */
    protected String requestPropertiesName;

    /**
     * The delete record @link(IDeleteRecord).
     */
    IDeleteRecord deleteRecord;

    /**
     * The test format @link(ITestFormat).
     */
    ITestFormat testFormat;

    /**
     * The process record @link(IProcessRecord).
     */
    IProcessRecord processRecord;

    /**
     * The session properties name @link(String).
     */
    IRequestProperties requestProperties;

    DatasetDescriptor datasetDescriptor;
    private IVersionFileDAO versionFileDAO;

    /**
     *
     * @param datasetDescriptor
     */
    @Override
    public void setDatasetDescriptor(DatasetDescriptor datasetDescriptor) {
        this.datasetDescriptor = datasetDescriptor;
    }

    /**
     * Delete records.
     *
     * @param versionFile
     * @link(VersionFile) the version file
     * @throws BusinessException the business exception
     * @link(VersionFile) the version file
     * @see org.inra.ecoinfo.dataset.IRecorder#deleteRecords(org.inra.ecoinfo.dataset .versioning.entity.VersionFile)
     */
    @Override
    public void deleteRecords(final VersionFile versionFile) throws BusinessException {
        this.deleteRecord.deleteRecord(versionFile);
    }

    /**
     * Record.
     *
     * @param versionFile
     * @link(VersionFile) the version file
     * @param fileEncoding
     * @link(String) the file encoding
     * @throws BusinessException the business exception
     * @link(VersionFile) the version file
     * @link(String) the file encoding
     * @see org.inra.ecoinfo.dataset.AbstractRecorder#record(org.inra.ecoinfo .dataset.versioning.entity.VersionFile, java.lang.String) test the format and process the record of the version
     */
    @Override
    public void record(final VersionFile versionFile, final String fileEncoding) throws BusinessException {
        try {
            final IRequestProperties requestProperties = this.getRequestProperties();
            final BadsFormatsReport badsFormatsReport = new BadsFormatsReport(this.getLocalizationManager().getMessage(Constantes.SNOT_DATASET_BUNDLE_NAME, SNOTRecorder.PROPERTY_MSG_ERROR_BAD_FORMAT));
            byte[] datasSanitized = null;

            datasSanitized = Utils.sanitizeData(versionFile.getData(), this.getLocalizationManager());
            CSVParser parser = new CSVParser(new InputStreamReader(new ByteArrayInputStream(datasSanitized), fileEncoding), AbstractRecorder.SEPARATOR);
            parser = new CSVParser(new InputStreamReader(new ByteArrayInputStream(versionFile.getData()), fileEncoding), AbstractRecorder.SEPARATOR);

            this.testFormat(parser, versionFile, requestProperties, fileEncoding, this.datasetDescriptor);
            parser = new CSVParser(new InputStreamReader(new ByteArrayInputStream(
                    versionFile.getData()), fileEncoding), AbstractRecorder.SEPARATOR);
            try {
                this.processRecord(parser, versionFile, requestProperties, fileEncoding, this.datasetDescriptor);
            } catch (InterruptedException e) {
                LOGGER.error("interrupted thread", e);
            }
        } catch (final BadDelimiterException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * Sets the delete record.
     *
     * @param deleteRecord the new delete record
     * @see
     * org.inra.ecoinfo.acbb.dataset.ISNOTRecorder#setDeleteRecord(org.inra.
     * ecoinfo.acbb.dataset.IDeleteRecord)
     */
    @Override
    public final void setDeleteRecord(final IDeleteRecord deleteRecord) {
        this.deleteRecord = deleteRecord;
    }

    /**
     * Sets the localization manager.
     *
     * @param localizationManager the new localization manager
     * @see
     * org.inra.ecoinfo.dataset.AbstractRecorder#setLocalizationManager(org.inra.ecoinfo.localization.ILocalizationManager)
     */
    @Override
    public final void setLocalizationManager(final ILocalizationManager localizationManager) {
        SNOTRecorder.setStaticLocalizationManager(localizationManager);
        super.setLocalizationManager(localizationManager);
    }

    /**
     * Sets the process record.
     *
     * @param processRecord the new process record
     * @see
     * org.inra.ecoinfo.acbb.dataset.ISNOTRecorder#setProcessRecord(org.inra
     * .ecoinfo.acbb.dataset.IProcessRecord)
     */
    @Override
    public final void setProcessRecord(final IProcessRecord processRecord) {
        this.processRecord = processRecord;
    }

    /**
     * Sets the test format.
     *
     * @param testFormat the new test format
     * @see
     * org.inra.ecoinfo.acbb.dataset.ISNOTRecorder#setTestFormat(org.inra.ecoinfo
     * .acbb.dataset.ITestFormat)
     */
    @Override
    public final void setTestFormat(final ITestFormat testFormat) {
        this.testFormat = testFormat;
    }

    /**
     * Test format.
     *
     * @param versionFile
     * @link(VersionFile) the version file
     * @param encoding
     * @link(String) the encoding
     * @throws BusinessException the business exception
     * @link(VersionFile) the version file
     * @link(String) the encoding
     * @see org.inra.ecoinfo.dataset.AbstractRecorder#testFormat(org.inra.ecoinfo .dataset.versioning.entity.VersionFile, java.lang.String)
     */
    @Override
    public void testFormat(final VersionFile versionFile, final String encoding) throws BusinessException {
        final IRequestProperties requestProperties = this.getRequestProperties();
        CSVParser parser = null;
        final BadsFormatsReport badsFormatsReport = new BadsFormatsReport(this.getLocalizationManager().getMessage(Constantes.SNOT_DATASET_BUNDLE_NAME, SNOTRecorder.PROPERTY_MSG_ERROR_BAD_FORMAT));
        byte[] datasSanitized = null;

        try {
            datasSanitized = Utils.sanitizeData(versionFile.getData(), this.getLocalizationManager());
        } catch (final BusinessException e) {
            LOGGER.debug(e.getMessage(), e);
            badsFormatsReport.addException(e);
            throw new BusinessException(badsFormatsReport.getMessages());
        }

        try {
            parser = new CSVParser(new InputStreamReader(new ByteArrayInputStream(datasSanitized), encoding), AbstractRecorder.SEPARATOR);
        } catch (final BadDelimiterException | IOException e) {
            LOGGER.debug(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), e);
        }
        this.testFormat(parser, versionFile, requestProperties, encoding, this.datasetDescriptor);
    }

    /**
     *
     * @return
     */
    public String getNomSommetHierarchie() {
        return null;
    }

    /**
     *
     * @param datatypeDAO
     */
    public void setDatatypeDAO(IDatatypeDAO datatypeDAO) {
        this.datatypeDAO = datatypeDAO;
    }

    /**
     *
     * @param datatypeVariableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
    }

    /**
     *
     * @param siteDAO
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteDAO) {
        this.siteSnotDAO = siteDAO;
    }

    /**
     *
     * @param themeDAO
     */
    public void setThemeDAO(IThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    /**
     *
     * @param uniteDAO
     */
    public void setUniteDAO(IUniteDAO uniteDAO) {
        this.uniteDAO = uniteDAO;
    }

    /**
     *
     * @param utilisateurDAO
     */
    public void setUtilisateurDAO(IUtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    /**
     *
     * @param variableDAO
     */
    public void setVariableDAO(IVariableSnotDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    /**
     * Process record.
     *
     * @param parser
     * @link(CSVParser) the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param fileEncoding
     * @link(String) the file encoding
     * @throws BusinessException the business exception
     * @link(CSVParser) the parser
     * @link(VersionFile) the version file
     * @link(String) the file encoding
     * @see org.inra.ecoinfo.dataset.AbstractRecorder#processRecord(com.Ostermiller .util.CSVParser, org.inra.ecoinfo.dataset.versioning.entity.VersionFile, java.lang.String) does nothing because of overload record ()
     */
    @Override
    protected void processRecord(final CSVParser parser, final VersionFile versionFile, final String fileEncoding) throws BusinessException {
    }

    /*
     * Ajout Monique 30/04/2004 En vue de vérifier que les dates de début/fin présentes dans le nom du fichier à déposer correspondent aux dates de début/fin de l'entête du fichier
     */
    /**
     *
     * @param erreurs
     * @param message
     */
    protected void ajouterMessageErreur(BadsFormatsReport erreurs, String message) {
        String messageErreur = localizationManager.getMessage(Constantes.SNOT_DATASET_BUNDLE_NAME, message);
        BadExpectedValueException exception = new BadExpectedValueException(messageErreur);
        erreurs.addException(exception);
    }

    /**
     * <p>
     * process the record of the versionFile.
     *
     * @param parser the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesSnot) the session properties
     * @param fileEncoding the file encoding
     * @param DatasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor acbb
     * @throws BusinessException the business exception {@link VersionFile} {@link IRequestPropertiesSnot} {@link DatasetDescriptor} the dataset descriptor acbb
     * @throws InterruptedException
     */
    void processRecord(final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String fileEncoding, final DatasetDescriptor datasetDescriptor) throws BusinessException, InterruptedException {
        Allocator allocator = Allocator.getInstance();
        try {
            allocator.allocate("publish", versionFile.getFileSize() * 50);
            this.processRecord.processRecord(parser, versionFile, requestProperties, fileEncoding, datasetDescriptor);
        } finally {
            allocator.free("publish");

        }
    }

    /**
     * Gets the session properties.
     *
     * @return the session properties
     * @see org.inra.ecoinfo.acbb.dataset.ISNOTRecorder#getRequestProperties()
     */
    IRequestProperties getRequestProperties() {
        final Object bean = ApplicationContextHolder.getContext().getBean(this.requestPropertiesName);
        return (IRequestProperties) bean;
    }

    /**
     * Sets the session properties name.
     *
     * @param requestPropertiesName the new session properties name
     * @see
     * org.inra.ecoinfo.acbb.dataset.ISNOTRecorder#setRequestPropertiesName(java.lang.String)
     */
    @Override
    public final void setRequestProperties(final String requestPropertiesName) {
        this.requestPropertiesName = requestPropertiesName;
    }

    /**
     * Test format.
     *
     * @param parser
     * @link(CSVParser) the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesSnot) the session properties
     * @param encoding
     * @link(String) the encoding
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @throws BadFormatException the bad format exception
     * @throws BusinessException call test the format of a version file
     * @link(CSVParser) the parser {@link VersionFile} {@link IRequestPropertiesSnot}
     * @link(String) the encoding {@link DatasetDescriptor}
     */
    void testFormat(final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String encoding, final DatasetDescriptor datasetDescriptor) throws BusinessException {
        try {
            this.testFormat.testFormat(parser, versionFile, requestProperties, encoding, datasetDescriptor);
        } catch (BadFormatException ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     *
     * @param versionFileDAO
     */
    @Override
    public void setVersionFileDAO(IVersionFileDAO versionFileDAO) {
        this.versionFileDAO=versionFileDAO;
    }
}
