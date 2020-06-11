package org.cnrs.osuc.snot.dataset.infoComplementaire.dto;

/**
 *
 * @author ptcherniati
 */
public class ValeurComplementReelDTO extends ValeurComplementDTO {

    double valeurInfo;

    /**
     *
     */
    public ValeurComplementReelDTO() {
        super();
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     */
    public ValeurComplementReelDTO(String nom, double valeurInfo) {
        this.nom = nom;
        this.valeurInfo = valeurInfo;
    }

    /**
     *
     * @return
     */
    public double getValeurInfo() {
        return this.valeurInfo;
    }

    /**
     *
     * @param valeurInfo
     */
    public void setValeurInfo(double valeurInfo) {
        this.valeurInfo = valeurInfo;
    }

}
