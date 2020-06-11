/*
 *
 */
package org.cnrs.osuc.snot.dataset;

import java.io.Serializable;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 * The Interface IDeleteRecord. interface objects used to remove file versions
 * 
 * @author Tcherniatinsky Philippe
 * 
 */
public interface IDeleteRecord extends Serializable {

    /**
     * Delete record.
     * 
     * @param versionFile
     * @link(VersionFile) the version file
     * @throws BusinessException
     *             delete the version of versionFile {@link VersionFile} the version file
     * @link(VersionFile) the version file
     */
    void deleteRecord(VersionFile versionFile) throws BusinessException;

}
