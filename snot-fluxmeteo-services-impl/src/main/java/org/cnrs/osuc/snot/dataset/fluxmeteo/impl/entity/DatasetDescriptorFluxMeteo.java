package org.cnrs.osuc.snot.dataset.fluxmeteo.impl.entity;

import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DatasetDescriptor;

/**
 *
 * @author ptcherniati
 */
public class DatasetDescriptorFluxMeteo extends DatasetDescriptor {

    private int enTete;
    private String frequence;

    /**
     *
     * @param nom
     * @return
     */
    public Column getColumn(String nom) {
        for (Column column : this.getColumns()) {
            if (column.getName().equals(nom)) {
                return column;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int getEnTete() {
        return this.enTete;
    }

    /**
     *
     * @param enTete
     */
    public void setEnTete(int enTete) {
        this.enTete = enTete;
    }

    /**
     *
     * @param enTete
     */
    public void setEnTete(String enTete) {
        this.enTete = Integer.parseInt(enTete);
    }

    /**
     *
     * @return
     */
    public String getFrequence() {
        return this.frequence;
    }

    /**
     *
     * @param frequence
     */
    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    /*
     * public int getReferencedColumn(int i) { if (i > getColumns().size()) return -1; String referencedName = getColumns().get(i).getRefVariableName(); if (referencedName != null) return getColumns().indexOf(getColumn(referencedName)); return -1; }
     */
    /** **/
    /* Ajout Monique juillet 2013 */
    /**
     * @param nom
     * @return  **/
    public int getNoQualityColumn(String nom) {
        int no = -1;
        for (int i = 0; i < this.getColumns().size(); i++) {
            Column column = this.getColumns().get(i);
            if (column.getRefVariableName() != null && column.getRefVariableName().equalsIgnoreCase(nom)) {
                no = i;
                break;
            }
        }
        return no;
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
