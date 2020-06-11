/**
 *
 */
package org.cnrs.osuc.snot.refdata.informationcomplementaire;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.IListeValeurInfoDAO;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ListeValeurInfo;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author sophie
 * 
 */
public class Recorder extends AbstractCSVMetadataRecorder<InformationComplementaire> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String INFOCPLT_NONDEFINI = "INFOCPLT_NONDEFINI";

    /**
     *
     */
    protected static final String LSTINFOVAL_NONDEFINI = "LSTINFOVAL_NONDEFINI";

    IInformationComplementaireDAO infoComplementaireDAO;
    IListeValeurInfoDAO listeValeurInfoDAO;

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
                String nom = tokenizerValues.nextToken();
                final Optional<InformationComplementaire> icOpt = this.infoComplementaireDAO.getByNom(nom);
                InformationComplementaire informationComplementaire = icOpt.orElseThrow(() -> new BusinessException(getMessage(nom)));
                this.infoComplementaireDAO.remove(informationComplementaire);
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
     * @param infoComplementaire
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(InformationComplementaire infoComplementaire) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesNameDescription = this.localizationManager.newProperties(InformationComplementaire.TABLE_NAME, RefDataConstantes.COLUMN_DESCRIPTION_INFCPLT, Locale.ENGLISH);
        String localizedChampNameDescription = "";
        if (infoComplementaire != null) {
            localizedChampNameDescription = propertiesNameDescription.containsKey(infoComplementaire.getDescription()) ? propertiesNameDescription.getProperty(infoComplementaire.getDescription()) : infoComplementaire.getDescription();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoComplementaire == null ? Constantes.CST_STRING_EMPTY : infoComplementaire.getNom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(infoComplementaire == null ? Constantes.CST_STRING_EMPTY : infoComplementaire.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoComplementaire == null ? Constantes.CST_STRING_EMPTY : localizedChampNameDescription, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(infoComplementaire == null ? Constantes.CST_STRING_EMPTY : infoComplementaire.getListeValeurInfo() == null ? Constantes.CST_STRING_EMPTY : infoComplementaire.getListeValeurInfo().getNom(), this._getNomsListeValInfoPossibles(), null, false, false, false));
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
        try {
            this.skipHeader(parser);
            String[] values = null;
            ErrorsReport errorsReport = new ErrorsReport();
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values, InformationComplementaire.TABLE_NAME);
                String nom = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                String nomListeValeur = tokenizerValues.nextToken();
                ListeValeurInfo listeValeurInfo = this.listeValeurInfoDAO.getByNom(nomListeValeur)
                        .orElse(null);
                InformationComplementaire infoComplementaire = new InformationComplementaire(nom, description, listeValeurInfo);                
                InformationComplementaire dbInfoComplementaire = this.infoComplementaireDAO.getByNom(nom)
                        .orElse(null);
                if(errorsReport.hasErrors()){
                    continue;
                }
                this._createOrUpdate(nom, description, listeValeurInfo, dbInfoComplementaire, infoComplementaire);
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }
    /**
     * @param infoComplementaireDAO
     *            the infoComplementaireDAO to set
     */
    public void setInfoComplementaireDAO(IInformationComplementaireDAO infoComplementaireDAO) {
        this.infoComplementaireDAO = infoComplementaireDAO;
    }

    /**
     * @param listeValeurInfoDAO
     *            the listeValeurInfoDAO to set
     */
    public void setListeValeurInfoDAO(IListeValeurInfoDAO listeValeurInfoDAO) {
        this.listeValeurInfoDAO = listeValeurInfoDAO;
    }

    /**
     * @param nom
     * @param description
     * @param listeValeurInfo
     * @param dbInfoComplementaire
     * @param infoComplementaire
     * @throws PersistenceException
     */
    private void _createOrUpdate(String nom, String description, ListeValeurInfo listeValeurInfo, InformationComplementaire dbInfoComplementaire, InformationComplementaire infoComplementaire) throws PersistenceException {
        if (dbInfoComplementaire == null) {
            this.infoComplementaireDAO.saveOrUpdate(infoComplementaire);
        } else {
            dbInfoComplementaire.setNom(nom);
            dbInfoComplementaire.setDescription(description);
            dbInfoComplementaire.setListeValeurInfo(listeValeurInfo);
            this.infoComplementaireDAO.saveOrUpdate(dbInfoComplementaire);
        }
    }

    /**
     * @return
     * @throws PersistenceException
     */
    private String[] _getNomsListeValInfoPossibles() {
        List<ListeValeurInfo> lstListeValInfos = this.listeValeurInfoDAO.getAll(ListeValeurInfo.class);
        String[] codeListeValInfoPossibles = new String[lstListeValInfos.size() + 1];
        codeListeValInfoPossibles[0] = Constantes.CST_STRING_EMPTY;
        int index = 1;
        for (ListeValeurInfo listeVInf : lstListeValInfos) {
            codeListeValInfoPossibles[index++] = listeVInf.getNom();
        }
        return codeListeValInfoPossibles;
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
    protected List<InformationComplementaire> getAllElements() throws BusinessException {
        return this.infoComplementaireDAO.getAll(InformationComplementaire.class);
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, INFOCPLT_NONDEFINI), nom);
    }

    private String getMessageListeValeurInfo(String nomListeValeur) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, LSTINFOVAL_NONDEFINI), nomListeValeur);
    }


}