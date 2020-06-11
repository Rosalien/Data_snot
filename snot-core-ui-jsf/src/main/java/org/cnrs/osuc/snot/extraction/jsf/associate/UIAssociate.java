/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.extraction.jsf.associate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import org.inra.ecoinfo.dataset.config.impl.DataTypeDescription;
import org.inra.ecoinfo.filecomp.IFileCompManager;
import org.inra.ecoinfo.filecomp.entity.FileComp;
import org.inra.ecoinfo.filecomp.jsf.ItemFile;
import org.cnrs.osuc.snot.extraction.jsf.ICollector;
import org.cnrs.osuc.snot.extraction.jsf.IStepBuilder;
import org.cnrs.osuc.snot.extraction.jsf.date.AbstractDatesFormParam;
import org.cnrs.osuc.snot.extraction.jsf.date.UIDate;
import org.cnrs.osuc.snot.extraction.jsf.variable.UIVariable;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.localization.entity.Localization;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.utils.IntervalDate;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tcherniatinsky
 */
public class UIAssociate implements IStepBuilder<List<Integer>> {

    /**
     *
     */
    public static final String PARAMETER_CODE = FileComp.class.getSimpleName();

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(UIAssociate.class);

    /**
     *
     */
    public Map<String, String> localizedDatatypes = new HashMap();

    private final Map<Long, AssociateJSF> associatesAvailables = new HashMap<>();
    private long idAssociateSelected;
    private final Map<Long, AssociateJSF> associatesSelected = new HashMap<>();
    private ILocalizationManager localizationManager;
    private IFileCompManager fileCompManager;

    /**
     *
     * @param fileCompManager
     */
    public UIAssociate(IFileCompManager fileCompManager) {
        this.fileCompManager = fileCompManager;
    }

    /**
     *
     * @return
     */
    public String addAllAssociates() {
        final Map<Long, AssociateJSF> associatesSelected = this.associatesSelected;
        associatesSelected.clear();
        associatesAvailables.values().stream().map((associate) -> {
            associatesSelected.put(associate.getFile().getFileComp().getId(), associate);
            return associate;
        }).forEach((associate) -> {
            associate.setSelected(Boolean.TRUE);
        });
        return null;
    }

    /**
     *
     * @return @throws BusinessException
     */
    public List<AssociateJSF> getAssociatesAvailables() throws BusinessException {
        assert associatesAvailables != null : "null associates";
        return new LinkedList<>(associatesAvailables.values());
    }

