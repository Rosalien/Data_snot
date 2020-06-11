package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import javax.persistence.Transient;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.IInformationComplementaireDAO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public class Fabrique {

    /**
     *
     */
    public static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.infoComplementaire.messages";

    /**
     *
     */
    @Transient
    protected IInformationComplementaireDAO infoComplementaireDAO;

    /**
     *
     */
    @Transient
    protected ILocalizationManager localizationManager;

    /**
     *
     * @param nom
     * @return
     * @throws BusinessException
     */
    public boolean isAInfoListe(String nom) throws BusinessException {
        boolean reponse = true;
        InformationComplementaire info;
        try {
            info = this.trouverIdentite(nom);
        } catch (PersistenceException e) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "INFO_COMPLEMENTAIRE_INCONNUE");
            String messageErreur = String.format(message, nom);
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        if (info.getListeValeurInfo() == null || info.getListeValeurInfo().getLstItemListe() == null || info.getListeValeurInfo().getLstItemListe().isEmpty()) {
            reponse = false;
        }
        return reponse;
    }

    /**
     *
     * @param infoComplementaireDAO
     */
    public void setInfoComplementaireDAO(IInformationComplementaireDAO infoComplementaireDAO) {
        this.infoComplementaireDAO = infoComplementaireDAO;
    }

    /**
     *
     * @param localizationManager
     */
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     *
     * @param nom
     * @return
     * @throws PersistenceException
     */
    protected InformationComplementaire trouverIdentite(String nom) throws PersistenceException {
        return this.infoComplementaireDAO.getByNom(nom)
                .orElseThrow(PersistenceException::new);
    }
}
