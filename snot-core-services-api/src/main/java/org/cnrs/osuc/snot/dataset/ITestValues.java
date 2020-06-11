/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import com.Ostermiller.util.CSVParser;
import java.io.Serializable;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 * <p>
 * object interface used to test the consistency of the values ​​of a file.
 * 
 * @author Tcherniatinsky Philippe
 */
public interface ITestValues extends Serializable {

    /**
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
    void testValues(long startline, CSVParser parser, VersionFile versionFile,
            IRequestProperties requestProperties, String encoding,
            BadsFormatsReport badsFormatsReport, DatasetDescriptor datasetDescriptor,
            String datatypeName) throws BusinessException;

}
