///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.inra.ecoinfo.synthesis.jsf;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.function.BiConsumer;
//import java.util.function.BinaryOperator;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import org.inra.ecoinfo.localization.entity.Localization;
//import org.inra.ecoinfo.mga.enums.Activities;
//import org.inra.ecoinfo.synthesis.ISynthesisManager;
//import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
////import org.cnrs.osuc.snot.synthesis.fluxsh.SynthesisDatatype;
////import org.cnrs.osuc.snot.synthesis.fluxsh.SynthesisValue;
//import org.inra.ecoinfo.utils.DateUtil;
//import org.inra.ecoinfo.utils.exceptions.BusinessException;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.ChartModel;
//import org.primefaces.model.chart.DateAxis;
//import org.primefaces.model.chart.LegendPlacement;
//import org.primefaces.model.chart.LineChartModel;
//import org.primefaces.model.chart.LineChartSeries;
//
///**
// *
// * @author ptchernia
// */
//public class SpecificSynthesisChartTrendVariableBuilder implements ISynthesisChartTrenderVariableBuilder {
//
//    ISynthesisManager synthesisManager;
//    String language = Localization.getDefaultLocalisation();
//
//    public <SV extends GenericSynthesisValue> ChartModel buildModel(ItemDatatableVariableSynthesis item, String context, Class<SV> synthesisDatatypeClass, String language) throws BusinessException {
//        this.language = language;
//        LineChartModel dateModel = new LineChartModel();
//        Map<String, LineChartSeries> seriesMap = new HashMap();
//        Map<String, Float> seriesMapNumber = new HashMap();
//        LineChartSeries series = new LineChartSeries();
//        series.setShowLine(false);  //pour retirer les lignes
//        final String variable = item.getVariable().getCode();
//        final String variablecode = item.getLocalizedName();      
//        final String labelY = variable.replaceAll("^(.*)\\/(.*)-(.*)-(.*)-(.*)$", "$4 ($5)");               
////        final String labelY = item.getLocalizedUniteNameGetAxisName();
//        final LocalDateTime dateDebut = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, item.getDebut());
//        final LocalDateTime dateFin = DateUtil.readLocalDateTimeFromText(DateUtil.DD_MM_YYYY, item.getFin());
////        final Stream<GenericSynthesisValue> synthesisValueStream = synthesisManager.getSynthesisValuesByVariableNodeableAndSiteByDatesInterval(context, variable, dateDebut, dateFin, item.getVariableNode(), (Class<GenericSynthesisValue>) synthesisDatatypeClass, Stream.of(Activities.extraction).collect(Collectors.toList()));
////      Création des dates pour l'axe des abscisses
//        DateAxis axis = new DateAxis();
//        Duration duration = Duration.between(dateDebut, dateFin);
//        duration = duration.dividedBy(20);
//        axis.setMin(DateUtil.getUTCDateTextWithLocaleFromLocalDateTime(dateDebut.minus(duration), "yyyy-MM-dd", Locale.forLanguageTag(language)));
//        axis.setMax(DateUtil.getUTCDateTextWithLocaleFromLocalDateTime(dateFin.plus(duration), "yyyy-MM-dd", Locale.forLanguageTag(language)));
//        dateModel.getAxes().put(AxisType.X, axis);
////      Labels pour les axes X et Y
//        dateModel.getAxis(AxisType.Y).setLabel(labelY);
//        dateModel.getAxis(AxisType.X).setLabel("Date");
//        dateModel.setZoom(true);
//        dateModel.setLegendPosition("n");
//        Collector<SynthesisValue, TreeMap<LocalDateTime, Average>, LineChartSeries> collector = getCollector();
////        synthesisValueStream
////                .map(gsv -> (SynthesisValue) gsv)
////                .collect(Collectors.groupingBy(SynthesisValue::getSite, collector))
////                .values().stream()
////                .sorted(Comparator.comparing(l->Integer.parseInt(l.getLabel())))
////                .forEach(line -> dateModel.addSeries(line));
//        dateModel.setExtender("graphicVariableChartExtender");
//        dateModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
//        dateModel.setLegendRows(1);
//        return dateModel;
//    }
//
//    public void setSynthesisManager(ISynthesisManager synthesisManager) {
//        this.synthesisManager = synthesisManager;
//    }
//
//    private Collector<SynthesisValue, TreeMap<LocalDateTime, Average>, LineChartSeries> getCollector() {
//        Collector<SynthesisValue, TreeMap<LocalDateTime, Average>, LineChartSeries> collector = Collector.of(TreeMap::new,
//                this::addValue,
//                this::mergeMap,
//                this::convertMap,
//                Collector.Characteristics.CONCURRENT
//        );
//        return collector;
//    }
//
//    private void addValue(TreeMap<LocalDateTime, Average> map, SynthesisValue sv) {
//        map
//                .<LocalDateTime, Average>compute(sv.getDate(),
//                        (k1, v1) -> {
//                            if (v1 == null) {
//                                return new Average(k1, sv.getSite(), sv.getValueFloat());
//                            } else {
//                                v1.add(sv.getValueFloat());
//                                return null;
//                            }
//                        }
//                );
//    }
//
//    <T extends SortedMap<LocalDateTime, Average>> T mergeMap(T m1, T m2) {
//        m2.entrySet().parallelStream().forEach((u) -> {
//            if (!m1.containsKey(u.getKey())) {
//                m1.put(u.getKey(), u.getValue());
//            } else {
//                m1.<LocalDateTime, Average>get(u.getKey()).add(u.getValue());
//            }
//        });
//        return m1;
//    }
//
//    LineChartSeries convertMap(SortedMap<LocalDateTime, Average> map) {
//        LineChartSeries line = map.values().stream().findFirst().map(a -> new LineChartSeries(a.site)).orElseGet(LineChartSeries::new);
//        map
//                .forEach((date, average) -> {
//                    line.set(
//                            DateUtil.getUTCDateTextWithLocaleFromLocalDateTime(date, "yyyy-MM-dd", Locale.forLanguageTag(this.language)),
//                            average.getAverage()
//                    );
//                    line.setShowLine(false);//pour retirer les lignes
//                });
//        return line;
//    }
//
//    @Override
//    public <SV extends GenericSynthesisValue> ChartModel buildModel(ItemDatatableVariableSynthesis idvs, Class<SV> type, String string) throws BusinessException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public class Average {
//
//        String site;
//        LocalDateTime date;
//        Double sum = null;
//        int counter = 0;
//
//        public Average(LocalDateTime date, String site) {
//            this.site = site;
//            this.date = date;
//        }
//
//        public Average(LocalDateTime date, String site, Double d) {
//            this.site = site;
//            this.date = date;
//            this.sum = d;
//            this.counter = 1;
//        }
//
//        public Average(LocalDateTime date, String site, Float f) {
//            this.site = site;
//            this.date = date;
//            this.sum = f.doubleValue();
//            this.counter = 1;
//        }
//
//        void add(Double d) {
//            sum += d;
//            counter++;
//        }
//
//        void add(Float f) {
//            add(f.doubleValue());
//        }
//
//        void add(Average a) {
//            this.counter += a.counter;
//            this.sum += a.sum;
//        }
//
//        Double getAverage() {
//            return sum / counter;
//        }
//    }
//}
