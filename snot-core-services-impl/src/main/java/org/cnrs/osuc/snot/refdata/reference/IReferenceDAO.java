/**
 *
 */
package org.cnrs.osuc.snot.refdata.reference;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IReferenceDAO extends IDAO<Reference> {

    /**
     *
     * @param doi
     * @return
     */
    Optional<Reference> getByDOI(String doi);

}
