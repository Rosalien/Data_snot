package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.CSVParser;
import com.google.common.base.Strings;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.Transient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.inra.ecoinfo.dataset.config.IDatasetConfiguration;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ExpectedColumn;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesSnot;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesIntervalValue;
import org.cnrs.osuc.snot.dataset.ITestHeaders;
import org.cnrs.osuc.snot.dataset.UtilVerifRecorder;
import static org.cnrs.osuc.snot.dataset.impl.SNOTRecorder.*;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.IVariableSnotDAO;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.Outils;
import org.cnrs.osuc.snot.utils.Util;
import org.inra.ecoinfo.identification.IUtilisateurDAO;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.sitethemedatatype.ISiteThemeDatatypeDAO;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.refdata.unite.IUniteDAO;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadFormatException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;
import org.inra.ecoinfo.refdata.site.Site;

/**
 * @author sophie
 *
 */
/**
 * auteur : Monique Schoeser
 *
 */
public abstract class AbstractVerificateurEntete implements ITestHeaders {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.messages";

    /**
     *
     */
    protected static final String LINE_MAXVAL_MISSING = "LINE_MAXVAL_MISSING";

    /**
     *
     */
    protected static final String BAD_LINE_MAX = "ERREUR_LIGNEMAX";

    /**
     *
     */
    protected static final String LINE_MINVAL_MISSING = "LINE_MINVAL_MISSING";

    /**
     *
     */
    protected static final String BAD_MIN_LINE = "ERREUR_LIGNEMIN";
    /**
     * The LOGGER.
     */
    @Transient
    final static Logger LOGGER = LoggerFactory.getLogger(AbstractVerificateurEntete.class);

    /**
     *
     */
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

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
    protected ILocalizationManager localizationManager;

    /**
     *
     */
    protected String datatypeName;

    /**
     *
     */
    protected IDatasetConfiguration datasetConfiguration;

    /**
     *
     */
    protected String formatRegexDate = Constantes.REGEX_DATE;

    /**
     *
     */
    protected String frequenceAttendu = Constantes.INFRA_JOURNALIER;

    /**
     *
     */
    protected ISiteThemeDatatypeDAO siteThemeDatatypeDAO;
    
    /**
     *
     */
    protected IPolicyManager policyManager;

    /**
     *
     */
    protected String formatDate = null;

