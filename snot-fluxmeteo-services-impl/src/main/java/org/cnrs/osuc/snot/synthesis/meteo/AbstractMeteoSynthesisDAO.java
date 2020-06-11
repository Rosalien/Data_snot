/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.meteo;

import java.time.LocalDate;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_;
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
public abstract class AbstractMeteoSynthesisDAO<SV extends GenericSynthesisValue, SD extends GenericSynthesisDatatype, V extends ValeurMeteo<M> , M extends MesureMeteo<V> > extends AbstractSynthesis<SV, SD> {

    @Override
    public Stream<SV> getSynthesisValue() {
        CriteriaQuery<SV> query = builder.createQuery(synthesisValueClass);
        Root<V> v = query.from(getValueClass());
        Join<V, MesureMeteo> m = v.join(ValeurMeteo_.mesure);
        Join<V, RealNode> varRn = v.join(ValeurMeteo_.realNode);
        Join<RealNode, RealNode> siteRn = varRn.join(RealNode_.parent).join(RealNode_.parent).join(RealNode_.parent);
        Path<String> parcelleCode = siteRn.join(RealNode_.nodeable).get(Nodeable_.code);
        query.distinct(true);
        final Path<Float> valeur = v.get(ValeurMeteo_.value);
        final Path<String> variableCode = varRn.join(RealNode_.nodeable).get(Nodeable_.code);
        final Path<LocalDate> dateMesure = m.get(MesureMeteo_.date);
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
