package org.cnrs.osuc.snot.dataset.fluxmeteo.valeurs;

import java.util.Map;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.IExpertColonne;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;

/**
 *
 * @author ptcherniati
 */
public class VerificateurValeurFluxMeteo extends AbstractVerificateurValeur {

    @Override
    public void verfierValeurReelle(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol, Map<String, DatatypeVariableUniteSnot> vtd) {
        if (value.length() > 0) {
            boolean ok = this.convertirReel(erreurs, column.getName(), value, noLigne, noCol);
            if (ok) {
                DatatypeVariableUniteSnot dvuf = (DatatypeVariableUniteSnot) vtd.get(column.getName());
                Float min = dvuf.getMin();
                Float max = dvuf.getMax();
                this.verifierMinMax(erreurs, value, column.getName(), noLigne, noCol, min, max);
            }
        }
    }

    /**
     *
     * @param erreurs
     * @param value
     * @param name
     * @param noLigne
     * @param noCol
     * @param min
     * @param max
     */
    protected void verifierMinMax(BadsFormatsReport erreurs, String value, String name, int noLigne, int noCol, Float min, Float max) {
        Float badValeur = Float.parseFloat(AbstractVerificateurValeur.BAD_MESURE);
        Float valeur = Float.parseFloat(value);
        String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "BAD_INTERVAL_FLOAT_VALUE");
        String messageErreur = String.format(message, noLigne, noCol, name, valeur, min, max);
        if (valeur.intValue() != badValeur.intValue()) {
            if (min != null && valeur < min) {
                erreurs.addException(new BadExpectedValueException(messageErreur));
            }
            if (max != null && valeur > max) {
                erreurs.addException(new BadExpectedValueException(messageErreur));
            }
        }

    }

    @Override
                     int getNoVariableReferencePar(int i, IExpertColonne expertColonne) {
                         int no = expertColonne.getReferencedColumn(i);
                         return no;
    }

}
