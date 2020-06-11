package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementEntierDTO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = "valeur_information_entier",
        indexes = {
            @Index(name = "vie_vic_idx", columnList = "id_info")
        })
public class ValeurInformationEntier extends ValeurInformationComplementaire {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected int valeur;

    /**
     *
     * @return
     */
    public int getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    @Override
    public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
        ValeurComplementEntierDTO monDTO = null;
        try {
            monDTO = (ValeurComplementEntierDTO) dto;
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
        Integer maValeur = this.valeur;
        return maValeur.toString();
    }

    /**
     *
     */
    public static class MaFabrique implements IFabrique<ValeurInformationEntier> {

        /**
         *
         * @return
         */
        @Override
        public ValeurInformationEntier fabrique() {
            return new ValeurInformationEntier();
        }
    }
}
