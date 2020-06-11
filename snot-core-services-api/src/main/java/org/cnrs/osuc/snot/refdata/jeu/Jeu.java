/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.jeu;

import java.io.Serializable;
import org.inra.ecoinfo.refdata.theme.Theme;
import org.inra.ecoinfo.refdata.site.Site;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author jbparoissien
 */
@Entity
@Table(name = Jeu.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {SiteSnot.RECURENT_NAME_ID, Theme.THEME_NAME_ID}),
        indexes = {
            @Index(name = "ir_code_site_idx", columnList = SiteSnot.RECURENT_NAME_ID)
            ,
            @Index(name = "ir_theme_idx", columnList = Theme.THEME_NAME_ID)

        })

public class Jeu implements Serializable {

    /**
     *
     */
    static public final String NAME_ENTITY_JPA_SNOT = "jeu";
    public static final String ID_JPA = RefDataConstantes.JEU_ID;
    public static final String TABLE_NAME = RefDataConstantes.JEU_TABLE_NAME;

    @Id
    @Column(name = Jeu.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false, targetEntity = SiteSnot.class)
    @JoinColumn(name = SiteSnot.RECURENT_NAME_ID, referencedColumnName = SiteSnot.RECURENT_NAME_ID, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private SiteSnot site;

    @ManyToOne(optional = false, targetEntity = Theme.class)
    @JoinColumn(name = Theme.THEME_NAME_ID, referencedColumnName = Theme.THEME_NAME_ID, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Theme theme;

//    @ManyToOne(optional = false, targetEntity = DataType.class)
//    @JoinColumn(name = "test", referencedColumnName = DataType.DATATYPE_NAME_ID, nullable = false)
//    @LazyToOne(LazyToOneOption.PROXY)
//    private DataType datatype;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_CODE_JEU, length = 50)
    private String code_jeu;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_TITRE_JEU, length = 150)
    private String titre;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_DESCRIPTION_JEU, length = 2000)
    private String description;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_OBJECTIF_JEU, length = 2000)
    private String objectif;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_GENEALOGIE_JEU, length = 2000)
    private String genealogie;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_DOI_JEU, length = 500)
    private String doi;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_CITATION_JEU, length = 2000)
    private String citation;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_TITRE_LICENCE_JEU, length = 50)
    private String titre_licence;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_URL_LICENCE_JEU, length = 200)
    private String url_licence;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_URL_DOWNLOAD_JEU, length = 200)
    private String url_download;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_URL_INFO_JEU, length = 200)
    private String url_info;

    /**
     *
     */
    public Jeu() {
        super();
    }

    /**
     * Instantiates a new jeu.
     *
     * @param code_jeu
     * @param code_site
     * @param code_theme
     * @param titre
     * @param description
     * @param objectif
     * @param genealogie
     * @param doi
     * @param citation
     * @param titre_licence
     * @param url_licence
     * @param url_download
     * @param url_info
     * 
     */
    public Jeu(String code_jeu, SiteSnot code_site, Theme code_theme, String titre, String description, String objectif, String genealogie,
            String doi, String citation, String titre_licence, String url_licence, String url_download,String url_info) {
        super();
        this.code_jeu = code_jeu;
        this.site = code_site;
        this.theme = code_theme;
        this.titre = titre;
        this.description = description;
        this.objectif = objectif;
        this.genealogie = genealogie;
        this.doi = doi;
        this.citation = citation;
        this.titre_licence = titre_licence;
        this.url_licence = url_licence;
        this.url_download = url_download;
        this.url_info = url_info;
    }

    public String getUrl_download() {
        return url_download;
    }

    public void setUrl_download(String url_download) {
        this.url_download = url_download;
    }

    public String getUrl_info() {
        return url_info;
    }

    public void setUrl_info(String url_info) {
        this.url_info = url_info;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getDoi() {
        return doi;
    }

    public String getTitre_licence() {
        return titre_licence;
    }

    public void setTitre_licence(String titre_licence) {
        this.titre_licence = titre_licence;
    }

    public String getUrl_licence() {
        return url_licence;
    }

    public void setUrl_licence(String url_licence) {
        this.url_licence = url_licence;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return code_jeu
     */
    public String getCodeJeu() {
        return this.code_jeu;
    }

    /**
     * @param code_jeu
     *
     */
    public void setCodeJeu(String code_jeu) {
        this.code_jeu = code_jeu;
    }

    public SiteSnot getSite() {
        return site;
    }

    public void setSite(SiteSnot site) {
        this.site = site;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * @return titre
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * @param titre
     *
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return objectif
     */
    public String getObjectif() {
        return this.objectif;
    }

    /**
     * @param objectif
     *
     */
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    /**
     * @return genealogie
     */
    public String getGenealogie() {
        return genealogie;
    }

    /**
     * @param genealogie
     *
     */
    public void setGenealogie(String genealogie) {
        this.genealogie = genealogie;
    }

}
