/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.flux.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.entity.Valeur;
import org.cnrs.osuc.snot.dataset.flux.IValeurFluxTour;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.RealNode;


/**
 * @author philippe
 * @param <M>
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@BatchSize(size = 14)
//@cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public abstract class ValeurFluxTour<M extends MesureFlux> extends Valeur implements IValeurFluxTour<M> {


    /**
     *
     */
    private static final long serialVersionUID = 1L; 
    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false)
    @JoinColumn(name = "mesure_id")
    @LazyToOne(LazyToOneOption.PROXY)
    private M mesure;
    
    /**
     *
     */
    public ValeurFluxTour() {
        super();
    }

    /**
     * @param value
     * @param realNode
     * @param qualityClass
     * @param mesure
     */
    public ValeurFluxTour(Float value, RealNode realNode, QualityClass qualityClass, M mesure) {
        super(value, realNode, qualityClass);
        this.mesure= mesure;
        if (mesure != null) {
            mesure.getValeursFluxTour().add(this);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Long getMesureFluxId() {
        return this.getMesure() == null ? null : this.getMesure().getId();
    }
    /*
     * @return the mesure
     */
    @Override
    public M getMesure() {
        return this.mesure;
    }

    /**
     * @param mesure
     *            the mesure to set
     */
    public void setMesure(M mesure) {
        this.mesure = mesure;
    }

    @Override
    public String toString() {
        return "ValeurFluxTour [" + this.getRealNode().getNodeable().getCode() + Constantes.CST_SPACE + this.getValue();
    }
}
