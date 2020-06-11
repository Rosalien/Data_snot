///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.cnrs.osuc.snot.extraction.fluxmeteo.impl;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.Collection;
//import static java.util.Collections.list;
//import java.util.HashSet;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.zip.ZipOutputStream;
//import org.inra.ecoinfo.utils.AbstractIntegrator;
//import java.util.zip.ZipEntry;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.core.MediaType;
//import org.cnrs.osuc.snot.dataset.AbstractValeur;
//import org.cnrs.osuc.snot.dataset.entity.Valeur;
//import org.cnrs.osuc.snot.refdata.jeu.IJeuDAO;
//import org.cnrs.osuc.snot.refdata.site.SiteSnot;
//import org.inra.ecoinfo.extraction.IParameter;
//import org.inra.ecoinfo.mga.business.composite.Nodeable;
//import org.inra.ecoinfo.mga.business.composite.RealNode;
//
///**
// *
// * @author jbparoissien
// */
//public class HelloFifi extends AbstractIntegrator<FluxMeteoParameterVO> {
//
//    IJeuDAO jeuDAO;
////    Set<RealNode> realNodesDatatypes = new HashSet<>();
//    Set<RealNode> realNodesVariable = new HashSet<>();
//    Set<String> stations = new TreeSet();
//    Set<String> codesJeu = new TreeSet();
//
//    @Override
//    public String getMapEntryKey() {
//        return "fluxmeteoparameters";
//    }
//
//    public void embed(Collection<FluxMeteoBuildOutputDisplay> fluxMeteoBuildOutputDisplay) throws IOException {
//
//    }
//
//    @Override
//    public void embed(ZipOutputStream zipOutputStream, Collection<FluxMeteoParameterVO> collection) throws IOException {
//        System.out.println("********************************************/nGénération de la métadonnées PIVOT/n********************************************");
//
//        FluxMeteoParameterVO parameter = collection.stream().findFirst().orElse(null);
//
//        zipOutputStream.putNextEntry(
//                //                filename = parameter.getParameters().get()
//                new ZipEntry("TOUR_fr.json"));
//        //        String hello = "\"********************************************/nHello Fifi/n****getSelectedJeu****************************************\"";
//        //        byte[] b = hello.getBytes();
//        //        zipOutputStream.write(b);
//
//        String pivot = getPivotFormat(parameter);
//        zipOutputStream.write(pivot.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String deDup(String s) {
//        return new LinkedHashSet<String>(Arrays.asList(s.split("@@"))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", "-");
//    }
//
//    public String getPivotFormat(IParameter parameters) {
//        if (parameters instanceof FluxMeteoParameterVO) {
//            FluxMeteoParameterVO fmp = (FluxMeteoParameterVO) parameters;
//            parseTree(fmp.valeurChambreFluxSols);
//            parseTree(fmp.valeurFluxTours);
//            parseTree(fmp.valeurMeteoTours);
//        }
//        Collection<SiteSnot> sites = (List<SiteSnot>) parameters.getParameters().get(Nodeable.class.getSimpleName());
//        Collection<RealNode> realnodes = (List<RealNode>) parameters.getParameters().get(Nodeable.class.getSimpleName());
//
//        String realNodePaths = codesJeu.stream()
//                .collect(Collectors.joining("@@"));
//
////        List<String> Test = codesJeu.stream()
////                .collect(Collectors.joining("@@"))
////                .split("@@")
//////                .distinct()
////                .collect(Collectors.toList());
//
//        String test = deDup(realNodePaths);
//
//        //ICI, VOIR POUR GENERER PLUSIEURS CODE_JEU
//        String realNodePath = realNodePaths.split("@@")[0];
//
//        final String codeJeu = realNodePath.replaceAll("^(.*)\\/(.*),(.*),(.*)_(.*),(.*)$", "$1-$3-$4");
//
//        System.out.println(
//                "***********************************************RealNodePath : " + realNodePath);
//        System.out.println(
//                "***********************************************Code Jeu : " + codeJeu);
//
////        final String codeJeu = realNodePath.replaceAll("^(.*)/(.*),(.*),(.*),(.*)$", "$1-$3-$4");
//        Client client = ClientBuilder.newClient();
//        final Invocation.Builder request = client.target("http://localhost:8081/rest/resources/")
//                .path("pivot")
//                .queryParam("codes_jeu", codeJeu)
//                .request(MediaType.APPLICATION_JSON);
//        final String pivotFormat = request
//                .get(String.class);
//
//        System.out.println(pivotFormat);
//        return pivotFormat;
//
//    }
//
//    protected <V extends Valeur> void parseTree(List<V> valeurs) {
//        valeurs.stream()
//                .map(
//                        v -> v.getRealNode()
//                )
//                .filter(
//                        r -> !realNodesVariable.contains(r)
//                )
//                .peek(
//                        r -> realNodesVariable.add(r)
//                )
//                .map(
//                        r -> r.getPath()
//                )
//                //                .map(
//                //                        r -> r.getParent()
//                //                )
//                //                .filter(
//                //                        r -> !realNodesDatatypes.contains(r)
//                //                )
//                //                .peek(
//                //                        r -> realNodesDatatypes.add(r)
//                //                )
//                //                .map(
//                //                        r -> getCodeJeu(r)
//                //                )
//                .forEach(
//                        codeJeu -> codesJeu.add(codeJeu)
//                );
//    }
//
//    protected String getCodeStation(RealNode realNodeDatatype) {
//        String datatype = realNodeDatatype.getNodeable().getCode();
//
//        final RealNode realNodeTheme = realNodeDatatype.getParent();
//        String theme = realNodeTheme.getNodeable().getCode();
//
//        final RealNode realNodeStation = realNodeTheme.getParent();
//        return realNodeStation.getNodeable().getCode().split("/")[1];
//    }
//
//    protected String getCodeJeu(RealNode realNodeDatatype) {
//        String datatype = realNodeDatatype.getNodeable().getCode();
//
//        final RealNode realNodeTheme = realNodeDatatype.getParent();
//        String theme = realNodeTheme.getNodeable().getCode();
//
//        final RealNode realNodeStation = realNodeTheme.getParent();
//        String station = realNodeStation.getNodeable().getCode().split("/")[1];
//        this.stations.add(station);
//
//        final RealNode realNodeSite = realNodeStation.getAncestor();
//        String site = realNodeSite.getNodeable().getCode();
//        return String.format("%s-%s-%s", site, theme, datatype);
//    }
//
//    protected String getCodeJeuVariable(RealNode realNodeVariable) {
//        String dvu = realNodeVariable.getNodeable().getCode();
//        final RealNode realNodeDatatype = realNodeVariable.getParent();
//        String datatype = realNodeDatatype.getNodeable().getCode();
//
//        final RealNode realNodeTheme = realNodeDatatype.getParent();
//        String theme = realNodeTheme.getNodeable().getCode();
//
//        final RealNode realNodeStation = realNodeTheme.getParent();
//        String station = realNodeStation.getNodeable().getCode().split("/")[1];
//
//        final RealNode realNodeSite = realNodeStation.getAncestor();
//        String site = realNodeSite.getNodeable().getCode();
//        return String.format("%s-%s-%s-%s", site, theme, datatype, dvu);
//    }
//
//    public void setJeuDAO(IJeuDAO jeuDAO) {
//        this.jeuDAO = jeuDAO;
//    }
//}
