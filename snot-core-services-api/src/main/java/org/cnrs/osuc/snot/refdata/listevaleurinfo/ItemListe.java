/**
 *
 */
package org.cnrs.osuc.snot.refdata.listevaleurinfo;

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


/**
 * @author sophie
 * 
 */
@Entity
@Table(name = ItemListe.TABLE_NAME, 
        uniqueConstraints = @UniqueConstraint(columnNames = {RefDataConstantes.COLUMN_LIBELLE_ITEMLST, RefDataConstantes.COLUMN_NOTE_ITEMLST, RefDataConstantes.LSTVALINFO_ID}),indexes = 
                {@Index(name = "il_lvalinf_idx", columnList = RefDataConstantes.LSTVALINFO_ID)})
public class ItemListe implements Serializable {

    /**
     *
     */
    public static final String ID_JPA = RefDataConstantes.ITEMLST_ID;

    /**
     *
     */
    public static final String TABLE_NAME = RefDataConstantes.ITEMLST_TABLE_NAME;
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = ItemListe.ID_JPA)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false, unique = false, name = RefDataConstantes.COLUMN_LIBELLE_ITEMLST, length = 50)
    private String libelle;

    @Column(nullable = false, unique = false, name = RefDataConstantes.COLUMN_NOTE_ITEMLST, length = 50)
    private String note;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, targetEntity = ListeValeurInfo.class)
    @JoinColumn(name = RefDataConstantes.LSTVALINFO_ID, referencedColumnName = ListeValeurInfo.ID_JPA, nullable = false)
    private ListeValeurInfo listeValeurInfo;

    /**
     * empty constructor
     */
    public ItemListe() {
        super();
    }

    /**
     * @param libelle
     * @param note
     */
    public ItemListe(String libelle, String note) {
        super();
        this.libelle = libelle;
        this.note = note;
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
     * @return the libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     * @param libelle
     *            the libelle to set
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * @return the listeValeurInfo
     */
    public ListeValeurInfo getListeValeurInfo() {
        return this.listeValeurInfo;
    }

    /**
     * @param listeValeurInfo
     *            the listeValeurInfo to set
     */
    public void setListeValeurInfo(ListeValeurInfo listeValeurInfo) {
        this.listeValeurInfo = listeValeurInfo;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @param note
     *            the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

}
