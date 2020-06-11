package org.cnrs.osuc.snot.synthesis;

import org.inra.ecoinfo.synthesis.MetaSynthesisDatatype;

/**
 *
 * @author ptcherniati
 */
public class SnotMetaSynthesisDatatype extends MetaSynthesisDatatype {
    /**
     * The build synthesis datatype query @link(String).
     * 
     */
    private boolean isDirectSynthesis;
    private String directBuildSynthesisValuesQuery;

    private String directBuildSynthesisDatatypeQuery="select new %s(site,min(date),max(date)) from  %s  group by site ";
    /**
     * The default build synthesis values query @link(String).
     */
    private String defaultBuildSynthesisValuesQuery;
    /**
     * The default build synthesis values query @link(String).
     */
    private String defaultBuildSynthesisValuesQueryType;
    /**
     *
     */
    protected boolean semiHoraire;
    /**
     *
     */
    protected boolean synthesisParProfondeur;
    /**
     *
     */
    protected boolean chambreAFlux;

    /**
     *
     * @return
     */
    public String getDefaultBuildSynthesisValuesQuery() {
        return defaultBuildSynthesisValuesQuery;
    }

    /**
     *
     * @param defaultBuildSynthesisValuesQuery
     */
    public void setDefaultBuildSynthesisValuesQuery(String defaultBuildSynthesisValuesQuery) {
        this.defaultBuildSynthesisValuesQuery = defaultBuildSynthesisValuesQuery;
    }

    /**
     *
     * @return
     */
    public String getDefaultBuildSynthesisValuesQueryType() {
        return defaultBuildSynthesisValuesQueryType;
    }

    /**
     *
     * @param defaultBuildSynthesisValuesQueryType
     */
    public void setDefaultBuildSynthesisValuesQueryType(String defaultBuildSynthesisValuesQueryType) {
        this.defaultBuildSynthesisValuesQueryType = defaultBuildSynthesisValuesQueryType;
    }

    /**
     *
     * @return
     */
    public boolean isIsDirectSynthesis() {
        return isDirectSynthesis;
    }

    /**
     *
     * @param isDirectSynthesis
     */
    public void setIsDirectSynthesis(boolean isDirectSynthesis) {
        this.isDirectSynthesis = isDirectSynthesis;
    }

    /**
     *
     * @return
     */
    public String getDirectBuildSynthesisValuesQuery() {
        return directBuildSynthesisValuesQuery;
    }

    /**
     *
     * @param directBuildSynthesisValuesQuery
     */
    public void setDirectBuildSynthesisValuesQuery(String directBuildSynthesisValuesQuery) {
        this.directBuildSynthesisValuesQuery = directBuildSynthesisValuesQuery;
    }

    /**
     *
     * @return
     */
    public String getDirectBuildSynthesisDatatypeQuery() {
        return directBuildSynthesisDatatypeQuery;
    }

    /**
     *
     * @param directBuildSynthesisDatatypeQuery
     */
    public void setDirectBuildSynthesisDatatypeQuery(String directBuildSynthesisDatatypeQuery) {
        this.directBuildSynthesisDatatypeQuery = directBuildSynthesisDatatypeQuery;
    }


    /**
     *
     * @return
     */
    public boolean isChambreAFlux() {
        return this.chambreAFlux;
    }

    /**
     *
     * @param chambreAFlux
     */
    public void setChambreAFlux(boolean chambreAFlux) {
        this.chambreAFlux = chambreAFlux;
    }

    /**
     *
     * @return
     */
    public boolean isSemiHoraire() {
        return this.semiHoraire;
    }

    /**
     *
     * @param semiHoraire
     */
    public void setSemiHoraire(final boolean semiHoraire) {
        this.semiHoraire = semiHoraire;
    }

    /**
     *
     * @return
     */
    public boolean isSynthesisParProfondeur() {
        return this.synthesisParProfondeur;
    }

    /**
     *
     * @param synthesisParProfondeur
     */
    public void setSynthesisParProfondeur(final boolean synthesisParProfondeur) {
        this.synthesisParProfondeur = synthesisParProfondeur;
    }

}
