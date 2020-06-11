package org.cnrs.osuc.snot.refdata.typezoneetude;

import java.util.LinkedList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;

/**
 * @author philippe
 */
@Entity
@Table(name = TypeSite.NAME_ENTITY_JPA, uniqueConstraints = @UniqueConstraint(columnNames = {"tze_nom"}))
public class TypeSite {

    /**
     *
     */
    static public final String ID_JPA = "tze_id";

    /**
     *
     */
    static public final String NAME_ENTITY_JPA = "types_zone_etude_tze";
    static private final String STRING_EMPTY = "";
    @Id
    @Column(name = TypeSite.ID_JPA)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "tze_code", length = 150)
    private String code;

    @Column(nullable = true, name = "tze_definition", length = 150)
    private String definition = TypeSite.STRING_EMPTY;

    @Column(nullable = false, unique = true, name = "tze_nom", length = 150)
    private String nom = TypeSite.STRING_EMPTY;

    @OneToMany(mappedBy = "typeSite", cascade = ALL)
    @LazyToOne(LazyToOneOption.PROXY)
    private List<SiteSnot> sites = new LinkedList<>();

    /**
     *
     */
    public TypeSite() {
        super();
    }

    /**
     *
     * @param code
     * @param nom
     * @param definition
     */
    public TypeSite(String code, String nom, String definition) {
        super();
        this.code = code;
        this.nom = nom;
        this.definition = definition;
    }

    /**
     *
     * @param site
     */
    public void addSite(SiteSnot site) {
        if (site == null) {
            return;
        }
        site.setTypeSite(this);
        if (!this.getSites().contains(site)) {
            this.getSites().add(site);
        }
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getDefinition() {
        return this.definition == null ? TypeSite.STRING_EMPTY : this.definition;
    }

    /**
     *
     * @param definition
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return this.nom == null ? TypeSite.STRING_EMPTY : this.nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom == null ? TypeSite.STRING_EMPTY : nom;
    }

    /**
     *
     * @return
     */
    public List<SiteSnot> getSites() {
        return this.sites;
    }

    /**
     *
     * @param sites
     */
    public void setSites(List<SiteSnot> sites) {
        this.sites = sites;
    }

    /**
     *
     * @param definition
     */
    public void setDescription(String definition) {
        this.definition = definition == null ? TypeSite.STRING_EMPTY : definition;
    }

    @Override
    public String toString() {
        return this.nom;
    }

}
