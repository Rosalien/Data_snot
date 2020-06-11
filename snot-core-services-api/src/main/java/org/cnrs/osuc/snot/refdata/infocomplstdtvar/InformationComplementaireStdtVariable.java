/**
 *
 */
package org.cnrs.osuc.snot.refdata.infocomplstdtvar;

import java.io.Serializable;
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
import org.cnrs.osuc.snot.refdata.RefDataConstantes;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.cnrs.osuc.snot.refdata.jeu.Jeu;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.inra.ecoinfo.mga.business.composite.RealNode;

/**
 * @author sophie
 *
 */
@Entity
@Table(name = InformationComplementaireStdtVariable.TABLE_NAME,
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.STDTVARIABLE_ID, RefDataConstantes.INFOCOMPL_ID, Jeu.ID_JPA}), indexes = {
            @Index(name = "icstdv_sdtv_idx", columnList = RefDataConstantes.STDTVARIABLE_ID)
            ,
            @Index(name = "icstdv_infcplt_idx", columnList = RefDataConstantes.INFOCOMPL_ID)
            ,
        @Index(name = "icstdv_code_jeu_idx", columnList = Jeu.ID_JPA)})
public class InformationComplementaireStdtVariable implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.INFOCOMPLSTDTVAR_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.INFOCOMPLSTDTVAR_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = InformationComplementaireStdtVariable.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = RealNode.class)
    @JoinColumn(name = RefDataConstantes.STDTVARIABLE_ID, nullable = false, referencedColumnName = RealNode.ID_JPA)
    private RealNode realNode;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = InformationComplementaire.class)
    @JoinColumn(name = RefDataConstantes.INFOCOMPL_ID, nullable = false, referencedColumnName = InformationComplementaire.ID_JPA)
    private InformationComplementaire infoComplementaire;

    @ManyToOne(optional = false, targetEntity = Jeu.class)
    @JoinColumn(name = Jeu.ID_JPA, referencedColumnName = Jeu.ID_JPA, nullable = false)
    @LazyToOne(LazyToOneOption.PROXY)
    private Jeu jeu;

    /**
     * empty constructor
     */
    public InformationComplementaireStdtVariable() {
        super();
    }

    /**
     * @param realNode
     * @param infoComplementaire
     */
    public InformationComplementaireStdtVariable(Jeu jeu,RealNode realNode, InformationComplementaire infoComplementaire) {
        super();
        this.jeu = jeu;
        this.realNode = realNode;
        this.infoComplementaire = infoComplementaire;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
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
     * @return the infoComplementaire
     */
    public InformationComplementaire getInfoComplementaire() {
        return this.infoComplementaire;
    }

    /**
     * @param infoComplementaire the infoComplementaire to set
     */
    public void setInfoComplementaire(InformationComplementaire infoComplementaire) {
        this.infoComplementaire = infoComplementaire;
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
