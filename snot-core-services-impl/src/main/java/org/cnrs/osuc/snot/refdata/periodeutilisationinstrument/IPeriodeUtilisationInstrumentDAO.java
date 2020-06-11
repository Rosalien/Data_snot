/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeutilisationinstrument;


import java.time.LocalDate;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author sophie
 * 
 */
public interface IPeriodeUtilisationInstrumentDAO extends IDAO<PeriodeUtilisationInstrument> {

    /**
     *
     * @param realNode
     * @param code_jeu
     * @param instrument
     * @param debutPeriode
     * @return
     */
    Optional<PeriodeUtilisationInstrument> getByStdtVarInstrAndDates(RealNode realNode, Jeu code_jeu, Instrument instrument, LocalDate debutPeriode);

}