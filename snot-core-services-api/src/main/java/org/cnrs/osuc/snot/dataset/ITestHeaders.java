package org.cnrs.osuc.snot.dataset;

/*
 *
 */

import com.Ostermiller.util.CSVParser;
import java.io.Serializable;
import org.inra.ecoinfo.dataset.config.IDatasetConfiguration;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 * <p>
 * interface objects used to test the frame header file.
 * 
 * @author Tcherniatinsky Philippe
 */
public interface ITestHeaders extends Serializable {

    /** The Constant SEPARATOR. */
    char SEPARATOR = ';';

    /**
     * Test headers.
     * 
     * @param parser
     *            the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesACBB) the session properties
     * @param encoding
     *            the encoding
     * @param badsFormatsReport
     * @link(BadsFormatsReport) the bads formats report
     * @param datasetDescriptor
     * @link(DatasetDescriptorACBB) the dataset descriptor
     * @return the last line number of the header
     * @throws BusinessException
     *             test the frame header of a file {@link VersionFile} the version file
     *             {@link IRequestPropertiesACBB} the session properties {@link BadsFormatsReport}
     *             the bads formats report {@link DatasetDescriptorACBB} the dataset descriptor
     * @link(VersionFile) the version file
     * @link(IRequestPropertiesACBB) the session properties
     * @link(BadsFormatsReport) the bads formats report
     * @link(DatasetDescriptorACBB) the dataset descriptor
     */
    long testHeaders(CSVParser parser, VersionFile versionFile,
            IRequestProperties requestProperties, String encoding,
            BadsFormatsReport badsFormatsReport, DatasetDescriptor datasetDescriptor)
            throws BusinessException;

    /**
     *
     * @param datasetConfiguration
     */
    void setDatasetConfiguration(final IDatasetConfiguration datasetConfiguration);
}
