package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity;

import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class VariableValue {

    private QualityClass  qualityClass;
    private Float         value;
    private RealNode realNode;

    /**
     *
     * @param realNode
     * @param variable
     * @param value
     * @param qualityClass
     */
    public VariableValue(RealNode realNode, Float value, QualityClass qualityClass) {
        super();
        this.realNode = realNode;
        this.value = value;
        this.qualityClass = qualityClass;
    }

    /**
     *
     * @return
     */
    public QualityClass getQualityClass() {
        return this.qualityClass;
    }

    /**
     *
     * @param qualityClass
     */
    public void setQualityClass(QualityClass qualityClass) {
        this.qualityClass = qualityClass;
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
    public RealNode getRealNode() {
        return realNode;
    }

    /**
     *
     * @param realNode
     */
    public void setRealNode(RealNode realNode) {
        this.realNode = realNode;
    }
}
