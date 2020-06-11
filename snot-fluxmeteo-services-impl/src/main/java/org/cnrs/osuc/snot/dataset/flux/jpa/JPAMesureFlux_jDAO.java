/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.flux.jpa;


import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_j;


/**
 * @author philippe
 * 
 */
public class JPAMesureFlux_jDAO extends JPAMesureFluxDAO<MesureFlux_j> implements IMesureFluxDAO<MesureFlux_j> {

    /**
     *
     * @return
     */
    @Override
    public Class<MesureFlux_j> getMeasureClass() {
        return MesureFlux_j.class;
    }
}
