package org.cnrs.osuc.snot.synthesis;

/**
 *
 * @author ptcherniati
 */
public class Facteur {

    /**
     *
     */
    protected int valeurNumerique;

    /**
     *
     */
    protected String valeurAlphanumerique;

    /**
     *
     */
    protected boolean isNumeriqueNatif;

    /**
     *
     */
    public Facteur() {
        super();
    }

    /**
     *
     * @param valeurNumerique
     * @param isNumeriqueNatif
     */
    public Facteur(int valeurNumerique, boolean isNumeriqueNatif) {
        super();
        this.valeurNumerique = valeurNumerique;
        this.isNumeriqueNatif = isNumeriqueNatif;
    }

    /**
     *
     * @param valeurNumerique
     * @param isNumeriqueNatif
     */
    public Facteur(Long valeurNumerique, boolean isNumeriqueNatif) {
        super();
        this.valeurNumerique = valeurNumerique.intValue();
        this.isNumeriqueNatif = isNumeriqueNatif;
    }

    /**
     *
     * @param valeurAlphanumerique
     * @param isNumeriqueNatif
     */
    public Facteur(String valeurAlphanumerique, boolean isNumeriqueNatif) {
        super();
        this.valeurAlphanumerique = valeurAlphanumerique;
        this.isNumeriqueNatif = isNumeriqueNatif;
    }

    /**
     *
     * @return
     */
    public String getValeurAlphanumerique() {
        return this.valeurAlphanumerique;
    }

    /**
     *
     * @param valeurAlphanumerique
     */
    public void setValeurAlphanumerique(String valeurAlphanumerique) {
        this.valeurAlphanumerique = valeurAlphanumerique;
    }

    /**
     *
     * @return
     */
    public int getValeurNumerique() {
        return this.valeurNumerique;
    }

    /**
     *
     * @param valeurNumerique
     */
    public void setValeurNumerique(int valeurNumerique) {
        this.valeurNumerique = valeurNumerique;
    }

    /**
     *
     * @return
     */
    public boolean isNumeriqueNatif() {
        return this.isNumeriqueNatif;
    }

    /**
     *
     * @param isNumeriqueNatif
     */
    public void setNumeriqueNatif(boolean isNumeriqueNatif) {
        this.isNumeriqueNatif = isNumeriqueNatif;
    }

    @Override
    public String toString() {
        String reponse = this.valeurAlphanumerique;
        if (this.isNumeriqueNatif) {
            Integer transit = this.valeurNumerique;
            reponse = transit.toString();
        }
        return reponse;
    }

}
