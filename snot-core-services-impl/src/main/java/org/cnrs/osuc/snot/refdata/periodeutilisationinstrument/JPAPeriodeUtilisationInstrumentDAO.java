/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeutilisationinstrument;


import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author sophie
 * 
 */
public class JPAPeriodeUtilisationInstrumentDAO extends AbstractJPADAO<PeriodeUtilisationInstrument> implements IPeriodeUtilisationInstrumentDAO {
    private static final Logger LOGGER = Logger.getLogger(JPAPeriodeUtilisationInstrumentDAO.class);

    /**
     *
     * @param realNode
     * @param code_jeu
     * @param instrument
     * @param debutPeriode
     * @return
     */
    @Override
    public Optional<PeriodeUtilisationInstrument> getByStdtVarInstrAndDates(RealNode realNode, Jeu code_jeu, Instrument instrument, LocalDate debutPeriode) {
        CriteriaQuery<PeriodeUtilisationInstrument> query = builder.createQuery(PeriodeUtilisationInstrument.class);
        Root<PeriodeUtilisationInstrument> periode = query.from(PeriodeUtilisationInstrument.class);
        query
                .select(periode)
                .where(builder.and(
                        builder.equal(periode.get(PeriodeUtilisationInstrument_.realNode), realNode),
                        builder.equal(periode.get(PeriodeUtilisationInstrument_.jeu), code_jeu),
                        builder.equal(periode.get(PeriodeUtilisationInstrument_.instrument), instrument),
                        builder.equal(periode.get(PeriodeUtilisationInstrument_.dateDebut), debutPeriode)
//                        builder.equal(periode.get(PeriodeUtilisationInstrument_.dateFin), finPeriode)
                ));
        return getOptional(query);
    }

}