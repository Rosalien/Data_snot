package org.cnrs.osuc.snot.dataset.flux.jpa;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_j;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_jTour;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationFlux_jDAO extends JPAPublicationFluxDAO {

    
    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureFlux_j.class, ValeurFlux_jTour.class);
    }
}