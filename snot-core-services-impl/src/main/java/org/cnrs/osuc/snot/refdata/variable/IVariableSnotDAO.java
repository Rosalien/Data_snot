package org.cnrs.osuc.snot.refdata.variable;

import java.util.List;

import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.refdata.variable.IVariableDAO;

/**
 *
 * @author ptcherniati
 */
public interface IVariableSnotDAO extends IVariableDAO {

    /**
     *
     * @return
     */
    List<VariableSnot> getAllVariablesSnot();

    /**
     *
     * @param themeCode
     * @return
     */
    List<VariableSnot> getDatatypeVariableUniteForTheme(String themeCode);

    /**
     *
     * @param variableCode
     * @return
     */
    List<DatatypeVariableUniteSnot> getDatatypeVariableUniteForVariable(String variableCode);
}