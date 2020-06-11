package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.util.Objects;

/**
 *
 * @author ptcherniati
 */
abstract public class AbstractOrdonnee implements Comparable<AbstractOrdonnee> {

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractOrdonnee){
            return Objects.equals(getOrdonneeReelle(), ((AbstractOrdonnee)obj).getOrdonneeReelle());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    public abstract Integer getSonOrdonnee();

    @Override
    public abstract int compareTo(AbstractOrdonnee ordonnee);

    /**
     *
     * @param o
     * @return
     */
    public abstract boolean uneOrdonneeVaut(String o);

    /**
     *
     * @return
     */
    public abstract String getOrdonneeReelle();
}
