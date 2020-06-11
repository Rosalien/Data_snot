/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeapplicationmethode;

import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.AbstractJPADAO;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 * @author sophie
 *
 */
public class JPAPeriodeApplicationMethodeDAO extends AbstractJPADAO<PeriodeApplicationMethode> implements IPeriodeApplicationMethodeDAO {

    private static final Logger LOGGER = Logger.getLogger(JPAPeriodeApplicationMethodeDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.cnrs.osuc.snot.refdata.periodeapplicationmethode.IPeriodeApplicationMethodeDAO#getByStdtVarMCalcAndDates(org.cnrs.osuc.snot.refdata.sitethemedatatypevariable.SiteThemeDatatypeVariable,
     * org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul, java.util.Date, java.util.Date)
     */
    /**
     *
     * @param realNode
     * @param code_jeu
     * @param methodeCalcul
     * @param debutPeriode
     * @return
     */
    @Override
    public Optional<PeriodeApplicationMethode> getByStdtVarMCalcAndDates(RealNode realNode, Jeu code_jeu, MethodeCalcul methodeCalcul, LocalDate debutPeriode) {
        CriteriaQuery<PeriodeApplicationMethode> query = builder.createQuery(PeriodeApplicationMethode.class);
        Root<PeriodeApplicationMethode> periode = query.from(PeriodeApplicationMethode.class);
        query
                .select(periode)
                .where(builder.and(
                        builder.equal(periode.get(PeriodeApplicationMethode_.realNode), realNode),
                        builder.equal(periode.get(PeriodeApplicationMethode_.jeu), code_jeu),
                        builder.equal(periode.get(PeriodeApplicationMethode_.methodeCalcul), methodeCalcul),
                        builder.equal(periode.get(PeriodeApplicationMethode_.dateDebut), debutPeriode)
                //                        builder.equal(periode.get(PeriodeApplicationMethode_.dateFin), finPeriode)
                ));
        return getOptional(query);
    }

}
