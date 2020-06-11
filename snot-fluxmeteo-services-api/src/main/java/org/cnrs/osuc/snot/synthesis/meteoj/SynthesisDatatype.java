package org.cnrs.osuc.snot.synthesis.meteoj;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisDatatype;

/**
 *
 * @author ptcherniati
 */
@Entity(name = "MeteojSynthesisDatatype")
@Table(name = "MeteojSynthesisDatatype", 
       indexes = {
           @Index(name = "MeteojSynthesisDatatype_site_idx", columnList = "site")
       })
public class SynthesisDatatype extends GenericSynthesisDatatype {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public SynthesisDatatype() {
        super();
    }

    /**
     *
     * @param site
     * @param minDate
     * @param maxDate
     */
    public SynthesisDatatype(String site, LocalDateTime minDate, LocalDateTime maxDate) {
        super();
        this.site = site;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

}
