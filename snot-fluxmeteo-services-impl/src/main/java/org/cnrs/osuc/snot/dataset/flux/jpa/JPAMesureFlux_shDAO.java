/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.flux.jpa;

import java.time.LocalTime;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_sh;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_sh_;

/**
 * @author philippe
 *
 */
public class JPAMesureFlux_shDAO extends JPAMesureFluxDAO<MesureFlux_sh> implements IMesureFluxDAO<MesureFlux_sh> {

    /**
     *
     * @return
     */
    @Override
    public Class<MesureFlux_sh> getMeasureClass() {
        return MesureFlux_sh.class;
    }

    @Override
    protected Predicate getTimeExpression(Root<MesureFlux_sh> mesure, LocalTime time) {
        return builder.equal(mesure.get(MesureFlux_sh_.time), time);
    }
}
