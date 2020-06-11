package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.flux;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_j;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_jTour;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 *
 * @author ptcherniati
 */
public class ProcessRecordFlux_j extends ProcessRecordFlux<MesureFlux_j, ValeurFlux_jTour> {

    /**
     *
     */
    public ProcessRecordFlux_j() {}

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
    protected ValeurFlux_jTour getNewValeurFlux(Float value, MesureFlux_j mesureFlux, RealNode realNode, QualityClass qualityClass) {
        return new ValeurFlux_jTour(value, mesureFlux, realNode, qualityClass);
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
    protected MesureFlux_j getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber) {
        return new MesureFlux_j(version, date, originalLineNumber);
    }

}
