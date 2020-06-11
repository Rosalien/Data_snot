package org.cnrs.osuc.snot.dataset.infoComplementaire.dto;

/**
 *
 * @author ptcherniati
 */
public class ValeurComplementChaineDTO extends ValeurComplementDTO {

    String valeurInfo;

    /**
     *
     */
    public ValeurComplementChaineDTO() {
        super();
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     */
    public ValeurComplementChaineDTO(String nom, String valeurInfo) {
        this.nom = nom;
        this.valeurInfo = valeurInfo;
    }

    /**
     *
     * @return
     */
    public String getValeurInfo() {
        return this.valeurInfo;
    }

    /**
     *
     * @param valeurInfo
     */
    public void setValeurInfo(String valeurInfo) {
        this.valeurInfo = valeurInfo;
    }

}
