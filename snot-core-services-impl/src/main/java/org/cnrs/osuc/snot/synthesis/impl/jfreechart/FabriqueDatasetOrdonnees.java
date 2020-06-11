package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.cnrs.osuc.snot.synthesis.IGraphPresenceAbsence;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author ptcherniati
 */
public class FabriqueDatasetOrdonnees {
    private static final int NONE_VALUE = 3;
    private static final int ALL_VALUES = 1;
    private static final int SOME_VALUES = 2;

    /**
     *
     */
    protected static final int ABSENT = -9_999;

    /**
     *
     * @param synthesisVariables
     * @param isSemiHoraire
     * @param ordonnees
     * @return
     */
    public List<MyTimeSeries> createTimeSeriesRepartitionVariable(List<GenericSynthesisValue> synthesisVariables, boolean isSemiHoraire, List<AbstractOrdonnee> ordonnees) {
        Map<String, Map<LocalDateTime, List<Float>>> donneesTS = this.construireDonneesTimesSeries(synthesisVariables);
        return this.construireTimeSerieRepartition(donneesTS, isSemiHoraire, ordonnees);
    }

    /**
     *
     * @param synthesisValues
     * @return
     */
    public List<AbstractOrdonnee> ordonnees(List<GenericSynthesisValue> synthesisValues) {
        List<AbstractOrdonnee> ordonnees = null;
        if (synthesisValues != null && !synthesisValues.isEmpty()) {
            IGraphPresenceAbsence uneValeur = (IGraphPresenceAbsence) synthesisValues.get(0);
            if (uneValeur.getOrdonnee().isNumeriqueNatif()) {
                ordonnees = this.traiterCasNumerique(synthesisValues);
            } else {
                ordonnees = this.traiterCasAlphanumerique(synthesisValues);
            }
        }
        return ordonnees;
    }

    private List<MyTimeSeries> ranger(TimeSeries profondeurTous, TimeSeries profondeurAucun, TimeSeries profondeurPartiel) {
        List<MyTimeSeries> lesTs = new ArrayList<>();
        if (!profondeurTous.isEmpty()) {
            lesTs.add(new MyTimeSeries(profondeurTous, new Color(0, 102, 0)));
        }
        if (!profondeurAucun.isEmpty()) {
            lesTs.add(new MyTimeSeries(profondeurAucun, Color.red));
        }
        if (!profondeurPartiel.isEmpty()) {
            lesTs.add(new MyTimeSeries(profondeurPartiel, Color.orange));
        }
        return lesTs;
    }

    private List<AbstractOrdonnee> traiterCasAlphanumerique(List<GenericSynthesisValue> synthesisValues) {
        Map<String, Boolean> lesOrdonnees = new HashMap<>();
        List<Traitement> ordonnees = new ArrayList<>();
        Boolean vrai = true;
        for (GenericSynthesisValue value : synthesisValues) {
            IGraphPresenceAbsence uneValeur = (IGraphPresenceAbsence) value;
            String ordonnee = uneValeur.getOrdonnee().getValeurAlphanumerique();
            lesOrdonnees.put(ordonnee, vrai);
        }
        for (Entry<String, Boolean> entry : lesOrdonnees.entrySet()) {
            ordonnees.add(new Traitement(0, entry.getKey()));
        }
        Collections.sort(ordonnees);
        Integer ecartOrdonnee = 5;
        for (Traitement traitement : ordonnees) {
            traitement.setOrdonnee(ecartOrdonnee);
            ecartOrdonnee += 5;
        }
        List<AbstractOrdonnee> reponse = new ArrayList<>(ordonnees);
        return reponse;
    }

    private List<AbstractOrdonnee> traiterCasNumerique(List<GenericSynthesisValue> synthesisValues) {
        Map<Integer, Boolean> lesOrdonnees = new HashMap<>();
        List<Profondeur> ordonnees = new ArrayList<>();
        Boolean vrai = true;
        for (GenericSynthesisValue value : synthesisValues) {
            IGraphPresenceAbsence uneValeur = (IGraphPresenceAbsence) value;
            int ordonnee = uneValeur.getOrdonnee().getValeurNumerique();
            Integer uneOrdonnee = ordonnee;
            lesOrdonnees.put(uneOrdonnee, vrai);
        }
        for (Entry<Integer, Boolean> entry : lesOrdonnees.entrySet()) {
            ordonnees.add(new Profondeur(entry.getKey(), 0));
        }
        Collections.sort(ordonnees);
        Integer ecartOrdonnee = 5;
        for (Profondeur ordonnee : ordonnees) {
            ordonnee.setProfondeurFictive(ecartOrdonnee);
            ecartOrdonnee += 5;
        }
        List<AbstractOrdonnee> reponse = new ArrayList<>(ordonnees);
        return reponse;
    }

