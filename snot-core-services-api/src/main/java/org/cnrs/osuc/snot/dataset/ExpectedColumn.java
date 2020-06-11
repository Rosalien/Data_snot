/**
 *
 */
package org.cnrs.osuc.snot.dataset;

import org.inra.ecoinfo.utils.Column;

/**
 * @author sophie
 * 
 */
public class ExpectedColumn extends Column {

    private Float minValue;
    private Float maxValue;

    /**
     * empty constructor
     */
    public ExpectedColumn() {

    }

    /**
     * Constructeur
     * 
     * @param column
     */
    public ExpectedColumn(Column column) {
        super(column);
    }

    /**
     * @param column
     * @param minValue
     * @param maxValue
     */
    public ExpectedColumn(Column column, Float minValue, Float maxValue) {
        super(column);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * @param minValue
     * @param maxValue
     */
    /*
     * public ExpectedColumn(String name, Float minValue, Float maxValue) { super(); setName(name);
     * this.minValue = minValue; this.maxValue = maxValue; }
     */

    /**
     * @param minValue
     * @param maxValue
     */
    /*
     * public ExpectedColumn(String name, String refName, Float minValue, Float maxValue) { super();
     * setName(name); setRefVariableName(refName); this.minValue = minValue; this.maxValue =
     * maxValue; }
     */

    /**
     * @return the maxValue
     */
    public Float getMaxValue() {
        return this.maxValue;
    }

    /**
     * @param maxValue
     *            the maxValue to set
     */
    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public Float getMinValue() {
        return this.minValue;
    }

    /**
     * @param minValue
     *            the minValue to set
     */
    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }
}
