package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.meteo;


import java.time.LocalDate;
import java.time.LocalTime;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.entity.QualityClass;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.AbstractProcessRecordFluxMeteo;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.Line;
import org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity.VariableValue;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;

/**
 *
 * @author ptcherniati
 * @param <M>
 * @param <V>
 */
public abstract class ProcessRecordMeteo<M extends MesureMeteo, V extends ValeurMeteo<M>> extends AbstractProcessRecordFluxMeteo {

    /**
     *
     */
    protected IMesureMeteoDAO<M> mesureMeteoDAO;

    /**
     *
     * @param mesureMeteoDAO
     */
    public void setMesureMeteoDAO(IMesureMeteoDAO<M> mesureMeteoDAO) {
        this.mesureMeteoDAO = mesureMeteoDAO;
    }

    /**
     *
     * @param ligne
     * @param version
     * @throws PersistenceException
     */
    @Override
    protected void rangerEnBase(Line ligne, VersionFile version) throws PersistenceException {
        M mesureMeteo = this.getNewMesure(version, ligne.getDate(), ligne.getTime(), ligne.getOriginalLineNumber());
        for (VariableValue variableValue : ligne.getVariablesValues()) {
            RealNode realNode = variableValue.getRealNode();
            V valeur = this.getNewValeurMeteo(variableValue.getValue(), mesureMeteo, realNode, variableValue.getQualityClass());
            mesureMeteo.getValeurs().add(valeur);
            valeur.setMesure(mesureMeteo);
        }
        this.mesureMeteoDAO.saveOrUpdate(mesureMeteo);
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
    abstract protected V getNewValeurMeteo(Float value, M mesureFlux, RealNode realNode, QualityClass qualityClass);

    /**
     *
     * @param version
     * @param date
     * @param time
     * @param originalLineNumber
     * @return
     */
    protected abstract M getNewMesure(VersionFile version, LocalDate date, LocalTime time, Long originalLineNumber);

}
