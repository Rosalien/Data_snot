///**
// * OREILacs project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
// */
//
//package org.cnrs.osuc.snot.refdata.projet;
//
//import java.util.List;
//import java.util.Optional;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import org.inra.ecoinfo.AbstractJPADAO;
//import org.inra.ecoinfo.mga.business.composite.Nodeable_;
//import org.inra.ecoinfo.utils.exceptions.PersistenceException;
//
///**
// * The Class JPAProjetDAO.
// *
// * @author "Antoine Schellenberger"
// */
//public class JPAProjetDAO extends AbstractJPADAO<Projet> implements IProjetDAO {
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.inra.ecoinfo.monsoere.refdata.projet.IProjetDAO#getAll()
//     */
//    @Override
//    public List<Projet> getAll() throws PersistenceException {
//        return getAll(Projet.class);
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.inra.ecoinfo.monsoere.refdata.projet.IProjetDAO#getByCode(java.lang.String)
//     */
//
//    /**
//     *
//     * @param code
//     * @return
//     */
//
//    @Override
//    public Optional<Projet> getByCode(final String code) {
//            CriteriaQuery<Projet> query = builder.createQuery(Projet.class);
//            Root<Projet> pro = query.from(Projet.class);
//            query
//                    .select(pro)
//                    .where(builder.equal(pro.get(Nodeable_.code), code));
//            return getOptional(query);
//    }
//}
