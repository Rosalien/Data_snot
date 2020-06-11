/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.inra.ecoinfo.extraction.IParameter;

/**
 *
 * @author ptchernia
 */
public class Collector implements ICollector {

    /**
     *
     */
    protected IParameter parameter;

    /**
     *
     */
    protected List<IStepBuilder> stepBuilders;

    /**
     *
     */
    protected String commentExtraction;

    /**
     *
     * @param stepBuilders
     */
    public Collector(List<IStepBuilder> stepBuilders) {
        this.stepBuilders = stepBuilders;
    }

    @Override
    public void setParameters(IParameter parameter) {
        this.parameter = parameter;
        Map<String, Object> parametersMap = Optional.ofNullable(parameter)
                .map(p -> p.getParameters())
                .orElseGet(HashMap::new);
        parametersMap.putAll(parameter.getParameters());
    }

    /**
     *
     * @param <T>
     * @param entryKey
     * @return
     */
    @Override
    public <T> Optional<T> getParametersCollectionEntry(String entryKey) {
        if (!Strings.isNullOrEmpty(entryKey)) {
            Object entry = parameter.getParameters().get(entryKey);
            try {
                T entryCasted = (T) parameter.getParameters().get(entryKey);
                return Optional.ofNullable(entryCasted);
            } catch (ClassCastException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     *
     * @param <T>
     * @param entryKey
     * @param entry
     */
    @Override
    public <T> void addParameterCollectionEntry(String entryKey, T entry) {
        if (!Strings.isNullOrEmpty(entryKey)) {
            parameter.getParameters().put(entryKey, entry);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getCommentExtraction() {
        return parameter.getCommentaire();
    }

    /**
     *
     * @param commentExtraction
     */
    public void setCommentExtraction(String commentExtraction) {
        parameter.setCommentaire(commentExtraction);
    }

    /**
     *
     * @return
     */
    @Override
    public IParameter getParameters() {
        stepBuilders.stream()
                .forEach(sb->sb.addCollectToParameter(this));
        return this.parameter;
    }
}
