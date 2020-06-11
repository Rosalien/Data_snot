/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.cnrs.osuc.snot.refdata.RefDataConstantes;


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = ListeValeurInfo.TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_NOM_LSTVALINFO}))
public class ListeValeurInfo implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.LSTVALINFO_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.LSTVALINFO_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = ListeValeurInfo.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = true, name = RefDataConstantes.COLUMN_NOM_LSTVALINFO, length = 50)
    private String nom;

    @OneToMany(mappedBy = RefDataConstantes.NAME_ATTRIBUT_LISTEVALINFO, cascade = {PERSIST, MERGE, REFRESH, REMOVE})
    private List<ItemListe> lstItemListe = new LinkedList<>();

    /**
     * empty constructor
     */
    public ListeValeurInfo() {
        super();
    }

    /**
     * @param nom
     */
    public ListeValeurInfo(String nom) {
        super();
        this.nom = nom;
    }

    /**
     *
     * @param item
     */
    public void addItemListe(ItemListe item) {
        this.lstItemListe.add(item);
        item.setListeValeurInfo(this);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the lstItemListe
     */
    public List<ItemListe> getLstItemListe() {
        return this.lstItemListe;
    }

    /**
     * @param lstItemListe
     *            the lstItemListe to set
     */
    public void setLstItemListe(List<ItemListe> lstItemListe) {
        this.lstItemListe = lstItemListe;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * @param nom
     *            the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}
