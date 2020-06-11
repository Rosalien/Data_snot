package org.cnrs.osuc.snot.dataset.fluxmeteo.entete;

import org.cnrs.osuc.snot.utils.Constantes;

/**
 *
 * @author ptcherniati
 */
@SuppressWarnings("rawtypes")
public class SpecialisteEnteteMeteo extends AbstractVerificateurEnteteFluxMeteo {

    /**
     *
     */
    public SpecialisteEnteteMeteo() {
        this.datatypeName = Constantes.THEME_METEO;
    }
}
