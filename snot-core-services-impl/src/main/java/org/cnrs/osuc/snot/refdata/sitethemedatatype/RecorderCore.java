/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.sitethemedatatype;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.inra.ecoinfo.mga.business.composite.AbstractBranchNode;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator;
import org.inra.ecoinfo.mga.configurator.IMgaIOConfigurator;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.datatypevariableunite.DatatypeVariableUnite;
import org.inra.ecoinfo.refdata.datatypevariableunite.IDatatypeVariableUniteDAO;
import org.inra.ecoinfo.refdata.site.ISiteDAO;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.refdata.sitethemedatatype.ISiteThemeDatatypeDAO;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author jbparoissien
 */
public class RecorderCore extends AbstractCSVMetadataRecorder<INode> {
     /**
     * The site dao.
     */
    protected ISiteDAO siteDAO;
    /**
     * The theme dao.
     */
    protected IThemeDAO themeDAO;
    /**
     * The datatype dao.
     */
    protected IDatatypeDAO datatypeDAO;
    /**
     * The sites possibles.
     */
    private Map<String, String[]> sitesPossibles = new ConcurrentHashMap<>();
    /**
     * The themes possibles.
     */
    private Map<String, String[]> themesPossibles = new ConcurrentHashMap<>();
    /**
     * The datatypes possibles.
     */
    private Map<String, String[]> datatypesPossibles = new ConcurrentHashMap<>();
    /**
     * The projet site theme datatype dao.
     */
    protected ISiteThemeDatatypeDAO siteThemeDatatypeDAO;
    protected IDatatypeVariableUniteDAO datatypeVariableUniteDAO;

    // a voir
    final int CODE_CONF = 2;

