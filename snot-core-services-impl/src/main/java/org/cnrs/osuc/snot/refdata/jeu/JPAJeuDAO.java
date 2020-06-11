/**
 *
 */
package org.cnrs.osuc.snot.refdata.jeu;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;

/**
 * @author jbparoissien
 *
 */
public class JPAJeuDAO extends AbstractJPADAO<Jeu> implements IJeuDAO {

    private static final Logger LOGGER = Logger.getLogger(JPAJeuDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.jeu.IJeuDAO#getByCode(java.lang.String)
     */
    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<Jeu> getAll() {
        return getAll(Jeu.class);
    }

    /**
     *
     * @param code_jeu
     * @return
     */
    @Override
    public Optional<Jeu> getByCode(String code_jeu) {
        CriteriaQuery<Jeu> query = builder.createQuery(Jeu.class);
        Root<Jeu> jeu = query.from(Jeu.class);
        query
                .select(jeu)
                .where(builder.equal(jeu.get(Jeu_.code_jeu), code_jeu));
        return getOptional(query);
    }

//    /**
//     * A retirer/reconstruireJBP
//     *
//     * @param realnodes
//     * @return
//     */
//    @Override
//    public String buildCodeJeu(Collection<RealNode> realnodes) {
//
//        CriteriaQuery<String> query = builder.createQuery(String.class);
//        Root<Jeu> jeu = query.from(Jeu.class);
//        query
//                .select(jeu.get(Jeu_.code_jeu))
//                .where(jeu.in(realnodes));
//
//        return getResultAsStream(query)
//                .collect(Collectors.joining(","));
//    }

//    /**A retirer/reconstruireJBP
//     *
//     * @param sites
//     * @return
//     */
//    @Override
//    public String buildCodeJeuWithSite(Collection<SiteSnot> sites) {
//
//        CriteriaQuery<String> query = builder.createQuery(String.class);
//        Root<Jeu> jeu = query.from(Jeu.class);
//        Join<Jeu, SiteSnot> site = jeu.join(Jeu_.site);
//        query
//                .select(jeu.get(Jeu_.code_jeu))
//                .where(site.in(sites));
//    
//        return getResultAsStream(query)
//                .collect(Collectors.joining(","));
//    }
//    
    
//    @Override
//    public String buildCodeJeuDtvu(Collection<DatatypeVariableUniteSnot> dtvus) {
//
//        CriteriaQuery<String> query = builder.createQuery(String.class);
//        Root<Jeu> jeu = query.from(Jeu.class);
//        Join<Jeu, DatatypeVariableUniteSnot> dtvu = jeu.join(Jeu_.site);
////        Join<Jeu, DatatypeVariableUniteSnot> dtvu = jeu.join(Jeu_.theme);
//        query
//                .select(jeu.get(Jeu_.code_jeu))
//                .where(dtvu.in(dtvus));
//    
//        return getResultAsStream(query)
//                .collect(Collectors.joining(","));
//    }
//
}
