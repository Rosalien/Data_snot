/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.entity;

import java.util.Optional;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.refdata.unite.Unite;


/**
 * @author philippe
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Valeur extends AbstractValeur {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
     * @Id
     * 
     * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
     */

    @Enumerated(EnumType.ORDINAL)
    private QualityClass qualityClass;

    @Column(precision = 20)
    private Float value;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false)
    @JoinColumn(name = RealNode.ID_JPA, referencedColumnName = RealNode.ID_JPA, nullable = false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private RealNode realNode;

    /**
     *
     */
    public Valeur() {
        super();
    }

    /**
     * @param value
     * @param realNode
     * @param qualityClass
     */
    public Valeur(Float value, RealNode realNode, QualityClass qualityClass) {
        super();
        this.value = value;
        this.realNode = realNode;
        this.qualityClass = qualityClass;
    }

    /*
     * public Long getId() { return id; }
     */

    /**
     *
     * @return
    /*
     * public Long getId() { return id; }
     */

    /**
     *
     * @return
    /*
     * public Long getId() { return id; }
     */

    /**
     *
     * @return
    /*
     * public Long getId() { return id; }
     */

    /**
     *
     * @return
     */
    

    public QualityClass getQualityClass() {
        return this.qualityClass;
    }

    /*
     * public void setId(Long id) { this.id = id; }
     */

    /**
     *
     * @param qualityClass
     */
    
    public void setQualityClass(QualityClass qualityClass) {
        this.qualityClass = qualityClass;
    }

    /**
     *
     * @return
     */
    public Float getValue() {
        return this.value;
    }

    /**
     *
     * @param value
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public RealNode getRealNode() {
        return this.realNode;
    }

    /**
     *
     * @param variable
     */
    public void setRealNode(RealNode variable) {
        this.realNode = variable;
    }
    
    /**
     *
     * @return
     */
    @Transient
    public VariableSnot getVariable(){
        return Optional.ofNullable(realNode)
                .map(rn->(DatatypeVariableUniteSnot)rn.getNodeable())
                .map(dvu->(VariableSnot)dvu.getVariable())
                .orElse(null);
    }
    
    /**
     *
     * @return
     */
    @Transient
    public Unite getUnite(){
        return Optional.ofNullable(realNode)
                .map(rn->(DatatypeVariableUniteSnot)rn.getNodeable())
                .map(dvu->dvu.getUnite())
                .orElse(null);
    }
}
