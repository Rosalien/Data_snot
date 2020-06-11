/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.gestionnairejeu;

import java.util.List;
import java.util.Optional;
import org.inra.ecoinfo.IDAO;
import java.time.LocalDate;

/**
 *
 * @author jbparoissien
 */
public interface IGestionnaireJeuDAO extends IDAO<GestionnaireJeu> {

    /**
     *
     * @param email
     * @return
     */
    Optional<GestionnaireJeu> getByCode(String email);

    /**
     *
     * @param code_jeu
     * @param email
     * @param responsabilite
     * @param debutPeriode
     * @return
     */
    Optional<GestionnaireJeu> getByKey(String code_jeu, String email, String responsabilite, LocalDate debutPeriode);
}
