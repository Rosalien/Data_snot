package org.cnrs.osuc.snot.dataset.impl;

import com.google.common.base.Strings;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.cnrs.osuc.snot.dataset.IErrorsReport;
import org.cnrs.osuc.snot.dataset.ITestDuplicates;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class AbstractTestDuplicate.
 */
public abstract class AbstractTestDuplicate implements ITestDuplicates {

    /**
     *
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTestDuplicate.class);
    /** The Constant serialVersionUID @link(long). */
    static final long serialVersionUID = 1L;
    /** The errors report @link(IErrorsReport). */
    protected IErrorsReport errorsReport = new ErrorsReport();

    /**
     * Instantiates a new abstract test duplicate.
     */
    public AbstractTestDuplicate() {
        super();
    }

    /**
     * Adds the errors.
     * 
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report {@link BadsFormatsReport} the bads formats report
     */
    @Override
    public void addErrors(final BadsFormatsReport badsFormatsReport) {
        badsFormatsReport.addException(new BusinessException(this.errorsReport.getErrorsMessages()));
    }

    /**
     * Checks for error.
     * 
     * @return true, if successful
     * @see org.inra.ecoinfo.acbb.dataset.ITestDuplicates#hasError()
     */
    @Override
public boolean hasError() {
        return this.errorsReport.hasErrors();
    }

    /**
     * Sets the errors report.
     * 
     * @param errorsReport
     *            the new errors report @link(IErrorsReport) {@link ErrorsReport} the new errors report
     */
    @Override
    public final void setErrorsReport(final IErrorsReport errorsReport) {

        this.errorsReport = errorsReport;
    }

    /**
     *
     * @param args
     * @return
     */
    protected String getKey(final String... args) {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                buf.append(Constantes.CST_UNDERSCORE);
            }
            buf.append(args[i]);
            
        }
        return buf.toString();
    }

    /**
     *
     * @param dates
     * @param dateString
     * @param timeString
     * @param lineNumber
     * @param versionFile
     */
    protected void testLineForDuplicatesDateInDB(String[] dates, String dateString, String timeString, final long lineNumber, VersionFile versionFile) {
        if (dates == null || dates.length != 4 || Strings.isNullOrEmpty(dateString) || Strings.isNullOrEmpty(timeString)) {
            return;
        }
        if (this.isLimitDate(dates[0], dates[1], dateString, timeString)) {
            this.testLineForDuplicatesLineinDb(dates[0], dates[1], lineNumber, versionFile);
        } else if (this.isLimitDate(dates[2], dates[3], dateString, timeString)) {
            this.testLineForDuplicatesLineinDb(dates[2], dates[3], lineNumber, versionFile);
        }
    }

    /**
     *
     * @param dateDBString
     * @param timeDBString
     * @param lineNumber
     * @param versionFile
     */
    protected void testLineForDuplicatesLineinDb(String dateDBString, String timeDBString, final long lineNumber, VersionFile versionFile) {}

    /**
     *
     * @param startDateDB
     * @param endDateDB
     * @param startDate
     * @param endDate
     * @return
     */
    protected boolean isLimitDate(String startDateDB, String endDateDB, String startDate, String endDate) {
        try {
            String localValue = this.getLocalValue(startDate, endDate);
            String dateFormatUTC = SNOTRecorder.DD_MM_YYYY_HHMMSS_READ;
            LocalDateTime date = DateUtil.readLocalDateTimeFromText(dateFormatUTC, localValue);
            LocalDateTime dbDate = DateUtil.readLocalDateTimeFromText(dateFormatUTC, startDateDB + endDateDB + ":00");
            return date.isEqual(dbDate);
        } catch (DateTimeParseException e) {
            LOGGER.info("pas de date", e);
            return false;
        }

    }

    /**
     *
     * @param dateString
     * @param timeString
     * @return
     */
    protected String getLocalValue(String dateString, String timeString) {
        return String.format("%-" + SNOTRecorder.DD_MM_YYYY_HHMMSS_READ.length() + "." + SNOTRecorder.DD_MM_YYYY_HHMMSS_READ.length() + "s", dateString + timeString + ":00");
    }

}