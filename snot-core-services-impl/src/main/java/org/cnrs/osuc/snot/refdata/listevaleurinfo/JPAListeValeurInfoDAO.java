/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;


/**
 * @author sophie
 * 
 */
public class JPAListeValeurInfoDAO extends AbstractJPADAO<ListeValeurInfo> implements IListeValeurInfoDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAListeValeurInfoDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.listevaleurinfo.IListeValeurInfoDAO#getByNom(java.lang.String)
     */

    /**
     *
     * @param nom
     * @return
     */
    
    @Override
    public Optional<ListeValeurInfo> getByNom(String nom) {
        CriteriaQuery<ListeValeurInfo> query = builder.createQuery(ListeValeurInfo.class);
        Root<ListeValeurInfo> itemListe = query.from(ListeValeurInfo.class);
        query
                .select(itemListe)
                .where(builder.and(
                        builder.equal(itemListe.get(ListeValeurInfo_.nom), nom)
                ));
        return getOptional(query);
    }

}