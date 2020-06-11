/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import java.io.Serializable;

/**
 * The Interface IErrorsReport.
 * <p>
 * interface objects that generate error reports
 * 
 * @author Tcherniatinsky Philippe
 */
public interface IErrorsReport extends Serializable {

    /**
     * Adds the error message.
     * 
     * @param errorMessage
     * @link(String) the error message
     * @link(String) the error message {@link String} add error to report
     */
    void addErrorMessage(String errorMessage);

    /**
     * Adds the error message and add it the exception message.
     * 
     * @param errorMessage
     * @link(String) the error message
     * @param e
     * @link(Throwable) the e
     * @link(String) the error message
     * @link(Throwable) the exception
     */
    void addErrorMessage(String errorMessage, Throwable e);

    /**
     * Gets the errors messages.
     * 
     * @return the errors report message
     */
    String getErrorsMessages();

    /**
     * Checks for errors.
     * 
     * @return true if report has errors
     */
    boolean hasErrors();

}
