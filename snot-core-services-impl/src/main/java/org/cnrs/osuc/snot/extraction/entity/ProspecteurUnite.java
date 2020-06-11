package org.cnrs.osuc.snot.extraction.entity;

import java.util.List;
import java.util.stream.Collectors;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.Utils;

/**
 *
 * @author ptcherniati
 */
public class ProspecteurUnite implements IProspecteurUnite {

    IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO;

    /**
     *
     * @return
     */
    public IDatatypeVariableUniteSnotDAO getDatatypeVariableUniteSnotDAO() {
        return this.datatypeVariableUniteDAO;
    }

    public void setDatatypeVariableUniteDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO) {
        this.datatypeVariableUniteDAO = datatypeVariableUniteDAO;
    }

    /**
     *
     * @param datatypeVariableUniteDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO) {
        this.datatypeVariableUniteDAO = datatypeVariableUniteDAO;
    }
   
    
    @Override
    public String getNomUnitePourVariableDatatype(String datatypeName, List<Variable> variables) {
        List<Unite> lesUnites = this.getUnitePourVariableDatatype(datatypeName, variables);
        String ligne = ";;";
        for (Unite unite : lesUnites) {
            if (unite != null) {
                ligne = ligne + unite.getName()+ ";";
            } else {
                ligne += ";";
            }
        }
        return ligne;
    }

    @Override
    public String getCodeUnitePourVariableDatatype(String datatypeName, String variableName) {
          String datatypeCode = Utils.createCodeFromString(datatypeName);
       
        return this.datatypeVariableUniteDAO.getUnite(datatypeCode, variableName)
                .map(u->u.getCode())
                .orElseGet(String::new);
    }

    @Override
    public String getCodeUnitePourVariableDatatype(String datatypeName, Variable variable) {
        String codeVariable = variable.getCode();
        String datatypeCode = Utils.createCodeFromString(datatypeName);

        return this.datatypeVariableUniteDAO.getUnite(datatypeCode, codeVariable)
                .map(u->u.getCode())
                .orElseGet(String::new);
    }

    @Override
    public List<Unite> getUnitePourVariableDatatype(String datatypeName, List<Variable> variables) {
        return variables.stream()
                .map(var->this.getUnitePourVariableDatatype(datatypeName, var))
                .collect(Collectors.toList());
    }

    @Override
    public Unite getUnitePourVariableDatatype(String datatypeName, Variable variable) {
        String codeVariable = variable.getCode();
        String datatypeCode = Utils.createCodeFromString(datatypeName);


        return this.datatypeVariableUniteDAO.getUnite(datatypeCode, codeVariable).orElse(null);
        
    }
}