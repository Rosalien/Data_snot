/*
 *
 */
package org.cnrs.osuc.snot.dataset.impl;

import java.io.Serializable;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IDeleteRecord;
import org.cnrs.osuc.snot.dataset.ILocalPublicationDAO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class DeleteRecord.
 * <P>
 * generic implementation of {@link IDeleteRecord} deleting files
 * 
 * @see org.inra.ecoinfo.acbb.dataset.IDeleteRecord
 * @author Tcherniatinsky Philippe
 */
public class DeleteRecord implements IDeleteRecord, Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteRecord.class);

    /** The Constant serialVersionUID @link(long). */
    static final long serialVersionUID = 1L;

    /**
     * The version file dao @link(IVersionFileDAO). {@link IVersionFileDAO} The version file dao.
     */
    private IVersionFileDAO versionFileDAO;

    /**
     * The publication dao @link(ILocalPublicationDAO). {@link ILocalPublicationDAO} publication dao.
     */
    private ILocalPublicationDAO publicationDAO;

    /**
     * Instantiates a new delete record.
     */
    public DeleteRecord() {}

    /**
     * Delete record.
     * 
     * @link(VersionFile) the version file
     * @throws BusinessException
     *             the business exception
     * @link(VersionFile) the version file
     * @see org.inra.ecoinfo.acbb.dataset.IDeleteRecord#deleteRecord(org.inra.ecoinfo.dataset.versioning.entity.VersionFile)
     * @see org.inra.ecoinfo.acbb.dataset.ILocalPublicationDAO#removeVersion((org .inra.ecoinfo.dataset.versioning.entity.VersionFile)
     */
    @Override
    public void deleteRecord(final VersionFile versionFile) throws BusinessException {
        VersionFile finalVersionFile = versionFile;
        try {
            finalVersionFile = this.getVersionFileDAO().merge(finalVersionFile);
            this.getPublicationDAO().removeVersion(finalVersionFile.getId());
        } catch (final PersistenceException e) {
            LOGGER.debug(e.getMessage());
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     */
    public ILocalPublicationDAO getPublicationDAO() {
        return this.publicationDAO;
    }

    /**
     * Sets the publication dao.
     * 
     * @param publicationDAO
     *            the new publication dao @link(ILocalPublicationDAO) {@link ILocalPublicationDAO} the new publication dao
     */
    public final void setPublicationDAO(final ILocalPublicationDAO publicationDAO) {
        this.publicationDAO = publicationDAO;
    }

    /**
     *
     * @return
     */
    public IVersionFileDAO getVersionFileDAO() {
        return this.versionFileDAO;
    }

    /**
     * Sets the version file dao.
     * 
     * @param versionFileDAO
     *            the new version file dao @link(IVersionFileDAO) {@link IVersionFileDAO} the new version file dao
     */
    public final void setVersionFileDAO(final IVersionFileDAO versionFileDAO) {
        this.versionFileDAO = versionFileDAO;
    }

}
