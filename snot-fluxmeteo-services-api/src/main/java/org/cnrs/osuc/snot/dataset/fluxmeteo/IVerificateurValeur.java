package org.cnrs.osuc.snot.dataset.fluxmeteo;

import java.util.Map;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.IExpertColonne;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;

/**
 *
 * @author ptcherniati
 */
public interface IVerificateurValeur {

    /**
     *
     * @param erreurs
     * @param column
     * @param value
     * @param noLigne
     * @param noCol
     */
    void verfierValeurEntiere(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol);

    /**
     *
     * @param erreurs
     * @param expertColonne
     * @param column
     * @param value
     * @param values
     * @param noLigne
     * @param noCol
     */
    void verfierValeurQualite(BadsFormatsReport erreurs, IExpertColonne expertColonne, Column column, String value, String[] values, int noLigne, int noCol);

    /**
     *
     * @param erreurs
     * @param column
     * @param value
     * @param noLigne
     * @param noCol
     * @param vtd
     */
    void verfierValeurReelle(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol, Map<String, DatatypeVariableUniteSnot> vtd);

    /**
     *
     * @param erreurs
     * @param column
     * @param value
     * @param noLigne
     * @param noCol
     * @return
     */
    boolean verifierPresenceAbsence(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol);
}
