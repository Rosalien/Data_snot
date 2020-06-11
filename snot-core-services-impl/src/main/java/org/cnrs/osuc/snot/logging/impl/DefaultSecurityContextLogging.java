package org.cnrs.osuc.snot.logging.impl;


import org.aspectj.lang.JoinPoint;
import org.inra.ecoinfo.mga.managedbean.IPolicyManager;
import org.inra.ecoinfo.utils.LoggerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class DefaultSecurityContextLogging {

    /**
     *
     * @param joinPoint
     */
    public void logSecurityContextBefore(JoinPoint joinPoint) {
        Logger LOGGER = LoggerFactory.getLogger("logging");

        LOGGER.info("{}", new LoggerObject(((IPolicyManager) joinPoint.getTarget()).getCurrentUserLogin(), null, "s'est déconnecté"));
    }
}
