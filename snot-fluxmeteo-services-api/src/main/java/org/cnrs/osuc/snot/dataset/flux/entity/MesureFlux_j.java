/**
 * OREISnots project - see LICENCE.txt for use created: 31 mars 2009 15:16:22
 */
package org.cnrs.osuc.snot.dataset.flux.entity;


import java.time.LocalDate;
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
@Table(name = "mesures_flux_j_mfj", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"mfj_date", Site.RECURENT_NAME_ID})},
        indexes = {
        @Index(name = "mfj_ivf_idx", columnList = VersionFile.ID_JPA),
        @Index(name = "mfj_zet_idx", columnList = SiteSnot.RECURENT_NAME_ID)}
)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = MesureFlux_j.MESURE_FLUX_J_NAME_ID)), @AttributeOverride(name = "date", column = @Column(name = "mfj_date")),
        @AttributeOverride(name = "originalLine", column = @Column(name = "mfj_ligne"))})
public class MesureFlux_j extends MesureFlux<ValeurFlux_jTour> {

    /**
     *
     */
    static public final String MESURE_FLUX_J_NAME_ID = "mfj_id";

    /**
     *
     */
    public MesureFlux_j() {
        super();
    }

    /**
     * @param versionFileSnot
     * @param date
     * @param ligne
     */
    public MesureFlux_j(VersionFile versionFileSnot, LocalDate date, Long ligne) {

        super(versionFileSnot, date, ligne);
    }

    @Override
    public String toString() {
        final String dateText = Optional.ofNullable(getDate()).map(d->DateUtil.getUTCDateTextFromLocalDateTime(d.atStartOfDay(), DateUtil.DD_MM_YYYY)).orElseGet(String::new);
        StringBuilder valeurs = new StringBuilder(dateText);
        valeurs.append(Constantes.CST_SPACE);
        for (ValeurFlux_jTour vft : this.getValeursFluxTour()) {
            valeurs.append(vft.getRealNode().getNodeable().getCode()).append(" : ").append(vft.getValue()).append(", ");
        }
        return valeurs.toString();
    }

}
