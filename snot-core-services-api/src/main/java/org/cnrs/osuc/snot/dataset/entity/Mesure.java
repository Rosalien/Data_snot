/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
/**
 * @author philippe
 * 
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@BatchSize(size = 336)
//@cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public abstract class Mesure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {MERGE, REFRESH}, optional = false, targetEntity = VersionFile.class)
    @JoinColumn(name = VersionFile.ID_JPA, referencedColumnName = VersionFile.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private VersionFile version;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false)
    @JoinColumn(name = SiteSnot.RECURENT_NAME_ID, referencedColumnName = SiteSnot.RECURENT_NAME_ID, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private SiteSnot site;

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
    public SiteSnot getSite() {
        return this.site;
    }

    /**
     *
     * @param site
     */
    public void setSite(SiteSnot site) {
        this.site = site;
    }

    /**
     *
     * @return
     */
    public VersionFile getVersion() {
        return this.version;
    }

    /**
     *
     * @param version
     */
    public void setVersion(VersionFile version) {
        this.version = version;
    }
}
