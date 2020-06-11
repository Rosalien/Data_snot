package org.cnrs.osuc.snot.dataset.meteo.jpa;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_j;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_j;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationMeteo_jDAO extends JPAPublicationMeteoDAO {

    
    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureMeteo_j.class, ValeurMeteo_j.class);
    }
}