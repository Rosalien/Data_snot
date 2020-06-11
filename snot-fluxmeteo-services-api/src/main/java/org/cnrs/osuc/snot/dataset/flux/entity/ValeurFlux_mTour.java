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
import org.hibernate.annotations.BatchSize;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.inra.ecoinfo.mga.business.composite.RealNode;


/**
 * @author philippe
 */
@Entity
@Table(name = "Valeurs_flux_tour_vfm", indexes = {
    @Index(name = "mfm_vfm_index", columnList = "mesure_id"),
    @Index(name = "dvu_vfm_index", columnList = "id"),
    @Index(name="vfm_var_idx", columnList = VariableSnot.ID_JPA)
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = ValeurFlux_mTour.VALEUR_FLUX_M_TOUR_NAME_ID)), 
    @AttributeOverride(name = "qualityClass", column = @Column(name = "vfm_quality_class", nullable = true)),
    @AttributeOverride(name = "value", column = @Column(name = "vfm_valeur", nullable = false))})
@BatchSize(size = 14)
//@cache(region = "org.hibernate.cache.StandardQueryCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class ValeurFlux_mTour extends ValeurFluxTour<MesureFlux_m> {

    /**
     *
     */
    static public final String VALEUR_FLUX_M_TOUR_NAME_ID = "vfm_id";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ValeurFlux_mTour() {
        super();
        
    }

    /**
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param qualityClass
     */
    public ValeurFlux_mTour(Float value, MesureFlux_m mesureFlux, RealNode realNode, QualityClass qualityClass) {
        super(value, realNode, qualityClass, mesureFlux);
    }

}
