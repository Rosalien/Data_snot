package org.cnrs.osuc.snot.logging.entity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;

/**
 *
 * @author ptcherniati
 */
public interface ILogging {

    /**
     *
     * @param joinPoint
     */
    void logMethodEntry(JoinPoint joinPoint);

    /**
     *
     * @param staticPart
     * @param result
     */
    void logMethodExit(StaticPart staticPart, Object result);

}
