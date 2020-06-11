package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

/**
 *
 * @author ptcherniati
 */
public class Traitement extends AbstractOrdonnee {

    /**
     *
     */
    protected Integer ordonnee;

    /**
     *
     */
    protected String sonNom;

    /**
     *
     */
    public Traitement() {
        super();
    }

    /**
     *
     * @param ordonnee
     * @param sonNom
     */
    public Traitement(Integer ordonnee, String sonNom) {
        super();
        this.ordonnee = ordonnee;
        this.sonNom = sonNom;
    }

    /**
     *
     * @return
     */
    public Integer getOrdonnee() {
        return this.ordonnee;
    }

    /**
     *
     * @param ordonnee
     */
    public void setOrdonnee(Integer ordonnee) {
        this.ordonnee = ordonnee;
    }

    /**
     *
     * @return
     */
    public String getSonNom() {
        return this.sonNom;
    }

    /**
     *
     * @param sonNom
     */
    public void setSonNom(String sonNom) {
        this.sonNom = sonNom;
    }

    /**
     *
     * @param traitement
     * @return
     */
    public int comparer(Traitement traitement) {
        return getSonNom().compareTo(traitement.getSonNom());
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getSonOrdonnee() {
        return getOrdonnee();
    }

    @Override
    public int compareTo(AbstractOrdonnee ordonnee) {
        return comparer((Traitement) ordonnee);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean uneOrdonneeVaut(String o) {
        return o.equals(this.sonNom);
    }

    /**
     *
     * @return
     */
    @Override
    public String getOrdonneeReelle() {
        return this.sonNom;
    }

}
