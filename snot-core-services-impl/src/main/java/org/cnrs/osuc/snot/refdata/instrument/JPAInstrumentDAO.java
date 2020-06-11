/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrument;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;


/**
 * @author sophie
 * 
 */
public class JPAInstrumentDAO extends AbstractJPADAO<Instrument> implements IInstrumentDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAInstrumentDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.instrument.IInstrumentDAO#getByCode(java.lang.String)
     */

    /**
     *
     * @param code
     * @return
     */
    
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Instrument> getByCode(String code) {
        CriteriaQuery<Instrument> query = builder.createQuery(Instrument.class);
        Root<Instrument> instrument = query.from(Instrument.class);
        query
                .select(instrument)
                .where(builder.equal(instrument.get(Instrument_.code), code));
        return getOptional(query);
    }

}