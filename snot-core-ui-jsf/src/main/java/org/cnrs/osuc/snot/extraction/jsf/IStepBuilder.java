/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf;

import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;

/**
 *
 * @author ptchernia
 * @param <T>
 */
public interface IStepBuilder<T> {

    /**
     *
     * @param collector
     */
    void addCollectToParameter(ICollector collector);

    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector);

    /**
     *
     * @return
     */
    boolean isStepValid();
}
