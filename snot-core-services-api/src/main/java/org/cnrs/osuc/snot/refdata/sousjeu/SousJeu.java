/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.sousjeu;

import java.io.Serializable;
import java.time.LocalDate;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
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
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author jbparoissien
 */
@Entity
@Table(name = SousJeu.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {
    Jeu.ID_JPA,
    RefDataConstantes.COLUMN_DOI_JEU
}),
        indexes = {
            @Index(name = "ir_code_jeu_idx", columnList = Jeu.ID_JPA)
            ,
                @Index(name = "ir_doi_jeu_idx", columnList = RefDataConstantes.COLUMN_DOI_JEU)
        })

public class SousJeu implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.SOUSJEU_ID;
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = RefDataConstantes.SOUSJEU_TABLE_NAME;

    @Id
    @Column(name = SousJeu.ID_JPA)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;

    /**
     *
     */
    @Column(nullable = true, name = RefDataConstantes.COLUMN_TITRE_JEU, length = 2000)
    private String titre;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_DOI_JEU, length = 500)
    private String doi;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_CITATION_JEU, length = 2000)
    private String citation;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_TITRE_LICENCE_JEU, length = 50)
    private String titre_licence;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_URL_LICENCE_JEU, length = 200)
    private String url_licence;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_DATEDEBUT_PERS)
    LocalDate dateDebut;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_DATEFIN_PERS)
    LocalDate dateFin;

    /**
     *
     */
    public SousJeu() {
        super();
    }

    /**
     * Instantiates a new sousjeu.
     *
     * @param code_jeu
     * @param titre
     * @param doi
     * @param citation
     * @param titre_licence
     * @param url_licence
     * @param dateDebut
     * @param dateFin
     *
     */
    public SousJeu(Jeu code_jeu, String titre, String doi, String citation, String titre_licence, String url_licence, LocalDate dateDebut, LocalDate dateFin) {
        super();
        this.jeu = code_jeu;
        this.titre = titre;
        this.doi = doi;
        this.citation = citation;
        this.titre_licence = titre_licence;
        this.url_licence = url_licence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
     * @return codejeu
     */
    public Jeu getJeu() {
        return this.jeu;
    }

    /**
     * @param jeu
     *
     */
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
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
     * @return the dateDebut
     */
    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    /**
     * @param dateDebut the dateDebut to set
     */
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * @return the dateFin
     */
    public LocalDate getDateFin() {
        return this.dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

}
