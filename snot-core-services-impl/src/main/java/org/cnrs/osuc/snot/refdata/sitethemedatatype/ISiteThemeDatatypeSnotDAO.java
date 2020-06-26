/**
 * OREILacs project - see LICENCE.txt for use created: 5 mai 2009 11:53:17
 */
package org.cnrs.osuc.snot.refdata.sitethemedatatype;

import java.util.List;
import org.inra.ecoinfo.IDAO;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.RealNode;


/**
 * The Interface ISiteThemeDatatypeDAO.
 * 
 * @author "Antoine Schellenberger"
 */
public interface ISiteThemeDatatypeSnotDAO extends IDAO<INode> {

    /**
     * Gets the by path site theme code and datatype code.
     * 
     * @param pathSite
     *            the path site
     * @param themeCode
     *            the theme code
     * @param datatypeCode
     *            the datatype code
     * @return the by path site theme code and datatype code
     * @link(String) the path site
     * @link(String) the theme code
     * @link(String) the datatype code
     */
    List<INode> getByPathSiteThemeCodeAndDatatypeCode(String pathSite, String themeCode, String datatypeCode);
    

}
