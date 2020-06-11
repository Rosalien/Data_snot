/**
 *
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.entete;

import com.Ostermiller.util.CSVParser;
import java.io.IOException;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.ITestHeaders;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.ExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.request.RequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.impl.AbstractVerificateurEntete;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.Util;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;


/**
 * @author sophie
 * 
 */
public abstract class AbstractVerificateurEnteteFluxMeteo extends AbstractVerificateurEntete implements ITestHeaders {

    /**
     *
     */
    protected static final String PROPERTY_MSG_BAD_FREQUENCE = "BAD_FREQUENCE";

    /**
     *
     */
    protected int nbColonnesIdentifiantes;

    @Override
    public long testHeaders(CSVParser parser, VersionFile versionFile, IRequestProperties requestProperties, String encoding, BadsFormatsReport badsFormatsReport, DatasetDescriptor datasetDescriptor) throws BusinessException {
        long lineNumber = super.testHeaders(parser, versionFile, requestProperties, encoding, badsFormatsReport, datasetDescriptor);
        lineNumber = this.readSite(versionFile, badsFormatsReport, parser, lineNumber, requestProperties);
        lineNumber = this.readDatatype(badsFormatsReport, parser, lineNumber);
        lineNumber = this.readFrequence(requestProperties, badsFormatsReport, parser, lineNumber);
        lineNumber = this.readBeginAndEndDates(versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        lineNumber = this.readCommentaires(badsFormatsReport, parser, lineNumber, requestProperties);
        lineNumber = this.readEmptyLine(badsFormatsReport, parser, lineNumber);
        lineNumber = this.jumpLines(parser, lineNumber, 1, badsFormatsReport, "nom complet des variables");
            lineNumber = this.readNomDesColonnes(versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        lineNumber = this.readUnites(versionFile, badsFormatsReport, parser, lineNumber, requestProperties, datasetDescriptor);
        this.remplirVariablesTypeDonnees(datasetDescriptor, requestProperties);
        return lineNumber;
    }

    /**
     *
     * @param nbColonnesIdentifiantes
     */
    public void setNbColonnesIdentifiantes(int nbColonnesIdentifiantes) {
        this.nbColonnesIdentifiantes = nbColonnesIdentifiantes;
    }

    /**
     * * defined abstract method of AbstractVerificateurEntete **
     * @param values
     * @param lineNumber
     * @param badsFormatsReport
     */
    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.dataset.impl.AbstractVerificateurEntete#getMessageFrequenceErreur(java.lang.String[], org.inra.ecoinfo.dataset.BadsFormatsReport, int)
     */
    @Override
    protected void getMessageFrequenceErreur(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber) {
        String message = String.format(SNOTRecorder.getSnotMessage(PROPERTY_MSG_BAD_FREQUENCE), lineNumber, values[1].toLowerCase(), Constantes.FREQUENCE_NAME[3] + ", " + Constantes.FREQUENCE_NAME[1] + ", " + Constantes.FREQUENCE_NAME[2]);
        badsFormatsReport.addException(new BadExpectedValueException(message));
    }

    /**
     * <p> test header columns using an org.​inra.​ecoinfo.​snot.​dataset.​fluxmeteo.​colonne.​ExpertColonne<p>
     * <ul>
     * <li>identifiable columns must be present</li>
     * <li>at least one variable columns must be present</li>
     * <li>Names must be the same as in dataset descriptor -case insensitive- else error : cnrs.osuc.snot.dataset.messages INTITULE_INEXISTANT</li>
     * <li>all mandatory columns must be present else error :cnrs.osuc.snot.dataset.messages VARIABLE_ABSENTE</li>
     * </ul>
     * <p> the expert column is adding to sessioPreperties.</p>
     *
     * @param version
     * @param badsFormatsReport
     * @param parser
     * @param lineNumber
     * @param requestProperties
     * @param datasetDescriptor
     * @return
     */
    @Override
    protected long readNomDesColonnes(VersionFile version, BadsFormatsReport badsFormatsReport, CSVParser parser, long lineNumber, IRequestProperties requestProperties, DatasetDescriptor datasetDescriptor) {
        long finalLineNumber = lineNumber;
        try {
            String[] values;
            values = parser.getLine();
            finalLineNumber++;
            requestProperties.setColumnNames(values, datasetDescriptor.getColumns());
            ExpertColonne expertColonne = new ExpertColonne(datasetDescriptor.getColumns());
            ((RequestPropertiesFluxMeteo) requestProperties).setExpertColonne(expertColonne);
            boolean ok = expertColonne.existeColoneVariable(this.nbColonnesIdentifiantes, values);
            if (!ok) {
                BadExpectedValueException exception = new BadExpectedValueException(SNOTRecorder.getSnotMessage(Util.AUCUNE_VARIABLE));
                badsFormatsReport.addException(exception);
            } else {
                expertColonne.rangerColonnesCommeDansFichier(values, finalLineNumber, badsFormatsReport);
            }
            return ++lineNumber;
        } catch (IOException e) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(this.localizationManager.getMessage(AbstractVerificateurEntete.BUNDLE_SOURCE_PATH, SNOTRecorder.PROPERTY_MSG_BAD_FILE_LENGTH), lineNumber)));
            return finalLineNumber;
        }
    }

}
