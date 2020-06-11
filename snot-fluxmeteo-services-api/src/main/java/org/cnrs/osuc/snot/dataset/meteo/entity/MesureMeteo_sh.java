/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.meteo.entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.DateUtil;


/**
 * @author philippe
 */
@Entity
@Table(name = "mesures_meteo_sh_mms", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"mms_date", "mms_time", Site.RECURENT_NAME_ID})},
        indexes = {
        @Index(name = "mmsh_ivf_idx", columnList = VersionFile.ID_JPA),
        @Index(name = "mmsh_zet_idx", columnList = SiteSnot.RECURENT_NAME_ID)}
)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = MesureMeteo_sh.MESURE_METEO_SH_NAME_ID)), @AttributeOverride(name = "date", column = @Column(name = "mms_date")),
        @AttributeOverride(name = "originalLine", column = @Column(name = "mms_ligne")), @AttributeOverride(name = "time", column = @Column(name = "mms_time", nullable = false))

})
public class MesureMeteo_sh extends MesureMeteo<ValeurMeteo_sh> {

    /**
     *
     */
    static public final String MESURE_METEO_SH_NAME_ID = "mms_id";

    @Column(name = "mms_time")
    private LocalTime time;

    /**
     *
     */
    public MesureMeteo_sh() {
        super();
    }

    /**
     * @param versionFileSnot
     * @param date
     * @param time
     * @param ligne
     */
    public MesureMeteo_sh(VersionFile versionFileSnot, LocalDate date, LocalTime time, Long ligne) {

        super(versionFileSnot, date, ligne);
        this.time = time;
    }

    /**
     *
     * @return
     */
    @Override
    public LocalTime getTime() {
        return this.time;
    }

    /**
     *
     * @param time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final String dateText = Optional.ofNullable(getDate()).map(d->DateUtil.getUTCDateTextFromLocalDateTime(d.atStartOfDay(), DateUtil.DD_MM_YYYY)).orElseGet(String::new);
        final String timeText = Optional.ofNullable(getTime()).map(t->DateUtil.getUTCDateTextFromLocalDateTime(t, DateUtil.HH_MM)).orElseGet(String::new );
        StringBuilder valeurs = new StringBuilder(dateText);
        valeurs.append(" - ").append(timeText).append(Constantes.CST_SPACE);
        for (ValeurMeteo_sh vft : this.getValeurs()) {
            valeurs.append(vft.getRealNode().getNodeable().getCode()).append(" : ").append(vft.getValue()).append(", ");
        }
        return valeurs.toString();
    }
    }