    /**
     *
     * @param donneesTS
     * @param semiHoraire
     * @param ordonnees
     * @return
     */
    protected List<MyTimeSeries> construireTimeSerieRepartition(Map<String, Map<LocalDateTime, List<Float>>> donneesTS, boolean semiHoraire, List<AbstractOrdonnee> ordonnees) {
        List<MyTimeSeries> lesTs = new LinkedList();
        for (Entry<String, Map<LocalDateTime, List<Float>>> entry : donneesTS.entrySet()) {
            String ordonnee = entry.getKey();
            String titre = ordonnee;
            Float pFictive = this.chercherCorrespondant(ordonnee, ordonnees);
            TimeSeries profondeurTous = new TimeSeries(titre);
            TimeSeries profondeurAucun = new TimeSeries(titre);
            TimeSeries profondeurPartiel = new TimeSeries(titre);
            for (Entry<LocalDateTime, List<Float>> entry2 : entry.getValue().entrySet()) {
                int etat = this.touteLesRepetitions(entry2.getValue());
                Date date = Date.from(entry2.getKey().toInstant(ZoneOffset.UTC));
                if (etat == ALL_VALUES && !semiHoraire) {
                    profondeurTous.addOrUpdate(new Day(date), pFictive);
                }
                if (etat == ALL_VALUES && semiHoraire) {
                    profondeurTous.addOrUpdate(new Minute(date), pFictive);
                }
                if (etat == SOME_VALUES && !semiHoraire) {
                    profondeurPartiel.addOrUpdate(new Day(date), pFictive);
                }
                if (etat == SOME_VALUES && semiHoraire) {
                    profondeurPartiel.addOrUpdate(new Minute(date), pFictive);
                }
                if (etat == NONE_VALUE && !semiHoraire) {
                    profondeurAucun.addOrUpdate(new Day(date), pFictive);
                }
                if (etat == NONE_VALUE && semiHoraire) {
                    profondeurAucun.addOrUpdate(new Minute(date), pFictive);
                }
            }
            lesTs.addAll(this.ranger(profondeurTous, profondeurAucun, profondeurPartiel));
        }
        return lesTs;
    }

    /**
     *
     * @param ordonneeReelle
     * @param ordonnees
     * @return
     */
    protected Float chercherCorrespondant(String ordonneeReelle, List<AbstractOrdonnee> ordonnees) {
        Float reponse = (float) 0.;
        for (AbstractOrdonnee uneOrdonnee : ordonnees) {
            if (uneOrdonnee.uneOrdonneeVaut(ordonneeReelle)) {
                reponse = (float) uneOrdonnee.getSonOrdonnee();
                break;
            }
        }
        return reponse;
    }

    /**
     *
     * @param synthesisValues
     * @return
     */
    protected Map<String, Map<LocalDateTime, List<Float>>> construireDonneesTimesSeries(List<GenericSynthesisValue> synthesisValues) {
        Map<String, Map<LocalDateTime, List<Float>>> donneesTS = new HashMap<>();
        for (GenericSynthesisValue value : synthesisValues) {
            IGraphPresenceAbsence uneValeur = (IGraphPresenceAbsence) value;
            String sonOrdonnee = uneValeur.getOrdonnee().toString();
            if (donneesTS.get(sonOrdonnee) == null) {
                Map<LocalDateTime, List<Float>> timeSeries = new HashMap<>();
                List<Float> listeValeur = new ArrayList<>();
                listeValeur.add(uneValeur.getValueFloat());
                timeSeries.put(uneValeur.getDate(), listeValeur);
                donneesTS.put(sonOrdonnee, timeSeries);
            } else {
                Map<LocalDateTime, List<Float>> timeSeries = donneesTS.get(sonOrdonnee);
                List<Float> listeValeur = timeSeries.get(uneValeur.getDate());
                if (listeValeur == null) {
                    listeValeur = new ArrayList<>();
                    timeSeries.put(uneValeur.getDate(), listeValeur);
                }
                listeValeur.add(uneValeur.getValueFloat());
            }
        }
        return donneesTS;
    }

    /**
     *
     * @param value
     * @return
     */
    protected int touteLesRepetitions(List<Float> value) {
        int total = value.size();
        int reponse = SOME_VALUES;
        int present = 0;
        int absent = 0;
        for (Float v : value) {
            if (v.intValue() == ABSENT) {
                absent += 1;
            } else {
                present += 1;
            }
        }
        if (present == total) {
            reponse = ALL_VALUES;
        } else if (absent == total) {
            reponse = NONE_VALUE;
        }
        return reponse;
    }
}