    /**
     * <P>
     * test if a line is empty.
     *
     * @param values
     * @link(String[]) the values
     * @return true if the line is empty {@link String[]}
     * <p>
     * the values of the line
     * @link(String[]) the values
     */
    protected Boolean estLigneVide(final String[] values) {
        for (final String string : values) {
            if (!string.equals(Constantes.CST_STRING_EMPTY)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param localizationManager the localizationManager to set
     */
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     * @param siteSnotDAO the siteSnotDAO to set
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteSnotDAO) {
        this.siteSnotDAO = siteSnotDAO;
    }

    /**
     */
    @Override
    public void setDatasetConfiguration(IDatasetConfiguration datasetConfiguration) {
        this.datasetConfiguration = datasetConfiguration;
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
     * @param variableDAO
     */
    public void setVariableDAO(IVariableSnotDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    /**
     *
     * @param versionFile
     * @return
     */
    protected String getFormatDate(VersionFile versionFile) {
        Optional<DataType> chercherNodeable = Outils.chercherNodeable(versionFile, DataType.class);
        return Outils.chercherNodeable(versionFile, DataType.class)
                .map(d -> d.getCode())
                .map(datatypeCode -> this.datasetConfiguration.getFileNameDateFormat(datatypeCode).replaceAll("-", "/"))
                .orElse(DateUtil.DD_MM_YYYY);
    }

    /**
     * <p>
     * Test headers. do nothing by default must be overriding</p>
     *
     * @param parser the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesSnot) the session properties
     * @param encoding the encoding
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @return the line number
     * @throws BusinessException the business exception {@link VersionFile} the version file {@link IRequestPropertiesSnot} the session properties {@link BadsFormatsReport} the bads formats report {@link DatasetDescriptor} the dataset descriptor
     * @link(VersionFile) the version file
     * @link(IRequestPropertiesSnot) the session properties
     * @link(BadsFormatsReport) the bads formats report
     * @link(DatasetDescriptor) the dataset descriptor
     * @see org.inra.ecoinfo.Snot.dataset.ITestHeaders#testHeaders(com.Ostermiller.util.CSVParser, org.inra.ecoinfo.dataset.versioning.entity.VersionFile, org.inra.ecoinfo.Snot.dataset.IRequestPropertiesSnot, java.lang.String)
     */
    @Override
    public long testHeaders(final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String encoding, final BadsFormatsReport badsFormatsReport, final DatasetDescriptor datasetDescriptor) throws BusinessException {
        return 0;
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
     * @param datatypeName
     */
    public void setDatatypeName(String datatypeName) {
        this.datatypeName = datatypeName;
    }

    /**
     *
     * @param formatRegexDate
     */
    public void setFormatRegexDate(String formatRegexDate) {
        this.formatRegexDate = formatRegexDate;
    }

    /**
     *
     * @param frequenceAttendu
     */
    public void setFrequenceAttendu(String frequenceAttendu) {
        this.frequenceAttendu = frequenceAttendu;
    }

    /**
     * @param datatypeVariableUniteSnotDAO the datatypeVariableUniteSnotDAO to
     * set
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
    }

    /**
     *
     * @param formatDate
     */
    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    /**
     * <p>
     * return the file name from the version</p>
     *
     * @param version
     * @return the file name from the version
     */
    protected String buildDownloadFilename(VersionFile version) {
        return version.getDataset().buildDownloadFilename(this.datasetConfiguration);
    }

    /**
     * @return the LOGGER
     */
    protected Logger getLogger() {
        return this.LOGGER;
    }

    /**
     * <p>
     * jump lines from parser.
     *
     * @param parser the parser
     * @param lineNumber
     * @param numberOfJumpedLines int
     * <p>
     * number of line to jump
     * @param badsFormatsReport
     * @param lineJumpedDescription
     * @return the new line number
     */
    protected long jumpLines(final CSVParser parser, final long lineNumber, final int numberOfJumpedLines, BadsFormatsReport badsFormatsReport, String lineJumpedDescription) {
        long finalLineNumber = lineNumber;
        this.LOGGER.info(String.format("Jump %d line(s) : %s", lineNumber, lineJumpedDescription));
        try {
            for (int j = 0; j < numberOfJumpedLines; j++) {
                getLine(parser);
                finalLineNumber++;
            }
            return finalLineNumber;
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
            return finalLineNumber++;
        }
    }

    /* Abstract methods */
    /**
     * <p>
     * Must be overriden for adding an error message in badsFormatsReport when
     * frequency is erroneous.</p> **************** @param values
     *
     * @param values
     * @param lineNumber
     * @param badsFormatsReport
     */
    protected abstract void getMessageFrequenceErreur(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber);

    /**
     * <p>
     * get a site from database with an array parentsite|child site|...
     *
     * @param sites
     * @return the SiteSnot
     * @throws PersistenceException
     */
    protected Optional<SiteSnot> getSite(String[] sites) throws PersistenceException {
        Optional<SiteSnot> site = Optional.empty();
        if (sites.length < 2 || Strings.isNullOrEmpty(sites[1])) {
            throw new PersistenceException(PROPERTY_MSG_SITE_ABSENT);
        }
        for (int i = 1; i < sites.length; i++) {
            String code = Utils.createCodeFromString(sites[i]);
            if (code.equals(Constantes.CST_STRING_EMPTY)) {
                break;
            } else {
                site = site.isPresent()
                        ? this.siteSnotDAO.getByCode(site.get().getCode().concat(PatternConfigurator.ANCESTOR_SEPARATOR).concat(code))
                        : this.siteSnotDAO.getByCode(code);
            }
            if (!site.isPresent()) {
                return Optional.empty();
            }
        }
        return site;
    }

    /*
     * <p>
     * read the comment from the next line lineNumber and store it in
     * IRequestProperties.setCommentaire(commentaire)</p>
     * <p>
     * <table><tr><td style="border:solid 1px">commentaire : </td><td style="border:solid 1px">comment</td>
     * </tr></table></p>
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.dataset.impl.IVerificateurEntete#traiterCommentaires(java.lang.String[])
     */
    /**
     *
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @return
     */
    protected long readCommentaires(final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, IRequestProperties requestProperties) {
        long finalLineNumber = lineNumber;
        try {
            String[] values;
            values = getLine(parser);
            finalLineNumber++;
            String commentaire;
            if (values == null) {
                throw new IOException(PROPERTY_MSG_BAD_FILE_LENGTH);
            }
            if (values.length < 2) {
                commentaire = "";
            } else {
                commentaire = values[1];
            }
            requestProperties.setCommentaire(commentaire);
            return finalLineNumber;
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
            return finalLineNumber;
        }
    }

    /**
     * <p>
     * read a the begin date (readBeginDate) and the end date (readEndDate) from
     * the 2 next lines from line lineNumber and verify than they can build an
     * intervalle(testIntervalDate) and than they're consistents
     * (testAreConsistentDates) with file name</p>
     * <p>
     * <table>
     * <tr><td style="border:solid 1px">date de debut :
     * </td><td style="border:solid 1px">01/2008</td></tr>
     * <tr><td style="border:solid 1px">date de fin :
     * </td><td style="border:solid 1px">02/2008</td></tr>
     * </table></p>
     * <p>
     * see delegate functions for errors</p>
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readBeginAndEndDates(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties, final DatasetDescriptor datasetDescriptor) {
        long returnLineNumber = lineNumber;
        returnLineNumber = this.readBeginDate(version, badsFormatsReport, parser, returnLineNumber, requestProperties, datasetDescriptor);
        returnLineNumber = this.readEndDate(version, badsFormatsReport, parser, returnLineNumber, requestProperties, datasetDescriptor);
        this.testIntervalDate(badsFormatsReport, returnLineNumber, requestProperties);
        if (requestProperties.getDatedeDebut() != null && requestProperties.getDateDeFin() != null) {
            this.testAreConsistentDates(badsFormatsReport, version, requestProperties, returnLineNumber, datasetDescriptor);
        }
        return returnLineNumber;
    }

    /**
     * <p>
     * read the beginDate from the next line lineNumber and store it in
     * IRequestProperties.setDateDeDebut(startDate)</p>
     * <p>
     * The date have to match the regexp pattern this.formatRegexDate/p>
     * <p>
     * The date format is either this.formatDate either obtained from the
     * versionFile</p>
     * <table>
     * <tr><td style="border:solid 1px">date de debut :
     * </td><td style="border:solid 1px">01/2008</td></tr>
     * </table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>Existence de la date de début →
     * org.cnrs.osuc.snot.dataset.messages / PROPERTY_MSG_MISSING_BEGIN_DATE
     * </li>
     * <li>date au mauvais pattern ou date imparsable→
     * org.cnrs.osuc.snot.dataset.messages / PROPERTY_MSG_INVALID_BEGIN_DATE
     * </li>
     * <li>Pas de ligne de date→
     * org.inrasiteSnot.ecoinfo.snot.dataset.messages / EOF</li></ul>
     *
     * @param versionFile
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readBeginDate(final VersionFile versionFile, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties, final DatasetDescriptor datasetDescriptor) {
        long returnLineNumber = lineNumber;
        String msgDateDebutManquante = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISSING_BEGIN_DATE), lineNumber, 2);
        try {
            String[] line = getLine(parser);
            if (line.length < 2 || Strings.isNullOrEmpty(line[1])) {
                badsFormatsReport.addException(new BadExpectedValueException(msgDateDebutManquante));
                return ++returnLineNumber;
            }
            final String date = line[1];
            String msgDateDebutNonValide = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_BEGIN_DATE), date, lineNumber, 2, formatDate == null ? getFormatDate(versionFile) : formatDate);
            if (!date.matches(this.formatRegexDate)) {
                badsFormatsReport.addException(new BadExpectedValueException(msgDateDebutNonValide));
                requestProperties.setDateDeDebut(null);
            } else {
                parseStartDate(versionFile, date, requestProperties, badsFormatsReport, returnLineNumber);
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            LOGGER.debug("ArrayIndexOutOfBoundsException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISSING_BEGIN_DATE), returnLineNumber, 2)));
            requestProperties.setDateDeDebut(null);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
            requestProperties.setDateDeFin(null);
        }
        return ++returnLineNumber;
    }

    /**
     * <p>
     * parse date with formatdate or format from version ans set it in
     * requestProperties.setDateDeDebut(startDate)</p>
     * <p>
     * put message PROPERTY_MSG_INVALID_BEGIN_DATE on error</p>
     *
     * @param versionFile
     * @param date date to string
     * @param requestProperties
     * @param badsFormatsReport
     * @param returnLineNumber
     */
    protected void parseStartDate(final VersionFile versionFile, final String date,
            final IRequestProperties requestProperties, final BadsFormatsReport badsFormatsReport, long returnLineNumber) {
        LocalDateTime startDate;
        String localFormatDate = this.formatDate == null ? getFormatDate(versionFile) : formatDate;
        try {
            if (DateUtil.MM_YYYY.equals(localFormatDate)) {
                startDate = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, "01/".concat(date));
            } else {
                startDate = DateUtil.readLocalDateTimeFromText(localFormatDate, date);
            }
            requestProperties.setDateDeDebut(startDate);
        } catch (DateTimeParseException ex) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_BEGIN_DATE), date, returnLineNumber, 2, localFormatDate)));
            requestProperties.setDateDeDebut(null);
        }
    }

    /**
     * <p>
     * read a the datatype from the next line lineNumber and verify than it's
     * equals to this.datatypeName</p>
     * <p>
     * <table><tr><td style="border:solid 1px">datatype</td><td style="border:solid 1px">datyp_sh</td></tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>Test pas de datatype → org.cnrs.osuc.snot.dataset.messages /
     * DATATYPE_ABSENT</li>
     * <li>Mauvais datatype → org.cnrs.osuc.snot.dataset.messages /
     * BAD_DATATYPET</li>
     * <li>Pas de ligne de datatype→ org.cnrs.osuc.snot.dataset.messages /
     * EOF</li></ul>
     *
     * @param badsFormatsReport
     * @param lineNumber
     * @link(BadsFormatsReport) the bads formats report
     * @param parser the parser
     * @link(String) the datatype name
     * @return the long
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the datatype name
     */
    protected long readDatatype(final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber) {
        try {
            String[] values;
            values = getLine(parser);
            if (values == null) {
                throw new IOException(PROPERTY_MSG_BAD_FILE_LENGTH);
            } else if (values == null || values.length < 2 || Strings.isNullOrEmpty(values[1])) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_MISSING_DATATYPE), lineNumber, 2)));
            } else if (!values[1].trim().equalsIgnoreCase(this.datatypeName.trim())) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_DATATYPE), lineNumber)));
            }
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        } catch (final IndexOutOfBoundsException e1) {
            LOGGER.debug("IndexOutOfBoundsException", e1);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_MISSING_DATATYPE), lineNumber)));
        }
        return lineNumber + 1;

    }

    /**
     * <p>
     * read a the frequence from the next line lineNumber and verify than it's
     * equals to this.frequenceAttendu and store it in
     * IRequestProperties.setFrequence</p>
     * <p>
     * <table><tr><td style="border:solid 1px">fréquence :
     * </td><td style="border:solid 1px">mensuel</td></tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>Test pas de fréquence → org.cnrs.osuc.snot.dataset.messages /
     * DATATYPE_ABSENT</li>
     * <li>Mauvais fréquence → must be implement</li>
     * <li>Pas de ligne fréquence→ org.cnrs.osuc.snot.dataset.messages /
     * EOF</li></ul>
     *
     * @param requestProperties
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @return
     */
    protected long readFrequence(final IRequestProperties requestProperties, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber) {
        try {
            String[] values;
            values = getLine(parser);
            String frequence = null;
            if (values == null || values.length < 2 || Strings.isNullOrEmpty(values[1])) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_EMPTY_FREQUENCE), lineNumber)));
            } else if (!this.frequenceAttendu.trim().equalsIgnoreCase(values[1].trim())) {
                this.getMessageFrequenceErreur(values, badsFormatsReport, lineNumber);
            } else {
                frequence = this.frequenceAttendu;
            }
            requestProperties.setFrequence(frequence);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        } catch (final IndexOutOfBoundsException e1) {
            LOGGER.debug("IndexOutOfBoundsException", e1);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_EMPTY_FREQUENCE), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * return the next String[] values from next line in parser</p>
     *
     * @param parser
     * @return the values of next line
     * @throws IOException
     */
    protected String[] getLine(final CSVParser parser) throws IOException {
        String[] values;
        values = parser.getLine();
        if (values == null) {
            throw new IOException(PROPERTY_MSG_BAD_FILE_LENGTH);
        }
        return values;
    }

    /**
     * <p>
     * read an empty line.</p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>pas une ligne vide → org.cnrs.osuc.snot.dataset.messages
     * /PROPERTY_MSG_MISSING_EMPTY_LINE</li></ul>
     *
     * @param badsFormatsReport
     * @param lineNumber
     * @link(BadsFormatsReport) the bads formats report
     * @param parser the parser
     * @return the long
     * @link(BadsFormatsReport) the bads formats report
     */
    protected long readEmptyLine(final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber) {
        try {
            String[] values;
            values = getLine(parser);
            if (!this.estLigneVide(values)) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISSING_EMPTY_LINE), lineNumber)));
            }
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * read the endDate from the next line lineNumber and store it in
     * IRequestProperties.setDateDeFin(startDate)</p>
     * <p>
     * The date have to match the regexp pattern this.formatRegexDate/p>
     * <p>
     * The date format is either this.formatDate either obtained from the
     * versionFile</p>
     * <table>
     * <tr><td style="border:solid 1px">date de fin :
     * </td><td style="border:solid 1px">02/2008</td></tr>
     * </table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>Existence de la date de fin →
     * org.cnrs.osuc.snot.dataset.messages / PROPERTY_MSG_MISSING_END_DATE
     * </li>
     * <li>date au mauvais pattern ou date imparsable→
     * org.cnrs.osuc.snot.dataset.messages / PROPERTY_MSG_INVALID_END_DATE
     * </li>
     * <li>Pas de ligne de date→
     * org.inrasiteSnot.ecoinfo.snot.dataset.messages / EOF</li></ul>
     *
     * @param versionFile
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readEndDate(final VersionFile versionFile, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties, DatasetDescriptor datasetDescriptor) {
        String msgDateFinManquante = SNOTRecorder.getSnotMessage(PROPERTY_MSG_MISSING_END_DATE);
        try {
            String[] line = getLine(parser);
            if (line.length < 2 || Strings.isNullOrEmpty(line[1])) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(msgDateFinManquante, lineNumber, 2)));
                return lineNumber + 1;
            }
            final String date = line[1];
            if (!date.matches(this.formatRegexDate)) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_END_DATE), date, lineNumber, 2, formatDate)));
                requestProperties.setDateDeFin(null);
            } else {
                parseEndDate(datasetDescriptor, versionFile, date, requestProperties, badsFormatsReport, lineNumber);
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            LOGGER.debug("ArrayIndexOutOfBoundsException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISSING_END_DATE), lineNumber, 1)));
            requestProperties.setDateDeFin(null);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
            requestProperties.setDateDeFin(null);
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * parse date with formatDate or format from versionFile and put it in
     * requestProperties.setDateDeFin(endDate)</p>
     * <p>
     * on error add error PROPERTY_MSG_INVALID_END_DATE in badsFormatsReport</p>
     *
     * @param datasetDescriptor
     * @param versionFile
     * @param date
     * @param requestProperties
     * @param badsFormatsReport
     * @param returnLineNumber
     */
    protected void parseEndDate(DatasetDescriptor datasetDescriptor, final VersionFile versionFile, final String date, final IRequestProperties requestProperties, final BadsFormatsReport badsFormatsReport, long returnLineNumber) {
        LocalDateTime endDate;
        String localFormatdate=null;
        try {
            localFormatdate = formatDate == null ? getFormatDate(versionFile) : formatDate;
            if (localFormatdate.equals(DateUtil.MM_YYYY)) {
                endDate = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, "01/".concat(date));
            } else {
                endDate = DateUtil.readLocalDateTimeFromText(localFormatdate, date);
            }
            if (Constantes.FREQUENCE_NAME[2].equals(this.frequenceAttendu)) {
                endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
            }
            requestProperties.setDateDeFin(endDate);
        } catch (DateTimeParseException ex) {
            LOGGER.debug("ParseException", ex);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_END_DATE), date, returnLineNumber, 2, localFormatdate)));
            requestProperties.setDateDeFin(null);
        }
    }

    /**
     * <p>
     * read the column names header line from the next line lineNumber and store
     * it in IRequestProperties.setColumnNames(columnNames,
     * datasetDescriptor.getColumns())</p>
     * <p>
     * In this function all the column of the line are compared to the columns
     * of datasetDescriptor</p>
     * <p>
     * This function must be used if all the column exists and are in the same
     * order than in dataset descriptor</p>
     * <p>
     * <table><tr>
     * <td style="border:solid 1px">date</td>
     * <td style="border:solid 1px">time</td>
     * <td style="border:solid 1px">CO2</td>
     * <td style="border:solid 1px">hO</td>
     * </tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>mauvaise colonne ->org.cnrs.osuc.snot.dataset.messages
     * /PROPERTY_MSG_MISMACH_COLUMN_HEADER</li>
     * <li>Pas de ligne de names of columns→
     * org.cnrs.osuc.snot.dataset.messages / EOF</li></ul>
     *
     * @param badsFormatsReport
     * @param lineNumber
     * @link(BadsFormatsReport) the bads formats report
     * @param parser the parser
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestPropertiesSnot) the session properties Snot
     * @return the long
     * @link(BadsFormatsReport) the bads formats report
     * @link(DatasetDescriptor) the dataset descriptor
     */
    protected long readLineHeader(final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        try {
            String[] columnNames = getLine(parser);
            requestProperties.setColumnNames(columnNames, datasetDescriptor.getColumns());

            String value;
            for (int index = 0; index < columnNames.length; index++) {
                if (index > datasetDescriptor.getColumns().size() - 1) {
                    break;
                }
                value = columnNames[index].trim();
                final Column column = datasetDescriptor.getColumns().get(index);
                if (!column.getName().trim().equalsIgnoreCase(value)) {
                    badsFormatsReport
                            .addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISMACH_COLUMN_HEADER), lineNumber, index + 1, value, column.getName().trim().toLowerCase())));
                }
            }
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * read the site from the next line lineNumber and store it in
     * IRequestProperties.setSite(siteSnot)</p>
     * <p>
     * <table><tr><td style="border:solid 1px">site</td><td style="border:solid 1px">hesse</td><td style="border:solid 1px">Hesse
     * 1</td></tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>Test null site → org.cnrs.osuc.snot.dataset.messages /
     * BAD_SITE</li>
     * <li>PersistenceException→ org.cnrs.osuc.snot.dataset.messages /
     * SITE_ABSENT</li>
     * <li>Pas de ligne de site→ org.cnrs.osuc.snot.dataset.messages /
     * EOF</li></ul>
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @return
     */
    protected long readSite(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties) {
        SiteSnot siteSnot;
        try {
            String[] values = getLine(parser);
            siteSnot = this.getSite(values).orElse(null);
            SiteSnot versionSite = Outils.chercherNodeable(version, SiteSnot.class).orElse(null);
            if (siteSnot == null || !siteSnot.getPath().equals(versionSite.getPath())) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_SITE), lineNumber)));
            }
            requestProperties.setSite(siteSnot);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        } catch (PersistenceException e) {
            LOGGER.debug("PersistenceException", e);
            siteSnot = null;
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_SITE_ABSENT), lineNumber)));
        }
        return lineNumber + 1;

    }

    /**
     * <p>
     * read the theme from next line lineNumber and test if this's the same as
     * the upload node</p>
     * <p>
     * <table><tr>
     * <td style="border:solid 1px">theme :</td>
     * <td style="border:solid 1px">thematic</td>
     * </tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>bad thematic ->org.cnrs.osuc.snot.dataset.messages
     * /BAD_THEME_GES</li>
     * <li>Pas de ligne de names of columns→
     * org.cnrs.osuc.snot.dataset.messages / EOF</li></ul>
     *
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @return
     */
    protected long readTheme(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber) {
        try {
            String[] values = getLine(parser);
            final String themeAttendu = Outils.chercherNodeable(version, Theme.class)
                    .map(t -> t.getCode())
                    .orElseGet(null);
            if (values == null || values.length < 2 || !values[1].equalsIgnoreCase(themeAttendu)) {
                final BadExpectedValueException exception = new BadExpectedValueException(Util.BAD_THEME_GES);
                badsFormatsReport.addException(exception);
            }
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * sanitize the data of the file.</p>
     *
     * @param versionFile
     * @link(VersionFile)
     * @link(Logger) the LOGGER
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @return the byte[]
     * @throws BadFormatException the bad format exception {@link VersionFile} the version file {@link Logger} the LOGGER {@link BadsFormatsReport} the bads formats report
     * @link(VersionFile) the version file
     * @link(Logger) the LOGGER
     * @link(BadsFormatsReport) the bads formats report
     * @see org.inra.ecoinfo.utils.Utils$sanitizeData(org.inra.ecoinfo.dataset. versioning .entity.VersionFile)
     */
    protected byte[] sanitizeData(final VersionFile versionFile, final BadsFormatsReport badsFormatsReport) throws BadFormatException {
        byte[] datasSanitized = null;
        try {
            datasSanitized = Utils.sanitizeData(versionFile.getData(), this.localizationManager);
        } catch (final BusinessException e) {
            LOGGER.debug(e.getMessage());
            badsFormatsReport.addException(e);
            throw new BadFormatException(badsFormatsReport);
        }
        return datasSanitized;
    }

    /**
     * <p>
     * test interval date from date in requestProperties.getDateDeDebut and
     * requestProperties.getDateDeFin</p>
     * <p>
     * dateDeDebut must be before dateDeFin</p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>mauvais intervalle → org.cnrs.osuc.snot.dataset.messages /
     * PROPERTY_MSG_INVALID_INTERVAL_DATE </li></ul>
     *
     * @param badsFormatsReport
     * @param lineNumber
     * @param requestProperties
     */
    protected void testIntervalDate(final BadsFormatsReport badsFormatsReport, final long lineNumber, final IRequestProperties requestProperties) {
        if (requestProperties.getDatedeDebut() != null
                && requestProperties.getDateDeFin() != null
                && requestProperties.getDatedeDebut().isAfter(requestProperties.getDateDeFin())) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_INTERVAL_DATE),
                    DateUtil.getUTCDateTextFromLocalDateTime(requestProperties.getDatedeDebut(), requestProperties.getDateFormat()),
                    lineNumber - 1, 1,
                    DateUtil.getUTCDateTextFromLocalDateTime(requestProperties.getDateDeFin(), requestProperties.getDateFormat()),
                    lineNumber, 1)));
        }
    }

    /**
     * <p>
     * fill valeurs with the min (searchMin = true) or the max (searchMin =
     * false) values from database</p>
     *
     * @param datasetDescriptor
     * @param index the index of the column
     * @param variableCode the name of the column (variable code)
     * @param uniteCode the unit for the variable (column name)
     * @param searchMin if true valeurs is the array of min values else it's the
     * array of max values
     * @param valeurs the arrays to fill
     * @throws BusinessException Recherche et récupère la valeur min ou la
     * valeur max dans DatatypeUnitéVariableSnot
     */
    protected void getMinMaxFromDatatypeUniteVariableSnot(DatasetDescriptor datasetDescriptor, int index, String jeuCode, String siteCode, String variableCode, String uniteCode, boolean searchMin, String[] valeurs) throws BusinessException {
        String dtCode = datasetDescriptor.getName();
        float minDtUVariable = 0;
        float maxDtUVariable = 0;
        try {
            DatatypeVariableUniteSnot dtvu = (DatatypeVariableUniteSnot) this.datatypeVariableUniteSnotDAO.getByNKey(jeuCode,siteCode, dtCode, variableCode, uniteCode)
                    .orElseThrow(PersistenceException::new);
            if (searchMin) {
                minDtUVariable = dtvu.getMin();
                valeurs[index] = Float.toString(minDtUVariable);
            } else {
                maxDtUVariable = dtvu.getMax();
                valeurs[index] = Float.toString(maxDtUVariable);
            }
        } catch (PersistenceException e) {
            throw new BusinessException(e);
        }
    }

    /**
     * <p>
     * min et max value provenant du requestProperties ou si non saisi, du
     * DatatypeUnitevariablesnot pour les variables et variable gapfillée</p>
     * <p>
     * store it in requestProperties.setValeursMin(valeursMin) and
     * requestProperties.setValeursMax(valeursMax)</p>
     *
     * <p>
     * </p>
     *
     * @param datasetDescriptor
     * @param requestProperties
     * @throws BusinessException
     */
    protected void minMaxValues(DatasetDescriptor datasetDescriptor, IRequestPropertiesIntervalValue requestProperties) throws BusinessException {
        int index = 0;
        final String[] columnNames = requestProperties.getColumnNames();
        final int undefinedColumn = datasetDescriptor.getUndefinedColumn();
        String[] valeursMin = new String[columnNames.length - undefinedColumn];
        String[] valeursMax = new String[columnNames.length - undefinedColumn];
        String[] valeursMinLine = requestProperties.getValeurMinInFile();
        String[] valeursMaxLine = requestProperties.getValeurMaxInFile();
        String[] uniteNames = requestProperties.getuniteNames();
        Site station = requestProperties.getSite();
        String[] jeu = null;//requestProperties.getJeu();
        for (int i = datasetDescriptor.getUndefinedColumn(); i < columnNames.length; i++) {
            verifyMinMaxForIndex(valeursMinLine, i, valeursMin, index, jeu, station.getCode(), valeursMaxLine, valeursMax, requestProperties, columnNames, datasetDescriptor, uniteNames, undefinedColumn);
            index++;
        }
        requestProperties.setValeursMin(valeursMin);
        requestProperties.setValeursMax(valeursMax);
    }

    private void verifyMinMaxForIndex(String[] valeursMinLine, int i, String[] valeursMin, int index, String[] jeuCode, String siteCode, String[] valeursMaxLine, String[] valeursMax, IRequestPropertiesIntervalValue requestProperties, final String[] columnNames, DatasetDescriptor datasetDescriptor, String[] uniteNames, final int undefinedColumn) throws BusinessException, NumberFormatException {
        if (valeursMinLine[i] != null && !valeursMinLine[i].isEmpty()) {
            valeursMin[index] = valeursMinLine[i];
        }
        if (valeursMaxLine[i] != null && !valeursMaxLine[i].isEmpty()) {
            valeursMax[index] = valeursMaxLine[i];
        }
        ExpectedColumn expectedColumn = requestProperties.getExpectedColumns().get(columnNames[i]);
        if (expectedColumn != null && (expectedColumn.isVariable() || expectedColumn.getFlagType().equals(Constantes.PROPERTY_CST_VARIABLE_GF))) {
            if (StringUtils.isEmpty(valeursMinLine[i])) {
                this.getMinMaxFromDatatypeUniteVariableSnot(datasetDescriptor, index, jeuCode[i],siteCode, columnNames[i], uniteNames[i], true, valeursMin);
                String valeurMin = valeursMin[i - undefinedColumn];
                if (NumberUtils.isNumber(valeurMin)) {
                    expectedColumn.setMinValue(Float.parseFloat(valeursMin[i - undefinedColumn]));
                }
            }
            if (StringUtils.isEmpty(valeursMaxLine[i])) {
                this.getMinMaxFromDatatypeUniteVariableSnot(datasetDescriptor, index,jeuCode[i], siteCode, columnNames[i], uniteNames[i], false, valeursMax);
                String valeurMax = valeursMax[i - undefinedColumn];
                if (NumberUtils.isNumber(valeurMax)) {
                    expectedColumn.setMaxValue(Float.parseFloat(valeursMax[i - undefinedColumn]));
                }
            }
        }
    }

    /**
     * <p>
     * read the max values from the next line lineNumber and store it in
     * requestProperties.setValeurMaxInFile(maxValuesInFile)</p>
     * <p>
     * store in requestProperties.setValeursMax(maxValues) the max value for
     * undefined columns;</p>
     * <p>
     * <table><tr><td style="border:solid 1px">24.5</td><td style="border:solid 1px">30.2</td><td style="border:solid 1px">100
     * 1</td></tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>la ligne ne commence pas par max →
     * org.cnrs.osuc.snot.dataset.messages / LINE_MAXVAL_MISSING</li>
     * <li>La ligne n'a pas le même nombre de colonne que celle d'en-tete→
     * org.cnrs.osuc.snot.dataset.messages / BAD_LINE_MAX</li>
     * <li>Pas de ligne de site→ org.cnrs.osuc.snot.dataset.messages /
     * EOF</li></ul>
     *
     * @see
     * org.cnrs.osuc.snot.dataset.fluxgazeux.chambrefluxsol.IVerificateurEnteteFluxSol#verifierValeurMax(java.lang.String[],
     * java.lang.String[], org.inra.ecoinfo.dataset.BadsFormatsReport, int)
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    /**
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readValeursMax(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestPropertiesIntervalValue requestProperties, final DatasetDescriptor datasetDescriptor) {
        try {
            String[] maxValuesInFile = getLine(parser);
            final int undefinedColumn = datasetDescriptor.getUndefinedColumn();
            if (!maxValuesInFile[0].toLowerCase().contains(Constantes.CST_MAX)) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(LINE_MAXVAL_MISSING), lineNumber)));
            }
            final String[] columnNames = requestProperties.getColumnNames();
            if (maxValuesInFile.length != columnNames.length) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(BAD_LINE_MAX), lineNumber)));
                throw new BusinessException(badsFormatsReport.getMessages());
            }
            String[] maxValues = Arrays.copyOfRange(maxValuesInFile, undefinedColumn, maxValuesInFile.length);
            for (int i = undefinedColumn; i < maxValuesInFile.length; i++) {
                if (maxValuesInFile[i] != null && !maxValuesInFile[i].isEmpty()) {
                    UtilVerifRecorder.isValueFormatFloat(lineNumber, i + 1, maxValuesInFile[i], columnNames[i], badsFormatsReport);
                } else {
                    maxValues[i - undefinedColumn] = null;
                }
            }
            requestProperties.setValeurMaxInFile(maxValuesInFile);
            requestProperties.setValeursMax(maxValues);
        } catch (BusinessException e) {
            LOGGER.debug("BusinessException", e);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * read the min values from the next line lineNumber and store it in
     * requestProperties.setValeurMinInFile(minValuesInFile)</p>
     * <p>
     * store in requestProperties.setValeursMin(maxValues) the min value for
     * undefined columns;</p>
     * <p>
     * <table><tr><td style="border:solid 1px">24.5</td><td style="border:solid 1px">30.2</td><td style="border:solid 1px">100
     * 1</td></tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>la ligne ne commence pas par min →
     * org.cnrs.osuc.snot.dataset.messages / LINE_MINVAL_MISSING</li>
     * <li>La ligne n'a pas le même nombre de colonne que celle d'en-tete→
     * org.cnrs.osuc.snot.dataset.messages / BAD_LINE_MIN</li>
     * <li>Pas de ligne de site→ org.cnrs.osuc.snot.dataset.messages /
     * EOF</li></ul>
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readValeursMin(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestPropertiesIntervalValue requestProperties, final DatasetDescriptor datasetDescriptor) {
        try {
            String[] minValuesInFile = getLine(parser);
            final int undefinedColumn = datasetDescriptor.getUndefinedColumn();
            if (!minValuesInFile[0].toLowerCase().contains(Constantes.CST_MIN)) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(LINE_MINVAL_MISSING), lineNumber)));
                return lineNumber + 1;
            }
            final String[] columnNames = requestProperties.getColumnNames();
            if (minValuesInFile.length != columnNames.length) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(BAD_MIN_LINE), lineNumber)));
                throw new BusinessException(badsFormatsReport.getMessages());
            }
            String[] minValues = Arrays.copyOfRange(minValuesInFile, undefinedColumn, minValuesInFile.length);
            for (int i = undefinedColumn; i < minValuesInFile.length; i++) {
                if (minValuesInFile[i] != null && !minValuesInFile[i].isEmpty()) {
                    UtilVerifRecorder.isValueFormatFloat(lineNumber, i + 1, minValuesInFile[i], columnNames[i], badsFormatsReport);
                } else {
                    minValues[i - undefinedColumn] = null;
                }
            }
            requestProperties.setValeurMinInFile(minValuesInFile);
            requestProperties.setValeursMin(minValues);
        } catch (BusinessException e) {
            LOGGER.debug("BusinessException", e);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * read the column units header line from the next line lineNumber and store
     * it in IRequestProperties.setUniteNames(valuesUnite)</p>
     * <p>
     * test if the units are the same than in database for variable or than the
     * dateFormat for dates</p>
     * <p>
     * <table><tr>
     * <td style="border:solid 1px">MM/yyyy</td>
     * <td style="border:solid 1px">µmol mol-1</td>
     * <td style="border:solid 1px">mmol mol-1</td>
     * </tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>mauvaise unité →
     * org.cnrs.osuc.snot.dataset.messages/ERREUR_LIGNEUNITE</li>
     * <li>Pas de ligne de names of columns→
     * org.cnrs.osuc.snot.dataset.messages / EOF</li></ul>
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readUnites(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties, final DatasetDescriptor datasetDescriptor) {
        try {
            String[] valuesUnite = getLine(parser);
            final String[] columnNames = requestProperties.getColumnNames();
            if (valuesUnite.length != columnNames.length) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("ERREUR_LIGNEUNITE"), lineNumber)));
                return lineNumber + 1;
            }
            if (CollectionUtils.isEmpty(requestProperties.getLstDatasetNameColumns())) {
                requestProperties.setLstDatasetNameColumns(UtilVerifRecorder.getLstDatasetNameColumns(datasetDescriptor));
            }
            for (int i = 0; i < valuesUnite.length; i++) {
                if (!Strings.isNullOrEmpty(valuesUnite[i])) {
                    String jeuCode = null;
                    if (testUniteForIndex(requestProperties, columnNames, jeuCode,i, datasetDescriptor, valuesUnite, badsFormatsReport, lineNumber)) {
                    } else {
                    }
                }
            }
            requestProperties.setUniteNames(valuesUnite);
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    private boolean testUniteForIndex(final IRequestProperties requestProperties, final String[] columnNames, String jeu, int i, final DatasetDescriptor datasetDescriptor, String[] valuesUnite, final BadsFormatsReport badsFormatsReport, final long lineNumber) {
        int indexColumn = requestProperties.getLstDatasetNameColumns().indexOf(columnNames[i]);
        if (indexColumn < 0) {
            return true;
        }
        Column column = datasetDescriptor.getColumns().get(indexColumn);
        if (column.isFlag() && column.getFlagType().equals(Constantes.PROPERTY_CST_DATE_TYPE) && !column.getFormatType().equalsIgnoreCase(valuesUnite[i])) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("ERREUR_LIGNEUNITE_DATE"), lineNumber)));
        } else if (column.isVariable() || column.getFlagType().equals(Constantes.PROPERTY_CST_VARIABLE_GF)) {
            String codetTypeDeDonnees = datasetDescriptor.getName();
            String jeuCode = null;
            DatatypeVariableUniteSnot dtuv = this.datatypeVariableUniteSnotDAO.getByNKey(jeuCode,requestProperties.getSite().getCode(), codetTypeDeDonnees, column.getName(), valuesUnite[i]).orElse(null);
            if (dtuv == null) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("ERREUR_UNITE"), lineNumber, valuesUnite[i], columnNames[i], datasetDescriptor.getName())));
            }
        }
        return false;
    }

    /**
     * <p>
     * read the column names header line from the next line lineNumber and store
     * it in IRequestProperties.setColumnNames(columnNames,
     * datasetDescriptor.getColumns())</p>
     * <p>
     * in this function all the column of the libne are compared to the columns
     * of the datadet descriptor but the two functionsverifieFirstColonnes and
     * testNomColonnes can be overriden</p>
     * <p>
     * by default, functionsverifieFirstColonnes test that the column exist and
     * matches the expected column name and testNomColonnes return always true
     * </p>
     * <p>
     * Must be used if dataset descriptor describe only first columns</p>
     * <p>
     * <table><tr>
     * <td style="border:solid 1px">date</td>
     * <td style="border:solid 1px">time</td>
     * <td style="border:solid 1px">CO2</td>
     * <td style="border:solid 1px">hO</td>
     * </tr></table></p>
     * <p>
     * Add errors in badsFormatsReport :>
     * <ul><li>mauvaise colonne ->org.cnrs.osuc.snot.dataset.messages
     * /PROPERTY_MSG_INTITULE_ABSENT</li>
     * <li>Pas de ligne de names of columns→
     * org.cnrs.osuc.snot.dataset.messages / EOF</li></ul>
     *
     * @see
     * org.cnrs.osuc.snot.dataset.impl.IVerificateurEntete#verifierNomDesColonnes(java.lang.String[],
     * int, org.inra.ecoinfo.dataset.BadsFormatsReport,
     * org.inra.ecoinfo.dataset.DatasetDescriptor)
     *
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    protected long readNomDesColonnes(final VersionFile version, final BadsFormatsReport badsFormatsReport, final CSVParser parser, final long lineNumber, final IRequestProperties requestProperties, final DatasetDescriptor datasetDescriptor) {
        try {
            String[] columnNames = getLine(parser);
            requestProperties.setColumnNames(columnNames, datasetDescriptor.getColumns());
            if (CollectionUtils.isEmpty(requestProperties.getLstDatasetNameColumns())) {
                requestProperties.setLstDatasetNameColumns(UtilVerifRecorder.getLstDatasetNameColumns(datasetDescriptor));
            }
            this.verifieFirstColonnes(columnNames, lineNumber, badsFormatsReport, datasetDescriptor, requestProperties.getLstDatasetNameColumns());
            for (int index = datasetDescriptor.getUndefinedColumn(); index < columnNames.length; index++) {
                testNomColonnes(columnNames, index, badsFormatsReport, lineNumber, datasetDescriptor, version, requestProperties);
            }
        } catch (BusinessException e) {
            LOGGER.debug("BusinessException", e);
            if (!SNOTRecorder.BAD_FORMAT_EXCEPTION.equals(e.getMessage())) {
                badsFormatsReport.addException(e);
            }
        } catch (IOException e) {
            LOGGER.debug("IOException", e);
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
        }
        return lineNumber + 1;
    }

    /**
     * <p>
     * must be implemented for testing column names in columnNames</p>
     *
     * @param columnNames
     * @param index
     * @param badsFormatsReport
     * @param lineNumber
     * @param datasetDescriptor
     * @param version
     * @param requestProperties
     * @return
     * @throws BusinessException
     */
    protected boolean testNomColonnes(String[] columnNames, int index, final BadsFormatsReport badsFormatsReport, final long lineNumber, final DatasetDescriptor datasetDescriptor, final VersionFile version, IRequestProperties requestProperties) throws BusinessException {
        return true;
    }

    /**
     * <p>
     * return the datatype_frequency from the dataset descriptor</p>
     *
     * @param datasetDescriptor
     * @return
     */
    protected String getDatatypeWithFrequenceCode(DatasetDescriptor datasetDescriptor) {
        return datasetDescriptor.getName();
    }

    /**
     * <p>
     * vérifie la présence et l'exactitude des premières colonnes du fichier
     * avant les colonnes variables et les colonnes associées aux variables</p>
     *
     * @param columnNames
     * @param lineNumber
     * @param badsFormatsReport
     * @param datasetDescriptor vérifie la présence et l'exactitude des
     * premières colonnes du fichier avant les colonnes variables et les
     * colonnes associées aux variables
     * @param lstDatasetNameColumns
     */
    protected void verifieFirstColonnes(String[] columnNames, long lineNumber, BadsFormatsReport badsFormatsReport, DatasetDescriptor datasetDescriptor, List<String> lstDatasetNameColumns) {
        for (int i = 0; i < datasetDescriptor.getUndefinedColumn(); i++) {
            String valueInFile = columnNames[i];
            String datasetNameColumn = lstDatasetNameColumns.get(i);
            if (valueInFile == null || !datasetNameColumn.trim().equalsIgnoreCase(valueInFile.trim())) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INTITULE_ABSENT), lineNumber, i + 1, valueInFile, datasetNameColumn)));
            }
        }
    }

    /**
     * <p>
     * this function is call to get a Map<String,DatatypeVariableUnite> for the
     * current datatype<p>
     * <p>
     * he map is stored in
     * requestProperties.setVariableTypeDonnees(variableTypeDonnees)</p>
     * <p>
     * the key of th map is the variable name</p>
     *
     * @param datasetDescriptor
     * @param requestProperties
     * @throws BusinessException
     */
    protected void remplirVariablesTypeDonnees(DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) throws BusinessException {
        String dtCode = datasetDescriptor.getName();
        Map<String, DatatypeVariableUniteSnot> variableTypeDonnees = new HashMap<>();
        List<DatatypeVariableUniteSnot> lstDtvus = this.datatypeVariableUniteSnotDAO.getByDatatype(dtCode);
        for (DatatypeVariableUniteSnot datatypeVariableUnite : lstDtvus) {
            variableTypeDonnees.put(datatypeVariableUnite.getVariable().getName(), datatypeVariableUnite);
        }
        requestProperties.setVariableTypeDonnees(variableTypeDonnees);
    }

    /**
     * <p>
     * test if date from requestProperties.getDatedeDebut() and
     * requestProperties.getDatedeFin() are the same than those in file name</p>
     * <p>
     * locale dates are compared</p>
     * <p>
     * Add errors in badsFormatsReport
     * <ul><li>bad begin date → org.cnrs.osuc.snot.dataset.messages /
     * PROPERTY_MSG_IMPROPER_BEGIN_DATE</li>
     * <li>bad end date→ org.cnrs.osuc.snot.dataset.messages /
     * PROPERTY_MSG_IMPROPER_END_DATE</li>
     * </ul></p>
     *
     * @param badsFormatsReport
     * @param version
     * @param requestProperties
     * @param lineNumber
     * @param datasetDescriptor
     */
    protected void testAreConsistentDates(BadsFormatsReport badsFormatsReport, VersionFile version, IRequestProperties requestProperties, final long lineNumber, DatasetDescriptor datasetDescriptor) {
        LocalDateTime dateDeDebut = null, dateDeFin = null;
        dateDeDebut = requestProperties.getDatedeDebut();
        dateDeFin = requestProperties.getDateDeFin();
        String formatDate = this.getFormatDate(version);
        if (dateDeDebut == null || !version.getDataset().getDateDebutPeriode().isEqual(dateDeDebut)) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_IMPROPER_BEGIN_DATE), DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, formatDate), lineNumber, 1, this.buildDownloadFilename(version))));
        }
        if (dateDeFin == null || !version.getDataset().getDateFinPeriode().toLocalDate().isEqual(dateDeFin.toLocalDate())) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_IMPROPER_END_DATE), DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, formatDate), lineNumber, 2, this.buildDownloadFilename(version))));
        }
    }

    /**
     *
     * @param policyManager
     */
    public void setPolicyManager(IPolicyManager policyManager) {
        this.policyManager = policyManager;
    }

}
