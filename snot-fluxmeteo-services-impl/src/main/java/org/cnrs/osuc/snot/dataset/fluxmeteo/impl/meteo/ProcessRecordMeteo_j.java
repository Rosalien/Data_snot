package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo;


import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo.ProcessRecordMeteo;
import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_j;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_j;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordMeteo_j extends ProcessRecordMeteo<MesureMeteo_j, ValeurMeteo_j> {

    /**
     *
     */
    protected IMesureMeteoDAO<? extends MesureMeteo_j> mesureMeteoDAO;

    /**
     *
     */
    public ProcessRecordMeteo_j() {}

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
    protected ValeurMeteo_j getNewValeurMeteo(Float value, MesureMeteo_j mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurMeteo_j(value, mesureFlux, realNode, qualityClass);
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
    protected MesureMeteo_j getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureMeteo_j(version, date, originalLineNumber);
    }
}
