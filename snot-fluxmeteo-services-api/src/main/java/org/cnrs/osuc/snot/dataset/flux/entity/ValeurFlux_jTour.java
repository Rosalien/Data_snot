/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.flux.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.mga.business.composite.RealNode;


/**
 * @author philippe
 */
@Entity
@Table(name = "Valeurs_flux_tour_vfj", indexes = {
    @Index(name = "mfj_vfj_index", columnList = "mesure_id"),
    @Index(name = "dvu_vfj_index", columnList = "id"),
    @Index(name="vfj_var_idx", columnList = VariableSnot.ID_JPA)
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = ValeurFlux_jTour.VALEUR_FLUX_J_TOUR_NAME_ID)), 
    @AttributeOverride(name = "qualityClass", column = @Column(name = "vfj_quality_class", nullable = true)),
    @AttributeOverride(name = "value", column = @Column(name = "vfj_valeur", nullable = false))
})
public class ValeurFlux_jTour extends ValeurFluxTour<MesureFlux_j> {

    /**
     *
     */
    static public final String VALEUR_FLUX_J_TOUR_NAME_ID = "vfj_id";

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ValeurFlux_jTour() {
        super();
    }

    /**
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param qualityClass
     */
    public ValeurFlux_jTour(Float value, MesureFlux_j mesureFlux, RealNode realNode, QualityClass qualityClass) {
        super(value, realNode, qualityClass, mesureFlux);
    }

}
