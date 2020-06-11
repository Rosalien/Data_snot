package org.cnrs.osuc.snot.refdata.datatypevariableunite;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.IDAO;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public interface IDatatypeVariableUniteSnotDAO extends IDAO<DatatypeVariableUniteSnot> {

    /**
     *
     */
    public static final String VARIABLE_CODE_ALIAS = "variable_code";

    /**
     *
     */
    public static final String DATATYPE_ALIAS = "datatype";

    /**
     *
     * @param jeuCode
     * @param siteCode
     * @param datatypeCode
     * @param variableCode
     * @param uniteCode
     *
     * @return
     */
    public Optional<DatatypeVariableUniteSnot> getByNKey(String jeuCode, String siteCode, String datatypeCode, String variableCode, String uniteCode);
 
    
    
    /**
     *
     * @param name
     * @return
     */
    Map<String, DatatypeVariableUniteSnot> getAllVariableTypeDonneesByDataType(String siteCode, String datatypeCode);

    /**
     *
     * @param lstCodeDatatype
     * @param user
     * @return
     */
    List<VariableSnot> getLstVariableByLstDatatypeAndUser(List<String> lstCodeDatatype, IUser user);

    /**
     *
     * @param dvu
     * @throws PersistenceException
     */
    void delete(DatatypeVariableUniteSnot dvu) throws PersistenceException;

    List<DatatypeVariableUniteSnot> getByDatatype(String datatypeCode);

    /**
     * <p>
     * get a map of realnode variable sorted by variable where parent node
     * datatype is realNode</p>
     *
     * @param realNode
     * @return
     */
    Map<Variable, RealNode> getRealNodesVariables(RealNode realNode);

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
    Optional<Unite> getUnite(final String datatypeCode, final String variableCode);

    /**
     *
     * @param realNode
     * @return
     */
    RealNode mergeRealNode(RealNode realNode);

    /**
     *
     * @param siteCode
     * @param datatypeCode
     *
     * @return
     */
    public List<DatatypeVariableUniteSnot> getBySiteDatatype(final String siteCode, final String datatypeCode);
}
