package org.cnrs.osuc.snot.dataset.meteo;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.Tuple;
import org.inra.ecoinfo.IDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;


/**
 * @author philippe
 * 
 * @param <T>
 */
public interface IMesureMeteoDAO<T extends MesureMeteo> extends IDAO<T> {

    /**
     *
     * @param date
     * @param time
     * @return
     */
    Optional<Tuple> getLinePublicationNameDoublon(LocalDate date, LocalTime time);

    /**
     *
     * @return
     */
    Class<T> getMeasureClass();
}
