/**
 *
 */
package org.cnrs.osuc.snot.refdata.methodecalculreference;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IMethodeCalculReferenceDAO extends IDAO<MethodeCalculReference> {

    /**
     *
     * @param codeMethodecalcul
     * @param doiReference
     * @return
     */
    Optional<MethodeCalculReference> getByCodeMCalcDoiRef(String codeMethodecalcul, String doiReference);

}