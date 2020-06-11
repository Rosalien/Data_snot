/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalcul;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;


/**
 * @author sophie
 * 
 */
public class JPAMethodeCalculDAO extends AbstractJPADAO<MethodeCalcul> implements IMethodeCalculDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAMethodeCalculDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.methodecalcul.IMethodeCalculDAO#getByCode(java.lang.String)
     */

    /**
     *
     * @param code
     * @return
     */
    
    @Override
    @SuppressWarnings("unchecked")
    public Optional<MethodeCalcul> getByCode(String code) {
        CriteriaQuery<MethodeCalcul> query = builder.createQuery(MethodeCalcul.class);
        Root<MethodeCalcul> methodeCalcul = query.from(MethodeCalcul.class);
        query
                .select(methodeCalcul)
                .where(builder.equal(methodeCalcul.get(MethodeCalcul_.code), code));
        return getOptional(query);
    }

}