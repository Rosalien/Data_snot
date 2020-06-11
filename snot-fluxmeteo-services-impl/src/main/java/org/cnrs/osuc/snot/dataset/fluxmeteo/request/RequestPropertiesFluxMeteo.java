package org.cnrs.osuc.snot.dataset.fluxmeteo.request;

import java.util.List;
import org.cnrs.osuc.snot.dataset.fluxmeteo.colonne.ExpertColonne;
import org.cnrs.osuc.snot.dataset.fluxmeteo.session.IRequestPropertiesFluxMeteo;
import org.cnrs.osuc.snot.dataset.impl.RequestProperties;
import org.inra.ecoinfo.utils.Column;

/**
 *
 * @author ptcherniati
 */
public class RequestPropertiesFluxMeteo extends RequestProperties implements IRequestPropertiesFluxMeteo {

    /**
     *
     */
    protected boolean verifierSequenceDate = true;

    ExpertColonne expertColonne;

    @Override
    public Column getColumn(int i) {
        return this.expertColonne.getColonnesDansOrdreFichier().get(i);
    }

    /**
     *
     * @return
     */
    public ExpertColonne getExpertColonne() {
        return this.expertColonne;
    }

    /**
     *
     * @param expertColonne
     */
    public void setExpertColonne(ExpertColonne expertColonne) {
        this.expertColonne = expertColonne;
    }

    /**
     *
     * @return
     */
    public List<Column> getColums() {
        return this.expertColonne.getColonnesDansOrdreFichier();
    }


    @Override
    public boolean isVerifierSequenceDate() {
        return this.verifierSequenceDate;
    }

    @Override
    public void setVerifierSequenceDate(boolean verifierSequenceDate) {
        this.verifierSequenceDate = verifierSequenceDate;
    }

}
