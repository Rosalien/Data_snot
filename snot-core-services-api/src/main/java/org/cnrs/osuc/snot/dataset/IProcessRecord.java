/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import com.Ostermiller.util.CSVParser;
import java.io.Serializable;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 * The Interface IprocessRecord.
 * <p>
 * interface objects used to publish versions of files
 * 
 * @author Tcherniatinsky Philippe
 */
public interface IProcessRecord extends Serializable {

    /**
     * <p>
     * Process record of the file.
     * 
     * @param parser
     *            the parser
     * @param versionFile
     * @link(VersionFile) the version file
     * @param requestProperties
     * @link(IRequestPropertiesACBB) the session properties
     * @param fileEncoding
     *            the file encoding
     * @param datasetDescriptorACBB
     * @link(DatasetDescriptorACBB) the dataset descriptor acbb
     * @throws BusinessException
     *             the business exception {@link VersionFile} the version file
     *             {@link IRequestPropertiesACBB} the session properties
     *             {@link DatasetDescriptorACBB} the {@link DatasetDescriptorACBB}
     * @link(VersionFile) the version file
     * @link(IRequestPropertiesACBB) the session properties
     * @link(DatasetDescriptorACBB) the dataset descriptor acbb
     */
    void processRecord(CSVParser parser, VersionFile versionFile,
            IRequestProperties requestProperties, String fileEncoding,
            DatasetDescriptor datasetDescriptorACBB) throws BusinessException;

}
