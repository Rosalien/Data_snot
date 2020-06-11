package org.cnrs.osuc.snot.dataset;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;


/**
 * @author philippe
 * 
 */
public interface IRequestProperties extends IRequestPropertiesSnot {

    /**
     * <p> this function must to be implemented to add a date to a map with waiting dates.</p>
     *
     * @param date
     * @param noLigne
     * @throws BadExpectedValueException
     */
    void addDate(LocalDateTime date, long noLigne) throws BadExpectedValueException;

    /**
     * <p> get the value of the comment reading in from the header</p>
     *
     * @return
     */
    String getCommentaire();

    /**
     * <p> get the value of the UTC start date reading in from the header</p>
     *
     * @return
     */
    LocalDateTime getDatedeDebut();

    /**
     * <p> get the value of the UTC end date reading in from the header</p>
     *
     * @return
     */
    LocalDateTime getDateDeFin();

    /**
     * <p> get the value of the frequency reading in from the header</p>
     *
     * @return
     */
    String getFrequence();

    /**
     * <p> get the value of the site reading in the header</p>
     *
     * @return
     */
    SiteSnot getSite();

    /**
     * <p>get the contents of the column names header line</p>
     *
     * @return
     */
    String[] getColumnNames();

    /**
     * <p>get the contents of the units names  header line</p>
     *
     * @return
     */
    String[] getuniteNames();

    /**
     * <p> this function must to be implemented to initialize a map with waiting dates.</p>
     */
    void initDate();

    /**
     * <p> set the value of the comment reading in from the header</p>
     *
     * @param commentaire
     */
    void setCommentaire(String commentaire);

    /**
     * <p> set the value of the UTC start date reading in from the header</p>
     *
     * @param dateDeDebut
     */
    void setDateDeDebut(LocalDateTime dateDeDebut);

    /**
     * <p> set the value of the UTC end date reading in from the header</p>
     *
     * @param dateDeFin
     */
    void setDateDeFin(LocalDateTime dateDeFin);

    /**
     * <p> set the value of the frequency reading in from the header</p>
     *
     * @param frequence
     */
    void setFrequence(String frequence);

    /**
     * <p> set the value of the site reading in from the header</p>
     *
     * @param site
     */
    void setSite(SiteSnot site);

    /**
     * <p> this function must to be implemented test date into a map with waiting dates.</p>
     *
     * @param badsFormatsReport
     */
    void testDates(BadsFormatsReport badsFormatsReport);

    /**
     * <p>set the contents of the column names header line</p>
     *
     * @param columnNames
     * @param columns
     */
    void setColumnNames(String[] columnNames, List<Column> columns);

    /**
     * <p>set the contents of the units names header line</p>
     *
     * @param uniteNames
     */
    void setUniteNames(String[] uniteNames);

    /**
     *
     * @param i
     * @return
     */
    Column getColumn(int i);

    /**
     * <p> this function is call to set a Map<String,DatatypeVariableUnite> for the current datatype<p>
     * <p> the key of th map is the variable name</p>
     *
     * @param variableTypeDeDonnees
     */
    void setVariableTypeDonnees(Map<String, DatatypeVariableUniteSnot> variableTypeDeDonnees);

    /**
     * <p>get the contents of the column names from datasetDescriptor</p>
     *
     * @return  
     */
    List<String> getLstDatasetNameColumns();

    /**
     * <p> this function is call to get a Map<String,DatatypeVariableUniteSnot> for the current datatype<p>
     * <p> the key of th map is the variable name</p>
     *
     * @return  
     */
    Map<String, DatatypeVariableUniteSnot> getVariableTypeDonnees();

    /**
     * <p>set the contents of the column names from datasetDescriptor</p>
     *
     * @param lstDatasetNameColumns
     */
    void setLstDatasetNameColumns( List<String> lstDatasetNameColumns); 

    /**
     *
     * @return
     */
    String getDateFormat();

}
