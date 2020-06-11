/**
 *
 */
package org.cnrs.osuc.snot.refdata.jeu;

import java.util.List;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author jbparoissien
 * 
 */
public interface IJeuDAO extends IDAO<Jeu> {

    /**
     *
     * @param code_jeu
     * @return
     */
    Optional<Jeu> getByCode(String code_jeu);
 
    /**
     *
     * @return
     */
    List<Jeu> getAll();

//    /**A retirerJBP
//     *
//     * @param realNode
//     * @return
//     */
//    String buildCodeJeu(Collection<RealNode> realNode);
    
    
}