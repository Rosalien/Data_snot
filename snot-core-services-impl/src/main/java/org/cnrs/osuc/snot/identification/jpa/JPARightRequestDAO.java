/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.identification.jpa;

import java.util.List;
import javax.persistence.Query;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.identification.entity.RightsRequest;
import org.inra.ecoinfo.identification.IRightRequestDAO;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class JPARightRequestDAO extends AbstractJPADAO<RightsRequest> implements IRightRequestDAO<RightsRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPARightRequestDAO.class);

    /**
     *
     * @param utilisateur
     * @return
     */
    @Override
    public List<RightsRequest> getByUser(Utilisateur utilisateur) {
        List<RightsRequest> requests = null;
        Query query = entityManager.createQuery("from " + RightsRequest.NAME_ENTITY + " where utilisateur=:utilisateur");
        try {
            List<RightsRequest> req = query.getResultList();
        } catch (javax.persistence.PersistenceException e) {
            LOGGER.error("error finding requests", e);
        }
        return requests;
    }

    /**
     *
     * @return
     */
    @Override
    public List<RightsRequest> getAll() {
        return getAll(RightsRequest.class);
    }

}
