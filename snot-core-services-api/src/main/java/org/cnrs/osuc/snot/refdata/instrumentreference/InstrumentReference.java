/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrumentreference;

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
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.reference.Reference;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = InstrumentReference.TABLE_NAME, 
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.INSTRUMENT_ID, RefDataConstantes.REFERENCE_ID}),indexes = 
                {@Index(name = "ir_ref_idx", columnList = RefDataConstantes.REFERENCE_ID),
                @Index(name = "ir_instr_idx", columnList = RefDataConstantes.INSTRUMENT_ID)})
public class InstrumentReference implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.INSTRUMENTREFERENCE_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.INSTRUMENTREFERENCE_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = InstrumentReference.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = Instrument.class)
    @JoinColumn(name = RefDataConstantes.INSTRUMENT_ID, referencedColumnName = Instrument.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Instrument instrument;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = Reference.class)
    @JoinColumn(name = RefDataConstantes.REFERENCE_ID, referencedColumnName = Reference.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Reference referenceInstrument;

    /**
     * empty constructor
     */
    public InstrumentReference() {
        super();
    }

    /**
     * @param instrument
     * @param referenceInstrument
     */
    public InstrumentReference(Instrument instrument, Reference referenceInstrument) {
        super();
        this.instrument = instrument;
        this.referenceInstrument = referenceInstrument;
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
     * @return the instrument
     */
    public Instrument getInstrument() {
        return this.instrument;
    }

    /**
     * @param instrument
     *            the instrument to set
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    /**
     * @return the referenceInstrument
     */
    public Reference getReferenceInstrument() {
        return this.referenceInstrument;
    }

    /**
     * @param referenceInstrument
     *            the referenceInstrument to set
     */
    public void setReferenceInstrument(Reference referenceInstrument) {
        this.referenceInstrument = referenceInstrument;
    }

}
