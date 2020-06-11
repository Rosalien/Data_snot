package org.cnrs.osuc.snot.dataset.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import org.inra.ecoinfo.dataset.config.IDatasetConfiguration;
import org.inra.ecoinfo.dataset.versioning.entity.VersionFile;
import org.cnrs.osuc.snot.dataset.IRequestProperties;
import org.cnrs.osuc.snot.refdata.datatypevariableunite.DatatypeVariableUniteSnot;
import org.cnrs.osuc.snot.refdata.site.SiteSnot;
import org.cnrs.osuc.snot.utils.Constantes;
import org.cnrs.osuc.snot.utils.Util;
import org.inra.ecoinfo.utils.Column;
import org.inra.ecoinfo.utils.DateUtil;
import org.inra.ecoinfo.utils.exceptions.BadExpectedValueException;
import org.inra.ecoinfo.utils.exceptions.BadsFormatsReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author philippe
 *
 */
public class RequestProperties implements IRequestProperties {

    private static final String SORTEABLE_DATE_FORMAT = "yyyyMMdd";
    private static final String SORTEABLE_TIME_FORMAT = "HHmm";

    /**
     *
     */
    public Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
    private String frequence;
    private SiteSnot site = null;
    private SortedMap<String, Boolean> dates = new TreeMap();
    private SortedMap<String, SortedMap<String, Boolean>> localesDates = new TreeMap();
    private String commentaire;
    private String testDuplicateName;
    private String dateFormat;
    private IDatasetConfiguration datasetConfiguration;
    private String[] columnNames;
    private Map<Integer, Column> columns = new LinkedHashMap<>();
    private String[] uniteNames;
    Map<String, DatatypeVariableUniteSnot> variableTypeDonnees;

    List<String> lstDatasetNameColumns;
    /* utc date */

    /**
     *
     */
    protected LocalDateTime dateDeDebut;
    /* utc date */

    /**
     *
     */
    protected LocalDateTime dateDeFin;

    /**
     *
     */
    public RequestProperties() {
        super();

    }

    @Override
    public List<String> getLstDatasetNameColumns() {
        return lstDatasetNameColumns;
    }

    @Override
    public void setVariableTypeDonnees(Map<String, DatatypeVariableUniteSnot> variableTypeDeDonnees) {
        this.variableTypeDonnees = variableTypeDeDonnees;
    }

    @Override
    public Map<String, DatatypeVariableUniteSnot> getVariableTypeDonnees() {
        return variableTypeDonnees;
    }

    @Override
    public void setLstDatasetNameColumns(List<String> lstDatasetNameColumns) {
        this.lstDatasetNameColumns = lstDatasetNameColumns;
    }

    @Override
    public Column getColumn(int i) {
        return this.columns.get(i);
    }

    /*
     * Ajout MS le 18 mars 2014. Il manquait un no Ligne pour alimenter le message DATE_HORS_LIMITES La méthode addDate(Date date) est appelé à dispataitre
     */
    @Override
    public void addDate(LocalDateTime date, long noLigne) throws BadExpectedValueException {
        if (date == null || this.frequence == null || !Arrays.asList(Constantes.FREQUENCE_NAME).contains(this.frequence)) {
            return;
        }
        if (!Constantes.FREQUENCE_NAME[3].equals(this.getFrequence())) {
            this.addContinuesDates(date, noLigne);
        } else if (this.getFrequence() != null) {
            this.addDiscretDates(date, noLigne);
        }
    }

    @Override
    public String testDuplicateName() {
        return this.testDuplicateName;
    }

    @Override
    public String getCommentaire() {
        return this.commentaire;
    }

    @Override
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public LocalDateTime getDatedeDebut() {
        return this.dateDeDebut;
    }

    @Override
    public LocalDateTime getDateDeFin() {
        return this.dateDeFin;
    }

    @Override
    public void setDateDeFin(LocalDateTime dateDeFin) {
        this.dateDeFin = dateDeFin;
        if (this.frequence == null || !Arrays.asList(Constantes.FREQUENCE_NAME).contains(this.frequence)) {
            return;
        }
        if (!Constantes.FREQUENCE_NAME[3].equals(this.getFrequence())) {
            this.setContinueDateFin(dateDeFin);
        } else {
            this.setDiscretDateFin(dateDeFin);
        }
    }

    @Override
    public String getFrequence() {
        return this.frequence;
    }

    @Override
    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    /**
     *
     * @param version
     * @return
     */
    public String getNomDeFichier(VersionFile version) {
        return version.getDataset().buildDownloadFilename(this.datasetConfiguration);
    }

    @Override
    public SiteSnot getSite() {
        return this.site;
    }

    @Override
    public void setSite(SiteSnot site) {
        this.site = site;
    }

