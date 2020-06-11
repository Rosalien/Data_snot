/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeapplicationmethode;

import java.io.Serializable;
import java.time.LocalDate;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
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
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.cnrs.osuc.snot.refdata.methodecalcul.MethodeCalcul;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 * @author sophie
 *
 */
@Entity
@Table(name = PeriodeApplicationMethode.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.METHODECALCUL_ID, Jeu.ID_JPA, RefDataConstantes.STDTVARIABLE_ID, RefDataConstantes.COLUMN_DATEDEBUT_PAPPMETH,
    RefDataConstantes.COLUMN_DATEFIN_PAPPMETH}),
        indexes = {
            @Index(name = "pam_code_jeu_idx", columnList = Jeu.ID_JPA)
            ,

        @Index(name = "pam_stdtvar_idx", columnList = RefDataConstantes.STDTVARIABLE_ID)
            ,
            @Index(name = "pam_instr_idx", columnList = RefDataConstantes.METHODECALCUL_ID)

        })
public class PeriodeApplicationMethode implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.PAPPMETHODE_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.PERIODEAPPMETHODE_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = PeriodeApplicationMethode.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = MethodeCalcul.class)
    @JoinColumn(name = RefDataConstantes.METHODECALCUL_ID, referencedColumnName = MethodeCalcul.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private MethodeCalcul methodeCalcul;

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = RealNode.class)
    @JoinColumn(name = RefDataConstantes.STDTVARIABLE_ID, referencedColumnName = RealNode.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private RealNode realNode;

    @Column(nullable = false, unique = false, name = RefDataConstantes.COLUMN_DATEDEBUT_PAPPMETH)
    private LocalDate dateDebut;

    @Column(nullable = true, unique = false, name = RefDataConstantes.COLUMN_DATEFIN_PAPPMETH)
    private LocalDate dateFin;

    /**
     * empty constructor
     */
    public PeriodeApplicationMethode() {
        super();
    }

    /**
     * @param methodeCalcul
     * @param realNode
     * @param jeu
     * @param dateDebut
     * @param dateFin
     */
    public PeriodeApplicationMethode(MethodeCalcul methodeCalcul, Jeu code_jeu, RealNode realNode, LocalDate dateDebut, LocalDate dateFin) {
        super();
        this.methodeCalcul = methodeCalcul;
        this.jeu = code_jeu;
        this.realNode = realNode;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
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
     * @return the methodeCalcul
     */
    public MethodeCalcul getMethodeCalcul() {
        return this.methodeCalcul;
    }

    /**
     * @param methodeCalcul the methodeCalcul to set
     */
    public void setMethodeCalcul(MethodeCalcul methodeCalcul) {
        this.methodeCalcul = methodeCalcul;
    }

    /**
     * @return the realNode
     */
    public RealNode getRealNode() {
        return this.realNode;
    }

    /**
     * @param realNode the realNode to set
     */
    public void setRealNode(RealNode realNode) {
        this.realNode = realNode;
    }

}
