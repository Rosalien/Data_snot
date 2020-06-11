package org.cnrs.osuc.snot.dataset.flux;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;


/**
 * @author philippe
 * @param <T>
 * 
 */
public interface IValeurFluxTour<T extends MesureFlux> {

    /**
     *
     * @return
     */
    MesureFlux getMesure();

    /**
     *
     * @return
     */
    Long getMesureFluxId();
}
