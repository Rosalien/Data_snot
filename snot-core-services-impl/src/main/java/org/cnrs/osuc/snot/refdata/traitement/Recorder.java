/**
 *
 */
package org.cnrs.osuc.snot.refdata.traitement;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author sophie
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<Traitement> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String TREATMENT_NONDEFINI = "TREATMENT_NONDEFINI";

    /**
     *
     */
    protected ISiteSnotDAO siteSnotDAO;

    /**
     *
     */
    protected ITraitementDAO traitementDAO;

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
                tokenizerValues.nextToken();
                String codeTraitement = tokenizerValues.nextToken();
                this.traitementDAO.remove(this.traitementDAO.getByCode(codeTraitement).orElseThrow(() -> new BusinessException(getMessage(codeTraitement))));
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
     * @param traitement
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(Traitement traitement) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesNameLibelle = this.localizationManager.newProperties(Traitement.TABLE_NAME, RefDataConstantes.COLUMN_LIBELLE_TRT, Locale.ENGLISH);
        Properties propertiesNameDescription = this.localizationManager.newProperties(Traitement.TABLE_NAME, RefDataConstantes.COLUMN_DESCRIPT_TRT, Locale.ENGLISH);
        String localizedChampNameLibelle = "";
        String localizedChampNameDescription = "";
        if (traitement != null) {
            localizedChampNameLibelle = propertiesNameLibelle.containsKey(traitement.getLibelle()) ? propertiesNameLibelle.getProperty(traitement.getLibelle()) : traitement.getLibelle();
            localizedChampNameDescription = propertiesNameDescription.containsKey(traitement.getDescription()) ? propertiesNameDescription.getProperty(traitement.getDescription()) : traitement.getDescription();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : traitement.getZoneEtude().getDisplayPath(), this._getCodeZoneEtudePossibles(), null, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : traitement.getCode(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : traitement.getLibelle(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : localizedChampNameLibelle, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : traitement.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(traitement == null ? Constantes.CST_STRING_EMPTY : localizedChampNameDescription, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
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

                TokenizerValues tokenizerValues = new TokenizerValues(values, Traitement.TABLE_NAME);
                String codeSite = Utils.createCodeFromString(tokenizerValues.nextToken());
                String codeTraitement = tokenizerValues.nextToken();
                String libelle = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                Optional<SiteSnot> zoneEtude = this.siteSnotDAO.getByCode(codeSite);
                if (!zoneEtude.isPresent()) {
                    errorsReport.addErrorMessage(String.format(this.localizationManager.getMessage(Recorder.BUNDLE_SOURCE_PATH, "SITE_NONDEFINI"), codeSite));
                    continue;
                }
                Traitement dbTraitement = this.traitementDAO.getByCode(codeTraitement)
                        .orElseGet(()->new Traitement(zoneEtude.get(), codeTraitement, libelle, description));
                if (!errorsReport.hasErrors()) {
                    this._createOrUpdate(zoneEtude.get(), codeTraitement, libelle, description, dbTraitement);
                }
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param siteSnotDAO the siteSnotDAO to set
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteSnotDAO) {
        this.siteSnotDAO = siteSnotDAO;
    }

    /**
     * @param traitementDAO the traitementDAO to set
     */
    public void setTraitementDAO(ITraitementDAO traitementDAO) {
        this.traitementDAO = traitementDAO;
    }

    /**
     * @param zoneEtude
     * @param codeTraitement
     * @param libelle
     * @param description
     * @param dbTraitement
     * @param traitement
     * @throws PersistenceException
     */
    private void _createOrUpdate(SiteSnot zoneEtude, String codeTraitement, String libelle, String description, Traitement dbTraitement) throws PersistenceException {
        if (dbTraitement.getId()==null) {
            this.traitementDAO.saveOrUpdate(dbTraitement);
        } else {
            dbTraitement.setZoneEtude(zoneEtude);
            dbTraitement.setCode(codeTraitement);
            dbTraitement.setLibelle(libelle);
            dbTraitement.setDescription(description);
            this.traitementDAO.saveOrUpdate(dbTraitement);
        }
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getCodeZoneEtudePossibles() {
        List<Site> sites = this.siteSnotDAO.getAll();
        String[] codesSitesPossibles = new String[sites.size()];
        int index = 0;
        for (Site site : sites) {
            codesSitesPossibles[index++] = site.getDisplayPath();
        }
        return codesSitesPossibles;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
     */

    /**
     *
     * @return
     * @throws BusinessException
     */

    @Override
    protected List<Traitement> getAllElements() throws BusinessException {
        return this.traitementDAO.getAll(Traitement.class);
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, TREATMENT_NONDEFINI), nom);
    }

}