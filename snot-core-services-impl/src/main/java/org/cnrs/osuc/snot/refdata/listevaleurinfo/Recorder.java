/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
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
public class Recorder extends AbstractCSVMetadataRecorder<ItemListe> {

    private static final Logger LOGGER = Logger.getLogger(Recorder.class);
    private static final String LSTINFOVAL_NONDEFINI = "LSTINFOVAL_NONDEFINI";
    private static final String LSTINFOVAL_3ARGS_NONDEFINI = "LSTINFOVAL_3ARGS_NONDEFINI";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    IListeValeurInfoDAO listeValeurInfoDAO;
    IItemListeDAO itemListeDAO;

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
                ErrorsReport errorsReport = new ErrorsReport();
                String nom = tokenizerValues.nextToken();

                tokenizerValues.nextToken();
                String libelle = tokenizerValues.nextToken();
                tokenizerValues.nextToken();
                String note = tokenizerValues.nextToken();

                Optional<ListeValeurInfo> listeValeurInfo = this.listeValeurInfoDAO.getByNom(nom);
                if (!listeValeurInfo.isPresent()) {
                    errorsReport.addErrorMessage(getMessageListeValeurInfo(nom));
                    continue;
                }
                Optional<ItemListe> itemListe = this.itemListeDAO.getByLibelleNoteListeValeurInfo(libelle, note, listeValeurInfo.get());
                if (!itemListe.isPresent()) {
                    throw new BusinessException(getMessageItemListe(libelle, note, nom));
                }
                List<ItemListe> lstItem = this.itemListeDAO.getByListeValeurInfo(listeValeurInfo.get());
                if (lstItem.size() > 1) {
                    this.itemListeDAO.remove(itemListe.get());
                } else {
                    this.listeValeurInfoDAO.remove(listeValeurInfo.get());
                }

            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * @return the itemListeDAO
     */
    public IItemListeDAO getItemListeDAO() {
        return this.itemListeDAO;
    }

    /**
     * @param itemListeDAO the itemListeDAO to set
     */
    public void setItemListeDAO(IItemListeDAO itemListeDAO) {
        this.itemListeDAO = itemListeDAO;
    }

    /**
     * @return the listeValeurInfoDAO
     */
    public IListeValeurInfoDAO getListeValeurInfoDAO() {
        return this.listeValeurInfoDAO;
    }

    /**
     * @param listeValeurInfoDAO the listeValeurInfoDAO to set
     */
    public void setListeValeurInfoDAO(IListeValeurInfoDAO listeValeurInfoDAO) {
        this.listeValeurInfoDAO = listeValeurInfoDAO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
     */

    /**
     *
     * @param itemliste
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(ItemListe itemliste) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesNameNom = this.localizationManager.newProperties(ListeValeurInfo.TABLE_NAME, RefDataConstantes.COLUMN_NOM_LSTVALINFO, Locale.ENGLISH);
        Properties propertiesNameLibelle = this.localizationManager.newProperties(ListeValeurInfo.TABLE_NAME, RefDataConstantes.COLUMN_LIBELLE_ITEMLST, Locale.ENGLISH);
        String localizedChampNameNom = "";
        String localizedChampNameLibelle = "";
        ListeValeurInfo listeValeurInfo = null;
        if (itemliste != null) {
            listeValeurInfo = itemliste.getListeValeurInfo();
            localizedChampNameLibelle = propertiesNameLibelle.containsKey(itemliste.getLibelle()) ? propertiesNameLibelle.getProperty(itemliste.getLibelle()) : itemliste.getLibelle();
        }
        if (listeValeurInfo != null) {
            localizedChampNameNom = propertiesNameNom.containsKey(listeValeurInfo.getNom()) ? propertiesNameNom.getProperty(listeValeurInfo.getNom()) : listeValeurInfo.getNom();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(listeValeurInfo == null ? Constantes.CST_STRING_EMPTY : listeValeurInfo.getNom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(listeValeurInfo == null ? Constantes.CST_STRING_EMPTY : localizedChampNameNom, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(itemliste == null ? Constantes.CST_STRING_EMPTY : itemliste.getLibelle(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(itemliste == null ? Constantes.CST_STRING_EMPTY : localizedChampNameLibelle, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(itemliste == null ? Constantes.CST_STRING_EMPTY : itemliste.getNote(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, ListeValeurInfo.TABLE_NAME);
                String nom = tokenizerValues.nextToken();
                String libelle = tokenizerValues.nextToken();
                String note = tokenizerValues.nextToken();
                ListeValeurInfo dbListeValeurInfo = this.listeValeurInfoDAO.getByNom(nom).orElseGet(()->new ListeValeurInfo(nom));
                if (dbListeValeurInfo.getId()==null) {
                    listeValeurInfoDAO.saveOrUpdate(dbListeValeurInfo);
                    ItemListe dbItemListe = this.itemListeDAO.getByLibelleListeValeurInfo(libelle, dbListeValeurInfo)
                            .orElse(null);
                    if (dbItemListe!=null) {
                        dbItemListe.setLibelle(libelle);
                        dbItemListe.setNote(note);
                        this.listeValeurInfoDAO.saveOrUpdate(dbListeValeurInfo);
                    } else {
                        ItemListe itemliste = new ItemListe(libelle, note);
                        itemliste.setListeValeurInfo(dbListeValeurInfo);
                        dbListeValeurInfo.addItemListe(itemliste);
                        this.listeValeurInfoDAO.saveOrUpdate(dbListeValeurInfo);
                    }
                } else {
                    ItemListe itemliste = new ItemListe(libelle, note);
                    itemliste.setListeValeurInfo(dbListeValeurInfo);
                    dbListeValeurInfo.addItemListe(itemliste);
                    this.listeValeurInfoDAO.saveOrUpdate(dbListeValeurInfo);
                }
            }
        } catch (IOException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        } catch (PersistenceException e) {
            LOGGER.debug(e.getMessage());
            throw new BusinessException(e.getMessage(), e);
        }
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
    protected List<ItemListe> getAllElements() throws BusinessException {
        return this.itemListeDAO.getAll(ItemListe.class);
    }

    private String getMessageListeValeurInfo(String nomListeValeur) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, LSTINFOVAL_NONDEFINI), nomListeValeur);
    }

    private String getMessageItemListe(String libelle, String note, String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, LSTINFOVAL_3ARGS_NONDEFINI), nom, libelle, note);
    }

}