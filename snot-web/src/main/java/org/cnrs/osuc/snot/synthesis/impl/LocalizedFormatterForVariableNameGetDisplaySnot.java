/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.Nodeable;

import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.synthesis.ILocalizedFormatter;

/**
 *
 * @author jbparoissien
 */
public class LocalizedFormatterForVariableNameGetDisplaySnot implements ILocalizedFormatter<DatatypeVariableUniteSnot> {

    ILocalizationManager localizationManager;

    public LocalizedFormatterForVariableNameGetDisplaySnot() {
    }

    @Override
    public String format(DatatypeVariableUniteSnot nodeable, Locale locale, Object... arguments) {
        Optional<DatatypeVariableUniteSnot> dvu = Optional.ofNullable(nodeable);
        final Properties propertiesVariablesNames = localizationManager.newProperties(Nodeable.getLocalisationEntite(Variable.class), Nodeable.ENTITE_COLUMN_NAME, locale);
        return dvu.map(nodeabledvu -> nodeabledvu.getVariable().getName())
                .map(name -> propertiesVariablesNames.getProperty(name, name))
                .orElse("Error while retrieving variable display)");
    }

    @Override
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

}

