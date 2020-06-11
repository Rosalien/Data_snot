/**
 * OREILacs project - see LICENCE.txt for use created: 7 avr. 2009 16:17:33
 */
package org.cnrs.osuc.snot.refdata.variable;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author "Philippe"
 */
public class Recorder extends AbstractCSVMetadataRecorder<VariableSnot> {

    /**
     *
     */
    protected IVariableSnotDAO variableDAO;
    Properties propertiesName;

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
                this.variableDAO.remove(this.variableDAO.getByCode(code)
                .orElseThrow(()->new BusinessException("bad varaible")));
            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param variable
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(VariableSnot variable) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(variable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : variable.getCode(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(variable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : variable.getDefinition(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(variable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : propertiesName.getProperty(variable.getDefinition(), variable.getDefinition()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(variable == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : variable.getTheiacategories(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, true, true));
        return lineModelGridMetadata;
    }

    /**
     *
     * @return
     */
    @Override
    protected ModelGridMetadata<VariableSnot> initModelGridMetadata() {
        propertiesName = this.localizationManager.newProperties(Nodeable.getLocalisationEntite(VariableSnot.class), Nodeable.ENTITE_COLUMN_NAME, Locale.ENGLISH);
        return super.initModelGridMetadata();
    }

    /**
     * @param parser
     * @param encoding
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        ErrorsReport errorsReport = new ErrorsReport();
        try {
            this.skipHeader(parser);
            String[] values = null;
            int lineNumber = 0;
            while ((values = parser.getLine()) != null) {
                lineNumber++;
                TokenizerValues tokenizerValues = new TokenizerValues(values, Nodeable.getLocalisationEntite(VariableSnot.class));
                String code = tokenizerValues.nextToken();
                String definition = tokenizerValues.nextToken();
                String theiacategories = tokenizerValues.nextToken();
                this.persistVariable(errorsReport, code, definition, theiacategories, lineNumber);
            }
            if (errorsReport.hasErrors()) {
                throw new BusinessException(errorsReport.getErrorsMessages());
            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param variableDAO
     */
    public void setVariableDAO(IVariableSnotDAO variableDAO) {
        this.variableDAO = variableDAO;
    }

    /*
     * @Override public void checkUpdatesOperations(CSVParser parser) throws BusinessException, UpdateNotificationException {
     *
     * throw new NotYetImplementedException(); }
     */
    private void createOrUpdateVariable(ErrorsReport errorsReport, String code, String definition, String theiacategories, int lineNumber, VariableSnot dbVariable) throws PersistenceException {

        if (dbVariable == null) {
            dbVariable = new VariableSnot(code, definition,theiacategories);
            this.variableDAO.saveOrUpdate(dbVariable);
        } else {
            dbVariable.setCode(code);
            dbVariable.setDefinition(definition);       
            dbVariable.setTheiacategories(theiacategories);
            this.variableDAO.saveOrUpdate(dbVariable);
        }
    }

    private void persistVariable(ErrorsReport errorsReport, String code, String definition, String theiacategories, int lineNumber) throws PersistenceException {

        VariableSnot dbVariable = (VariableSnot) this.variableDAO.getByCode(code).orElse(null);
        this.createOrUpdateVariable(errorsReport, code, definition, theiacategories, lineNumber, dbVariable);
    }

    /**
     *
     * @return
     * @throws BusinessException
     */
    @Override
    protected List<VariableSnot> getAllElements() throws BusinessException {
        return  this.variableDAO.getAllVariablesSnot();
    }

}