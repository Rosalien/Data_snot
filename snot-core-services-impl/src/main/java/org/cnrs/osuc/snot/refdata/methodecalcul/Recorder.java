/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalcul;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
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
public class Recorder extends AbstractCSVMetadataRecorder<MethodeCalcul> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String METHODE_CALCUL_NON_DEFINE = "METHODE_CALCUL_NON_DEFINE";

    IMethodeCalculDAO methodeCalculDAO;

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
                String code = tokenizerValues.nextToken();
                this.methodeCalculDAO.remove(
                        this.methodeCalculDAO.getByCode(code)
                                .orElseThrow(() -> new BusinessException(getMessage(code))
                                )
                );
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
     * @param methodeCalcul
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(MethodeCalcul methodeCalcul) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesNameLibelle = this.localizationManager.newProperties(MethodeCalcul.TABLE_NAME, RefDataConstantes.COLUMN_LIBELLE_MCALC, Locale.ENGLISH);
        Properties propertiesNameDescription = this.localizationManager.newProperties(MethodeCalcul.TABLE_NAME, RefDataConstantes.COLUMN_DESCRIPT_MCALC, Locale.ENGLISH);
        String localizedChampNameLibelle = "";
        String localizedChampNameDescription = "";
        if (methodeCalcul != null) {
            localizedChampNameLibelle = propertiesNameLibelle.containsKey(methodeCalcul.getLibelle()) ? propertiesNameLibelle.getProperty(methodeCalcul.getLibelle()) : methodeCalcul.getLibelle();
            localizedChampNameDescription = propertiesNameDescription.containsKey(methodeCalcul.getDescription()) ? propertiesNameDescription.getProperty(methodeCalcul.getDescription()) : methodeCalcul.getDescription();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalcul == null ? Constantes.CST_STRING_EMPTY : methodeCalcul.getCode(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalcul == null ? Constantes.CST_STRING_EMPTY : methodeCalcul.getLibelle(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalcul == null ? Constantes.CST_STRING_EMPTY : localizedChampNameLibelle, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalcul == null ? Constantes.CST_STRING_EMPTY : methodeCalcul.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalcul == null ? Constantes.CST_STRING_EMPTY : localizedChampNameDescription, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
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
            while ((values = parser.getLine()) != null) {
                TokenizerValues tokenizerValues = new TokenizerValues(values, MethodeCalcul.TABLE_NAME);
                String code = tokenizerValues.nextToken();
                String libelle = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                MethodeCalcul dbMethodeCalcul = this.methodeCalculDAO.getByCode(code)
                        .orElseGet(()->new MethodeCalcul(code, libelle, description));
                this._createOrUpdate(code, libelle, description, dbMethodeCalcul);
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param methodeCalculDAO the methodeCalculDAO to set
     */
    public void setMethodeCalculDAO(IMethodeCalculDAO methodeCalculDAO) {
        this.methodeCalculDAO = methodeCalculDAO;
    }

    /**
     * @param code
     * @param libelle
     * @param description
     * @param dbMethodeCalcul
     * @param methodeCalcul
     * @throws PersistenceException
     */
    private void _createOrUpdate(String code, String libelle, String description, MethodeCalcul dbMethodeCalcul) throws PersistenceException {
        if (dbMethodeCalcul.getId()==null) {
            this.methodeCalculDAO.saveOrUpdate(dbMethodeCalcul);
        } else {
            dbMethodeCalcul.setCode(code);
            dbMethodeCalcul.setLibelle(libelle);
            dbMethodeCalcul.setDescription(description);
            this.methodeCalculDAO.saveOrUpdate(dbMethodeCalcul);
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
    protected List<MethodeCalcul> getAllElements() throws BusinessException {
        return this.methodeCalculDAO.getAll(MethodeCalcul.class);
    }

    private String getMessage(String code) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, METHODE_CALCUL_NON_DEFINE), code);
    }

}