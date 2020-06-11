/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:53:17
 */
package org.cnrs.osuc.snot.refdata.typezoneetude;

import java.util.List;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author philippe
 */
public interface ITypeSiteDAO extends IDAO<TypeSite> {

    /**
     *
     * @return
     */
    List<TypeSite> getAll();

    /**
     *
     * @param code
     * @return
     */
    Optional<TypeSite> getTypeSiteByCode(String code);
}