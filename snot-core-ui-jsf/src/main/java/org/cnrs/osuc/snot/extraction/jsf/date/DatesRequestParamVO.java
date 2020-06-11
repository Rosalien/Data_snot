/**
 * OREILacs project - see LICENCE.txt for use created: 7 janv. 2010 09:54:40
 */
package org.cnrs.osuc.snot.extraction.jsf.date;

import org.cnrs.osuc.snot.extraction.jsf.date.IDateFormParameter;

/**
 * The Class DatesRequestParamVO.
 * 
 * 
 * @author "Antoine Schellenberger"
 */
public class DatesRequestParamVO {

    /**
     * The dates years continuous form param.
     * 
     * @link(DatesYearsContinuousFormParamVO).
     */
    IDateFormParameter datesFormParam;

    /**
     * 
     * @param datesFormParam
     */
    public DatesRequestParamVO(IDateFormParameter datesFormParam) {
        this.datesFormParam = datesFormParam;
    }

    /**
     * Gets the dates years continuous form param.
     * 
     * 
     * @return the dates years continuous form param
     */
    public IDateFormParameter getDatesFormParam() {
        return this.datesFormParam;
    }

    /**
     * Sets the dates years continuous form param.
     * 
     * 
     * @param dateFormParameter
     *            the new dates years continuous form param
     */
    public void setDatesYearsContinuousFormParam(
            final IDateFormParameter dateFormParameter) {
        this.datesFormParam = dateFormParameter;
    }

}
