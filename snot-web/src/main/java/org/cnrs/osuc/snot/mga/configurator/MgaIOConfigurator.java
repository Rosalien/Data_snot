package org.cnrs.osuc.snot.mga.configurator;

import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
//import org.cnrs.osuc.snot.extraction.fluxmeteo.IFluxParameter;
//import org.cnrs.osuc.snot.extraction.fluxmeteo.impl.FluxExtractor;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfiguration;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator;
import static org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator.*;
import org.inra.ecoinfo.mga.enums.Activities;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.datatype.DataType;
//import org.inra.ecoinfo.refdata.datatypevariableunite.DatatypeVariableUnite;
import org.inra.ecoinfo.refdata.refdata.Refdata;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.refdata.variable.Variable;

/**
 *
 * @author yahiaoui
 */
public class MgaIOConfigurator extends AbstractMgaIOConfigurator {

    /**
     * Configuration Zero
     */
    final static Integer[] ENTRY_ORDER_0_1_2_3 = new Integer[]{0, 1, 2, 3};
    final static Integer[] ENTRY_ORDER_0_3 = new Integer[]{0, 3};
    final static Integer[] ENTRY_ORDER_0_2 = new Integer[]{0, 2};
    final static Integer[] ENTRY_ORDER_0_1 = new Integer[]{0, 1};
    final static Integer[] ENTRY_ORDER_1_0 = new Integer[]{1, 0};
    final static Integer[] ENTRY_ORDER_1_2 = new Integer[]{1, 2};
    final static Integer[] ENTRY_ORDER_2_0 = new Integer[]{2, 0};
    final static Integer[] ENTRY_ORDER_2_1 = new Integer[]{2, 1};
    final static Integer[] ENTRY_ORDER_2_3 = new Integer[]{2, 3};
    final static Integer[] ENTRY_ORDER_0_2_3= new Integer[]{0,2,3};
    final static Integer[] ENTRY_ORDER_3 = new Integer[]{3};
    final static Integer[] ENTRY_ORDER_2 = new Integer[]{2};
    final static Integer[] ENTRY_ORDER_3_0_1 = new Integer[]{3,0,1};
    
    private static final Integer[] EXTRACTION_ORDER_0_1 = new Integer[]{0, 1};
    final static Integer[] ENTRY_ORDER_REF = new Integer[]{0};
    private static final Integer SYNTHESIS_CONFIGURATION_500 = 500;
    private static final Integer EXTRACTION_CONFIGURATION_100 = 100;
    private static final Integer DATASET_CONFIGURATION_200 = 200;

    final static Class<INodeable>[] ENTRY_INSTANCE_STD = new Class[]{
        SiteSnot.class,
        Theme.class,
        DataType.class
    };
    final static Class<INodeable>[] ENTRY_INSTANCE_SD = new Class[]{
        SiteSnot.class,
        DataType.class
    };

    final static Class<INodeable>[] ENTRY_INSTANCE_SDV = new Class[]{
        SiteSnot.class,
        DataType.class,
        Variable.class
    };
    
    final static Class<INodeable>[] ENTRY_INSTANCE_D = new Class[]{
        DataType.class,};

    final static Class<INodeable>[] ENTRY_INSTANCE_DVU = new Class[]{
        DatatypeVariableUniteSnot.class
    };

    final static Class<INodeable>[] ENTRY_INSTANCE_DV = new Class[]{
        DataType.class,
        Variable.class
    };

    final static Class<INodeable>[] ENTRY_INSTANCE_DS = new Class[]{
        DataType.class,
        SiteSnot.class
    };

    final static Class<INodeable>[] ENTRY_INSTANCE_STDV = new Class[]{
        SiteSnot.class,
        Theme.class,
        DataType.class,
        DatatypeVariableUniteSnot.class
    };

    final static Class<INodeable>[] ENTRY_INSTANCE_REF = new Class[]{
        Refdata.class
    };

    final static Activities[] ACTIVITIES_SAPDSE
            = new Activities[]{
                Activities.synthese,
                Activities.administration,
                Activities.publication,
                Activities.depot,
                Activities.suppression,
                Activities.extraction
            };

    final static Activities[] ACTIVITIES_SE
            = new Activities[]{
                Activities.synthese,
                Activities.extraction
            };

    final static Activities[] ACTIVITIES_A
            = new Activities[]{
                Activities.associate
            };

    final static Activities[] ACTIVITIES_REF
            = new Activities[]{
                Activities.edition,
                Activities.publication,
                Activities.suppression,
                Activities.telechargement,
                Activities.administration};

    /**
     *
     */
    public MgaIOConfigurator() {
        super(new MgaDisplayerConfiguration() {
        });
    }

