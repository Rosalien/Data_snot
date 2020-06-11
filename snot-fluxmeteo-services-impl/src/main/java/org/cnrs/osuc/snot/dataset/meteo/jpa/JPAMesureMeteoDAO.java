/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.meteo.jpa;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.inra.ecoinfo.AbstractJPADAO;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset_;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile_;
import org.cnrs.osuc.snot.dataset.meteo.IMesureMeteoDAO;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_;


/**
 * @author philippe
 * @param <T>
 * 
 */
public abstract class JPAMesureMeteoDAO<T extends MesureMeteo> extends AbstractJPADAO<T> implements IMesureMeteoDAO<T>{
    
    @Override
    public Optional<Tuple> getLinePublicationNameDoublon(LocalDate date, LocalTime time){
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<T> m = query.from(getMeasureClass());
        Join<T, VersionFile> v = m.join(MesureMeteo_.version);
        Join<VersionFile, Dataset> dataset = v.join(VersionFile_.dataset);
        Path<Long> datasetId = dataset.get(Dataset_.id);
        query
                .multiselect(datasetId, m.get(MesureMeteo_.originalLine))
                .where(
                        builder.equal(m.get(MesureMeteo_.date), date),
                        getTimeExpression(m, time)
                );
        return getOptional(query);
    }

    /**
     *
     * @param mesure
     * @param time
     * @return
     */
    protected Predicate getTimeExpression(Root<T> mesure, LocalTime time){
        return builder.isTrue(builder.literal(true));
    }
}
