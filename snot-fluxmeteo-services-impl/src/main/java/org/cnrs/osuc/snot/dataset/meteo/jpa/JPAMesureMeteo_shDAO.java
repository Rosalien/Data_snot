/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.meteo.jpa;


import java.time.LocalTime;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_sh;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_sh_;


/**
 * @author philippe
 * 
 */
public class JPAMesureMeteo_shDAO extends JPAMesureMeteoDAO<MesureMeteo_sh> implements IMesureMeteoDAO<MesureMeteo_sh> {

    @Override
    protected Predicate getTimeExpression(Root<MesureMeteo_sh> mesure, LocalTime time) {
        return builder.equal(mesure.get(MesureMeteo_sh_.time), time);
    }

    @Override
    public Class<MesureMeteo_sh> getMeasureClass() {
        return MesureMeteo_sh.class;
        
    }
}
