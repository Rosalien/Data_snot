/**
 *
 */
package org.cnrs.osuc.snot.refdata.informationcomplementaire;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IInformationComplementaireDAO extends IDAO<InformationComplementaire> {

    /**
     *
     * @param nom
     * @return
     */
    Optional<InformationComplementaire> getByNom(String nom);

}
