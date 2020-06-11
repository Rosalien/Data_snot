/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.unite;

import com.Ostermiller.util.CSVParser;
import java.io.File;
import java.io.IOException;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 */
public class Recorder extends org.inra.ecoinfo.refdata.unite.Recorder {

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
            String[] values = parser.getLine();
            int lineNumber = 0;
            while (values != null) {
                final TokenizerValues tokenizerValues = new TokenizerValues(values, Unite.NAME_ENTITY_JPA);
                lineNumber++;
                final String code = tokenizerValues.nextToken();
                final String nom = tokenizerValues.nextToken();
                final Unite unite = new Unite(code, nom);
                final Unite dbUnite = this.uniteDAO.getByCode(code).orElse(null);
                if (dbUnite == null) {
                    this.uniteDAO.saveOrUpdate(unite);
                } else {
                    dbUnite.setName(nom);
                }
                values = parser.getLine();
            }
        } catch (final IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

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
            String[] values = parser.getLine();
            while (values != null) {
                final TokenizerValues tokenizerValues = new TokenizerValues(values);
                final String code = tokenizerValues.nextToken();
                this.uniteDAO.remove(this.uniteDAO.getByCode(code)
                        .orElseThrow(() -> new PersistenceException("bad unit")));
                values = parser.getLine();
            }
        } catch (final IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

}
