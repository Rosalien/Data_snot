/**
 *
 */
package org.cnrs.osuc.snot.refdata.reference;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;


/**
 * @author sophie
 * 
 */
public class JPAReferenceDAO extends AbstractJPADAO<Reference> implements IReferenceDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAReferenceDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.reference.IReferenceDAO#getByDOI(java.lang.String)
     */

    /**
     *
     * @param doi
     * @return
     */
    
    @Override
    public Optional<Reference> getByDOI(String doi){
        CriteriaQuery<Reference> query = builder.createQuery(Reference.class);
        Root<Reference> reference = query.from(Reference.class);
        query
                .select(reference)
                .where(builder.equal(reference.get(Reference_.doi), doi));
        return getOptional(query);
    }

}