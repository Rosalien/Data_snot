/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.nodeable;

import org.cnrs.osuc.snot.refdata.site.SiteSnot;

/**
 *
 * @author ptchernia
 */
public class UISite extends UITreeNode<SiteSnot>{
    
    /**
     *
     * @param availablesSynthesisDepositPlacesDAO
     */
    public UISite(IAvailablesSynthesisDepositPlacesDAO availablesSynthesisDepositPlacesDAO) {
        super(availablesSynthesisDepositPlacesDAO);
    }
    
    
}
