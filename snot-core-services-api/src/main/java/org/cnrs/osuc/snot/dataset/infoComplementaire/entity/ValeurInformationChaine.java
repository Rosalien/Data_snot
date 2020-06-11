package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementChaineDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = "valeur_information_chaine",
        indexes = {
            @Index(name = "vich_vic_idx", columnList = "id_info")
        })
public class ValeurInformationChaine extends ValeurInformationComplementaire {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected String valeur;

    /**
     *
     */
    public ValeurInformationChaine() {
        super();
    }

    /**
     *
     * @return
     */
    public String getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    @Override
    public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
        ValeurComplementChaineDTO monDTO = null;
        try {
            monDTO = (ValeurComplementChaineDTO) dto;
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
        return this.valeur;
    }

    /**
     *
     */
    public static class MaFabrique implements IFabrique<ValeurInformationChaine> {

        /**
         *
         * @return
         */
        @Override
        public ValeurInformationChaine fabrique() {
            return new ValeurInformationChaine();
        }
    }

}
