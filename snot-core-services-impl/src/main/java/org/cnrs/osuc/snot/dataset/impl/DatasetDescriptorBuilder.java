/**
 * OREILacs project - see LICENCE.txt for use created: 23 févr. 2009 16:11:37
 */
package org.cnrs.osuc.snot.dataset.impl;

import java.io.File;
import java.io.IOException;
import org.apache.commons.digester.Digester;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.xml.sax.SAXException;

/**
 * @author philippe
 * 
 */
public class DatasetDescriptorBuilder {

    /**
     *
     * @param datasetDescriptorFile
     * @return
     * @throws IOException
     * @throws SAXException
     */
    public static final DatasetDescriptor buildDescriptor(File datasetDescriptorFile)
            throws IOException, SAXException {
        DatasetDescriptor datasetDescriptor = null;
        Digester digester = new Digester();
        digester.setNamespaceAware(true);
        digester.setUseContextClassLoader(true);
        digester.addObjectCreate("dataset-descriptor", DatasetDescriptor.class);
        digester.addCallMethod("dataset-descriptor/name", "setName", 0);
        digester.addObjectCreate("dataset-descriptor/column", Column.class);
        digester.addSetNext("dataset-descriptor/column", "addColumn");
        digester.addCallMethod("dataset-descriptor/column/name", "setName", 0);
        digester.addCallMethod("dataset-descriptor/column/not-null", "setNullable", 0);
        digester.addCallMethod("dataset-descriptor/column/value-type", "setValueType", 0);
        digester.addCallMethod("dataset-descriptor/column/variable", "setVariable", 0);
        digester.addCallMethod("dataset-descriptor/column/flag", "setFlag", 0);
        digester.addCallMethod("dataset-descriptor/column/flag-type", "setFlagType", 0);
        digester.addCallMethod("dataset-descriptor/column/ref-variable-name", "setRefVariableName",
                0);
        digester.addCallMethod("dataset-descriptor/column/format-type", "setFormatType", 0);
        /*
         * digester.addCallMethod( "dataset-descriptor/column/variable-reference/category",
         * "setRefCategory", 0); digester.addCallMethod(
         * "dataset-descriptor/column/variable-reference/column", "setRefColumn", 0);
         */

        datasetDescriptor = (DatasetDescriptor) digester.parse(datasetDescriptorFile);

        return datasetDescriptor;
    }
}
