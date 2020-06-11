package org.cnrs.osuc.snot.synthesis.impl;

/**
 *
 * @author ptcherniati
 */
public class ValeurProfondeur {

    /**
     *
     */
    protected float valeur;

    /**
     *
     */
    protected int   profondeur;

    /**
     *
     */
    protected int   repetition;

    /**
     *
     */
    public ValeurProfondeur() {
    }

    /**
     *
     * @param valeur
     * @param profondeur
     * @param repetition
     */
    public ValeurProfondeur(float valeur, int profondeur, int repetition) {
        this.valeur = valeur;
        this.profondeur = profondeur;
        this.repetition = repetition;
    }

    /**
     *
     * @return
     */
    public int getRepetition() {
        return this.repetition;
    }

    /**
     *
     * @param repetition
     */
    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    /**
     *
     * @return
     */
    public float getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(float valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @return
     */
    public int getProfondeur() {
        return this.profondeur;
    }

    /**
     *
     * @param profondeur
     */
    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

}
