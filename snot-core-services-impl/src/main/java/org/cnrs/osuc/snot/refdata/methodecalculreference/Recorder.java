/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalculreference;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.cnrs.osuc.snot.refdata.methodecalcul.IMethodeCalculDAO;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
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
public class Recorder extends AbstractCSVMetadataRecorder<MethodeCalculReference> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";

    /**
     *
     */
    protected static final String MCALC_NONDEFINI = "MCALC_NONDEFINI";

    /**
     *
     */
    protected static final String REF_NONDEFINI = "REF_NONDEFINI";

    /**
     *
     */
    protected static final String REF_DOI_NONDEFINI = "REF_DOI_NONDEFINI";

    IMethodeCalculDAO methodeCalculDAO;
    IReferenceDAO referenceDAO;
    IMethodeCalculReferenceDAO methodeCalculReferenceDAO;

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
                String codeMethodecalcul = tokenizerValues.nextToken();
                String doiReference = tokenizerValues.nextToken();
                this.methodeCalculReferenceDAO.remove(this.methodeCalculReferenceDAO.getByCodeMCalcDoiRef(codeMethodecalcul, doiReference)
                        .orElseThrow(() -> new BusinessException(getMessage(codeMethodecalcul, doiReference))));
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
     * @param methodeCalculReference
     * @return
     * @throws BusinessException
     */

    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(MethodeCalculReference methodeCalculReference) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalculReference == null ? Constantes.CST_STRING_EMPTY : methodeCalculReference.getMethodeCalcul().getCode(), this._getCodeMethodeCalculPossibles(), null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(methodeCalculReference == null ? Constantes.CST_STRING_EMPTY : methodeCalculReference.getReferenceMCalc().getDoi(), this._getDoiReferencePossibles(), null, true, false, true));
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

                TokenizerValues tokenizerValues = new TokenizerValues(values, MethodeCalculReference.TABLE_NAME);
                String codeMethodecalcul = tokenizerValues.nextToken();
                String doiReference = tokenizerValues.nextToken();
                Optional<MethodeCalcul> methodeCalcul = this.methodeCalculDAO.getByCode(codeMethodecalcul);
                if(!methodeCalcul.isPresent()){
                    errorsReport.addErrorMessage(getMessageMethode(codeMethodecalcul));
                    continue;
                }
                Optional<Reference> reference = this.referenceDAO.getByDOI(doiReference);
                if(!reference.isPresent()){
                    errorsReport.addErrorMessage(getMessage(doiReference));
                    continue;
                }
                if (errorsReport.hasErrors()) {
                    continue;
                }
                Optional<MethodeCalculReference> dbMethodeCalculReference = this.methodeCalculReferenceDAO.getByCodeMCalcDoiRef(codeMethodecalcul, doiReference);
                if (!dbMethodeCalculReference.isPresent()) {
                    MethodeCalculReference methodeCalculReference = new MethodeCalculReference(methodeCalcul.get(), reference.get());
                    this.methodeCalculReferenceDAO.saveOrUpdate(methodeCalculReference);
                }
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
     * @param methodeCalculReferenceDAO the methodeCalculReferenceDAO to set
     */
    public void setMethodeCalculReferenceDAO(IMethodeCalculReferenceDAO methodeCalculReferenceDAO) {
        this.methodeCalculReferenceDAO = methodeCalculReferenceDAO;
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
    private String[] _getCodeMethodeCalculPossibles() {
        List<MethodeCalcul> lstMethodeCalculs = this.methodeCalculDAO.getAll(MethodeCalcul.class);
        String[] codeMethodeCalculPossibles = new String[lstMethodeCalculs.size()];
        int index = 0;
        for (MethodeCalcul methodeCalcul : lstMethodeCalculs) {
            codeMethodeCalculPossibles[index++] = methodeCalcul.getCode();
        }
        return codeMethodeCalculPossibles;
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
    protected List<MethodeCalculReference> getAllElements() throws BusinessException {
        return this.methodeCalculReferenceDAO.getAll(MethodeCalculReference.class);
    }

    private String getMessage(String nom, String doi) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, REF_DOI_NONDEFINI), doi, nom);
    }

    private String getMessage(String doi) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, REF_NONDEFINI), doi);
    }

    private String getMessageMethode(String methodeCalcul) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, MCALC_NONDEFINI), methodeCalcul);
    }

}