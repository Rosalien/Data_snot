/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.gestionnairejeu;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;
import java.time.LocalDate;
import javax.persistence.criteria.Join;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.jeu.Jeu_;
import org.inra.ecoinfo.refdata.valeurqualitative.ValeurQualitative;
import org.inra.ecoinfo.refdata.valeurqualitative.ValeurQualitative_;

/**
 *
 * @author jbparoissien
 */
public class JPAGestionnaireJeuDAO extends AbstractJPADAO<GestionnaireJeu> implements IGestionnaireJeuDAO {

    private static final Logger LOGGER = Logger.getLogger(JPAGestionnaireJeuDAO.class);

    /**
     *
     * @param code_jeu
     * @param email
     * @param responsabilite
     * @param debutPeriode
     * @return
     */
    @Override
    public Optional<GestionnaireJeu> getByKey(String code_jeu, String email, String responsabilite, LocalDate debutPeriode) {
        CriteriaQuery<GestionnaireJeu> query = builder.createQuery(GestionnaireJeu.class);
        Root<GestionnaireJeu> gestionnairejeu = query.from(GestionnaireJeu.class);
        Join<GestionnaireJeu, Jeu> jeu = gestionnairejeu.join(GestionnaireJeu_.jeu);
        Join<GestionnaireJeu, ValeurQualitative> valeurqualitative = gestionnairejeu.join(GestionnaireJeu_.responsabilite);
        query
                .select(gestionnairejeu)
                .where(builder.and(
                        builder.equal(jeu.get(Jeu_.code_jeu), code_jeu),
                        builder.equal(gestionnairejeu.get(GestionnaireJeu_.email), email),
                        builder.equal(valeurqualitative.get(ValeurQualitative_.valeur), responsabilite),
                        builder.equal(gestionnairejeu.get(GestionnaireJeu_.dateDebut), debutPeriode)
                ));
        return getOptional(query);
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public Optional<GestionnaireJeu> getByCode(String email) {
        CriteriaQuery<GestionnaireJeu> query = builder.createQuery(GestionnaireJeu.class);
        Root<GestionnaireJeu> gestionnairejeu = query.from(GestionnaireJeu.class);
        query
                .select(gestionnairejeu)
                .where(builder.equal(gestionnairejeu.get(GestionnaireJeu_.email), email));
        return getOptional(query);
    }

}
