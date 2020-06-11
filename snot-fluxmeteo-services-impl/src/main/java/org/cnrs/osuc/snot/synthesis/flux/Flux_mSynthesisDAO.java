/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.flux;

import org.cnrs.osuc.snot.dataset.flux.entity.MesureFlux_m;
import org.cnrs.osuc.snot.dataset.flux.entity.ValeurFlux_mTour;
import org.cnrs.osuc.snot.synthesis.fluxm.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.fluxm.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Flux_mSynthesisDAO extends AbstractFluxSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurFlux_mTour, MesureFlux_m> {

    @Override
    Class<ValeurFlux_mTour> getValueClass() {
        return ValeurFlux_mTour.class;
    }

    @Override
    Class<MesureFlux_m> getMeasureClass() {
        return MesureFlux_m.class;
    }

}
