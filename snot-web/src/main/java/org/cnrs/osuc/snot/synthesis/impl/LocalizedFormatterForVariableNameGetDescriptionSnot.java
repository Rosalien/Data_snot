/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.impl;

import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.synthesis.ILocalizedFormatter;

/**
 *
 * @author jbparoissien
 */
public class LocalizedFormatterForVariableNameGetDescriptionSnot implements ILocalizedFormatter<DatatypeVariableUniteSnot> {

    ILocalizationManager localizationManager;

    public LocalizedFormatterForVariableNameGetDescriptionSnot() {
    }

    @Override
    public String format(DatatypeVariableUniteSnot nodeable, Locale locale, Object... arguments) {
        Optional<DatatypeVariableUniteSnot> dvu = Optional.ofNullable(nodeable);
        final Properties propertiesVariablesDefinitions = localizationManager.newProperties(Nodeable.getLocalisationEntite(Variable.class), "definition", locale);
        return dvu.map(nodeabledvu -> StringUtils.isEmpty(nodeabledvu.getVariable().getDefinition()) ? nodeabledvu.getVariable().getName() : nodeabledvu.getVariable().getDefinition())
                .map(definition -> propertiesVariablesDefinitions.getProperty(definition, definition))
                .orElse("Error while retrieving variable definition)");
    }

    @Override
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

}
