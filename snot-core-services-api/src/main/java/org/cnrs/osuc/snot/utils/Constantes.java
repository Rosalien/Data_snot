/**
 *
 */
package org.cnrs.osuc.snot.utils;

import org.apache.log4j.Logger;
import org.inra.ecoinfo.utils.DateUtil;

/**
 * @author sophie
 *
 */
public class Constantes {

    /**
     *
     */
    public static final Logger LOGGER_FOR_EXTRACTION = Logger.getLogger("org.cnrs.osuc.snot.extraction.request");

    /**
     * * DATASET **
     */
    public static final String PROPERTY_CST_INVALID_BAD_MEASURE = "-9999";

    /**
     *
     */
    public static final String PROPERTY_CST_DATE_TYPE = "date";

    /**
     *
     */
    public static final String PROPERTY_CST_TIME_TYPE = "time";

    /**
     *
     */
    public static final String PROPERTY_CST_VARIABLE_TYPE = "variable";

    /**
     *
     */
    public static final String PROPERTY_CST_QUALITY_CLASS_TYPE = "quality class";

    /**
     *
     */
    public static final String PROPERTY_CST_FLOAT_TYPE = "float";

    /**
     *
     */
    public static final String PROPERTY_CST_DOUBLE_TYPE = "double";

    /**
     *
     */
    public static final String PROPERTY_CST_INTEGER_TYPE = "integer";

    /**
     *
     */
    public static final String PROPERTY_CST_ECART_TYPE = "ecart type";

    /**
     *
     */
    public static final String PROPERTY_CST_VALEUR_QUALITATIVE_TYPE = "valeur_qualitative";
    /**
     * The Constant PROPERTY_CST_LIST_VALEURS_QUALITATIVES_TYPE @link(String).
     */
    public static final String PROPERTY_CST_LIST_VALEURS_QUALITATIVES_TYPE = "liste_valeurs_qualitatives";
    /**
     * The Constant PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE. /** The Constant
     * PROPERTY_CST_GAP_FIELD_TYPE.
     */

    /**
     * The Constant PROPERTY_CST_NOT_AVALAIBALE.
     */
    public static final String PROPERTY_CST_GAP_FIELD_TYPE = "gap field";

    /**
     *
     */
    public static final String PROPERTY_CST_NBRE_MESURES = "nbre mesures";

    /**
     *
     */
    public static final String PROPERTY_CST_QUALITY_CODE = "quality code";

    /**
     *
     */
    public static final String PROPERTY_CST_IS_GAP_FILLED = "variable_isgf";

    /**
     *
     */
    public static final String PROPERTY_CST_CODE_TRT = "traitement";

    /**
     *
     */
    public static final String PROPERTY_CST_NO_CHAMBRE = "chambre";

    /**
     *
     */
    public static final String PROPERTY_CST_DUREE_MESURE = "dureeM";

    /**
     *
     */
    public static final String PROPERTY_CST_VARIABLE_GF = "variable_gf";

    /**
     *
     */
    public static final String REGEXP_QUALITY_CLASS_NUMBER = "[0-2]";

    /**
     *
     */
    public static final String REGEX_DATE = "^(([0-2][0-9]/(0[0-9]|1[0-2]))|(30/(04|06|09|11))|(3[01]/(01|03|05|07|08|10|12)))/[0-2][0-9]{3}$";

    /**
     *
     */
    public static final String REGEX_DATE_MOIS = "^(01|02|03|04|05|06|07|08|09|10|11|12)/[0-2][0-9]{3}$";

    /**
     *
     */
    public static final String REGEX_HEURE = "^(([01]?[0-9]|2[0-3]):[0-5][0-9]|24:00)$";

    /**
     *
     */
    public static final String FILE_NAME_DATASET_DESCRIPTOR_SEMI_HORAIRE_XML = "dataset-descriptor-semi-horaire.xml";

    /**
     *
     */
    public static final String FILE_NAME_DATASET_DESCRIPTOR_JOURNALIER_XML = "dataset-descriptor-journalier.xml";

    /**
     *
     */
    public static final String FILE_NAME_DATASET_DESCRIPTOR_MENSUEL_XML = "dataset-descriptor-mensuel.xml";

    /**
     *
     */
    public static final String FILE_NAME_DATASET_DESCRIPTOR_INFRA_JOURNALIER_XML = "dataset-descriptor-infra-journalier.xml";

    /**
     *
     */
    public static final String FORMAT_DATE = "dd/mm/yyyy";

    /**
     *
     */
    public static final String FORMAT_DATE_MENSUEL = "mm/yyyy";

