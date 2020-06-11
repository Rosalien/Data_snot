/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.extraction.jsf.nodeable.IAvailablesSynthesisDepositPlacesDAO;
import org.cnrs.osuc.snot.utils.Outils;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet_;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.business.composite.RealNode_;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype_;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue_;
import org.inra.ecoinfo.utils.IntervalDate;

/**
 *
 * @author ptchernia
 */
public class AbstractJPAAvailablesSynthesisDepositPlacesDAO extends AbstractJPADAO<RealNode> implements IAvailablesSynthesisDepositPlacesDAO {

    List<Class<? extends GenericSynthesisDatatype>> genericSynthesisDatatypesClasses = new LinkedList();
    List<Class<? extends GenericSynthesisValue>> genericSynthesisValuesClasses = new LinkedList();

    public List<RealNode> getRealNodes(IUser user, IntervalDate intervalDate) {
        Map<String, RealNode> realNodes = new HashMap();
        genericSynthesisDatatypesClasses.stream().
                forEach(sd -> realNodes.putAll(
                getRealNodesForSynthesisDatatype(sd, user, intervalDate).stream()
                        .collect(Collectors.toMap(node -> node.getPath(), node -> node))
        ));
        return new LinkedList(realNodes.values());
    }

    public List<RealNode> getRealNodesForSynthesisDatatype(Class<? extends GenericSynthesisDatatype> genericSynthesisDatatypeClass, IUser user, IntervalDate intervalDate) {
        CriteriaQuery<RealNode> query = builder.createQuery(RealNode.class);
        Root<NodeDataSet> nds = query.from(NodeDataSet.class);
        Join<NodeDataSet, RealNode> realNode = nds.join(NodeDataSet_.realNode);
        Root<? extends GenericSynthesisDatatype> genericSynthesisDatatype = query.from(genericSynthesisDatatypeClass);
        Path<String> site = genericSynthesisDatatype.get(GenericSynthesisDatatype_.site);
        List<Predicate> predicateAnd = new LinkedList();
        predicateAnd.add(builder.equal(realNode.get(RealNode_.path), site));
        if (!(user == null || user.getIsRoot())) {
            Outils.addRestrictiveRequestOnRoles(user, query, predicateAnd, builder, nds, null);
        }
//        Path<LocalDateTime> date = genericSynthesisValue.<LocalDateTime>get(GenericSynthesisValue_.date);
//        Optional.ofNullable(intervalDate).ifPresent(
//                interval -> predicateAnd.add(builder.between(date, interval.getBeginDate(), interval.getEndDate()))
//        );
        query
                .select(realNode)
                .where(builder.and(predicateAnd.toArray(new Predicate[0])))
                .distinct(true);
        return getResultList(query);

    }

    /**
     *
     * @return
     */
    public List<Class<? extends GenericSynthesisDatatype>> getGenericSynthesisDatatypesClasses() {
        return genericSynthesisDatatypesClasses;
    }

    /**
     *
     * @return
     */
    public List<Class<? extends GenericSynthesisValue>> getGenericSynthesisValuesClasses() {
        return genericSynthesisValuesClasses;
    }
}
