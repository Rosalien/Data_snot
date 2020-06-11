/**
 *
 */
package org.cnrs.osuc.snot.refdata.jeu;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author jbparoissien
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<Jeu> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String JEU_NONDEFINI = "JEU_NONDEFINI";

    /**
     *
     */
    protected static final String SITE_NONDEFINI = "SITE_NONDEFINI";

    /**
     *
     */
    protected static final String THEME_NONDEFINI = "THEME_NONDEFINI";

    /**
     *
     */
    protected ISiteSnotDAO siteSnotDAO;

    /**
     *
     */
    protected IThemeDAO themeDAO;

    /**
     *
     */
//    protected IDatatypeDAO datatypeDAO;
    /**
     *
     */
    protected IJeuDAO jeuDAO;
    private String[] sitesPossibles;
    private String[] themesPossibles;
//    private String[] datatypesPossibles;
//    private String[] stdtPossibles;

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
                this.jeuDAO.remove(this.jeuDAO.getByCode(codeJeu).orElseThrow(() -> new BusinessException(getMessage(codeJeu))));
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
     * @param jeu
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(Jeu jeu) throws BusinessException {
        {
            LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getCodeJeu(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_CODE_JEU, true, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getSite().getCode(), sitesPossibles, RefDataConstantes.COLUMN_CODESITE_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getTheme().getCode(), themesPossibles, RefDataConstantes.COLUMN_CODETHEME_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getTitre(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_TITRE_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_DESCRIPTION_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getObjectif(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_OBJECTIF_JEU, false, false, false));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getGenealogie(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_GENEALOGIE_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getDoi(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_DOI_JEU, false, false, false));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getCitation(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_CITATION_JEU, false, false, true));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getTitre_licence(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_TITRE_LICENCE_JEU, false, false, false));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getUrl_licence(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_URL_LICENCE_JEU, false, false, false));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getUrl_download(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_URL_DOWNLOAD_JEU, false, false, false));
            lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(jeu == null ? Constantes.CST_STRING_EMPTY : jeu.getUrl_info(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, RefDataConstantes.COLUMN_URL_INFO_JEU, false, false, false));
            return lineModelGridMetadata;
        }
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, Jeu.TABLE_NAME);
                String codeJeu = tokenizerValues.nextToken();
                String codeSite = Utils.createCodeFromString(tokenizerValues.nextToken());
                String codeTheme = Utils.createCodeFromString(tokenizerValues.nextToken());
                String titre = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                String objectif = tokenizerValues.nextToken();
                String genealogie = tokenizerValues.nextToken();
                String doi = tokenizerValues.nextToken();
                String citation = tokenizerValues.nextToken();
                String titre_licence = tokenizerValues.nextToken();
                String url_licence = tokenizerValues.nextToken();
                String url_download = tokenizerValues.nextToken();
                String url_info = tokenizerValues.nextToken();
//                Optional<RealNode> realnode = mgaServiceBuilder.getRecorder().getRealNodeByNKey(codeJeu);
//                if (!realnode.isPresent()) {
//                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "REALNODE_NONDEFINI"), codeJeu));
//                    continue;
//                }
                Optional<SiteSnot> code_site = this.siteSnotDAO.getByCode(codeSite);
                if (!code_site.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "SITE_NONDEFINI"), codeSite));
                    continue;
                }
                Optional<Theme> code_theme = this.themeDAO.getByCode(codeTheme);
                if (!code_theme.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "THEME_NONDEFINI"), codeTheme));
                    continue;
                }
//                Optional<DataType> code_datatype = this.datatypeDAO.getByCode(codeDatatype);
//                if (!code_theme.isPresent()) {
//                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "DATATYPE_NONDEFINI"), codeDatatype));
//                    continue;
//                }
                Jeu dbJeu = this.jeuDAO.getByCode(codeJeu)
                        .orElseGet(() -> new Jeu(codeJeu, code_site.get(), code_theme.get(), titre, description, objectif, genealogie, doi, citation, titre_licence, url_licence, url_download, url_info));
                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(codeJeu, code_site.get(), code_theme.get(), titre, description, objectif, genealogie, doi, citation, titre_licence, url_licence, url_download, url_info, dbJeu);
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
     *
     *
     **
     * @param codeJeu
     * @param code_site
     * @param code_theme
     * @param titre
     * @param description
     * @param objectif
     * @param genealogie
     * @param dbJeu
     * @param jeu
     * @throws PersistenceException
     */
    private void _createOrUpdate(String codeJeu, SiteSnot code_site, Theme code_theme, String titre, String description, String objectif, String genealogie, String doi, String citation, String titre_licence,String url_licence, String url_download,String url_info,Jeu dbJeu) throws PersistenceException {
        if (dbJeu.getId() == null) {
            this.jeuDAO.saveOrUpdate(dbJeu);
        } else {
            dbJeu.setCodeJeu(codeJeu);
            dbJeu.setSite(code_site);
            dbJeu.setTheme(code_theme);
            dbJeu.setTitre(titre);
            dbJeu.setDescription(description);
            dbJeu.setObjectif(objectif);
            dbJeu.setGenealogie(genealogie);
            dbJeu.setDoi(doi);
            dbJeu.setCitation(citation);
            dbJeu.setTitre_licence(titre_licence);
            dbJeu.setUrl_licence(url_licence);
            dbJeu.setUrl_download(url_download);
            dbJeu.setUrl_info(url_info);
            this.jeuDAO.saveOrUpdate(dbJeu);
        }
    }
    
    /**
     * @return @throws PersistenceException
     */
    private String[] getCodeSitePossibles() {
        List<Site> sites = this.siteSnotDAO.getAll();
        String[] codesSitesPossibles = new String[sites.size()];
        int index = 0;
        for (Site site : sites) {
            codesSitesPossibles[index++] = site.getCode();
        }
        return codesSitesPossibles;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] getCodeThemesPossibles() {
        List<Theme> themes = this.themeDAO.getAll();
        String[] codesThemesPossibles = new String[themes.size()];
        int index = 0;
        for (Theme theme : themes) {
            codesThemesPossibles[index++] = theme.getName();
        }
        return codesThemesPossibles;
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
    protected List<Jeu> getAllElements() throws BusinessException {
        return this.jeuDAO.getAll(Jeu.class);
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, SITE_NONDEFINI), nom);
    }

    @Override
    protected ModelGridMetadata<Jeu> initModelGridMetadata() {
//        this.stdtPossibles = this._getStdtPossibles();
        themesPossibles = getCodeThemesPossibles();
        sitesPossibles = getCodeSitePossibles();
//        datatypesPossibles = getCodeDatatypePossibles();
        return super.initModelGridMetadata();
    }

    /**
     * @param siteSnotDAO the siteSnotDAO to set
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteSnotDAO) {
        this.siteSnotDAO = siteSnotDAO;
    }

    /**
     * @param themeDAO the siteSnotDAO to set
     */
    public void setThemeDAO(IThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

}
