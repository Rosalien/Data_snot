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
@Table(name = "mesures_flux_m_mfm", 
        uniqueConstraints = {@UniqueConstraint(columnNames = {"mfm_date", Site.RECURENT_NAME_ID})},
        indexes = {
        @Index(name = "mfm_ivf_idx", columnList = VersionFile.ID_JPA),
        @Index(name = "mfm_zet_idx", columnList = SiteSnot.RECURENT_NAME_ID)}
)
@AttributeOverrides({@AttributeOverride(name = "id", column = @Column(name = MesureFlux_m.MESURE_FLUX_M_NAME_ID)), @AttributeOverride(name = "date", column = @Column(name = "mfm_date")),
        @AttributeOverride(name = "originalLine", column = @Column(name = "mfm_ligne"))})
public class MesureFlux_m extends MesureFlux<ValeurFlux_mTour> {

    /**
     *
     */
    static public final String MESURE_FLUX_M_NAME_ID = "mfm_id";

    /**
     *
     */
    public MesureFlux_m() {
        super();
    }

    /**
     * @param versionFileSnot
     * @param date
     * @param ligne
     */
    public MesureFlux_m(VersionFile versionFileSnot, LocalDate date, Long ligne) {

        super(versionFileSnot, date, ligne);
    }

    @Override
    public String toString() {
        final String dateText = Optional.ofNullable(getDate()).map(d->DateUtil.getUTCDateTextFromLocalDateTime(d.atStartOfDay(), DateUtil.DD_MM_YYYY)).orElseGet(String::new);
        StringBuilder valeurs = new StringBuilder(dateText);
        valeurs.append(Constantes.CST_SPACE);
        for (ValeurFlux_mTour vft : getValeursFluxTour()) {
            valeurs.append(vft.getRealNode().getNodeable().getCode()).append(" : ").append(vft.getValue()).append(", ");
        }
        return valeurs.toString();
    }

}
