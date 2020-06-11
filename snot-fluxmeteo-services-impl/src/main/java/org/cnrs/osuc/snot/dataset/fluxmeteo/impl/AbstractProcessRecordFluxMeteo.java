/**
 *
 */
package org.cnrs.osuc.snot.dataset.fluxmeteo.impl;

import com.Ostermiller.util.CSVParser;
import com.google.common.base.Strings;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.ExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.DatasetDescriptorFluxMeteo;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.cnrs.osuc.snot.dataset.fluxmeteo.request.RequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.impl.AbstractProcessRecordSnot;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author sophie
 *
 */
public abstract class AbstractProcessRecordFluxMeteo extends AbstractProcessRecordSnot {

    /**
     *
     */
    protected static final String BUNDLE_NAME = "org.cnrs.osuc.snot.dataset.messages";

    @Override
    public void processRecord(CSVParser parser, VersionFile versionFile, IRequestProperties requestProperties, String fileEncoding, DatasetDescriptor datasetDescriptor) throws BusinessException {
        long start = System.currentTimeMillis();
        String message = SNOTRecorder.getSnotMessage("STARTING_RECORD");
        this.LOGGER.debug(message);
        ErrorsReport erreurs = new ErrorsReport();
        try {
            List<RealNode> dbRealNodes = buildVariablesHeaderAndSkipHeader(parser, (RequestPropertiesFluxMeteo) requestProperties, datasetDescriptor, versionFile.getDataset().getRealNode());
            List<Line> mesures = this.traiterLesDonnees(parser, (RequestPropertiesFluxMeteo) requestProperties, dbRealNodes, datasetDescriptor, erreurs);
            List<Line> ligneEnErreur = this.mettreEnBase(mesures, versionFile);
            this.faireRapport(ligneEnErreur, erreurs);
        } catch (PersistenceException | IOException error) {
            throw new BusinessException(error);
        }
        long duree = (System.currentTimeMillis() - start) / 1_000;
        this.LOGGER.info("duree processRecord : {} secondes", duree);
    }

    /**
     *
     * @param ligneEnErreur
     * @param erreurs
     * @throws PersistenceException
     */
    protected void faireRapport(List<Line> ligneEnErreur, ErrorsReport erreurs) throws PersistenceException {
        if (ligneEnErreur == null || ligneEnErreur.isEmpty()) {
            return;
        }
        for (Line ligne : ligneEnErreur) {
            String message = SNOTRecorder.getSnotMessage("ERREUR_INSERTION_MESURE");
            String erreurMessage = String.format(message, ligne.getOriginalLineNumber().intValue());
            erreurs.addErrorMessage(erreurMessage);
        }
        if (erreurs.hasErrors()) {
            this.LOGGER.debug(erreurs.getErrorsMessages());
            throw new PersistenceException(erreurs.getErrorsMessages());
        } else {
            String message = SNOTRecorder.getSnotMessage("ARCHIVED_SUCCESS");
            this.LOGGER.debug(message);
        }

    }

    /**
     *
     * @param mesures
     * @param versionFile
     * @return
     */
    protected List<Line> mettreEnBase(List<Line> mesures, VersionFile versionFile) {
        List<Line> ligneEnErreur = new ArrayList<>();
        for (Line ligne : mesures) {
            try {
                this.rangerEnBase(ligne, versionFile);
            } catch (PersistenceException e) {
                ligneEnErreur.add(ligne);
            }
        }
        return ligneEnErreur;
    }

    /**
     *
     * @param ligne
     * @param versionFile
     * @throws PersistenceException
     */
    protected abstract void rangerEnBase(Line ligne, VersionFile versionFile) throws PersistenceException;

    /**
     *
     * @param parser
     * @param requestPropertiesFluxMeteo
     * @param dbRealNodes
     * @param datasetDescriptor
     * @param errorsReport
     * @return
     * @throws IOException
     */
    protected List<Line> traiterLesDonnees(CSVParser parser, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, List<RealNode> dbRealNodes, DatasetDescriptor datasetDescriptor, ErrorsReport errorsReport) throws IOException {
        List<Line> mesures = new LinkedList();
        long noLigne = 1;
        String[] values = null;
        while ((values = parser.getLine()) != null) {
            Line uneLigne = this.traiterDate(values, noLigne, datasetDescriptor, errorsReport);
            this.traiterLesValeurs(uneLigne, values, requestPropertiesFluxMeteo, dbRealNodes);
            mesures.add(uneLigne);
            noLigne++;
        }
        return mesures;
    }

