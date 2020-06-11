/**
 *
 */
package org.cnrs.osuc.snot.refdata.reference;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.instrumentreference.InstrumentReference;
import org.cnrs.osuc.snot.refdata.methodecalculreference.MethodeCalculReference;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = Reference.TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_DOI_REF}))
public class Reference implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.REFERENCE_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.REFERENCE_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = Reference.ID_JPA)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_DOI_REF, length = 200)
    private String doi;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_PREMIERAUTEUR_REF, length = 50)
    private String premierAuteur;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_ANNEE_REF, length = 4)
    private String annee;

    /*
     * @ManyToMany(targetEntity=MethodeCalcul.class, mappedBy="lstReferences") private List<MethodeCalcul> lstMethodeCalculs;
     */

    @OneToMany(mappedBy = RefDataConstantes.NAME_ATTRIBUT_REFMCALC, cascade = {PERSIST, MERGE, REFRESH})
    private List<MethodeCalculReference> lstMethodesCalcul = new LinkedList<>();

    @OneToMany(mappedBy = RefDataConstantes.NAME_ATTRIBUT_REFINSTRUMENT, cascade = {PERSIST, MERGE, REFRESH})
    private List<InstrumentReference> lstInstrument = new LinkedList<>();

    /*
     * @ManyToMany(targetEntity=Instrument.class, mappedBy="lstReferences") private List<Instrument> lstInstruments;
     */

    /**
     * empty constructor
     */
    public Reference() {
        super();
    }

    /**
     * @param premierAuteur
     * @param annee
     * @param doi
     */
    public Reference(String doi, String premierAuteur, String annee) {
        super();
        this.doi = doi;
        this.premierAuteur = premierAuteur;
        this.annee = annee;
    }

    /**
     * @return the annee
     */
    public String getAnnee() {
        return this.annee;
    }

    /**
     * @param annee
     *            the annee to set
     */
    public void setAnnee(String annee) {
        this.annee = annee;
    }

    /**
     * @return the doi
     */
    public String getDoi() {
        return this.doi;
    }

    /**
     * @param doi
     *            the doi to set
     */
    public void setDoi(String doi) {
        this.doi = doi;
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
     * @return the lstInstrument
     */
    public List<InstrumentReference> getLstInstrument() {
        return this.lstInstrument;
    }

    /**
     * @param lstInstrument
     *            the lstInstrument to set
     */
    public void setLstInstrument(List<InstrumentReference> lstInstrument) {
        this.lstInstrument = lstInstrument;
    }

    /**
     * @return the lstMethodeCalculs
     */
    public List<MethodeCalculReference> getLstMethodesCalcul() {
        return this.lstMethodesCalcul;
    }

    /**
     * @param lstMethodesCalcul
     */
    public void setLstMethodesCalcul(List<MethodeCalculReference> lstMethodesCalcul) {
        this.lstMethodesCalcul = lstMethodesCalcul;
    }

    /**
     * @return the premierAuteur
     */
    public String getPremierAuteur() {
        return this.premierAuteur;
    }

    /**
     * @param premierAuteur
     *            the premierAuteur to set
     */
    public void setPremierAuteur(String premierAuteur) {
        this.premierAuteur = premierAuteur;
    }

}
