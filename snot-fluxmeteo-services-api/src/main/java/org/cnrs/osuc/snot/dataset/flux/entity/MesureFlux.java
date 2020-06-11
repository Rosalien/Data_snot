/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.flux.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.Mesure;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Outils;

/**
 * @author philippe
 * @param <V>
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MesureFlux<V extends ValeurFluxTour> extends Mesure implements Comparable<MesureFlux> {

    /**
     *
     */
    public static int NO_INDEX = 0;

    private LocalDate date;
    private Long originalLine;
    @OneToMany(mappedBy = "mesure", cascade = ALL)
    private List<V> lstValeurs = new LinkedList<>();

    /**
     *
     */
    public MesureFlux() {
    }

    /**
     * @param versionFileSnot
     * @param date
     * @param ligne
     */
    public MesureFlux(VersionFile versionFileSnot, LocalDate date, Long ligne) {
        this.setVersion(versionFileSnot);
        this.date = date;
        this.originalLine = ligne;
        Outils.chercherNodeable(versionFileSnot, SiteSnot.class)
                .ifPresent(s -> setSite(s));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MesureFlux<?> other = (MesureFlux<?>) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return Objects.equals(this.getVersion(), other.getVersion());
    }

    @Override
    public int compareTo(MesureFlux o) {
        int comparaDate = this.getDate().compareTo(o.getDate());
        if (comparaDate != 0 || this.getTime() == null) {
            return comparaDate;
        }
        return this.getTime().compareTo(o.getTime());
    }

    /**
     *
     * @return
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    @Override
    public Long getId() {
        return super.getId();
    }

    /**
     *
     * @return
     */
    public Long getOriginalLine() {
        return this.originalLine;
    }

    /**
     *
     * @param originalLine
     */
    public void setOriginalLine(Long originalLine) {
        this.originalLine = originalLine;
    }

    /**
     *
     * @return
     */
    public LocalTime getTime() {
        return null;
    }

    /**
     *
     * @return
     */
    public List<V> getValeursFluxTour() {
        return lstValeurs;
    }

    /**
     *
     * @param valeursFluxTour
     */
    public void setValeursFluxTour(List<V> valeursFluxTour) {
        this.lstValeurs = valeursFluxTour;
    }
}
