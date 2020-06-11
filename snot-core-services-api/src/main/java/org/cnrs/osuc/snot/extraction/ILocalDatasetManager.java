package org.cnrs.osuc.snot.extraction;


import java.time.LocalDateTime;
import java.util.List;
import org.inra.ecoinfo.dataset.IDatasetManager;
import org.inra.ecoinfo.extraction.IParameter;
import org.inra.ecoinfo.extraction.entity.Requete;
import org.inra.ecoinfo.utils.exceptions.BusinessException;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author philippe Interface de gestion des extractions
 * 
 */
public interface ILocalDatasetManager extends IDatasetManager {

    /**
     *
     */
    String RYTHME_INVALID = "Le rythme \"%s\" n'est pas un rythme valide.";

    /**
     *
     * @param parameters
     * @param login
     * @throws BusinessException
     */
    void extract(IParameter parameters, String login) throws BusinessException;

    /**
     *
     * @param parameter
     * @return
     */
    String getExtractName(IParameter parameter);

    /**
     *
     * @param login
     * @param dataTypeName
     * @return
     * @throws BusinessException
     */
    List<Requete> getRequestsByExtractionTypeAndLogin(String login, String dataTypeName) throws BusinessException;

    /**
     *
     * @param login
     * @param rythme
     * @param commentaires
     * @param fileRandomSuffix
     * @param startDate
     * @param processDate
     * @throws PersistenceException
     */
    void registerRequest(String login, String rythme, String commentaires, String fileRandomSuffix, LocalDateTime startDate, LocalDateTime processDate) throws PersistenceException;
}
