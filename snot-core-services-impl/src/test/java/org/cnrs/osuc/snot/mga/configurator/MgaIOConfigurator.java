package org.cnrs.osuc.snot.mga.configurator;

import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfiguration;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator;
import static org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator.*;
import org.inra.ecoinfo.mga.enums.Activities;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.refdata.Refdata;
import org.inra.ecoinfo.refdata.theme.Theme;

/**
 *
 * @author yahiaoui
 */
public class MgaIOConfigurator extends AbstractMgaIOConfigurator {

    /**
     * Configuration Zero
     */
    final static Integer[] ENTRY_ORDER_0_1_2_3 = new Integer[]{0, 1, 2, 3};
    final static Integer[] ENTRY_ORDER_REF = new Integer[]{0};
    final static Class<INodeable>[] ENTRY_INSTANCE_STD = new Class[]{
            SiteSnot.class,
            Theme.class,
            DataType.class
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

        Class stickyLeafDatasetRights = DatatypeVariableUniteSnot.class;
        Configuration configDatasetRights
                = new Configuration(
                        DATASET_CONFIGURATION_RIGHTS,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STDV,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_1_2_3,
                        true,
                        WhichTree.TREEDATASET,
                        stickyLeafDatasetRights,
                        false);
        Configuration configDataset
                = new Configuration(
                        DATASET_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STD,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_1_2_3,
                        true,
                        WhichTree.TREEDATASET,
                        null,
                        true);

        Configuration configRefdataRights
                = new Configuration(REFDATA_CONFIGURATION_RIGHTS,
                        Refdata.class,
                        ENTRY_ORDER_REF,
                        ENTRY_INSTANCE_REF,
                        ACTIVITIES_REF,
                        ENTRY_ORDER_REF,
                        true,
                        WhichTree.TREEREFDATA,
                        null,
                        false);

        Configuration configRefdata
                = new Configuration(REFDATA_CONFIGURATION,
                        Refdata.class,
                        ENTRY_ORDER_REF,
                        ENTRY_INSTANCE_REF,
                        ACTIVITIES_REF,
                        ENTRY_ORDER_REF,
                        true,
                        WhichTree.TREEREFDATA,
                        null,
                        true);

        Configuration configSynthesis
                = new Configuration(SYNTHESIS_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STD,
                        ACTIVITIES_SAPDSE,
                        ENTRY_ORDER_0_1_2_3,
                        true,
                        WhichTree.TREEDATASET,
                        null,
                        true);
        //configSynthesis.setSkeletonBuilder("synthesisManager");

        Configuration configAssociate
                = new Configuration(
                        ASSOCIATE_CONFIGURATION,
                        DataType.class,
                        ENTRY_ORDER_0_1_2_3,
                        ENTRY_INSTANCE_STD,
                        ACTIVITIES_A,
                        ENTRY_ORDER_0_1_2_3,
                        true,
                        WhichTree.TREEDATASET,
                        null,
                        false);

        /**
         * ------------------------------------------------------------------------------------
         * *
         */
        getConfigurations().computeIfAbsent(DATASET_CONFIGURATION_RIGHTS, k -> configDatasetRights);
        getConfigurations().computeIfAbsent(REFDATA_CONFIGURATION_RIGHTS, k -> configRefdataRights);
        getConfigurations().computeIfAbsent(DATASET_CONFIGURATION, k -> configDataset);
        getConfigurations().computeIfAbsent(REFDATA_CONFIGURATION, k -> configRefdata);
        getConfigurations().computeIfAbsent(ASSOCIATE_CONFIGURATION, k -> configAssociate);
        getConfigurations().computeIfAbsent(SYNTHESIS_CONFIGURATION, k -> configSynthesis);

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
