package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.axis.NumberTickUnit;

/**
 *
 * @author ptcherniati
 */
public class MyTickUnit extends NumberTickUnit {


    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected List<AbstractOrdonnee> ordonnees = new ArrayList<>();

    /**
     *
     * @param size
     */
    public MyTickUnit(double size) {
        super(size);
    }

    /**
     *
     * @param size
     * @param ordonnees
     */
    public MyTickUnit(double size, List<AbstractOrdonnee> ordonnees) {
        super(size);
        this.ordonnees = ordonnees;
    }

    /**
     *
     * @return
     */
    public List<AbstractOrdonnee> getOrdonnees() {
        return this.ordonnees;
    }

    /**
     *
     * @param ordonnees
     */
    public void setOrdonnees(List<AbstractOrdonnee> ordonnees) {
        this.ordonnees = ordonnees;
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public String valueToString(double value) {
        String reponse = "";
        for (AbstractOrdonnee ordonnee : this.ordonnees) {
            if ((int)value == ordonnee.getSonOrdonnee()) {
                return  ordonnee.getOrdonneeReelle();
            }
        }
        return reponse;
    }

}
