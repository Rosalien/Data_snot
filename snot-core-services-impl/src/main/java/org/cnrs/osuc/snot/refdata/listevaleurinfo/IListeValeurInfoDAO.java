/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IListeValeurInfoDAO extends IDAO<ListeValeurInfo> {

    /**
     *
     * @param nom
     * @return
     */
    Optional<ListeValeurInfo> getByNom(String nom);

}
