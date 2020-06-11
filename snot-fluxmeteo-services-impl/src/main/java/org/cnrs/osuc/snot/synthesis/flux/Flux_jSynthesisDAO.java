/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.flux;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_j;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_jTour;
import org.cnrs.osuc.snot.synthesis.fluxj.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.fluxj.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Flux_jSynthesisDAO extends AbstractFluxSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurFlux_jTour, MesureFlux_j> {

    @Override
    Class<ValeurFlux_jTour> getValueClass() {
        return ValeurFlux_jTour.class;
    }

    @Override
    Class<MesureFlux_j> getMeasureClass() {
        return MesureFlux_j.class;
    }

}
