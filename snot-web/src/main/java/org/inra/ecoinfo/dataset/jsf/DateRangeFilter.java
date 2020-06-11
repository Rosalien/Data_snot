///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.inra.ecoinfo.dataset.jsf;
//
//import java.io.Serializable;
//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Locale;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.faces.bean.ApplicationScoped;
//import javax.inject.Named;
//import org.apache.commons.lang.StringUtils;
//
///**
// *
// * @author jbparoissien
// */
//@Named
//@ApplicationScoped
//
//public class DateRangeFilter implements Serializable {
//
//  public boolean filterByDate(Object value, Object filter, Locale locale) {
//        // it fails before calling this method
//        String filterText = (filter == null) ? null : filter.toString().trim();
//        if(StringUtils.isEmpty(filterText)) {
//            return true;
//        }
//        if(value == null) {
//            return false;
//        }
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        Date filterDate;
//        try {
//            filterDate = (Date) df.parse(filterText);
//        } catch (ParseException e) {
//            return false;
//        }
//      java.util.Date dateFrom = null;
//      java.util.Date dateTo;
//        return filterDate.after(dateFrom) &&  filterDate.before(dateTo);
//    } 
//}