package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.flux;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.AbstractProcessRecordFluxMeteo;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 * @param <M>
 * @param <V>
 */
public abstract class ProcessRecordFlux<M extends MesureFlux, V extends ValeurFluxTour<M>> extends AbstractProcessRecordFluxMeteo {

    /**
     *
     */
    protected IMesureFluxDAO<M> mesureFluxDAO;

    /**
     *
     * @param mesureFluxDAO
     */
    public void setMesureFluxDAO(IMesureFluxDAO<M> mesureFluxDAO) {
        this.mesureFluxDAO = mesureFluxDAO;
    }

    /**
     *
     * @param ligne
     * @param version
     * @throws PersistenceException
     */
    @Override
    protected void rangerEnBase(Line ligne, VersionFile version) throws PersistenceException {
        M mesureFlux = this.getNewMesure(version, ligne.getDate(), ligne.getTime(), ligne.getOriginalLineNumber());
        for (VariableValue variableValue : ligne.getVariablesValues()) {
            RealNode realNode = variableValue.getRealNode();
            V valeur = this.getNewValeurFlux(variableValue.getValue(), mesureFlux, realNode, variableValue.getQualityClass());
            mesureFlux.getValeursFluxTour().add(valeur);
            valeur.setMesure(mesureFlux);
        }
        this.mesureFluxDAO.saveOrUpdate(mesureFlux);
    }

    /**
     *
     * @param value
     * @param mesureFlux
     * @param realNode
     * @param variable
     * @param qualityClass
     * @return
     */
    abstract protected V getNewValeurFlux(Float value, M mesureFlux, RealNode realNode, QualityClass qualityClass);

    /**
     *
     * @param version
     * @param date
     * @param time
     * @param originalLineNumber
     * @return
     */
    abstract protected M getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber);
}
