/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.flux;

import java.time.LocalDate;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux;
import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFluxTour_;
import org.inra.ecoinfo.mga.business.composite.Nodeable_;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.business.composite.RealNode_;
import org.inra.ecoinfo.synthesis.AbstractSynthesis;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;

/**
 *
 * @author ptchernia
 */
public abstract class AbstractFluxSynthesisDAO<SV extends GenericSynthesisValue, SD extends GenericSynthesisDatatype, V extends ValeurFluxTour<M> , M extends MesureFlux<V> > extends AbstractSynthesis<SV, SD> {

    @Override
    public Stream<SV> getSynthesisValue() {
        CriteriaQuery<SV> query = builder.createQuery(synthesisValueClass);
        Root<V> v = query.from(getValueClass());
        Join<V, MesureFlux> m = v.join(ValeurFluxTour_.mesure);
        Join<V, RealNode> varRn = v.join(ValeurFluxTour_.realNode);
        Join<RealNode, RealNode> siteRn = varRn.join(RealNode_.parent).join(RealNode_.parent).join(RealNode_.parent);
        Path<String> parcelleCode = siteRn.join(RealNode_.nodeable).get(Nodeable_.code);
        query.distinct(true);
        final Path<Float> valeur = v.get(ValeurFluxTour_.value);
        final Path<String> variableCode = varRn.join(RealNode_.nodeable).get(Nodeable_.code);
        final Path<LocalDate> dateMesure = m.get(MesureFlux_.date);
        final Path<String> sitePath = siteRn.get(RealNode_.path);
        query.select(
                builder.construct(
                        synthesisValueClass,
                        dateMesure,
                        sitePath,
                        variableCode,
                        builder.avg(valeur)
                )
        )
                .where(
                        builder.gt(valeur, -9999)
                )
                .groupBy(
                        sitePath,
                        variableCode,
                        dateMesure
                )
                .orderBy(
                        builder.asc(sitePath),
                        builder.asc(variableCode),
                        builder.asc(dateMesure)
                );
        return getResultAsStream(query);

    }
    abstract Class<V> getValueClass();
    abstract Class<M> getMeasureClass();

    
}
