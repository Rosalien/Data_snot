package org.cnrs.osuc.snot.logging.impl;


import org.aspectj.lang.JoinPoint.StaticPart;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.utils.LoggerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class DefaultIdentificationLogging {

    /**
     *
     * @param staticPart
     * @param result
     */
    public void logCheckPasswordExit(StaticPart staticPart, Object result) {
        Logger LOGGER = LoggerFactory.getLogger("logging");
        LOGGER.info("{}", new LoggerObject(((Utilisateur) result).getLogin(), null, "s'est connecté"));
    }
}
