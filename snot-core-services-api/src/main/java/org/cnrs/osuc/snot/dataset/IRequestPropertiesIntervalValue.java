/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.
 */
package org.cnrs.osuc.snot.dataset;

import java.util.Map;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.inra.ecoinfo.utils.DatasetDescriptor;

/**
 *
 * @author ptcherniati
 */
public interface IRequestPropertiesIntervalValue {

    /**
     * <p>
     * get the contents of the min values in header line for undefined
     * columns</p>
     *
     * @return
     */
    String[] getValeursMin();

    /**
     * <p>
     * get the contents of the max values in header line for undefined
     * columns</p>
     *
     * @return
     */
    String[] getValeursMax();

    /**
     * <p>
     * get the contents of the max values in header line</p>
     *
     * @return
     */
    String[] getValeurMaxInFile();

    /**
     * <p>
     * get the contents of the min values in header line</p>
     *
     * @return
     */
    String[] getValeurMinInFile();

    /**
     * <p>
     * set the contents of the max values in header line</p>
     *
     * @param valeurMaxInFile
     */
    void setValeurMaxInFile(String[] valeurMaxInFile);

    /**
     * <p>
     * set the contents of the min values in header line</p>
     *
     * @param valeurMinInFile
     */
    void setValeurMinInFile(String[] valeurMinInFile);

    /**
     * <p>
     * set the contents of the min values in header line for undefined
     * columns</p>
     *
     * @param valeursMin
     */
    void setValeursMin(String[] valeursMin);

    /**
     * <p>
     * set the contents of the max values in header line for undefined
     * columns</p>
     *
     * @param valeursMax
     */
    void setValeursMax(String[] valeursMax);

    /**
     *
     * @return
     */
    String[] getColumnNames();

    /**
     *
     * @return
     */
    String[] getuniteNames();

    /**
     *
     * @return
     */
    String[] getJeu();

    /**
     *
     * @param <T>
     * @return
     */
    <T extends ExpectedColumn> Map<String, T> getExpectedColumns();

    /**
     *
     * @param <T>
     * @param columnName
     * @param datasetDescriptor
     * @return
     */
    <T extends ExpectedColumn> T addExpectedColumn(String columnName, DatasetDescriptor datasetDescriptor);

    SiteSnot getSite();
}
