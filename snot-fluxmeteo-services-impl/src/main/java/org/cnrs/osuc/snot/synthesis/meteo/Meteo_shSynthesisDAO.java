/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.synthesis.meteo;

import org.cnrs.osuc.snot.dataset.meteo.entity.MesureMeteo_sh;
import org.cnrs.osuc.snot.dataset.meteo.entity.ValeurMeteo_sh;
import org.cnrs.osuc.snot.synthesis.meteosh.SynthesisDatatype;
import org.cnrs.osuc.snot.synthesis.meteosh.SynthesisValue;

/**
 *
 * @author ptchernia
 */
public class Meteo_shSynthesisDAO extends AbstractMeteoSynthesisDAO<SynthesisValue, SynthesisDatatype, ValeurMeteo_sh, MesureMeteo_sh> {

    @Override
    Class<ValeurMeteo_sh> getValueClass() {
        return ValeurMeteo_sh.class;
    }

    @Override
    Class<MesureMeteo_sh> getMeasureClass() {
        return MesureMeteo_sh.class;
    }

}
