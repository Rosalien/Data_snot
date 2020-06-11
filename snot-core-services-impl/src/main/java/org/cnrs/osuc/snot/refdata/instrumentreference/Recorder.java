/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrumentreference;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.cnrs.osuc.snot.refdata.instrument.IInstrumentDAO;
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.reference.IReferenceDAO;
import org.cnrs.osuc.snot.refdata.reference.Reference;
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
public class Recorder extends AbstractCSVMetadataRecorder<InstrumentReference> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String REF_DOI_NONDEFINI = "REF_DOI_NONDEFINI";

    /**
     *
     */
    protected static final String REF_NONDEFINI = "REF_DOI_NONDEFINI";

    /**
     *
     */
    protected static final String INSTRUMENT_NONDEFINI = "INSTRUMENT_NONDEFINI";

    IInstrumentDAO instrumentDAO;
    IReferenceDAO referenceDAO;
    IInstrumentReferenceDAO instrumentReferenceDAO;

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
                String codeInstrument = tokenizerValues.nextToken();
                String doiReference = tokenizerValues.nextToken();
                this.instrumentReferenceDAO.remove(this.instrumentReferenceDAO.getByCodeInstrDoiRef(codeInstrument, doiReference).orElseThrow(() -> new BusinessException(getMessage(codeInstrument, doiReference))));
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
     * @param instrumentReference
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(InstrumentReference instrumentReference) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrumentReference == null ? Constantes.CST_STRING_EMPTY : instrumentReference.getInstrument().getCode(), this._getCodeInstrumentPossibles(), null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(instrumentReference == null ? Constantes.CST_STRING_EMPTY : instrumentReference.getReferenceInstrument().getDoi(), this._getDoiReferencePossibles(), null, true, false, true));
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

                TokenizerValues tokenizerValues = new TokenizerValues(values, InstrumentReference.TABLE_NAME);
                String codeInstrument = tokenizerValues.nextToken();
                String doiReference = tokenizerValues.nextToken();
                Optional<Reference> reference = this.referenceDAO.getByDOI(doiReference);
                if (!reference.isPresent()) {
                    errorsReport.addErrorMessage(getMessage(doiReference));
                }
                Optional<Instrument> instrument = this.instrumentDAO.getByCode(codeInstrument);
                if (!instrument.isPresent()) {
                    errorsReport.addErrorMessage(getMessageInstrument(codeInstrument));
                }
                if (errorsReport.hasErrors()) {
                    continue;
                }
                InstrumentReference instrumentReference = this.instrumentReferenceDAO.getByCodeInstrDoiRef(codeInstrument, doiReference)
                        .orElseGet(() -> new InstrumentReference(instrument.get(), reference.get()));
                this.instrumentReferenceDAO.saveOrUpdate(instrumentReference);
            }
            if (errorsReport.hasErrors()) {
                throw new BusinessException(errorsReport.getErrorsMessages());
            }
        } catch (IOException | PersistenceException e1) {
            throw new BusinessException(e1.getMessage(), e1);
        }
    }

    /**
     * @param instrumentDAO the instrumentDAO to set
     */
    public void setInstrumentDAO(IInstrumentDAO instrumentDAO) {
        this.instrumentDAO = instrumentDAO;
    }

    /**
     * @param instrumentReferenceDAO the instrumentReferenceDAO to set
     */
    public void setInstrumentReferenceDAO(IInstrumentReferenceDAO instrumentReferenceDAO) {
        this.instrumentReferenceDAO = instrumentReferenceDAO;
    }

    /**
     * @param referenceDAO the referenceDAO to set
     */
    public void setReferenceDAO(IReferenceDAO referenceDAO) {
        this.referenceDAO = referenceDAO;
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getCodeInstrumentPossibles() {
        return this.instrumentDAO.getAll(Instrument.class)
                .stream()
                .map(Instrument::getCode)
                .collect(Collectors.toSet())
                .toArray(new String[0]);
    }

    /**
     * @return @throws PersistenceException
     */
    private String[] _getDoiReferencePossibles() {
        List<Reference> lstReferences = this.referenceDAO.getAll(Reference.class);
        String[] doiReferencePossibles = new String[lstReferences.size()];
        int index = 0;
        for (Reference reference : lstReferences) {
            doiReferencePossibles[index++] = reference.getDoi();
        }
        return doiReferencePossibles;
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
    protected List<InstrumentReference> getAllElements() throws BusinessException {
        return this.instrumentReferenceDAO.getAll(InstrumentReference.class);
    }

    private String getMessage(String nom, String doi) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, REF_DOI_NONDEFINI), doi, nom);
    }

    private String getMessage(String doi) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, REF_NONDEFINI), doi);
    }

    private String getMessageInstrument(String codeInstrument) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, INSTRUMENT_NONDEFINI), codeInstrument);
    }

}