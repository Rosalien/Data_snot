/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.variable;

import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.Nodeable;

/**
 *
 * @author ptchernia
 */
public interface IAvailablesSynthesisVariablesDAO {

    /**
     *
     * @param user
     * @param datesFormParam
     * @param nodeables
     * @return
     */
    Map<DatatypeVariableUniteSnot, List<Long>> getVariables(IUser user, AbstractDatesFormParam datesFormParam, List<? extends Nodeable> nodeables);
    
}
