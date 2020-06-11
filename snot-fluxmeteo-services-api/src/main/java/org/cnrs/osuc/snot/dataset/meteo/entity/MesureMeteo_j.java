/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.meteo.entity;


import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.BatchSize;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.utils.DateUtil;


/**
 * @author philippe
 */
@Entity
@Table(name = "mesures_meteo_j_mmj", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"mmj_date", Site.RECURENT_NAME_ID})},
        indexes = {
        @Index(name = "mmj_ivf_idx", columnList = VersionFile.ID_JPA),
        @Index(name = "mmj_zet_idx", columnList = SiteSnot.RECURENT_NAME_ID)}
)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = MesureMeteo_j.MESURE_METEO_J_NAME_ID)), @AttributeOverride(name = "date", column = @Column(name = "mmj_date")),
        @AttributeOverride(name = "originalLine", column = @Column(name = "mmj_ligne"))})
@BatchSize(size = 14)
//@cache(region = "org.hibernate.cache.StandardQueryCache", usage = CacheConcurrencyStrategy.READ_ONLY)
public class MesureMeteo_j extends MesureMeteo<ValeurMeteo_j> {

    /**
     *
     */
    public static final String MESURE_METEO_J_NAME_ID = "mmj_id";
    
    /**
     *
     */
    public MesureMeteo_j() {
        super();
    }

    /**
     * @param versionFileSnot
     * @param date
     * @param ligne
     */
    public MesureMeteo_j(VersionFile versionFileSnot, LocalDate date, Long ligne) {

        super(versionFileSnot, date, ligne);
    }

    @Override
    public String toString() {
        final String dateText = Optional.ofNullable(getDate()).map(d->DateUtil.getUTCDateTextFromLocalDateTime(d.atStartOfDay(), DateUtil.DD_MM_YYYY)).orElseGet(String::new);
        StringBuilder valeurs = new StringBuilder(dateText);
        valeurs.append(Constantes.CST_SPACE);
        this.getValeurs().forEach((vft) -> {
            valeurs.append(vft.getRealNode().getNodeable().getCode()).append(" : ").append(vft.getValue()).append(", ");
        });
        return valeurs.toString();
    }

    }
