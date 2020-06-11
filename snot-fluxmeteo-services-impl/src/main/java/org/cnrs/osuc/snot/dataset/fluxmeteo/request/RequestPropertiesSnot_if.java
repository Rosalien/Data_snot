package org.cnrs.osuc.snot.dataset.fluxmeteo.request;

/**
 *
 * @author ptcherniati
 */
public class RequestPropertiesSnot_if extends RequestPropertiesFluxMeteo {

    /**
     *
     */
    protected int pas;

    /**
     *
     */
    public RequestPropertiesSnot_if() {
        super();
        this.verifierSequenceDate = true;
        this.pas = 30;
    }

    /**
     *
     * @return
     */
    public int getPas() {
        return this.pas;
    }

    /**
     *
     * @param pas
     */
    public void setPas(int pas) {
        this.pas = pas;
    }
}
