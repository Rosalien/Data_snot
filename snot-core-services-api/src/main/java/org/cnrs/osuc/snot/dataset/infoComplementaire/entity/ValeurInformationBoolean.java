package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementBooleanDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = "valeur_information_boolean", 
        indexes = {
            @Index(name = "vib_vic_idx", columnList = "id_info")
        })
public class ValeurInformationBoolean extends ValeurInformationComplementaire {

    /**
     *
     */
    protected static final long serialVersionUID = 1L;

    /**
     *
     */
    protected boolean valeur;

    /**
     *
     */
    public ValeurInformationBoolean() {
        super();
    }

    /**
     *
     * @return
     */
    public boolean getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    @Override
    public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
        ValeurComplementBooleanDTO monDTO = null;
        try {
            monDTO = (ValeurComplementBooleanDTO) dto;
        } catch (ClassCastException e) {
            String message = this.localizationManager.getMessage(ValeurInformationComplementaire.BUNDLE_SOURCE_PATH, "TYPE_DTO_INCORRECT");
            BusinessException erreur = new BusinessException(message);
            throw erreur;
        }
        this.valeur = monDTO.isValeurInfo();
    }

    /**
     *
     * @return
     */
    @Override
    public String valeurToString() {
        if (this.valeur) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     *
     */
    public static class MaFabrique implements IFabrique<ValeurInformationBoolean> {

        /**
         *
         * @return
         */
        @Override
        public ValeurInformationBoolean fabrique() {
            return new ValeurInformationBoolean();
        }
    }
}
