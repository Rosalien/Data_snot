package org.cnrs.osuc.snot.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.cnrs.osuc.snot.dataset.infoComplementaire.entity.ValeurInformationComplementaire;

/**
 *
 * @author ptcherniati
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractValeur implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id_valeur;

    /**
     *
     */
    @OneToMany(mappedBy = "valeurDecoree", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.EXTRA)
    protected List<ValeurInformationComplementaire> complements;

    /**
     *
     */
    public AbstractValeur() {
        super();
    }

    /**
     *
     * @param info
     */
    public void ajouterInfoComplementaire(ValeurInformationComplementaire info) {
        if (this.complements == null) {
            this.complements = new ArrayList<>();
        }
        this.complements.add(info);
    }

    /**
     *
     * @return
     */
    public List<ValeurInformationComplementaire> getComplements() {
        return this.complements;
    }

    /**
     *
     * @param complements
     */
    public void setComplements(List<ValeurInformationComplementaire> complements) {
        this.complements = complements;
    }

    /**
     *
     * @return
     */
    public Long getId_valeur() {
        return this.id_valeur;
    }
}
