package org.cnrs.osuc.snot.dataset;

import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author philippe
 * 
 */
public interface ILocalPublicationDAO extends IVersionFileDAO {

    /**
     *
     * @param versionfileId
     * @throws PersistenceException
     */
    void removeVersion(Long versionfileId) throws PersistenceException;
}
