package org.cnrs.osuc.snot.dataset.fluxmeteo.valeurs;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.fluxmeteo.IVerificateurValeur;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.IExpertColonne;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadValueTypeException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.NullValueException;

/**
 *
 * @author ptcherniati
 */
public abstract class AbstractVerificateurValeur implements IVerificateurValeur {

    /**
     *
     */
    protected static ILocalizationManager localizationManager;

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.messages";

    /**
     *
     */
    protected static final String COMMA = Constantes.CST_COMMA;

    /**
     *
     */
    protected static final String DOT = Constantes.CST_DOT;

    /**
     *
     */
    protected static final String SPACE = Constantes.CST_SPACE;

    /**
     *
     */
    protected static final String BAD_MESURE = Constantes.PROPERTY_CST_INVALID_BAD_MEASURE;

    /**
     *
     */
    protected static final String QUALITY_CLASS_REGEXP = Constantes.REGEXP_QUALITY_CLASS_NUMBER;

    /**/

    /**
     *
     * @param localizationManager
     */
    
    public static void setLocalizationManager(ILocalizationManager localizationManager) {
        AbstractVerificateurValeur.localizationManager = localizationManager;
    }

    /**
     *
     */
    protected DatasetDescriptor datasetDescriptor;

    @Override
    public void verfierValeurEntiere(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol) {
        value = value.replaceAll(AbstractVerificateurValeur.SPACE, Constantes.CST_STRING_EMPTY);
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "BAD_INT_VALUE");
            String messageErreur = String.format(message, noLigne, noCol, column.getName(), value);
            erreurs.addException(new BadValueTypeException(messageErreur));
        }

    }

    @Override
    public void verfierValeurQualite(BadsFormatsReport erreurs, IExpertColonne expertColonne, Column column, String value, String[] values, int noLigne, int noCol) {
        if (!value.matches(AbstractVerificateurValeur.QUALITY_CLASS_REGEXP)) {
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "QUALITY_CLASS_NON_VALIDE");
            String messageErreur = String.format(message, value, noLigne, noCol, column.getName());
            erreurs.addException(new NullValueException(messageErreur));
            return;
        }
        int referencedColumn = this.getNoVariableReferencePar(noCol - 1, expertColonne);
        if (referencedColumn == -1) {
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "MISSING_REFERENCED_VALUE");
            String messageErreur = String.format(message, noLigne, noCol - 1, column.getRefVariableName());
            erreurs.addException(new BadExpectedValueException(messageErreur));
            return;
        }
        if (referencedColumn > values.length) {
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "OUT_OF_BOUND_REFERENCED_VALUE");
            String messageErreur = String.format(message, noLigne, noCol - 1, column.getRefVariableName());
            erreurs.addException(new BadExpectedValueException(messageErreur));
            return;
        }
        String referencedValue = values[referencedColumn];
        int noMediocre = QualityClass.MEDIOCRE.getNumero();
        if (Integer.parseInt(value) != noMediocre && referencedValue.equals(AbstractVerificateurValeur.BAD_MESURE)) {
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "REFERENCED_VALUE_NOT_BAD_VALUE");
            String messageErreur = String.format(message, noLigne, noCol - 1, value, referencedValue, noMediocre, AbstractVerificateurValeur.BAD_MESURE);
            erreurs.addException(new BadExpectedValueException(messageErreur));
        }

    }

    @Override
    public void verfierValeurReelle(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol, Map<String, DatatypeVariableUniteSnot> vtd) {
        if (value.length() > 0) {
            this.convertirReel(erreurs, column.getName(), value, noLigne, noCol);
        }

    }

    @Override
    public boolean verifierPresenceAbsence(BadsFormatsReport erreurs, Column column, String value, int noLigne, int noCol) {
        boolean ok = true;
        if (!column.isNullable() && StringUtils.isEmpty(value)) {
            ok = false;
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "VALEUR_ABSENTE");
            String messageErreur = String.format(message, noLigne, noCol, column.getName());
            erreurs.addException(new NullValueException(messageErreur));
        }
        return ok;
    }

    /**
     *
     * @param erreurs
     * @param colonneName
     * @param value
     * @param noLigne
     * @param noCol
     * @return
     */
    protected boolean convertirReel(BadsFormatsReport erreurs, String colonneName, String value, int noLigne, int noCol) {
        boolean ok = true;
        value = value.replaceAll(AbstractVerificateurValeur.COMMA, AbstractVerificateurValeur.DOT);
        value = value.replaceAll(AbstractVerificateurValeur.SPACE, Constantes.CST_STRING_EMPTY);
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            ok = false;
            String message = AbstractVerificateurValeur.localizationManager.getMessage(AbstractVerificateurValeur.BUNDLE_SOURCE_PATH, "BAD_FLOAT_VALUE");
            String messageErreur = String.format(message, noLigne, noCol, colonneName, value);
            erreurs.addException(new BadValueTypeException(messageErreur));
        }
        return ok;
    }

    abstract int getNoVariableReferencePar(int i, IExpertColonne expertColonne);

}
