package org.cnrs.osuc.snot.dataset;

import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.mga.configuration.PatternConfigurator;

/**
 * @author philippe
 * 
 */
public interface IRequestPropertiesSnot {

    /**
     *
     */
    String DOT         = Constantes.CST_DOT;

    /**
     *
     */
    String UNDERSCORE  = Constantes.CST_UNDERSCORE;

    /**
     *
     */
    String SLASHES     = PatternConfigurator.ANCESTOR_SEPARATOR;

    /**
     *
     */
    String TIRET       = Constantes.CST_HYPHEN;

    /**
     *
     */
    String FORMAT_FILE = Constantes.FORMAT_FILE;

    /**
     *
     * @return
     */
    String testDuplicateName();
}
