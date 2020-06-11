package org.cnrs.osuc.snot.dataset;

import org.cnrs.osuc.snot.utils.Constantes;

/**
 * @author sophie
 *
 */
public class ErrorsReport implements IErrorsReport {

    /**
     * The Constant CST_SPACE_TAB @link(String).
     */
    static final String CST_SPACE_TAB = "      ";
    private static final long serialVersionUID = 1L;

    private String errorsMessages = new String();

    /**
     * @param errorMessage
     */
    @Override
    public void addErrorMessage(String errorMessage) {
        this.errorsMessages = this.errorsMessages.concat(Constantes.CST_HYPHEN).concat(errorMessage).concat(Constantes.CST_NEW_LINE);
    }

    /**
     * @return the errorsMessages
     */
    @Override
    public String getErrorsMessages() {
        return this.errorsMessages;
    }

    /**
     * @return true if errorsMessages not empty
     */
    @Override
    public boolean hasErrors() {
        return this.errorsMessages.length() > 0;
    }

    /**
     * Adds the error message.
     *
     * @param errorMessage
     * @link(String) the error message
     * @param e
     * @link(Throwable) the e
     * @see org.inra.ecoinfo.acbb.dataset.IErrorsReport#addErrorMessage(java.lang.String, java.lang.Throwable) Adds the error message and add it the exception message.
     * @link(String) the error message
     * @link(Throwable) the exception
     */
    @Override
    public void addErrorMessage(final String errorMessage, final Throwable e) {
        this.errorsMessages = this.errorsMessages.concat(Constantes.CST_HYPHEN).concat(errorMessage).concat(Constantes.CST_NEW_LINE).concat(ErrorsReport.CST_SPACE_TAB).concat(e.getMessage()).concat(Constantes.CST_NEW_LINE);
    }
}
