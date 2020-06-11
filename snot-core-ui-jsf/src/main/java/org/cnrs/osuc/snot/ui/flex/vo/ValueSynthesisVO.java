package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 *
 * @author ptcherniati
 */
public class ValueSynthesisVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime              date;

    private String            site;

    private String            variable;

    private Float             value;

    /**
     *
     * @param date
     * @param site
     * @param variable
     * @param value
     */
    public ValueSynthesisVO(LocalDateTime date, String site, String variable, Float value) {
        super();
        this.date = date;
        this.site = site;
        this.variable = variable;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     *
     * @param date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getSite() {
        return this.site;
    }

    /**
     *
     * @param site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     *
     * @return
     */
    public Float getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getVariable() {
        return this.variable;
    }

    /**
     *
     * @param variable
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

}
