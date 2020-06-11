package org.cnrs.osuc.snot.logging.impl;


import org.aspectj.lang.JoinPoint.StaticPart;
import org.inra.ecoinfo.notifications.entity.Notification;
import org.inra.ecoinfo.utils.LoggerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class DefaultNotificationLogging {

    /**
     *
     * @param staticPart
     * @param result
     */
    public void logAddNotificationExit(StaticPart staticPart, Object result) {
        Logger LOGGER = LoggerFactory.getLogger("logging");
        LOGGER.info("{}", new LoggerObject(
                (Notification) ((org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint) staticPart)
                        .getArgs()[0]));
    }
}
