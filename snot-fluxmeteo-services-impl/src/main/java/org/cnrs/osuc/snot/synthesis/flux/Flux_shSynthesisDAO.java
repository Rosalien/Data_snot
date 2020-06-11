/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.flux;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_sh;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_shTour;
import org.cnrs.osuc.snot.synthesis.fluxsh.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.fluxsh.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Flux_shSynthesisDAO extends AbstractFluxSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurFlux_shTour, MesureFlux_sh> {

    @Override
    Class<ValeurFlux_shTour> getValueClass() {
        return ValeurFlux_shTour.class;
    }

    @Override
    Class<MesureFlux_sh> getMeasureClass() {
        return MesureFlux_sh.class;
    }

}
