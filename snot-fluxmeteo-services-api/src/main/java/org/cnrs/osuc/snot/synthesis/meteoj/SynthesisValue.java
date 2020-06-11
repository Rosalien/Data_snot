/**
 * OREILacs project - see LICENCE.txt for use created: 31 mars 2009 13:30:55
 */
package org.cnrs.osuc.snot.synthesis.meteoj;


import java.time.LocalDate;
import javax.persistence.Entity;
import org.cnrs.osuc.snot.synthesis.ITypeGraphiqueSynthese;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;


/**
 * @author "Antoine Schellenberger"
 * 
 */
@Entity(name = "MeteojSynthesisValue")
@javax.persistence.Table(name = "MeteojSynthesisValue", 
       indexes = {
           @javax.persistence.Index(name = "MeteojSynthesisValue_site_variable_idx", columnList = "site,variable"),
           @javax.persistence.Index(name = "MeteojSynthesisValue_site_idx", columnList = "site")
       })
public class SynthesisValue extends GenericSynthesisValue implements ITypeGraphiqueSynthese {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected static final boolean SEMIHORAIRE = false;

    /**
     *
     */
    public SynthesisValue() {
        super();
    }

    /**
     *
     * @param date
     * @param site
     * @param variable
     * @param value
     */
    public SynthesisValue(final LocalDate date, final String site, final String variable, final Double value) {
        super();
        this.date = date.atStartOfDay();
        this.site = site;
        this.variable = variable;
        this.valueFloat = value.floatValue();
        this.valueString = value.toString();
        this.isMean=false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSemihoraire() {
        return SEMIHORAIRE;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isPresenceAbsence() {

        return false;
    }

}
