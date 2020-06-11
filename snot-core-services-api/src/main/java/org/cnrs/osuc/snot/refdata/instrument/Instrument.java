/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrument;

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
import org.hibernate.annotations.Type;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.instrumentreference.InstrumentReference;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = Instrument.TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_CODE_INSTR}))
public class Instrument implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.INSTRUMENT_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.INSTRUMENT_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = Instrument.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_CODE_INSTR, length = 50)
    private String code;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_LIBELLE_INSTR, length = 100)
    private String libelle;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_INFOSCAL_INSTR, length = 255)
    private String infosCalibration;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_FABRICANT_INSTR, length = 200)
    private String fabricant;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_DESCRIPT_INSTR)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    /*
     * @ManyToMany(targetEntity=Reference.class) private List<Reference> lstReferences;
     */

    @OneToMany(mappedBy = RefDataConstantes.NAME_ATTRIBUT_INSTR, cascade = {PERSIST, MERGE, REFRESH})
    private List<InstrumentReference> lstReferences = new LinkedList<>();

    /**
     * empty constructor
     */
    public Instrument() {
        super();
    }

    /**
     * @param code
     * @param libelle
     * @param infosCalibration
     * @param fabricant
     * @param description
     */
    public Instrument(String code, String libelle, String infosCalibration, String fabricant, String description) {
        super();
        this.code = code;
        this.libelle = libelle;
        this.infosCalibration = infosCalibration;
        this.fabricant = fabricant;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the fabricant
     */
    public String getFabricant() {
        return this.fabricant;
    }

    /**
     * @param fabricant
     *            the fabricant to set
     */
    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
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
     * @return the infosCalibration
     */
    public String getInfosCalibration() {
        return this.infosCalibration;
    }

    /**
     * @param infosCalibration
     *            the infosCalibration to set
     */
    public void setInfosCalibration(String infosCalibration) {
        this.infosCalibration = infosCalibration;
    }

    /**
     * @return the libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     * @param libelle
     *            the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * @return the lstReferences
     */
    public List<InstrumentReference> getLstReferences() {
        return this.lstReferences;
    }

    /**
     * @param lstReferences
     *            the lstReferences to set
     */
    public void setLstReferences(List<InstrumentReference> lstReferences) {
        this.lstReferences = lstReferences;
    }

}
