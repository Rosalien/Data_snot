package org.cnrs.osuc.snot.synthesis.jpa;


import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.inra.ecoinfo.synthesis.ISynthesisDAO;
import org.inra.ecoinfo.synthesis.jpa.JPASynthesisDAO;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class SnotJPASynthesisDAO extends JPASynthesisDAO {
    private static final String PATTERN_SELECT_MIN_MAX_VALUE = "select min(z.date), max(z.date) from %sSynthesisValue z where z.variable = :variable";
//private static final String PATTERN_SELECT_MIN_MAX_VALUE = "select min(z.date), max(z.date) from %sSynthesisValue z where z.site = :site and z.variable = :variable";
    static final String PREFIX_CLIMAT_SOL_MENSUEL = "cmsm";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     */
    protected ISynthesisDAO climatSynthesisDAO;

    /**
     *
     * @return
     */
    public ISynthesisDAO getClimatSynthesisDAO() {
        return this.climatSynthesisDAO;
    }

    /**
     *
     * @param climatSynthesisDAO
     */
    public void setClimatSynthesisDAO(ISynthesisDAO climatSynthesisDAO) {
        this.climatSynthesisDAO = climatSynthesisDAO;
    }

    @SuppressWarnings("unchecked")
    private Object[] getDateBySiteAndVariableSh(String variableName, String datatypeCode) throws PersistenceException {
        try {
            String prefix = StringUtils.capitalize(this.synthesisRegister.retrieveMetadataSynthesisDatatype(datatypeCode).getPrefix());
            String queryString = String.format(PATTERN_SELECT_MIN_MAX_VALUE, prefix);
            Query query = this.entityManager.createQuery(queryString);
//            query.setParameter("site", siteName);
            query.setParameter("variable", variableName);
            List<Object[]> dates = query.getResultList();
            Object[] reponse = new Object[2];
            Object[] limite = dates.get(0);
            reponse[0] = limite[0];
            LocalDateTime fin = (LocalDateTime) limite[1];
            reponse[1] = fin.plusDays(1).toLocalDate().atStartOfDay();
            return reponse;
        } catch (Exception e) {
            this.LOGGER.error(PersistenceException.getLastCauseExceptionMessage(e));
            throw new PersistenceException(e.getMessage(), e);
        }
    }

}
