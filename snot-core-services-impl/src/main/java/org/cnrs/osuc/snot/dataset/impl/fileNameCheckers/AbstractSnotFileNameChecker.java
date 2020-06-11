/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl.fileNameCheckers;

import com.google.common.base.Strings;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.inra.ecoinfo.dataset.IFileNameChecker;
import org.inra.ecoinfo.dataset.config.IDatasetConfiguration;
import org.inra.ecoinfo.dataset.exception.InvalidFileNameException;
import org.inra.ecoinfo.dataset.impl.filenamecheckers.AbstractFileNameChecker;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Outils;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public abstract class AbstractSnotFileNameChecker extends AbstractFileNameChecker {

    /**
     *
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileNameChecker.class);

    /**
     *
     */
    protected static final String PATTERN_FILE_NAME = "(%s|.*?)_(%s|.*?)_(%s|.*?)_(%s|.*?).csv";

    /**
     *
     * @param version
     * @return
     */
    protected static String getDatatypeFromVersion(VersionFile version) {
        return Outils.chercherNodeable(version, DataType.class)
                .map(d -> d.getCode())
                .orElseGet(String::new);
    }

    /**
     *
     * @param version
     * @return
     */
    protected static String getSiteFromVersion(VersionFile version) {
        return Outils.chercherNodeable(version, SiteSnot.class)
                .map(d -> d.getCode().replaceAll("/", "-"))
                .orElseGet(String::new);
    }

    /**
     *
     */
    protected IDatasetConfiguration datasetConfiguration;

    /**
     *
     * @param dataset
     * @return
     */
    @Override
    public String getFilePath(Dataset dataset) {
        assert dataset != null : "null dataset";
        assert dataset.getDateDebutPeriode() != null : "null start date for version";
        assert dataset.getDateFinPeriode() != null : "null end date for version";
        Optional<SiteSnot> site = Outils.chercherNodeable(dataset, SiteSnot.class);
        Optional<DataType> dataType = Outils.chercherNodeable(dataset, DataType.class);
        String currentSite = site.map(s -> s.getPath()).orElseGet(String::new);
        if (!Strings.isNullOrEmpty(this.configuration.getSiteSeparatorForFileNames())) {
            currentSite = currentSite.replaceAll(PatternConfigurator.ANCESTOR_SEPARATOR, this.configuration.getSiteSeparatorForFileNames());
        }
        return String.format(IFileNameChecker.PATTERN_FILE_NAME_PATH, currentSite, dataType.map(d -> d.getCode()).orElseGet(String::new),
                DateUtil.getUTCDateTextFromLocalDateTime(dataset.getDateDebutPeriode(), getDatePattern()),
                DateUtil.getUTCDateTextFromLocalDateTime(dataset.getDateFinPeriode(), getDatePattern()),
                Optional.ofNullable(dataset).map(d -> d.getPublishVersion()).map(v -> v.getVersionNumber()).orElseGet(() -> dataset.getLastVersionNumber()));
    }

    /**
     *
     * @param fileName
     * @param version
     * @return
     * @throws InvalidFileNameException
     */
    @Override
    public boolean isValidFileName(String fileName, VersionFile version) throws InvalidFileNameException {
        String dateFormatToSearchPattern = getDatePattern().replaceAll("[A-Z]|[a-z]", ".");
        fileName = this.cleanFileName(fileName);
        final String patternFileName = String.format(PATTERN_FILE_NAME, getSiteFromVersion(version), getDatatypeFromVersion(version), dateFormatToSearchPattern, dateFormatToSearchPattern);
        Matcher splitFilename = Pattern.compile(patternFileName).matcher(fileName);
        testDates(version, getSiteFromVersion(version), getDatatypeFromVersion(version), splitFilename);
        testSite(version, splitFilename.group(1), getDatatypeFromVersion(version), getSiteFromVersion(version));
        testDatatype(getSiteFromVersion(version), getDatatypeFromVersion(version), splitFilename.group(2));
        String waitingFileName = version.getDataset().buildDownloadFilename(datasetConfiguration);
        return fileName.equals(cleanFileName(waitingFileName));
    }

    /**
     *
     * @param datasetConfiguration
     */
    public void setDatasetConfiguration(IDatasetConfiguration datasetConfiguration) {
        this.datasetConfiguration = datasetConfiguration;
    }

    /**
     * Test site.
     *
     * @param version the version
     * @param currentSite the current site
     * @param currentDatatype the current datatype
     * @param siteName the site name
     * @throws InvalidFileNameException the invalid file name exception
     * @link(VersionFile) the version
     * @link(String) the current site
     * @link(String) the current datatype
     * @link(String) the site name
     */
    @Override
    protected void testSite(final VersionFile version, final String currentSite, final String currentDatatype, final String siteName) throws InvalidFileNameException {
        List<Site> sites = siteDAO.getAll();
        String dbSiteName = null;
        for (Site site : sites) {
            if (site.getCode().replaceAll("/", "-").equals(siteName)) {
                dbSiteName = site.getCode();
                break;
            }
        }
        if (dbSiteName == null || !currentSite.equals(siteName)) {
            throw new InvalidFileNameException(String.format(AbstractFileNameChecker.INVALID_FILE_NAME, currentSite, currentDatatype, getDatePattern(), getDatePattern()));
        }
    }

    /**
     *
     * @param splitFilename
     * @param group
     * @return
     * @throws ParseException
     */
    protected LocalDateTime getDateForString(Matcher splitFilename, int group) throws ParseException {
        if (!splitFilename.matches()) {
            throw new ParseException("no parse exception", group);
        }
        return DateUtil.readLocalDateTimeFromText(getDatePattern(), splitFilename.group(group));
    }

    /**
     *
     * @return
     */
    @Override
    protected abstract String getDatePattern();

    /**
     *
     * @param version
     * @param currentSite
     * @param currentDatatype
     * @param splitFilename
     * @throws InvalidFileNameException
     */
    @Override
    protected void testDates(VersionFile version, String currentSite, String currentDatatype, Matcher splitFilename) throws InvalidFileNameException {
        IntervalDate intervalDate;
        try {
            if (!splitFilename.matches()) {
                throw new ParseException("no matches", 0);
            }
            if (getDatePattern().equals(DateUtil.MM_YYYY_FILE)) {
                LocalDateTime startDate = DateUtil.readLocalDateFromText(DateUtil.DD_MM_YYYY_FILE, "01-".concat(splitFilename.group(3)))
                        .atStartOfDay();
                LocalDateTime endDate = DateUtil.readLocalDateFromText(DateUtil.DD_MM_YYYY_FILE, "01-".concat(splitFilename.group(4)))
                        .with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay();
                intervalDate = new IntervalDate(startDate, endDate, getDatePattern());
            } else {
                intervalDate = new IntervalDate(splitFilename.group(3), splitFilename.group(4), getDatePattern());
            }
        } catch (BadExpectedValueException | ParseException e1) {
            throw new InvalidFileNameException(String.format(AbstractFileNameChecker.INVALID_FILE_NAME, currentSite, currentDatatype, getDatePattern(), getDatePattern(), AbstractFileNameChecker.FILE_FORMAT));
        }
        if (version != null) {
            try {
                version.getDataset().setDateDebutPeriode(intervalDate.getBeginDate());
                version.getDataset().setDateFinPeriode(intervalDate.getEndDate());
            } catch (DateTimeParseException e) {
                throw new InvalidFileNameException("Can't change locale", e);
            }
        }
    }

}
