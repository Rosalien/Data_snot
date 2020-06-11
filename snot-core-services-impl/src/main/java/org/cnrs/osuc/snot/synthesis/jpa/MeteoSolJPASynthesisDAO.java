package org.cnrs.osuc.snot.synthesis.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import org.inra.ecoinfo.refdata.site.Site;
import org.inra.ecoinfo.refdata.variable.Variable;
import org.inra.ecoinfo.synthesis.jpa.JPASynthesisDAO;

/**
 *
 * @author ptcherniati
 */
public class MeteoSolJPASynthesisDAO extends JPASynthesisDAO {

    private void chercherLesSites(String queryString, List<Site> lesSites) {
        Query query = this.entityManager.createQuery(queryString);
        List<Site> sites = query.getResultList();
        mettreAJourListeDesSites(lesSites, sites);
    }

    private void mettreAJourListeDesSites(final List<Site> lesSites, final List<Site> sites) {
        sites.stream()
                .filter(s-> !estdejaLa(lesSites, s.getCode()))
                .forEach(lesSites::add);
    }

    private boolean estdejaLa(List<Site> lesSites, String code) {
        return lesSites.stream().anyMatch(site->site.getCode().equals(code));
    }

    private void chercherLesVariables(String queryString, Site site, List<Variable> lesVariables) {
        String queryStringComplet = String.format(queryString, site.getId().toString());
        Query query = this.entityManager.createNativeQuery(queryStringComplet, Variable.RESULTSET_MAPPING_JPA);
        @SuppressWarnings("unchecked")
                List<Variable> variables = query.getResultList();
        this.mettreAJourListeVariables(variables, lesVariables);
    }

    private void mettreAJourListeVariables(List<Variable> variables, List<Variable> lesVariables) {
        variables.stream()
                .filter(var-> !variablePresente(var.getCode(), lesVariables))
                .forEach(lesVariables::add);
    }

    private boolean variablePresente(String code, List<Variable> lesVariables) {
        return lesVariables.stream()
                .map(var->var.getCode())
                .anyMatch(sonCode->sonCode.equals(code));
    }

    @SuppressWarnings("unchecked")
    private LocalDateTime[] chercherLesLocalDateTimesMinEtMax(String queryString, Site site, LocalDateTime[] reponse) {
        Query query = this.entityManager.createQuery(queryString);
        query.setParameter("siteId", site.getId());
        List<Object[]> dates = query.getResultList();
        return  this.MettreAjourLocalDateTimes(dates, reponse);
    }

    private LocalDateTime[] MettreAjourLocalDateTimes(List<Object[]> dates, LocalDateTime[] reponse) {
        Object[] datesTrouvees = dates.get(0);
        LocalDateTime min = (LocalDateTime) datesTrouvees[0];
        LocalDateTime max = (LocalDateTime) datesTrouvees[1];
        reponse[0] = this.actualiserMin(min, reponse[0]);
        reponse[1] = this.actualiserMax(max, reponse[1]);
        return reponse;
    }

    private LocalDateTime actualiserMax(LocalDateTime max, LocalDateTime date) {
        return Optional.ofNullable(max)
                .filter(m->m.isAfter(date))
                .map(m->m)
                .orElse(date);
    }

    private LocalDateTime actualiserMin(LocalDateTime min, final LocalDateTime date) {
        return Optional.ofNullable(min)
                .filter(m->m.isBefore(date))
                .map(m->m)
                .orElse(date);
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> chercherValeurs(Site site, Variable variable, String question) {
        Query query = this.entityManager.createQuery(question);
        query.setParameter("siteId", site.getId());
        query.setParameter("variableId", variable.getId());
        return query.getResultList();

    }

}
