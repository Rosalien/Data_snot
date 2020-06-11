package org.cnrs.osuc.snot.dataset.test.utils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.inra.ecoinfo.dataset.config.IDatasetConfiguration;
import org.inra.ecoinfo.dataset.versioning.IVersionFileDAO;
import org.inra.ecoinfo.dataset.versioning.entity.Dataset;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.identification.IUtilisateurSnotDAO;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.refdata.traitement.ITraitementDAO;
import org.cnrs.osuc.snot.refdata.traitement.Traitement;
import org.cnrs.osuc.snot.refdata.variable.IVariableSnotDAO;
import org.cnrs.osuc.snot.refdata.variable.VariableSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.SnotMessages;
import org.cnrs.osuc.snot.utils.Outils;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.localization.entity.Localization;
import org.inra.ecoinfo.localization.entity.UserLocale;
import org.inra.ecoinfo.localization.impl.SpringLocalizationManager;
import org.inra.ecoinfo.mga.business.IMgaServiceBuilder;
import org.inra.ecoinfo.mga.business.composite.INode;
import org.inra.ecoinfo.mga.business.composite.NodeDataSet;
import org.inra.ecoinfo.mga.business.composite.RealNode;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.mga.middleware.IMgaRecorder;
import org.inra.ecoinfo.refdata.datatype.DataType;
import org.inra.ecoinfo.refdata.datatype.IDatatypeDAO;
import org.inra.ecoinfo.refdata.sitethemedatatype.ISiteThemeDatatypeDAO;
import org.inra.ecoinfo.refdata.theme.IThemeDAO;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.refdata.unite.IUniteDAO;
import org.inra.ecoinfo.refdata.unite.Unite;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.PersistenceException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.IDatatypeVariableUniteSnotDAO;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.site.ISiteSnotDAO;

/**
 *
 *
 *
 * @author ptcherniati
 */
public class MockUtils {

    /**
     *
     */
    public static String DATE_DEBUT = "01/01/2012";

    /**
     *
     */
    public static String DATE_FIN = "31/12/2012";

    /**
     *
     */
    public static String DATE_DEBUT_COMPLETE = "01/01/2012 00:00:00";

    /**
     *
     */
    public static String DATE_FIN_COMPLETE = "31/12/2012 00:00:00";

    /**
     *
     */
    public static String PARCELLE_NOM = "Parcelle";

    /**
     *
     */
    public static String SITE_PARENT = "parent";

    /**
     *
     */
    public static String SITE = "site";

    /**
     *
     */
    public static String SITE_CODE = "parent/site/enfant";

    /**
     *
     */
    public static String JEU_CODE = "codeJeu";

    /**
     *
     */
    public static String SITE_PARENT_CODE = "parent";

    /**
     *
     */
    public static String SITE_ENFANT = "enfant";

    /**
     *
     */
    public static String SITE_NOM = "Site/enfant";

    /**
     *
     */
    public static Long SITE_ID = 1L;

    /**
     *
     */
    public static String THEME = "theme";

    /**
     *
     */
    public static String DATATYPE = "datatype";

    /**
     *
     */
    public static String DATATYPE_NOM = "Datatype";

    /**
     *
     */
    public static String VARIABLE_CODE = "variablecode";

    /**
     *
     */
    public static String VARIABLE_NOM = "variableNom";

    /**
     *
     */
    public static String VARIABLE_AFFICHAGE = "variableAffichage";

    /**
     *
     */
    public static String UNITE = "unite";

    /**
     *
     */
    public static String TRAITEMENT = "traitement";

    /**
     *
     */
    public static String TRAITEMENT_LIBELLE = "Traitement";

    /**
     *
     */
    public static String SITE_PATH = "parent/site/enfant";

    /**
     *
     */
    public static String SITE_THEME_PATH = SITE_PATH.concat(PatternConfigurator.PATH_SEPARATOR).concat(THEME);

    /**
     *
     */
    public static String SITE_THEME_DATATYPE_PATH = SITE_THEME_PATH.concat(PatternConfigurator.PATH_SEPARATOR).concat(DATATYPE);

