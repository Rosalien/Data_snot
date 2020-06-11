/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.meteo;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_j;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_j;
import org.cnrs.osuc.snot.synthesis.meteoj.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.meteoj.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Meteo_jSynthesisDAO extends AbstractMeteoSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurMeteo_j, MesureMeteo_j> {

    @Override
    Class<ValeurMeteo_j> getValueClass() {
        return ValeurMeteo_j.class;
    }

    @Override
    Class<MesureMeteo_j> getMeasureClass() {
        return MesureMeteo_j.class;
    }

}
