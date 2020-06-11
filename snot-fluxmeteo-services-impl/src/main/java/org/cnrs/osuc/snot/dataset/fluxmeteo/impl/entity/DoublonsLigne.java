package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.cnrs.osuc.snot.utils.Util;

/**
 *
 * @author ptcherniati
 */
public class DoublonsLigne {

    private final SortedMap<String, SortedMap<String, SortedSet<Integer>>> dateTimeLine = new TreeMap<>();
    private final SortedMap<String, SortedSet<Integer>> dateLine = new TreeMap<>();

    /**
     *
     */
    protected ErrorsReport errorsReport = new ErrorsReport();

    /**
     *
     */
    public DoublonsLigne() {}

    /**
     * @param date
     * @param lineNumber
     */
    public void addLine(String date, int lineNumber) {
        if (this.dateLine.containsKey(date)) {
            this.errorsReport.addErrorMessage(String.format(Util.DOUBLON_LINE_DATE, lineNumber, date, this.dateLine.get(date).first()));
        } else {
            SortedSet<Integer> setLine = new TreeSet<>();
            this.dateLine.put(date, setLine);
        }

        this.dateLine.get(date).add(lineNumber);
    }

    /**
     *
     * @param date
     * @param time
     * @param lineNumber
     */
    public void addLine(String date, String time, int lineNumber) {
        if (!this.dateTimeLine.containsKey(date)) {
            SortedMap<String, SortedSet<Integer>> timeMap = new TreeMap<>();
            this.dateTimeLine.put(date, timeMap);
        }
        if (this.dateTimeLine.get(date).containsKey(time)) {
            this.errorsReport.addErrorMessage(String.format(Util.DOUBLON_LINE, lineNumber, date, time, this.dateTimeLine.get(date).get(time).first()));
        } else {
            SortedSet<Integer> setLine = new TreeSet<>();
            this.dateTimeLine.get(date).put(time, setLine);
        }
        this.dateTimeLine.get(date).get(time).add(lineNumber);
    }

    /**
     *
     * @return
     */
    public SortedMap<String, SortedSet<Integer>> getDateLine() {
        return this.dateLine;
    }

    /**
     *
     * @return
     */
    public SortedMap<String, SortedMap<String, SortedSet<Integer>>> getDateTimeLine() {
        return this.dateTimeLine;
    }

    /**
     *
     * @return
     */
    public ErrorsReport getErrorsReport() {
        return this.errorsReport;
    }

    /**
     *
     * @param errorsReport
     */
    public void setErrorsReport(ErrorsReport errorsReport) {
        this.errorsReport = errorsReport;
    }

}
