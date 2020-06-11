/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrumentreference;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.instrument.Instrument_;
import org.cnrs.osuc.snot.refdata.reference.Reference;
import org.cnrs.osuc.snot.refdata.reference.Reference_;

/**
 * @author sophie
 *
 */
public class JPAInstrumentReferenceDAO extends AbstractJPADAO<InstrumentReference> implements IInstrumentReferenceDAO {

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.instrumentreference.IInstrumentReferenceDAO#getByCodeInstrDoiRef(java.lang.String, java.lang.String)
     */
    /**
     *
     * @param codeInstrument
     * @param doiReference
     * @return
     */
    @Override
    public Optional<InstrumentReference> getByCodeInstrDoiRef(String codeInstrument, String doiReference) {
        CriteriaQuery<InstrumentReference> query = builder.createQuery(InstrumentReference.class);
        Root<InstrumentReference> instrumentReference = query.from(InstrumentReference.class);
        Join<InstrumentReference, Instrument> instrument = instrumentReference.join(InstrumentReference_.instrument);
        Join<InstrumentReference, Reference> methodeInstrumentReference = instrumentReference.join(InstrumentReference_.referenceInstrument);
        query
                .select(instrumentReference)
                .where(builder.and(
                        builder.equal(instrument.get(Instrument_.code), codeInstrument),
                        builder.equal(methodeInstrumentReference.get(Reference_.doi), doiReference)
                ));
        return getOptional(query);
    }

}