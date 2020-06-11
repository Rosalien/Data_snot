package org.cnrs.osuc.snot.dataset.fluxmeteo.impl;

import com.Ostermiller.util.CSVParser;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.inra.ecoinfo.dataset.AbstractRecorder;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.IExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.request.RequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.fluxmeteo.session.IRequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.inra.ecoinfo.identification.IUtilisateurDAO;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.unite.IUniteDAO;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 * @param <D>
 */
public abstract class GenericRecorder<D extends DatasetDescriptor> extends AbstractRecorder {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.messages";

    /**
     *
     */
    protected IRequestPropertiesFluxMeteo requestProperties = new RequestPropertiesFluxMeteo();

    /**
     *
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     */
    protected ILocalizationManager localizationManager;

    /**
     *
     */
    protected Map<String, DatatypeVariableUniteSnot> variablesTypeDonnees;

    /**
     *
     */
    protected IVersionFileDAO versionFileDAO;

    /**
     *
     */
    protected IDatatypeDAO datatypeDAO;

    /**
     *
     */
    protected IThemeDAO themeDAO;

    /**
     *
     */
    protected IUtilisateurDAO utilisateurDAO;

    /**
     *
     */
    protected IUniteDAO uniteDAO;

    /**
     *
     */
    protected IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO;

    /**
     *
     */
    protected IExpertColonne expertColonne;

    /**
     *
     * @return
     */
    public Map<String, DatatypeVariableUniteSnot> getVariablesTypeDonnees() {
        return this.variablesTypeDonnees;
    }

    /**
     *
     * @param variablesTypeDonnees
     */
    public void setVariablesTypeDonnees(Map<String, DatatypeVariableUniteSnot> variablesTypeDonnees) {
        this.variablesTypeDonnees = variablesTypeDonnees;
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
    public void setDatatypeVariableUniteDAO(IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO) {
        this.datatypeVariableUniteDAO = datatypeVariableUniteDAO;
    }

    /**
     *
     * @param localizationManager
     */
    @Override
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     *
     * @param themeDAO
     */
    public void setThemeDAO(IThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
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
     * @param utilisateurDAO
     */
    public void setUtilisateurDAO(IUtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    /**
     *
     * @param versionFileDAO
     */
    @Override
    public void setVersionFileDAO(IVersionFileDAO versionFileDAO) {
        this.versionFileDAO = versionFileDAO;
    }

    /**
     *
     * @param versionFile
     * @param encoding
     * @throws BusinessException
     */
    @Override
    public void testFormat(VersionFile versionFile, String encoding) throws BusinessException {
    }

    private void initialiserDatatypeUniteVariable() throws BusinessException {
        String nom = this.datasetDescriptor.getName();
        Map<String, DatatypeVariableUniteSnot> maMap;
        try {
            maMap = this.construireMapDVU(nom);
        }catch (PersistenceException e) {
            throw new BusinessException(e);
        }
        this.setVariablesTypeDonnees(maMap);
    }

    /*
     * Modif. de private à protected pour pouvoir en hériter dans RecoderFlux_m.java et RecorderMeteo_m.java
     */

    /**
     *
     * @param erreurs
     * @param message
     */
    
    protected void ajouterMessageErreur(BadsFormatsReport erreurs, String message) {
        String messageErreur = this.localizationManager.getMessage(GenericRecorder.BUNDLE_SOURCE_PATH, message);
        BadExpectedValueException exception = new BadExpectedValueException(messageErreur);
        erreurs.addException(exception);
    }

    /**
     *
     */
    protected abstract void clearSession();

    /**
     *
     * @param nom
     * @return
     * @throws PersistenceException
     */
    protected Map<String, DatatypeVariableUniteSnot> construireMapDVU(String nom) throws PersistenceException {
        List<DatatypeVariableUniteSnot> dvu = this.datatypeVariableUniteDAO.getByDatatype(nom);
        Map<String, DatatypeVariableUniteSnot> variableTypeDonnees = new HashMap<>();
        for (DatatypeVariableUniteSnot datatypeVariableUnite : dvu) {
            variableTypeDonnees.put(datatypeVariableUnite.getVariable().getCode(), datatypeVariableUnite);
        }
        return variableTypeDonnees;
    }

    /**
     *
     * @return
     */
    protected abstract String getResourcename();

    /**
     *
     * @return
     */
    protected abstract int getTailleEntete();

    /**
     *
     */
    protected abstract void initialiserDatasetDescriptor();

    /**
     *
     * @param parser
     * @param versionFile
     * @param fileEncoding
     * @throws BusinessException
     */
    @Override
    protected void processRecord(CSVParser parser, VersionFile versionFile, String fileEncoding) throws BusinessException {
        
    }

    /**
     *
     * @param badsFormatsReport
     * @param versionFile
     */
    protected void verifierDatesNomFichierCartouche(BadsFormatsReport badsFormatsReport, VersionFile versionFile) {
        LocalDateTime debutNomFichier = versionFile.getDataset().getDateDebutPeriode();
        String dateDebutNomFichier = DateUtil.getUTCDateTextFromLocalDateTime(debutNomFichier, DateUtil.DD_MM_YYYY);
        LocalDateTime finNomFichier = versionFile.getDataset().getDateFinPeriode();
        String dateFinNomFichier = DateUtil.getUTCDateTextFromLocalDateTime(finNomFichier, DateUtil.DD_MM_YYYY);
    }

}