    /**
     *
     */
    public RecorderCore() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#deleteRecord(com.Ostermiller.util
     * .CSVParser, java.io.File, java.lang.String)
     */
    @Override
    public void deleteRecord(final CSVParser parser, final File file, final String encoding)
            throws BusinessException {
        final ErrorsReport errorsReport = new ErrorsReport();
        IMgaIOConfigurator configurator = mgaServiceBuilder.getMgaIOConfigurator();
        Stream<String> buildOrderedPaths = buildOrderedPathes(file, configurator, CODE_CONF, false);
        buildOrderedPaths
                .map(p -> mgaServiceBuilder.getRecorder().getRealNodeByNKey(p))
                .map(p -> p.orElse(null))
                .forEach(rn -> mgaServiceBuilder.getRecorder().remove(rn));
        if (errorsReport.hasErrors()) {
            throw new BusinessException(errorsReport.getErrorsMessages());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
     */
    @Override
    protected List<INode> getAllElements() throws BusinessException {

        Stream<INode> nodes = Stream.empty();
        try {
            nodes = mgaServiceBuilder.getMgaIOConfigurator().getConfiguration(CODE_CONF)
                    .map(conf->mgaServiceBuilder.loadNodes(conf, policyManager.isRoot(), false))
                    .orElse(Stream.empty());

        } catch (Exception ex) {
            Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nodes.collect(Collectors.toList());
    }

    /**
     * Gets the datatype dao.
     *
     * @return the datatype dao
     */
    public IDatatypeDAO getDatatypeDAO() {
        return datatypeDAO;
    }

    /**
     * Gets the datatypes possibles.
     *
     * @return the datatypes possibles
     */
    public Map<String, String[]> getDatatypesPossibles() {
        return datatypesPossibles;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(final INode node)
            throws BusinessException {
        final LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();

        try {

            lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                    new ColumnModelGridMetadata(
                            node == null ? AbstractCSVMetadataRecorder.EMPTY_STRING
                                    : node.getNodeByNodeableTypeResource(Site.class).getNodeable().getCode(), sitesPossibles, null, true, false,
                            true));

            lineModelGridMetadata
                    .getColumnsModelGridMetadatas()
                    .add(new ColumnModelGridMetadata(
                            node == null ? AbstractCSVMetadataRecorder.EMPTY_STRING
                                    : node.getNodeByNodeableTypeResource(Theme.class).getNodeable().getCode(),
                            themesPossibles, null, true, false, true));

            lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                    new ColumnModelGridMetadata(
                            node == null ? AbstractCSVMetadataRecorder.EMPTY_STRING
                                    : node.getNodeByNodeableTypeResource(DataType.class).getNodeable().getCode(),
                            datatypesPossibles, null, true, false, true));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage(), ex);
        }

        return lineModelGridMetadata;
    }

    /**
     * Gets the projet site theme datatype dao.
     *
     * @return the projet site theme datatype dao
     */
    public ISiteThemeDatatypeDAO getSiteThemeDatatypeDAO() {
        return siteThemeDatatypeDAO;
    }

    /**
     * Gets the site dao.
     *
     * @return the site dao
     */
    public ISiteDAO getSiteDAO() {
        return siteDAO;
    }

    /**
     * Gets the sites possibles.
     *
     * @return the sites possibles
     */
    public Map<String, String[]> getSitesPossibles() {
        return sitesPossibles;
    }

    /**
     * Gets the theme dao.
     *
     * @return the theme dao
     */
    public IThemeDAO getThemeDAO() {
        return themeDAO;
    }

    /**
     * Gets the themes possibles.
     *
     * @return the themes possibles
     */
    public Map<String, String[]> getThemesPossibles() {
        return themesPossibles;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#initModelGridMetadata()
     */
    @Override
    protected ModelGridMetadata<INode> initModelGridMetadata() {
        try {
            updatePathsSitesPossibles();
            updateNamesThemesPossibles();
            updateNamesDatatypesPossibles();
        } catch (final PersistenceException e) {
            LOGGER.error("can't init modelMetadata", e);
        }
        return super.initModelGridMetadata();
    }

    /**
     * Process record.
     *
     * @param parser the parser
     * @param file the file
     * @param encoding the encoding
     * @throws BusinessException the business exception
     */
    @Override
    public void processRecord(final CSVParser parser, final File file, final String encoding)
            throws BusinessException {

        final ErrorsReport errorsReport = new ErrorsReport();
        List listNodes = new ArrayList();

        try {
            IMgaIOConfigurator configurator = mgaServiceBuilder.getMgaIOConfigurator();
            Set existingPathes = getAllElements().stream().map(n -> n.getPath()).collect(Collectors.toSet());
            Stream<String> buildOrderedPaths = buildOrderedPathes(file, configurator, CODE_CONF, true)
                    .filter(p -> !existingPathes.contains(p));

            Stream<INode> listChild = buildLeaves(buildOrderedPaths, CODE_CONF).collect(Collectors.toList()).stream()
                    .peek(node -> listNodes.add(node));
            persistNodes(listChild, parser);
            if (errorsReport.hasErrors()) {
                throw new BusinessException(errorsReport.getErrorsMessages());
            }
            addVariableNodes(listNodes);
            treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION);
            treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION_RIGHTS);
            policyManager.clearTreeFromSession();
        } catch (final javax.persistence.PersistenceException | BusinessException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * Sets the datatype dao.
     *
     * @param datatypeDAO the new datatype dao
     */
    public void setDatatypeDAO(final IDatatypeDAO datatypeDAO) {
        this.datatypeDAO = datatypeDAO;
    }

    /**
     * Sets the datatypes possibles.
     *
     * @param datatypesPossibles the datatypes possibles
     */
    public void setDatatypesPossibles(final Map<String, String[]> datatypesPossibles) {
        this.datatypesPossibles = datatypesPossibles;
    }

    /**
     * Sets the projet site theme datatype dao.
     *
     * @param siteThemeDatatypeDAO the new projet site theme datatype dao
     */
    public void setSiteThemeDatatypeDAO(
            final ISiteThemeDatatypeDAO siteThemeDatatypeDAO) {
        this.siteThemeDatatypeDAO = siteThemeDatatypeDAO;
    }

    /**
     * Sets the site dao.
     *
     * @param siteDAO the new site dao
     */
    public void setSiteDAO(final ISiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }

    /**
     * Sets the theme dao.
     *
     * @param themeDAO the new theme dao
     */
    public void setThemeDAO(final IThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    /**
     * Update names datatypes possibles.
     *
     * @throws PersistenceException the persistence exception
     */
    private void updateNamesDatatypesPossibles() throws PersistenceException {
        final List<DataType> datatypes = datatypeDAO.getAll(DataType.class);
        final String[] namesDatatypesPossibles = new String[datatypes.size()];
        int index = 0;
        for (final DataType datatype : datatypes) {
            namesDatatypesPossibles[index++] = datatype.getName();
        }
        datatypesPossibles.put(ColumnModelGridMetadata.NULL_KEY, namesDatatypesPossibles);
    }

    /**
     * Update names themes possibles.
     *
     * @throws PersistenceException the persistence exception
     */
    private void updateNamesThemesPossibles() throws PersistenceException {
        final List<Theme> themes = themeDAO.getAll(Theme.class);
        final String[] namesThemesPossibles = new String[themes.size()];
        int index = 0;
        for (final Theme theme : themes) {
            namesThemesPossibles[index++] = theme.getName();
        }
        themesPossibles.put(ColumnModelGridMetadata.NULL_KEY, namesThemesPossibles);
    }

    /**
     * Update paths sites possibles.
     *
     * @throws PersistenceException the persistence exception
     */
    private void updatePathsSitesPossibles() throws PersistenceException {
        final List<Site> sites = siteDAO.getAll(Site.class);
        final String[] pathsSitesPossibles = new String[sites.size()];
        int index = 0;
        for (final Site site : sites) {
            pathsSitesPossibles[index++] = site.getPath();
        }
        sitesPossibles.put(ColumnModelGridMetadata.NULL_KEY, pathsSitesPossibles);
    }

    /**
     *
     * @param <T>
     * @param datatype
     */
    protected <T extends AbstractBranchNode> void removeStickyLeaves(T datatype) {
        WhichTree whichTree = mgaServiceBuilder.getMgaIOConfigurator().getConfiguration(CODE_CONF)
                .map(conf->conf.getWhichTree())
                .orElse(WhichTree.TREEDATASET);
        mgaServiceBuilder.getRecorder().removeStickyLeaves(datatype, whichTree);
    }

    private void addVariableNodes(List<INode> listNodes) {
        Map<DataType, List<DatatypeVariableUnite>> datatypeNodes = datatypeVariableUniteDAO.getAll(DatatypeVariableUnite.class).stream()
                .collect(Collectors.groupingBy(DatatypeVariableUnite::getDatatype));
        listNodes.stream()
                .forEach((node) -> {
                    DataType datatype = (DataType) node.getNodeable();
                    if (datatypeNodes.containsKey(datatype)) {
                        datatypeNodes.get(datatype).forEach((dvu) -> {
                            String path = String.format("%s%s%s", node.getRealNode().getPath(), PatternConfigurator.PATH_SEPARATOR, dvu.getCode());
                            RealNode rn = new RealNode(node.getRealNode(), null, dvu, path);
                            mgaServiceBuilder.getRecorder().saveOrUpdate(rn);
                            NodeDataSet nds = new NodeDataSet((NodeDataSet) node, null);
                            nds.setRealNode(rn);
                            mgaServiceBuilder.getRecorder().merge(nds);
                        });
                    }
                });
    }

    public void setDatatypeVariableUniteDAO(IDatatypeVariableUniteDAO datatypeVariableUniteDAO) {
        this.datatypeVariableUniteDAO = datatypeVariableUniteDAO;
    }
}
