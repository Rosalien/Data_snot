package org.cnrs.osuc.snot.dataset.infoComplementaire.dto;

import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;

/**
 *
 * @author ptcherniati
 */
public class ValeurComplementItemListeDTO extends ValeurComplementDTO {

    ItemListe valeurInfo;

    /**
     *
     */
    public ValeurComplementItemListeDTO() {
        super();
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     */
    public ValeurComplementItemListeDTO(String nom, ItemListe valeurInfo) {
        this.nom = nom;
        this.valeurInfo = valeurInfo;
    }

    /**
     *
     * @return
     */
    public ItemListe getValeurInfo() {
        return this.valeurInfo;
    }

    /**
     *
     * @param valeurInfo
     */
    public void setValeurInfo(ItemListe valeurInfo) {
        this.valeurInfo = valeurInfo;
    }

}
