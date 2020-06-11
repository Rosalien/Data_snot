package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.IFabrique;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationComplementaire;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 * @param <R>
 */
public class Constructeur<R extends ValeurInformationComplementaire> extends Fabrique {

    /**
     *
     * @param fabrique
     * @param dto
     * @return
     * @throws BusinessException
     */
    public ValeurInformationComplementaire construire(IFabrique<R> fabrique, ValeurComplementDTO dto) throws BusinessException {
        InformationComplementaire info = null;
        try {
            info = this.trouverIdentite(dto.getNom());
        } catch (PersistenceException e) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "INFO_COMPLEMENTAIRE_INCONNUE");
            String messageErreur = String.format(message, dto.getNom());
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        R valeurInfo = fabrique.fabrique();
        valeurInfo.rangerComplementDTO(dto);
        valeurInfo.setIdentite(info);
        return valeurInfo;

    }

    /**
     *
     * @param fabrique
     * @param dto
     * @param qui
     * @return
     * @throws BusinessException
     */
    public ValeurInformationComplementaire construire(IFabrique<R> fabrique, ValeurComplementDTO dto, AbstractValeur qui) throws BusinessException {
        InformationComplementaire info = null;
        try {
            info = this.trouverIdentite(dto.getNom());
        } catch (PersistenceException e) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "INFO_COMPLEMENTAIRE_INCONNUE");
            String messageErreur = String.format(message, dto.getNom());
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        R valeurInfo = fabrique.fabrique();
        valeurInfo.rangerComplementDTO(dto);
        valeurInfo.setIdentite(info);
        valeurInfo.setValeurDecoree(qui);
        qui.ajouterInfoComplementaire(valeurInfo);
        return valeurInfo;

    }
}
