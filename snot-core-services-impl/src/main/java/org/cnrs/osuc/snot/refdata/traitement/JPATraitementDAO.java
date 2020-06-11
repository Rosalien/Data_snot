/**
 *
 */
package org.cnrs.osuc.snot.refdata.traitement;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;

/**
 * @author sophie
 *
 */
public class JPATraitementDAO extends AbstractJPADAO<Traitement> implements ITraitementDAO {

    private static final Logger LOGGER = Logger.getLogger(JPATraitementDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.traitement.ITraitementDAO#getByCode(java.lang.String)
     */
    /**
     *
     * @param code
     * @return
     */
    @Override
    public Optional<Traitement> getByCode(String code) {
        CriteriaQuery<Traitement> query = builder.createQuery(Traitement.class);
        Root<Traitement> traitement = query.from(Traitement.class);
        query
                .select(traitement)
                .where(builder.equal(traitement.get(Traitement_.code), code));
        return getOptional(query);
    }
}