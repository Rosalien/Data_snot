package org.cnrs.osuc.snot.dataset.flux.jpa;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_sh;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_shTour;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe
 * 
 */
public class JPAPublicationFlux_shDAO extends JPAPublicationFluxDAO {

    
    /**
     *
     * @param versionId
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionId) throws PersistenceException {
        super.removeVersion(versionId, MesureFlux_sh.class, ValeurFlux_shTour.class);
    }
}
