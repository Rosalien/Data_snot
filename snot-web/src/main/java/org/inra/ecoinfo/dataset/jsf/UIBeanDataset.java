package org.inra.ecoinfo.dataset.jsf;

import java.beans.Transient;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.FetchProfile.Item;
import static org.eclipse.persistence.expressions.ExpressionOperator.currentDate;
import org.inra.ecoinfo.dataset.IDatasetManager;
import org.inra.ecoinfo.dataset.versioning.IVersionManager;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.identification.IUsersManager;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.mga.business.composite.IFlatNode;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.ITreeNode;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.mga.managedbean.UIBeanActionTree;
import org.inra.ecoinfo.notifications.INotificationsManager;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.UncatchedExceptionLogger;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * The Class UIBeanDataset.
 */
@ManagedBean(name = "uiDataset")
@ViewScoped
public class UIBeanDataset implements Serializable {

    /**
     * The Constant serialVersionUID @link(long).
     */
    private static final long serialVersionUID = 1L;
    /**
     * The datasets selection @link(List<Dataset>).
     */
    private List<Dataset> datasetsSelection = new LinkedList<>();
    /**
     * The display dataset panel @link(Boolean).
     */
    private Boolean displayDatasetPanel;
    /**
     * The selected dataset @link(Dataset).
     */
    private Dataset selectedDataset;

    /**
     * The selected version @link(SelectedVersion).
     */
    private SelectedVersion selectedVersion = null;
    private List<SelectedVersion> selectedVersions = new LinkedList();

    /**
     * The dataset current selection @link(IUIBeanDatasetCurrentSelection).
     */
    @ManagedProperty(value = "#{datasetManager}")
    protected IDatasetManager datasetManager;
    /**
     * The notifications manager @link(INotificationsManager).
     */
    @ManagedProperty(value = "#{notificationsManager}")
    protected INotificationsManager notificationsManager;
    /**
     * The security context @link(IPolicyManager).
     */
    @ManagedProperty(value = "#{policyManager}")
    protected IPolicyManager policyManager;
    /**
     * The transaction manager @link(JpaTransactionManager).
     */
    @ManagedProperty(value = "#{transactionManager}")
    protected JpaTransactionManager transactionManager;
    /**
     * The users manager @link(IUsersManager).
     */
    @ManagedProperty(value = "#{usersManager}")
    protected IUsersManager usersManager;
    /**
     * The localisation manager @link(ILocalizationManager).
     */
    @ManagedProperty(value = "#{localisationManager}")
    protected ILocalizationManager localisationManager;

//    @ManagedProperty(value = "#{uiBeanActionTree}")
//    private UIBeanActionTree uiBeanActionTree;
    @ManagedProperty(value = "#{versioningManager}")
    private IVersionManager versionManager;

    @ManagedProperty(value = "#{uiBeanActionTree}")
    protected UIBeanActionTree uiBeanActionTree;
    private List<SelectedVersion> filteredSelections;

    /**
     * Instantiates a new uI bean dataset.
     */
    public UIBeanDataset() {
    }

    /**
     *
     * @param policyManager
     */
    public void setPolicyManager(IPolicyManager policyManager) {
        this.policyManager = policyManager;
    }

    /**
     *
     * @param uiBeanActionTree
     */
    public void setUiBeanActionTree(UIBeanActionTree uiBeanActionTree) {
        this.uiBeanActionTree = uiBeanActionTree;
    }

    /**
     *
     * @return
     */
    public List<SelectedVersion> getSelectedVersions() {
        return selectedVersions;
    }

    public boolean filterByDate(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }

        if (value == null) {
            return false;
        }

