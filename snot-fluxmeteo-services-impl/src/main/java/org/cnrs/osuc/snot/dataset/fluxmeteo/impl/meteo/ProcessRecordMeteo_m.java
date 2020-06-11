package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo;


import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo.ProcessRecordMeteo;
import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_m;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_m;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordMeteo_m extends ProcessRecordMeteo<MesureMeteo_m, ValeurMeteo_m> {

    /**
     *
     */
    protected IMesureMeteoDAO<? extends MesureMeteo_m> mesureMeteoDAO;

    /**
     *
     */
    public ProcessRecordMeteo_m() {}

    /**
     *
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param variable
     * @param qualityClass
     * @return
     */
    @Override
    protected ValeurMeteo_m getNewValeurMeteo(Float value, MesureMeteo_m mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurMeteo_m(value, mesureFlux, realNode, qualityClass);
    }

    /**
     *
     * @param version
     * @param date
     * @param time
     * @param originalLineNumber
     * @return
     */
    @Override
    protected MesureMeteo_m getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureMeteo_m(version, date, originalLineNumber);
    }
}
