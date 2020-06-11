package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.flux;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_sh;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_shTour;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordFlux_if extends ProcessRecordFlux<MesureFlux_sh, ValeurFlux_shTour> {

    /**
     *
     */
    public ProcessRecordFlux_if() {}

    /**
     *
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param qualityClass
     * @return
     */
    @Override
    protected ValeurFlux_shTour getNewValeurFlux(Float value, MesureFlux_sh mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurFlux_shTour(value, mesureFlux, realNode, qualityClass);
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
    protected MesureFlux_sh getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureFlux_sh(version, date, time, originalLineNumber);
    }

}
