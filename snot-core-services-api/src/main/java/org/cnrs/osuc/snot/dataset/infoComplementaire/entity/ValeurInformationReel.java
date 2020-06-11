package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementReelDTO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = "valeur_information_reelle", 
        indexes = {
            @Index(name = "vir_vic_idx", columnList = "id_info")
        })
public class ValeurInformationReel extends ValeurInformationComplementaire {


    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected double valeur;

    /**
     *
     */
    public ValeurInformationReel() {
        super();
    }

    /**
     *
     * @return
     */
    public double getValeur() {
        return this.valeur;
    }

    /**
     *
     * @param valeur
     */
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    @Override
    public void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException {
        ValeurComplementReelDTO monDTO = null;
        try {
            monDTO = (ValeurComplementReelDTO) dto;
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
        Double maValeur = this.valeur;
        return maValeur.toString();
    }

    /**
     *
     */
    public static class MaFabrique implements IFabrique<ValeurInformationReel> {

        /**
         *
         * @return
         */
        @Override
        public ValeurInformationReel fabrique() {
            return new ValeurInformationReel();
        }
    }

}
