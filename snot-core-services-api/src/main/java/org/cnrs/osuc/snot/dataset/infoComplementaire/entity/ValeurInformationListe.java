package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementItemListeDTO;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = "valeur_information_liste",
        indexes = {
            @Index(name = "vil_vic_idx", columnList = "id_info"),
            @Index(name = "vil_item_idx", columnList = "id_item")
        })
public class ValeurInformationListe extends ValeurInformationComplementaire {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false)
    @JoinColumn(name = "id_item")
    protected ItemListe valeur;

    /**
     *
     */
    public ValeurInformationListe() {
        super();
    }

    /**
     *
     * @param valeur
     */
    public ValeurInformationListe(ItemListe valeur) {
        super();
        this.valeur = valeur;
    }

    /**
     *
     * @return
     */
    public ItemListe getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(ItemListe valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    @Override
    public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
        ValeurComplementItemListeDTO monDTO = null;
        try {
            monDTO = (ValeurComplementItemListeDTO) dto;
        } catch (ClassCastException e) {
            String message = this.localizationManager.getMessage(ValeurInformationComplementaire.BUNDLE_SOURCE_PATH, "TYPE_DTO_INCORRECT");
            BusinessException erreur = new BusinessException(message);
            throw erreur;
        }
        this.valeur = monDTO.getValeurInfo();
    }

    /**
     *
     * @return
     */
    @Override
    public String valeurToString() {
        return this.valeur.getNote();
    }

    /**
     *
     */
    public static class MaFabrique implements IFabrique<ValeurInformationListe> {

        /**
         *
         * @return
         */
        @Override
        public ValeurInformationListe fabrique() {
            return new ValeurInformationListe();
        }
    }

}
