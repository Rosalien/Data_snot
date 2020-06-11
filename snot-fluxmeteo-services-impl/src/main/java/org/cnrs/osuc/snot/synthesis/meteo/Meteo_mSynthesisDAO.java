/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.meteo;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_m;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_m;
import org.cnrs.osuc.snot.synthesis.meteom.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.meteom.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Meteo_mSynthesisDAO extends AbstractMeteoSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurMeteo_m, MesureMeteo_m> {

    @Override
    Class<ValeurMeteo_m> getValueClass() {
        return ValeurMeteo_m.class;
    }

    @Override
    Class<MesureMeteo_m> getMeasureClass() {
        return MesureMeteo_m.class;
    }

}
