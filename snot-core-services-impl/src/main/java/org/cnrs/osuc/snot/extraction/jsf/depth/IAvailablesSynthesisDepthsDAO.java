/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.depth;

import java.util.List;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.Nodeable;

/**
 *
 * @author ptchernia
 */
public interface IAvailablesSynthesisDepthsDAO {

    /**
     *
     * @param user
     * @param datesFormParam
     * @param nodeables
     * @param dvus
     * @return
     */
    List<Integer> getDepths(IUser user, AbstractDatesFormParam datesFormParam, List<? extends Nodeable> nodeables, List<DatatypeVariableUniteSnot> dvus);

}
