/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import java.io.Serializable;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;

/**
 * The Interface ITestDuplicates.
 * <p>
 * interface objects used to test the existence of duplicates
 * 
 * @author Tcherniatinsky Philippe
 */
public interface ITestDuplicates extends Serializable {

    /** message name for send error message for one line. */
    String PROPERTY_MSG_DOUBLON_LINE         = "PROPERTY_MSG_DOUBLON_LINE";

    /**
     *
     */
    String PROPERTY_MSG_DOUBLON_LINE_IN_FILE = "PROPERTY_MSG_DOUBLON_LINE_IN_FILE";

    /**
     * Adds the errors.
     * 
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @link(BadsFormatsReport) the bads formats report
     */
    void addErrors(BadsFormatsReport badsFormatsReport);

    /**
     * Adds the line.
     * 
     * @param values
     * @link(String[]) the values
     * @param lineNumber
     *            int the line number {@link String[]} the values
     * @link(String[]) the values
     */
    void addLine(String[] values, long lineNumber);

    /**
     * Checks for error.
     * 
     * @return has duplicates line errors
     */
    boolean hasError();

    /**
     * Sets the errors report.
     * 
     * @param errorsReport
     *            the new errors report {@link IErrorsReport} setter for errors report object
     */
    void setErrorsReport(IErrorsReport errorsReport);
}
