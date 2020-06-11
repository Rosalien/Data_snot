package org.cnrs.osuc.snot.refdata.instrument;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IInstrumentDAO extends IDAO<Instrument> {

    /**
     *
     * @param code
     * @return
     */
    Optional<Instrument> getByCode(String code);
}