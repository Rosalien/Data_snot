package org.cnrs.osuc.snot.synthesis;

import java.time.LocalDateTime;



/**
 *
 * @author ptcherniati
 */
public interface IGraphPresenceAbsence {

    /**
     *
     * @return
     */
    LocalDateTime getDate();

    /**
     *
     * @return
     */
    Facteur getOrdonnee();

    /**
     *
     * @return
     */
    Facteur getRepetition();

    /**
     *
     * @return
     */
    Float getValueFloat();

    /**
     *
     * @return
     */
    boolean isPresenceAbsence();

    /**
     *
     * @return
     */
    boolean isSemihoraire();

}
