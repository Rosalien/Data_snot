/**
 * OREILacs project - see LICENCE.txt for use created: 7 avr. 2009 16:17:33
 */
package org.cnrs.osuc.snot.refdata.site;

import com.Ostermiller.util.CSVParser;
import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.typezoneetude.ITypeSiteDAO;
import org.cnrs.osuc.snot.refdata.typezoneetude.TypeSite;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.Nodeable;
import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
import org.inra.ecoinfo.refdata.LineModelGridMetadata;
import org.inra.ecoinfo.refdata.ModelGridMetadata;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.Utils;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author "Antoine Schellenberger"
 *
 */
public class Recorder extends AbstractCSVMetadataRecorder<SiteSnot> {

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";
    private static final String SITE_NONDEFINI = "STRING_EMPTY";
    private static final String PARENT_SITE_NONDEFINI = "PARENT_SITE_NONDEFINI";

    private static final String STRING_EMPTY = "";
    private static final String DATE_NON_VALIDE = "la date \"%s\" est non valide à la ligne %d";
    private static final String TYPESITE_NON_REFERENCE = "Le type de site \"%s\" est requis et n'est pas référencé en base de données à la ligne %d";
    private static final String PROPERTY_CST_TYPE_ZONE = "Type de zone";
    private static final String PROPERTY_CST_NAME = "nom";
    private static final String PROPERTY_CST_DESCRIPTION = "description";
    private static final String PROPERTY_CST_NOMCOMPLET = "nomcomplet";
    private static final String PROPERTY_CST_COORDONNEESBBOX = "coordonnees_bbox";
    private static final String PROPERTY_CST_SURFACE = "surface";
    private static final String PROPERTY_CST_ALTITUDE = "altitude";
    private static final String PROPERTY_CST_CLIMAT = "climat";
    private static final String PROPERTY_CST_GEOLOGIE = "geologie";
    private static final String PROPERTY_CST_DIR_WIND = "dir_vent";
    private static final String PROPERTY_CST_COUNTRY = "pays";
    private static final String PROPERTY_CST_START_DATE = "date début";
    private static final String PROPERTY_CST_END_DATE = "date fin";
    private static final String PROPERTY_CST_PARENT_PLACE = "zone parente";
    private static final SiteSnot EMPTY_SITE = new SiteSnot(Recorder.STRING_EMPTY);

    /**
     *
     */
    protected ISiteSnotDAO siteSnotDAO;

    /**
     *
     */
    protected ITypeSiteDAO typeSiteDAO;

    private Properties propertiesNomFR;
    private Properties propertiesNomEN;

    private Properties pays_en;
    private Properties descriptionProperties;
//    private Properties nomProperties;   
    private Properties directionVentProperties;

    String[] typeSitesPossibles;
    String[] sitesPossibles;

