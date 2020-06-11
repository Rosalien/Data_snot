package org.cnrs.osuc.snot.refdata.site;

import java.time.LocalDate;
import java.util.Optional;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.cnrs.osuc.snot.refdata.typezoneetude.TypeSite;
import org.inra.ecoinfo.mga.business.composite.INodeable;
import org.inra.ecoinfo.refdata.site.Site;

/**
 *
 * @author ptcherniati
 */
@Entity
@Table(name = SiteSnot.NAME_ENTITY_JPA,
        indexes = {
            @Index(name = "site_snot_typesite_idx", columnList = TypeSite.ID_JPA)
        })
//@DiscriminatorValue("site_snot")
//@AssociationOverride(name = "parent", joinColumns = @JoinColumn(name = Site.RECURENT_NAME_PARENT_ID, nullable = true))
@PrimaryKeyJoinColumn(name = SiteSnot.RECURENT_NAME_ID)

public class SiteSnot extends Site {

    /**
     *
     */
    static public final String NAME_ENTITY_JPA = "site_snot";

    /**
     * The Constant RECURENT_NAME_PARENT_ID @link(String).
     */
    public static final String NAME_PARENT_ID = "parent_id";
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = {MERGE, PERSIST, REFRESH}, optional = false)
    @JoinColumn(name = TypeSite.ID_JPA, referencedColumnName = TypeSite.ID_JPA, nullable = false)
    private TypeSite typeSite;

    @Column(nullable = true, unique = false, name = "zet_coordonnees_bbox", length = 250)
    private String coordonnees_bbox;

    @Column(nullable = true, unique = false, name = "zet_surface", length = 10)
    private String surface;

    @Column(nullable = true, unique = false, name = "zet_altitude", length = 10)
    private String altitude;

    @Column(nullable = true, unique = false, name = "zet_climat", length = 50)
    private String climat;

    @Column(nullable = true, unique = false, name = "zet_geologie", length = 50)
    private String geologie;

    @Column(nullable = true, unique = false, name = "zet_dir_vent", length = 50)
    private String direction_vent;

    @Column(nullable = true, unique = false, name = "zet_pays", length = 50)
    private String pays;

    @Column(nullable = false, unique = false, name = "zet_date_debut")
    private LocalDate date_debut;

    @Column(nullable = true, unique = false, name = "zet_date_fin")
    private LocalDate date_fin;

    /**
     *
     */
    public SiteSnot() {
        super();
    }

    /**
     *
     * @param nom
     */
    public SiteSnot(String nom) {
        super(nom, null, null);
        this.setDate_debut(LocalDate.now());
        this.setDate_fin(LocalDate.now());
    }

    /**
     *
     * @param typeSite
     * @param parent
     * @param nom
     * @param description
     * @param coordonnees_bbox
     * @param surface
     * @param altitude
     * @param climat
     * @param geologie
     * @param directionVent
     * @param pays
     *
     *
     */
    public SiteSnot(TypeSite typeSite, SiteSnot parent, String nom, String description, String coordonnees_bbox, String surface, String altitude, String climat, String geologie, String direction_vent, String pays, LocalDate dateDebut, LocalDate dateFin) {
        super(nom, description, parent);
        Optional.ofNullable(typeSite).ifPresent(t -> this.typeSite = t);
        this.coordonnees_bbox = coordonnees_bbox;
        this.surface = surface;
        this.altitude = altitude;
        this.climat = climat;
        this.geologie = geologie;
        this.direction_vent = direction_vent;
        this.pays = pays;
        this.date_debut = dateDebut;
        this.date_fin = dateFin;
    }


    /**
     *
     * @return
     */
    public String getAltitude() {
        return this.altitude;
    }

    /**
     *
     * @param altitude
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @return
     */
    public LocalDate getDate_debut() {
        return this.date_debut;
    }

    /**
     *
     * @param dateDebut
     */
    public void setDate_debut(LocalDate dateDebut) {
        this.date_debut = dateDebut;
    }

    /**
     *
     * @return
     */
    public LocalDate getDate_fin() {
        return this.date_fin;
    }

    /**
     *
     * @param dateFin
     */
    public void setDate_fin(LocalDate dateFin) {
        this.date_fin = dateFin;
    }

    /**
     *
     * @return
     */
    public String getDirection_vent() {
        return this.direction_vent;
    }

    /**
     *
     * @param directionVent
     */
    public void setDirection_vent(String directionVent) {
        this.direction_vent = directionVent;
    }

    /**
     *
     * @return
     */
    public String getPays() {
        return this.pays;
    }

    public String getCoordonnees_bbox() {
        return coordonnees_bbox;
    }

    public void setCoordonnees_bbox(String coordonnees_bbox) {
        this.coordonnees_bbox = coordonnees_bbox;
    }

    public String getClimat() {
        return climat;
    }

    public void setClimat(String climat) {
        this.climat = climat;
    }

    public String getGeologie() {
        return geologie;
    }

    public void setGeologie(String geologie) {
        this.geologie = geologie;
    }

    /**
     *
     * @param pays
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    /**
     *
     * @return
     */
    public SiteSnot getRoot() {
        if (this.getParent() != null) {
            return ((SiteSnot) this.getParent()).getRoot();
        } else {
            return this;
        }
    }

    /**
     *
     * @return
     */
    public SiteSnot getRootSite() {
        SiteSnot root = this;
        while (root.getParent() != null) {
            root = (SiteSnot) root.getParent();
        }
        return root;
    }

    /**
     *
     * @return
     */
    public String getSurface() {
        return this.surface;
    }

    /**
     *
     * @param surface
     */
    public void setSurface(String surface) {
        this.surface = surface;
    }

    /**
     *
     * @return
     */
    public TypeSite getTypeSite() {
        return this.typeSite;
    }

    /**
     *
     * @param typeSite
     */
    public void setTypeSite(TypeSite typeSite) {
        this.typeSite = typeSite;
    }

    @Override
    public String toString() {
        return "Site : " + this.getPath();
    }

    /**
     *
     * @param <T>
     * @return
     */
    @Override
    public <T extends INodeable> Class<T> getNodeableType() {
        return (Class<T>) SiteSnot.class;
    }
}
