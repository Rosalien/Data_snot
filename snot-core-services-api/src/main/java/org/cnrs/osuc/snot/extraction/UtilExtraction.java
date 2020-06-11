package org.cnrs.osuc.snot.extraction;

/**
 * @author sophie
 * 
 */
public class UtilExtraction {
//
    /**
     *
     */
    public final static String      MSG_INTERVAL_ERROR                       = "Format de date invalide. Le format doit être : %s";

//    /**
//     *
//     */
//    public static final String      RYTHME_INVALID                           = "La fréquence \"%s\" n'est pas valide";
//
    /**
     *
     */
    public final static String      MSG_EXTRACTION_ABORTED                   = "Echec de l'extraction des données";
//
//    /**
//     *
//     */
//    public static final String      L_EXTRACTION_N_A_RETOURNÉ_AUCUN_RÉSULTAT = "L'extraction n'a retourné aucun résultat";
//
//    /*
//     * RYTHME_INVALID = "The frequency  \"%s\" is not valid"; MSG_EXTRACTION_ABORTED =
//     * "Failure of data extraction";
//     */
//
//    /**
//     *
//    /*
//     * RYTHME_INVALID = "The frequency  \"%s\" is not valid"; MSG_EXTRACTION_ABORTED =
//     * "Failure of data extraction";
//     */
//
//    /**
//     *
//    /*
//     * RYTHME_INVALID = "The frequency  \"%s\" is not valid"; MSG_EXTRACTION_ABORTED =
//     * "Failure of data extraction";
//     */
//
//    /**
//     *
//    /*
//     * RYTHME_INVALID = "The frequency  \"%s\" is not valid"; MSG_EXTRACTION_ABORTED =
//     * "Failure of data extraction";
//     */
//
//    /**
//     *
//     */
//    
//
//    public static final String      BAD_PARAMETERS                           = "Les paramètres en arguments sont erronés.";
//    private static final String     RESOURCE_PATH                            = "%s/%s/%s";
//
//    private static final String     RESOURCE_PATH_VARIABLE                   = "%s/%s/%s/%s";
//
//    /**
//     *
//     * @param securityContext
//     * @param privilegePropertyStart
//     * @param privilegePropertyEnd
//     * @param path
//     */
//    public static void utilSetetPrivilegePropertiesForRoleExtractionAndTypeDataset(ISecurityContext securityContext, PrivilegeProperty privilegePropertyStart, PrivilegeProperty privilegePropertyEnd, String path) {
//        List<PrivilegeProperty> privilegeProperties = securityContext.getMatchingPrivilegesProperties(path, Arrays.asList(new String[]{Role.ROLE_EXTRACTION}), ISecurityDatasetManager.PRIVILEGE_TYPE_DATASET);
//        for (PrivilegeProperty privilegeProperty : privilegeProperties) {
//            if (null != privilegeProperty.getNom()) {
//                switch (privilegeProperty.getNom()) {
//                    case PrivilegeProperty.PROPERTY_START:
//                        privilegePropertyStart = privilegeProperty;
//                        break;
//                    case PrivilegeProperty.PROPERTY_END:
//                        privilegePropertyEnd = privilegeProperty;
//                        break;
//                }
//            }
//        }
//    }
//
//    /*
//     * private ValeurMeteoSol<?> _valeursMeteoSolAutorisee(ValeurMeteoSol<?> valeur, String
//     * frequence) { Theme theme = Outils.chercherTheme(profil.getVersionFile()); DataType dataType =
//     * Outils.chercherDataType(profil.getVersionFile()); String codeTheme = theme.getCode(); String
//     * codeDataType = dataType.getCode(); String codeVariable = valeur.getVariable().getCode();
//     * 
//     * String sitePath = profil.getZoneEtude().getPath();
//     * 
//     * String path = null;
//     * 
//     * path = new StringBuffer( sitePath.concat(Utils.SEPARATOR_URL). concat(codeTheme).
//     * concat(Utils.SEPARATOR_URL). concat(codeDataType). concat(Utils.SEPARATOR_URL).
//     * concat(codeVariable)). toString();
//     * 
//     * PrivilegeProperty privilegePropertyStart = null; PrivilegeProperty privilegePropertyEnd =
//     * null; setPrivilegePropertiesForRoleExtractionAndTypeDataset(privilegePropertyStart,
//     * privilegePropertyEnd, path); if (hasRoleforSiteThemeDatatype(sitePath, codeTheme,
//     * codeDataType, Role.ROLE_EXTRACTION) || hasRoleforSiteThemeDatatypeVariable(sitePath,
//     * codeTheme, codeDataType, codeVariable, Role.ROLE_EXTRACTION)) { try { Calendar dateDebut =
//     * DateUtil.calendarLocale(); Calendar dateFin = DateUtil.calendarLocale();
//     * dateDebut.setTime(DateUtil.getSimpleDateFormatDateLocale().parse(privilegePropertyStart ==
//     * null ? "01/01/1000" : privilegePropertyStart.getValue()));
//     * dateFin.setTime(privilegePropertyEnd == null ? new Date() :
//     * DateUtil.getSimpleDateFormatDateLocale().parse(privilegePropertyEnd.getValue()));
//     * 
//     * if (dateDebut.after(profil.getDate()) || dateFin.before(profil.getDate()))
//     * 
//     * return valeur; } catch (Exception e) {
//     * 
//     * } } return valeur; }
//     */
//
//    private ISecurityContext        securityContext;
//
//    /**
//     *
//     */
//    protected IPrivilegePropertyDAO privilegePropertyDAO;
//
//    /**
//     * @return the privilegePropertyDAO
//     */
//    public IPrivilegePropertyDAO getPrivilegePropertyDAO() {
//        return this.privilegePropertyDAO;
//    }
//
//    /**
//     * @param privilegePropertyDAO
//     *            the privilegePropertyDAO to set
//     */
//    public void setPrivilegePropertyDAO(IPrivilegePropertyDAO privilegePropertyDAO) {
//        this.privilegePropertyDAO = privilegePropertyDAO;
//    }
//
//    /**
//     * @return the securityContext
//     */
//    public ISecurityContext getSecurityContext() {
//        return this.securityContext;
//    }
//
//    /**
//     * @param securityContext
//     *            the securityContext to set
//     */
//    public void setSecurityContext(ISecurityContext securityContext) {
//        this.securityContext = securityContext;
//    }
//
//    /**
//     *
//     * @param site
//     * @param theme
//     * @param datatype
//     * @param role
//     * @return
//     */
//    public boolean hasRoleforSiteThemeDatatype(String site, String theme, String datatype, String role) {
//        try {
//            return this.securityContext.hasPrivilege(String.format(UtilExtraction.RESOURCE_PATH, site, theme, datatype), role, Role.ROLE_EXTRACTION) != null;
//        }catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     *
//     * @param site
//     * @param theme
//     * @param datatype
//     * @param variable
//     * @param role
//     * @return
//     */
//    public boolean hasRoleforSiteThemeDatatypeVariable(String site, String theme, String datatype, String variable, String role) {
//        try {
//            return this.securityContext.hasPrivilege(String.format(
//                    UtilExtraction.RESOURCE_PATH_VARIABLE, site, theme, datatype, variable), role, Role.ROLE_EXTRACTION) != null;
//        }catch (SecurityException e) {
//            return false;
//        }
//    }
//
//    /**
//     *
//     * @param privilegePropertyStart
//     * @param privilegePropertyEnd
//     * @param path
//     */
//    public void setPrivilegePropertiesForRoleExtractionAndTypeDataset(PrivilegeProperty privilegePropertyStart, PrivilegeProperty privilegePropertyEnd, String path) {
//        List<PrivilegeProperty> privilegeProperties = this.securityContext.getMatchingPrivilegesProperties(path, Arrays.asList(new String[]{Role.ROLE_EXTRACTION}), ISecurityDatasetManager.PRIVILEGE_TYPE_DATASET);
//        for (PrivilegeProperty privilegeProperty : privilegeProperties) {
//            if (null != privilegeProperty.getNom()) {
//                switch (privilegeProperty.getNom()) {
//                    case PrivilegeProperty.PROPERTY_START:
//                        privilegePropertyStart = privilegeProperty;
//                        break;
//                    case PrivilegeProperty.PROPERTY_END:
//                        privilegePropertyEnd = privilegeProperty;
//                        break;
//                }
//            }
//        }
//    }

}
