package org.cnrs.osuc.snot.dataset.flux.jpa;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile_;
import org.inra.ecoinfo.dataset.versioning.jpa.JPAVersionFileDAO;
import org.cnrs.osuc.snot.dataset.ILocalPublicationDAO;
import org.cnrs.osuc.snot.dataset.entity.Mesure_;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour_;

/**
 * @author philippe
 *
 */
abstract class JPAPublicationFluxDAO extends JPAVersionFileDAO implements ILocalPublicationDAO {

    public <M extends MesureFlux<V>, V extends ValeurFluxTour<M>> void removeVersion(Long versionfileId, Class<M> measureClass, Class<V> valueClass) {
        deleteValues(valueClass, measureClass, versionfileId);
        deleteMeasures(measureClass, versionfileId);
        
    }

    protected <M extends MesureFlux<V>, V extends ValeurFluxTour<M>> void deleteValues(Class<V> valueClass, Class<M> measureClass, Long versionfileId) {
        CriteriaDelete<V> delete = builder.createCriteriaDelete(valueClass);
        Root<V> v = delete.from(valueClass);
        Subquery<M> measureDelete = delete.subquery(measureClass);
        Root<M> m = measureDelete.from(measureClass);
        measureDelete
                .select(m)
                .where(builder.equal(m.get(Mesure_.version).get(VersionFile_.id), versionfileId));
        delete
                .where(v.get(ValeurFluxTour_.mesure).in(measureDelete));
        delete(delete);
    }

    protected <M extends MesureFlux<V>, V extends ValeurFluxTour<M>> void deleteMeasures(Class<M> measureClass, Long versionfileId) {
        CriteriaDelete<M> delete = builder.createCriteriaDelete(measureClass);
        Root<M> m = delete.from(measureClass);
        delete
                .where(builder.equal(m.get(Mesure_.version).get(VersionFile_.id), versionfileId));
        delete(delete);
    }
}
