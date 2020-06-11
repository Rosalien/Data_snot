/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalculreference;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul_;
import org.cnrs.osuc.snot.refdata.reference.Reference;
import org.cnrs.osuc.snot.refdata.reference.Reference_;


/**
 * @author sophie
 * 
 */
public class JPAMethodeCalculReferenceDAO extends AbstractJPADAO<MethodeCalculReference> implements IMethodeCalculReferenceDAO {

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.methodecalculreference.IMethodeCalculReferenceDAO#getByCodeMCalcDoiRef(java.lang.String, java.lang.String)
     */

    /**
     *
     * @param codeMethodecalcul
     * @param doiReference
     * @return
     */
    
    @Override
    public Optional<MethodeCalculReference> getByCodeMCalcDoiRef(String codeMethodecalcul, String doiReference) {
        CriteriaQuery<MethodeCalculReference> query = builder.createQuery(MethodeCalculReference.class);
        Root<MethodeCalculReference> methodeReference = query.from(MethodeCalculReference.class);
        Join<MethodeCalculReference, MethodeCalcul> methodeCalcul = methodeReference.join(MethodeCalculReference_.methodeCalcul);
        Join<MethodeCalculReference, Reference> methodeCalculReference = methodeReference.join(MethodeCalculReference_.referenceMCalc);
        query
                .select(methodeReference)
                .where(builder.and(
                        builder.equal(methodeCalcul.get(MethodeCalcul_.code), codeMethodecalcul),
                        builder.equal(methodeCalculReference.get(Reference_.doi), doiReference)
                ));
        return getOptional(query);
    }

}