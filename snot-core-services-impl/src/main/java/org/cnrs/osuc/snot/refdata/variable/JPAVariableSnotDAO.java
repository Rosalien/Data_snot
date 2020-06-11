package org.cnrs.osuc.snot.refdata.variable;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot_;
import org.inra.ecoinfo.refdata.variable.JPAVariableDAO;

/**
 *
 * @author ptcherniati
 */
public class JPAVariableSnotDAO extends JPAVariableDAO implements IVariableSnotDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAVariableSnotDAO.class);
    private static final String QUUERY_GET_VARIABLES_FOR_THEME ="select dvu.variable from DatatypeVariableUniteSnot dvu where dvu.theme.code= :themeCode";
    private static final String QUUERY_GET_DVU_FOR_VARIABLE ="from DatatypeVariableUniteSnot dvu where dvu.variable.code= :variableCode";

    /**
     *
     * @param themeCode
     * @return
     */
    @Override
    public List<VariableSnot> getDatatypeVariableUniteForTheme(String themeCode) {
        Query query = entityManager.createQuery(QUUERY_GET_VARIABLES_FOR_THEME);
        query.setParameter("themeCode", themeCode);
        try {
            return query.getResultList();
        } catch (IllegalStateException e) {
            LOGGER.error("no variable for theme "+themeCode, e);
            return new LinkedList();
        }
    }

    /**
     *
     * @param variableCode
     * @return
     */
    @Override
    public List<DatatypeVariableUniteSnot> getDatatypeVariableUniteForVariable(String variableCode) {
        Query query = entityManager.createQuery(QUUERY_GET_DVU_FOR_VARIABLE);
        query.setParameter("variableCode", variableCode);
        try {
            return query.getResultList();
        } catch (IllegalStateException e) {
            LOGGER.error("no DatatypeVariableUniteSnot for variable "+variableCode, e);
            return new LinkedList();
        }        
    }

    /**
     *
     * @return
     */
    @Override
    public List<VariableSnot> getAllVariablesSnot() {
        CriteriaQuery<VariableSnot> query = builder.createQuery(VariableSnot.class);
        Root<VariableSnot> variable = query.from(VariableSnot.class);
        query
                .select(variable)
                .orderBy(builder.asc(variable.get(VariableSnot_.code)));
        return getResultList(query);
    }
}