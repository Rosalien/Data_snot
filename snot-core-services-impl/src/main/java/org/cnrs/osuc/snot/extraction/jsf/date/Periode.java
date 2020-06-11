package org.cnrs.osuc.snot.extraction.jsf.date;

/**
 * The Class Periode.
 */
public class Periode {

    /**
     * The Constant LABEL.
     */
    public static final String CONTINUOUS_DATE = "DatesYearsContinuousFormParam";
    /**
     * The date start.
     */
    private String             dateStart;
    /**
     * The date end.
     */
    private String             dateEnd;

    /**
     * Instantiates a new periode.
     * 
     * @param dateStart
     *            the date start
     * @param dateEnd
     *            the date end
     */
    public Periode(String dateStart, String dateEnd) {
        super();
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Gets the date end.
     * 
     * @return the date end
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * Gets the date start.
     * 
     * @return the date start
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * Sets the date end.
     * 
     * @param dateEnd
     *            the new date end
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Sets the date start.
     * 
     * @param dateStart
     *            the new date start
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
}
