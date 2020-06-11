/**
 *
 */
package org.cnrs.osuc.snot.refdata.instrumentreference;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IInstrumentReferenceDAO extends IDAO<InstrumentReference> {

    /**
     *
     * @param codeInstrument
     * @param doiReference
     * @return
     */
    Optional<InstrumentReference> getByCodeInstrDoiRef(String codeInstrument, String doiReference);

}