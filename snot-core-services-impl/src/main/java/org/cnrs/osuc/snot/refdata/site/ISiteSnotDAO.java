/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:53:17
 */
package org.cnrs.osuc.snot.refdata.site;

import java.util.Map;
import java.util.Optional;
import org.inra.ecoinfo.refdata.site.ISiteDAO;


/**
 * @author philippe
 * 
 */
public interface ISiteSnotDAO extends ISiteDAO {

    /**
     *
     * @param code
     * @return
     */
    Optional<SiteSnot> getByCode(String code);

    /**
     *
     * @param code
     * @param parent
     * @return
     */
    Optional<SiteSnot> getByNameAndParent(String code, SiteSnot parent);

    /**
     *
     * @return
     */
    Map<String, SiteSnot> getListSite();
}