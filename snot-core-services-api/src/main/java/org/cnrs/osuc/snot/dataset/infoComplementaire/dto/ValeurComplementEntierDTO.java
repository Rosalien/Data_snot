package org.cnrs.osuc.snot.dataset.infoComplementaire.dto;

/**
 *
 * @author ptcherniati
 */
public class ValeurComplementEntierDTO extends ValeurComplementDTO {

    int valeurInfo;

    /**
     *
     */
    public ValeurComplementEntierDTO() {
        super();
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     */
    public ValeurComplementEntierDTO(String nom, int valeurInfo) {
        this.nom = nom;
        this.valeurInfo = valeurInfo;
    }

    /**
     *
     * @return
     */
    public int getValeurInfo() {
        return this.valeurInfo;
    }

    /**
     *
     * @param valeurInfo
     */
    public void setValeurInfo(int valeurInfo) {
        this.valeurInfo = valeurInfo;
    }

}
