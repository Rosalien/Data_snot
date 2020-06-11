/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.nodeable;

import java.util.List;
import org.inra.ecoinfo.mga.business.IUser;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.IntervalDate;

/**
 *
 * @author ptchernia
 */
public interface IAvailablesSynthesisDepositPlacesDAO {

    /**
     *
     * @param user
     * @param intervalDate
     * @return
     */
    List<RealNode> getRealNodes(IUser user, IntervalDate intervalDate);
    
}