    /**
     *
     */
    @Override
    protected void initConfigurations() {
        /**
         * Configuration Zero
         */
        boolean includeAncestor = true;
        WhichTree whichTreeDataSet = WhichTree.TREEDATASET;
        Class stickyLeafDatasetRights = DatatypeVariableUniteSnot.class;

        /**
         * Extraction par variable
         */
        /**
         * Droit sur les données
         */
        Configuration configDatasetRights
                = new Configuration(
                        DATASET_CONFIGURATION_RIGHTS,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STDV,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_1_2_3,
                        includeAncestor,
                        whichTreeDataSet,
                        stickyLeafDatasetRights,
                        false);

        /**
         * Gestion des données expérimentales
         */
        Configuration configDataset
                = new Configuration(
                        DATASET_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STD,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_1_2_3,
                        includeAncestor,
                        whichTreeDataSet,
                        null,
                        true);
        
        Configuration configDataset_200
                = new Configuration(
                        DATASET_CONFIGURATION_200,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_SD,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_2,
                        includeAncestor,
                        whichTreeDataSet,
                        null,
                        true);
        /**
         * Gestion des droits sur les données de référence
         */
        Configuration configRefdataRights
                = new Configuration(REFDATA_CONFIGURATION_RIGHTS,
                        Refdata.class,
                        ENTRY_ORDER_REF,
                        ENTRY_INSTANCE_REF,
                        ACTIVITIES_REF,
                        ENTRY_ORDER_REF,
                        includeAncestor,
                        WhichTree.TREEREFDATA,
                        null,
                        false);

        /**
         * Gestion des données de référence
         */
        Configuration configRefdata
                = new Configuration(REFDATA_CONFIGURATION,
                        Refdata.class,
                        ENTRY_ORDER_REF,
                        ENTRY_INSTANCE_REF,
                        ACTIVITIES_REF,
                        ENTRY_ORDER_REF,
                        includeAncestor,
                        WhichTree.TREEREFDATA,
                        null,
                        true);
        
        /**
         * Synthèse
         */
        //Synthèse par défaut
        Configuration configSynthesis
                = new Configuration(
                        SYNTHESIS_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_SD,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_2,
                        includeAncestor,
                        whichTreeDataSet,
                        null,
                        true);

        // Synthèse par datatype
        AbstractMgaIOConfiguration configSynthesis_500
                = new Configuration(SYNTHESIS_CONFIGURATION_500,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,//ce qui est dans la base
                        ENTRY_INSTANCE_DVU,
                        ACTIVITIES_SE,
                        ENTRY_ORDER_2,
                        includeAncestor,
                        whichTreeDataSet,
                        null,
                        true);

        configSynthesis.setSkeletonBuilder("synthesisManager");
        Activities[] activitiesAssociate
                = new Activities[]{
                    Activities.associate
                };

        Configuration configAssociate
                = new Configuration(
                        ASSOCIATE_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STD,
                        ACTIVITIES_A,
                        ENTRY_ORDER_0_1_2_3,
                        includeAncestor,
                        whichTreeDataSet,
                        null,
                        false);
        
        // Extraction par variable      
//        AbstractMgaIOConfiguration configExtraction_100
//                = new Configuration(
//                        EXTRACTION_CONFIGURATION_100,
//                        DataType.class,
//                        ENTRY_ORDER_0_1_2_3,
//                        ENTRY_INSTANCE_STD,
//                        new Activities[]{Activities.extraction},
//                        ENTRY_ORDER_3_0_1,
//                        includeAncestor,
//                        whichTreeDataSet,
//                        null,
//                        true);
        
       
        /**
         * ------------------------------------------------------------------------------------
         * *
         */
        getConfigurations().computeIfAbsent(DATASET_CONFIGURATION_RIGHTS, k -> configDatasetRights);
        getConfigurations().computeIfAbsent(REFDATA_CONFIGURATION_RIGHTS, k -> configRefdataRights);
        getConfigurations().computeIfAbsent(DATASET_CONFIGURATION, k -> configDataset);
        getConfigurations().computeIfAbsent(DATASET_CONFIGURATION_200, k -> configDataset_200);
        
        
        getConfigurations().computeIfAbsent(REFDATA_CONFIGURATION, k -> configRefdata);
        getConfigurations().computeIfAbsent(ASSOCIATE_CONFIGURATION, k -> configAssociate);
        getConfigurations().computeIfAbsent(SYNTHESIS_CONFIGURATION, k -> configSynthesis);
        getConfigurations().computeIfAbsent(SYNTHESIS_CONFIGURATION_500, k -> configSynthesis_500);
//        getConfigurations().computeIfAbsent(EXTRACTION_CONFIGURATION_100, k -> configExtraction_100);

        /**
         * ------------------------------------------------------------------------------------
         * *
         */
    }

    class Configuration extends AbstractMgaIOConfiguration {

        public Configuration(int codeConfig, Class<? extends INodeable> leafType, Integer[] entryOrder, Class<INodeable>[] entryTypes, Activities[] activities, Integer[] sortOrder, boolean includeAncestor, WhichTree whichTree, Class<? extends INodeable> stickyLeafType, boolean displayColumnNames) {
            super(codeConfig, leafType, entryOrder, entryTypes, activities, sortOrder, includeAncestor, whichTree, stickyLeafType, displayColumnNames);
        }

    }

}
