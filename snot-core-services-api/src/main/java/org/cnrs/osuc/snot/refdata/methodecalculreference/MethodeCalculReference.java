/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalculreference;

import java.io.Serializable;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.cnrs.osuc.snot.refdata.reference.Reference;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = MethodeCalculReference.TABLE_NAME, 
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.METHODECALCUL_ID, RefDataConstantes.REFERENCE_ID}),
        indexes = {
        @Index(name = "mcr_mc", columnList = RefDataConstantes.METHODECALCUL_ID),
        @Index(name = "mcr_ref", columnList = RefDataConstantes.REFERENCE_ID)})
public class MethodeCalculReference implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.MCALCREFERENCE_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.MCALCREFERENCE_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = MethodeCalculReference.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = MethodeCalcul.class)
    @JoinColumn(name = RefDataConstantes.METHODECALCUL_ID, referencedColumnName = MethodeCalcul.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private MethodeCalcul methodeCalcul;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = Reference.class)
    @JoinColumn(name = RefDataConstantes.REFERENCE_ID, referencedColumnName = Reference.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Reference referenceMCalc;

    /**
     * empty constructor
     */
    public MethodeCalculReference() {
        super();
    }

    /**
     * @param methodeCalcul
     * @param referenceMCalc
     */
    public MethodeCalculReference(MethodeCalcul methodeCalcul, Reference referenceMCalc) {
        super();
        this.methodeCalcul = methodeCalcul;
        this.referenceMCalc = referenceMCalc;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the methodeCalcul
     */
    public MethodeCalcul getMethodeCalcul() {
        return this.methodeCalcul;
    }

    /**
     * @param methodeCalcul
     *            the methodeCalcul to set
     */
    public void setMethodeCalcul(MethodeCalcul methodeCalcul) {
        this.methodeCalcul = methodeCalcul;
    }

    /**
     * @return the referenceMCalc
     */
    public Reference getReferenceMCalc() {
        return this.referenceMCalc;
    }

    /**
     * @param referenceMCalc
     *            the referenceMCalc to set
     */
    public void setReferenceMCalc(Reference referenceMCalc) {
        this.referenceMCalc = referenceMCalc;
    }

}
