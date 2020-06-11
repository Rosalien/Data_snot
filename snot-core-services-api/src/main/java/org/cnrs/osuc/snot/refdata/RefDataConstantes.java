/**
 *
 */
package org.cnrs.osuc.snot.refdata;

/**
 * @author sophie
 *
 */
public class RefDataConstantes {

    /* Noms des tables */
    /**
     *
     */
    public static final String TRAITEMENT_TABLE_NAME = "traitement";

    /**
     *
     */
    public static final String METHODECALCUL_TABLE_NAME = "methode_calcul";

    /**
     *
     */
    public static final String INSTRUMENT_TABLE_NAME = "instrument";

    /**
     *
     */
    public static final String REFERENCE_TABLE_NAME = "reference";

    /**
     *
     */
    public static final String GESTIONNAIREJEU_TABLE_NAME = "gestionnairejeu";

    /**
     *
     */
    public static final String ROLE_TABLE_NAME = "role";

    /**
     *
     */
    public static final String STATION_ID = "station_id";

    /**
     *
     */
    public static final String STATION_TABLE_NAME = "station";

    /**
     *
     */
    public static final String COLUMN_CODEDATATYPE_STATION = "code_datatype";

    /**
     *
     */
    public static final String COLUMN_CODE_STATION = "code_station";

    /**
     *
     */
    public static final String COLUMN_DESCRIPTION_STATION = "description";

    /**
     *
     */
    public static final String COLUMN_NOM_STATION = "nom";

    /**
     *
     */
    public static final String COLUMN_STDTV_STATION = "code_stdtv";

    /**
     *
     */
    public static final String COLUMN_LAT_STATION = "latitude";

    /**
     *
     */
    public static final String COLUMN_LON_STATION = "longitude";

    /**
     *
     */
    public static final String COLUMN_COOR_STATION = "coordonnees";

    /**
     *
     */
    public static final String MCALCREFERENCE_TABLE_NAME = "methode_calcul_reference";

    /**
     *
     */
    public static final String INSTRUMENTREFERENCE_TABLE_NAME = "instrument_reference";

    /**
     *
     */
    public static final String STDVARIABLE_TABLE_NAME = "site_theme_datatype_variable";

    /**
     *
     */
    public static final String PERIODEAPPMETHODE_TABLE_NAME = "periode_application_methode";

    /**
     *
     */
    public static final String PERIODEROLEPERSONNE_TABLE_NAME = "periode_role_personne";

    /**
     *
     */
    public static final String PERIODEUTILINSTRUMENT_TABLE_NAME = "periode_utilisation_instrument";

    /**
     *
     */
    public static final String LSTVALINFO_TABLE_NAME = "liste_valeur_info";

    /**
     *
     */
    public static final String ITEMLST_TABLE_NAME = "item_liste";

    /**
     *
     */
    public static final String INFOCOMPL_TABLE_NAME = "information_complementaire";

    /**
     *
     */
    public static final String INFOCOMPLSTDTVAR_TABLE_NAME = "information_complementaire_stdt_variable";

    /**
     *
     */
    public static final String JEU_TABLE_NAME = "jeu";

    /**
     *
     */
    public static final String SOUSJEU_TABLE_NAME = "sousjeu";

    
    
    /* Id des tables */
    /**
     *
     */
    public static final String TRAITEMENT_ID = "trt_id";

    /**
     *
     */
    public static final String METHODECALCUL_ID = "mcalc_id";

    /**
     *
     */
    public static final String INSTRUMENT_ID = "instr_id";

    /**
     *
     */
    public static final String JEU_ID = "jeu_id";

    /**
     *
     */
    public static final String SOUSJEU_ID = "sousjeu_id";
    
    /**
     *
     */
    public static final String REFERENCE_ID = "ref_id";

    /**
     *
     */
    public static final String PERSONNE_ID = "pers_id";

    /**
     *
     */
    public static final String GESTIONNAIREJEU_ID = "gestjeu_id";

    /**
     *
     */
    public static final String MCALCREFERENCE_ID = "mcalcref_id";

