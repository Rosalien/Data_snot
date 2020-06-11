package org.cnrs.osuc.snot.dataset.impl;

import com.google.common.base.Strings;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.digester.Digester;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.xml.sax.SAXException;


/**
 * @author philippe
 * 
 */
/**
 * ajout datasetdescriptor climat du sol sophie
 * 
 */
/**
 * ajout datasetdescriptor commun a flux et meteo (Monique, juillet 2013)
 * 
 */
public class DatasetDescriptorBuilderSnots extends DatasetDescriptorBuilder {

    /**
     * The Constant Snot_DATASET_BUNDLE_NAME.
     */
    static final String DATASET_DESCRIPTOR_PATH_PATTERN = "org/cnrs/osuc/snot/dataset/%s";
    static final int BUILD_DESCRIPTOR_WITH_VARIABLE_NAMES_AND_UNDEFINED_COLUMNS = 0;
    static final int BUILD_DESCRIPTOR_DEFAULT = 1;

    /**
     *
     * @param <T>
     * @param t
     * @param datasetDescriptorPath
     * @param method
     * @return
     * @throws IOException
     * @throws SAXException
     */
    public static final <T extends DatasetDescriptor> T buildDescriptor(Class<T> t, String datasetDescriptorPath, int method) throws IOException, SAXException {
        if (t == null) {
            throw new NoClassDefFoundError();
        }
        if (Strings.isNullOrEmpty(datasetDescriptorPath)) {
            throw new IllegalArgumentException("no dataset descriptor path");
        }
        T datasetDescriptor = null;
        final String path = String.format(DATASET_DESCRIPTOR_PATH_PATTERN, datasetDescriptorPath);
        InputStream datasetDescriptorResource = DatasetDescriptorBuilderSnots.class.getClassLoader().getResourceAsStream(path);
        switch (method) {
            case BUILD_DESCRIPTOR_WITH_VARIABLE_NAMES_AND_UNDEFINED_COLUMNS :
                return buildDescriptorWithVariableNamesAndUndefinedColumns(t, datasetDescriptorResource);
            case BUILD_DESCRIPTOR_DEFAULT :
                return buildDescriptor(t, datasetDescriptorResource);
            default :
                return buildDescriptor(t, datasetDescriptorResource);
        }
    }

    /**
     *
     * @param <T>
     * @param t
     * @param datasetDescriptorResource
     * @return
     * @throws IOException
     * @throws SAXException
     */
    @SuppressWarnings("unchecked")
    public static final <T extends DatasetDescriptor> T buildDescriptor(Class<T> t, InputStream datasetDescriptorResource) throws IOException, SAXException {
        T datasetDescriptorFlux = null;
        Digester digester = getDigester(t);
        datasetDescriptorFlux = (T) digester.parse(datasetDescriptorResource);
        return datasetDescriptorFlux;
    }

    /**
     * @param <T>
     * @param t
     * @param datasetDescriptorResource
     * @return
     * @throws IOException
     * @throws SAXException
     */
    @SuppressWarnings("unchecked")
    public static final <T extends DatasetDescriptor> T buildDescriptorWithVariableNamesAndUndefinedColumns(Class<T> t, InputStream datasetDescriptorResource) throws IOException, SAXException {
        T datasetDescriptorCS = null;
        Digester digester = getDigester(t);
        DatasetDescriptorBuilderSnots.setVariableName(digester);

        datasetDescriptorCS = (T) digester.parse(datasetDescriptorResource);

        return datasetDescriptorCS;
    }

    static <T extends DatasetDescriptor> Digester getDigester(Class<T> t) {
        Digester digester = new Digester();
        digester.setNamespaceAware(true);
        digester.setUseContextClassLoader(true);
        digester.addObjectCreate("dataset-descriptor", t);
        DatasetDescriptorBuilderSnots.setName(digester);
        DatasetDescriptorBuilderSnots.setFrequence(digester);
        DatasetDescriptorBuilderSnots.setColumns(digester);
        DatasetDescriptorBuilderSnots.setSites(digester);
        DatasetDescriptorBuilderSnots.setUndefinedColumn(digester);
        DatasetDescriptorBuilderSnots.setEnTete(digester);
        return digester;
    }

    static final void setColumns(Digester digester) {
        digester.addObjectCreate("dataset-descriptor/column", Column.class);
        digester.addSetNext("dataset-descriptor/column", "addColumn");
        digester.addCallMethod("dataset-descriptor/column/name", "setName", 0);
        digester.addCallMethod("dataset-descriptor/column/not-null", "setNullable", 0);
        digester.addCallMethod("dataset-descriptor/column/value-type", "setValueType", 0);
        digester.addCallMethod("dataset-descriptor/column/variable", "setVariable", 0);
        digester.addCallMethod("dataset-descriptor/column/flag", "setFlag", 0);
        digester.addCallMethod("dataset-descriptor/column/flag-type", "setFlagType", 0);
        digester.addCallMethod("dataset-descriptor/column/ref-variable-name", "setRefVariableName", 0);
        digester.addCallMethod("dataset-descriptor/column/format-type", "setFormatType", 0);

    }

    static final void setEnTete(Digester digester) {
        digester.addCallMethod("dataset-descriptor/en-tete", "setEnTete", 0);
    }

    static final void setFrequence(Digester digester) {
        digester.addCallMethod("dataset-descriptor/frequence", "setFrequence", 0);
    }

    static final void setName(Digester digester) {
        digester.addCallMethod("dataset-descriptor/name", "setName", 0);
    }

    static void setSites(Digester digester) {
        digester.addCallMethod("dataset-descriptor/sites", "setSites", 0);
    }

    static void setUndefinedColumn(Digester digester) {
        digester.addCallMethod("dataset-descriptor/undefined-column", "setUndefinedColumn", 0);
    }

    static void setVariableName(Digester digester) {
        digester.addCallMethod("dataset-descriptor/variableName", "setVariableName", 0);
    }
}