        Date dt2 = (Date) filter;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = sdf.format(dt2);
        boolean status = date2.equals(value.toString());
        return status;
    }

    /**
     *
     * @param selectedVersions
     */
    public void setSelectedVersions(List<SelectedVersion> selectedVersions) {
        this.selectedVersions = selectedVersions;
    }

    /**
     *
     * @return @throws BusinessException
     */
    public List<SelectedVersion> getFilteredSelections() throws BusinessException {
        return filteredSelections;
    }

    /**
     *
     * @param fileteredSelections
     * @throws BusinessException
     */
    public void setFilteredSelections(List<SelectedVersion> fileteredSelections) throws BusinessException {
        this.filteredSelections = fileteredSelections;
    }

    /**
     * Delete version.
     *
     * @return the string
     * @throws BusinessException the business exception
     */
    public String deleteVersion() throws BusinessException {
        final VersionFile versionFileSelected = selectedVersion.getVersionFile();
        datasetManager.deleteVersion(versionFileSelected);
        selectedVersions.remove(selectedVersion);
        updateDatasetsSelection();
        if (selectedDataset.getVersions().size() > 1) {
            updateDatasetSelected();
        } else {
            selectedDataset = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":#{p:component('selectionDatatableID')}");
        context.update(":#{p:component('datasetsDetailsPanel')}");
        return null;
    }

    /**
     *
     * @param selectedDataset
     */
    public void setSelectedDataset(Dataset selectedDataset) {
        this.selectedDataset = selectedDataset;
        Map<Long, VersionFile> versions = selectedDataset == null ? new HashMap() : selectedDataset.getVersions();
        this.selectedVersions.clear();
        versions.entrySet().stream()
                .map((entry) -> entry.getValue())
                .map((value) -> new SelectedVersion(value))
                .forEach((version) -> {
                    selectedVersions.add(version);
                });
    }

    /**
     * Gets the datasets selection.
     *
     * @return the datasets selection
     */
    public List<Dataset> getDatasetsSelection() {
        return datasetsSelection;
    }

    /**
     * Gets the display dataset panel.
     *
     * @return the display dataset panel
     */
    public Boolean getDisplayDatasetPanel() {
        return displayDatasetPanel;
    }

    /**
     * Gets the selected dataset.
     *
     * @return the selected dataset
     */
    public Dataset getSelectedDataset() {
        return selectedDataset;
    }

    /**
     * Gets the selected version.
     *
     * @return the selected version
     */
    public SelectedVersion getSelectedVersion() {
        return selectedVersion;
    }

    /**
     * Sets the selected version.
     *
     * @param selectedVersion the new selected version
     */
    public void setSelectedVersion(final SelectedVersion selectedVersion) {
        this.selectedVersion = selectedVersion;
    }

    /**
     * Gets the time zone.
     *
     * @return the time zone
     */
    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    /**
     * Inits the file upload.
     */
    public void initFileUpload() {
        LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).info("Inits the file upload unused");
    }

    /**
     * Navigate.
     *
     * @return the string
     */
    public String navigate() {
        return "dataset";
    }

    /**
     * Publish checkbox.
     *
     * @return the string
     * @throws BusinessException the business exception
     */
    public String publishCheckbox() throws BusinessException {
        try {
            for (VersionFile versionFile : selectedDataset.getVersions().values()) {
                if (versionFile.getId() == selectedVersion.getId()) {
                    selectedVersion.versionFile = versionFile;
                    break;
                }
            }
            if (selectedVersion.getChecked()) {
                datasetManager.publishVersion(selectedVersion.versionFile);
            } else {
                datasetManager.unPublishVersion(selectedVersion.versionFile);
            }
            updateDatasetsSelection();
            updateDatasetSelected();
        } catch (final BusinessException e) {
            UncatchedExceptionLogger.log("error in uibenadataset", e);
            return null;
        }

        setSelectedDataset(selectedDataset);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("#{p:component('dataTableID')}");
        return null;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedTreeNode() {
        return uiBeanActionTree.getSelectedTreeNode();
    }

    /**
     *
     * @param selectedTreeNode
     * @throws BusinessException
     */
    public void setSelectedTreeNode(TreeNode selectedTreeNode) throws BusinessException {
        if (selectedTreeNode != null && !selectedTreeNode.equals(uiBeanActionTree.getSelectedTreeNode())) {
            uiBeanActionTree.setSelectedTreeNode(selectedTreeNode);
            this.selectedVersions.clear();
            this.selectedDataset = null;
            uiBeanActionTree.updateColumns();
            selectionChanged();
        }
    }

    /**
     * Selection changed.
     *
     * @throws BusinessException the business exception
     * @link(TreeSelectionChangeEvent) the selection change event
     */
    public void selectionChanged() throws BusinessException {
        if (uiBeanActionTree.getSelectedTreeNode() == null) {
            return;
        }
        //uiBeanActionTree.getSelectedTreeNode().setExpanded(true);
        if (uiBeanActionTree.getSelectedTreeNode().isLeaf()) {

            displayDatasetPanel = true;
            IFlatNode node = (IFlatNode) ((ITreeNode) uiBeanActionTree.getSelectedTreeNode().getData()).getData();
            Objects.requireNonNull(node);
            datasetsSelection = datasetManager.retrieveDatasets(node.getRealNodeLeafId());

            if (selectedDataset != null && datasetsSelection.contains(selectedDataset)) {
                selectedDataset.setRealNode(((INode) node).getRealNode());
            } else {
                selectedDataset = null;
            }
        } else {
            datasetsSelection = null;
            displayDatasetPanel = false;
            selectedDataset = null;
        }
        uiBeanActionTree.updateColumns();
    }

    /**
     * Sets the dataset manager.
     *
     * @param datasetManager the new dataset manager
     */
    public void setDatasetManager(final IDatasetManager datasetManager) {
        this.datasetManager = datasetManager;
    }

    /**
     * Sets the localisation manager.
     *
     * @param localisationManager the new localisation manager
     */
    public void setLocalisationManager(final ILocalizationManager localisationManager) {
        this.localisationManager = localisationManager;
    }

    /**
     * Sets the notifications manager.
     *
     * @param notificationsManager the new notifications manager
     */
    public void setNotificationsManager(final INotificationsManager notificationsManager) {
        this.notificationsManager = notificationsManager;
    }

    /**
     * Sets the security context.
     *
     * @param policyManager
     */
    public void setPolicyManage(final IPolicyManager policyManager) {
        this.policyManager = policyManager;
    }

    /**
     * Sets the selected dataset id.
     *
     * @param selectedDatasetId the new selected dataset id
     */
    public void setSelectedDatasetId(final Long selectedDatasetId) {
        selectedDataset = retrieveDatasetById(selectedDatasetId);
    }

    /**
     * Sets the transaction manager.
     *
     * @param transactionManager the new transaction manager
     */
    public void setTransactionManager(final JpaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Sets the users manager.
     *
     * @param usersManager the new users manager
     */
    public void setUsersManager(final IUsersManager usersManager) {
        this.usersManager = usersManager;
    }

    /**
     * Update dataset selected.
     */
    private void updateDatasetSelected() {
        if (selectedDataset != null) {
            selectedDataset = retrieveSelectedDatasetById(selectedDataset.getId(), datasetsSelection);
        }
        setSelectedDataset(selectedDataset);

    }

    public String customFormatDate(LocalDateTime date) {
        if (date != null) {
            return DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY);
        }
        return "";
    }

    public String customFormatDateHour(LocalDateTime date) {
        if (date != null) {
            return DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY_HH_MM);
        }
        return "";
    }

    /**
     * Update datasets selection.
     *
     * @throws BusinessException the business exception
     */
    private void updateDatasetsSelection() throws BusinessException {
        datasetsSelection = datasetManager.retrieveDatasets(((IFlatNode) ((ITreeNode) uiBeanActionTree.getSelectedTreeNode().getData()).getData()).getRealNodeLeafId());
    }

    /**
     * Retrieve dataset by id.
     *
     * @param id the id
     * @return the dataset
     * @link(Long) the id
     */
    private Dataset retrieveDatasetById(final Long id) {
        for (final Dataset dataset : datasetsSelection) {
            if (dataset.getId().equals(id)) {
                return dataset;
            }
        }
        return null;
    }

    /**
     * Retrieve selected dataset by id.
     *
     * @param id the id
     * @param datasets the datasets
     * @return the dataset
     * @link(Long) the id
     * @link(List<Dataset>) the datasets
     */
    Dataset retrieveSelectedDatasetById(final Long id, final List<Dataset> datasets) {
        for (final Dataset dataset : datasets) {
            if (dataset.getId().equals(id)) {
                return dataset;
            }
        }
        return null;
    }

    /**
     * Upload submit listener.
     *
     * @param event the event
     * @link(FileUploadEvent) the event
     */
    public void uploadSubmitListen(final FileUploadEvent event) {
        try {

            final UploadedFile uploadedFile = event.getFile();

            IFlatNode selectedNode = (IFlatNode) ((ITreeNode) uiBeanActionTree.getSelectedTreeNode().getData()).getData();

            if (selectedNode != null) {
                final VersionFile version = datasetManager.getVersionFromFileName(uploadedFile.getFileName(), selectedNode.getRealNodeLeafId());

                if (version != null) {
                    version.setData(uploadedFile.getContents());
                    datasetManager.uploadVersion(version);
                    updateDatasetsSelection();
                    updateDatasetSelected();
                }
            }
        } catch (final BusinessException e) {
            UncatchedExceptionLogger.log("error in uibenadataset", e);
        }
    }

    /**
     * Upload check listener.
     *
     * @param event the event
     * @link(FileUploadEvent) the event
     */
    public void uploadCheckListener(org.primefaces.event.FileUploadEvent event) {
        try {
            final UploadedFile uploadedFile = event.getFile();
            if (selectedDataset != null) {
                final VersionFile version = datasetManager.getVersionFromFileName(
                        uploadedFile.getFileName(),
                        selectedDataset.getRealNode().getId()
                );
                if (version != null) {
                    version.setData(Utils.sanitizeData(uploadedFile.getContents(), localisationManager));
                    datasetManager.checkVersion(version);
                }
            }
        } catch (final BusinessException e) {
            UncatchedExceptionLogger.log("error in uibenadataset", e);
        }
    }

    /**
     *
     * @param versionFile
     * @return
     * @throws BusinessException
     */
    public StreamedContent upload(VersionFile versionFile) throws BusinessException {
        ByteArrayInputStream stream = new ByteArrayInputStream(datasetManager.downloadVersionById(versionFile.getId()));
        return new DefaultStreamedContent(stream, "application/octet-stream", versionManager.getDownloadFileName(versionFile).replaceAll(",", "_"));
    }

    /**
     *
     * @param versionManager
     */
    public void setVersionManager(IVersionManager versionManager) {
        this.versionManager = versionManager;
    }

    /**
     * The Class SelectedVersion.
     */
    public class SelectedVersion {

        /**
         * The checked @link(Boolean).
         */
        private Boolean checked;
        /**
         * The id @link(Long).
         */
        private Long id;
        /**
         * The version file @link(VersionFile).
         */
        private VersionFile versionFile;

        private SelectedVersion(VersionFile versionFile) {
            this.versionFile = versionFile;
            this.id = versionFile.getId();
            this.checked = versionFile.equals(versionFile.getDataset().getPublishVersion());
        }

        /**
         *
         * @param checked
         * @param id
         */
        public void checkSelectedVersion(Boolean checked, Long id) {
            setChecked(checked);
            setId(id);
        }

        /**
         *
         * @param checked
         */
        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

        /**
         *
         * @param id
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * Gets the checked.
         *
         * @return the checked
         */
        public Boolean getChecked() {
            return checked;
        }

        /**
         * Gets the id.
         *
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * Gets the version file.
         *
         * @return the version file
         */
        public VersionFile getVersionFile() {
            return versionFile;
        }

        /**
         *
         */
        public void resetSelectedDataSet() {
            selectedDataset = null;
        }
    }
}
