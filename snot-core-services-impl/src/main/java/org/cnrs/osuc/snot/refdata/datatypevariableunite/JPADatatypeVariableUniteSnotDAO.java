package org.cnrs.osuc.snot.refdata.datatypevariableunite;

import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.jeu.Jeu_;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot_;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Outils;
import org.inra.ecoinfo.AbstractJPADAO;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet_;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.business.composite.Nodeable_;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.business.composite.RealNode_;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.datatype.DataType_;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.unite.Unite_;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.refdata.variable.Variable_;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class JPADatatypeVariableUniteSnotDAO extends AbstractJPADAO<DatatypeVariableUniteSnot> implements IDatatypeVariableUniteSnotDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPADatatypeVariableUniteSnotDAO.class);

    @Override
    public Map<String, DatatypeVariableUniteSnot> getAllVariableTypeDonneesByDataType(String siteCode, String datatypeCode) {
        CriteriaQuery<DatatypeVariableUniteSnot> query = builder.createQuery(DatatypeVariableUniteSnot.class);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        Join<DatatypeVariableUniteSnot, SiteSnot> site = dvu.join(DatatypeVariableUniteSnot_.siteSnot);
        Join<DatatypeVariableUniteSnot, DataType> datatype = dvu.join(DatatypeVariableUniteSnot_.datatype);
        query
                .select(dvu)
                .where(
                        builder.equal(site.get(SiteSnot_.code), siteCode),
                        builder.equal(datatype.get(DataType_.code),
                                Utils.createCodeFromString(datatypeCode)));
        return getResultAsStream(query)
                .collect(Collectors.toMap(
                        d -> d.getVariable().getCode(),
                        d -> d
                ));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO#getLstVariableByLstDatatype(java.util.List)
     */
    @Override
    public List<VariableSnot> getLstVariableByLstDatatypeAndUser(List<String> lstCodeDatatype, IUser user) {
        CriteriaQuery<VariableSnot> query = builder.createQuery(VariableSnot.class);
        Root<RealNode> realNodeDatatype = query.from(RealNode.class);
        Join<RealNode, DatatypeVariableUniteSnot> dvu = builder.treat(realNodeDatatype.join(RealNode_.nodeable), DatatypeVariableUniteSnot.class);
        Join<DatatypeVariableUniteSnot, DataType> datatype = dvu.join(DatatypeVariableUniteSnot_.datatype);
        Join<DatatypeVariableUniteSnot, VariableSnot> variable = builder.treat(dvu.join(DatatypeVariableUniteSnot_.variable), VariableSnot.class);
        List<Predicate> predicatesAnd = new LinkedList();
        if (!user.getIsRoot()) {
            Root<NodeDataSet> nds = query.from(NodeDataSet.class);
            predicatesAnd.add(builder.equal(nds.get(NodeDataSet_.realNode), realNodeDatatype));
            Outils.addRestrictiveRequestOnRoles(user, query, predicatesAnd, builder, nds, null);
        }
        predicatesAnd.add(datatype.get(DataType_.code).in(lstCodeDatatype));
        query
                .select(variable)
                .where(datatype.get(DataType_.code).in(lstCodeDatatype));
        return getResultList(query);
    }

    /**
     *
     * @param jeuCode
     * @param siteCode
     * @param datatypeCode
     * @param variableCode
     * @param uniteCode
     * @return
     */
    @Override
    public Optional<DatatypeVariableUniteSnot> getByNKey(String jeuCode, String siteCode, String datatypeCode, String variableCode, String uniteCode) {
        CriteriaQuery<DatatypeVariableUniteSnot> query = builder.createQuery(DatatypeVariableUniteSnot.class);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        Join<DatatypeVariableUniteSnot, Jeu> jeu = dvu.join(DatatypeVariableUniteSnot_.jeu);
        Join<DatatypeVariableUniteSnot, SiteSnot> site = dvu.join(DatatypeVariableUniteSnot_.siteSnot);
        Join<DatatypeVariableUniteSnot, Unite> unite = dvu.join(DatatypeVariableUniteSnot_.unite);
        Join<DatatypeVariableUniteSnot, DataType> datatype = dvu.join(DatatypeVariableUniteSnot_.datatype);
        Join<DatatypeVariableUniteSnot, VariableSnot> variable = dvu.join(DatatypeVariableUniteSnot_.variable);
        query
                .select(dvu)
                .where(
                        builder.equal(jeu.get(Jeu_.code_jeu), jeuCode),
                        builder.equal(site.get(SiteSnot_.code), siteCode),
                        builder.equal(datatype.get(DataType_.code), datatypeCode),
                        builder.equal(variable.get(Variable_.code), variableCode),
                        builder.equal(unite.get(Unite_.code), uniteCode)
                );
        return getOptional(query);
    }

    /**
     *
     * @param dvu
     * @throws PersistenceException
     */
    @Override
    public void delete(DatatypeVariableUniteSnot dvu) throws PersistenceException {
        try {
            CriteriaQuery<INode> query = builder.createQuery(INode.class);
            Root<NodeDataSet> node = query.from(NodeDataSet.class);
            query.select(node);
            List<INode> nodes = getResultAsStream(query)
                    .filter(
                            n -> n.getRealNode().getNodeable().getCode().equals(dvu.getCode())
                    )
                    .collect(Collectors.toList());
            CriteriaDelete<NodeDataSet> nodedatasetCriteriaDelete = builder.createCriteriaDelete(NodeDataSet.class);
            Root<NodeDataSet> nds = nodedatasetCriteriaDelete.from(NodeDataSet.class);
            nodedatasetCriteriaDelete.where(nds.in(nodes));
            if (delete(nodedatasetCriteriaDelete) > 0) {
                List<RealNode> realNodes = nodes.stream().map(n -> n.getRealNode()).collect(Collectors.toList());
                CriteriaDelete<RealNode> realnodesCriteriaDelete = builder.createCriteriaDelete(RealNode.class);
                Root<RealNode> rn = realnodesCriteriaDelete.from(RealNode.class);
                realnodesCriteriaDelete.where(rn.in(realNodes));
                if (delete(realnodesCriteriaDelete) > 0) {
                    remove(dvu);
                }
            }
        } catch (final Exception e) {
            LOGGER.error(PersistenceException.getLastCauseExceptionMessage(e));
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public List<DatatypeVariableUniteSnot> getBySiteDatatype(final String siteCode, final String datatypeCode) {
        CriteriaQuery<DatatypeVariableUniteSnot> query = builder.createQuery(DatatypeVariableUniteSnot.class);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        Join<DatatypeVariableUniteSnot, SiteSnot> site = dvu.join(DatatypeVariableUniteSnot_.siteSnot);

        query.where(
                builder.equal(site.get(SiteSnot_.code), siteCode),
                builder.equal(dvu.get(DatatypeVariableUniteSnot_.datatype).get(Nodeable_.code), datatypeCode)
        );
        query.orderBy(builder.asc(dvu.get(DatatypeVariableUniteSnot_.variable).get(Variable_.code)));
        query.select(dvu);
        return getResultList(query);
    }

    @Override
    public List<DatatypeVariableUniteSnot> getByDatatype(final String datatypeCode) {
        CriteriaQuery<DatatypeVariableUniteSnot> query = builder.createQuery(DatatypeVariableUniteSnot.class);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        query.where(
                builder.equal(dvu.get(DatatypeVariableUniteSnot_.datatype).get(Nodeable_.code), datatypeCode)
        );
        query.orderBy(builder.asc(dvu.get(DatatypeVariableUniteSnot_.variable).get(Variable_.code)));
        query.select(dvu);
        return getResultList(query);
    }

    /**
     * <p>
     * get a map of realnode variable sorted by variable where parent node
     * datatype is realNode</p>
     *
     * @param realNode
     * @return
     */
    @Override
    public Map<Variable, RealNode> getRealNodesVariables(RealNode realNode) {

        CriteriaQuery<RealNode> query = builder.createQuery(RealNode.class);
        Root<RealNode> rn = query.from(RealNode.class);
        final Join<RealNode, Nodeable> nodeable = rn.join(RealNode_.nodeable);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        dvu.join(DatatypeVariableUniteSnot_.variable, JoinType.LEFT);
        query.where(
                builder.equal(rn.get(RealNode_.parent), realNode),
                builder.equal(nodeable.get(Nodeable_.id), dvu.get(DatatypeVariableUniteSnot_.id))
        );
        query.select(rn);
        final List<RealNode> resultList = getResultList(query);
        return Maps.uniqueIndex(resultList, c -> ((DatatypeVariableUniteSnot) c.getNodeable()).getVariable());
    }

    /**
     * Gets the unite.
     *
     * @param datatypeCode
     * @param variableCode
     * @return the unite
     * @see
     * org.inra.ecoinfo.refdata.datatypevariableunite.IDatatypeVariableUniteDAO#getUnite(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Optional<Unite> getUnite(final String datatypeCode, final String variableCode) {
        CriteriaQuery<Unite> query = builder.createQuery(Unite.class);
        Root<DatatypeVariableUniteSnot> dvu = query.from(DatatypeVariableUniteSnot.class);
        query.where(
                builder.equal(dvu.get(DatatypeVariableUniteSnot_.datatype).get(Nodeable_.code), datatypeCode),
                builder.equal(dvu.get(DatatypeVariableUniteSnot_.variable).get(Nodeable_.code), variableCode)
        );
        query.orderBy(builder.asc(dvu.get(DatatypeVariableUniteSnot_.variable).get(Variable_.code)));
        query.select(dvu.get(DatatypeVariableUniteSnot_.unite));
        return getOptional(query);
    }

    /**
     *
     * @param realNode
     * @return
     */
    @Override
    public RealNode mergeRealNode(RealNode realNode) {
        return entityManager.merge(realNode);
    }


}