    @Override
    public void initDate() {
        if (this.frequence == null || !Arrays.asList(Constantes.FREQUENCE_NAME).contains(this.frequence)) {
            return;
        }
        if (!Constantes.FREQUENCE_NAME[3].equals(this.getFrequence())) {
            this.dates = new TreeMap();
        } else {
            this.localesDates = new TreeMap();
        }

    }

    @Override
    public void setDateDeDebut(LocalDateTime datedeDebut) {
        this.dateDeDebut = datedeDebut;
        this.initDate();
    }

    /**
     *
     * @param testDuplicateName
     */
    public void setTestDuplicateName(String testDuplicateName) {
        this.testDuplicateName = testDuplicateName;
    }

    @Override
    public void testDates(BadsFormatsReport badsFormatsReport) {
        if (this.frequence == null || !Arrays.asList(Constantes.FREQUENCE_NAME).contains(this.frequence)) {
            return;
        }
        if (!Constantes.FREQUENCE_NAME[3].equals(this.getFrequence())) {
            if (this.testContinuesDates(badsFormatsReport)) {
            }
        } else if (this.testDiscretDates(badsFormatsReport)) {
        }
    }

    /*
     * @Override public void testNomFichier(String originalNomFichier, BadsFormatsReport badsFormatsReport) { StringBuffer pattern = new
     * StringBuffer("^(.*)").append(IRequestProperties.CST_UNDERSCORE).append("(.*").append(IRequestProperties.CST_UNDERSCORE).append(".*)").append(IRequestProperties.CST_UNDERSCORE).append("(.*)")
     * .append(IRequestProperties.CST_UNDERSCORE).append("(.*)").append(IRequestProperties.CST_DOT).append(IRequestProperties.FORMAT_FILE).append("$"); Matcher matches = Pattern.compile(pattern.toString()).matcher(originalNomFichier); String originalSitePath
     * = ""; String originalDatatypeFrequence = ""; String originalDateDeDebut = ""; String originalDateDeFin = ""; if (!matches.matches()) { badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_NAME_FILE)); return; } else {
     * originalSitePath = matches.group(1); originalDatatypeFrequence = matches.group(2); originalDateDeDebut = matches.group(3); originalDateDeFin = matches.group(4); } StringBuffer nomFichier = new StringBuffer(); try {
     * nomFichier.append(site.getPath()).append(IRequestProperties.CST_UNDERSCORE); if (Constantes.FREQUENCE_NAME[0].equals(frequence)) { nomFichier.append(Constantes.DATATYPE_FREQUENCE_FLUX[0]).append(IRequestProperties.CST_UNDERSCORE);
     * nomFichier.append(Constantes.DATATYPE_FREQUENCE_FLUX[0]).append(IRequestProperties.CST_UNDERSCORE); nomFichier.append(DateUtil.getSimpleDateFormatDateUTCForFile().format(dateDeDebut)).append(IRequestProperties.CST_UNDERSCORE);
     * nomFichier.append(DateUtil.getSimpleDateFormatDateUTCForFile().format(dateDeFin)).append(IRequestProperties.CST_DOT).append(IRequestProperties.FORMAT_FILE); } else if (Constantes.FREQUENCE_NAME[1].equals(frequence)) {
     * nomFichier.append(Constantes.DATATYPE_FREQUENCE_FLUX[1]).append(IRequestProperties.CST_UNDERSCORE); nomFichier.append(DateUtil.getSimpleDateFormatDateUTCForFile().format(dateDeDebut)).append(IRequestProperties.CST_UNDERSCORE);
     * nomFichier.append(DateUtil.getSimpleDateFormatDateUTCForFile().format(dateDeFin)).append(IRequestProperties.CST_DOT).append(IRequestProperties.FORMAT_FILE); } else if (Constantes.FREQUENCE_NAME[2].equals(frequence)) {
     * nomFichier.append(Constantes.DATATYPE_FREQUENCE_FLUX[2]).append(IRequestProperties.CST_UNDERSCORE); nomFichier.append(DateUtil.getSimpleDateMoisFormatDateUTCForFile().format(dateDeDebut)).append(IRequestProperties.CST_UNDERSCORE);
     * nomFichier.append(DateUtil.getSimpleDateMoisFormatDateUTCForFile().format(dateDeFin)).append(IRequestProperties.CST_DOT).append(IRequestProperties.FORMAT_FILE); } Matcher matches2 = Pattern.compile(pattern.toString()).matcher(nomFichier); String
     * sitePath = ""; String datatypeFrequence = ""; String dateDeDebut = ""; String dateDeFin = ""; if (!matches2.matches()) { badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_NAME_FILE)); } else { sitePath = matches2.group(1);
     * datatypeFrequence = matches2.group(2); dateDeDebut = matches2.group(3).replace(IRequestProperties.SLASHES, IRequestProperties.CST_HYPHEN); dateDeFin = matches2.group(4).replace(IRequestProperties.SLASHES, IRequestProperties.CST_HYPHEN); } if
     * (!originalSitePath.equals(sitePath)) { badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_SITE_FILE_NAME)); } if (!originalDatatypeFrequence.equals(datatypeFrequence)) { badsFormatsReport.addException(new
     * BadExpectedValueException(Util.BAD_DATATYPE)); } if (!originalDateDeDebut.equals(dateDeDebut)) { badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_DATE_DEBUT)); } if (!originalDateDeFin.equals(dateDeFin)) {
     * badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_DATE_FIN)); } } catch (Exception e) { badsFormatsReport.addException(new BadExpectedValueException(Util.BAD_NAME_FILE)); } }
     */
    /**
     *
     * @param datasetConfiguration
     */
    public void setDatasetConfiguration(IDatasetConfiguration datasetConfiguration) {
        this.datasetConfiguration = datasetConfiguration;
    }

