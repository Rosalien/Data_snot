package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

/**
 *
 * @author ptcherniati
 */
public class Profondeur extends AbstractOrdonnee {

    /**
     *
     */
    protected Integer profondeurReelle;

    /**
     *
     */
    protected Integer profondeurFictive;

    /**
     *
     * @param profondeurReelle
     * @param profondeurFictive
     */
    public Profondeur(Integer profondeurReelle, Integer profondeurFictive) {
        this.profondeurReelle = profondeurReelle;
        this.profondeurFictive = profondeurFictive;
    }

    /**
     *
     */
    public Profondeur() {
        super();
    }

    /**
     *
     * @return
     */
    public Integer getProfondeurReelle() {
        return this.profondeurReelle;
    }

    /**
     *
     * @param profondeurReelle
     */
    public void setProfondeurReelle(Integer profondeurReelle) {
        this.profondeurReelle = profondeurReelle;
    }

    /**
     *
     * @return
     */
    public Integer getProfondeurFictive() {
        return this.profondeurFictive;
    }

    /**
     *
     * @param profondeurFictive
     */
    public void setProfondeurFictive(Integer profondeurFictive) {
        this.profondeurFictive = profondeurFictive;
    }

    /**
     *
     * @param profondeur
     * @return
     */
    public int comparer(Profondeur profondeur) {
        int reponse;
        if (this.getProfondeurReelle() < profondeur.getProfondeurReelle()) {
            reponse = -1;
        } else if (this.getProfondeurReelle() > profondeur.getProfondeurReelle()) {
            reponse = 1;
        } else {
            reponse = 0;
        }
        return reponse;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getSonOrdonnee() {
        return this.profondeurFictive;
    }

    @Override
    public int compareTo(AbstractOrdonnee ordonnee) {
        Profondeur profondeur = (Profondeur) ordonnee;
        int reponse = this.comparer(profondeur);
        return reponse;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean uneOrdonneeVaut(String o) {
        boolean reponse = false;
        Integer oRecherchee = Integer.parseInt(o);
        if (oRecherchee == this.profondeurReelle) {
            reponse = true;
        }
        return reponse;
    }

    /**
     *
     * @return
     */
    @Override
    public String getOrdonneeReelle() {
        String reponse = this.profondeurReelle.toString();
        return reponse;
    }

}
