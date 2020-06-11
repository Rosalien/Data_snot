package org.cnrs.osuc.snot.dataset.fluxmeteo.colonne;

import java.util.List;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;

/**
 *
 * @author ptcherniati
 */
public interface IExpertColonne {

    /**
     *
     * @param nbInutiles
     * @param nomsColonnes
     * @return
     */
    boolean existeColoneVariable(int nbInutiles, String[] nomsColonnes);

    /**
     *
     * @return
     */
    List<Column> getColonnesDansDescripteur();

    /**
     *
     * @return
     */
    List<Column> getColonnesDansOrdreFichier();

    /**
     *
     * @param nom
     * @return
     */
    Column getColumn(String nom);

    /**
     *
     * @param nom
     * @return
     */
    int getNoQualityColumn(String nom);

    /**
     *
     * @param i
     * @return
     */
    int getReferencedColumn(int i);

    /**
     *
     * @param nomsColonnes
     * @param noLigne
     * @param badsFormatsReport
     */
    void rangerColonnesCommeDansFichier(String[] nomsColonnes, long noLigne, BadsFormatsReport badsFormatsReport);

    /**
     *
     * @param colonnesDansDescripteur
     */
    void setColonnesDansDescripteur(List<Column> colonnesDansDescripteur);

    /**
     *
     * @param colonnesDansOrdreFichier
     */
    void setColonnesDansOrdreFichier(List<Column> colonnesDansOrdreFichier);

    /**
     *
     * @param noLigne
     * @param badsFormatsReport
     * @return
     */
    boolean variablesObligatoiresPresentes(long noLigne, BadsFormatsReport badsFormatsReport);
}
