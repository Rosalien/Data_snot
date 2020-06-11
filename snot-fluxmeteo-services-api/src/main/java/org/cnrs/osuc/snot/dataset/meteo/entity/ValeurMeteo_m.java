/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.meteo.entity;

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
@Table(name = "valeurs_meteo_m_vmm", indexes = {
    @Index(name = "mmm_vmm_index", columnList = "mesure_id"),
    @Index(name = "dvu_vmm_index", columnList = "id"),
    @Index(name="vmm_var_idx", columnList = VariableSnot.ID_JPA)
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = ValeurMeteo_m.VALEUR_METEO_M_TOUR_NAME_ID)),
    @AttributeOverride(name = "qualityClass", column = @Column(name = "vmm_quality_class", nullable = true)),
    @AttributeOverride(name = "value", column = @Column(name = "vmm_valeur", nullable = false))})
@BatchSize(size = 14)
//@cache(region = "org.hibernate.cache.StandardQueryCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class ValeurMeteo_m extends ValeurMeteo<MesureMeteo_m> {

    /**
     *
     */
    static public final String VALEUR_METEO_M_TOUR_NAME_ID = "vmm_id";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ValeurMeteo_m() {
        super();

    }

    /**
     * @param value
     * @param mesureMeteo
     * @param realNode
     * @param qualityClass
     */
    public ValeurMeteo_m(Float value, MesureMeteo_m mesureMeteo, RealNode realNode, QualityClass qualityClass) {
        super(value, realNode, qualityClass, mesureMeteo);
    }
}
