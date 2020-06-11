/**
 *
 */
package org.cnrs.osuc.snot.refdata.infocomplstdtvar;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.IInformationComplementaireDAO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import static org.cnrs.osuc.snot.utils.Constantes.CST_HYPHEN;
import org.inra.ecoinfo.mga.enums.WhichTree;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.variable.IVariableDAO;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.SRDV;
import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.refdata.ModelGridMetadata;

/**
 * @author sophie
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<InformationComplementaireStdtVariable> {

    private static final Logger LOGGER = Logger.getLogger(Recorder.class);
    private static final String MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE = "MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    IVariableDAO variableDAO;
    IInformationComplementaireDAO infoComplementaireDAO;
    IInfoComplementaireStdtVariableDAO infoCpltStdtVariableDAO;
    IDatatypeVariableUniteSnotDAO variableUniteSnotDAO;
    private String[] stdtVarPossibles;
    private String[] nomsInfoCpltPossibles;
    protected IJeuDAO jeuSnotDAO;
    private String[] jeuPossibles;

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#deleteRecord(com.Ostermiller.util.CSVParser, java.io.File, java.lang.String)
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
                TokenizerValues tokenizerValues = new TokenizerValues(values);
                String nomStdtVar = tokenizerValues.nextToken();
                String infoCpltNom = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(nomStdtVar, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.hasError()) {
                    throw new BusinessException(srdv.getErrorMessage());
                }
                Optional<InformationComplementaire> infoCpltOpt = this.infoComplementaireDAO.getByNom(infoCpltNom);
                if (!infoCpltOpt.isPresent()) {
                    String message = localizationManager.getMessage(BUNDLE_SOURCE_PATH, MISSING_SITE_THEME_DATATYPE_VARIABLE_REALNODE);
                    throw new BusinessException(message);
                }
                InformationComplementaire infoCplt = infoCpltOpt.get();
                Optional<InformationComplementaireStdtVariable> infoCpltStdtVariableOpt = this.infoCpltStdtVariableDAO.getByStdtVariableAndInfoComplt(srdv.getRealNode(), infoCplt);
                this.infoCpltStdtVariableDAO.remove(infoCpltStdtVariableOpt.get());
            }
        } catch (final IOException | PersistenceException e) {
            LOGGER.debug(e.getMessage());
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
     */
    /**
     *
     * @param infoCpltStdtVariable
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(InformationComplementaireStdtVariable infoCpltStdtVariable) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();

        String displayStdtVar = Optional.ofNullable(infoCpltStdtVariable)
                .map(info -> info.getRealNode())
                .map(rn -> new SRDV(rn, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO))
                .map(SRDV::getCode)
                .orElseGet(String::new);
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoCpltStdtVariable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : displayStdtVar, stdtVarPossibles, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoCpltStdtVariable == null ? Constantes.CST_STRING_EMPTY : infoCpltStdtVariable.getJeu().getCodeJeu(), jeuPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoCpltStdtVariable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : infoCpltStdtVariable.getInfoComplementaire().getNom(), nomsInfoCpltPossibles, null, true, false, true));
        
        return lineModelGridMetadata;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#processRecord(com.Ostermiller.util.CSVParser, java.io.File, java.lang.String)
     */
    /**
     *
     * @param parser
     * @param file
     * @param encoding
     * @throws BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        ErrorsReport errorsReport = new ErrorsReport();
        try {
            this.skipHeader(parser);
            String[] values = null;
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values, InformationComplementaireStdtVariable.TABLE_NAME);
                String codeJeu = tokenizerValues.nextToken();
                String nomStdtVar = tokenizerValues.nextToken();
                String infoComplementaire = tokenizerValues.nextToken();
                SRDV srdv = new SRDV(nomStdtVar, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO);
                if (srdv.hasError()) {
                    throw new BusinessException(srdv.getErrorMessage());
                }
                Optional<InformationComplementaire> infoCplt = this.infoComplementaireDAO.getByNom(infoComplementaire);
                if (!infoCplt.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "INFOCPLT_NONDEFINI"), srdv.getCode()));
                }
                if (errorsReport.hasErrors()) {
                    continue;
                }
                Optional<InformationComplementaireStdtVariable> dbInfoCpltStdtVariable = this.infoCpltStdtVariableDAO.getByStdtVariableAndInfoComplt(srdv.getRealNode(), infoCplt.get());
                Optional<Jeu> code_jeu = this.jeuSnotDAO.getByCode(codeJeu);
                if (!code_jeu.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "JEU_NONDEFINI"), codeJeu));
                    continue;
                }
                if (!dbInfoCpltStdtVariable.isPresent()) {
                    InformationComplementaireStdtVariable infoCpltStdtVariable = new InformationComplementaireStdtVariable(code_jeu.get(),srdv.getRealNode(), infoCplt.get());
                    this.infoCpltStdtVariableDAO.saveOrUpdate(infoCpltStdtVariable);
                }
            }
            if (errorsReport.hasErrors()) {
                throw new BusinessException(errorsReport.getErrorsMessages());
            }
        } catch (IOException | PersistenceException e1) {
            LOGGER.debug(e1);
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param infoComplementaireDAO
     */
    public void setInfoComplementaireDAO(IInformationComplementaireDAO infoComplementaireDAO) {
        this.infoComplementaireDAO = infoComplementaireDAO;
    }

    /**
     * @param infoCpltStdtVariableDAO the infoCpltStdtVariableDAO to set
     */
    public void setInfoCpltStdtVariableDAO(IInfoComplementaireStdtVariableDAO infoCpltStdtVariableDAO) {
        this.infoCpltStdtVariableDAO = infoCpltStdtVariableDAO;
    }

    /**
     * @param variableDAO the variableDAO to set
     */
    public void setVariableDAO(IVariableDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getNomsInfoCpltPossibles() {
        List<InformationComplementaire> lstInfoCplts = this.infoComplementaireDAO.getAll(InformationComplementaire.class);
        String[] codeInfoCpltPossibles = new String[lstInfoCplts.size()];
        int index = 0;
        for (InformationComplementaire ifCplt : lstInfoCplts) {
            codeInfoCpltPossibles[index++] = ifCplt.getNom();
        }
        return codeInfoCpltPossibles;
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
     * @param jeuSnotDAO the jeuSnotDAO to set
     */
    public void setJeuDAO(IJeuDAO jeuSnotDAO) {
        this.jeuSnotDAO = jeuSnotDAO;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getStdtVarPossibles() {
        return policyManager.getMgaServiceBuilder().getRecorder().getNodesByTypeResource(WhichTree.TREEDATASET, DatatypeVariableUniteSnot.class)
                .map(n -> n.getRealNode())
                .map(rn -> new SRDV(rn, localizationManager, policyManager.getMgaServiceBuilder().getRecorder(), variableUniteSnotDAO))
                .map(realnode -> realnode.getCode())
                .collect(Collectors.toSet())
                .toArray(new String[0]);
    }

    /*
     * (non-Javadoc)
     *
    * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
     */
    /**
     *
     * @return @throws BusinessException
     */
    @Override
    protected List<InformationComplementaireStdtVariable> getAllElements() throws BusinessException {
        return this.infoCpltStdtVariableDAO.getAll(InformationComplementaireStdtVariable.class);
    }

    /**
     *
     * @param variableUniteSnotDAO
     */
    public void setDatatypeVariableUniteSnotDAO(IDatatypeVariableUniteSnotDAO variableUniteSnotDAO) {
        this.variableUniteSnotDAO = variableUniteSnotDAO;
    }

    /**
     *
     * @return
     */
    @Override
    protected ModelGridMetadata<InformationComplementaireStdtVariable> initModelGridMetadata() {
        this.stdtVarPossibles = this._getStdtVarPossibles();
        jeuPossibles = getCodeJeuPossibles();
        this.nomsInfoCpltPossibles = this._getNomsInfoCpltPossibles();
        return super.initModelGridMetadata();
    }

}
