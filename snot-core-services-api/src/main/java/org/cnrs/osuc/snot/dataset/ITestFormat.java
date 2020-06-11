/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import com.Ostermiller.util.CSVParser;
import java.io.Serializable;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadFormatException;

/**
 * <p>
 * This interface is used for test the format of a file.
 * 
 * @author Tcherniatinsky Philippe
 */
public interface ITestFormat extends Serializable {

    /** The Constant PROPERTY_MSG_ERROR_BAD_FORMAT. */
    String PROPERTY_MSG_ERROR_BAD_FORMAT     = "PROPERTY_MSG_ERROR_BAD_FORMAT";

    /** The Constant PROPERTY_MSG_CHECKING_FORMAT_FILE. */
    String PROPERTY_MSG_CHECKING_FORMAT_FILE = "PROPERTY_MSG_CHECKING_FORMAT_FILE";

    /** The Constant PROPERTY_MSG_BAD_HEADER_SIZE. */
    String PROPERTY_MSG_BAD_HEADER_SIZE      = "PROPERTY_MSG_BAD_HEADER_SIZE";

    /** The Constant DATASET_DESCRIPTOR_XML. */
    String DATASET_DESCRIPTOR_XML            = "dataset-descriptor.xml";

    /**
     * Sets the datatype name.
     * 
     * @param datatypeName
     *            the new datatype name {@link String} the new datatypeName
     */
    void setDatatypeName(String datatypeName);

    /**
     * Sets the test headers.
     * 
     * @param testHeaders
     *            setter of object that test the headers of the file
     */
    void setTestHeaders(ITestHeaders testHeaders);

    /**
     * Sets the test values.
     * 
     * @param testValues
     *            the new test values
     */
    void setTestValues(ITestValues testValues);

    /**
     * Test format.
     * 
     * @param parser
     *            the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesACBB) the session properties
     * @param encoding
     *            the encoding
     * @param datasetDescriptor
     * @link(DatasetDescriptorACBB) the dataset descriptor
     * @throws BadFormatException
     *             the bad format exception {@link VersionFile} the version file
     *             {@link IRequestPropertiesACBB} the session properties
     *             {@link DatasetDescriptorACBB} test the format of a version file
     * @link(VersionFile) the version file
     * @link(IRequestPropertiesACBB) the session properties
     * @link(DatasetDescriptorACBB) the dataset descriptor
     */
    void testFormat(CSVParser parser, VersionFile versionFile,
            IRequestProperties requestProperties, String encoding,
            DatasetDescriptor datasetDescriptor) throws BadFormatException;

}
