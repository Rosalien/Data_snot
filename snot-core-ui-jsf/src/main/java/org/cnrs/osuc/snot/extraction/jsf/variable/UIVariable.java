/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.variable;

import org.cnrs.osuc.snot.extraction.jsf.variable.IAvailablesSynthesisVariablesDAO;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.extraction.jsf.date.UIDate;
import org.cnrs.osuc.snot.extraction.jsf.nodeable.UITreeNode;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.variable.Variable;

/**
 *
 * @author ptchernia
 */
public class UIVariable implements IStepBuilder<List<DatatypeVariableUniteSnot>> {
    static Properties propertiesVariableName;
    static Properties propertiesVariableDefinition;
    static Properties propertiesDatatypeName;

    /**
     *
     */
    public static final String PARAMETER_CODE = DatatypeVariableUniteSnot.class.getSimpleName();

    /**
     *
     */
    public static final String PARAMETER_CODE_NODEDATASET = NodeDataSet.class.getSimpleName();
    Map<String, List<VariableJSF>> variablesAvailables = new HashMap();
    IAvailablesSynthesisVariablesDAO availablesSynthesisVariablesDAO;

    /**
     *
     * @param availablesSynthesisVariablesDAO
     */
    public UIVariable(IAvailablesSynthesisVariablesDAO availablesSynthesisVariablesDAO) {
        this.availablesSynthesisVariablesDAO = availablesSynthesisVariablesDAO;
    }

    /**
     *
     * @param collector
     */
    @Override
    public void addCollectToParameter(ICollector collector) {        
        List<DatatypeVariableUniteSnot> selectedDVU = variablesAvailables
                .values()
                .stream()
                .flatMap(k->k.stream())
                .filter(vjsf->vjsf.selected)
                .map(vjsf->(DatatypeVariableUniteSnot)vjsf.dvu)
                .collect(Collectors.toList());
        collector.addParameterCollectionEntry(PARAMETER_CODE, selectedDVU);
        List<Long> selectedNodes = variablesAvailables.values().stream()
                .flatMap(k->k.stream())
                .filter(vjsf->vjsf.selected)
                .map(vjsf->vjsf.nodeIds)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        collector.addParameterCollectionEntry(PARAMETER_CODE, selectedDVU);
        collector.addParameterCollectionEntry(PARAMETER_CODE_NODEDATASET, selectedNodes);
    }

    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
        propertiesVariableName = localizationManager.newProperties(
                Nodeable.getLocalisationEntite(VariableSnot.class), 
                Nodeable.ENTITE_COLUMN_NAME, 
                FacesContext.getCurrentInstance().getViewRoot().getLocale()
        );
        propertiesVariableDefinition = localizationManager.newProperties(
                Nodeable.getLocalisationEntite(VariableSnot.class), 
                "definition", 
                FacesContext.getCurrentInstance().getViewRoot().getLocale()
        );
        propertiesDatatypeName = localizationManager.newProperties(
                Nodeable.getLocalisationEntite(DataType.class), 
                Nodeable.ENTITE_COLUMN_NAME, 
                FacesContext.getCurrentInstance().getViewRoot().getLocale()
        );
        Optional<AbstractDatesFormParam> dateFormParameter = collector.getParametersCollectionEntry(UIDate.PARAMETER_CODE);
        Optional<List<? extends Nodeable>> nodeables = collector.getParametersCollectionEntry(UITreeNode.PARAMETER_CODE);
        variablesAvailables = availablesSynthesisVariablesDAO.getVariables(policyManager.getCurrentUser(), dateFormParameter.orElse(null), nodeables.orElseGet(LinkedList::new))
                .entrySet().stream()
                .map(entry->new VariableJSF(entry.getKey(), entry.getValue()))
                .collect(Collectors.groupingBy(dvu->dvu.getLocalizedDatatypeName()));
    }
