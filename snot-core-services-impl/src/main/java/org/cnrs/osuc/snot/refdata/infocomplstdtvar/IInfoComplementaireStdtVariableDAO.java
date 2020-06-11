/**
 *
 */
package org.cnrs.osuc.snot.refdata.infocomplstdtvar;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;


/**
 * @author sophie
 * 
 */
public interface IInfoComplementaireStdtVariableDAO extends IDAO<InformationComplementaireStdtVariable> {

    /**
     *
     * @param realNode
     * @param siteThemeDatatypeVariable
     * @param infoComplt
     * @return
     */
    Optional<InformationComplementaireStdtVariable> getByStdtVariableAndInfoComplt(RealNode realNode, InformationComplementaire infoComplt);

}