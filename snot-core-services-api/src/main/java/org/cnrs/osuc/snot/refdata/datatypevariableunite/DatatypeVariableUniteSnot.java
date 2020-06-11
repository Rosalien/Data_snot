package org.cnrs.osuc.snot.refdata.datatypevariableunite;

import java.io.Serializable;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.inra.ecoinfo.mga.business.composite.Nodeable;

/**
 * @author philippe
 *
 */
@Entity
@Table(name = DatatypeVariableUniteSnot.NAME_ENTITY_JPA_SNOT,
        uniqueConstraints = @UniqueConstraint(columnNames = {Jeu.ID_JPA,
            SiteSnot.RECURENT_NAME_ID, DataType.ID_JPA, VariableSnot.ID_JPA, Unite.ID_JPA
        }),
        indexes = {
            @Index(name = "dtvu_code_jeu_idx", columnList = Jeu.ID_JPA)
            ,
            @Index(name = "dtvu_code_site_idx", columnList = SiteSnot.RECURENT_NAME_ID)
            ,  
            @Index(name = "dtvu_code_datatype_idx", columnList = DataType.ID_JPA)
            ,
            @Index(name = "dtvu_code_variable_idx", columnList = VariableSnot.ID_JPA)
            ,
            @Index(name = "dtvu_code_unite_idx", columnList = Unite.ID_JPA)
        }
)

@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "datatypeVariableUnite_snot")
@PrimaryKeyJoinColumn(name = DatatypeVariableUniteSnot.ID_JPA)

public class DatatypeVariableUniteSnot extends Nodeable implements Serializable {

    public static final String ID_JPA = "sdvu_id";

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;

    @ManyToOne(optional = false, targetEntity = SiteSnot.class)
    @JoinColumn(name = SiteSnot.RECURENT_NAME_ID, referencedColumnName = SiteSnot.RECURENT_NAME_ID, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private SiteSnot siteSnot;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = DataType.ID_JPA, nullable = false)
    private DataType datatype;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = VariableSnot.ID_JPA, nullable = false)
    private VariableSnot variable;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = Unite.ID_JPA, nullable = false)
    private Unite unite;

    /**
     *
     */
    static public final String NAME_ENTITY_JPA_SNOT = "datatype_unite_variable_snot_vdt";

    /**
     *
     */
    public static final String NAME_ENTITY_JPA = "datatype_unite_variable_vdt";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = Constantes.CST_MIN)
    private Float min = null;

    @Column(name = Constantes.CST_MAX)
    private Float max = null;

    @Column(nullable = true, unique = false, name = "missingvalue", length = 20)
    private String missingvalue;

    /**
     *
     */
    public DatatypeVariableUniteSnot() {
        super();
    }

    /**
     *
     * @param code_jeu
     * @param siteSnot
     * @param datatype
     * @param unite
     * @param variable
     * @param min
     * @param max
     * @param missingvalue
     */
    public DatatypeVariableUniteSnot(Jeu code_jeu, SiteSnot siteSnot, DataType datatype, Unite unite, VariableSnot variable, Float min, Float max, String missingvalue) {
//        setDatatype(datatype);
//        setVariable(variable);
//        setUnite(unite);
        setCode(String.format("%s-%s-%s-%s", siteSnot.getCode(), datatype.getCode(), variable.getCode(), unite.getCode()));
//        setDataTypeVariableUnite(String.format("%s_%s_%s",datatype.getCode(), variable.getCode(), unite.getCode()));
        this.jeu = code_jeu;
        this.siteSnot = siteSnot;
        this.datatype = datatype;
        this.unite = unite;
        this.variable = variable;
        this.min = min;
        this.max = max;
        this.missingvalue = missingvalue;
    }


    public SiteSnot getSiteSnot() {
        return siteSnot;
    }

    public void setSiteSnot(SiteSnot siteSnot) {
        this.siteSnot = siteSnot;
    }

    public String getMissingValue() {
        return missingvalue;
    }

    public void setMissingValue(String missingvalue) {
        this.missingvalue = missingvalue;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public DataType getDatatype() {
        return datatype;
    }

    public void setDatatype(DataType datatype) {
        this.datatype = datatype;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public VariableSnot getVariable() {
        return variable;
    }

    public void setVariable(VariableSnot variable) {
        this.variable = variable;
    }

    /**
     *
     * @return
     */
    public Float getMax() {
        return this.max;
    }

    /**
     *
     * @param max
     */
    public void setMax(Float max) {
        this.max = max;
    }

    /**
     *
     * @return
     */
    public Float getMin() {
        return this.min;
    }

    /**
     *
     * @param min
     */
    public void setMin(Float min) {
        this.min = min;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return getCode();
    }

    /**
     *
     * @param <T>
     * @return
     */
    @Override
    public <T extends INodeable> Class<T> getNodeableType() {
        return (Class<T>) DatatypeVariableUniteSnot.class;
    }

    @Override
    public String toString() {
        return getCode();
    }
   
}
