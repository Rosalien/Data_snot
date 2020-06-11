package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import java.util.List;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationListe;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public class FabriqueInfoListe extends Constructeur<ValeurInformationListe> {

    private ItemListe chercherItemAvecNote(List<ItemListe> liste, String note, String nomListe) throws BusinessException {
        ItemListe item = null;
        for (ItemListe unItem : liste) {
            if (unItem.getNote().equals(note)) {
                item = unItem;
                break;
            }
        }
        if (item == null) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "ITEM_INCONNU");
            String messageErreur = String.format(message, nomListe, note);
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        return item;
    }

    /**
     *
     * @param nom
     * @param note
     * @return
     * @throws BusinessException
     */
    protected ItemListe trouverItem(String nom, String note) throws BusinessException {
        InformationComplementaire info;
        try {
            info = this.trouverIdentite(nom);
        } catch (PersistenceException e) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "INFO_COMPLEMENTAIRE_INCONNUE");
            String messageErreur = String.format(message, nom);
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        if (!this.isAInfoListe(nom)) {
            String message = this.localizationManager.getMessage(Fabrique.BUNDLE_SOURCE_PATH, "INFO_INFO_SANS_LISTE");
            String messageErreur = String.format(message, nom);
            BusinessException erreur = new BusinessException(messageErreur);
            throw erreur;
        }
        String nomListe = info.getListeValeurInfo().getNom();
        List<ItemListe> laListe = info.getListeValeurInfo().getLstItemListe();
        ItemListe item = this.chercherItemAvecNote(laListe, note, nomListe);

        return item;
    }
}
