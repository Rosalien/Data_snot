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
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.extraction.jsf.variable.IAvailablesSynthesisVariablesDAO;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.utils.Outils;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet_;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.business.composite.Nodeable_;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.business.composite.RealNode_;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue_;
import org.inra.ecoinfo.utils.IntervalDate;

/**
 *
 * @author ptchernia
 */
public class AbstractJPAAvailablesSynthesisVariablesDAO extends AbstractJPADAO<RealNode> implements IAvailablesSynthesisVariablesDAO {

    /**
     *
     */
    public static final String DVU = "dvu";

    /**
     *
     */
    public static final String NODE = "node";

    Map<String, List<Class<? extends GenericSynthesisValue>>> genericSynthesisValuesClasses = new HashMap();

//    List<Class<? extends GenericSynthesisValue>> genericSynthesisValuesClasses = new LinkedList();
//    Test JBP
//        public List<RealNode> getRealNodes(IUser user, IntervalDate intervalDate) {
//        Map<String, RealNode> realNodes = new HashMap();
//        genericSynthesisValuesClasses.stream().
//                forEach(sd -> realNodes.putAll(
//                getVariables(sd, user, datesFormParam).stream()
//                        .collect(Collectors.toMap(node -> node.getPath(), node -> node))
//        ));
//        return new LinkedList(realNodes.values());
//    }

////    Test JBP
//    public Map<DatatypeVariableUniteSnot, List<Long>> getVariables(IUser user, AbstractDatesFormParam datesFormParam, List<? extends Nodeable> nodeables) {
//        CriteriaQuery<RealNode> query = builder.createQuery(RealNode.class);
//        Root<NodeDataSet> nds = query.from(NodeDataSet.class);
//        Join<NodeDataSet, RealNode> realNode = nds.join(NodeDataSet_.realNode);
//        Root<? extends GenericSynthesisValue> genericSynthesisValue = query.from(genericSynthesisValueClass);
//                
//        Path<String> variable = genericSynthesisValue.get(GenericSynthesisValue_.variable);
//        List<Predicate> predicateAnd = new LinkedList();
//        predicateAnd.add(builder.equal(realNode.get(Nodeable_.code), variable));
//        Path<LocalDateTime> date = genericSynthesisValue.<LocalDateTime>get(GenericSynthesisValue_.date);
//        if (!user.getIsRoot()) {
//            Outils.addRestrictiveRequestOnRoles(user, query, predicateAnd, builder, nds, date);
//        }
//        query
//                .select(realNode)
//                .where(builder.and(predicateAnd.toArray(new Predicate[0])))
//                .distinct(true);
//        return getResultList(query);
//    }
    /**
     *
     * @param user
     * @param datesFormParam
     * @param nodeables
     * @return
     */
    @Override
    public Map<DatatypeVariableUniteSnot, List<Long>> getVariables(IUser user, AbstractDatesFormParam datesFormParam, List<? extends Nodeable> nodeables) {
        if (datesFormParam.intervalsDate().isEmpty()) {
            return new HashMap();
        }
        IntervalDate intervalDate = datesFormParam.intervalsDate().get(0);
        String rythme = datesFormParam.getRythme();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<NodeDataSet> nds = query.from(NodeDataSet.class);
        Join<NodeDataSet, RealNode> realNodeVariable = nds.join(NodeDataSet_.realNode);
        Join<RealNode, RealNode> realNodeDatatype = realNodeVariable.join(RealNode_.parent);
        Join<RealNode, RealNode> realNodeTheme = realNodeDatatype.join(RealNode_.parent);
        Join<RealNode, RealNode> realNodeSite = realNodeTheme.join(RealNode_.parent);
        Join<RealNode, Nodeable> site = realNodeSite.join(RealNode_.nodeable);
        Join<RealNode, DatatypeVariableUniteSnot> dvu = builder.treat(realNodeVariable.join(RealNode_.nodeable), DatatypeVariableUniteSnot.class);
        List<Predicate> predicateOr = new LinkedList();
        predicateOr = genericSynthesisValuesClasses.getOrDefault(rythme, new LinkedList<>()).stream()
                .map(genericSynthesisDatatypeClasse -> buildSubquery(query, genericSynthesisDatatypeClasse, intervalDate, user))
                .map(sq -> dvu.in(sq))
                .collect(Collectors.toList());
        query
                .multiselect(dvu.alias(DVU), nds.get(NodeDataSet_.id).alias(NODE))
                .distinct(true)
                .where(
                        site.in(nodeables),
                        builder.or(predicateOr.toArray(new Predicate[0]))
                );
        Map<DatatypeVariableUniteSnot, List<Long>> variables = new HashMap();
        getResultList(query)
                .stream()
                .forEach(t -> variables
                .computeIfAbsent(t.get(DVU, DatatypeVariableUniteSnot.class), k -> new LinkedList<>())
                .add(t.get(NODE, Long.class))
                );
        return variables;
    }
    /**
     *
     * @param query
     * @param genericSynthesisValueClass
     * @param intervalDate
     * @param user
     * @return
     */
    public Subquery<DatatypeVariableUniteSnot> buildSubquery(CriteriaQuery<Tuple> query, Class<? extends GenericSynthesisValue> genericSynthesisValueClass, IntervalDate intervalDate, IUser user) {
        Subquery<DatatypeVariableUniteSnot> subquery = query.subquery(DatatypeVariableUniteSnot.class);
        Root<? extends GenericSynthesisValue> genericSynthesisValue = subquery.from(genericSynthesisValueClass);
        Root<RealNode> realNode = subquery.from(RealNode.class);
        Root<NodeDataSet> nds = query.from(NodeDataSet.class);
        Join<RealNode, DatatypeVariableUniteSnot> dvu = builder.treat(realNode.join(RealNode_.nodeable), DatatypeVariableUniteSnot.class);
        Path<String> variable = genericSynthesisValue.get(GenericSynthesisValue_.variable);
        List<Predicate> predicateAnd = new LinkedList();
        predicateAnd.add(builder.equal(dvu.get(Nodeable_.code), variable));
        Path<LocalDateTime> date = genericSynthesisValue.<LocalDateTime>get(GenericSynthesisValue_.date);
//        Optional.ofNullable(intervalDate).ifPresent(
//                interval -> predicateAnd.add(builder.between(date, interval.getBeginDate(), interval.getEndDate()))
//        );               
//        if (!user.getIsRoot()) {
////            Root<NodeDataSet> nds = query.from(NodeDataSet.class);
////            predicateAnd.add(builder.equal(nds.get(NodeDataSet_.realNode), realNode));
//            Outils.addRestrictiveRequestOnRoles(user, query, predicateAnd, builder, nds, null);
//        }
        subquery
                .select(dvu)
                //                .where(predicateAnd.toArray(new Predicate[0]))//Voir le problème ici
                .where(builder.and(predicateAnd.toArray(new Predicate[0])))
                .distinct(true);
        return subquery;
    }
    /**
     *
     * @return
     */
    public Map<String, List<Class<? extends GenericSynthesisValue>>> getGenericSynthesisValuesClasses() {
        return genericSynthesisValuesClasses;
    }
    
//    public List<Class<? extends GenericSynthesisValue>> getGenericSynthesisValuesClasses() {
//        return genericSynthesisValuesClasses;
//    }
    
}
