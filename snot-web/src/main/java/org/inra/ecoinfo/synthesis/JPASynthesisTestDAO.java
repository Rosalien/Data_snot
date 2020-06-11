///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.inra.ecoinfo.synthesis;
//
//import com.Ostermiller.util.CSVParser;
//import java.io.File;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.time.temporal.ChronoUnit;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.stream.Stream;
//import org.inra.ecoinfo.synthesis.test.SynthesisValue;
//import org.inra.ecoinfo.synthesis.test.SynthesisDatatype;
//
///**
// *
// * @author ptchernia
// */
//public class JPASynthesisTestDAO extends AbstractSynthesis<SynthesisValue, SynthesisDatatype> {
//
//    @Override
//    public Stream<SynthesisValue> getSynthesisValue() {
//        String path = null;
//        try {
//            path = getClass().getResource("./values.sql").toURI().getPath();
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(JPASynthesisTestDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            return Files.lines(Paths.get(path))
//                    .skip(1)
//                    .map(p->p.replaceAll("\"", "").split(";"))
//                    .map(sa->new SynthesisValue(sa));
//        } catch (IOException ex) {
//            Logger.getLogger(JPASynthesisTestDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return Stream.empty();
//    }
//
//    protected static float getModulation() {
//        return (Double.valueOf(Math.random() - 0.5F)).floatValue();
//    }
//
//}
