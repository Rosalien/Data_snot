/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;

/**
 * @author sophie
 *
 */
public class JPAItemListeDAO extends AbstractJPADAO<ItemListe> implements IItemListeDAO {

    private static final Logger LOGGER = Logger.getLogger(JPAItemListeDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.listevaleurinfo.IItemListeDAO#getByLibelleListeValeurInfo(java.lang.String, org.cnrs.osuc.snot.refdata.listevaleurinfo.ListeValeurInfo)
     */
    /**
     *
     * @param libelle
     * @param listeValeurInfo
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Optional<ItemListe> getByLibelleListeValeurInfo(String libelle, ListeValeurInfo listeValeurInfo) {
        CriteriaQuery<ItemListe> query = builder.createQuery(ItemListe.class);
        Root<ItemListe> itemListe = query.from(ItemListe.class);
        query
                .select(itemListe)
                .where(builder.and(
                        builder.equal(itemListe.get(ItemListe_.libelle), libelle),
                        itemListe.get(ItemListe_.listeValeurInfo).in(listeValeurInfo)
                ));
        return getOptional(query);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.listevaleurinfo.IItemListeDAO#getByLibelleNoteListeValeurInfo(java.lang.String, java.lang.String, org.cnrs.osuc.snot.refdata.listevaleurinfo.ListeValeurInfo)
     */
    /**
     *
     * @param libelle
     * @param note
     * @param listeValeurInfo
     * @return
     */
    @Override
    public Optional<ItemListe> getByLibelleNoteListeValeurInfo(String libelle, String note, ListeValeurInfo listeValeurInfo) {
        CriteriaQuery<ItemListe> query = builder.createQuery(ItemListe.class);
        Root<ItemListe> itemListe = query.from(ItemListe.class);
        query
                .select(itemListe)
                .where(builder.and(
                        builder.equal(itemListe.get(ItemListe_.libelle), libelle),
                        builder.equal(itemListe.get(ItemListe_.note), note),
                        builder.equal(itemListe.get(ItemListe_.listeValeurInfo), listeValeurInfo)
                ));
        return getOptional(query);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.listevaleurinfo.IItemListeDAO#getByListeValeurInfo(org.cnrs.osuc.snot.refdata.listevaleurinfo.ListeValeurInfo)
     */
    /**
     *
     * @param listeValeurInfo
     * @return
     */
    @Override
    public List<ItemListe> getByListeValeurInfo(ListeValeurInfo listeValeurInfo) {
        CriteriaQuery<ItemListe> query = builder.createQuery(ItemListe.class);
        Root<ItemListe> itemListe = query.from(ItemListe.class);
        query
                .select(itemListe)
                .where(builder.and(
                        builder.equal(itemListe.get(ItemListe_.listeValeurInfo), listeValeurInfo)
                ));
        return getResultList(query);
    }

}