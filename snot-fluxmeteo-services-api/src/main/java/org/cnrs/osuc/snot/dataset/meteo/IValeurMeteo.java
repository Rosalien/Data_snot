package org.cnrs.osuc.snot.dataset.meteo;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;


/**
 * @author philippe
 * @param <T>
 * 
 */
public interface IValeurMeteo<T extends MesureMeteo> {

    /**
     *
     * @return
     */
    T getMesure();

    /**
     *
     * @return
     */
    Long getMesureMeteoId();
}
