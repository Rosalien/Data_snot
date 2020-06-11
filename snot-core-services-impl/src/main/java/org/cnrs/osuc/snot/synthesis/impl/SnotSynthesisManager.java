package org.cnrs.osuc.snot.synthesis.impl;

import org.inra.ecoinfo.synthesis.impl.LocalSynthesisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class SnotSynthesisManager extends LocalSynthesisManager {

    private static final String PATTERN_STRING_FORMAT_SYNTHESISVALUE_CLASS = "%s.%s.SynthesisValue";
    private static final String METHOD_SETVALUEFLOAT = "setValueFloat";
    private static final String METHOD_SETVALUEQUALITATIVE = "setValueString";
    private static final String METHOD_SETISMEAN = "setIsMean";
    private static final String METHOD_SETDATE = "setDate";
    private static final String METHOD_SETVARIABLE = "setVariable";
    private static final String METHOD_SETSITE = "setSite";
    private static final String METHOD_SETPROFONDEUR = "setProfondeur";
    private static final String METHOD_SETREPETITION = "setRepetition";
//    private static final String METHOD_SETCHAMBRE = "setChambre";
    private static final String METHOD_SETTraitement = "setTraitement";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     *
     */
    protected boolean isSemiHoraire;

//    @Override
//    public void processSynthesisVariablesAndValues(Site availableSite, String datatypeName, Variable availableVariable, String prefix) {
//        final MetaSynthesisDatatype metadataSynthesisDatatype = this.synthesisRegister.retrieveMetadataSynthesisDatatype(datatypeName);
//        if (!(metadataSynthesisDatatype instanceof SnotMetaSynthesisDatatype)) {
//           super.processSynthesisVariablesAndValues(availableSite, datatypeName, availableVariable, prefix);
//           return;
//        }
//        SnotMetaSynthesisDatatype meta = (SnotMetaSynthesisDatatype) metadataSynthesisDatatype;
//        if (meta.isSynthesisParProfondeur()) {
//            this.processSynthesisVariableAndValuesAndProfondeur(availableSite, datatypeName, availableVariable, prefix);
//        } else {
//            try {
//                this.processSynthesisVariableAndValuesSansProfondeur(availableSite, datatypeName, availableVariable, prefix);
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
//                throw new PersistenceException(ex);
//            }
//        }
//        this.synthesisDAO.flush();
//    }

//    private void processSynthesisVariableAndValuesSansProfondeur(Site availableSite, String datatypeName, Variable availableVariable, String prefix) throws PersistenceException, IllegalArgumentException, ClassNotFoundException,
//                                                                                                                                                            InstantiationException, IllegalAccessException, InvocationTargetException {
//        this.LOGGER.info("****Je suis dan sprocessSynthesisVariableAndValuesSansProfondeur de SnotSynthesisManager");
//        this.LOGGER.info("****et j'ai reçu : " + availableSite.getCode() + " ; " + datatypeName + " ; " + availableVariable.getCode() + " ; " + prefix);
//        this.LOGGER.info("****je vais appeler synthesisDAO.getValeursAndDateBySiteAndVariable ");
//        List<Object[]> valeursDates = this.synthesisDAO.getValeursAndDateBySiteAndVariable(availableSite, availableVariable, datatypeName);
//        this.LOGGER.info("****synthesisDAO.getValeursAndDateBySiteAndVariable m'a retourné " + valeursDates.size() + " items");
//        Map<Date, Float> mapValeurDate = this.construireMapValeurDateSansProfondeur(valeursDates, datatypeName);
//        for (Date date : mapValeurDate.keySet()) {
//            Float valeur = mapValeurDate.get(date);
//            this.LOGGER.info("****je vais ranger la valeur " + valeur.doubleValue() + " pour la date " + date);
//            Object object = this.buildSynthesisValueSansProfondeur(availableSite, prefix, availableVariable, date, valeur);
//            this.synthesisDAO.saveOrUpdateGeneric(object);
//        }
//
//    }

////    private Map<Date, Float> construireMapValeurDateSansProfondeur(List<Object[]> valeursDates, String datatypeName) {
////        Map<Date, Float> maMap;
////        SnotMetaSynthesisDatatype meta = (SnotMetaSynthesisDatatype) this.synthesisRegister.retrieveMetadataSynthesisDatatype(datatypeName);
////        if (meta.isSemiHoraire()) {
////            this.LOGGER.info("****Je suis dans construireMapValeurDateSansProfondeur de SnotSynthesisManager et j'ai reçu " + valeursDates.size() + " données à ranger pour le type de données " + datatypeName);
////            maMap = this.constructionMethode1SansProfondeur(valeursDates);
////        } else {
////            maMap = this.constructionMethode2SansProfondeur(valeursDates);
////        }
////        return maMap;
////    }
////
////    private Map<Date, Float> constructionMethode1SansProfondeur(List<Object[]> valeursDates) {
////        this.LOGGER.info("****Je suis dans constructionMethode1SansProfondeur de SnotSynthesisManager et j'ai reçu " + valeursDates.size() + " données à ranger");
////        Map<Date, Float> mapValeurDate = new HashMap<>();
////
////        for (Object[] valeurDate : valeursDates) {
////            Date date = (Date) valeurDate[0];
////            Time heure = (Time) valeurDate[2];
////            Float valeur = (Float) valeurDate[1];
////            Date dateEtHeure = DateUtil.concatener(date, heure);
////            mapValeurDate.put(dateEtHeure, valeur);
////        }
////        return mapValeurDate;
////    }
////
////    private Map<Date, Float> constructionMethode2SansProfondeur(List<Object[]> valeursDates) {
////        Map<Date, Float> mapValeurDate = new HashMap<>();
////
////        for (Object[] valeurDate : valeursDates) {
////            Date date = (Date) valeurDate[0];
////            Float valeur = (Float) valeurDate[1];
////            mapValeurDate.put(date, valeur);
////        }
////        return mapValeurDate;
////    }
////
////    private Object buildSynthesisValueSansProfondeur(Site availableSite, String prefix, Variable availableVariable, Date date, Float value) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
////                                                                                                                                                   InvocationTargetException, PersistenceException {
////        Class<?> synthesisValueClass = Class.forName(String.format(PATTERN_STRING_FORMAT_SYNTHESISVALUE_CLASS, this.synthesisRegister.getBasePackage(), prefix));
////        Object synthesisValue = synthesisValueClass.newInstance();
////        this.setProperty(METHOD_SETSITE, availableSite.getPath(), synthesisValueClass, synthesisValue);
////        this.setProperty(METHOD_SETDATE, date, synthesisValueClass, synthesisValue);
////        this.setProperty(METHOD_SETVARIABLE, availableVariable.getNom(), synthesisValueClass, synthesisValue);
////        this.setProperty(METHOD_SETVALUEFLOAT, value, synthesisValueClass, synthesisValue);
////        this.setProperty(METHOD_SETVALUEQUALITATIVE, "", synthesisValueClass, synthesisValue);
////        Boolean mean = false;
////        this.setProperty(METHOD_SETISMEAN, mean, synthesisValueClass, synthesisValue);
////
////        return synthesisValue;
////
////    }
//
//    private void processSynthesisVariablePourChambreAFlux(Site availableSite, String datatypeName, Variable availableVariable, String prefix, List<Object[]> valeursDates) throws PersistenceException {
//        SnotMetaSynthesisDatatype meta = (SnotMetaSynthesisDatatype) this.synthesisRegister.retrieveMetadataSynthesisDatatype(datatypeName);
//        if (meta.isSemiHoraire()) {
//            this.processSynthesisVariablePourChambreAFluxInfraJ(availableSite, datatypeName, availableVariable, prefix, valeursDates);
//        } else {
//            this.processSynthesisVariablePourChambreAFluxNotInfraJ(availableSite, datatypeName, availableVariable, prefix, valeursDates);
//        }
//    }
//
//    private void processSynthesisVariablePourChambreAFluxNotInfraJ(Site availableSite, String datatypeName, Variable availableVariable, String prefix, List<Object[]> valeursDates) throws PersistenceException {
//        for (Object[] valeurDate : valeursDates) {
//            Date date = (Date) valeurDate[0];
//            Float laValeur = (Float) valeurDate[1];
//            String chambre = (String) valeurDate[2];
//            Traitement traitement = (Traitement) valeurDate[3];
//            String nomTraitement = traitement.getCode();
//            Object object = this.buildSynthesisValueChambre(availableSite, prefix, availableVariable, date, laValeur, chambre, nomTraitement);
//            this.synthesisDAO.saveOrUpdateGeneric(object);
//        }
//    }
//
//    private Object buildSynthesisValueChambre(Site availableSite, String prefix, Variable availableVariable, Date date, Float laValeur, String chambre, String traitement) throws PersistenceException {
//        try {
//            Class<?> synthesisValueClass = Class.forName(String.format(PATTERN_STRING_FORMAT_SYNTHESISVALUE_CLASS, this.synthesisRegister.getBasePackage(), prefix));
//            Object synthesisValue = synthesisValueClass.newInstance();
//            this.setProperty(METHOD_SETSITE, availableSite.getPath(), synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETDATE, date, synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETVARIABLE, availableVariable.getNom(), synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETVALUEFLOAT, laValeur.floatValue(), synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETVALUEQUALITATIVE, "", synthesisValueClass, synthesisValue);
//            Boolean mean = false;
//            this.setProperty(METHOD_SETISMEAN, mean, synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETCHAMBRE, chambre, synthesisValueClass, synthesisValue);
//            this.setProperty(METHOD_SETTraitement, traitement, synthesisValueClass, synthesisValue);
//            return synthesisValue;
//        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
//            throw new PersistenceException(ex);
//        }
//
//    }
//
//    private void processSynthesisVariablePourChambreAFluxInfraJ(Site availableSite, String datatypeName, Variable availableVariable, String prefix, List<Object[]> valeursDates) throws PersistenceException {
//        for (Object[] valeurDate : valeursDates) {
//            Date date = (Date) valeurDate[0];
//            Float laValeur = (Float) valeurDate[1];
//            String chambre = (String) valeurDate[2];
//            Date heure = (Date) valeurDate[3];
//            Traitement traitement = (Traitement) valeurDate[4];
//            String nomTraitement = traitement.getCode();
//            Date dateHeure = DateUtil.concatener(date, heure);
//            Object object = this.buildSynthesisValueChambre(availableSite, prefix, availableVariable, dateHeure, laValeur, chambre, nomTraitement);
//            this.synthesisDAO.saveOrUpdateGeneric(object);
//        }
//    }
//
//    private void processSynthesisVariableAndValuesAndProfondeurJ(Site availableSite, String datatypeName, Variable availableVariable, String prefix, List<Object[]> valeursDates) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, PersistenceException {
//        for (Object[] valeurDate : valeursDates) {
//            Date date = (Date) valeurDate[0];
//            Float laValeur = (Float) valeurDate[1];
//            Integer profondeur = (Integer) valeurDate[2];
//            Integer repetition = null;
//            if (valeurDate.length == 3) {
//                repetition = 1;
//            } else {
//                repetition = (Integer) valeurDate[3];
//            }
//            ValeurProfondeur vp = new ValeurProfondeur(laValeur, profondeur, repetition);
//            Object object = this.buildSynthesisValueProfondeur(availableSite, prefix, availableVariable, date, vp);
//            this.synthesisDAO.saveOrUpdateGeneric(object);
//        }
//
//    }
//
//    private void processSynthesisVariableAndValuesAndProfondeurSH(Site availableSite, String datatypeName, Variable availableVariable, String prefix, List<Object[]> valeursDates) throws PersistenceException {
//        for (Object[] valeurDate : valeursDates) {
//            try {
//                Date date = (Date) valeurDate[0];
//                Float laValeur = (Float) valeurDate[1];
//                Integer profondeur = (Integer) valeurDate[2];
//                Integer repetition = (Integer) valeurDate[3];
//                Time heure = (Time) valeurDate[4];
//                Date dateHeure = DateUtil.concatener(date, heure);
//                ValeurProfondeur vp = new ValeurProfondeur(laValeur, profondeur, repetition);
//                Object object = this.buildSynthesisValueProfondeur(availableSite, prefix, availableVariable, dateHeure, vp);
//                this.synthesisDAO.saveOrUpdateGeneric(object);
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
//                throw new PersistenceConstraintException(ex);
//            }
//        }
//    }
//
//    private Object buildSynthesisValueProfondeur(Site availableSite, String prefix, Variable availableVariable, Date date, ValeurProfondeur valeur) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        Class<?> synthesisValueClass = Class.forName(String.format(PATTERN_STRING_FORMAT_SYNTHESISVALUE_CLASS, this.synthesisRegister.getBasePackage(), prefix));
//        Object synthesisValue = synthesisValueClass.newInstance();
//        this.setProperty(METHOD_SETSITE, availableSite.getPath(), synthesisValueClass, synthesisValue);
//        this.setProperty(METHOD_SETDATE, date, synthesisValueClass, synthesisValue);
//        this.setProperty(METHOD_SETVARIABLE, availableVariable.getNom(), synthesisValueClass, synthesisValue);
//        Float valeurMesuree = valeur.getValeur();
//        Integer profondeur = valeur.getProfondeur();
//        Integer repetition = valeur.getRepetition();
//        this.setProperty(METHOD_SETVALUEFLOAT, valeurMesuree, synthesisValueClass, synthesisValue);
//        this.setProperty(METHOD_SETPROFONDEUR, profondeur, synthesisValueClass, synthesisValue);
//        this.setProperty(METHOD_SETREPETITION, repetition, synthesisValueClass, synthesisValue);
//        this.setProperty(METHOD_SETVALUEQUALITATIVE, "", synthesisValueClass, synthesisValue);
//        Boolean mean = false;
//        this.setProperty(METHOD_SETISMEAN, mean, synthesisValueClass, synthesisValue);
//        return synthesisValue;
//
//    }
//
//    /**
//     *
//     * @param availableSite
//     * @param datatypeName
//     * @param availableVariable
//     * @param prefix
//     * @throws PersistenceException
//     */
//    protected void processSynthesisVariableAndValuesAndProfondeur(Site availableSite, String datatypeName, Variable availableVariable, String prefix) throws PersistenceException {
//        List<Object[]> valeursDates = this.synthesisDAO.getValeursAndDateBySiteAndVariable(availableSite, availableVariable, datatypeName);
//        SnotMetaSynthesisDatatype meta = (SnotMetaSynthesisDatatype) this.synthesisRegister.retrieveMetadataSynthesisDatatype(datatypeName);
//        if (meta.isChambreAFlux()) {
//            this.processSynthesisVariablePourChambreAFlux(availableSite, datatypeName, availableVariable, prefix, valeursDates);
//        } else {
//            if (meta.isSemiHoraire()) {
//                this.processSynthesisVariableAndValuesAndProfondeurSH(availableSite, datatypeName, availableVariable, prefix, valeursDates);
//            } else {
//                try {
//                    this.processSynthesisVariableAndValuesAndProfondeurJ(availableSite, datatypeName, availableVariable, prefix, valeursDates);
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
//                    throw new PersistenceException(ex);
//                }
//            }
//        }
//        this.synthesisDAO.flush();
//    }

}
