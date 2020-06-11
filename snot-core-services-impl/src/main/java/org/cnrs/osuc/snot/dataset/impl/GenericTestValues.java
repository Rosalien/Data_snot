/**
 *
 */
package org.cnrs.osuc.snot.dataset.impl;

import com.Ostermiller.util.CSVParser;
import com.google.common.base.Strings;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.dataset.IRequestPropertiesIntervalValue;
import org.cnrs.osuc.snot.dataset.ITestDuplicates;
import org.cnrs.osuc.snot.dataset.ITestValues;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.exceptions.EmptyFileException;
import org.inra.ecoinfo.utils.ApplicationContextHolder;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadValueTypeException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.NullValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;

/**
 * The Class GenericTestValues.
 * <p>
 * ana generic implementation of {@link ITestValues}
 *
 * @author Tcherniatinsky Philippe
 */
public class GenericTestValues implements ITestValues {

    /**
     * The Constant serialVersionUID @link(long).
     */
    static final long serialVersionUID = 1L;

    /**
     * The LOGGER.
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
    /**
     * The datatype unite variable Snotdao
     *
     * @link(IDatatypeUniteVariableSnotDAO).
     */
    IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO;

    /**
     * Instantiates a new generic test values.
     */
    public GenericTestValues() {
        super();
    }

    /**
     *
     * @param requestProperties
     * @return the testDuplicate from its name in requestProperties
     */
    public ITestDuplicates getTestDuplicates(IRequestProperties requestProperties) {
        return (ITestDuplicates) ApplicationContextHolder.getContext().getBean(requestProperties.testDuplicateName());
    }

