/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalcul;

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
import org.cnrs.osuc.snot.refdata.methodecalculreference.MethodeCalculReference;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = MethodeCalcul.TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_CODE_MCALC}))
public class MethodeCalcul implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.METHODECALCUL_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.METHODECALCUL_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = MethodeCalcul.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_CODE_MCALC, length = 50)
    private String code;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_LIBELLE_MCALC, length = 100)
    private String libelle;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_DESCRIPT_MCALC)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    /*
     * @ManyToMany(targetEntity=Reference.class) private List<Reference> lstReferences;
     */

    @OneToMany(mappedBy = RefDataConstantes.NAME_ATTRIBUT_MCALC, cascade = {PERSIST, MERGE, REFRESH})
    private List<MethodeCalculReference> lstReferences = new LinkedList<>();

    /**
     * empty constructor
     */
    public MethodeCalcul() {
        super();
    }

    /**
     * @param code
     * @param libelle
     * @param description
     */
    public MethodeCalcul(String code, String libelle, String description) {
        super();
        this.code = code;
        this.libelle = libelle;
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
    public List<MethodeCalculReference> getLstReferences() {
        return this.lstReferences;
    }

    /**
     * @param lstReferences
     *            the lstReferences to set
     */
    public void setLstReferences(List<MethodeCalculReference> lstReferences) {
        this.lstReferences = lstReferences;
    }

}
