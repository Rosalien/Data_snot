/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.flux.jpa;


import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_m;


/**
 * @author philippe
 * 
 */
public class JPAMesureFlux_mDAO extends JPAMesureFluxDAO<MesureFlux_m> implements IMesureFluxDAO<MesureFlux_m> {

    /**
     *
     * @return
     */
    @Override
    public Class<MesureFlux_m> getMeasureClass() {
        return MesureFlux_m.class;
    }
}
