package org.cnrs.osuc.snot.extraction.jsf.date;

import java.util.List;

/**
 * The Interface IDateFormParameter.
 */
public interface IDateFormParameter {

    /**
     * Gets the periods from date form parameter.
     *
     * @return the periods from date form parameter
     */
    List<Periode> getPeriodsFromDateFormParameter();

    /**
     *
     * @param <T>
     * @return
     */
    public <T extends AbstractDatesFormParam> T copy();
}
