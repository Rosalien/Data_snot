/**
 *
 */
package org.cnrs.osuc.snot.refdata.gestionnairejeu;

import com.google.common.base.Strings;
import com.Ostermiller.util.CSVParser;
import static com.sun.org.apache.xalan.internal.lib.NodeInfo.lineNumber;
import java.time.LocalDate;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.periodeutilisationinstrument.PeriodeUtilisationInstrument;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.inra.ecoinfo.refdata.valeurqualitative.IValeurQualitativeDAO;
import org.inra.ecoinfo.refdata.valeurqualitative.ValeurQualitative;

/**
 * @author jbparoissien
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<GestionnaireJeu> {

    protected static final String CODE_LIST = "responsabilite_gestionnairejeu";
    private static final String DATEFORMAT_ERROR = "DATEFORMAT_ERROR";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     * import org.assertj.core.util.Strings;
     */
    protected static final String GESTIONNAIREJEU_NONDEFINI = "GESTIONNAIREJEU";

    /**
     * The Constant PROPERTY_MSG_BAD_DATE_FORMAT @link(String).
     */
    static final String PROPERTY_MSG_BAD_DATE_FORMAT = "PROPERTY_MSG_BAD_DATE_FORMAT";

    /**
     *
     */
    protected IGestionnaireJeuDAO gestionnaireJeuDAO;

    /**
     *
     */
    protected IJeuDAO jeuDAO;
    private String[] jeuxPossibles;

    /**
     *
     */
    protected IValeurQualitativeDAO valeurQualitativeDAO;
    private String[] responsabilitePossibles;

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
                String codeJeu = tokenizerValues.nextToken();
                String nom = tokenizerValues.nextToken();
                String prenom = tokenizerValues.nextToken();
                String email = tokenizerValues.nextToken();
                String responsabilite = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                this.gestionnaireJeuDAO.remove(this.gestionnaireJeuDAO.getByKey(codeJeu, email, responsabilite, debutPeriode)
                        .orElseThrow(() -> new BusinessException("bad email")));
            }
        } catch (IOException | PersistenceException e) {
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
     * @param gestionnaireJeu
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(GestionnaireJeu gestionnaireJeu) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        String dateDebut = "";
        String dateFin = this.dateFin(gestionnaireJeu);
        if (gestionnaireJeu != null) {
            dateDebut = DateUtil.getUTCDateTextFromLocalDateTime(gestionnaireJeu.getDateDebut(), DateUtil.DD_MM_YYYY);
            if (gestionnaireJeu.getDateFin() != null) {
                dateDebut = DateUtil.getUTCDateTextFromLocalDateTime(gestionnaireJeu.getDateDebut().atStartOfDay(), DateUtil.DD_MM_YYYY);
//                dateFin = DateUtil.getUTCDateTextFromLocalDateTime(gestionnaireJeu.getDateFin(), DateUtil.DD_MM_YYYY);
            } else {
                dateFin = null;
            }
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : gestionnaireJeu.getJeu().getCodeJeu(), jeuxPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : gestionnaireJeu.getNom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_NOM_PERS, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : gestionnaireJeu.getPrenom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_PRENOM_PERS, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : gestionnaireJeu.getEmail(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_EMAIL_PERS, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : gestionnaireJeu.getResponsabilite().getValeur(), responsabilitePossibles, RefDataConstantes.COLUMN_RESPONSABILITE_PERS, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : dateDebut, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(gestionnaireJeu == null ? Constantes.CST_STRING_EMPTY : dateFin, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
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
            int lineNumber = 0;
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values, GestionnaireJeu.TABLE_NAME);
                String codeJeu = tokenizerValues.nextToken();
                String nom = tokenizerValues.nextToken();
                String prenom = tokenizerValues.nextToken();
                String email = tokenizerValues.nextToken();
                String responsabilite = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                LocalDate finPeriode;
                if (StringUtils.isEmpty(dateFin)) {
                    finPeriode = null;
                } else {
                    try {
                        finPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();
                    } catch (DateTimeParseException e) {
                        throw new BusinessException(String.format(DATEFORMAT_ERROR, dateFin, lineNumber));
                    }
                }
//                String dateDebut = tokenizerValues.nextToken();
//                String dateFin = tokenizerValues.nextToken();
//                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
//                LocalDate finPeriode = Strings.isNullOrEmpty(dateFin)
//                        ? null
//                        : DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();
                Optional<Jeu> code_jeu = this.jeuDAO.getByCode(codeJeu);
//                List<ValeurQualitative> code_responsabiliteList = this.valeurQualitativeDAO.getByCode(responsabilite);
                Optional<ValeurQualitative> code_responsabilite = this.valeurQualitativeDAO.getByCodeAndValue(CODE_LIST, responsabilite);
                if (!code_jeu.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "JEU_NONDEFINI"), codeJeu));
                    continue;
                }
                if (!code_responsabilite.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "RESPONSABILITE_NONDEFINI"), responsabilite));
                    continue;
                }
                GestionnaireJeu dbGestionnaireJeu;
                dbGestionnaireJeu = this.gestionnaireJeuDAO.getByKey(codeJeu, email, responsabilite, debutPeriode)
                        .orElseGet(() -> new GestionnaireJeu(code_jeu.get(), nom, prenom, email, code_responsabilite.get(), debutPeriode, finPeriode));
                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(code_jeu.get(), nom, prenom, email, code_responsabilite.get(), debutPeriode, finPeriode, dbGestionnaireJeu);
                }
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param jeuDAO the jeuDAO to set
     */
    public void setJeuDAO(IJeuDAO jeuDAO) {
        this.jeuDAO = jeuDAO;
    }

    /**
     * @param ValeurQualitativeDAO the ValeurQualitativeDAO to set
     */
    public void setValeurQualitativeDAO(IValeurQualitativeDAO valeurQualitativeDAO) {
        this.valeurQualitativeDAO = valeurQualitativeDAO;
    }

    /**
     * @param gestionnaireJeuDAO the gestionnaireJeuDAO to set
     */
    public void setGestionnaireJeuDAO(IGestionnaireJeuDAO gestionnaireJeuDAO) {
        this.gestionnaireJeuDAO = gestionnaireJeuDAO;
    }

    /**
     * @param code_jeu
     * @param nom
     * @param prenom
     * @param email
     * @param code_responsabilite
     * @param dateDebut
     * @param dateFin
     * @param dbGestionnaireJeu
     * @throws PersistenceException
     */
    private void _createOrUpdate(Jeu code_jeu, String nom, String prenom, String email, ValeurQualitative code_responsabilite, LocalDate dateDebut, LocalDate dateFin, GestionnaireJeu dbGestionnaireJeu) throws PersistenceException {
        if (dbGestionnaireJeu.getId() == null) {
            this.gestionnaireJeuDAO.saveOrUpdate(dbGestionnaireJeu);
        } else {
            dbGestionnaireJeu.setJeu(code_jeu);
            dbGestionnaireJeu.setNom(nom);
            dbGestionnaireJeu.setPrenom(prenom);
            dbGestionnaireJeu.setEmail(email);
            dbGestionnaireJeu.setResponsabilite(code_responsabilite);
            dbGestionnaireJeu.setDateDebut(dateDebut);
            dbGestionnaireJeu.setDateFin(dateFin);
            this.gestionnaireJeuDAO.saveOrUpdate(dbGestionnaireJeu);
        }
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] getCodeJeuxPossibles() {
        List<Jeu> jeux = this.jeuDAO.getAll();
        String[] codesJeuxPossibles = new String[jeux.size()];
        int index = 0;
        for (Jeu jeu : jeux) {
            codesJeuxPossibles[index++] = jeu.getCodeJeu();
        }
        return codesJeuxPossibles;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] getCodeResponsabilitePossibles() {
        List<ValeurQualitative> valeurqualitatives = this.valeurQualitativeDAO.getAll();
        String[] codesResponsabilitePossibles = new String[valeurqualitatives.size()];
        int index = 0;
        for (ValeurQualitative valeurQualitative : valeurqualitatives) {
            codesResponsabilitePossibles[index++] = valeurQualitative.getValeur();
        }
        return codesResponsabilitePossibles;
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
    protected List<GestionnaireJeu> getAllElements() throws BusinessException {
        return this.gestionnaireJeuDAO.getAll(GestionnaireJeu.class);
    }

    private String getMessage(Jeu codejeu, String nom, String prenom, String email, ValeurQualitative code_responsabilite, String dateDebut, String dateFin) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, GESTIONNAIREJEU_NONDEFINI), nom);
    }

    @Override
    protected ModelGridMetadata<GestionnaireJeu> initModelGridMetadata() {
        jeuxPossibles = getCodeJeuxPossibles();
        responsabilitePossibles = getCodeResponsabilitePossibles();
        return super.initModelGridMetadata();
    }

    private String dateFin(GestionnaireJeu gestionnaireJeu) {
        return Optional.ofNullable(gestionnaireJeu)
                .map(s -> s.getDateFin())
                .map(d -> DateUtil.getUTCDateTextFromLocalDateTime(d, DateUtil.DD_MM_YYYY))
                .orElseGet(String::new);
    }
}
