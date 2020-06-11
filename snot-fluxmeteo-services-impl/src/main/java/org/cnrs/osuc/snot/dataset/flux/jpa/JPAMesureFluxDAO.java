/**
 * OREISnots project - see LICENCE.txt for use created: 5 mai 2009 11:50:44
 */
package org.cnrs.osuc.snot.dataset.flux.jpa;


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
import org.cnrs.osuc.snot.dataset.flux.IMesureFluxDAO;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_;


/**
 * @author philippe
 * @param <T>
 * 
 */
public abstract class JPAMesureFluxDAO<T extends MesureFlux> extends AbstractJPADAO<T> implements IMesureFluxDAO<T>{
    
    /**
     *
     * @param date
     * @param time
     * @return
     */
    @Override
    public Optional<Tuple> getLinePublicationNameDoublon(LocalDate date, LocalTime time){
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<T> m = query.from(getMeasureClass());
        Join<T, VersionFile> v = m.join(MesureFlux_.version);
        Join<VersionFile, Dataset> dataset = v.join(VersionFile_.dataset);
        Path<Long> datasetId = dataset.get(Dataset_.id);
        query
                .multiselect(datasetId, m.get(MesureFlux_.originalLine))
                .where(
                        builder.equal(m.get(MesureFlux_.date), date),
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
