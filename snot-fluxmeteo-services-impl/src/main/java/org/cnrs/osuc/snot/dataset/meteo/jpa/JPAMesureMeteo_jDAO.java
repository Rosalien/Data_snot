/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.meteo.jpa;

import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_j;

/**
 * @author philippe
 *
 */
public class JPAMesureMeteo_jDAO extends JPAMesureMeteoDAO<MesureMeteo_j> implements IMesureMeteoDAO<MesureMeteo_j> {

    @Override
    public Class<MesureMeteo_j> getMeasureClass() {
        return MesureMeteo_j.class;
    }
}
