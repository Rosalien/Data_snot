/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.display;

import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;

/**
 *
 * @author ptchernia
 */
public class UIDisplay implements IStepBuilder<Integer> {

    public static final String PARAMETER_CODE = "display";
    boolean europeanFormat=true;

    public UIDisplay(ICollector collector) {
        collector.addParameterCollectionEntry(PARAMETER_CODE, europeanFormat?1:2);
    }

    @Override
    public void addCollectToParameter(ICollector collector) {
        collector.addParameterCollectionEntry(PARAMETER_CODE, europeanFormat?1:2);
    }

    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
    }

    @Override
    public boolean isStepValid() {
        return true;
    }

    public boolean isEuropeanFormat() {
        return europeanFormat;
    }

    public void setEuropeanFormat(boolean europeanFormat) {
        this.europeanFormat = europeanFormat;
    }

}
