package org.cnrs.osuc.snot.dataset.meteo.jpa;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile_;
import org.inra.ecoinfo.dataset.versioning.jpa.JPAVersionFileDAO;
import org.cnrs.osuc.snot.dataset.ILocalPublicationDAO;
import org.cnrs.osuc.snot.dataset.entity.Mesure_;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_;

/**
 * @author philippe
 * 
 */
abstract class JPAPublicationMeteoDAO extends JPAVersionFileDAO implements ILocalPublicationDAO {
    
    public <M extends MesureMeteo<V>, V extends ValeurMeteo<M>> void removeVersion(Long versionfileId, Class<M> measureClass, Class<V> valueClass) {
        deleteValues(valueClass, measureClass, versionfileId);
        deleteMeasures(measureClass, versionfileId);
        
    }

    protected <M extends MesureMeteo<V>, V extends ValeurMeteo<M>> void deleteValues(Class<V> valueClass, Class<M> measureClass, Long versionfileId) {
        CriteriaDelete<V> delete = builder.createCriteriaDelete(valueClass);
        Root<V> v = delete.from(valueClass);
        Subquery<M> measureDelete = delete.subquery(measureClass);
        Root<M> m = measureDelete.from(measureClass);
        measureDelete
                .select(m)
                .where(builder.equal(m.get(Mesure_.version).get(VersionFile_.id), versionfileId));
        delete
                .where(v.get(ValeurMeteo_.mesure).in(measureDelete));
        delete(delete);
    }

    protected <M extends MesureMeteo<V>, V extends ValeurMeteo<M>> void deleteMeasures(Class<M> measureClass, Long versionfileId) {
        CriteriaDelete<M> delete = builder.createCriteriaDelete(measureClass);
        Root<M> m = delete.from(measureClass);
        delete
                .where(builder.equal(m.get(Mesure_.version).get(VersionFile_.id), versionfileId));
        delete(delete);
    }
}
