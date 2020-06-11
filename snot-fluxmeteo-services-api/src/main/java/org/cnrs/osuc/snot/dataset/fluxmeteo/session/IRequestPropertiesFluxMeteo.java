package org.cnrs.osuc.snot.dataset.fluxmeteo.session;

import org.cnrs.osuc.snot.dataset.IRequestProperties;

/**
 *
 * @author ptcherniati
 */
public interface IRequestPropertiesFluxMeteo extends IRequestProperties {

    /**
     *
     * @return
     */
    boolean isVerifierSequenceDate();

    /**
     *
     * @param verifierSequenceDate
     */
    void setVerifierSequenceDate(boolean verifierSequenceDate);
}
