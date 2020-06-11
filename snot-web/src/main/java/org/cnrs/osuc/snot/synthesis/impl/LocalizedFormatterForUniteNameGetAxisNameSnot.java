package org.cnrs.osuc.snot.synthesis.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.synthesis.ILocalizedFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jbparoissien
 */
public class LocalizedFormatterForUniteNameGetAxisNameSnot implements ILocalizedFormatter<DatatypeVariableUniteSnot> {
    
  ILocalizationManager localizationManager;

    public LocalizedFormatterForUniteNameGetAxisNameSnot() {
    }

    @Override
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    @Override
    public String format(DatatypeVariableUniteSnot dvu, Locale locale, Object... arguments) {
        return Optional.ofNullable(dvu).map(nodeabledvu -> getVariableAxixName(nodeabledvu, locale))
                .orElse("Error while retrieving variable definition)");
    }

    private String getVariableAxixName(DatatypeVariableUniteSnot dvu, Locale locale) {
        Variable variable = dvu.getVariable();
        Unite unite = dvu.getUnite();
        final Properties propertiesVariablesName = localizationManager.newProperties(Nodeable.getLocalisationEntite(Variable.class), Nodeable.ENTITE_COLUMN_NAME, locale);
        final Properties propertiesUnitDisplay = localizationManager.newProperties(Nodeable.getLocalisationEntite(Unite.class), Nodeable.ENTITE_COLUMN_NAME, locale);
        String unitName = propertiesUnitDisplay.getProperty(unite.getName(), unite.getName());
        String variableName = propertiesVariablesName.getProperty(variable.getName(), variable.getName());
        return Optional.ofNullable(variable)
                .map(v -> v.getAffichage())
                .map(a -> String.format("%s (%s - %s)", variableName, a, unitName))
                .orElseGet(()->String.format("%s (%s)", variableName, unitName));
    }

}

