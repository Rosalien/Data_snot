package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.flux;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_m;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_mTour;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordFlux_m extends ProcessRecordFlux<MesureFlux_m, ValeurFlux_mTour> {

    /**
     *
     */
    public ProcessRecordFlux_m() {}

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
    protected ValeurFlux_mTour getNewValeurFlux(Float value, MesureFlux_m mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurFlux_mTour(value, mesureFlux, realNode, qualityClass);
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
    protected MesureFlux_m getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureFlux_m(version, date, originalLineNumber);
    }

}