    /**
     *
     */
    public static final String INSTRUMENTREFERENCE_ID = "instrref_id";

    /**
     *
     */
    public static final String STDTVARIABLE_ID = "stdtvar_id";

    /**
     *
     */
    public static final String PAPPMETHODE_ID = "pam_id";

    /**
     *
     */
    public static final String PERIODEROLEPERSONNE_ID = "prp_id";

    /**
     *
     */
    public static final String PUTILINSTRUMENT_ID = "pui_id";

    /**
     *
     */
    public static final String LSTVALINFO_ID = "lvalinf_id";

    /**
     *
     */
    public static final String ITEMLST_ID = "il_id";

    /**
     *
     */
    public static final String INFOCOMPL_ID = "infcplt_id";

    /**
     *
     */
    public static final String INFOCOMPLSTDTVAR_ID = "ifstdtvar_id";

    /* Noms des colonnes pour la table traitement */
    /**
     *
     */
    public static final String COLUMN_ZET_ID = "zet_id";

    /**
     *
     */
    public static final String COLUMN_CODE_TRT = "code";

    /**
     *
     */
    public static final String COLUMN_LIBELLE_TRT = "libelle";

    /**
     *
     */
    public static final String COLUMN_DESCRIPT_TRT = "description";

    /* Noms des colonnes pour la table methode_calcul */
    /**
     *
     */
    public static final String COLUMN_CODE_MCALC = "code";

    /**
     *
     */
    public static final String COLUMN_LIBELLE_MCALC = "libelle";

    /**
     *
     */
    public static final String COLUMN_DESCRIPT_MCALC = "description";

    /* Noms des colonnes pour la table instrument */
    /**
     *
     */
    public static final String COLUMN_CODE_INSTR = "code";

    /**
     *
     */
    public static final String COLUMN_LIBELLE_INSTR = "libelle";

    /**
     *
     */
    public static final String COLUMN_INFOSCAL_INSTR = "infos_calibration";

    /**
     *
     */
    public static final String COLUMN_FABRICANT_INSTR = "fabricant";

    /**
     *
     */
    public static final String COLUMN_DESCRIPT_INSTR = "description";

    /* Noms des colonnes pour la table perioderolepersonne (PRP) */
    /**
     *
     */
    public static final String COLUMN_DATEDEBUT_PRP = "date_debut";

    /**
     *
     */
    public static final String COLUMN_DATEFIN_PRP = "date_fin";

    /* Noms des colonnes pour la table personne */
    /**
     *
     */
    public static final String COLUMN_DATASETID_PERS = "datasetid";

    /**
     *
     */
    public static final String COLUMN_NOM_PERS = "nom";

    /**
     *
     */
    public static final String COLUMN_PRENOM_PERS = "prenom";

    /**
     *
     */
    public static final String COLUMN_EMAIL_PERS = "email";

    /**
     *
     */
    public static final String COLUMN_RESPONSABILITE_PERS = "responsabilite";

    /**
     *
     */
    public static final String COLUMN_DATEDEBUT_PERS = "date_debut";

    /**
     *
     */
    public static final String COLUMN_DATEFIN_PERS = "date_fin";

    /* Noms des colonnes pour la table jeu */
    /**
     *
     */
    public static final String COLUMN_CODESITE_JEU = "code_site";

    /**
     *
     */
    public static final String COLUMN_CODETHEME_JEU = "code_theme";

    /**
     *
     */
    public static final String COLUMN_CODEDATATYPE_JEU = "code_datatype";

    /**
     *
     */
    public static final String COLUMN_CODE_JEU = "code_jeu";

    /**
     *
     */
    public static final String COLUMN_TITRE_JEU = "titre";

    /**
     *
     */
    public static final String COLUMN_DESCRIPTION_JEU = "description";

    /**
     *
     */
    public static final String COLUMN_OBJECTIF_JEU = "objectif";

    /**
     *
     */
    public static final String COLUMN_GENEALOGIE_JEU = "genealogie";

    /**
     *
     */
    public static final String COLUMN_DOI_JEU = "doi";

