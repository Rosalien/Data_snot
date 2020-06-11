/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeapplicationmethode;


import java.time.LocalDate;
import java.util.Optional;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.IDAO;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author sophie
 * 
 */
public interface IPeriodeApplicationMethodeDAO extends IDAO<PeriodeApplicationMethode> {

    /**
     *
     * @param realNode
     * @param code_jeu
     * @param methodeCalcul
     * @param debutPeriode
     * @return
     */
    Optional<PeriodeApplicationMethode> getByStdtVarMCalcAndDates(RealNode realNode, Jeu code_jeu, MethodeCalcul methodeCalcul, LocalDate debutPeriode);

}