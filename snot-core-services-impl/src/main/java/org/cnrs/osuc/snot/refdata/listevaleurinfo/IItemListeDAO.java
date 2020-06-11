/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import java.util.List;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;


/**
 * @author sophie
 * 
 */
public interface IItemListeDAO extends IDAO<ItemListe> {

    /**
     *
     * @param libelle
     * @param listeValeurInfo
     * @return
     */
    Optional<ItemListe> getByLibelleListeValeurInfo(String libelle, ListeValeurInfo listeValeurInfo);

    /**
     *
     * @param libelle
     * @param note
     * @param listeValeurInfo
     * @return
     */
    Optional<ItemListe> getByLibelleNoteListeValeurInfo(String libelle, String note, ListeValeurInfo listeValeurInfo);

    /**
     *
     * @param listeValeurInfo
     * @return
     */
    List<ItemListe> getByListeValeurInfo(ListeValeurInfo listeValeurInfo);

}