    /**
     *
     * @param datatypeVariableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteSnotDAO) {
        this.datatypeVariableUniteSnotDAO = datatypeVariableUniteSnotDAO;
    }

    /**
     * <p>
     * for each line, test if line is duplicated</p>
     * <p>
     * for each value in line check if the value is conform to dataset
     * description column</p>
     * <p>
     * then test dates with requestProperties.testDates</p>
     * <p>
     * put error in badsFormatsReport</p>
     *
     * @param startline
     * <long> the startline
     * @param parser
     * @link(CSVParser) the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestProperties) the session properties
     * @param encoding
     * @link(String) the encoding
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param datatypeName
     * @link(String) the datatype name
     * @throws BusinessException the business exception
     * @link(CSVParser) the parser
     * @link(VersionFile) the version file
     * @link(IRequestProperties) the session properties
     * @link(String) the encoding
     * @link(BadsFormatsReport) the bads formats report
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(String) the datatype name
     * @see org.inra.ecoinfo.Snot.dataset.ITestValues#testValues(long, com.Ostermiller.util.CSVParser, org.inra.ecoinfo.dataset.versioning.entity.VersionFile, org.inra.ecoinfo.Snot.dataset.IRequestProperties, java.lang.String,
     *      org.inra.ecoinfo.utils.exceptions.BadsFormatsReport)
     */
    @Override
    public void testValues(final long startline, final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String encoding, final BadsFormatsReport badsFormatsReport, final DatasetDescriptor datasetDescriptor, final String datatypeName) throws BusinessException {
        long lineNumber = startline;
        final long headerCountLine = lineNumber;
        final Map<String, DatatypeVariableUniteSnot> variablesTypeDonnees = this.datatypeVariableUniteSnotDAO.getAllVariableTypeDonneesByDataType(requestProperties.getSite().getCode(), datatypeName);
        String[] values;
        ITestDuplicates testDuplicates = this.getTestDuplicates(requestProperties);
        final int lastColumnIndex = requestProperties.getColumnNames().length - 1;
        try {
            while ((values = parser.getLine()) != null) {
                lineNumber++;

                if (testDuplicates != null) {
                    testDuplicates.addLine(values, lineNumber);
                }
                for (int index = 0; index < values.length; index++) {
                    String value = values[index];
                    if (index > lastColumnIndex) {
                        break;
                    }

                    value = values[index];
                    Column column = requestProperties.getColumn(index);
                    if (column == null && datasetDescriptor.getColumns().size() > index) {
                        column = datasetDescriptor.getColumns().get(index);
                    }

                    this.checkValue(badsFormatsReport, lineNumber, index, value, column, values, variablesTypeDonnees, datasetDescriptor, requestProperties);
                }
                this.verifieLine(lineNumber, versionFile, requestProperties, badsFormatsReport, datasetDescriptor, datatypeName, values);
            }
        } catch (final IOException e) {
            this.LOGGER.debug(e.getMessage());
            badsFormatsReport.addException(e);
        }
        requestProperties.testDates(badsFormatsReport);
        if (lineNumber == headerCountLine) {
            badsFormatsReport.addException(new EmptyFileException(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_NO_DATA)));
        }
        if (testDuplicates != null && testDuplicates.hasError()) {
            testDuplicates.addErrors(badsFormatsReport);
        }
    }

    /**
     * Check date type value.
     * <p>
     * use format in UTC from datasetDescriptor column format else
     * dd/MM/yyyy</p>
     * <p>
     * error PROPERTY_MSG_INVALID_DATE</p>
     *
     * @param values
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index int the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @return the date {@link String[]} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types donnees
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot
     */
    protected LocalDate checkDateTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        String dateFormat = Strings.isNullOrEmpty(column.getFormatType()) ? DateUtil.DD_MM_YYYY : column.getFormatType();
        try {
            final LocalDateTime date;
            if (DateUtil.MM_YYYY.equals(dateFormat)) {
                date = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, "01/".concat(value));
            } else {
                date = DateUtil.readLocalDateTimeFromText(dateFormat, value);
            }
            if (!value.equals(DateUtil.getUTCDateTextFromLocalDateTime(date, dateFormat))) {
                badsFormatsReport.addException(new NullValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_DATE), value, lineNumber, index + 1, column.getName(), dateFormat)));
                return null;
            }
            return date.toLocalDate();
        } catch (final DateTimeException e) {
            badsFormatsReport.addException(new NullValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_DATE), value, lineNumber, index + 1, column.getName(), dateFormat)));
            return null;
        }
    }

    /**
     * Check float type value.
     * <p>
     * error PROPERTY_CST_INVALID_BAD_MEASURE</p>
     *
     * @param values
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index int the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @return the float {@link String[]} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types donnees
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot
     */
    protected Float checkFloatTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        try {
            Float floatValue = Float.parseFloat(value);
            this.checkIntervalValue(requestProperties, datasetDescriptor, badsFormatsReport, lineNumber, index, column, variablesTypesDonnees, floatValue);
            return floatValue;
        } catch (final NumberFormatException e) {
            badsFormatsReport.addException(new BadValueTypeException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_FLOAT_VALUE), lineNumber, index + 1, column.getName(), value)));
            return Float.parseFloat(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);
        }
    }

    /**
     * Check float type value.
     * <p>
     * error PROPERTY_MSG_INVALID_FLOAT_VALUE</p>
     *
     * @param values
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index int the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @return the float {@link String[]} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types donnees
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot
     */
    protected Double checkDoubleTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        try {
            Double doubleValue = Double.parseDouble(value);
            this.checkIntervalValue(requestProperties, datasetDescriptor, badsFormatsReport, lineNumber, index, column, variablesTypesDonnees, doubleValue.floatValue());
            return doubleValue;
        } catch (final NumberFormatException e) {
            badsFormatsReport.addException(new BadValueTypeException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_DOUBLE_VALUE), lineNumber, index + 1, column.getName(), value)));
            return Double.parseDouble(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);
        }
    }

    /**
     * Check integer type value.
     * <p>
     * error PROPERTY_MSG_INVALID_INT_VALUE</p>
     *
     * @param values
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @return the int {@link String[]} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types donnees
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot
     */
    protected Integer checkIntegerTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        try {
            final Integer intValue = Integer.parseInt(value);
            this.checkIntervalValue(requestProperties, datasetDescriptor, badsFormatsReport, lineNumber, index, column, variablesTypesDonnees, (float) intValue);
            return intValue;
        } catch (final NumberFormatException e) {
            badsFormatsReport.addException(new BadValueTypeException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_INT_VALUE), lineNumber, index + 1, column.getName(), value)));
            return Integer.parseInt(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);
        }
    }

    /**
     * Check interval value.
     * <p>
     * check if value is in interval value</p>
     * <p>
     * use interval from min and max line and put error
     * PROPERTY_MSG_INCORRECT_VALUE_MIN / PROPERTY_MSG_INCORRECT_VALUE_MAX in
     * badsFormatsReport</p>
     * <p>
     * if these values doesn't exist use min and max values from
     * datatypevariableunites and put error
     * PROPERTY_MSG_INCORRECT_VALUE_MIN_DTUV /
     * PROPERTY_MSG_INCORRECT_VALUE_MAX_DTUV in badsFormatsReport</p>
     *
     * @param requestProperties
     * @param badsFormatsReport
     * @param datasetDescriptor
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber
     * <long> the line number
     * @param index
     * @link(int) the index
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param floatValue
     * @link(Float) the float value
     * @link(BadsFormatsReport) the bads formats report
     * @link(int) the index
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(Float) the float value
     */
    protected void checkIntervalValue(final IRequestProperties requestProperties, final DatasetDescriptor datasetDescriptor, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final Float floatValue) {
        boolean badIntervalValue = Constantes.PROPERTY_CST_VARIABLE_TYPE.equals(column.getFlagType());
        if (!badIntervalValue) {
            return;
        }
        int undefinedColumn = datasetDescriptor.getUndefinedColumn();
        IRequestPropertiesIntervalValue requestPropertiesIntervalValue = (requestProperties instanceof IRequestPropertiesIntervalValue) ? (IRequestPropertiesIntervalValue) requestProperties : null;
        if (requestPropertiesIntervalValue != null) {
            final String[] valeursMin = requestPropertiesIntervalValue.getValeursMin();
            final String[] valeursMax = requestPropertiesIntervalValue.getValeursMax();
            final String[] valeurMinInFile = requestPropertiesIntervalValue.getValeurMinInFile();
            final String[] valeurMaxInFile = requestPropertiesIntervalValue.getValeurMaxInFile();
            final Float badMesure = new Float(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);
            if (valeursMin[index - undefinedColumn] != null && !floatValue.equals(badMesure) && floatValue < Float.parseFloat(valeursMin[index - undefinedColumn])) {
                String message = "";
                if (valeurMinInFile[index] != null && !valeurMinInFile[index + 1].isEmpty()) {
                    message = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INCORRECT_VALUE_MIN), lineNumber, index + 1, column.getName(), floatValue, new Double(valeursMin[index - undefinedColumn]));
                } else {
                    message = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INCORRECT_VALUE_MIN_DTUV), lineNumber, index + 1, column.getName(), floatValue, new Double(valeursMin[index - undefinedColumn]));
                }
                badsFormatsReport.addException(new BadExpectedValueException(message));
            }

            if (valeursMax[index - undefinedColumn] != null && !floatValue.equals(badMesure) && floatValue > Float.parseFloat(valeursMax[index - undefinedColumn])) {
                String message = "";
                if (valeurMaxInFile[index] != null && !valeurMaxInFile[index + 1].isEmpty()) {
                    message = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INCORRECT_VALUE_MAX), lineNumber, index + 1, column.getName(), floatValue, new Double(valeursMax[index - undefinedColumn]));
                } else {
                    message = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INCORRECT_VALUE_MAX_DTUV), lineNumber, index + 1, column.getName(), floatValue, new Double(valeursMax[index - undefinedColumn]));
                }
                badsFormatsReport.addException(new BadExpectedValueException(message));
            }
        } else {
            badIntervalValue = badIntervalValue && variablesTypesDonnees.containsKey(column.getName());
            badIntervalValue = badIntervalValue && floatValue != Float.parseFloat(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);

            Float valeurMin;
            Float valeurMax;
            if (variablesTypesDonnees.get(column.getName()) != null) {
                valeurMin = variablesTypesDonnees.get(column.getName()).getMin();
                final boolean isBadMinValue = valeurMin != null && floatValue < valeurMin;
                valeurMax = variablesTypesDonnees.get(column.getName()).getMax();
                final boolean isBadMaxValue = valeurMax != null && floatValue > valeurMax;
                final boolean isOutOfRangeValue = isBadMinValue || isBadMaxValue;
                badIntervalValue = badIntervalValue && isOutOfRangeValue;
            } else {
                return;
            }
            if (badIntervalValue) {
                badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_INTERVAL_FLOAT_VALUE), lineNumber, index + 1, column.getName(), floatValue, valeurMin,
                        valeurMax)));
            }
        }
    }

    /**
     * Check other type value.
     * <p>
     * can't be overriden to add other type value checks</p>
     *
     * @param values
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot {@link String []} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types
     *                           donnees
     */
    protected void checkOtherTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {

    }

    /**
     * Check time type value.
     * <p>
     * use format in UTC from datasetDescriptor column format else HH:mm:ss</p>
     * <p>
     * add seconds if not exists</p>
     * <p>
     * add error PROPERTY_MSG_INVALID_TIME in badsFormatsReport</p>
     *
     * @param values
     * @param value
     * @link(String[]) the values
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index the column index
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset descriptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @return the date {@link String[]} the values {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link DatatypeUniteVariableSnot} the variables types donnees
     * @link(String[]) the values
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot
     */
    protected LocalTime checkTimeTypeValue(final String[] values, final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        String localValue = value;
        if (value.matches("[0-9]:.*")) {
            localValue = String.format("0%s", localValue);
        }
        String dateFormat = Strings.isNullOrEmpty(column.getFormatType()) ? DateUtil.HH_MM : column.getFormatType();
        try {
            LocalTime time;
            time = DateUtil.readLocalTimeFromText(dateFormat, localValue);
            if (!localValue.equals(DateUtil.getUTCDateTextFromLocalDateTime(time, dateFormat))) {
                badsFormatsReport.addException(new NullValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_TIME), localValue, lineNumber, index + 1, column.getName(), dateFormat)));
            }
            return time;
        } catch (final DateTimeException e) {
            badsFormatsReport.addException(new NullValueException(String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_INVALID_TIME), localValue, lineNumber, index + 1, column.getName(), dateFormat)));
            return null;
        }
    }

    /**
     * Check value.
     * <p>
     * you can add custum checks by overriden the checkOtherTypeValue method</p>
     * <p>
     * <ul>
     * <li>- La valeur est nettoyée ;</li>
     * <li>- non nullable est vide → PROPERTY_MSG_MISSING_VALUE</li>
     * <li>- PROPERTY_CST_FLOAT_TYPE mais pas float→
     * PROPERTY_MSG_INVALID_FLOAT_VALUE</li>
     * <li>- PROPERTY_CST_ECART_TYPE mais pas float→
     * PROPERTY_MSG_INVALID_FLOAT_VALUE</li>
     * <li>- PROPERTY_CST_NBRE_MESURES mais pas float→
     * PROPERTY_MSG_INVALID_FLOAT_VALUE</li>
     * <li>- PROPERTY_CST_QUALITY_CODE mais pas float→
     * PROPERTY_MSG_INVALID_FLOAT_VALUE</li>
     * <li>- PROPERTY_CST_INTEGER_TYPE mais pas integer→
     * PROPERTY_MSG_INVALID_INT_VALUE</li>
     * <li>- PROPERTY_CST_NO_CHAMBRE mais pas integer→
     * PROPERTY_MSG_INVALID_INT_VALUE</li>
     * <li>- PROPERTY_CST_DUREE_MESURE mais pas integer→
     * PROPERTY_MSG_INVALID_INT_VALUE</li>
     * <li>- PROPERTY_CST_DATE_TYPE mais pas date au format
     * column.getFormatType() ou par default dd/MM/yyyy →
     * PROPERTY_MSG_INVALID_DATE</li>
     * <li>- PROPERTY_CST_TIME_TYPE mais pas heure au format
     * column.getFormatType() ou par default hh:mm →
     * PROPERTY_MSG_INVALID_DATE</li>
     * <li>- sinon si on a un autre type checkOtherTypeValue(values,
     * badsFormatsReport, lineNumber, index, cleanValue, column,
     * variablesTypesDonnees, datasetDescriptor, requestProperties) à
     * surcharger.</li>
     * </ul></p>
     *
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param lineNumber int the line number
     * @param index int the column index
     * @param value
     * @link(String) the value
     * @param column
     * @link(Column) the column
     * @param values
     * @link(String[]) the values
     * @param variablesTypesDonnees
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @param datasetDescriptor
     * @link(DatasetDescriptor) the dataset describadsptor
     * @param requestProperties
     * @link(IRequestProperties) the session properties Snot
     * @link(BadsFormatsReport) the bads formats report
     * @link(String) the value
     * @link(Column) the column
     * @link(String[]) the values
     * @link(Map<String,DatatypeUniteVariableSnot>) the variables types donnees
     * @link(DatasetDescriptor) the dataset descriptor
     * @link(IRequestProperties) the session properties Snot {@link BadsFormatsReport} the bads formats report {@link String} the value {@link Column} the column {@link String[]} the values {@link DatatypeUniteVariableSnot} the variables types
     *                           donnees
     */
    protected void checkValue(final BadsFormatsReport badsFormatsReport, final long lineNumber, final int index, final String value, final Column column, final String[] values, final Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, final DatasetDescriptor datasetDescriptor, final IRequestProperties requestProperties) {
        if (column == null) {
            return;
        }
        String cleanValue = value;
        if (!column.isNullable() && StringUtils.isEmpty(cleanValue)) {
            final String message = String.format(SNOTRecorder.getSnotMessage(SNOTRecorder.PROPERTY_MSG_MISSING_VALUE), lineNumber, index + 1, column.getName());
            final NullValueException badFormatException = new NullValueException(message);
            badsFormatsReport.addException(badFormatException);
        }
        final String valueType = column.getValueType();
        if (Constantes.PROPERTY_CST_FLOAT_TYPE.equals(valueType) && !Strings.isNullOrEmpty(value)) {
            this.checkFloatTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (Constantes.PROPERTY_CST_INTEGER_TYPE.equals(valueType) && !Strings.isNullOrEmpty(value)) {
            this.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (Constantes.PROPERTY_CST_DOUBLE_TYPE.equals(valueType) && !Strings.isNullOrEmpty(value)) {
            this.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.isFlag() && Constantes.PROPERTY_CST_DATE_TYPE.equals(column.getFlagType())) {
            this.checkDateTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.isFlag() && Constantes.PROPERTY_CST_TIME_TYPE.equals(column.getFlagType())) {
            this.checkTimeTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.getFlagType().equals(Constantes.PROPERTY_CST_NO_CHAMBRE) && !Strings.isNullOrEmpty(value)) {
            this.checkNumeroChambreTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.getFlagType().equals(Constantes.PROPERTY_CST_DUREE_MESURE) && !Strings.isNullOrEmpty(value)) {
            this.checkDureeMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.getFlagType().equals(Constantes.PROPERTY_CST_ECART_TYPE) && !Strings.isNullOrEmpty(value)) {
            this.checkEcartTypeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.getFlagType().equals(Constantes.PROPERTY_CST_NBRE_MESURES) && !Strings.isNullOrEmpty(value)) {
            this.checkNbMesureTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else if (column.getFlagType().equals(Constantes.PROPERTY_CST_QUALITY_CODE) && !Strings.isNullOrEmpty(value)) {
            this.checkQualityCodeTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        } else {
            this.checkOtherTypeValue(values, badsFormatsReport, lineNumber, index, cleanValue, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
        }

    }

    /**
     *
     * @param versionFile
     * @return An IntervalDate from file name
     */
    protected IntervalDate getIntervalDateFromVersion(VersionFile versionFile) {
        if (versionFile == null) {
            return null;
        }
        try {
            IntervalDate intervalDate = new IntervalDate(versionFile.getDataset().getDateDebutPeriode(), versionFile.getDataset().getDateFinPeriode(), SNOTRecorder.DD_MM_YYYY_HHMMSS_DOUBLONS_FILE);
            return intervalDate;
        } catch (BadExpectedValueException e) {
            this.LOGGER.info("pas de version", e);
            return null;
        }
    }

    /**
     * <p>
     * can be overriden to inform than an other version with concummitent dates
     * is already to be published</p>
     *
     * @param startline
     * @param parser
     * @param versionFile
     * @param requestProperties
     * @param encoding
     * @param badsFormatsReport
     * @param datasetDescriptor
     * @param datatypeName
     * @throws BusinessException
     */
    protected void testDuplicatesLineWithpublishedVersion(final long startline, final CSVParser parser, final VersionFile versionFile, final IRequestProperties requestProperties, final String encoding, final BadsFormatsReport badsFormatsReport, final DatasetDescriptor datasetDescriptor, final String datatypeName) throws BusinessException {
    }

    /**
     * <p>
     * if line have time column frequence = Constantes.FREQUENCE_NAME[0] ||
     * frequence = Constantes.FREQUENCE_NAME[3]) use the
     * sessionPropertie.addDate method with date+time timestamp</p>
     *
     * @param lineNumber
     * @param versionFile
     * @param requestProperties
     * @param badsFormatsReport
     * @param datasetDescriptor
     * @param datatypeName
     * @param values
     */
    protected void verifieLine(long lineNumber, VersionFile versionFile, IRequestProperties requestProperties, BadsFormatsReport badsFormatsReport, DatasetDescriptor datasetDescriptor, String datatypeName, String[] values) {
        BadsFormatsReport localReport = new BadsFormatsReport(datatypeName);
        LocalDate date = this.checkDateTypeValue(values, localReport, lineNumber, 0, values[0], datasetDescriptor.getColumns().get(0), requestProperties.getVariableTypeDonnees(), datasetDescriptor, requestProperties);
        LocalDateTime datetime = date.atStartOfDay();

        if (Constantes.FREQUENCE_NAME[0].equals(requestProperties.getFrequence()) || Constantes.FREQUENCE_NAME[3].equals(requestProperties.getFrequence())) {
            LocalTime time = this.checkTimeTypeValue(values, localReport, lineNumber, 1, values[1], datasetDescriptor.getColumns().get(1), requestProperties.getVariableTypeDonnees(), datasetDescriptor, requestProperties);
            if (date != null && time != null) {
                datetime = date.atTime(time);
            }
        }
        if (localReport.hasErrors()) {
            return;
        }
        try {
            requestProperties.addDate(datetime, lineNumber);
        } catch (BadExpectedValueException ex) {
            badsFormatsReport.addException(ex);
        }

    }

    /**
     *
     * @param values
     * @param badsFormatsReport
     * @param lineNumber
     * @param index
     * @param value
     * @param column
     * @param variablesTypesDonnees
     * @param datasetDescriptor
     * @param requestProperties
     */
    protected void checkNumeroChambreTypeValue(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber, int index, String value, Column column, Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) {
        this.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }

    /**
     *
     * @param values
     * @param badsFormatsReport
     * @param lineNumber
     * @param index
     * @param value
     * @param column
     * @param variablesTypesDonnees
     * @param datasetDescriptor
     * @param requestProperties
     */
    protected void checkDureeMesureTypeValue(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber, int index, String value, Column column, Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) {
        this.checkIntegerTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }

    /**
     *
     * @param values
     * @param badsFormatsReport
     * @param lineNumber
     * @param index
     * @param value
     * @param column
     * @param variablesTypesDonnees
     * @param datasetDescriptor
     * @param requestProperties
     */
    protected void checkEcartTypeTypeValue(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber, int index, String value, Column column, Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) {
        this.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }

    /**
     *
     * @param values
     * @param badsFormatsReport
     * @param lineNumber
     * @param index
     * @param value
     * @param column
     * @param variablesTypesDonnees
     * @param datasetDescriptor
     * @param requestProperties
     */
    protected void checkNbMesureTypeValue(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber, int index, String value, Column column, Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) {
        this.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }

    /**
     *
     * @param values
     * @param badsFormatsReport
     * @param lineNumber
     * @param index
     * @param value
     * @param column
     * @param variablesTypesDonnees
     * @param datasetDescriptor
     * @param requestProperties
     */
    protected void checkQualityCodeTypeValue(String[] values, BadsFormatsReport badsFormatsReport, long lineNumber, int index, String value, Column column, Map<String, DatatypeVariableUniteSnot> variablesTypesDonnees, DatasetDescriptor datasetDescriptor, IRequestProperties requestProperties) {
        this.checkDoubleTypeValue(values, badsFormatsReport, lineNumber, index, value, column, variablesTypesDonnees, datasetDescriptor, requestProperties);
    }
}
