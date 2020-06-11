package org.cnrs.osuc.snot.extraction.jsf.date;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.component.ValueHolder;
import javax.faces.event.AjaxBehaviorEvent;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;

/**
 * The Class DatesYearsContinuousFormParamVO.
 */
public class DatesFormParamVO extends AbstractDatesFormParam implements IDateFormParameter {

    /**
     *
     */
    public static final String SEMI_HORAIRE = Constantes.SEMI_HORAIRE;

    /**
     *
     */
    public static final String JOURNALIER = Constantes.JOURNALIER;

    /**
     *
     */
    public static final String MENSUEL = Constantes.MENSUEL;

    /**
     *
     */
    public static final String SEMI_HORAIRE_LONG = "MSG_FREQ_DATE_STEP_SH_RADIOBUTTON_LABEL";

    /**
     *
     */
    public static final String JOURNALIER_LONG = "MSG_FREQ_DATE_STEP_J_RADIOBUTTON_LABEL";

    /**
     *
     */
    public static final String MENSUEL_LONG = "MSG_FREQ_DATE_STEP_M_RADIOBUTTON_LABEL";

    /**
     *
     * @param localizationManager
     */
    public DatesFormParamVO(ILocalizationManager localizationManager) {
        super(localizationManager);
        this.frequences.put(String.format(SNOTRecorder.getSnotMessageWithBundle(BUNDLE_SOURCE_PATH, JOURNALIER_LONG)), Constantes.JOURNALIER);
        this.frequences.put(String.format(SNOTRecorder.getSnotMessageWithBundle(BUNDLE_SOURCE_PATH, MENSUEL_LONG)), Constantes.MENSUEL);
        this.frequences.put(String.format(SNOTRecorder.getSnotMessageWithBundle(BUNDLE_SOURCE_PATH, SEMI_HORAIRE_LONG)), Constantes.SEMI_HORAIRE);
        periods.add(new HashMap());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.inra.ecoinfo.acbb.extraction.jsf.AbstractDatesFormParam#buildSummary(java.io.PrintStream
     * )
     */
    /**
     *
     * @param printStream
     * @throws DateTimeException
     */
    @Override
    public void buildSummary(PrintStream printStream) throws DateTimeException {
        printStream.print(
                String.format(
                        getLocalizationManager().getMessage(AbstractDatesFormParam.BUNDLE_SOURCE_PATH, PROPERTY_MSG_FROM_TO),
                        dateStart == null ? periods.get(0).get(START_INDEX) : DateUtil.getUTCDateTextFromLocalDateTime(dateStart, DateUtil.DD_MM_YYYY),
                        dateEnd == null ? periods.get(0).get(END_INDEX) : DateUtil.getUTCDateTextFromLocalDateTime(dateEnd, DateUtil.DD_MM_YYYY)
                ));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.acbb.extraction.jsf.AbstractDatesFormParam#customValidate()
     */
    /**
     *
     */
    @Override
    public void customValidate() {
        LOGGER.info("unused");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.acbb.extraction.jsf.AbstractDatesFormParam#getPatternDate()
     */
    /**
     *
     * @return
     */
    @Override
    public String getPatternDate() {
        return DateUtil.DD_MM_YYYY;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.inra.ecoinfo.acbb.extraction.jsf.IDateFormParameter#getPeriodsFromDateFormParameter()
     */
    @Override
    public List<Periode> getPeriodsFromDateFormParameter() {
        return getPeriods().stream()
                .map(period -> new Periode(period.get(AbstractDatesFormParam.START_INDEX), period.get(AbstractDatesFormParam.END_INDEX)))
                .collect(Collectors.toList());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.acbb.extraction.jsf.AbstractDatesFormParam#getSummaryHTML()
     */
    /**
     *
     * @return @throws DateTimeException
     */
    @Override
    public String getSummaryHTML() throws DateTimeException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final PrintStream printStream;
        try {
            printStream = new PrintStream(bos, true, StandardCharsets.ISO_8859_1.name());
            if (!getIsValid()) {
                printValidityMessages(printStream);
                return bos.toString();
            }
            for (final Map<String, String> periodsMap : periods) {
                printStream.println(String.format(
                        "%s<br/>",
                        String.format(
                                getLocalizationManager().getMessage(AbstractDatesFormParam.BUNDLE_SOURCE_PATH, PROPERTY_MSG_FROM_TO),
                                periodsMap.get(AbstractDatesFormParam.START_INDEX),
                                periodsMap.get(AbstractDatesFormParam.END_INDEX)))
                );
            }
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return bos.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.acbb.extraction.jsf.AbstractDatesFormParam#isEmpty()
     */
    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        for (final Map<String, String> periodsMap : periods) {
            if (periodsMap.entrySet().stream().anyMatch((entry) -> (periodsMap.get(entry.getKey()).isEmpty()))) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param event
     */
    public void changeDateStart(AjaxBehaviorEvent event) {
        LocalDate date = (LocalDate) ((ValueHolder) event.getSource()).getValue();
        setDateStart(date);
        periods.get(0).put("start", date == null ? "" : DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY));
    }

    /**
     *
     * @param event
     */
    public void changeDateEnd(AjaxBehaviorEvent event) {
        LocalDate date = (LocalDate) ((ValueHolder) event.getSource()).getValue();
        setDateEnd(date);
        periods.get(0).put("end", date == null ? "" : DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY));
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
    @Override
    public List<IntervalDate> intervalsDate() {
        final List<IntervalDate> intervalsDate = new LinkedList();
        for (final Map<String, String> period : this.periods) {
            if (period.get(AbstractDatesFormParam.START_INDEX) == null || period.get(AbstractDatesFormParam.END_INDEX) == null) {
                continue;
            }
            try {
                intervalsDate.add(IntervalDate.getIntervalDateddMMyyyy(
                        period.get(AbstractDatesFormParam.START_INDEX).replaceAll("/", "-"),
                        period.get(AbstractDatesFormParam.END_INDEX).replaceAll("/", "-")));
            } catch (final BadExpectedValueException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return intervalsDate;

    }

    /**
     *
     * @return
     */
    @Override
    public DatesFormParamVO copy() {
        DatesFormParamVO copy = new DatesFormParamVO(localizationManager);
        copy.dateStart = dateStart;
        copy.dateEnd = dateEnd;
        copy.periods = periods;
        copy.rythme = rythme;
        this.frequences = frequences;
        return copy;
    }

    @Override
    public void setPeriods(List<Map<String, String>> periods) {
        this.periods = periods;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getFrequences() {
        return frequences;
    }

}
