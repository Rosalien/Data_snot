package org.cnrs.osuc.snot.ui.flex.vo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ptcherniati
 */
public class SynthesisVariableVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<DatePlotVO> datesPlots = new LinkedList<>();
    private String variable;

    /**
     *
     */
    public SynthesisVariableVO() {
        super();
    }

    /**
     *
     * @param variable
     */
    public SynthesisVariableVO(String variable) {
        super();
        this.variable = variable;
    }

    /**
     *
     * @return
     */
    public List<DatePlotVO> getDatesPlots() {
        return this.datesPlots;
    }

    /**
     *
     * @param datesPlots
     */
    public void setDatesPlots(List<DatePlotVO> datesPlots) {
        this.datesPlots = datesPlots;
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
