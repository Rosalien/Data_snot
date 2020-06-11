package org.cnrs.osuc.snot.extraction.entity;

import java.util.List;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.variable.Variable;

/**
 *
 * @author ptcherniati
 */
public interface IProspecteurUnite {

    /**
     *
     * @param datatypeName
     * @param variables
     * @return
     */
    String getNomUnitePourVariableDatatype(String datatypeName, List<Variable> variables);

    /**
     *
     * @param datatypeName
     * @param variableName
     * @return
     */
    String getCodeUnitePourVariableDatatype(String datatypeName, String variableName);

    /**
     *
     * @param datatypeName
     * @param variable
     * @return
     */
    String getCodeUnitePourVariableDatatype(String datatypeName, Variable variable);

    /**
     *
     * @param datatypeName
     * @param variables
     * @return
     */
    List<Unite> getUnitePourVariableDatatype(String datatypeName, List<Variable> variables);

    /**
     *
     * @param datatypeName
     * @param variable
     * @return
     */
    Unite getUnitePourVariableDatatype(String datatypeName, Variable variable);
}
