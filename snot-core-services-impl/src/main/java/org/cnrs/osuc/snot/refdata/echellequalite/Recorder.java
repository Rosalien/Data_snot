/**
 *
 */
package org.cnrs.osuc.snot.refdata.echellequalite;

/**
 * @author sophie
 * 
 */
/*
 * public class Recorder extends AbstractCSVMetadataRecorder<EchelleQualite> { protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.refdata.messages";
 * 
 * IEchelleQualiteDAO echelleQualiteDAO;
 */

/**
 * @param nom
 * @param dbEchelleQualite
 * @param echelleQualite
 * @throws PersistenceException
 */
/*
 * private void _createOrUpdateSite(String nom, EchelleQualite dbEchelleQualite, EchelleQualite echelleQualite) throws PersistenceException { //création if (dbEchelleQualite == null) { echelleQualiteDAO.saveOrUpdate(echelleQualite); } else //update {
 * dbEchelleQualite.setNom(nom);
 * 
 * echelleQualiteDAO.saveOrUpdate(dbEchelleQualite); } }
 */

/*
 * (non-Javadoc)
 * 
 * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#deleteRecord(com.Ostermiller.util.CSVParser, java.io.File, java.lang.String)
 */
/*
 * @Override public void deleteRecord(CSVParser parser, File file, String encoding) throws BusinessException { try { String[] values = null; while ((values = parser.getLine()) != null) {
 * 
 * TokenizerValues tokenizerValues = new TokenizerValues(values)
 * 
 * echelleQualiteDAO.remove(echelleQualiteDAO.getByNom(nom)); } } catch (Exception e) { throw new BusinessException(e.getMessage(),e); } }
 */

/*
 * (non-Javadoc)
 * 
 * @see org.inra.ecoinfo.refdata.IMetadataRecorder#getNewLineModelGridMetadata(java.lang.Object)
 */
/*
 * @Override public LineModelGridMetadata getNewLineModelGridMetadata(EchelleQualite echelleQualite) throws PersistenceException { LineModelGridMetadata lineModelGridMetadata = new LineModelGridMetadata();
 * 
 * lineModelGridMetadata.getColumnsModelGridMetadatas().add( new ColumnModelGridMetadata(echelleQualite == null ? Constantes.STRING_EMPTY : echelleQualite.getNom(), ColumnModelGridMetadata.NULL_MAP_POSSIBLES, null, true, false, true)); return
 * lineModelGridMetadata; }
 */

/*
 * (non-Javadoc)
 * 
 * @see org.inra.ecoinfo.refdata.impl.AbstractCSVMetadataRecorder#getAllElements()
 */
/*
 * @Override protected List<EchelleQualite> getAllElements() throws PersistenceException { return echelleQualiteDAO.getAll(EchelleQualite.class); }
 */

/**
 * @param echelleQualiteDAO
 *            the echelleQualiteDAO to set
 */
/*
 * public void setEchelleQualiteDAO(IEchelleQualiteDAO echelleQualiteDAO) { this.echelleQualiteDAO = echelleQualiteDAO; }
 * 
 * 
 * }
 */