    /**
     *
     */
    public static final String COLUMN_TITRE_LICENCE_JEU = "titre_licence";

    /**
     *
     */
    public static final String COLUMN_URL_LICENCE_JEU = "url_licence";

    /**
     *
     */
    public static final String COLUMN_URL_DOWNLOAD_JEU = "url_download";

    /**
     *
     */
    public static final String COLUMN_URL_INFO_JEU = "url_info";

    /**
     *
     */
    public static final String COLUMN_CITATION_JEU = "citation";

    /* Noms des colonnes pour la table reference */
    /**
     *
     */
    public static final String COLUMN_DOI_REF = "DOI";

    /**
     *
     */
    public static final String COLUMN_PREMIERAUTEUR_REF = "premier_auteur";

    /**
     *
     */
    public static final String COLUMN_ANNEE_REF = "année";

    /* Noms des colonnes pour la table site_theme_datatype_variable */
    /**
     *
     * /* Noms des colonnes pour la table site_theme_datatype_variable
     */
    /**
     *
     * /* Noms des colonnes pour la table site_theme_datatype_variable
     */
    /**
     *
     * /* Noms des colonnes pour la table site_theme_datatype_variable
     */
    /**
     *
     */
    public static final String COLUMN_VARID_STDTVAR = "var_id";

    /* Noms des colonnes pour la table periode_application_methode */
    /**
     *
     */
    public static final String COLUMN_DATEDEBUT_PAPPMETH = "date_debut";

    /**
     *
     */
    public static final String COLUMN_DATEFIN_PAPPMETH = "date_fin";

    /* Noms des colonnes pour la table periode_utilisation_instrument */
    /**
     *
     */
    public static final String COLUMN_DATEDEBUT_PUTILINSTR = "date_debut";

    /**
     *
     */
    public static final String COLUMN_DATEFIN_PUILINSTR = "date_fin";

    /* Noms des colonnes pour la table liste_valeur_info */
    /**
     *
     */
    public static final String COLUMN_NOM_LSTVALINFO = "nom";

    /* Noms des colonnes pour la table item_liste */
    /**
     *
     */
    public static final String COLUMN_LIBELLE_ITEMLST = "libelle";

    /**
     *
     */
    public static final String COLUMN_NOTE_ITEMLST = "note";

    /* Noms des colonnes pour la table information_complementaire */
    /**
     *
     */
    public static final String COLUMN_NOM_INFCPLT = "nom";

    /**
     *
     */
    public static final String COLUMN_DESCRIPTION_INFCPLT = "description";

    /**
     *
     */
    public static final String NAME_ATTRIBUT_MCALC = "methodeCalcul";

    /**
     *
     */
    public static final String NAME_ATTRIBUT_REFMCALC = "referenceMCalc";

    /**
     *
     */
    public static final String NAME_ATTRIBUT_INSTR = "instrument";

    /**
     *
     */
    public static final String NAME_ATTRIBUT_REFINSTRUMENT = "referenceInstrument";

    /**
     *
     */
    public static final String NAME_ATTRIBUT_LISTEVALINFO = "listeValeurInfo";

    /* Noms des colonnes pour la table type de site */
    /**
     *
     */
    public static final String COLUMN_NOM_TYPE_SITE = "tze_nom";

    /**
     *
     */
    public static final String COLUMN_DEFINITION_TYPE_SITE = "tze_definition";

    /* Noms des colonnes pour la table site (snot) */
    /**
     *
     */
    public static final String COLUMN_SITE_DESCRIPTION = "zet_description";

    /**
     *
     */
    public static final String COLUMN_SITE_DIRECTION_PENTE = "zet_direction_pente";

    /**
     *
     */
    public static final String COLUMN_SITE_PAYS = "zet_pays";

    /**
     *
     */
    public static final String COLUMN_SITE_REGION = "zet_region";

    /**
     *
     */
    public static final String COLUMN_SITE_DIRECTION_VENT = "zet_direction_vent";

    /**
     *
     */
    public static final String COLUMN_SITE_TYPE_SNOT = "zet_type_snot";

}
