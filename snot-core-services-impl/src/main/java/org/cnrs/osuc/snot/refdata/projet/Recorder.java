///**
// * OREILacs project - see LICENCE.txt for use created: 7 avr. 2009 16:17:33
// */
//
//package org.cnrs.osuc.snot.refdata.projet;
//
//import com.Ostermiller.util.CSVParser;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//import java.util.Optional;
//import java.util.Properties;
//import org.inra.ecoinfo.mga.business.composite.Nodeable;
//import org.inra.ecoinfo.refdata.AbstractCSVMetadataRecorder;
//import org.inra.ecoinfo.refdata.ColumnModelGridMetadata;
//import org.inra.ecoinfo.refdata.LineModelGridMetadata;
//import org.inra.ecoinfo.refdata.ModelGridMetadata;
//import org.inra.ecoinfo.utils.Utils;
//import org.inra.ecoinfo.utils.exceptions.BusinessException;
//import org.inra.ecoinfo.utils.exceptions.PersistenceException;
//
///**
// * The Class Recorder.
// *
// * @author "Antoine Schellenberger"
// */
//public class Recorder extends AbstractCSVMetadataRecorder<Projet> {
//
//    /**
//     * The projet dao.
//     */
//    protected IProjetDAO projetDAO;
//    /**
//     * The properties nom fr.
//     */
//    private Properties propertiesNomFR;
//    /**
//     * The properties nom en.
//     */
//    private Properties propertiesNomEN;
//    /**
//     * The properties description en.
//     */
//    private Properties propertiesDescriptionEN;
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see
//     * org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#deleteRecord(com.Ostermiller.util
//     * .CSVParser, java.io.File, java.lang.String)
//     */
//    @Override
//    public void deleteRecord(final CSVParser parser, final File file, final String encoding)
//            throws BusinessException {
//        try {
//            String[] values = parser.getLine();
//            while (values != null) {
//                final String projetCode = Utils.createCodeFromString(values[0]);
//                projetDAO.remove(projetDAO.getByCode(projetCode)
//                        .orElseThrow(() -> new BusinessException("can't get projet")));
//
//                values = parser.getLine();
//            }
//        } catch (final IOException | PersistenceException e) {
//            throw new BusinessException(e.getMessage(), e);
//        }
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
//     */
//    @Override
//    protected List<Projet> getAllElements() throws BusinessException {
//        return projetDAO.getAll(Projet.class);
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
//     */
//    @Override
//    public LineModelGridMetadata getNewLineModelGridMetadata(final Projet projet)
//            throws BusinessException {
//        final LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
//        lineModelGridMetadata
//                .getColumnsModelGridMetadatas()
//                .add(new ColumnModelGridMetadata(
//                        projet == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : projet.getNom(),
//                        ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
//                new ColumnModelGridMetadata(
//                        projet == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : propertiesNomFR
//                                        .getProperty(projet.getNom()),
//                        ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
//                new ColumnModelGridMetadata(
//                        projet == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : propertiesNomEN
//                                        .getProperty(projet.getNom()),
//                        ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
//        lineModelGridMetadata.getColumnsModelGridMetadatas().add(
//                new ColumnModelGridMetadata(
//                        projet == null ? AbstractCSVMetadataRecorder.EMPTY_STRING : projet
//                                        .getDescriptionProjet(),
//                        ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
//        lineModelGridMetadata
//                .getColumnsModelGridMetadatas()
//                .add(new ColumnModelGridMetadata(
//                        projet == null ? AbstractCSVMetadataRecorder.EMPTY_STRING
//                                : propertiesDescriptionEN
//                                        .getProperty(projet.getDescriptionProjet()),
//                        ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, false, false, false));
//        return lineModelGridMetadata;
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#initModelGridMetadata()
//     */
//    @Override
//    protected ModelGridMetadata<Projet> initModelGridMetadata() {
//        propertiesNomFR = localizationManager.newProperties(Nodeable.getLocalisationEntite(Projet.class), Nodeable.ENTITE_COLUMN_NAME, Locale.FRANCE);
//        propertiesNomEN = localizationManager.newProperties(Nodeable.getLocalisationEntite(Projet.class), Nodeable.ENTITE_COLUMN_NAME, Locale.ENGLISH);
//        propertiesDescriptionEN = localizationManager.newProperties(Nodeable.getLocalisationEntite(Projet.class), "definition", Locale.ENGLISH);
//        return super.initModelGridMetadata();
//    }
//
//    /**
//     * Persist projet.
//     *
//     * @param errorsReport the errors report
//     * @param projetCode the projet code
//     * @param description the description
//     * @param projetNom the projet nom
//     * @throws PersistenceException the persistence exception
//     */
//    private void persistProjet(final String projetCode, final String description,
//            final String projetNom) throws PersistenceException {
//        final Projet projet = retrieveOrCreateDBProjet(projetCode, projetNom);
//        projet.setDescriptionProjet(description);
//        projetDAO.saveOrUpdate(projet);
//    }
//
//    /**
//     * Process record.
//     *
//     * @param parser the parser
//     * @param file the file
//     * @param encoding the encoding
//     * @throws BusinessException the business exception
//     */
//    @Override
//    public void processRecord(final CSVParser parser, final File file, final String encoding)
//            throws BusinessException {
//        final ErrorsReport errorsReport = new ErrorsReport();
//        try {
//            skipHeader(parser);
//            // On parcourt chaque ligne du fichier
//            String[] values = parser.getLine();
//            while (values != null) {
//                final TokenizerValues tokenizerValues = new TokenizerValues(values, Nodeable.getLocalisationEntite(Projet.class));
//                final String projetNom = tokenizerValues.nextToken();
//                final String projetCode = Utils.createCodeFromString(projetNom);
//                final String description = tokenizerValues.nextToken();
//                persistProjet(projetCode, description, projetNom);
//                values = parser.getLine();
//            }
//            if (errorsReport.hasErrors()) {
//                throw new BusinessException(errorsReport.getErrorsMessages());
//            }
//        } catch (final IOException | PersistenceException e) {
//            throw new BusinessException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Retrieve or create db projet.
//     *
//     * @param errorsReport the errors report
//     * @param projetCode the projet code
//     * @param projetNom the projet nom
//     * @return the projet
//     * @throws PersistenceException the persistence exception
//     */
//    private Projet retrieveOrCreateDBProjet(final String projetCode, final String projetNom)
//            throws PersistenceException {
//        Optional<Projet> projetOpt = projetDAO.getByCode(projetCode);
//        if (!projetOpt.isPresent()) {
//            Projet projet = new Projet();
//            projet.setNom(projetNom);
//            return projet;
//        }
//        return projetOpt.get();
//    }
//
//    /**
//     * Sets the projet dao.
//     *
//     * @param projetDAO the new projet dao
//     */
//    public void setProjetDAO(final IProjetDAO projetDAO) {
//        this.projetDAO = projetDAO;
//    }
//}
