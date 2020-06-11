package org.cnrs.osuc.snot.dataset.jpa;

import java.util.List;
import javax.persistence.Query;
import org.inra.ecoinfo.dataset.versioning.jpa.JPAVersionFileDAO;
import org.cnrs.osuc.snot.dataset.ILocalDepublicationDAO;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public class JPAPublicationDAO extends JPAVersionFileDAO implements ILocalDepublicationDAO {

    /**
     *
     * @param versionfileId
     * @param sommet
     * @throws PersistenceException
     */
    @Override
    public void removeVersion(Long versionfileId, String sommet) throws PersistenceException {
        String requeteRecherche = "from " + sommet + " where version.id=" + versionfileId.toString();
        Query query = this.entityManager.createQuery(requeteRecherche);
        @SuppressWarnings("rawtypes")
        List lesDonnees = query.getResultList();
        for (Object objet : lesDonnees) {
            this.entityManager.remove(objet);
        }
    }

}
