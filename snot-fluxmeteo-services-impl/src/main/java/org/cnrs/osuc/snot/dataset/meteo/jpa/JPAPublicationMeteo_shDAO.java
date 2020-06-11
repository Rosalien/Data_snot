package org.cnrs.osuc.snot.dataset.meteo.jpa;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_sh;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_sh;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationMeteo_shDAO extends JPAPublicationMeteoDAO {

   
    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureMeteo_sh.class, ValeurMeteo_sh.class);
    }
}