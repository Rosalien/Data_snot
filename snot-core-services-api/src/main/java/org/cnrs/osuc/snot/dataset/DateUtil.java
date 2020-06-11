package org.cnrs.osuc.snot.dataset;


/**
 *
 * @author ptcherniati
 */
public class DateUtil {
//
//    /**
//     *
//     * @param date
//     * @param pas
//     * @return
//     */
//    static public Date ajouterNJours(Date date, int pas) {
//        Calendar cfin = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        cfin.setTime(date);
//        cfin.add(Calendar.DAY_OF_YEAR, pas);
//        Date uneDate = new Date(cfin.getTimeInMillis());
//        return uneDate;
//    }
//
//    /**
//     *
//     * @param date
//     * @param pas
//     * @return
//     */
//    static public Date ajouterNMinutes(Date date, int pas) {
//        Calendar cfin = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        cfin.setTime(date);
//        cfin.add(Calendar.MINUTE, pas);
//        Date uneDate = new Date(cfin.getTimeInMillis());
//        return uneDate;
//    }
//
//    /**
//     *
//     * @param date
//     * @param pas
//     * @return
//     */
//    static public Date ajouterNMois(Date date, int pas) {
//        Calendar cfin = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        cfin.setTime(date);
//        cfin.add(Calendar.MONTH, pas);
//        Date uneDate = new Date(cfin.getTimeInMillis());
//        return uneDate;
//    }
//
//    /**
//     *
//     * @param date
//     * @return
//     */
//    static public Date arrondirExces(Date date) {
//        Date arrondir = date;
//        Calendar fin = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        fin.setTime(arrondir);
//        int heure = fin.get(Calendar.HOUR_OF_DAY);
//        int minute = fin.get(Calendar.MINUTE);
//        if (heure != 0 || minute != 0) {
//            fin.add(Calendar.DAY_OF_MONTH, 1);
//            fin.set(Calendar.HOUR_OF_DAY, 0);
//            fin.set(Calendar.MINUTE, 0);
//            fin.set(Calendar.SECOND, 0);
//            fin.set(Calendar.MILLISECOND, 0);
//            arrondir = new Date(fin.getTimeInMillis());
//        }
//        return arrondir;
//    }
//
//    /**
//     *
//     * @param date
//     * @param heure
//     * @return
//     */
//    public static Date concatener(Date date, Date heure) {
//        Calendar cHeure = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        cHeure.setTime(heure);
//        Calendar cDate = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        cDate.setTime(date);
//        Calendar cNewDate = Calendar.getInstance();
//        cNewDate.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH),
//                cDate.get(Calendar.DAY_OF_MONTH), cHeure.get(Calendar.HOUR_OF_DAY),
//                cHeure.get(Calendar.MINUTE), 0);
//        cNewDate.set(Calendar.MILLISECOND, 0);
//        Date newDate = new Date(cNewDate.getTimeInMillis());
//        return newDate;
//    }
//
//    /**
//     *
//     * @param date
//     * @param debut
//     * @param fin
//     * @param zeroFin
//     * @return
//     */
//    static public boolean dateDansIntervalle(Date date, Date debut, Date fin, boolean zeroFin) {
//        boolean ok = false;
//        debut = DateUtil.mettreJourAZeroHeure(debut);
//        if (zeroFin) {
//            fin = DateUtil.mettreJourAZeroHeure(fin);
//        }
//        if (date.equals(debut) || date.equals(fin) || date.after(debut) && date.before(fin)) {
//            ok = true;
//        }
//        return ok;
//    }
//
//    /**
//     *
//     * @param date
//     * @return
//     */
//    static public String dateUTCToStringCourt(Date date) {
//        SimpleDateFormat dateFormat = org.inra.ecoinfo.utils.DateUtil.getSimpleDateFormatDateUTC();
//        String dateChaine = dateFormat.format(date);
//        return dateChaine;
//    }
//
//    /**
//     *
//     * @param date
//     * @return
//     */
//    static public String dateToStringHeure(Date date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        String dateChaine = dateFormat.format(date);
//        return dateChaine;
//    }
//
//    /**
//     *
//     * @param date
//     * @return
//     */
//    static public String dateToStringLong(Date date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String dateChaine = dateFormat.format(date);
//        return dateChaine;
//    }
//
//    /**
//     *
//     * @param date
//     * @param pas
//     * @return
//     */
//    static public Date mettreDerniereDateDuJour(Date date, int pas) {
//        Date uneDate = DateUtil.mettreJourAZeroHeure(date);
//        Calendar calendar = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        calendar.setTime(uneDate);
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        calendar.add(Calendar.MINUTE, -pas);
//        Date laDateCorrigee = new Date(calendar.getTimeInMillis());
//        return laDateCorrigee;
//    }
//
//    /**
//     *
//     * @param uneDate
//     * @return
//     */
//    static public Date mettreHeureAZero(Date uneDate) {
//        Calendar calendar = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        calendar.setTime(uneDate);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date laDate = new Date(calendar.getTimeInMillis());
//        return laDate;
//    }
//
//    /**
//     *
//     * @param uneDate
//     * @return
//     */
//    static public Date mettreJourAZeroHeure(Date uneDate) {
//        Calendar calendar = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        calendar.setTime(uneDate);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date laDate = new Date(calendar.getTimeInMillis());
//        return laDate;
//    }
//
//    /**
//     *
//     * @param maDate
//     * @return
//     * @throws ParseException
//     */
//    static public Date stringToDateUTC(String maDate) throws ParseException {
//        SimpleDateFormat dateFormat = org.inra.ecoinfo.utils.DateUtil.getSimpleDateFormatDateUTC();
//        Date date = dateFormat.parse(maDate);
//        return date;
//    }
//
//    /**
//     *
//     * @param monHeure
//     * @return
//     * @throws ParseException
//     */
//    static public Time stringToTime(String monHeure) throws ParseException {
//        SimpleDateFormat dateFormat = org.inra.ecoinfo.utils.DateUtil.getSimpleDateFormatTimeUTC();
//        Date heure = dateFormat.parse(monHeure);
//        Calendar calendar = org.inra.ecoinfo.utils.DateUtil.calendarLocale();
//        calendar.setTime(heure);
//        Time sonHeure = new Time(calendar.getTimeInMillis());
//        return sonHeure;
//    }
//
//    /**
//     *
//     * @param monHeure
//     * @return
//     * @throws ParseException
//     */
//    static public Time stringToUTCTime(String monHeure) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        Date heure = dateFormat.parse(monHeure);
//        Calendar calendar = org.inra.ecoinfo.utils.DateUtil.calendarUTC();
//        calendar.setTime(heure);
//        Time sonHeure = new Time(calendar.getTimeInMillis());
//        return sonHeure;
//    }
}
