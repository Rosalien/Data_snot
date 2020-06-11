package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype;

/**
 *
 * @author ptcherniati
 */
public class SynthesisDatatypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            site;
    private String            datatype;
    private LocalDateTime              dateMin;
    private LocalDateTime              dateMax;

    /**
     *
     */
    public SynthesisDatatypeVO() {
        super();
    }

    /**
     *
     * @param synthesisDatatype
     * @param datatype
     */
    public SynthesisDatatypeVO(GenericSynthesisDatatype synthesisDatatype, String datatype) {
        this.site = synthesisDatatype.getSite();
        this.datatype = datatype;
        this.dateMin = synthesisDatatype.getMinDate();
        this.dateMax = synthesisDatatype.getMaxDate();
    }

    /**
     *
     * @param site
     * @param datatype
     * @param dateMin
     * @param dateMax
     */
    public SynthesisDatatypeVO(String site, String datatype, LocalDateTime dateMin, LocalDateTime dateMax) {
        super();
        this.site = site;
        this.datatype = datatype;
        this.dateMin = dateMin;
        this.dateMax = dateMax;
    }

    /**
     *
     * @return
     */
    public String getDatatype() {
        return this.datatype;
    }

    /**
     *
     * @param datatype
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDateMax() {
        return this.dateMax;
    }

    /**
     *
     * @param dateMax
     */
    public void setDateMax(LocalDateTime dateMax) {
        this.dateMax = dateMax;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getDateMin() {
        return this.dateMin;
    }

    /**
     *
     * @param dateMin
     */
    public void setDateMin(LocalDateTime dateMin) {
        this.dateMin = dateMin;
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

}
