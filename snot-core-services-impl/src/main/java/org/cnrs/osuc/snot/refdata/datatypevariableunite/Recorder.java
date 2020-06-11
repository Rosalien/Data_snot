/**
 * OREILacs project - see LICENCE.txt for use created: 7 avr. 2009 16:17:33
 */
package org.cnrs.osuc.snot.refdata.datatypevariableunite;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import static org.inra.ecoinfo.dataset.versioning.impl.GenericDatatypeProcessRecord.BUNDLE_PATH;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.mga.configurator.AbstractMgaIOConfigurator;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.unite.IUniteDAO;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.refdata.variable.IVariableDAO;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "Antoine Schellenberger"
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<DatatypeVariableUniteSnot> {
//    private static final String BUNDLE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    private static final Logger LOGGER = LoggerFactory.getLogger(Recorder.class);

    private static final String MSG_VARIABLE_MISSING_IN_DATABASE = "MSG_VARIABLE_MISSING_IN_DATABASE";
    private static final String MSG_DATATYPE_MISSING_IN_DATABASE = "MSG_DATATYPE_MISSING_IN_DATABASE";
    private static final String MSG_UNITE_MISSING_IN_DATABASE = "MSG_UNITE_MISSING_IN_DATABASE";

    private String[] jeuPossibles;

    /**
     *
     */
    protected IJeuDAO jeuSnotDAO;

    private String[] sitePossibles;

    /**
     *
     */
    protected ISiteSnotDAO siteSnotDAO;

    /**
     *
     */
    protected IDatatypeDAO datatypeDAO;

    /**
     *
     */
    protected IVariableDAO variableDAO;

    /**
     *
     */
    protected IUniteDAO uniteDAO;

    /**
     *
     */
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO;

    /*
     * @Override public void checkUpdatesOperations(CSVParser parser) throws BusinessException, UpdateNotificationException { throw new NotYetImplementedException();
     * 
     * }
     */
    /**
     *
     * @param parser
     * @param file
     * @param encoding
     * @throws BusinessException
     */
    @Override
    public void deleteRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        try {
            String[] values = null;
            while ((values = parser.getLine()) != null) {
                final TokenizerValues tokenizerValues = new TokenizerValues(values);
                final String jeuCode = tokenizerValues.nextToken();
                final String siteCode = tokenizerValues.nextToken();
                final String datatypeCode = Utils.createCodeFromString(tokenizerValues.nextToken());
                final String variableCode = tokenizerValues.nextToken();
                final String uniteCode = tokenizerValues.nextToken();
                final DatatypeVariableUniteSnot dbDVU = this.datatypeVariableUniteDAO
                        .getByNKey(jeuCode,siteCode, datatypeCode, variableCode, uniteCode)
                        .map(d -> {
                    return (DatatypeVariableUniteSnot) d;
                })
                        .orElseThrow(PersistenceException::new);
                mgaServiceBuilder.getRecorder().remove(dbDVU);
            }
            treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION);
            treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION_RIGHTS);
            policyManager.clearTreeFromSession();
        } catch (final IOException e) {
            LOGGER.debug(e.getMessage(), e);
            throw new BusinessException(e);
        } catch (PersistenceException ex) {
            java.util.logging.Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param datatypeVariableUnite
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(DatatypeVariableUniteSnot datatypeVariableUnite) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? Constantes.CST_STRING_EMPTY : datatypeVariableUnite.getJeu().getCodeJeu(), jeuPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getSiteSnot().getPath(), sitePossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getDatatype().getCode(), this.getNamesDatatypesPossibles(), null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getVariable().getCode(), this.getNamesVariablesPossibles(), null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getUnite().getCode(), this.getNamesUnitesPossibles(), null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getMin(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getMax(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(datatypeVariableUnite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : datatypeVariableUnite.getMissingValue(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, true));
        return lineModelGridMetadata;
    }

    /**
     * @param parser
     * @param encoding
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        ErrorsReport errorsReport = new ErrorsReport();
        Map<SiteSnot, Map<DataType, List<DatatypeVariableUniteSnot>>> nodeablesDVU = new HashMap<>();
        try {
            this.skipHeader(parser);
            String[] values = null;
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values, Nodeable.getLocalisationEntite(DatatypeVariableUniteSnot.class));
                String codeJeu = tokenizerValues.nextToken();
                String siteCode = tokenizerValues.nextToken();
                String datatypeCode = Utils.createCodeFromString(tokenizerValues.nextToken());
                String variableCode = tokenizerValues.nextToken();
                String uniteCode = tokenizerValues.nextToken();
                Float minValue = tokenizerValues.nextTokenFloat();
                Float maxValue = tokenizerValues.nextTokenFloat();
                String missingvalue = tokenizerValues.nextToken();
                this.persistDatatypeVariable(errorsReport, codeJeu, siteCode, datatypeCode, variableCode, uniteCode, minValue, maxValue, missingvalue, nodeablesDVU);
            }
            if (errorsReport.hasErrors()) {
                throw new BusinessException(errorsReport.getErrorsMessages());
            }
            try {
                Map<SiteSnot, Map<DataType, List<INode>>> datatypeNodesMap = new HashMap();

                mgaServiceBuilder.loadNodesByTypeResource(WhichTree.TREEDATASET, DataType.class)
                        .filter(
                                n -> nodeablesDVU.containsKey(n.getNodeByNodeableTypeResource(SiteSnot.class).getNodeable())
                        )
                        .filter(
                                n -> nodeablesDVU
                                        .get(n.getNodeByNodeableTypeResource(SiteSnot.class).getNodeable())
                                        .containsKey(n.getNodeByNodeableTypeResource(DataType.class).getNodeable())
                        )
                        .forEach(
                                p -> datatypeNodesMap
                                        .computeIfAbsent((SiteSnot) p.getNodeByNodeableTypeResource(SiteSnot.class).getNodeable(), k -> new HashMap<DataType, List<INode>>())
                                        .computeIfAbsent((DataType) p.getNodeByNodeableTypeResource(DataType.class).getNodeable(), k -> new LinkedList<INode>())
                                        .add(p)
                        );
                int[] i = new int[]{0};
                for (Iterator<Map.Entry<SiteSnot, Map<DataType, List<DatatypeVariableUniteSnot>>>> iteratorOnDVU = nodeablesDVU.entrySet().iterator(); iteratorOnDVU.hasNext();) {
                    Map.Entry<SiteSnot, Map<DataType, List<DatatypeVariableUniteSnot>>> dvusSiteEntry = iteratorOnDVU.next();
                    SiteSnot siteSnot = dvusSiteEntry.getKey();
                    final Map<DataType, List<DatatypeVariableUniteSnot>> value = dvusSiteEntry.getValue();
                    for (Map.Entry<DataType, List<DatatypeVariableUniteSnot>> entry : value.entrySet()) {
                        DataType datatype = entry.getKey();
                        List<DatatypeVariableUniteSnot> dvus = entry.getValue();
                        List<INode> datatypeNodeList = datatypeNodesMap
                                .getOrDefault(siteSnot, new HashMap<DataType, List<INode>>())
                                .getOrDefault(datatype, new LinkedList());
                        for (Iterator<INode> iteratorOnDatatypeNodes = datatypeNodeList.iterator(); iteratorOnDatatypeNodes.hasNext();) {
                            INode datatypeNode = iteratorOnDatatypeNodes.next();
                            for (Iterator<DatatypeVariableUniteSnot> dvuIterator = dvus.iterator(); dvuIterator.hasNext();) {
                                DatatypeVariableUniteSnot dvu = dvuIterator.next();
                                RealNode parentRn = datatypeNode.getRealNode();
                                String path = String.format("%s%s%s", parentRn.getPath(), PatternConfigurator.PATH_SEPARATOR, dvu.getCode());
                                RealNode rn = new RealNode(parentRn, null, dvu, path);
                                mgaServiceBuilder.getRecorder().saveOrUpdate(rn);
                                NodeDataSet nds = new NodeDataSet((NodeDataSet) datatypeNode, null);
                                nds.setRealNode(rn);
                                mgaServiceBuilder.getRecorder().merge(nds);
                            }
                            iteratorOnDatatypeNodes.remove();
                        }
                        mgaServiceBuilder.getRecorder().getEntityManager().flush();
                        iteratorOnDVU.remove();
                    }
                }
                treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION);
                treeApplicationCacheManager.removeSkeletonTree(AbstractMgaIOConfigurator.DATASET_CONFIGURATION_RIGHTS);
                policyManager.clearTreeFromSession();

                if (errorsReport.hasErrors()) {
                    throw new BusinessException(errorsReport.getErrorsMessages());
                }
            } catch (final BusinessException e) {
                throw new BusinessException(e.getMessage(), e);
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException(e);
        } catch (final PersistenceException e) {
            LOGGER.debug(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    /**
     *
     * @param datatypeDAO
     */
    public void setDatatypeDAO(IDatatypeDAO datatypeDAO) {
        this.datatypeDAO = datatypeDAO;
    }

    /**
     *
     * @param datatypeVariableUniteDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO) {
        this.datatypeVariableUniteDAO = datatypeVariableUniteDAO;
    }

    /**
     *
     * @param uniteDAO
     */
    public void setUniteDAO(IUniteDAO uniteDAO) {
        this.uniteDAO = uniteDAO;
    }

    /**
     *
     * @param variableDAO
     */
    public void setVariableDAO(IVariableDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    private String[] getNamesDatatypesPossibles() {
        List<DataType> datatypes = this.datatypeDAO.getAll(DataType.class);
        String[] namesDatatypesPossibles = new String[datatypes.size()];
        int index = 0;
        for (DataType datatype : datatypes) {
            namesDatatypesPossibles[index++] = datatype.getName();
        }
        return namesDatatypesPossibles;
    }

    private String[] getNamesUnitesPossibles() {
        List<Unite> unites = this.uniteDAO.getAll(Unite.class);
        String[] namesUnitesPossibles = new String[unites.size()];
        int index = 0;
        for (Unite unite : unites) {
            namesUnitesPossibles[index++] = unite.getCode();
        }
        return namesUnitesPossibles;
    }

    private String[] getNamesVariablesPossibles() {
        List<Variable> variables = this.variableDAO.getAll(Variable.class);
        String[] namesVariablesPossibles = new String[variables.size()];
        int index = 0;
        for (Variable variable : variables) {
            namesVariablesPossibles[index++] = variable.getName();
        }
        return namesVariablesPossibles;
    }

    private void persistDatatypeVariable(ErrorsReport errorsReport, String jeuCode, String siteCode, String datatypeCode,
            String variableCode, String uniteCode, Float min, Float max, String missingvalue,
            Map<SiteSnot, Map<DataType, List<DatatypeVariableUniteSnot>>> nodeablesDVU) throws PersistenceException {
        Jeu dbJeu = this.retrieveDBJeu(errorsReport, jeuCode);
        SiteSnot dbSite = this.retrieveDBSitesnot(errorsReport, siteCode);
        DataType dbDatatype = this.retrieveDBDatatype(errorsReport, datatypeCode);
        VariableSnot dbVariable = (VariableSnot) this.retrieveDBVariable(errorsReport, variableCode);
        Unite dbUnite = this.retrieveDBUnite(errorsReport, uniteCode);
        if (dbDatatype != null && dbVariable != null && dbUnite != null) {
            DatatypeVariableUniteSnot datatypeVariableUniteSnotDB = (DatatypeVariableUniteSnot) this.datatypeVariableUniteDAO.getByNKey(jeuCode,siteCode, datatypeCode, variableCode, uniteCode)
                    .orElse(null);
            if (datatypeVariableUniteSnotDB == null) {
                DatatypeVariableUniteSnot datatypeVariableUnite = new DatatypeVariableUniteSnot(dbJeu,dbSite, dbDatatype, dbUnite, dbVariable, min, max, missingvalue);
                this.datatypeVariableUniteDAO.saveOrUpdate(datatypeVariableUnite);
                this.datatypeVariableUniteDAO.flush();
                nodeablesDVU
                        .computeIfAbsent(dbSite, k -> new HashMap<DataType, List<DatatypeVariableUniteSnot>>())
                        .computeIfAbsent(dbDatatype, k -> new LinkedList<DatatypeVariableUniteSnot>())
                        .add(datatypeVariableUnite);
            } else {
//                datatypeVariableUniteSnotDB.setJeu(jeu);
                datatypeVariableUniteSnotDB.setMin(min);
                datatypeVariableUniteSnotDB.setMax(max);
                datatypeVariableUniteSnotDB.setMissingValue(missingvalue);
                this.datatypeVariableUniteDAO.saveOrUpdate(datatypeVariableUniteSnotDB);
                this.datatypeVariableUniteDAO.flush();
            }
        }
    }

    private Jeu retrieveDBJeu(ErrorsReport errorsReport, String jeuCode) throws PersistenceException {
        Jeu jeu = this.jeuSnotDAO.getByCode(jeuCode).orElse(null);
        if (jeuCode == null) {
            errorsReport.addErrorMessage(localizationManager.getMessage(BUNDLE_PATH, String.format(Recorder.MSG_DATATYPE_MISSING_IN_DATABASE, jeuCode)));
        }
        return jeu;
    }

    private SiteSnot retrieveDBSitesnot(ErrorsReport errorsReport, String siteCode) throws PersistenceException {
        SiteSnot siteSnot = this.siteSnotDAO.getByCode(siteCode).orElse(null);
        if (siteSnot == null) {
            errorsReport.addErrorMessage(localizationManager.getMessage(BUNDLE_PATH, String.format(Recorder.MSG_DATATYPE_MISSING_IN_DATABASE, siteCode)));
        }
        return siteSnot;
    }

    private DataType retrieveDBDatatype(ErrorsReport errorsReport, String datatypeCode) throws PersistenceException {
        DataType datatype = this.datatypeDAO.getByCode(datatypeCode).orElse(null);
        if (datatype == null) {
            errorsReport.addErrorMessage(localizationManager.getMessage(BUNDLE_PATH, String.format(Recorder.MSG_DATATYPE_MISSING_IN_DATABASE, datatypeCode)));
        }
        return datatype;
    }

    private Unite retrieveDBUnite(ErrorsReport errorsReport, String uniteCode) throws PersistenceException {
        Unite unite = this.uniteDAO.getByCode(uniteCode).orElse(null);
        if (unite == null) {
            errorsReport.addErrorMessage(localizationManager.getMessage(BUNDLE_PATH, String.format(Recorder.MSG_UNITE_MISSING_IN_DATABASE, uniteCode)));
        }
        return unite;
    }

    private Variable retrieveDBVariable(ErrorsReport errorsReport, String variableCode) throws PersistenceException {
        Variable variable = this.variableDAO.getByCode(variableCode).orElse(null);
        if (variable == null) {
            errorsReport.addErrorMessage(localizationManager.getMessage(BUNDLE_PATH, String.format(Recorder.MSG_VARIABLE_MISSING_IN_DATABASE, variableCode)));
        }
        return variable;
    }

    /**
     * @param siteSnotDAO the siteSnotDAO to set
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteSnotDAO) {
        this.siteSnotDAO = siteSnotDAO;
    }

    private String[] getNamesSitesPossibles() {
        List<SiteSnot> sitesnots = this.siteSnotDAO.getAll(SiteSnot.class);
        String[] namesSitesPossibles = new String[sitesnots.size()];
        int index = 0;
        for (SiteSnot sitesnot : sitesnots) {
            namesSitesPossibles[index++] = sitesnot.getCode();
        }
        return namesSitesPossibles;
    }

    /**
     * @param jeuSnotDAO the jeuSnotDAO to set
     */
    public void setJeuDAO(IJeuDAO jeuSnotDAO) {
        this.jeuSnotDAO = jeuSnotDAO;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] getCodeJeuPossibles() {
        List<Jeu> jeux = this.jeuSnotDAO.getAll();
        String[] codesJeusPossibles = new String[jeux.size()];
        int index = 0;
        for (Jeu jeu : jeux) {
            codesJeusPossibles[index++] = jeu.getCodeJeu();
        }
        return codesJeusPossibles;
    }

    /**
     *
     * @return
     */
    @Override
    protected List<DatatypeVariableUniteSnot> getAllElements() {
        return datatypeVariableUniteDAO.getAll(DatatypeVariableUniteSnot.class);
    }

    @Override
    protected ModelGridMetadata<DatatypeVariableUniteSnot> initModelGridMetadata() {
        jeuPossibles = getCodeJeuPossibles();
        sitePossibles = getNamesSitesPossibles();
        return super.initModelGridMetadata();
    }

}
