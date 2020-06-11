/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.refdata.site;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.typezoneetude.ITypeSiteDAO;
import org.inra.ecoinfo.refdata.site.JPASiteDAO;
import org.inra.ecoinfo.refdata.site.Site_;

/**
 * @author philippe
 *
 */
public class JPASiteSnotDAO extends JPASiteDAO implements ISiteSnotDAO {

    private static final Logger LOGGER = Logger.getLogger(JPASiteSnotDAO.class);

    /**
     *
     */
    protected ITypeSiteDAO typeSiteDAO;

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ore.forets.plugins.forets1.data.dao.ISiteDAO#getSiteByName(java .lang.String)
     */
    /**
     *
     * @param code
     * @return
     */
    @Override
    public Optional<SiteSnot> getByCode(String code) {
        CriteriaQuery<SiteSnot> query = builder.createQuery(SiteSnot.class);
        Root<SiteSnot> site = query.from(SiteSnot.class);
        query
                .select(site)
                .where(builder.equal(site.get(SiteSnot_.code), code));
        return getOptional(query);
    }

    /**
     *
     * @param name
     * @param parent
     * @return
     */
    @Override
    public Optional<SiteSnot> getByNameAndParent(String name, SiteSnot parent) {
        CriteriaQuery<SiteSnot> query = builder.createQuery(SiteSnot.class);
        Root<SiteSnot> site = query.from(SiteSnot.class);
        query
                .select(site)
                .where(builder.and(
                        builder.equal(site.get(SiteSnot_.name), name),
                        builder.equal(site.get(Site_.parent), parent)
                ));
        return getOptional(query);
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, SiteSnot> getListSite() {
        return getAll()
                .stream()
                .map(s -> (SiteSnot) s)
                .collect(Collectors.toMap(SiteSnot::getPath, s -> s));
    }

    /**
     *
     * @param typeSiteDAO
     */
    public void setTypeSiteDAO(ITypeSiteDAO typeSiteDAO) {
        this.typeSiteDAO = typeSiteDAO;
    }
}
