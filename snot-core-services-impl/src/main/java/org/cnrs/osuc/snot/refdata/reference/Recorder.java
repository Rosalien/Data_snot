/**
 *
 */
package org.cnrs.osuc.snot.refdata.reference;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
public class Recorder extends AbstractCSVMetadataRecorder<Reference> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String REF_NONDEFINI = "REF_NONDEFINI";

    IReferenceDAO referenceDAO;

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
                String doi = tokenizerValues.nextToken();
                this.referenceDAO.remove(this.referenceDAO.getByDOI(doi).orElseThrow(() -> new BusinessException(getMessage(doi))));
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
     * @param reference
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(Reference reference) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();

        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(reference == null ? Constantes.CST_STRING_EMPTY : reference.getDoi(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(reference == null ? Constantes.CST_STRING_EMPTY : reference.getPremierAuteur(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(reference == null ? Constantes.CST_STRING_EMPTY : reference.getAnnee(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
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
                TokenizerValues tokenizerValues = new TokenizerValues(values, Reference.TABLE_NAME);
                String doi = tokenizerValues.nextToken();
                String premierAuteur = tokenizerValues.nextToken();
                String annee = tokenizerValues.nextToken();
                Reference dbReference = this.referenceDAO.getByDOI(doi)
                        .orElseGet(()->new Reference(doi, premierAuteur, annee));
                this._createOrUpdate(doi, premierAuteur, annee, dbReference);
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param referenceDAO the referenceDAO to set
     */
    public void setReferenceDAO(IReferenceDAO referenceDAO) {
        this.referenceDAO = referenceDAO;
    }

    /**
     * @param doi
     * @param premierAuteur
     * @param annee
     * @param titre
     * @param type
     * @param description
     * @param dbReference
     * @param reference
     * @throws PersistenceException
     */
    private void _createOrUpdate(String doi, String premierAuteur, String annee, Reference dbReference) throws PersistenceException {
        if (dbReference.getId()==null) {
            this.referenceDAO.saveOrUpdate(dbReference);
        } else {
            dbReference.setDoi(doi);
            dbReference.setPremierAuteur(premierAuteur);
            dbReference.setAnnee(annee);
            this.referenceDAO.saveOrUpdate(dbReference);
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
    protected List<Reference> getAllElements() throws BusinessException {
        return this.referenceDAO.getAll(Reference.class);
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, REF_NONDEFINI), nom);
    }

}