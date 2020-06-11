package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity;

import org.cnrs.osuc.snot.utils.Constantes;

/**
 *
 * @author ptcherniati
 */
public class ErrorsReport {

    private static final String NEW_LINE       = Constantes.CST_NEW_LINE;
    private static final String TIRET          = Constantes.CST_HYPHEN;
    private String              errorsMessages = new String();

    /**
     *
     * @param errorMessage
     */
    public void addErrorMessage(String errorMessage) {
        this.errorsMessages = this.errorsMessages.concat(ErrorsReport.TIRET).concat(errorMessage)
                .concat(ErrorsReport.NEW_LINE);
    }

    /**
     *
     * @return
     */
    public String getErrorsMessages() {
        return this.errorsMessages;
    }

    /**
     *
     * @return
     */
    public boolean hasErrors() {
        return this.errorsMessages.length() > 0;
    }
}
