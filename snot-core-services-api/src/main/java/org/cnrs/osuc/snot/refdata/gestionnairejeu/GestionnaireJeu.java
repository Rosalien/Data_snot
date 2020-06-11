/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.refdata.gestionnairejeu;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.refdata.valeurqualitative.ValeurQualitative;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 *
 * @author jbparoissien
 */
@Entity
@Table(name = GestionnaireJeu.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {
            Jeu.ID_JPA,
            RefDataConstantes.COLUMN_EMAIL_PERS,
            ValeurQualitative.ID_JPA,
            RefDataConstantes.COLUMN_DATEDEBUT_PERS
        }),
        indexes = {
            @Index(name = "per_jeu_idx", columnList = Jeu.ID_JPA),
            @Index(name = "per_valeurqualitative_idx", columnList = ValeurQualitative.ID_JPA)
        }
) 
public class GestionnaireJeu implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.GESTIONNAIREJEU_ID;
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.GESTIONNAIREJEU_TABLE_NAME;

    @Id
    @Column(name = GestionnaireJeu.ID_JPA)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;
      
    @Column(nullable = false, name = RefDataConstantes.COLUMN_NOM_PERS, length = 50)
    private String nom;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_PRENOM_PERS, length = 50)
    private String prenom;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_EMAIL_PERS, length = 100)
    private String email;

    @ManyToOne(optional = false, targetEntity = ValeurQualitative.class)
    @JoinColumn(name = ValeurQualitative.ID_JPA, referencedColumnName = ValeurQualitative.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private ValeurQualitative responsabilite;

    @Column(nullable = false, name = RefDataConstantes.COLUMN_DATEDEBUT_PERS)
     LocalDate dateDebut;

    @Column(nullable = true, name = RefDataConstantes.COLUMN_DATEFIN_PERS)
     LocalDate dateFin;

    /**
     * empty constructor
     */
    public GestionnaireJeu() {
        super();
    }

    /**
     * @param code_jeu
     * @param nom
     * @param prenom
     * @param email
     * @param responsabilite
     * @param dateDebut
     * @param dateFin
     */
    public GestionnaireJeu(Jeu code_jeu, String nom, String prenom, String email, ValeurQualitative responsabilite, LocalDate dateDebut, LocalDate dateFin) {
        super();
        this.jeu = code_jeu;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.responsabilite = responsabilite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * @param nom
     *
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return prenom
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * @param prenom
     *
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the responsabilite
     */
    public ValeurQualitative getResponsabilite() {
        return this.responsabilite;
    }

    /**
     * @param responsabilite the responsabilite to set
     */
    public void setResponsabilite(ValeurQualitative responsabilite) {
        this.responsabilite = responsabilite;
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
