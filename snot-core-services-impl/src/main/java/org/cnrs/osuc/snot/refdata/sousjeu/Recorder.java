/**
 *
 */
package org.cnrs.osuc.snot.refdata.sousjeu;

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
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author jbparoissien
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<SousJeu> {

    private static final String DATEFORMAT_ERROR = "DATEFORMAT_ERROR";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     * import org.assertj.core.util.Strings;
     */
    protected static final String SOUSJEU_NONDEFINI = "SOUSJEU";

    /**
     * The Constant PROPERTY_MSG_BAD_DATE_FORMAT @link(String).
     */
    static final String PROPERTY_MSG_BAD_DATE_FORMAT = "PROPERTY_MSG_BAD_DATE_FORMAT";

    /**
     *
     */
    protected ISousJeuDAO sousJeuDAO;

    /**
     *
     */
    protected IJeuDAO jeuDAO;
    private String[] jeuxPossibles;

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
                String titre = tokenizerValues.nextToken();
                String doi = tokenizerValues.nextToken();
                String citation = tokenizerValues.nextToken();
                String titre_licence = tokenizerValues.nextToken();
                String url_licence = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                LocalDate finPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();

                this.sousJeuDAO.remove(this.sousJeuDAO.getByKey(codeJeu, doi)
                        .orElseThrow(() -> new BusinessException("bad doi")));
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
     * @param sousjeu
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(SousJeu sousJeu) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        String dateDebut = "";
        String dateFin = "";
        
        if (sousJeu != null) {
            dateDebut = DateUtil.getUTCDateTextFromLocalDateTime(sousJeu.getDateDebut(), DateUtil.DD_MM_YYYY);
            dateFin = DateUtil.getUTCDateTextFromLocalDateTime(sousJeu.getDateFin(), DateUtil.DD_MM_YYYY);
        }
        
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getJeu().getCodeJeu(), jeuxPossibles, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getTitre(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_TITRE_JEU, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getDoi(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_DOI_JEU, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getCitation(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_CITATION_JEU, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getTitre_licence(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_TITRE_LICENCE_JEU, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : sousJeu.getUrl_licence(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_URL_LICENCE_JEU, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : dateDebut, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(sousJeu == null ? Constantes.CST_STRING_EMPTY : dateFin, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, SousJeu.TABLE_NAME);
                String codeJeu = tokenizerValues.nextToken();
                String titre = tokenizerValues.nextToken();
                String doi = tokenizerValues.nextToken();
                String citation = tokenizerValues.nextToken();
                String titre_licence = tokenizerValues.nextToken();
                String url_licence = tokenizerValues.nextToken();
                String dateDebut = tokenizerValues.nextToken();
                String dateFin = tokenizerValues.nextToken();
                LocalDate debutPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebut).toLocalDate();
                LocalDate finPeriode = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFin).toLocalDate();

                Optional<Jeu> code_jeu = this.jeuDAO.getByCode(codeJeu);

                if (!code_jeu.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "JEU_NONDEFINI"), codeJeu));
                    continue;
                }
                SousJeu dbSousJeu;
                dbSousJeu = this.sousJeuDAO.getByKey(codeJeu, doi)
                        .orElseGet(() -> new SousJeu(code_jeu.get(), titre, doi, citation, titre_licence, url_licence, debutPeriode, finPeriode));
                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(code_jeu.get(), titre, doi, citation, titre_licence, url_licence, debutPeriode, finPeriode, dbSousJeu);
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
     * @param sousJeuDAO the sousJeuDAO to set
     */
    public void setSousJeuDAO(ISousJeuDAO sousJeuDAO) {
        this.sousJeuDAO = sousJeuDAO;
    }

    /**
     * @param code_jeu
     * @param titre
     * @param doi
     * @param citation
     * @param titre_licence
     * @param url_licence
     * @param dateDebut
     * @param dateFin
     * @param dbSousJeu
     * @throws PersistenceException
     */
    private void _createOrUpdate(Jeu code_jeu, String doi, String titre, String citation, String titre_licence, String url_licence, LocalDate dateDebut, LocalDate dateFin, SousJeu dbSousJeu) throws PersistenceException {
        if (dbSousJeu.getId() == null) {
            this.sousJeuDAO.saveOrUpdate(dbSousJeu);
        } else {
            dbSousJeu.setJeu(code_jeu);
            dbSousJeu.setTitre(titre);
            dbSousJeu.setDoi(doi);
            dbSousJeu.setCitation(citation);
            dbSousJeu.setTitre_licence(titre_licence);
            dbSousJeu.setUrl_licence(url_licence);
            dbSousJeu.setDateDebut(dateDebut);
            dbSousJeu.setDateFin(dateFin);
            this.sousJeuDAO.saveOrUpdate(dbSousJeu);
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

    @Override
    protected ModelGridMetadata<SousJeu> initModelGridMetadata() {
        jeuxPossibles = getCodeJeuxPossibles();
        return super.initModelGridMetadata();
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
    protected List<SousJeu> getAllElements() throws BusinessException {
        return this.sousJeuDAO.getAll(SousJeu.class);
    }

    private String getMessage(Jeu codejeu, String titre, String doi, String citation, String titre_licence, String url_licence, String dateDebut, String dateFin) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, SOUSJEU_NONDEFINI), doi);
    }

}
