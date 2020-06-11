/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalcul;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IMethodeCalculDAO extends IDAO<MethodeCalcul> {

    /**
     *
     * @param code
     * @return
     */
    Optional<MethodeCalcul> getByCode(String code);

}