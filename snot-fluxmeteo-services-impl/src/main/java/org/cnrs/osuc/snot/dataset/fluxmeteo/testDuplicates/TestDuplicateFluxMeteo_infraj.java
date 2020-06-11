/**
 *
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.testDuplicates;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.cnrs.osuc.snot.dataset.ITestDuplicates;
import org.cnrs.osuc.snot.dataset.impl.AbstractTestDuplicate;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;



/**
 * The Class TestDuplicateWsol.
 * <p>
 * implementation for Tillage of {@link ITestDuplicates}
 * 
 * @author Tcherniatinsky Philippe test the existence of duplicates in flux files
 */
public class TestDuplicateFluxMeteo_infraj extends AbstractTestDuplicate {


    /**
     * The Constant BUNDLE_SOURCE_PATH @link(String).
     */
    public static final String SNOT_DATASET_FLUXMETEO_BUNDLE_NAME = "org.cnrs.osuc.snot.dataset.fluxmeteo.impl.messages";

    /**
     *
     */
    public static final String PROPERTY_MSG_DOUBLON_LINE_INFRA_J = "PROPERTY_MSG_DOUBLON_LINE_INFRA_J";
    /**
     * The Constant serialVersionUID @link(long).
     */
    static final long serialVersionUID = 1L;

    /**
     * The date time line.
     * 
     * @link(SortedMap<String,SortedMap<String,SortedSet<Long>>>).
     */
    SortedMap<String, SortedSet<Long>> line;

    /**
     * Instantiates a new test duplicate flux.
     */
    public TestDuplicateFluxMeteo_infraj() {
        this.line = new TreeMap<>();
    }

    /**
     * Adds the line.
     * 
     * @param values
     * @link(String[]) the values
     * @param lineNumber
     *            long the line number {@link String[]} the values
     */
    @Override
    public void addLine(String[] values, long lineNumber) {
        this.addLine(values[0], values[1], lineNumber + 1);
    }

    /**
     * Adds the line.
     *
     * @param date
     * @param time
     * @link(String)
     * @link(String)
     * @link(String) the numero
     * @param lineNumber
     *            long the line number
     * @link(String) the date
     * @link(String) the order of the intervention in the day
     * @link(String) the number of the tool in intervention
     */
    protected void addLine(final String date, final String time, final long lineNumber) {
        final String key = this.getKey(date, time);
        if (!this.line.containsKey(key)) {
            SortedSet<Long> lineNumbers = new TreeSet();
            lineNumbers.add(lineNumber);
            this.line.put(key, lineNumbers);
        } else {
            this.line.get(key).add(lineNumber);
            this.errorsReport.addErrorMessage(String.format(SNOTRecorder.getSnotMessageWithBundle(TestDuplicateFluxMeteo_infraj.SNOT_DATASET_FLUXMETEO_BUNDLE_NAME, TestDuplicateFluxMeteo_infraj.PROPERTY_MSG_DOUBLON_LINE_INFRA_J), lineNumber, date, time, this.line.get(key).first().intValue()));
        }

    }
}
