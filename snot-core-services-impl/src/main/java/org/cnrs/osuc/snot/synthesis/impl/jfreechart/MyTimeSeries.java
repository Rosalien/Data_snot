package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.awt.Color;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author ptcherniati
 */
public class MyTimeSeries {

    /**
     *
     */
    protected TimeSeries timeSeries;

    /**
     *
     */
    protected Color      couleur;

    /**
     *
     * @param timeSeries
     * @param couleur
     */
    public MyTimeSeries(TimeSeries timeSeries, Color couleur) {
        super();
        this.timeSeries = timeSeries;
        this.couleur = couleur;
    }

    /**
     *
     */
    public MyTimeSeries() {
        super();
    }

    /**
     *
     * @return
     */
    public TimeSeries getTimeSeries() {
        return this.timeSeries;
    }

    /**
     *
     * @param timeSeries
     */
    public void setTimeSeries(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    /**
     *
     * @return
     */
    public Color getCouleur() {
        return this.couleur;
    }

    /**
     *
     * @param couleur
     */
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

}
