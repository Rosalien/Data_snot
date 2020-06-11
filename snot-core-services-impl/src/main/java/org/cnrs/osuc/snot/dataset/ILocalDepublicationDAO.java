package org.cnrs.osuc.snot.dataset;

import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public interface ILocalDepublicationDAO extends IVersionFileDAO {

    /**
     *
     * @param versionfileId
     * @param sommet
     * @throws PersistenceException
     */
    void removeVersion(Long versionfileId, String sommet) throws PersistenceException;
}
