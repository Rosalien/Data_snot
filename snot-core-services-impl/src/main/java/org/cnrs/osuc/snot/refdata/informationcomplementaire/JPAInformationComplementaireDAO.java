/**
 *
 */
package org.cnrs.osuc.snot.refdata.informationcomplementaire;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;


/**
 * @author sophie
 * 
 */
public class JPAInformationComplementaireDAO extends AbstractJPADAO<InformationComplementaire> implements IInformationComplementaireDAO {

    /**
     *
     * @param nom
     * @return
     */
    @Override
    public Optional<InformationComplementaire> getByNom(String nom){
        CriteriaQuery<InformationComplementaire> query = builder.createQuery(InformationComplementaire.class);
        Root<InformationComplementaire> ic = query.from(InformationComplementaire.class);
        query
                .select(ic)
                .where(builder.equal(ic.get(InformationComplementaire_.nom), nom));
        return getOptional(query);
    }
}