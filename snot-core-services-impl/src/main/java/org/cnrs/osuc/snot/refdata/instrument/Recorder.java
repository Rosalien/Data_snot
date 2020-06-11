/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrument;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
public class Recorder extends AbstractCSVMetadataRecorder<Instrument> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String INSTR_NONDEFINI = "INSTR_NONDEFINI";

    IInstrumentDAO instrumentDAO;

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
                this.instrumentDAO.remove(this.instrumentDAO.getByCode(code).orElseThrow(()-> new BusinessException(getMessage(code))));
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
     * @param instrument
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(Instrument instrument) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesNameLibelle = this.localizationManager.newProperties(Instrument.TABLE_NAME, RefDataConstantes.COLUMN_LIBELLE_INSTR, Locale.ENGLISH);
        Properties propertiesNameInfosCal = this.localizationManager.newProperties(Instrument.TABLE_NAME, RefDataConstantes.COLUMN_INFOSCAL_INSTR, Locale.ENGLISH);
        Properties propertiesNameFabricant = this.localizationManager.newProperties(Instrument.TABLE_NAME, RefDataConstantes.COLUMN_FABRICANT_INSTR, Locale.ENGLISH);
        Properties propertiesNameDescription = this.localizationManager.newProperties(Instrument.TABLE_NAME, RefDataConstantes.COLUMN_DESCRIPT_INSTR, Locale.ENGLISH);
        String localizedChampNameLibelle = "";
        String localizedChampNameInfosCal = "";
        String localizedChampNameFabricant = "";
        String localizedChampNameDescription = "";
        if (instrument != null) {
            localizedChampNameLibelle = propertiesNameLibelle.containsKey(instrument.getLibelle()) ? propertiesNameLibelle.getProperty(instrument.getLibelle()) : instrument.getLibelle();
            localizedChampNameInfosCal = propertiesNameInfosCal.containsKey(instrument.getInfosCalibration()) ? propertiesNameInfosCal.getProperty(instrument.getInfosCalibration()) : instrument.getInfosCalibration();
            localizedChampNameFabricant = propertiesNameFabricant.containsKey(instrument.getFabricant()) ? propertiesNameFabricant.getProperty(instrument.getFabricant()) : instrument.getFabricant();
            localizedChampNameDescription = propertiesNameDescription.containsKey(instrument.getDescription()) ? propertiesNameDescription.getProperty(instrument.getDescription()) : instrument.getDescription();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : instrument.getCode(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : instrument.getLibelle(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : localizedChampNameLibelle, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : instrument.getInfosCalibration(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : localizedChampNameInfosCal, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : instrument.getFabricant(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : localizedChampNameFabricant, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : instrument.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrument == null ? Constantes.CST_STRING_EMPTY : localizedChampNameDescription, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, Instrument.TABLE_NAME);
                String code = tokenizerValues.nextToken();
                String libelle = tokenizerValues.nextToken();
                String infosCalibration = tokenizerValues.nextToken();
                String fabricant = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                Optional<Instrument> dbInstrument = this.instrumentDAO.getByCode(code);
                Instrument instrument = dbInstrument.orElseGet(()-> new Instrument(code, libelle, infosCalibration, fabricant, description));
                this._createOrUpdate(code, libelle, infosCalibration, fabricant, description, dbInstrument.orElse(null), instrument);
            }
        }catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param instrumentDAO
     *            the instrumentDAO to set
     */
    public void setInstrumentDAO(IInstrumentDAO instrumentDAO) {
        this.instrumentDAO = instrumentDAO;
    }

    /**
     * @param code
     * @param libelle
     * @param infosCalibration
     * @param fabricant
     * @param description
     * @param dbInstrument
     * @param instrument
     * @throws PersistenceException
     */
    private void _createOrUpdate(String code, String libelle, String infosCalibration, String fabricant, String description, Instrument dbInstrument, Instrument instrument) throws PersistenceException {
        if (dbInstrument == null) {
            this.instrumentDAO.saveOrUpdate(instrument);
        } else {
            dbInstrument.setCode(code);
            dbInstrument.setLibelle(libelle);
            dbInstrument.setInfosCalibration(infosCalibration);
            dbInstrument.setFabricant(fabricant);
            dbInstrument.setDescription(description);
            this.instrumentDAO.saveOrUpdate(dbInstrument);
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
    protected List<Instrument> getAllElements() throws BusinessException {
        return this.instrumentDAO.getAll(Instrument.class);
    }

    private String getMessage(String code) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, INSTR_NONDEFINI), code);
    }

}