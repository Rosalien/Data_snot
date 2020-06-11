/**
 *
 */
package org.cnrs.osuc.snot.refdata.infocomplstdtvar;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author sophie
 *
 */
public class JPAInfoComplementaireStdtVariableDAO extends AbstractJPADAO<InformationComplementaireStdtVariable> implements IInfoComplementaireStdtVariableDAO {

    private static final Logger LOGGER = Logger.getLogger(JPAInfoComplementaireStdtVariableDAO.class);

    /**
     *
     * @param realNode
     * @param siteThemeDatatypeVariable
     * @param infoComplt
     * @return
     */
    @Override
    public Optional<InformationComplementaireStdtVariable> getByStdtVariableAndInfoComplt(RealNode realNode, InformationComplementaire infoComplt) {
        CriteriaQuery<InformationComplementaireStdtVariable> query = builder.createQuery(InformationComplementaireStdtVariable.class);
        Root<InformationComplementaireStdtVariable> icsv = query.from(InformationComplementaireStdtVariable.class);
        query.select(icsv);
        query.where(
                builder.equal(icsv.get(InformationComplementaireStdtVariable_.realNode), realNode),
                builder.equal(icsv.get(InformationComplementaireStdtVariable_.infoComplementaire), infoComplt)
        );
        return getOptional(query);
    }

}