    /**
     *
     */
    public static final String FORMAT_DATE_WT_YEAR = "dd/MM";

    /**
     *
     */
    public static final String PATTERN_VARIABLE_EUROPEAN_NOM = "^(%s_([0-9]+)_([0-9]+))$";

    /**
     *
     */
    public static final String VARIABLE_MSG = "Variable \"";

    /**
     *
     */
    public static final String VARIABLE_MSG_SUITE = "\" : ";

    /**
     *
     */
    public static final String FORMAT_FILE = "csv";

    /**
     *
     */
    public static final String THEME_FLUX = "flux";

    /**
     *
     */
    public static final String THEME_GES = "ges";

    /**
     *
     */
    public static final String THEME_METEO = "meteosol";

    /**
     *
     */
    public static final String THEME_HYDRO = "hydrocarto";

    /**
     *
     */
    public static final String THEME_BIOGEO = "biogeo";

    /**
     *
     */
    public static final String[] FREQUENCE_NAME = new String[]{
        "semi-horaire", "journalier", "mensuel", "infra-journalier"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_FLUX = {"flux_sh", "flux_j", "flux_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_EDDYCOVARIANCE = {"eddycovariance_sh", "eddycovariance_j", "eddycovariance_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_CHAMBRECO2 = {"chambreco2_sh", "chambreco2_infraj", "chambreco2_j", "chambreco2_m"};
        
    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_METEO = {"meteosol_sh", "meteosol_infraj", "meteosol_j", "meteosol_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_PIEZO = {"piezo_sh", "piezo_infraj", "piezo_j", "piezo_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_DEBIT = {"debit_sh", "debit_infraj", "debit_j", "debit_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_DOC = {"docpoc_sh", "docpoc_infraj", "docpoc_j", "docpoc_m"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_HUMIDITEVOL = {"swc_infraj", "swc_j"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_TEMPERATURE = {"ts_infraj", "ts_j"};
    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_FLUXCHALEUR = {"g_infraj", "g_j"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_TENSIONEAU = {"smp_infraj", "smp_j"};

    /**
     *
     */
    public static final String[] DATATYPE_FREQUENCE_PROFONDEURNAPPE = {"gwd_infraj", "gwd_j"};

    /**
     *
     */
    public static final String DATATYPE_CLIMATSOL_M = "cms_m";

    /**
     *
     */
    public static final String FORMAT_TYPE = "float";

    /**
     *
     */
    public static final String NAME_COLUMN_ECARTTYPE = "SE";

    /**
     *
     */
    public static final String NAME_COLUMN_NBREREP = "Nb Rep";

    /**
     *
     */
    public static final String DATATYPE_HUMIDITEVOL = "SWC";

    /**
     *
     */
    public static final String NAME_VARIABLE_HUMIDITEVOL = "SWC";

    /**
     *
     */
    public static final String DATATYPE_TEMPERATURE = "Ts";

    /**
     *
     */
    public static final String NAME_VARIABLE_TEMPERATURE = "Ts";

    /**
     *
     */
    public static final String DATATYPE_FLUXCHALEUR = "G";

    /**
     *
     */
    public static final String NAME_VARIABLE_FLUXCHALEUR = "G";

    /**
     *
     */
    public static final String DATATYPE_TENSIONEAU = "SMP";

    /**
     *
     */
    public static final String NAME_VARIABLE_TENSIONEAU = "SMP";

    /**
     *
     */
    public static final String DATATYPE_PROFONDEURNAPPE = "GWD";

    /**
     *
     */
    public static final String NAME_VARIABLE_PROFONDEURNAPPE = "GWD";

    /**
     *
     */
    public static final String THEME_CHAMBREFLUXSOL = "Flux gazeux";

    /**
     *
     */
    public static final String DATATYPE_CHAMBREFLUXSOL = "ChambreFluxSol";

    /**
     * * EXTRACTION **
     */
    public static final String RULE_METEO_NAVIGATION_JSF = "meteosol";

    /**
     * * EXTRACTION **
     */
    public static final String RULE_FLUXMETEO_NAVIGATION_JSF = "fluxMeteo";

    /**
     *
     */
    public static final int EUROPEAN_FORMAT = 1;

    /**
     *
     */
    public static final int CLASSIC_FORMAT = 2;

    /**
     *
     */
    public static final String CLIMATSOL_EXTRACTION_TYPE_CODE = "climat_du_sol";

    /**
     *
     */
    public static final String MENSUEL = "m";

    /**
     *
     */
    public static final String SEMI_HORAIRE = "sh";

    /**
     *
     */
    public static final String JOURNALIER = "j";

    /**
     *
     */
    public static final String INFRA_JOURNALIER = "infraj";

    /**
     *
     */
    public static final String MOTIF = "------------------------------------------";

    /**
     *
     */
    public static final String LIST_PATTERN = "\t-%s";

    /**
     *
     */
    public static final String SEPARATOR_PATTERN = "\t%s";

    /**
     *
     */
    public static final String CST_REQUEST_REMINDER = "request_reminder";

    /**
     *
     */
    public static final String EXTENSION_TXT = ".txt";

    /**
     *
     */
    public static final String COLUMN_NOM_TABLE_VARIABLE = "definition";

    /**
     *
     */
    public static final String EXTRACTION = "extraction";

    /**
     *
     */
    public static final String FILENAME_REMINDER = "recapitulatif_extraction";

    /**
     *
     */
    public static final String DATA = "dataResult";

    /**
     *
     */
    public static final String REQUEST_REMINDER = "RequestReminder";

    /**
     *
     */
    public static final String SEPARATOR_TEXT = Constantes.CST_UNDERSCORE;

    /**
     *
     */
    public static final String EXTENSION_ZIP = ".zip";

    /**
     *
     */
    public static final String EXTENSION_CSV = ".csv";

    /**
     *
     */
    public static final String SUFFIX_FILENAME_DEFAULT = "";

    /**
     *
     */
    public static final String CHARACTER_ENCODING_ISO_8859_1 = "ISO-8859-1";

    /**
     *
     */
    public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

    /**
     *
     */
    public static final String MAP_INDEX_0 = "0";

    /**
     *
     */
    public static final String CLIMATSOL = "meteosol";

    /**
     * * Application **
     */
    public static final String CST_NEW_LINE = "\n";

    /**
     *
     */
    public static final String CST_HYPHEN = "-";

    /**
     *
     */
    public static final String CST_COMMA = ",";

    /**
     *
     */
    public static final String CST_DOT = ".";

    /**
     *
     */
    public static final String CST_SPACE = " ";

    /**
     *
     */
    public static final String CST_STRING_EMPTY = "";

    /**
     *
     */
    public static final String CST_UNDERSCORE = "_";

    /**
     *
     */
    public static final String CST_STAR = "*";

    /**
     *
     */
    public static final String TAB = "\t";

    /**
     *
     */
    public static final String CST_PARENTHESE_OUVRANTE = " (";

    /**
     *
     */
    public static final String CST_PARENTHESE_FERMANTE = ")";

    /**
     *
     */
    public static final char CSY_CSV_SEPARATOR8CHAR = ';';

    /**
     *
     */
    public static final String CSY_CSV_SEPARATOR = ";";

    /**
     *
     */
    public static final String CST_MIN = "min";

    /**
     *
     */
    public static final String CST_MAX = "max";
    /**
     * The Constant PATTERN_TIME.
     */
    public static final String PATTERN_TIME = "^([0-1][0-9]|2[0-3]):([0-6][0-9])$|24:00";
    /**
     * The Constant PATTERN_DATE.
     */
    public static final String PATTERN_DATE = "^(([0-2][0-9]/(0[0-9]|1[0-2]))|(30/(04|06|09|11))|(3[01]/(01|03|05|07|08|10|12)))/[0-2][0-9]{3}";

    /**
     * getSorteableDateFormatUTC getSorteableDateFormatLocale
     */
    public static final String SORTEABLE_DATE_FORMAT = "yyyyMMddHHmm";
    /**
     * The Constant SNOT_DATASET_BUNDLE_NAME.
     */
    public static final String SNOT_DATASET_BUNDLE_NAME = "org.cnrs.osuc.snot.dataset.messages";
    /**
     * The Constant PROPERTY_CST_QUALITY_CLASS_GENERIC.
     */
    public static final String PROPERTY_CST_QUALITY_CLASS_GENERIC = "qc";
    /**
     * The Constant PROPERTY_CST_FRENCH_TIME.
     */
    public static final String PROPERTY_CST_FRENCH_TIME = DateUtil.HH_MM;
    /**
     * The Constant PROPERTY_CST_FRENCH_DATE.
     */
    public static final String PROPERTY_CST_FRENCH_DATE = DateUtil.DD_MM_YYYY;
    /**
     * The Constant PROPERTY_CST_VARIABLE_TYPE.
     */
    /**
     * The Constant PROPERTY_CST_NOT_AVALAIBALE.
     */
    public static final String PROPERTY_CST_NOT_AVALAIBALE = "NA";
}
