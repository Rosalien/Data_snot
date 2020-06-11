/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.refdata.typezoneetude;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;

/**
 * @author philippe
 *
 */
public class JPATypeSiteDAO extends AbstractJPADAO<TypeSite> implements ITypeSiteDAO {

    private static final Logger LOGGER = Logger.getLogger(JPATypeSiteDAO.class);

    /**
     *
     * @return
     */
    @Override
    public List<TypeSite> getAll() {
        return this.getAll(TypeSite.class);
    }

    /**
     *
     * @param code
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Optional<TypeSite> getTypeSiteByCode(String code) {
        CriteriaQuery<TypeSite> query = builder.createQuery(TypeSite.class);
        Root<TypeSite> typeSite = query.from(TypeSite.class);
        query
                .where(builder.equal(typeSite.get(TypeSite_.code), code))
                .select(typeSite);
        return getOptional(query);
    }

}