    private Locale getLocale() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        return locale;
    }

    /**
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public String removeAssociate(Long id) throws BusinessException {
        idAssociateSelected = id;
        final AssociateJSF associateSelected = associatesAvailables.get(idAssociateSelected);
        associatesSelected.remove(idAssociateSelected);
        associateSelected.setSelected(Boolean.FALSE);
        return null;
    }

    /**
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public String selectAssociate(Long id) throws BusinessException {
        idAssociateSelected = id;
        final AssociateJSF associateSelected = associatesAvailables.get(idAssociateSelected);
        if (!associateSelected.getSelected()) {
            associatesSelected.remove(idAssociateSelected);
            associateSelected.setSelected(Boolean.FALSE);
        } else {
            associatesSelected.put(idAssociateSelected, associateSelected);
            associateSelected.setSelected(Boolean.TRUE);
        }
        return null;
    }

    Map<String, Properties> getPropertiesForDescriptions() {
        Map<String, Properties> descriptionProperties = new HashMap<>();
        Localization.getLocalisations().stream().filter((locale) -> !(Localization.getDefaultLocalisation().equals(locale))).forEach((locale) -> {
            descriptionProperties.put(locale, localizationManager.newProperties(FileComp.NAME_ENTITY_JPA, FileComp.ENTITY_DESCRIPTION_COLUM_NAME, new Locale(locale)));
        });
        return descriptionProperties;
    }

    /**
     *
     * @return
     */
    public Map<Long, AssociateJSF> getAssociatesSelected() {
        return associatesSelected;
    }

    /**
     *
     * @return
     */
    public List<AssociateJSF> getListAssociatesSelected() {
        final List<AssociateJSF> associates = new LinkedList<>();
        associatesSelected.values().stream().forEach((associate) -> {
            associates.add(associate);
        });
        return associates;
    }

    /**
     *
     * @return
     */
    public long getIdAssociateSelected() {
        return idAssociateSelected;
    }

    /**
     *
     * @param idAssociateSelected
     */
    public void setIdAssociateSelected(long idAssociateSelected) {
        this.idAssociateSelected = idAssociateSelected;
    }

    /**
     *
     * @param collector
     */
    @Override
    public void addCollectToParameter(ICollector collector) {
        collector.addParameterCollectionEntry(FileComp.class.getSimpleName(), getListAssociates());
    }

    private Set<FileComp> getListAssociates() {
        return associatesSelected.values().stream()
                .map(associate -> associate.getFile().getFileComp())
                .collect(Collectors.toSet());
    }

    /**
     *
     * @param localizationManager
     * @param policyManager
     * @param collector
     */
    @Override
    public void init(ILocalizationManager localizationManager, IPolicyManager policyManager, ICollector collector) {
        this.localizationManager = localizationManager;
        Locale locale = getLocale();
        this.localizedDatatypes = localizationManager.newProperties(Nodeable.getLocalisationEntite(DataTypeDescription.class), Nodeable.ENTITE_COLUMN_NAME, locale)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Utils.createCodeFromString((String) entry.getKey()),
                        entry -> (String) entry.getValue())
                );
        associatesAvailables.clear();
        associatesSelected.clear();
        Optional<List<Long>> nodeIds = collector.getParametersCollectionEntry(UIVariable.PARAMETER_CODE_NODEDATASET);
        Optional<AbstractDatesFormParam> dateFormParameter = collector.getParametersCollectionEntry(UIDate.PARAMETER_CODE);
        List<IntervalDate> intervalsDate = dateFormParameter.map(dfp -> dfp.intervalsDate()).orElseGet(LinkedList::new);
        final List<FileComp> files = fileCompManager.getFileCompFromPathesAndIntervalsDate(nodeIds.orElseGet(LinkedList::new), intervalsDate);
        Map<String, Properties> propertiesForDescriptions = getPropertiesForDescriptions();
        files.stream()
                .filter((fileComp) -> (!associatesAvailables.keySet().contains(fileComp.getId())))
                .map(fileComp -> new ItemFile(fileComp, propertiesForDescriptions, null, locale.getCountry()))
                .forEach((file) -> {
                    final AssociateJSF associateVO = new AssociateJSF(file);
                    associatesAvailables.put(file.getFileComp().getId(), associateVO);
                    if (associateVO.getMandatory()) {
                        associatesSelected.put(file.getFileComp().getId(),
                                associateVO);
                    }
                });
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isStepValid() {
        return true;
    }

    /**
     *
     */
    public static class AssociateJSF {

        private ItemFile file;
        private String localizedFileName;
        private Boolean selected = Boolean.FALSE;
        private boolean mandatory;

        /**
         *
         * @param file
         */
        public AssociateJSF(ItemFile file) {
            super();
            this.file = file;
            if (file != null) {
                this.localizedFileName = file.getOriginalFileName();
                this.mandatory = file.getMandatory();
                this.selected = file.getMandatory();
            }
        }

        /**
         *
         * @return
         */
        public ItemFile getFile() {
            return file;
        }

        /**
         *
         * @return
         */
        public String getLocalizedFileName() {
            return localizedFileName;
        }

        /**
         *
         * @return
         */
        public boolean getMandatory() {
            return mandatory;
        }

        /**
         *
         * @return
         */
        public Boolean getSelected() {
            return selected;
        }

        /**
         *
         * @param file
         */
        public void setFile(ItemFile file) {
            this.file = file;
        }

        /**
         *
         * @param localizedFileName
         */
        public void setLocalizedFileName(String localizedFileName) {
            this.localizedFileName = localizedFileName;
        }

        /**
         *
         * @param mandatory
         */
        public void setMandatory(boolean mandatory) {
            this.mandatory = mandatory;
        }

        /**
         *
         * @param selected
         */
        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

    }

}
