/**
 * OREISnots project - see LICENCE.txt for use created: 7 avr. 2009 16:17:33
 */
package org.cnrs.osuc.snot.refdata.typezoneetude;

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
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author philippe
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<TypeSite> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";
    private static final String TYPE_SITE_NONDEFINI = "TYPE_SITE_NONDEFINI";

    /**
     *
     */

    /**
     *
     */
    protected ITypeSiteDAO typeSiteDAO;

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
                String code = Utils.createCodeFromString(nom);
                this.typeSiteDAO.remove(this.typeSiteDAO.getTypeSiteByCode(code)
                        .orElseThrow(() -> new BusinessException(getMessage(nom))));
            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param typeSite
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(TypeSite typeSite) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        Properties propertiesName = this.localizationManager.newProperties(TypeSite.NAME_ENTITY_JPA, RefDataConstantes.COLUMN_NOM_TYPE_SITE, Locale.ENGLISH);
        Properties propertiesDefinition = this.localizationManager.newProperties(TypeSite.NAME_ENTITY_JPA, RefDataConstantes.COLUMN_DEFINITION_TYPE_SITE, Locale.ENGLISH);
        String localizedNom = "";
        String localizedDefinition = "";
        if (typeSite != null) {
            localizedNom = propertiesName.containsKey(typeSite.getNom()) ? propertiesName.getProperty(typeSite.getNom()) : typeSite.getNom();
            localizedDefinition = propertiesDefinition.containsKey(typeSite.getDefinition()) ? propertiesDefinition.getProperty(typeSite.getDefinition()) : typeSite.getDefinition();
        }
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(typeSite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : typeSite.getNom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(typeSite == null ? Constantes.CST_STRING_EMPTY : localizedNom, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(typeSite == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : typeSite.getDefinition(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(typeSite == null ? Constantes.CST_STRING_EMPTY : localizedDefinition, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        return lineModelGridMetadata;

    }

    /**
     * @param parser
     * @param encoding
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        try {
            this.skipHeader(parser);
            ErrorsReport errorsReport = new ErrorsReport();
            String[] values = null;
            while ((values = parser.getLine()) != null) {
                final TokenizerValues tokenizerValues = new TokenizerValues(values, TypeSite.NAME_ENTITY_JPA);
                String nom = tokenizerValues.nextToken();
                String code = Utils.createCodeFromString(nom);
                String definition = tokenizerValues.nextToken();
                TypeSite dbTypeSite = this.typeSiteDAO.getTypeSiteByCode(code)
                        .orElseGet(() -> new TypeSite(code, nom, definition));
                if (dbTypeSite.getId() == null) {
                    this.typeSiteDAO.saveOrUpdate(dbTypeSite);
                } else {
                    dbTypeSite.setDescription(definition);
                    this.typeSiteDAO.saveOrUpdate(dbTypeSite);
                }
            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param typeSiteDAO
     */
    public void setTypeSiteDAO(ITypeSiteDAO typeSiteDAO) {
        this.typeSiteDAO = typeSiteDAO;
    }

    private boolean areTypesSitesEquals(TypeSite typeSite, TypeSite dbTypeSite) {
        return typeSite.getNom().equalsIgnoreCase(dbTypeSite.getNom()) && typeSite.getDefinition().equalsIgnoreCase(dbTypeSite.getDefinition());
    }

    /**
     *
     * @return @throws BusinessException
     */
    @Override
    protected List<TypeSite> getAllElements() throws BusinessException {
        return this.typeSiteDAO.getAll();
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, TYPE_SITE_NONDEFINI), nom);
    }

}
