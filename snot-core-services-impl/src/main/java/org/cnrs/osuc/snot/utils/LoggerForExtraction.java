/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cnrs.osuc.snot.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import org.apache.commons.io.FileUtils;
import org.inra.ecoinfo.identification.entity.Utilisateur;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class LoggerForExtraction {
    private static final String EXTRACTION_LOG_PATTERN = "%s : extraction de %s";
    private static final String LOG_PATTERN = "%s;%s;%s;%s;%s;\"%s\"";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerForExtraction.class.getName());

    /**
     *
     * @param utilisateur
     * @param file
     */
    public static void logRequest(Utilisateur utilisateur, File file) {
        final LocalDateTime date = LocalDateTime.now();
        final String dateComplete = DateUtil.getUTCDateTextFromLocalDateTime(LocalDateTime.now(), DateUtil.DD_MM_YYYY_HH_MM_SS);
        final String dateString =  DateUtil.getUTCDateTextFromLocalDateTime(LocalDateTime.now(), DateUtil.DD_MM_YYYY);
        final String timeString =  DateUtil.getUTCDateTextFromLocalDateTime(LocalDateTime.now(), DateUtil.HH_MM_SS);
        final String message = String.format(EXTRACTION_LOG_PATTERN, dateComplete, utilisateur.getLogin());
        printLine(file, dateString, timeString, utilisateur);
    }

    private static void printLine(File file, final String dateString, final String timeString, Utilisateur utilisateur) {
        try {
            Constantes.LOGGER_FOR_EXTRACTION.info(String.format(LOG_PATTERN, dateString, timeString, utilisateur.getLogin(), utilisateur.getNom(), utilisateur.getEmail(), FileUtils.readFileToString(file, Utils.ENCODING_ISO_8859_15)));
        } catch (IOException ex) {
            LOGGER.error("file not found", ex);
        }
    }
}