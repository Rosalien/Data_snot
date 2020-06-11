/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.depth;

import org.cnrs.osuc.snot.extraction.jsf.depth.IAvailablesSynthesisDepthsDAO;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.extraction.jsf.date.UIDate;
import org.cnrs.osuc.snot.extraction.jsf.nodeable.UITreeNode;
import org.cnrs.osuc.snot.extraction.jsf.variable.UIVariable;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author ptchernia
 */
public class UIDepth implements IStepBuilder<List<Integer>> {

    /**
     *
     */
    public static final String PARAMETER_CODE = "depths";
    List<Integer> depthsAvailables = new LinkedList();
    IAvailablesSynthesisDepthsDAO availablesSynthesisDepthsDAO;
    boolean interval = true;
    int min = 0;
    int max = 0;
    int depthMin = 0;
    int depthMax = 0;
    private SortedSet<Integer> selectedDepthArray = new TreeSet<>();
    private ICollector collector;

    /**
     *
     * @param availablesSynthesisDepthsDAO
     */
    public UIDepth(IAvailablesSynthesisDepthsDAO availablesSynthesisDepthsDAO) {
        this.availablesSynthesisDepthsDAO = availablesSynthesisDepthsDAO;
    }

    /**
     *
     * @param collector
     */
    @Override
    public void addCollectToParameter(ICollector collector) {
        collector.addParameterCollectionEntry(PARAMETER_CODE, getSelectedDepths());
    }

    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
        this.collector = collector;
        Optional<AbstractDatesFormParam> dateFormParameter = collector.getParametersCollectionEntry(UIDate.PARAMETER_CODE);
        Optional<List<? extends Nodeable>> nodeables = collector.getParametersCollectionEntry(UITreeNode.PARAMETER_CODE);
        Optional<List<DatatypeVariableUniteSnot>> dvus = collector.getParametersCollectionEntry(UIVariable.PARAMETER_CODE);
        depthsAvailables = availablesSynthesisDepthsDAO.getDepths(policyManager.getCurrentUser(), dateFormParameter.orElse(null), nodeables.orElseGet(LinkedList::new), dvus.orElseGet(LinkedList::new))
                .stream()
                .filter(d -> d != null)
                .sorted(Integer::compare)
                .collect(Collectors.toList());
        depthMin = min = depthsAvailables.stream()
                .min(Integer::compare)
                .orElse(0);
        depthMax = max = depthsAvailables.stream()
                .max(Integer::compare)
                .orElse(0);
        ;
    }

    /**
     *
     * @return
     */
    public SortedSet<Integer> getSelectedDepths() {
        if (interval) {
            return depthsAvailables.stream()
                    .filter(d -> d >= depthMin && d <= depthMax)
                    .collect(Collectors.toCollection(TreeSet::new));
        } else {
            return selectedDepthArray;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isStepValid() {
        if (collector == null) {
            return false;
        }
        Optional<AbstractDatesFormParam> dateFormParameter = collector.getParametersCollectionEntry(UIDate.PARAMETER_CODE);
        if (dateFormParameter.map(dfp -> Constantes.JOURNALIER.equals(dfp.getRythme())).orElse(false)) {
            return true;
        }
        if (interval) {
            return depthsAvailables.stream()
                    .filter(d -> d != null)
                    .anyMatch(d -> d >= depthMin && d <= depthMax);

        } else {
            return !selectedDepthArray.isEmpty();
        }
    }

    /**
     *
     * @return
     */
    public int getDepthMin() {
        return depthMin;
    }

    /**
     *
     * @param depthMin
     */
    public void setDepthMin(int depthMin) {
        this.depthMin = depthMin;
    }

    /**
     *
     * @return
     */
    public int getDepthMax() {
        return depthMax;
    }

    /**
     *
     * @param depthMax
     */
    public void setDepthMax(int depthMax) {
        this.depthMax = depthMax;
    }

    /**
     *
     * @return
     */
    public int getInterval() {
        return interval ? 0 : 1;
    }

    /**
     *
     * @param interval
     */
    public void setInterval(int interval) {
        this.interval = interval == 0;
    }

    /**
     *
     * @param event
     */
    public void onChange(TabChangeEvent event) {
        interval = !interval;
    }

    /**
     *
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @return
     */
    public String[] getSelectedDepthArray() {
        return selectedDepthArray.stream()
                .map(i -> i.toString())
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    /**
     *
     * @param selectedDepthArray
     */
    public void setSelectedDepthArray(String[] selectedDepthArray) {
        this.selectedDepthArray = Stream.of(selectedDepthArray)
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toCollection(TreeSet::new));
        interval = false;
    }

    /**
     *
     * @return
     */
    public List<Integer> getDepthsAvailables() {
        return depthsAvailables;
    }

}
