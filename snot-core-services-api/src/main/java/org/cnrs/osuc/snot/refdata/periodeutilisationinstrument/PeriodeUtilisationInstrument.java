/**
 *
 */
package org.cnrs.osuc.snot.refdata.periodeutilisationinstrument;

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
import org.cnrs.osuc.snot.refdata.instrument.Instrument;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 * @author sophie
 *
 */
@Entity
@Table(name = PeriodeUtilisationInstrument.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.INSTRUMENT_ID, Jeu.ID_JPA, RefDataConstantes.STDTVARIABLE_ID, RefDataConstantes.COLUMN_DATEDEBUT_PUTILINSTR,
    RefDataConstantes.COLUMN_DATEFIN_PUILINSTR}),
        indexes = {
            @Index(name = "pam_code_jeu_idx", columnList = Jeu.ID_JPA)
            ,
            @Index(name = "pui_stdtvar_idx", columnList = RefDataConstantes.STDTVARIABLE_ID)
            ,
            @Index(name = "put_instr_idx", columnList = RefDataConstantes.INSTRUMENT_ID)
        })
public class PeriodeUtilisationInstrument implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.PUTILINSTRUMENT_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.PERIODEUTILINSTRUMENT_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = PeriodeUtilisationInstrument.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = Instrument.class)
    @JoinColumn(name = RefDataConstantes.INSTRUMENT_ID, referencedColumnName = Instrument.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Instrument instrument;

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = RealNode.class)
    @JoinColumn(name = RefDataConstantes.STDTVARIABLE_ID, referencedColumnName = RealNode.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private RealNode realNode;

    @Column(nullable = false, unique=false, name = RefDataConstantes.COLUMN_DATEDEBUT_PUTILINSTR)
    private LocalDate dateDebut;

    @Column(nullable = true, unique=false, name = RefDataConstantes.COLUMN_DATEFIN_PUILINSTR)
    private LocalDate dateFin;

    /**
     * empty constructor
     */
    public PeriodeUtilisationInstrument() {
        super();
    }

    /**
     * @param instrument
     * @param realNode
     * @param jeu
     * @param dateDebut
     * @param dateFin
     */
    public PeriodeUtilisationInstrument(Instrument instrument, Jeu code_jeu, RealNode realNode, LocalDate dateDebut, LocalDate dateFin) {
        super();
        this.instrument = instrument;
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
     * @return the instrument
     */
    public Instrument getInstrument() {
        return this.instrument;
    }

    /**
     * @param instrument the instrument to set
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    /**
     * @return the siteThemeDatatypeVariable
     */
    public RealNode getRealNode() {
        return this.realNode;
    }

    /**
     * @param realNode
     */
    public void setRealNode(RealNode realNode) {
        this.realNode = realNode;
    }

}
