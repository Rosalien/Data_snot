/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.sousjeu;

import java.util.Optional;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.inra.ecoinfo.AbstractJPADAO;
import java.time.LocalDate;
import javax.persistence.criteria.Join;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.jeu.Jeu_;

/**
 *
 * @author jbparoissien
 */
public class JPASousJeuDAO extends AbstractJPADAO<SousJeu> implements ISousJeuDAO {

    private static final Logger LOGGER = Logger.getLogger(JPASousJeuDAO.class);

    /**
     *
     * @param code_jeu
     * @param doi
     * @return
     */
    @Override
    public Optional<SousJeu> getByKey(String code_jeu, String doi) {
        CriteriaQuery<SousJeu> query = builder.createQuery(SousJeu.class);
        Root<SousJeu> sousjeu = query.from(SousJeu.class);
        Join<SousJeu, Jeu> jeu = sousjeu.join(SousJeu_.jeu);
        query
                .select(sousjeu)
                .where(builder.and(
                        builder.equal(jeu.get(Jeu_.code_jeu), code_jeu),
                        builder.equal(sousjeu.get(SousJeu_.doi), doi)
                ));
        return getOptional(query);
    }

    /**
     *
     * @param doi
     * @return
     */
    @Override
    public Optional<SousJeu> getByCode(String doi) {
        CriteriaQuery<SousJeu> query = builder.createQuery(SousJeu.class);
        Root<SousJeu> sousjeu = query.from(SousJeu.class);
        query
                .select(sousjeu)
                .where(builder.equal(sousjeu.get(SousJeu_.doi), doi));
        return getOptional(query);
    }

}
