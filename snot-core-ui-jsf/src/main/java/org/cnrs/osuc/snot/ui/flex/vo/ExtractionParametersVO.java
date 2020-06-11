package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ptcherniati
 */
public class ExtractionParametersVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Boolean valueMin = false;
    private Boolean valueMax = false;
    private List<String> targetValue = new ArrayList<>();
    private List<String> uncertainty = new ArrayList<>();

    /**
     *
     */
    public ExtractionParametersVO() {
        super();
    }

    /**
     *
     * @param name
     */
    public ExtractionParametersVO(String name) {
        super();
        this.name = name;

    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public List<String> getTargetValue() {
        return this.targetValue;
    }

    /**
     *
     * @param targetValue
     */
    public void setTargetValue(List<String> targetValue) {
        this.targetValue = targetValue;
    }

    /**
     *
     * @return
     */
    public List<String> getUncertainty() {
        return this.uncertainty;
    }

    /**
     *
     * @param uncertainty
     */
    public void setUncertainty(List<String> uncertainty) {
        this.uncertainty = uncertainty;
    }

    /**
     *
     * @return
     */
    public Boolean getValueMax() {
        return this.valueMax;
    }

    /**
     *
     * @param valueMax
     */
    public void setValueMax(Boolean valueMax) {
        this.valueMax = valueMax;
    }

    /**
     *
     * @return
     */
    public Boolean getValueMin() {
        return this.valueMin;
    }

    /**
     *
     * @param valueMin
     */
    public void setValueMin(Boolean valueMin) {
        this.valueMin = valueMin;
    }

}