    @Override
    public String[] getColumnNames() {
        return this.columnNames;
    }

    @Override
    public void setColumnNames(String[] columnNames, List<Column> columns) {
        this.columnNames = columnNames;
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i];
            for (Column column : columns) {
                if (column.getName().equalsIgnoreCase(columnName)) {
                    this.columns.put(i, column);
                    break;
                }
            }
        }
    }

    @Override
    public String[] getuniteNames() {
        return this.uniteNames;

    }

    @Override
    public void setUniteNames(String[] uniteNames) {
        this.uniteNames = uniteNames;
    }

    /**
     *
     * @param date
     * @param noLigne
     * @throws BadExpectedValueException
     */
    protected void addDiscretDates(LocalDateTime date, long noLigne) throws BadExpectedValueException {
        String dateString = DateUtil.getUTCDateTextFromLocalDateTime(date, SORTEABLE_DATE_FORMAT);
        String timeString = DateUtil.getUTCDateTextFromLocalDateTime(date, SORTEABLE_TIME_FORMAT);
        if (!this.localesDates.containsKey(dateString)) {
            String message = "";
            message = String.format(Util.DATE_HORS_LIMITES, noLigne,
                    DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY_HH_MM),
                    DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                    DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
            );
            throw new BadExpectedValueException(message);
        }
        if (this.localesDates != null && date != null && this.localesDates.get(dateString).get(timeString) == null) {
            this.localesDates.get(dateString).put(timeString, true);
        }
    }

    /**
     *
     * @param date
     * @param noLigne
     * @return
     * @throws BadExpectedValueException
     */
    protected boolean addContinuesDates(LocalDateTime date, long noLigne) throws BadExpectedValueException {
        String dateString = DateUtil.getUTCDateTextFromLocalDateTime(date, Constantes.SORTEABLE_DATE_FORMAT);
        if (!this.dates.containsKey(dateString)) {
            String message = "";
            if (Constantes.FREQUENCE_NAME[0].equals(this.frequence)) {
                message = String.format(Util.DATE_HORS_LIMITES, noLigne,
                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY_HH_MM),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
                );
            } else if (Constantes.FREQUENCE_NAME[1].equals(this.frequence)) {
                message = String.format(Util.DATE_HORS_LIMITES, noLigne,
                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
                );
            } else if (Constantes.FREQUENCE_NAME[2].equals(this.frequence)) {
                message = String.format(Util.DATE_HORS_LIMITES, noLigne,
                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.MM_YYYY)
                );
            } else {
                message = String.format(Util.DATE_HORS_LIMITES, noLigne,
                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
                );
            }
            throw new BadExpectedValueException(message);
        }
        if (this.dates != null && date != null) {
            this.dates.put(dateString, true);
        }
        return false;
    }

    /**
     *
     * @param dateDeFin
     */
    protected void setDiscretDateFin(LocalDateTime dateDeFin) {
        LocalDateTime currentDate = dateDeDebut;
        if (this.dateDeDebut != null && dateDeFin != null && !dateDeDebut.isAfter(dateDeFin)) {
            LocalDateTime endDate = Optional.ofNullable(dateDeFin).map(d -> d.plusDays(1)).orElse(dateDeFin);
            String endDateToString = DateUtil.getUTCDateTextFromLocalDateTime(endDate, SORTEABLE_DATE_FORMAT);
            this.localesDates.headMap(endDateToString);
            while (currentDate.isBefore(endDate)) {
                final String dateString = DateUtil.getUTCDateTextFromLocalDateTime(currentDate, SORTEABLE_DATE_FORMAT);
                if (!this.localesDates.containsKey(dateString)) {
                    this.localesDates.put(dateString, new TreeMap());
                }
                currentDate = currentDate.plusDays(1);
            }
        } else {
            this.initDate();
        }
    }

    /**
     *
     * @param dateDeFin
     */
    protected void setContinueDateFin(LocalDateTime dateDeFin) {
        String sorteableDateFormat = Constantes.SORTEABLE_DATE_FORMAT;
        if (this.dateDeDebut != null && dateDeFin != null && !this.dateDeDebut.isAfter(dateDeFin)) {
            LocalDateTime endDate = dateDeFin.plusDays(1);
            LocalDateTime currentDate = dateDeDebut;
            this.dates.headMap(DateUtil.getUTCDateTextFromLocalDateTime(endDate, sorteableDateFormat));
            while (currentDate.isBefore(endDate)) {
                dates.computeIfAbsent(
                        DateUtil.getUTCDateTextFromLocalDateTime(currentDate, sorteableDateFormat),
                        k -> Boolean.FALSE
                );
                if (Constantes.FREQUENCE_NAME[1].equals(this.frequence)) {
                    currentDate = currentDate.plusDays(1);
                } else if (Constantes.FREQUENCE_NAME[2].equals(this.frequence)) {
                    currentDate = currentDate.plusMonths(1);
                } else if (Constantes.FREQUENCE_NAME[0].equals(this.frequence)) {
                    currentDate = currentDate.plusMinutes(30);
                } else {
                    break;
                }
            }
        } else {
            this.initDate();
        }
    }

    /**
     *
     * @param badsFormatsReport
     * @return
     */
    protected boolean testDiscretDates(BadsFormatsReport badsFormatsReport) {
        if (this.dateDeDebut == null || this.dateDeFin == null) {
            return true;
        }
        if (this.localesDates == null || this.localesDates.size() < 1) {
            badsFormatsReport.addException(new BadExpectedValueException(Util.PERIODE_NON_DEFINIE));
        }
        //Test sur la continuité des donnnées
//        for (String dateString : this.localesDates.keySet()) {
//            LocalDateTime date;
//            try {
//                date = DateUtil.readLocalDateTimeFromText(SORTEABLE_DATE_FORMAT, dateString);
//            } catch (DateTimeParseException ex) {
//                this.LOGGER.info(ex.getMessage());
//                continue;
//            }
//            if (this.localesDates.get(dateString).isEmpty()) {
//                badsFormatsReport.addException(
//                        new BadExpectedValueException(
//                                String.format(Util.DATE_MANQUANTE,
//                                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY_HH_MM),
//                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
//                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
//                                )));
//            }
//        }
        return false;
    }

    /**
     *
     * @param badsFormatsReport
     * @return
     */
    protected boolean testContinuesDates(BadsFormatsReport badsFormatsReport) {
        //String sorteableDateFormat = Constantes.SORTEABLE_DATE_FORMAT;
        if (this.dateDeDebut == null || this.dateDeFin == null) {
            return true;
        }
        if (this.dates == null || this.dates.size() < 1) {
            badsFormatsReport.addException(new BadExpectedValueException(Util.PERIODE_NON_DEFINIE));
        }
        for (String dateString : this.dates.keySet()) {
            LocalDateTime date;
            try {
                date = DateUtil.readLocalDateTimeFromText(Constantes.SORTEABLE_DATE_FORMAT, dateString);
            } catch (DateTimeParseException ex) {
                this.LOGGER.info(ex.getMessage());
                continue;
            }
            if (!this.dates.get(dateString)) {
                if (Constantes.FREQUENCE_NAME[0].equals(this.frequence)) {
                    badsFormatsReport.addException(new BadExpectedValueException(String.format(Util.DATE_MANQUANTE, 
                                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY_HH_MM),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
                    )));
                } else if (Constantes.FREQUENCE_NAME[1].equals(this.frequence)) {
                    badsFormatsReport.addException(new BadExpectedValueException(String.format(Util.DATE_MANQUANTE, 
                                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.DD_MM_YYYY),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.DD_MM_YYYY),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.DD_MM_YYYY)
                    )));
                } else if (Constantes.FREQUENCE_NAME[2].equals(this.frequence)) {
                    badsFormatsReport.addException(new BadExpectedValueException(String.format(Util.DATE_MANQUANTE,
                                        DateUtil.getUTCDateTextFromLocalDateTime(date, DateUtil.MM_YYYY),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeDebut, DateUtil.MM_YYYY),
                                        DateUtil.getUTCDateTextFromLocalDateTime(dateDeFin, DateUtil.MM_YYYY) 
                    )));
                }
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    protected SortedMap<String, Boolean> getDates() {
        return this.dates;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     *
     * @param dateFormat
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
