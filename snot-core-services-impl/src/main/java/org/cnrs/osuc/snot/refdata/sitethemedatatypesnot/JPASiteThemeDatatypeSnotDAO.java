/**
 * OREILacs project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.refdata.sitethemedatatypesnot;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;
import org.inra.ecoinfo.mga.business.composite.AbstractBranchNode;
import org.inra.ecoinfo.mga.business.composite.AbstractBranchNode_;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.business.composite.Nodeable_;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.business.composite.RealNode_;

/**
 * The Class JPASiteThemeDatatypeDAO.
 *
 * @author "Antoine Schellenberger"
 */
public class JPASiteThemeDatatypeSnotDAO extends AbstractJPADAO<INode> implements ISiteThemeDatatypeSnotDAO {

    /**
     * Gets the by path site theme code and datatype code.
     *
     * @param pathSite the path site
     * @param themeCode the theme code
     * @param datatypeCode the datatype code
     * @return the by path site theme code and datatype code
     * @see
     * org.inra.ecoinfo.refdata.sitethemedatatype.ISiteThemeDatatypeDAO#getByPathSiteThemeCodeAndDatatypeCode(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public List<INode> getByPathSiteThemeCodeAndDatatypeCode(String pathSite, String themeCode, String datatypeCode) {
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<NodeDataSet> dtyNode = query.from(NodeDataSet.class);
        Join<RealNode, Nodeable> dty = dtyNode.join(AbstractBranchNode_.realNode).join(RealNode_.nodeable);
        final Join<NodeDataSet, AbstractBranchNode> theNode = dtyNode.join(AbstractBranchNode_.parent);
        Join<RealNode, Nodeable> the = theNode.join(AbstractBranchNode_.realNode).join(RealNode_.nodeable);
        final Join<AbstractBranchNode, AbstractBranchNode> sitNode = theNode.join(AbstractBranchNode_.parent);
        Join<RealNode, Nodeable> sit = sitNode.join(AbstractBranchNode_.realNode).join(RealNode_.nodeable);
        query.where(
                builder.equal(sit.get(Nodeable_.code), pathSite),
                builder.equal(the.get(Nodeable_.code), themeCode),
                builder.equal(dty.get(Nodeable_.code), datatypeCode)
        );
        query.multiselect(dtyNode, theNode, sitNode);
        Tuple result = getFirstOrNull(query);
        return result == null
                ? null
                : result.getElements().stream()
                        .map(t -> (INode) result.get(t))
                        .collect(Collectors.toList());
    }

//    @Override
//    public Optional<SiteThemeDatatypeSnot> getByCode(String code) {
//        CriteriaQuery<SiteThemeDatatypeSnot> query = builder.createQuery(SiteThemeDatatypeSnot.class);
//        Root<SiteThemeDatatypeSnot> s = query.from(SiteThemeDatatypeSnot.class);
//        query
//                .select(s)
//                .where(
//                        builder.equal(s.get(Nodeable_.code), code)
//                );
//        return getOptional(query);
//    }

}
