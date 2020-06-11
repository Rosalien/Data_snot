/**
 *
 */
package org.cnrs.osuc.snot.refdata.traitement;

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
import org.hibernate.annotations.Type;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = Traitement.TABLE_NAME, 
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_CODE_TRT}), 
        indexes = {
            @Index(name = "tra_zet_idx", columnList = RefDataConstantes.COLUMN_ZET_ID)
        })
public class Traitement implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.TRAITEMENT_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.TRAITEMENT_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = Traitement.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = SiteSnot.class)
    @JoinColumn(name = RefDataConstantes.COLUMN_ZET_ID, referencedColumnName = SiteSnot.RECURENT_NAME_ID, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private SiteSnot zoneEtude;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_CODE_TRT, length = 50)
    private String code;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_LIBELLE_TRT, length = 100)
    private String libelle;
    
    @Type(type = "org.hibernate.type.TextType")
    @Column(nullable = true, name = RefDataConstantes.COLUMN_DESCRIPT_TRT)
    private String description;

    /**
     * empty constructor
     */
    public Traitement() {
        super();
    }

    /**
     * @param zoneEtude
     * @param code
     * @param libelle
     * @param description
     */
    public Traitement(SiteSnot zoneEtude, String code, String libelle, String description) {
        super();
        this.zoneEtude = zoneEtude;
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
     * @return the zoneEtude
     */
    public SiteSnot getZoneEtude() {
        return this.zoneEtude;
    }

    /**
     * @param zoneEtude
     *            the zoneEtude to set
     */
    public void setZoneEtude(SiteSnot zoneEtude) {
        this.zoneEtude = zoneEtude;
    }

}
