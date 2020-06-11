package org.cnrs.osuc.snot.mga.configurator;

import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.mga.configurator.AbstractMgaDisplayerConfiguration;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.refdata.Refdata;
import org.inra.ecoinfo.refdata.theme.Theme;

/**
 * 
 * @author ryahiaoui
 */
public class MgaDisplayerConfiguration extends AbstractMgaDisplayerConfiguration {

    /**
     *
     */
    public MgaDisplayerConfiguration() {

//        JBP test
        
        this.addColumnNamesForInstanceType(SiteSnot.class, new String[] {
                "PROPERTY_COLUMN_LOCALISATION_NAME", "PROPERTY_COLUMN_STATION_NAME",
                "PROPERTY_COLUMN_LOCALISATION_NAME", "PROPERTY_COLUMN_STATION_NAME",
                "PROPERTY_COLUMN_PLATEFORM_NAME", "PROPERTY_COLUMN_PLATEFORM_NAME",
                "PROPERTY_COLUMN_SITE_DEFAULT" });

//        this.addColumnNamesForInstanceType(Theme.class, new String[] {
//                "PROPERTY_COLUMN_THEME_NAME", "PROPERTY_COLUMN_THEME_NAME",
//                "PROPERTY_COLUMN_THEME_NAME", "PROPERTY_COLUMN_THEME_NAME",
//                "PROPERTY_COLUMN_THEME_NAME", "PROPERTY_COLUMN_THEME_NAME" });

        this.addColumnNamesForInstanceType(DataType.class, new String[] {
                "PROPERTY_COLUMN_DATATYPE_NAME", "PROPERTY_COLUMN_DATATYPE_NAME",
                "PROPERTY_COLUMN_DATATYPE_NAME", "PROPERTY_COLUMN_DATATYPE_NAME",
                "PROPERTY_COLUMN_DATATYPE_NAME", "PROPERTY_COLUMN_DATATYPE_NAME" });

        this.addColumnNamesForInstanceType(VariableSnot.class, new String[] {
                "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME",
                "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME",
                "PROPERTY_COLUMN_VARIABLE_NAME" });

        this.addColumnNamesForInstanceType(DatatypeVariableUniteSnot.class, new String[] {
                "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME",
                "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME",
                "PROPERTY_COLUMN_VARIABLE_NAME", "PROPERTY_COLUMN_VARIABLE_NAME" });

        this.addColumnNamesForInstanceType(Refdata.class, new String[] {
                "PROPERTY_COLUMN_REFDATA_NAME", "PROPERTY_COLUMN_REFDATA_NAME",
                "PROPERTY_COLUMN_REFDATA_NAME", "PROPERTY_COLUMN_REFDATA_NAME",
                "PROPERTY_COLUMN_REFDATA_NAME", "PROPERTY_COLUMN_REFDATA_NAME" });

    }

}
