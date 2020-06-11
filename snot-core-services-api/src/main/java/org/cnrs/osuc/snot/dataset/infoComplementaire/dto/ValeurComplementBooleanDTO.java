package org.cnrs.osuc.snot.dataset.infoComplementaire.dto;

/**
 *
 * @author ptcherniati
 */
public class ValeurComplementBooleanDTO extends ValeurComplementDTO {

    boolean valeurInfo;

    /**
     *
     */
    public ValeurComplementBooleanDTO() {
        super();
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     */
    public ValeurComplementBooleanDTO(String nom, boolean valeurInfo) {
        this.nom = nom;
        this.valeurInfo = valeurInfo;
    }

    /**
     *
     * @return
     */
    public boolean isValeurInfo() {
        return this.valeurInfo;
    }

    /**
     *
     * @param valeurInfo
     */
    public void setValeurInfo(boolean valeurInfo) {
        this.valeurInfo = valeurInfo;
    }

}