//JBP
//.collect(Collectors.groupingBy(dvu->dvu.getLocalizedDatatypeName()));
    /**
     * Adds the all variables.
     *
     * @return the string
     */
    public final String removeAllVariables() {
        variablesAvailables.values().stream()
                .flatMap(k->k.stream())
                .forEach(vjsf->vjsf.setSelected(false));
        return null;
    }

    /**
     * Select variable.
     *
     * @param variableSelected the variable selected
     */
    public final void selectVariable(final VariableJSF variableSelected) {
        variableSelected.setSelected(!variableSelected.selected);
    }

    /**
     * Adds the all variables.
     *
     * @return the string
     */
    public final String addAllVariables() {
        variablesAvailables.values().stream()
                .flatMap(k->k.stream())
                .forEach(vjsf->vjsf.setSelected(true));
        return null;
    }
    
    /**
     *
     * @return
     */
    public Map<String, List<VariableJSF>> getSelectedVariables(){
        return variablesAvailables.values().stream()
                .flatMap(k->k.stream())
                .filter(vjsf->vjsf.selected)
                .collect(Collectors.groupingBy(vjsf->vjsf.getLocalizedDatatypeName()));
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isStepValid() {
        return variablesAvailables.values().stream()
                .flatMap(l->l.stream())
                .anyMatch(vjsf->vjsf.selected);
    }

    /**
     *
     * @return
     */
    public Map<String, List<VariableJSF>> getVariablesAvailables() {
        return variablesAvailables;
    }

    /**
     * The Class VariableJSF.
     */
    public class VariableJSF {

        /**
         * The selected @link(boolean).
         */
        boolean selected = false;
        /**
         * The variable @link(VariableACBB).
         */
        DatatypeVariableUniteSnot dvu;
        List<Long> nodeIds = new LinkedList();

        /**
         * Instantiates a new variable jsf.
         * @param variable the variable
         * @param nodeIds
         * @param nodes
         * @param datatype
         */
        public VariableJSF(final DatatypeVariableUniteSnot dvu, List<Long> nodeIds) {
            super();
            this.dvu = dvu;
            this.nodeIds = nodeIds;
        }

        /**
         * @return the datatype
         */
        public String getDatatype() {
            return getLocalizedDatatypeName();
        }

        /**
         * Gets the localized name.
         *
         * @return the localized name
         */
        public String getLocalizedVariableName() {
            return  propertiesVariableName.getProperty(dvu.getVariable().getName(), dvu.getVariable().getName());
        }

        /**
         * Gets the localized name.
         *
         * @return the localized name
         */
        public String getLocalizedDatatypeName() {
            return propertiesDatatypeName.getProperty(dvu.getDatatype().getName(), dvu.getDatatype().getName());
        }

        /**
         * Gets the localized name.
         *
         * @return the localized name
         */
        public String getLocalizedVariableDefinition() {
            return propertiesVariableDefinition.getProperty(dvu.getVariable().getDefinition(), dvu.getVariable().getDefinition());
        }

        /**
         * Gets the selected.
         *
         * @return the selected
         */
        public boolean getSelected() {
            return this.selected;
        }

        /**
         * Gets the variable.
         *
         * @return the variable
         */
        public Variable getVariable() {
            return dvu.getVariable();
        }

        /**
         * Sets the selected.
         *
         * @param selected the new selected
         */
        public final void setSelected(final boolean selected) {
            this.selected = selected;
        }

        /**
         *
         * @return
         */
        public DatatypeVariableUniteSnot getDvu() {
            return dvu;
        }

        /**
         *
         * @param dvu
         */
        public void setDvu(DatatypeVariableUniteSnot dvu) {
            this.dvu = dvu;
        }

        /**
         *
         * @return
         */
        public List<Long> getNodeIds() {
            return nodeIds;
        }

        /**
         *
         * @param nodeIds
         */
        public void setNodeIds(List<Long> nodeIds) {
            this.nodeIds = nodeIds;
        }
    }
    
}