    /**
     * <p>
     * recherche dans les valeurs la colonne date et la colonne time, ainsi que
     * le numéro de la ligne pour créer l'objet line</p>
     * <p>
     * renvoie les erreurs DATE_NON_VALIDE_FOR_FORMAT et
     * TIME_NON_VALIDE_WITH_FORMAT
     *
     * @param values
     * @param noLigne
     * @param datasetDescriptor
     * @param errorsReport
     * @return une nouvelle ligne
     */
    protected Line traiterDate(String[] values, long noLigne, DatasetDescriptor datasetDescriptor, ErrorsReport errorsReport) {
        LocalDate date = this.getDate(datasetDescriptor, values, noLigne, errorsReport);
        final LocalTime dateTime = this.getTime(datasetDescriptor, values, noLigne, errorsReport);
        Line ligne = new Line(date, dateTime);
        ligne.setOriginalLineNumber(noLigne);
        return ligne;
    }

    /**
     *
     * @param uneLigne
     * @param values
     * @param requestPropertiesFluxMeteo
     * @param dbRealNodes
     */
    protected void traiterLesValeurs(Line uneLigne, String[] values, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, List<RealNode> dbRealNodes) {
        ExpertColonne expertColonne = requestPropertiesFluxMeteo.getExpertColonne();
        int actualVariable = 0;
        for (int i = 0; i < values.length; i++) {
            Column colonne = expertColonne.getColonnesDansOrdreFichier().get(i);
            final String valueType = colonne.getValueType();
            final String name = colonne.getName();
            final String flagType = colonne.getFlagType();
            final String value = values[i];
            boolean jumped = Constantes.PROPERTY_CST_DATE_TYPE.equals(valueType) || Constantes.PROPERTY_CST_TIME_TYPE.equals(valueType);
            jumped = jumped || Constantes.CST_STAR.equals(name) || Constantes.PROPERTY_CST_QUALITY_CLASS_TYPE.equals(flagType);
            if (jumped || Strings.isNullOrEmpty(value.trim())) {
                continue;
            }
            VariableValue variableValue = this.preparerValeur(colonne, i, values, actualVariable, requestPropertiesFluxMeteo, dbRealNodes);
            actualVariable++;
            uneLigne.getVariablesValues().add(variableValue);
        }

    }

    /**
     *
     * @param colonne
     * @param i
     * @param values
     * @param noVariable
     * @param requestPropertiesFluxMeteo
     * @param dbRealNodes
     * @return
     */
    protected VariableValue preparerValeur(Column colonne, int i, String[] values, int noVariable, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, List<RealNode> dbRealNodes) {
        ExpertColonne expertColonne = requestPropertiesFluxMeteo.getExpertColonne();
        RealNode dbrealNode = dbRealNodes.get(noVariable);
        String uneValeur = values[i].trim().replaceAll(Constantes.CST_COMMA, Constantes.CST_DOT);
        Float valeur = Float.parseFloat(uneValeur);
        int noQuality = expertColonne.getNoQualityColumn(colonne.getName());
        QualityClass valeurQualite = null;
        if (noQuality != -1) {
            uneValeur = values[noQuality].trim().replaceAll(Constantes.CST_COMMA, Constantes.CST_DOT);
            try {
                Integer qualite = Integer.parseInt(uneValeur);
                valeurQualite = QualityClass.getInstanceByNumber(qualite);
            } catch (NumberFormatException e) {
                LOGGER.info("value not an integer ", e);
            }
        }
        VariableValue variableValue = new VariableValue(dbrealNode, valeur, valeurQualite);
        return variableValue;
    }

    /**
     *
     * @param parser
     * @param requestPropertiesFluxMeteo
     * @param datasetDescriptor
     * @param datatypeRealNode
     * @return
     * @throws PersistenceException
     * @throws IOException
     */
    protected List<RealNode> buildVariablesHeaderAndSkipHeader(CSVParser parser, RequestPropertiesFluxMeteo requestPropertiesFluxMeteo, DatasetDescriptor datasetDescriptor, RealNode datatypeRealNode) throws PersistenceException, IOException {
        List<RealNode> dbRealNodes = new ArrayList();
        ExpertColonne expertColonne = requestPropertiesFluxMeteo.getExpertColonne();
        Map<String, RealNode> realNodeFromDatasetNode = datatypeVariableUniteSnotDAO.getRealNodesVariables(datatypeRealNode).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getCode(), k -> k.getValue()));
        for (Column colonne : expertColonne.getColonnesDansOrdreFichier()) {
            if (!Constantes.PROPERTY_CST_DATE_TYPE.equals(colonne.getName()) && !Constantes.PROPERTY_CST_TIME_TYPE.equals(colonne.getName())) {
                if (colonne.isVariable() && !Constantes.PROPERTY_CST_QUALITY_CLASS_TYPE.equals(colonne.getFlagType())) {
                    dbRealNodes.add(realNodeFromDatasetNode.get(colonne.getName()));
                }
            }
        }
        for (int i = 0; i < ((DatasetDescriptorFluxMeteo) datasetDescriptor).getEnTete(); i++) {
            parser.getLine();
        }
        return dbRealNodes;
    }

}
