/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf;

import java.util.Optional;
import org.inra.ecoinfo.extraction.IParameter;

/**
 *
 * @author ptchernia
 */
public interface ICollector {

    /**
     * <p>
     * add a copy of all entries in <i>parametersCollection</i> to
     * <i>parameter.parameter</i></p>
     *
     * @param parameter
     */
    void setParameters(IParameter parameter);

    /**
     *
     * @return
     */
    IParameter getParameters();

    /**
     *
     * @param <T>
     * @param entryKey
     * @return
     */
    <T> Optional<T> getParametersCollectionEntry(String entryKey);

    /**
     *
     * @param <T>
     * @param entryKey
     * @param entry
     */
    <T> void addParameterCollectionEntry(String entryKey, T entry);

    /**
     *
     * @return
     */
    String getCommentExtraction();
}
