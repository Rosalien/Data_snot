package org.cnrs.osuc.snot.dataset.flux;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.Tuple;
import org.inra.ecoinfo.IDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;


/**
 * @author philippe
 * 
 * @param <T>
 */
public interface IMesureFluxDAO<T extends MesureFlux> extends IDAO<T> {

    /**
     *
     */
    public static final String TUPLE_DATASET_ID = "dataset_id";

    /**
     *
     */
    public static final String TUPLE_ORIGINAL_LINE_NUMBER = "line number";

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
