package org.cnrs.osuc.snot.dataset.fluxmeteo.colonne;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import org.cnrs.osuc.snot.dataset.impl.SNOTRecorder;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.Util;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;

/**
 *
 * @author ptcherniati
 */
public class ExpertColonne implements IExpertColonne {

    /**
     *
     */
    protected List<Column> colonnesDansOrdreFichier = new ArrayList<>();

    /**
     *
     */
    protected List<Column> colonnesDansDescripteur = new ArrayList<>();

    /**
     *
     */
    public ExpertColonne() {
    }

    /**
     *
     * @param colonnes
     */
    public ExpertColonne(List<Column> colonnes) {
        for (Column uneColonne : colonnes) {
            this.colonnesDansDescripteur.add(uneColonne);
        }
    }

    /**
     * <p>
     * test if all mandatory columns exist and at least one variable column </p>
     *
     * @param nbMandatoryColumns
     * @param nomsColonnes
     * @return true if all mandatory columns exist and at least one variable
     * column
     */
    @Override
    public boolean existeColoneVariable(int nbMandatoryColumns, String[] nomsColonnes) {
        if (nomsColonnes == null || nomsColonnes.length == 0) {
            return false;
        }
        if (nomsColonnes.length < nbMandatoryColumns) {
            return false;
        }
        for (int cpt = nbMandatoryColumns; cpt < nomsColonnes.length; cpt++) {
            if (!nomsColonnes[cpt].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Column> getColonnesDansDescripteur() {
        return this.colonnesDansDescripteur;
    }

    @Override
    public void setColonnesDansDescripteur(List<Column> colonnesDansDescripteur) {
        this.colonnesDansDescripteur = colonnesDansDescripteur;
    }

    @Override
    public List<Column> getColonnesDansOrdreFichier() {
        return this.colonnesDansOrdreFichier;
    }

    @Override
    public void setColonnesDansOrdreFichier(List<Column> colonnesDansOrdreFichier) {
        this.colonnesDansOrdreFichier = colonnesDansOrdreFichier;
    }

    /**
     * <p>
     * return the column of datatset descriptor with nom </p>
     * <p>
     * not case sensible </p>
     *
     * @param nom
     * @return the column of datatset descriptor with nom
     */
    @Override
    public Column getColumn(String nom) {
        String nomRecherche;
        if (Strings.isNullOrEmpty(nom)) {
            nomRecherche = "";
        }
        nomRecherche = nom.trim();
        for (Column column : this.getColonnesDansDescripteur()) {
            if (nomRecherche.equalsIgnoreCase(column.getName())) {
                return column;
            }
        }
        return null;
    }

    /**
     * <p>
     * return the index of column "nom" in colonnes</p>
     * <p>
     * not case sensible </p>
     *
     * @param nom
     * @param colonnes
     * @return the index of column "nom" in colonnes
     */
    public int getColumn(String nom, List<Column> colonnes) {
        String nomRecherche;
        if (Strings.isNullOrEmpty(nom)) {
            nomRecherche = "";
        }
        nomRecherche = nom.trim();
        int reponse = -1;
        if (colonnes != null && !colonnes.isEmpty()) {
            for (int cpt = 0; cpt < colonnes.size(); cpt++) {
                Column uneColonne = colonnes.get(cpt);
                if (nomRecherche.equalsIgnoreCase(uneColonne.getName())) {
                    reponse = cpt;
                    break;
                }
            }
        }
        return reponse;
    }

    /**
     * <p>
     * return the number of the quality index column for the column for name
     * "nom"</p>
     *
     * @param nom
     * @return the number of the quality index column for the column for name
     * "nom"
     */
    @Override
    public int getNoQualityColumn(String nom) {
        String nomRecherche;
        if (Strings.isNullOrEmpty(nom)) {
            nomRecherche = "";
        }
        nomRecherche = nom.trim();
        int no = -1;
        for (int i = 0; i < this.getColonnesDansOrdreFichier().size(); i++) {
            Column column = this.getColonnesDansOrdreFichier().get(i);
            if (column.getRefVariableName() != null && column.getRefVariableName().equalsIgnoreCase(nomRecherche)) {
                no = i;
                break;
            }
        }
        return no;
    }

    /**
     * <p>
     * return the index of the referenced column for the column of index i</p>
     *
     * @param i
     * @return return the index of the referenced column for the column of index
     * i
     */
    @Override
    public int getReferencedColumn(int i) {
        int reponse = -1;
        if (i <= this.getColonnesDansOrdreFichier().size()) {
            String referencedName = this.getColonnesDansOrdreFichier().get(i).getRefVariableName();
            if (referencedName != null) {
                reponse = this.getColumn(referencedName, this.getColonnesDansOrdreFichier());
            }
        }
        return reponse;
    }

    /**
     * <p>
     * sort the column as in the column name header</p>
     * <p>
     * fill colonnesDansOrdreFichier with the column as in the column name
     * header</p>
     * <p>
     * remove colonnesDansDescripteur columns</p>
     * <p>
     * add an error "INTITULE_INEXISTANT" in badsFormatsReport if the column
     * doesn't exists</p>
     *
     * @param nomsColonnes
     * @param noLigne
     * @param badsFormatsReport
     */
    @Override
    public void rangerColonnesCommeDansFichier(String[] nomsColonnes, long noLigne, BadsFormatsReport badsFormatsReport) {
        for (int cpt = 0; cpt < nomsColonnes.length; cpt++) {
            String columnName = nomsColonnes[cpt];
            if(Constantes.PROPERTY_CST_DATE_TYPE.equalsIgnoreCase(columnName) || Constantes.PROPERTY_CST_TIME_TYPE.equalsIgnoreCase(columnName) ){
                columnName = columnName.toLowerCase();
            }
            Column uneColonne = this.getColumn(columnName);
            if (uneColonne != null) {
                this.colonnesDansOrdreFichier.add(uneColonne);
                this.colonnesDansDescripteur.remove(uneColonne);
            } else {
                String messageErreur = String.format(SNOTRecorder.getSnotMessage(Util.INTITULE_INEXISTANT), noLigne, cpt + 1, columnName);
                BadExpectedValueException exception = new BadExpectedValueException(messageErreur);
                badsFormatsReport.addException(exception);
            }
        }
        variablesObligatoiresPresentes(noLigne, badsFormatsReport);
    }

    /**
     * <p>
     * vérifie que toutes les colonnes obligatoires sont présente dans la ligne
     * d'en-tête</p>
     *
     * @param noLigne
     * @param badsFormatsReport
     * @return
     */
    @Override
    public boolean variablesObligatoiresPresentes(long noLigne, BadsFormatsReport badsFormatsReport) {
        boolean ok = true;
        for (Column colonne : this.colonnesDansDescripteur) {
            if (!colonne.isNullable()) {
                ok = false;
                String messageErreur = String.format(SNOTRecorder.getSnotMessage(Util.VARIABLE_ABSENTE), noLigne, colonne.getFieldName());
                BadExpectedValueException exception = new BadExpectedValueException(messageErreur);
                badsFormatsReport.addException(exception);
            }
        }
        return ok;
    }
}
