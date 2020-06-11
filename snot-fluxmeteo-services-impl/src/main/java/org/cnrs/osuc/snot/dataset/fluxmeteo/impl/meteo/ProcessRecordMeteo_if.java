package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_sh;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_sh;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordMeteo_if extends ProcessRecordMeteo<MesureMeteo_sh, ValeurMeteo_sh> {

    /**
     *
     */
    protected IMesureMeteoDAO<? extends MesureMeteo_sh> mesureMeteoDAO;

    /**
     *
     */
    public ProcessRecordMeteo_if() {}

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
    protected ValeurMeteo_sh getNewValeurMeteo(Float value, MesureMeteo_sh mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurMeteo_sh(value, mesureFlux, realNode, qualityClass);
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
    protected MesureMeteo_sh getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureMeteo_sh(version, date, time, originalLineNumber);
    }

}