    /**
     *
     * @param parser
     * @param file
     * @param encoding
     * @throws BusinessException
     */
    @Override
    /*
     * @Override public void checkUpdatesOperations(CSVParser parser) throws BusinessException, UpdateNotificationException { throw new NotYetImplementedException();
     * 
     * }
     */
    public void deleteRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        try {
            String[] values = null;
            while ((values = parser.getLine()) != null) {
                String nom = values[1];
                String parent_path = values[values.length - 1];
                Optional<SiteSnot> parent = siteSnotDAO.getByCode(parent_path);
                Optional<SiteSnot> site = Strings.isNullOrEmpty(parent_path)
                        ? Optional.empty()
                        : siteSnotDAO.getByNameAndParent(nom, parent.orElseThrow(() -> new BusinessException(getMessage(parent_path))));
                this.siteSnotDAO.remove(site.orElseThrow(() -> new BusinessException(getMessage(nom, parent_path))));
            }
        } catch (IOException | PersistenceException e) {
            throw new BusinessException("Cause = " + e.getCause() + " Message = " + e.getMessage());
        }
    }

    /**
     *
     * @param site
     * @return
     * @throws BusinessException
     */
    @Override
    public LineModelGridMetadata getNewLineModelGridMetadata(SiteSnot site) throws BusinessException {
        LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getTypeSite().getNom(), typeSitesPossibles, Recorder.PROPERTY_CST_TYPE_ZONE, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : site.getName(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : propertiesNomFR.getProperty(site.getName(), site.getName()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : propertiesNomEN.getProperty(site.getName(), site.getName()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getName(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : nomProperties.getProperty(site.getName(), site.getName()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Constantes.CST_STRING_EMPTY : propertiesNomFR.getProperty(site.getName(), site.getName()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Constantes.CST_STRING_EMPTY : propertiesNomEN.getProperty(site.getName(), site.getName()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getDescription(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_DESCRIPTION, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : descriptionProperties.getProperty(site.getDescription(), site.getDescription()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : DateUtil.getUTCDateTextFromLocalDateTime(site.getDate_debut(), DateUtil.DD_MM_YYYY), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_START_DATE, false,
                        false, true));
        String dateFin = this.dateFin(site);
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
                new ColumnModelGridMetadata(dateFin, ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_END_DATE, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getCoordonnees_bbox(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_COORDONNEESBBOX, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getSurface(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_SURFACE, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getAltitude(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_ALTITUDE, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getClimat(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_CLIMAT, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getGeologie(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_GEOLOGIE, false, false, true));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getDirection_vent(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_DIR_WIND, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Constantes.CST_STRING_EMPTY : directionVentProperties.getProperty(site.getDirection_vent(), site.getDirection_vent()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Recorder.STRING_EMPTY : site.getPays(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, Recorder.PROPERTY_CST_COUNTRY, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null ? Constantes.CST_STRING_EMPTY : pays_en.getProperty(site.getPays(), site.getPays()), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
        lineModelGridMetadata.getColumnsModelGridMetadatas().add(new ColumnModelGridMetadata(site == null || site.getParent() == null ? Recorder.STRING_EMPTY : site.getParent().getPath(), sitesPossibles, Recorder.PROPERTY_CST_PARENT_PLACE, false, false, true));
        return lineModelGridMetadata;
    }

    /**
     * @param parser
     * @param encoding
     * @throws org.inra.ecoinfo.utils.exceptions.BusinessException
     */
    @Override
    public void processRecord(CSVParser parser, File file, String encoding) throws BusinessException {
        AbstractCSVMetadataRecorder.ErrorsReport errorsReport = new AbstractCSVMetadataRecorder.ErrorsReport();
        try {
            this.skipHeader(parser);
            String[] values = null;
            int lineNumber = 0;
            while ((values = parser.getLine()) != null) {
                final AbstractCSVMetadataRecorder.TokenizerValues tokenizerValues = new AbstractCSVMetadataRecorder.TokenizerValues(values, Nodeable.getLocalisationEntite(SiteSnot.class));
                lineNumber++;
                String typeSiteCode = Utils.createCodeFromString(tokenizerValues.nextToken());
                String nom = tokenizerValues.nextToken();
                String description = tokenizerValues.nextToken();
                String dateDebutString = tokenizerValues.nextToken();
                LocalDate dateDebut;
                try {
                    dateDebut = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateDebutString).toLocalDate();
                } catch (DateTimeParseException e) {
                    throw new BusinessException(String.format(Recorder.DATE_NON_VALIDE, dateDebutString, lineNumber));
                }
                String dateFinString = tokenizerValues.nextToken();
                LocalDate dateFin;
                if (StringUtils.isEmpty(dateFinString)) {
                    dateFin = null;
                } else {
                    try {
                        dateFin = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, dateFinString).toLocalDate();
                    } catch (DateTimeParseException e) {
                        throw new BusinessException(String.format(Recorder.DATE_NON_VALIDE, dateFinString, lineNumber));
                    }
                }
                String coordonnees_bbox = tokenizerValues.nextToken();
                String surface = tokenizerValues.nextToken();
                String altitude = tokenizerValues.nextToken();
                String climat = tokenizerValues.nextToken();
                String geologie = tokenizerValues.nextToken();
                String directionVent = tokenizerValues.nextToken();
                String pays = tokenizerValues.nextToken();
                String nom_path_parent = tokenizerValues.nextToken();
                this.persistSite(errorsReport, typeSiteCode, nom_path_parent, nom, description, dateDebut, dateFin, coordonnees_bbox, surface, altitude, climat, geologie, directionVent, pays, lineNumber);
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
     * @param siteDAO
     */
    public void setSiteSnotDAO(ISiteSnotDAO siteDAO) {
        this.siteSnotDAO = siteDAO;
    }

    /**
     *
     * @param typeSiteDAO
     */
    public void setTypeSiteDAO(ITypeSiteDAO typeSiteDAO) {
        this.typeSiteDAO = typeSiteDAO;
    }

    /**
     * @param site
     * @param dbSite
     */
    /*
     * @Override public void checkUpdatesOperations(CSVParser parser) throws BusinessException, UpdateNotificationException { throw new NotYetImplementedException();
     *
     * }
     */
    @SuppressWarnings("unused")
    private void createOrUpdateSite(SiteSnot parent, String nom, String description, LocalDate dateDebut, LocalDate dateFin, String coordonnees_bbox, String surface, String altitude, String climat, String geologie, String directionVent, String pays, SiteSnot dbSite, TypeSite dbTypeSite) throws PersistenceException {
        if (dbSite == null) {
            this.createSite(parent, nom, description, dateDebut, dateFin, coordonnees_bbox, surface, altitude, climat, geologie, directionVent, pays, dbTypeSite);
        } else {
            this.updateSite(parent, nom, description, dateDebut, dateFin, coordonnees_bbox, surface, altitude, climat, geologie, directionVent, pays, dbSite, dbTypeSite);
        }
    }

    private void createSite(SiteSnot parent, String nom, String description, LocalDate dateDebut, LocalDate dateFin, String coordonnees_bbox, String surface, String altitude, String climat, String geologie, String directionVent, String pays, TypeSite dbTypeSite) throws PersistenceException {
        SiteSnot site = new SiteSnot(dbTypeSite, parent, nom, description, coordonnees_bbox, surface, altitude, climat, geologie, directionVent, pays, dateDebut, dateFin);
        site.setTypeSite(dbTypeSite);
        dbTypeSite.addSite(site);
        this.siteSnotDAO.flush();
        this.typeSiteDAO.flush();
    }

    private String dateFin(SiteSnot site) {
        return Optional.ofNullable(site)
                .map(s -> s.getDate_fin())
                .map(d -> DateUtil.getUTCDateTextFromLocalDateTime(d, DateUtil.DD_MM_YYYY))
                .orElseGet(String::new);
    }

    private String[] getNamesSitesPossibles() {
        List<Site> sites = this.siteSnotDAO.getAll();
        String[] namesSitesPossibles = new String[sites.size() + 1];
        namesSitesPossibles[0] = Recorder.STRING_EMPTY;
        int index = 1;
        for (Site site : sites) {
            namesSitesPossibles[index++] = site.getPath();
        }
        return namesSitesPossibles;
    }

    private String[] getNamesTypesSitesPossibles() {
        List<TypeSite> typesSites = this.typeSiteDAO.getAll(TypeSite.class);
        String[] namesTypesSitesPossibles = new String[typesSites.size()];
        int index = 0;
        for (TypeSite typeSite : typesSites) {
            namesTypesSitesPossibles[index++] = typeSite.getNom();
        }
        return namesTypesSitesPossibles;
    }

    private void persistSite(ErrorsReport errorsReport, String typeSiteCode, String nom_path_parent, String nom, String description, LocalDate dateDebut, LocalDate dateFin, String coordonnees_bbox, String surface, String altitude, String climat, String geologie, String directionVent, String pays, int lineNumber) throws PersistenceException {
        Optional<TypeSite> dbTypeSite = this.typeSiteDAO.getTypeSiteByCode(typeSiteCode);
        if (!dbTypeSite.isPresent()) {
            errorsReport.addErrorMessage(String.format(Recorder.TYPESITE_NON_REFERENCE, typeSiteCode, lineNumber));
            return;
        }
        String codeSite = Utils.createCodeFromString(nom);
        Optional<SiteSnot> parent = this.siteSnotDAO.getByCode(nom_path_parent);
        if (!Strings.isNullOrEmpty(nom_path_parent) && !parent.isPresent()) {
            errorsReport.addErrorMessage(getMessage(nom, nom_path_parent));
            return;
        }
        Optional<SiteSnot> parentOpt = siteSnotDAO.getByCode(nom_path_parent);
        if (!(Strings.isNullOrEmpty(nom_path_parent) || parentOpt.isPresent())) {
            errorsReport.addErrorMessage(getMessage(nom_path_parent));
            return;
        }
        Optional<SiteSnot> dbSite = Strings.isNullOrEmpty(nom_path_parent)
                ? siteSnotDAO.getByCode(codeSite)
                : siteSnotDAO.getByNameAndParent(nom, parentOpt.orElse(null));

        this.createOrUpdateSite(parent.orElse(null), nom, description, dateDebut, dateFin, coordonnees_bbox, surface, altitude, climat, geologie, directionVent, pays, dbSite.orElse(null), dbTypeSite.get());
    }

    private void updateSite(SiteSnot parent, String nom, String description, LocalDate dateDebut, LocalDate dateFin, String coordonnees_bbox, String surface, String altitude, String climat, String geologie, String directionVent, String pays, SiteSnot dbSite, TypeSite dbTypeSite) throws PersistenceException {
        dbSite.setTypeSite(dbTypeSite);
        dbSite.setParent(parent);
        dbSite.setName(nom);
        dbSite.setDescription(description);
        dbSite.setCoordonnees_bbox(coordonnees_bbox);
        dbSite.setSurface(surface);
        dbSite.setAltitude(altitude);
        dbSite.setClimat(climat);
        dbSite.setGeologie(geologie);
        dbSite.setDirection_vent(directionVent);
        dbSite.setPays(pays);
        dbSite.setDate_debut(dateDebut);
        dbSite.setDate_fin(dateFin);
        dbTypeSite.addSite(dbSite);
        this.siteSnotDAO.flush();
        this.typeSiteDAO.flush();
    }

    /**
     *
     * @return @throws BusinessException
     */
    @Override
    protected List<SiteSnot> getAllElements() throws BusinessException {
        LinkedList<SiteSnot> sites = new LinkedList<>(this.siteSnotDAO.getListSite().values());
        Collections.sort(sites, new Comparator<SiteSnot>() {

            @Override
            public int compare(SiteSnot o1, SiteSnot o2) {
                return o1.getPath().compareTo(o2.getPath());
            }
        });
        return sites;
    }

    private String getMessage(String nom) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, SITE_NONDEFINI), nom);
    }

    private String getMessage(String nom, String parent_path) {
        return String.format(localizationManager.getMessage(BUNDLE_SOURCE_PATH, PARENT_SITE_NONDEFINI), parent_path, nom);
    }

    /**
     *
     * @return
     */
    @Override
    protected ModelGridMetadata<SiteSnot> initModelGridMetadata() {
        final String tableName = Nodeable.getLocalisationEntite(SiteSnot.class);
        descriptionProperties = this.localizationManager.newProperties(tableName, PROPERTY_CST_DESCRIPTION, Locale.ENGLISH);
//        nomProperties = this.localizationManager.newProperties(tableName, PROPERTY_CST_NAME, Locale.ENGLISH);
        propertiesNomFR = localizationManager.newProperties(tableName, PROPERTY_CST_NAME, Locale.FRANCE);
        propertiesNomEN = localizationManager.newProperties(tableName, PROPERTY_CST_NAME, Locale.ENGLISH);
        pays_en = this.localizationManager.newProperties(tableName, RefDataConstantes.COLUMN_SITE_PAYS, Locale.ENGLISH);
        directionVentProperties = this.localizationManager.newProperties(tableName, PROPERTY_CST_DIR_WIND, Locale.ENGLISH);
        typeSitesPossibles = getNamesTypesSitesPossibles();
        sitesPossibles = getNamesSitesPossibles();
        return super.initModelGridMetadata();
    }

}
