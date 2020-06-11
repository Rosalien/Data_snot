/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.meteo.jpa;

import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_m;

/**
 * @author philippe
 *
 */
public class JPAMesureMeteo_mDAO extends JPAMesureMeteoDAO<MesureMeteo_m> implements IMesureMeteoDAO<MesureMeteo_m> {

    @Override
    public Class<MesureMeteo_m> getMeasureClass() {
        return MesureMeteo_m.class;
    }
}
