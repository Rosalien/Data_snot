package org.cnrs.osuc.snot.dataset.flux.jpa;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_m;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_mTour;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationFlux_mDAO extends JPAPublicationFluxDAO {

    
    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureFlux_m.class, ValeurFlux_mTour.class);
    }
}