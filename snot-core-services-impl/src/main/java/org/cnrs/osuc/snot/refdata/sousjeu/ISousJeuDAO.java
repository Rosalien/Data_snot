/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.sousjeu;

import java.util.List;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;
import java.time.LocalDate;

/**
 *
 * @author jbparoissien
 */
public interface ISousJeuDAO extends IDAO<SousJeu> {

    /**
     *
     * @param doi
     * @return
     */
    Optional<SousJeu> getByCode(String doi);

    /**
     *
     * @param code_jeu
     * @param doi
     * @return
     */
    Optional<SousJeu> getByKey(String code_jeu, String doi);
}
