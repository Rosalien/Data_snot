/**
 *
 */
package org.cnrs.osuc.snot.refdata.informationcomplementaire;

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
import org.hibernate.annotations.Type;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.listevaleurinfo.ListeValeurInfo;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = InformationComplementaire.TABLE_NAME, 
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_NOM_INFCPLT}),indexes = 
                {@Index(name = "ic_lvalinf_idx", columnList = RefDataConstantes.LSTVALINFO_ID)})
public class InformationComplementaire implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.INFOCOMPL_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.INFOCOMPL_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = InformationComplementaire.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_NOM_INFCPLT, length = 50)
    private String nom;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_DESCRIPTION_INFCPLT)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = true, targetEntity = ListeValeurInfo.class)
    @JoinColumn(name = RefDataConstantes.LSTVALINFO_ID, nullable = true, referencedColumnName = ListeValeurInfo.ID_JPA)
    private ListeValeurInfo listeValeurInfo;

    /**
     * empty constructor
     */
    public InformationComplementaire() {
        super();
    }

    /**
     * @param nom
     * @param description
     * @param listeValeurInfo
     */
    public InformationComplementaire(String nom, String description, ListeValeurInfo listeValeurInfo) {
        super();
        this.nom = nom;
        this.description = description;
        this.listeValeurInfo = listeValeurInfo;
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
     * @return the listeValeurInfo
     */
    public ListeValeurInfo getListeValeurInfo() {
        return this.listeValeurInfo;
    }

    /**
     * @param listeValeurInfo
     *            the listeValeurInfo to set
     */
    public void setListeValeurInfo(ListeValeurInfo listeValeurInfo) {
        this.listeValeurInfo = listeValeurInfo;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * @param nom
     *            the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}
