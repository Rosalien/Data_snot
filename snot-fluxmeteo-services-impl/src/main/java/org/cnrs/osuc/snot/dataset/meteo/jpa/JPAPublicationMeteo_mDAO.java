package org.cnrs.osuc.snot.dataset.meteo.jpa;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_m;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_m;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationMeteo_mDAO extends JPAPublicationMeteoDAO {

    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureMeteo_m.class, ValeurMeteo_m.class);
    }
}