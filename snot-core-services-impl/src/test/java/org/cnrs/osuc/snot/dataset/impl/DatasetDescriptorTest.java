/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset.impl;

import org.inra.ecoinfo.utils.DatasetDescriptor;

/**
 * 
 * @author ptcherniati
 */
public class DatasetDescriptorTest extends DatasetDescriptor {

    /**
     *
     */
    public String frequence;

    /**
     *
     */
    public String entete;

    /**
     *
     */
    public String sites;

    /**
     *
     */
    public String variableName;

    /**
     *
     */
    public DatasetDescriptorTest() {
    }

    /**
     *
     * @return
     */
    public String getFrequence() {
        return this.frequence;
    }

    /**
     *
     * @param frequence
     */
    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    /**
     *
     * @return
     */
    public String getEntete() {
        return this.entete;
    }

    /**
     *
     * @param entete
     */
    public void setEnTete(String entete) {
        this.entete = entete;
    }

    /**
     *
     * @return
     */
    public String getSites() {
        return this.sites;
    }

    /**
     *
     * @param sites
     */
    public void setSites(String sites) {
        this.sites = sites;
    }

    /**
     *
     * @return
     */
    public String getVariableName() {
        return this.variableName;
    }

    /**
     *
     * @param variableName
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

}
