/**
 *
 */
package org.cnrs.osuc.snot.dataset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;


/**
 * @author sophie
 * 
 */
public class UtilVerifRecorder {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.messages";
    /*
     * public Column GetLstColumnInDatasetdescriptor() {
     *
     * }
     */

    /**
     * @param datasetDescriptor
     * @return Renvoie la liste des noms de colonnes du dataset-descriptor
     */
    public static List<String> getLstDatasetNameColumns(DatasetDescriptor datasetDescriptor) {
        List<String> lstDatasetNameColumns = new LinkedList<>();
        List<Column> lstColumns = datasetDescriptor.getColumns();
        for (Iterator<Column> iterator = lstColumns.iterator(); iterator.hasNext();) {
            Column column = iterator.next();
            lstDatasetNameColumns.add(column.getName());
        }

        return lstDatasetNameColumns;

    }

    /**
     *
     * @param datasetDescriptor
     * @return
     */
    public static Map<String, Column> getMapColumns(DatasetDescriptor datasetDescriptor) {
        Map<String, Column> mapColumns = new HashMap();
        List<Column> lstColumns = datasetDescriptor.getColumns();
        for (Iterator<Column> iterator = lstColumns.iterator(); iterator.hasNext();) {
            Column column = iterator.next();
            mapColumns.put(column.getName(), column);
        }

        return mapColumns;

    }

    /**
     * @param lineNumber
     * @param i
     * @param value
     * @param column
     * @param badsFormatsReport
     */
    public static void isValueFormatDouble(long lineNumber, int i, String value, String column, BadsFormatsReport badsFormatsReport) {
        try {
            String testvalue = value.replaceAll(Constantes.CST_COMMA, Constantes.CST_DOT);
            testvalue = testvalue.replaceAll(Constantes.CST_SPACE, Constantes.CST_STRING_EMPTY);

            Double.parseDouble(testvalue);
        } catch (NumberFormatException e) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("BAD_DOUBLE_VALUE"), lineNumber, i, column, value)));
        }
    }

    /**
     * @param lineNumber
     * @param i
     * @param value
     * @param column
     * @param badsFormatsReport
     */
    public static void isValueFormatFloat(long lineNumber, int i, String value, String column, BadsFormatsReport badsFormatsReport) {
        try {
            String testValue = value.replaceAll(Constantes.CST_COMMA, Constantes.CST_DOT);
            testValue = testValue.replaceAll(Constantes.CST_SPACE, Constantes.CST_STRING_EMPTY);

            Float.parseFloat(testValue);
        } catch (NumberFormatException e) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("BAD_FLOAT_VALUE"), lineNumber, i, column, value)));
        }
    }

    /**
     * @param lineNumber
     * @param i
     * @param value
     * @param column
     * @param badsFormatsReport
     */
    public static void isValueFormatInteger(long lineNumber, int i, String value, String column, BadsFormatsReport badsFormatsReport) {
        try {
            String testValue = value.replaceAll(Constantes.CST_SPACE, Constantes.CST_STRING_EMPTY);

            Integer.parseInt(testValue);
        } catch (NumberFormatException e) {
            badsFormatsReport.addException(new BadExpectedValueException(String.format(SNOTRecorder.getSnotMessage("BAD_INT_VALUE"), lineNumber, i, column, value)));
        }
    }


}
