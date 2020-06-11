package org.cnrs.osuc.snot.extraction.jsf.date;

import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.UncatchedExceptionLogger;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractDatesFormParam.
 */
public abstract class AbstractDatesFormParam implements IDateFormParameter {

    /**
     * The Constant BUNDLE_SOURCE_PATH.
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.ui.flex.vo.messages";
    /**
     * The Constant MSG_END_DATE_INVALID.
     */
    private static final String MSG_END_DATE_INVALID = "PROPERTY_MSG_END_DATE_INVALID";
    /**
     * The Constant MSG_START_DATE_INVALID.
     */
    private static final String MSG_START_DATE_INVALID = "PROPERTY_MSG_START_DATE_INVALID";
    /**
     * The Constant MSG_END_DATE_EMPTY.
     */
    private static final String MSG_END_DATE_EMPTY = "PROPERTY_MSG_END_DATE_EMPTY";
    /**
     * The Constant MSG_START_DATE_EMPTY.
     */
    private static final String MSG_START_DATE_EMPTY = "PROPERTY_MSG_START_DATE_EMPTY";
    /**
     * The Constant MSG_BAD_PERIODE.
     */
    private static final String MSG_BAD_PERIODE = "PROPERTY_MSG_BAD_PERIODE";
    /**
     * The Constant MSG_FROM.
     */
    protected static final String MSG_FROM = "PROPERTY_MSG_FROM";
    /**
     * The Constant MSG_TO.
     */
    protected static final String MSG_TO = "PROPERTY_MSG_TO";
    /**
     * The Constant OR_CONDITION.
     */
    private static final String OR_CONDITION = "or";
    /**
     * The Constant DATE_FORMAT.
     */
    protected static final String DATE_FORMAT = "dd/MM/yyyy";
    /**
     * The Constant DATE_FORMAT_WT_YEAR.
     */
    protected static final String DATE_FORMAT_WT_YEAR = "dd/MM";
    /**
     * The Constant INITIAL_SQL_CONDITION.
     */
    protected static final String INITIAL_SQL_CONDITION = "and (";
    /**
     * The Constant SQL_CONDITION.
     */
    protected static final String SQL_CONDITION = "%s between :%s and :%s or ";
    /**
     * The Constant END_PREFIX.
     */
    protected static final String END_PREFIX = "end";
    /**
     * The Constant START_PREFIX.
     */
    protected static final String START_PREFIX = "start";
    /**
     * The Constant START_INDEX.
     */
    public static final String START_INDEX = "start";
    /**
     * The Constant END_INDEX.
     */
    public static final String END_INDEX = "end";

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatesFormParam.class);
    /**
     *
     */
    public static final String PROPERTY_MSG_FROM_TO = "PROPERTY_MSG_FROM_TO";
    /**
     *
     */
    public static final String PROPERTY_MSG_FROM_TO_2 = "PROPERTY_MSG_FROM_TO_2";
    /**
     * The periods.
     */
    protected List<Map<String, String>> periods = new LinkedList();
    /**
     * The localization manager.
     */
    protected ILocalizationManager localizationManager;
    /**
     * The validity messages.
     */
    protected List<String> validityMessages = new LinkedList<>();

    /**
     *
     */
    protected LocalDate dateEnd = null;

    /**
     *
     */
    protected LocalDate dateStart = null;

    /**
     *
     */
    protected Map<String, String> frequences = new HashMap();

    /**
     *
     */
    protected String rythme = Constantes.INFRA_JOURNALIER;

    /**
     * Instantiates a new abstract dates form param.
     */
    public AbstractDatesFormParam() {
        super();
        periods.add(buildNewMapPeriod());
    }

    /**
     * Instantiates a new abstract dates form param.
     *
     * @param localizationManager the localization manager
     */
    public AbstractDatesFormParam(ILocalizationManager localizationManager) {
        super();
        this.localizationManager = localizationManager;
    }

    /**
     * Builds the summary.
     *
     * @param printStream the print stream
     * @throws DateTimeException the parse exception
     */
    public abstract void buildSummary(PrintStream printStream) throws DateTimeException;

    /**
     * Custom validate.
     */
    public abstract void customValidate();

    /**
     * Gets the pattern date.
     *
     * @return the pattern date
     */
    public String getPatternDate() {
        return rythme == Constantes.MENSUEL ? DateUtil.MM_YYYY : DateUtil.DD_MM_YYYY;
    }

    /**
     * Gets the summary html.
     *
     * @return the summary html
     * @throws DateTimeException the parse exception
     */
    public abstract String getSummaryHTML() throws DateTimeException;

    /**
     * Gets the checks if the dates form param is valid.
     *
     * @return the checks if is valid
     */
    public Boolean getIsValid() {
        validityMessages = new LinkedList<>();
        customValidate();
        testValidityPeriods();
        return validityMessages.isEmpty();
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public abstract boolean isEmpty();

    /**
     * Gets the localization manager.
     *
     * @return the localization manager
     */
    public ILocalizationManager getLocalizationManager() {
        return localizationManager;
    }

    /**
     * Gets the periods.
     *
     * @return the periods
     */
    public List<Map<String, String>> getPeriods() {
        return periods;
    }

    /**
     * Gets the validity messages.
     *
     * @return the validity messages
     */
    public List<String> getValidityMessages() {
        return validityMessages;
    }

    private LocalDate parseEndDate(Integer index, final String dateFormat,
            final Map<String, String> periodsMap, LocalDate endDate) {
        try {
            if (!periodsMap.get(AbstractDatesFormParam.END_INDEX).matches(
                    getPatternDate().replaceAll("[^/]", "."))) {
                throw new DateTimeException("");
            }
            endDate = DateUtil.readLocalDateFromText(dateFormat, periodsMap.get(AbstractDatesFormParam.END_INDEX));
        } catch (final DateTimeException e) {
            validityMessages.add(String.format(
                    getLocalizationManager().getMessage(AbstractDatesFormParam.BUNDLE_SOURCE_PATH,
                            AbstractDatesFormParam.MSG_END_DATE_INVALID), index + 1,
                    getPatternDate(), periodsMap.get(AbstractDatesFormParam.END_INDEX)));
            UncatchedExceptionLogger.log("bads dates", e);
        }
        return endDate;
    }

    /**
     * Prints the validity messages.
     *
     * @param printStream the print stream
     */
    protected void printValidityMessages(PrintStream printStream) {
        printStream.println("<ul>");
        validityMessages.stream().forEach((validityMessage) -> {
            printStream.println(String.format("<li>%s</li>", validityMessage));
        });
        printStream.println("</ul>");
    }

    /**
     * Sets the localization manager.
     *
     * @param localizationManager the new localization manager
     */
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     * Sets the periods.
     *
     * @param periods the periods
     */
    public void setPeriods(List<Map<String, String>> periods) {
        this.periods = periods;
    }

    /**
     * Builds the new map period.
     *
     * @return the map
     */
    protected final Map<String, String> buildNewMapPeriod() {
        final Map<String, String> periodMap = new HashMap<>();
        periodMap.put(AbstractDatesFormParam.START_INDEX, org.apache.commons.lang.StringUtils.EMPTY);
        periodMap.put(AbstractDatesFormParam.END_INDEX, org.apache.commons.lang.StringUtils.EMPTY);
        return periodMap;
    }

    /**
     * Test validity periods.
     */
    protected void testValidityPeriods() {
        Integer index = 0;
        for (final Map<String, String> periodsMap : periods) {
            LocalDate startDate = null;
            LocalDate endDate = null;
            startDate = testStartDateForIndex(index, getPatternDate(), periodsMap, startDate);
            endDate = testEndDateForIndex(index, getPatternDate(), periodsMap, endDate);
            testIsGoodIntervalDateForIndex(index, startDate, endDate);
            index++;
        }
    }

    /**
     * test the start input for index or add message in validity message
     *
     * @param index
     * @param dateFormat
     * @param periodsMap
     * @param startDate
     * @return
     */
    private LocalDate testStartDateForIndex(Integer index, final String dateFormat,
            final Map<String, String> periodsMap, LocalDate startDate) {
        if (periodsMap.get(AbstractDatesFormParam.START_INDEX) == null
                || periodsMap.get(AbstractDatesFormParam.START_INDEX).trim().isEmpty()) {
            validityMessages.add(String.format(
                    getLocalizationManager().getMessage(AbstractDatesFormParam.BUNDLE_SOURCE_PATH,
                            AbstractDatesFormParam.MSG_START_DATE_EMPTY), index + 1,
                    getPatternDate()));
        } else {
            try {
                if (!periodsMap.get(AbstractDatesFormParam.START_INDEX).matches(
                        getPatternDate().replaceAll("[^/]", "."))) {
                    throw new DateTimeException("");
                }
                startDate = DateUtil.readLocalDateFromText(dateFormat, periodsMap.get(AbstractDatesFormParam.START_INDEX));
            } catch (final DateTimeException e) {
                validityMessages.add(String.format(
                        getLocalizationManager().getMessage(
                                AbstractDatesFormParam.BUNDLE_SOURCE_PATH,
                                AbstractDatesFormParam.MSG_START_DATE_INVALID), index + 1,
                        getPatternDate(), periodsMap.get(AbstractDatesFormParam.START_INDEX)));
                UncatchedExceptionLogger.log("bads dates", e);
            }
        }
        return startDate;
    }

    /**
     * test the end input for index or add message in validity message
     *
     * @param index
     * @param dateFormat
     * @param periodsMap
     * @param endDate
     * @return
     */
    private LocalDate testEndDateForIndex(Integer index, final String dateFormat,
            final Map<String, String> periodsMap, LocalDate endDate) {
        if (periodsMap.get(AbstractDatesFormParam.END_INDEX) == null
                || periodsMap.get(AbstractDatesFormParam.END_INDEX).trim().isEmpty()) {
            validityMessages
                    .add(String.format(
                            getLocalizationManager().getMessage(
                                    AbstractDatesFormParam.BUNDLE_SOURCE_PATH,
                                    AbstractDatesFormParam.MSG_END_DATE_EMPTY), index + 1,
                            getPatternDate()));
        } else {
            endDate = parseEndDate(index, dateFormat, periodsMap, endDate);
        }
        return endDate;
    }

    /**
     * test the if interval is correct or add message in validity message
     *
     * @param index
     * @param startDate
     * @param endDate
     * @return
     */
    private void testIsGoodIntervalDateForIndex(Integer index, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.compareTo(endDate) > 0) {
            validityMessages.add(String.format(
                    getLocalizationManager().getMessage(AbstractDatesFormParam.BUNDLE_SOURCE_PATH,
                            AbstractDatesFormParam.MSG_BAD_PERIODE), index + 1));
        }
    }

    /**
     *
     * @return @throws DateTimeException
     */
    public LocalDate getDateEnd() throws DateTimeException {
        return this.dateEnd;
    }

    /**
     *
     * @return @throws DateTimeException
     */
    public LocalDate getDateStart() throws DateTimeException {
        return this.dateStart;
    }

    /**
     *
     * @return
     */
    public String getRythme() {
        return rythme;
    }

    /**
     *
     * @param dateEnd
     */
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     *
     * @param dateStart
     */
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    /**
     *
     * @param rythme
     */
    public void setRythme(String rythme) {
        this.rythme = rythme;
    }

    /**
     *
     * @return @throws DateTimeException
     */
    /**
     *
     * @param dateEnd
     */
    /**
     *
     * @return @throws DateTimeException
     */
    /**
     *
     * @param dateStart
     */
    /**
     * Intervals date.
     *
     *
     * @return the list
     */
    public List<IntervalDate> intervalsDate() {
        final List<IntervalDate> intervalsDate = new LinkedList();
        for (final Map<String, String> period : this.periods) {
            try {
                IntervalDate intervalDate = new IntervalDate(
                        period.get(AbstractDatesFormParam.START_INDEX).replaceAll("/", "-"),
                        period.get(AbstractDatesFormParam.END_INDEX).replaceAll("/", "-"),
                        DateUtil.DD_MM_YYYY_FILE
                );
                if (rythme.equals(Constantes.MENSUEL)) {
                    intervalDate = new IntervalDate(
                            intervalDate.getBeginDate(),
                            intervalDate.getEndDate().with(TemporalAdjusters.lastDayOfMonth()),
                            DateUtil.MM_YYYY_FILE
                    );
                }
                intervalsDate.add(intervalDate);
            } catch (final BadExpectedValueException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return intervalsDate;
    }
}