    /**
     *
     */
    public static String SITE_THEME_DATATYPE_VARIABLE_PATH = SITE_THEME_DATATYPE_PATH.concat(PatternConfigurator.PATH_SEPARATOR).concat(VARIABLE_CODE);

    /**
     *
     */
    public static ILocalizationManager localizationManager;
    private static final String UTILISATEUR = "utilisateur";
    private static final Logger LOG = Logger.getLogger(MockUtils.class.getName());

    static {
    }

    /**
     *
     * @return
     */
    public static MockUtils getInstance() {

        MockUtils instance = new MockUtils();
        try {
            instance.initMocks();
        } catch (ParseException ex) {
            Logger.getLogger(MockUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        MockitoAnnotations.initMocks(instance);

        try {

            instance.initMocks();

        } catch (ParseException ex) {

            return null;

        }

        initLocalization();

        return instance;

    }

    /**
     *
     */
    public static void initLocalization() {
        Localization.setDefaultLocalisation("fr");

        Localization.setLocalisations(Arrays.asList(new String[]{"fr"}));

        localizationManager = new SpringLocalizationManager();

        ((SpringLocalizationManager) localizationManager).setUserLocale(new UserLocale());

        SnotMessages.setLocalizationManager(localizationManager);
    }

    /**
     *
     */
    @Mock
    public VersionFile versionFile;

    /**
     *
     */
    @Mock
    public IVersionFileDAO versionFileDAO;

    /**
     *
     */
    @Mock
    public Dataset dataset;

    /**
     *
     */
    @Mock
    public RealNode siteRealNode;

    /**
     *
     */
    @Mock
    public RealNode themeRealNode;

    /**
     *
     */
    @Mock
    public RealNode datatypeRealNode;

    /**
     *
     */
    @Mock
    public RealNode variableRealNode;

    /**
     *
     */
    @Mock
    public NodeDataSet siteNode;

    /**
     *
     */
    @Mock
    public NodeDataSet themeNode;

    /**
     *
     */
    @Mock
    public NodeDataSet datatypeNode;

    /**
     *
     */
    @Mock
    public NodeDataSet variableNode;

    /**
     *
     */
    @Mock
    public ISiteThemeDatatypeDAO siteThemeDatatypeDAO;

    /**
     *
     */
    @Mock
    public SiteSnot site;
    /**
     *
     */
    @Mock
    public SiteSnot siteParent;
    /**
     *
     */
    @Mock
    public SiteSnot siteEnfant;

    /**
     *
     */
    @Mock
    public ISiteSnotDAO siteDAO;

    /**
     *
     */
    @Mock
    public Theme theme;

    /**
     *
     */
    @Mock
    public IThemeDAO themeDAO;

    /**
     *
     */
    @Mock
    public DataType datatype;

    /**
     *
     */
    @Mock
    public IDatatypeDAO datatypeDAO;

    /**
     *
     */
    @Mock
    public Utilisateur utilisateur;

    /**
     *
     */
    @Mock
    public VariableSnot variable;

    /**
     *
     */
    @Mock
    public IVariableSnotDAO variableDAO;

    /**
     *
     */
    @Mock
    public Unite unite;

    /**
     *
     */
    @Mock
    public IUniteDAO uniteDAO;

    /**
     *
     */
    @Mock
    public Traitement traitement;

    /**
     *
     */
    @Mock
    public ITraitementDAO traitementDAO;

    /**
     *
     */
    @Mock
    public DatatypeVariableUniteSnot datatypeVariableUnite;

    /**
     *
     */
    @Mock
    public IDatatypeVariableUniteSnotDAO datatypeVariableUniteDAO;

    /**
     *
     */
    @Mock
    public List<DatatypeVariableUniteSnot> datatypeVariableUnites;

    /**
     *
     */
    @Mock
    public Column column;

    /**
     *
     */
    @Mock
    public List<Column> columns;

    /**
     *
     */
    @Mock
    public DatasetDescriptor datasetDescriptor;

    /**
     *
     */
    public LocalDateTime dateDebut;

    /**
     *
     */
    public LocalDateTime dateDebutComplete;

    /**
     *
     */
    public LocalDateTime dateFin;

    /**
     *
     */
    public LocalDateTime dateFinComplete;

    /**
     *
     */
    @Mock
    public IDatasetConfiguration datasetConfiguration;

    /**
     *
     */
    @Mock
    public IUtilisateurSnotDAO utilisateurDAO;

    /**
     *
     */
    @Mock
    public IPolicyManager policyManager;

    /**
     *
     */
    @Mock
    public IMgaServiceBuilder mgaServiceBuilder;

    /**
     *
     */
    @Mock
    public IMgaRecorder mgaRecorder;

    /**
     *
     */
    public MockUtils() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     * @throws ParseException
     */
    public void initMocks() throws ParseException {

        try {

            this.initDates();

            this.initEntities();

            this.initDAOs();

            this.initDatasetDescriptor();

            this.initDataset();
            when(policyManager.getMgaServiceBuilder()).thenReturn(mgaServiceBuilder);
            when(mgaServiceBuilder.getRecorder()).thenReturn(mgaRecorder);

        } catch (PersistenceException ex) {

            Logger.getLogger(MockUtils.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     *
     * @throws ParseException
     */
    @Test
    public void test() throws ParseException {

        MockUtils mockUtils = MockUtils.getInstance();

        VersionFile v = mockUtils.versionFile;

        assertEquals(UTILISATEUR, v.getUploadUser().getLogin());

        assertTrue(1L == v.getVersionNumber());

        assertEquals(DATE_DEBUT, DateUtil.getUTCDateTextFromLocalDateTime(v.getDataset().getDateDebutPeriode(), DateUtil.DD_MM_YYYY));

        assertEquals(DATE_FIN, DateUtil.getUTCDateTextFromLocalDateTime(v.getDataset().getDateFinPeriode(), DateUtil.DD_MM_YYYY));

        assertEquals(v, v.getDataset().getVersions().get(1L));

        assertEquals(DATATYPE, Outils.chercherNodeable(v.getDataset(), DataType.class).map(d -> d.getCode()).orElseGet(String::new));

        assertEquals(SITE_CODE, Outils.chercherNodeable(v.getDataset(), SiteSnot.class).map(d -> d.getCode()).orElseGet(String::new));
        
//        assertEquals(JEU_CODE, Outils.chercherNodeable(v.getDataset(), Jeu.class).map(d -> d.getCode()).orElseGet(String::new));

        
        assertEquals(SITE_PATH, Outils.chercherNodeable(v.getDataset(), SiteSnot.class).map(d -> d.getPath()).orElseGet(String::new));

        assertEquals(THEME, Outils.chercherNodeable(v.getDataset(), Theme.class).map(d -> d.getCode()).orElseGet(String::new));

    }

    private void initEntities() {

        // init jeu
        //when(this.jeu.getCode()).thenReturn(Jeu_CODE);
        
        // init site
        when(this.site.getPath()).thenReturn(SITE_PATH);

        when(this.site.getCode()).thenReturn(SITE_CODE);

        when(this.site.getName()).thenReturn(SITE_NOM);

        when(this.site.getId()).thenReturn(SITE_ID);

        // init theme
        when(this.theme.getCode()).thenReturn(THEME);

        // init datatype
        when(this.datatype.getCode()).thenReturn(DATATYPE);

        // init ast
        when(this.siteRealNode.getNodeable()).thenReturn(this.site);
        when(this.siteRealNode.getPath()).thenReturn(SITE_PATH);
        when(this.themeRealNode.getNodeable()).thenReturn(this.theme);
        when(this.themeRealNode.getParent()).thenReturn(this.siteRealNode);
        when(this.themeRealNode.getPath()).thenReturn(SITE_THEME_PATH);
        when(this.datatypeRealNode.getNodeable()).thenReturn(this.datatype);
        when(this.datatypeRealNode.getParent()).thenReturn(this.themeRealNode);
        when(this.datatypeRealNode.getPath()).thenReturn(SITE_THEME_DATATYPE_PATH);
        when(this.variableRealNode.getNodeable()).thenReturn(this.variable);
        when(this.variableRealNode.getParent()).thenReturn(this.variableRealNode);
        when(this.variableRealNode.getPath()).thenReturn(SITE_THEME_DATATYPE_VARIABLE_PATH);
        when(datatypeRealNode.getNodeByNodeableTypeResource(SiteSnot.class)).thenReturn(siteRealNode);
        when(datatypeRealNode.getNodeByNodeableTypeResource(Theme.class)).thenReturn(themeRealNode);
        when(datatypeRealNode.getNodeByNodeableTypeResource(DataType.class)).thenReturn(datatypeRealNode);
        when(variableRealNode.getNodeByNodeableTypeResource(SiteSnot.class)).thenReturn(siteRealNode);
        when(variableRealNode.getNodeByNodeableTypeResource(Theme.class)).thenReturn(themeRealNode);
        when(variableRealNode.getNodeByNodeableTypeResource(DataType.class)).thenReturn(datatypeRealNode);
        when(variableRealNode.getNodeByNodeableTypeResource(VariableSnot.class)).thenReturn(variableRealNode);
        when(this.siteNode.getNodeable()).thenReturn(this.site);
        when(this.siteNode.getPath()).thenReturn(SITE_PATH);
        when(this.themeNode.getNodeable()).thenReturn(this.theme);
        when(this.themeNode.getParent()).thenReturn(this.siteNode);
        when(this.themeNode.getPath()).thenReturn(SITE_THEME_PATH);
        when(this.datatypeNode.getNodeable()).thenReturn(this.datatype);
        when(this.datatypeNode.getParent()).thenReturn(this.themeNode);
        when(this.datatypeNode.getPath()).thenReturn(SITE_THEME_DATATYPE_PATH);
        when(this.variableNode.getNodeable()).thenReturn(this.variable);
        when(this.variableNode.getParent()).thenReturn(this.variableNode);
        when(this.variableNode.getPath()).thenReturn(SITE_THEME_DATATYPE_VARIABLE_PATH);

        when(this.traitement.getCode()).thenReturn(TRAITEMENT);
        when(this.traitement.getLibelle()).thenReturn(TRAITEMENT_LIBELLE);

        // init variable
        when(this.variable.getCode()).thenReturn(VARIABLE_CODE);

        when(this.variable.getName()).thenReturn(VARIABLE_NOM);

        when(this.variable.getAffichage()).thenReturn(VARIABLE_AFFICHAGE);

        // init unite
        when(this.unite.getCode()).thenReturn(UNITE);

    }

    private void initDataset() throws ParseException {

        LocalDateTime dateDebut = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, DATE_DEBUT);

        LocalDateTime dateFin = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, DATE_FIN);

        // init user
        when(this.utilisateur.getLogin()).thenReturn(UTILISATEUR);

        // init dataset
        when(this.dataset.getDateDebutPeriode()).thenReturn(this.dateDebut);

        when(this.dataset.getDateFinPeriode()).thenReturn(this.dateFin);

        when(this.dataset.getVersions()).thenReturn(this.getVersions());

        when(this.dataset.getLastVersion()).thenReturn(this.versionFile);

        when(this.dataset.getVersions()).thenReturn(this.getVersions());

        when(this.dataset.getRealNode()).thenReturn(this.datatypeRealNode);

        // init version
        when(this.versionFile.getDataset()).thenReturn(this.dataset);

        when(this.versionFile.getVersionNumber()).thenReturn(1L);
        when(datasetConfiguration.getFileNameDateFormat(MockUtils.DATATYPE)).thenReturn(DateUtil.DD_MM_YYYY);

        when(this.versionFile.getUploadUser()).thenReturn(this.utilisateur);
        final String buildName = String.format("%s_%s_%s_%s.csv", SITE_PATH.replaceAll("/", Constantes.CST_HYPHEN), DATATYPE,
                DateUtil.getUTCDateTextFromLocalDateTime(dateDebut, DateUtil.DD_MM_YYYY_FILE),
                DateUtil.getUTCDateTextFromLocalDateTime(dateFin, DateUtil.DD_MM_YYYY_FILE)
        );

        when(this.dataset.buildDownloadFilename(this.datasetConfiguration)).thenReturn(buildName);

    }

    private void initDAOs() throws PersistenceException {

        // daos
        when(utilisateurDAO.getById(any(Long.class))).thenReturn(Optional.of(utilisateur));
        doReturn(Optional.empty()).when(siteDAO).getByCode(any());
        doReturn(Optional.empty()).when(siteDAO).getByCodeAndParent(any(), any());
        //doReturn(Optional.empty()).when(siteDAO).getByNameAndParent(any(), any());//JBP1809
        when(this.siteDAO.getByPath(SITE_PATH)).thenReturn(Optional.of(this.site));

        when(this.siteDAO.getByCode(SITE_CODE)).thenReturn(Optional.of(this.site));

        when(this.siteDAO.getByCode(SITE_PARENT)).thenReturn(Optional.of(this.siteParent));//JBP1809

        when(this.siteDAO.getByCodeAndParent(SITE, siteParent)).thenReturn(Optional.of(this.site));//JBP1809

        when(this.siteDAO.getByCodeAndParent(SITE_ENFANT, site)).thenReturn(Optional.of(this.siteEnfant));//JBP1809

        doReturn(Optional.empty()).when(datatypeDAO).getByCode(any());
        when(this.datatypeDAO.getByCode(DATATYPE)).thenReturn(Optional.of(this.datatype));

        doReturn(Optional.empty()).when(variableDAO).getByCode(any());
        when(this.variableDAO.getByCode(VARIABLE_CODE)).thenReturn(Optional.of(this.variable));

        doReturn(Optional.empty()).when(datatypeVariableUniteDAO).getByNKey(any(), any(), any(), any(), any());
        doReturn(Optional.empty()).when(datatypeVariableUniteDAO).getUnite(any(), any());
        when(this.datatypeVariableUniteDAO.getByDatatype(DATATYPE)).thenReturn(datatypeVariableUnites);
        when(this.datatypeVariableUniteDAO.getByNKey(JEU_CODE,SITE_CODE, DATATYPE, VARIABLE_CODE, UNITE)).thenReturn(Optional.of(this.datatypeVariableUnite));

        doReturn(Optional.empty()).when(uniteDAO).getByCode(any());
        when(this.uniteDAO.getByCode(UNITE)).thenReturn(Optional.of(this.unite));

        doReturn(Optional.empty()).when(traitementDAO).getByCode(any());
        when(this.traitementDAO.getByCode(TRAITEMENT)).thenReturn(Optional.of(this.traitement));

        when(siteThemeDatatypeDAO.getByPathSiteThemeCodeAndDatatypeCode(SITE_CODE, THEME, DATATYPE)).thenReturn(Arrays.asList(new INode[]{datatypeNode}));
        doReturn(Optional.empty()).when(versionFileDAO).getById(anyLong());
    }

    private void getDataset() {
    }

    private Map<Long, VersionFile> getVersions() {

        Map<Long, VersionFile> versions = new HashMap();

        versions.put(1L, this.versionFile);

        return versions;

    }

    private void initDatasetDescriptor() {

        when(this.datasetDescriptor.getColumns()).thenReturn(this.columns);

        when(this.column.getName()).thenReturn("colonne1", "colonne2", "colonne3", "colonne4", "colonne5", "colonne6", "colonne7", "colonne8", "colonne9", "colonne10", null);

        when(this.datasetDescriptor.getName()).thenReturn("datasetDescriptorName");
        when(this.columns.size()).thenReturn(10);

        when(this.datasetDescriptor.getUndefinedColumn()).thenReturn(2);

        for (int i = 0; i < 10; i++) {

            when(this.columns.get(i)).thenReturn(this.column);

        }

    }

    private void initDates() throws ParseException {

        this.dateDebut = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, DATE_DEBUT);

        this.dateFin = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, DATE_FIN);

        this.dateDebutComplete = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM_SS, DATE_DEBUT_COMPLETE);

        this.dateFinComplete = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY_HH_MM_SS, DATE_FIN_COMPLETE);

    }

}
