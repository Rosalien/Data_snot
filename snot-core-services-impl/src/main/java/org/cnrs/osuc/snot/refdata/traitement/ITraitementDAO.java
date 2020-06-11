/**
 *
 */
package org.cnrs.osuc.snot.refdata.traitement;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface ITraitementDAO extends IDAO<Traitement> {

    /**
     *
     * @param code
     * @return
     */
    Optional<Traitement> getByCode(String code);

}