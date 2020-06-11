package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 *
 * @author ptcherniati
 */
public class DatePlotVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime              date;
    private Float             value;

    /**
     *
     */
    public DatePlotVO() {
        super();
    }

    /**
     *
     * @param date
     */
    public DatePlotVO(LocalDateTime date) {
        super();
        this.date = date;
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
}
