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
@Table(name = "Valeurs_flux_tour_vfs", indexes = {
    @Index(name = "mfsh_vfsh_index", columnList = "mesure_id"),
    @Index(name = "dvu_vfsh_index", columnList = "id"),
    @Index(name="vfsh_var_idx", columnList = VariableSnot.ID_JPA)
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = ValeurFlux_shTour.VALEUR_FLUX_SH_TOUR_NAME_ID)), 
    @AttributeOverride(name = "mesure_id", column = @Column(name = MesureFlux_sh.MESURE_FLUX_SH_NAME_ID)), 
    @AttributeOverride(name = "qualityClass", column = @Column(name = "vfs_quality_class", nullable = true)),
    @AttributeOverride(name = "value", column = @Column(name = "vfsh_valeur", nullable = false))})
@BatchSize(size = 14)
//@cache(region = "org.hibernate.cache.StandardQueryCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class ValeurFlux_shTour extends ValeurFluxTour<MesureFlux_sh> {

    /**
     *
     */
    static public final String VALEUR_FLUX_SH_TOUR_NAME_ID = "vfs_id";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ValeurFlux_shTour() {
        super();
    }

    /**
     *
     * @param value
     * @param realNode
     * @param qualityClass
     * @param mesure
     */
    public ValeurFlux_shTour(Float value, RealNode realNode, QualityClass qualityClass, MesureFlux_sh mesure) {
        super(value, realNode, qualityClass, mesure);
    }

    /**
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param qualityClass
     */
    public ValeurFlux_shTour(Float value, MesureFlux_sh mesureFlux, RealNode realNode, QualityClass qualityClass) {

        super(value, realNode, qualityClass, mesureFlux);
    }

}
