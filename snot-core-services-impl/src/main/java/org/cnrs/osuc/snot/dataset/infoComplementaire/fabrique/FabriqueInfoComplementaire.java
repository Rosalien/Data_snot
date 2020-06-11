package org.cnrs.osuc.snot.dataset.infoComplementaire.fabrique;

import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementBooleanDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementChaineDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementEntierDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementItemListeDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementReelDTO;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationBoolean;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationChaine;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationComplementaire;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationEntier;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationListe;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationReel;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ItemListe;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
public class FabriqueInfoComplementaire {

    /**
     *
     */
    protected static FabriqueInfoBoolean fabriqueBoolean;

    /**
     *
     */
    protected static FabriqueInfoChaine fabriqueChaine;

    /**
     *
     */
    protected static FabriqueInfoEntier fabriqueEntier;

    /**
     *
     */
    protected static FabriqueInfoListe fabriqueListe;

    /**
     *
     */
    protected static FabriqueInfoReel fabriqueReel;

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, boolean valeurInfo)
            throws BusinessException {
        ValeurComplementBooleanDTO dto = new ValeurComplementBooleanDTO(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriqueBoolean
                .construire(new ValeurInformationBoolean.MaFabrique(), dto);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @param valeurDecoree
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, boolean valeurInfo,
            AbstractValeur valeurDecoree) throws BusinessException {
        ValeurInformationBoolean vic = (ValeurInformationBoolean) FabriqueInfoComplementaire
                .fabriquer(nom, valeurInfo);
        vic.setValeurDecoree(valeurDecoree);
        valeurDecoree.ajouterInfoComplementaire(vic);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, double valeurInfo)
            throws BusinessException {
        ValeurComplementReelDTO dto = new ValeurComplementReelDTO(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriqueReel.construire(
                new ValeurInformationReel.MaFabrique(), dto);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @param valeurDecoree
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, double valeurInfo,
            AbstractValeur valeurDecoree) throws BusinessException {
        ValeurInformationReel vic = (ValeurInformationReel) FabriqueInfoComplementaire.fabriquer(
                nom, valeurInfo);
        vic.setValeurDecoree(valeurDecoree);
        valeurDecoree.ajouterInfoComplementaire(vic);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, int valeurInfo)
            throws BusinessException {
        ValeurComplementEntierDTO dto = new ValeurComplementEntierDTO(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriqueEntier.construire(
                new ValeurInformationEntier.MaFabrique(), dto);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @param valeurDecoree
     * @return
     * @throws BusinessException
     */
    static public ValeurInformationComplementaire fabriquer(String nom, int valeurInfo,
            AbstractValeur valeurDecoree) throws BusinessException {
        ValeurInformationEntier vic = (ValeurInformationEntier) FabriqueInfoComplementaire
                .fabriquer(nom, valeurInfo);
        vic.setValeurDecoree(valeurDecoree);
        valeurDecoree.ajouterInfoComplementaire(vic);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    public static ValeurInformationComplementaire fabriquer(String nom, String valeurInfo)
            throws BusinessException {
        ValeurInformationComplementaire vic;
        if (FabriqueInfoComplementaire.fabriqueChaine.isAInfoListe(nom)) {
            vic = FabriqueInfoComplementaire.fabriquerAvecItem(nom, valeurInfo);
        } else {
            vic = FabriqueInfoComplementaire.fabriquerAvecChaine(nom, valeurInfo);
        }
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @param valeurDecoree
     * @return
     * @throws BusinessException
     */
    public static ValeurInformationComplementaire fabriquer(String nom, String valeurInfo, AbstractValeur valeurDecoree) throws BusinessException {
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriquer(nom, valeurInfo);
        vic.setValeurDecoree(valeurDecoree);
        valeurDecoree.ajouterInfoComplementaire(vic);
        return vic;
    }

    /**
     *
     * @param fabriqueBoolean
     */
    static public void setFabriqueBoolean(FabriqueInfoBoolean fabriqueBoolean) {
        FabriqueInfoComplementaire.fabriqueBoolean = fabriqueBoolean;
    }

    /**
     *
     * @param fabriqueChaine
     */
    static public void setFabriqueChaine(FabriqueInfoChaine fabriqueChaine) {
        FabriqueInfoComplementaire.fabriqueChaine = fabriqueChaine;
    }

    /**
     *
     * @param fabriqueEntier
     */
    public static void setFabriqueEntier(FabriqueInfoEntier fabriqueEntier) {
        FabriqueInfoComplementaire.fabriqueEntier = fabriqueEntier;
    }

    /**
     *
     * @param fabriqueListe
     */
    public static void setFabriqueListe(FabriqueInfoListe fabriqueListe) {
        FabriqueInfoComplementaire.fabriqueListe = fabriqueListe;
    }

    /**
     *
     * @param fabriqueReel
     */
    public static void setFabriqueReel(FabriqueInfoReel fabriqueReel) {
        FabriqueInfoComplementaire.fabriqueReel = fabriqueReel;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    protected static ValeurInformationComplementaire fabriquer(String nom, ItemListe valeurInfo) throws BusinessException {
        ValeurComplementItemListeDTO dto = new ValeurComplementItemListeDTO(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriqueListe.construire(
                new ValeurInformationListe.MaFabrique(), dto);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @param valeurDecoree
     * @return
     * @throws BusinessException
     */
    protected static ValeurInformationComplementaire fabriquer(String nom, ItemListe valeurInfo, AbstractValeur valeurDecoree) throws BusinessException {
        ValeurInformationListe vic = (ValeurInformationListe) FabriqueInfoComplementaire.fabriquer(
                nom, valeurInfo);
        vic.setValeurDecoree(valeurDecoree);
        valeurDecoree.ajouterInfoComplementaire(vic);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    protected static ValeurInformationComplementaire fabriquerAvecChaine(String nom, String valeurInfo) throws BusinessException {
        ValeurComplementChaineDTO dto = new ValeurComplementChaineDTO(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriqueChaine.construire(
                new ValeurInformationChaine.MaFabrique(), dto);
        return vic;
    }

    /**
     *
     * @param nom
     * @param valeurInfo
     * @return
     * @throws BusinessException
     */
    protected static ValeurInformationComplementaire fabriquerAvecItem(String nom, String valeurInfo) throws BusinessException {
        ItemListe item = FabriqueInfoComplementaire.fabriqueListe.trouverItem(nom, valeurInfo);
        ValeurInformationComplementaire vic = FabriqueInfoComplementaire.fabriquer(nom, item);
        return vic;
    }

}
