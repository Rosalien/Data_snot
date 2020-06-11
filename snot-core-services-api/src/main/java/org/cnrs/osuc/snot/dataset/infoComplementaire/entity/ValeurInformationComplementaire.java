package org.cnrs.osuc.snot.dataset.infoComplementaire.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.cnrs.osuc.snot.dataset.AbstractValeur;
import org.cnrs.osuc.snot.dataset.infoComplementaire.dto.ValeurComplementDTO;
import org.cnrs.osuc.snot.refdata.informationcomplementaire.InformationComplementaire;
import org.inra.ecoinfo.localization.ILocalizationManager;
import org.inra.ecoinfo.utils.exceptions.BusinessException;

/**
 *
 * @author ptcherniati
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "valeur_information_complementaire")
public abstract class ValeurInformationComplementaire implements Serializable {

    /**
     *
     */
    public static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.dataset.infoComplementaire.messages";

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Transient
    protected ILocalizationManager localizationManager;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id_info;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_valeur")
    protected AbstractValeur valeurDecoree;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_identite")
    protected InformationComplementaire identite;

    /**
     *
     */
    public ValeurInformationComplementaire() {
        super();
    }

    /**
     *
     * @return
     */
    public int getId_info() {
        return this.id_info;
    }

    /**
     *
     * @return
     */
    public InformationComplementaire getIdentite() {
        return this.identite;
    }

    /**
     *
     * @param identite
     */
    public void setIdentite(InformationComplementaire identite) {
        this.identite = identite;
    }

    /**
     *
     * @return
     */
    public AbstractValeur getValeurDecoree() {
        return this.valeurDecoree;
    }

    /**
     *
     * @param valeurDecoree
     */
    public void setValeurDecoree(AbstractValeur valeurDecoree) {
        this.valeurDecoree = valeurDecoree;
    }

    /**
     *
     * @param dto
     * @throws BusinessException
     */
    public abstract void rangerComplementDTO(ValeurComplementDTO dto) throws BusinessException;

    /**
     *
     * @param localizationManager
     */
    public void setLocalizationManager(ILocalizationManager localizationManager) {
        this.localizationManager = localizationManager;
    }

    /**
     *
     * @return
     */
    abstract public String valeurToString();
}
