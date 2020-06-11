package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.CSVParser;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Transient;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.ErrorsReport;
import org.cnrs.osuc.snot.dataset.IProcessRecord;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.traitement.ITraitementDAO;
import org.cnrs.osuc.snot.refdata.variable.IVariableSnotDAO;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractProcessRecord.
 * <p>
 * generic abstract implementation of {
 *
 * @see IProcessRecord}
 *
 */
public class AbstractProcessRecordSnot implements IProcessRecord, Serializable {

    /**
     *
     */
    protected static final String BUNDLE_PATH = "org.cnrs.osuc.snot.dataset.impl.messages";

    /**
     *
     */
    protected static final String PROPERTY_MSG_UNFOUND_VALEUR_QUALITATIVE = "PROPERTY_MSG_UNFOUND_VALEUR_QUALITATIVE";

    /**
     *
     */
    protected static final String BAD_DATASET_DESCRIPTOR = "Bad dataset-descriptor";
    /**
     * The Constant serialVersionUID.
     */
    static final long serialVersionUID = 1L;

    /**
     * The LOGGER.
     */
    @Transient
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The localization manager.
     */
    @Transient
    protected ILocalizationManager localizationManager;

    /**
     * The variable dao.
     */
    @Transient
    protected IVariableSnotDAO variableDAO;

    /**
     * The version file dao.
     */
    @Transient
    protected IVersionFileDAO versionFileDAO;

    /**
     * The traitement dao.
     */
    @Transient
    protected ITraitementDAO traitementDAO;

    /**
     * The datatype unite variable snotdao
     * {@link IDatatypeUniteVariableSnotDAO} .
     */
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

    /**
     * Instantiates a new abstract process record.
     */
    protected AbstractProcessRecordSnot() {
        super();
    }

    @Override
    public void processRecord(CSVParser parser, VersionFile versionFile, IRequestProperties requestProperties, String fileEncoding, DatasetDescriptor datasetDescriptorACBB) throws BusinessException {
        // nothing to do
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
     * @param variableDAO
     */
    public void setVariableDAO(IVariableSnotDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    /**
     *
     * @param versionFileDAO
     */
    public void setVersionFileDAO(IVersionFileDAO versionFileDAO) {
        this.versionFileDAO = versionFileDAO;
    }

    /**
     *
     * @param traitementDAO
     */
    public void setTraitementDAO(ITraitementDAO traitementDAO) {
        this.traitementDAO = traitementDAO;
    }

    /**
     *
     * @param datatypeVariableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
    }

    /**
     * Builds the variables header and skip header.
     * <p>
     * add the variable from variables culumns name in db variable</p>
     * <p>
     * the parser is then ready to read the first line of data</p>
     *
     * @param parser
     * @param datatypeRealNode
     * @link{CSVParser the parser
     * @param datasetDescriptor
     * @link{DatasetDescriptor the dataset descriptor
     * @return the list of variables
     * @throws PersistenceException the persistence exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected List<RealNode> buildVariablesHeaderAndSkipHeader(final CSVParser parser, final DatasetDescriptor datasetDescriptor, RealNode datatypeRealNode) throws PersistenceException, IOException {
        Map<String, RealNode> nodes = datatypeVariableUniteSnotDAO.getRealNodesVariables(datatypeRealNode).entrySet().stream()
                .collect(Collectors.toMap(k -> k.getKey().getCode(), k -> k.getValue()));
        List<RealNode> dbRealNodes = new LinkedList();
        for (final Column colonne : datasetDescriptor.getColumns()) {
            final String flagType = colonne.getFlagType();
            if (Constantes.PROPERTY_CST_DATE_TYPE.equals(flagType) || Constantes.PROPERTY_CST_TIME_TYPE.equals(flagType) || Constantes.PROPERTY_CST_QUALITY_CLASS_TYPE.equals(flagType)) {
                continue;
            }
            final String columnName = colonne.getName();
            if (colonne.isFlag()
                    && (flagType.equals(Constantes.PROPERTY_CST_VARIABLE_TYPE) || flagType.equals(Constantes.PROPERTY_CST_LIST_VALEURS_QUALITATIVES_TYPE) || flagType.equals(Constantes.PROPERTY_CST_VALEUR_QUALITATIVE_TYPE))
                    && nodes.containsKey(columnName)) {
                dbRealNodes.add(nodes.get(columnName));

            }
        }
        return dbRealNodes;
    }

    /**
     *
     * @param datasetDescriptor
     * @param values
     * @param linenumber
     * @param errorsReport
     * @return
     */
    protected LocalDate getDate(DatasetDescriptor datasetDescriptor, String[] values, long linenumber, ErrorsReport errorsReport) {
        return datasetDescriptor.getColumns().stream()
                .filter(column -> Constantes.PROPERTY_CST_DATE_TYPE.equals(column.getValueType()))
                .map((column) -> {
                    int i = datasetDescriptor.getColumns().indexOf(column);
                    try {
                        if (DateUtil.MM_YYYY.equals(column.getFormatType())) {
                            return DateUtil.readLocalDateFromText(DateUtil.DD_MM_YYYY, "01/".concat(values[i]));
                        } else {
                            return DateUtil.readLocalDateFromText(column.getFormatType(), values[i]);
                        }
                    } catch (DateTimeParseException ex) {
                        String message = SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_DATE);
                        String messageErreur = String.format(message, values[i], linenumber, i + 1, Constantes.PROPERTY_CST_DOUBLE_TYPE, column.getFormatType());
                        errorsReport.addErrorMessage(messageErreur);
                        LOGGER.debug(message, ex);
                        return null;
                    }
                })
                .filter(e -> e != null)
                .findFirst()
                .orElse(null);
    }

    /**
     *
     * @param datasetDescriptor
     * @param values
     * @param linenumber
     * @param errorsReport
     * @return
     */
    protected LocalTime getTime(DatasetDescriptor datasetDescriptor, String[] values, long linenumber, ErrorsReport errorsReport) {
        return datasetDescriptor.getColumns().stream()
                .filter(column -> Constantes.PROPERTY_CST_TIME_TYPE.equals(column.getValueType()))
                .map((column) -> {
                    int i = datasetDescriptor.getColumns().indexOf(column);
                    String dateFormat = column.getFormatType().replaceAll("-", "/");
                    try {
                        String valueTime = values[i];
                        if (valueTime.matches("[0-9]:.*")) {
                            valueTime = String.format("0%s", valueTime);
                        }
                        return DateUtil.readLocalTimeFromText(dateFormat, valueTime);
                    } catch (DateTimeParseException ex) {
                        String message = SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_TIME);
                        String messageErreur = String.format(message, values[i], linenumber, i + 1, Constantes.PROPERTY_CST_TIME_TYPE, dateFormat);
                        errorsReport.addErrorMessage(messageErreur);
                        LOGGER.debug(message, ex);
                        return null;
                    }
                })
                .filter(date -> date != null)
                .findFirst()
                .orElse(null);

    }
}
