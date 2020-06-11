package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.awt.Color;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import org.cnrs.osuc.snot.synthesis.IGraphPresenceAbsence;
import org.cnrs.osuc.snot.synthesis.ITypeGraphiqueSynthese;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ptcherniati
 */
public class ChartFabrique {

    /**
     *
     */
    public static final String TOUT = "MSG_TOUT";

    /**
     *
     */
    public static final String AUCUN = "MSG_AUCUNE";

    /**
     *
     */
    public static final String PARTIE = "MSG_PARTIEL";

    /**
     *
     */
    public static final String TITRE = "TITRE";

    /**
     *
     */
    public static final String TITRE_MOYENNE = "TITRE_MOYENNE";

    /**
     *
     */
    public static final String TENDANCE_MOYENNE = "TENDANCE_MOYENNE";

    /**
     *
     */
    public static final String TENDANCE_MOYENNE_MENSUELLE = "TENDANCE_MOYENNE_MENSUELLE";

    /**
     *
     */
    public static final String TENDANCE_MOYENNE_CHAMBERS = "TENDANCE_MOYENNE_CHAMBERS";

    /**
     *
     */
    public static final String TENDANCE_MOYENNE_MENSUELLE_CHAMBERS = "TENDANCE_MOYENNE_MENSUELLE_CHAMBERS";

    /**
     *
     */
    public static final String PROFONDEUR = "PROFONDEUR";

    /**
     *
     */
    public static final String PROFONDEUR_REPETITION = "PROFONDEUR_REPETITION";

    /**
     *
     */
    public static final String TREATMENT_CHAMBER = "TREATMENT_CHAMBER";

    /**
     *
     */
    public static final String REPETITIONS_N = "REPETITIONS_N";

    /**
     *
     */
    public static final String REPETITION_1 = "REPETITION_1";

    /**
     *
     */
    public static final String REPETITION_0 = "REPETITION_0";

    /**
     *
     */
    public static final String CHAMBER_N = "CHAMBER_N";

    /**
     *
     */
    public static final String CHAMBER_1 = "CHAMBER_1";

    /**
     *
     */
    public static final String CHAMBER_0 = "CHAMBER_0";
    /**
     *
     */
    protected static final String LABEL_AXIS_DATE = "date";

    /**
     *
     */
    protected static final String PATTERN_NAME_UNIT = "%s(%s)";

    /**
     *
     */
    protected static final String BUNDLE_SOURCE_PATH = "org.cnrs.osuc.snot.synthesis.impl.jfreechart.messages";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     * @param dataset
     * @return
     */
    public JFreeChart buildChartTimeRepartition(XYDataset dataset) {
        synchronized (this) {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(null,
                    null,
                    null,
                    dataset,
                    false,
                    false,
                    false
            );
            chart.setBackgroundPaint(Color.white);
            XYPlot plot = (XYPlot) chart.getPlot();
            plot.getRangeAxis().setVisible(false);
            plot.setBackgroundAlpha(0);
            plot.setBackgroundPaint(Color.white);
            plot.setOutlineVisible(false);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.WHITE);
            chart.setAntiAlias(false);
            chart.setTextAntiAlias(true);
            plot.setDomainGridlinePaint(Color.BLACK);
            return chart;
        }
    }

    /**
     *
     * @param locale
     * @param dataset
     * @param variableName
     * @param uniteName
     * @return
     */
    public JFreeChart buildChartValuesVariable(Locale locale, XYDataset dataset, String variableName, String uniteName) {
        synchronized (this) {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(null,
                    LABEL_AXIS_DATE,
                    String.format(PATTERN_NAME_UNIT, variableName, uniteName),
                    dataset,
                    false,
                    false,
                    false
            );
            chart.setBackgroundPaint(Color.white);
            XYPlot plot = (XYPlot) chart.getPlot();
            DateAxis dateAxis = new DateAxis(null, TimeZone.getTimeZone("GMT"), locale);
            dateAxis.setLowerMargin(0.02);
            dateAxis.setUpperMargin(0.02);
            //dateAxis.setTickLabelInsets(new org.jfree.ui.RectangleInsets(0, 7, 0, 7));
            plot.setDomainAxis(dateAxis);
            plot.setBackgroundPaint(Color.white);
            plot.setOutlineVisible(false);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.WHITE);
            chart.setAntiAlias(true);
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setRangeGridlinePaint(Color.BLACK);
            return chart;
        }
    }

    /*
     * public JFreeChart buildChartTimeRepartitionParProfondeur(List<MyTimeSeries> timeSeries, XYLineAndShapeRenderer renderer, List<Date> minMax, List<Profondeur> profondeurs) { TimeSeriesCollection dataset = new TimeSeriesCollection();
     * for(MyTimeSeries myTimeSeries : timeSeries) { dataset.addSeries(myTimeSeries.getTimeSeries()); } String tout = localizationManager.getMessage(BUNDLE_SOURCE_PATH, TOUT); String aucun = localizationManager.getMessage(BUNDLE_SOURCE_PATH, AUCUN);
     * String partie = localizationManager.getMessage(BUNDLE_SOURCE_PATH, PARTIE); synchronized (this) { JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title null, // x-axis label null, // y-axis label dataset, // data true, // create
     * legend? false, // generate tooltips? false // generate URLs? ); Shape point = ShapeUtilities.createDiamond(3); chart.setBackgroundPaint(Color.white); XYPlot plot = chart.getXYPlot(); plot.setBackgroundPaint(Color.white);
     * plot.setDomainGridlinePaint(Color.BLACK); plot.setRangeGridlinePaint(Color.BLACK); ValueAxis abscisse = plot.getDomainAxis(); NumberAxis ordonnee = (NumberAxis) plot.getRangeAxis(); /*TickUnits ticks = new TickUnits(); for(Profondeur
     * profondeur : profondeurs){ ticks.add(new NumberTickUnit(profondeur.getProfondeurReelle())); }
     */
 /*
     * MyTickUnit tick = new MyTickUnit(5.0, profondeurs); TickUnits ticks = new TickUnits(); ticks.add(tick); ordonnee.setStandardTickUnits(ticks); ordonnee.setTickLabelsVisible(true)
     * LegendItemCollection(); chartLegend.add(new LegendItem(aucun, null, null, null, point, Color.red)); chartLegend.add(new LegendItem(tout, null, null, null, point, Color.green)); chartLegend.add(new LegendItem(partie, null, null, null, point,
     * Color.orange)); plot.setFixedLegendItems(chartLegend)
     * renderer.setSeriesPaint(i, myTimeSeries.getCouleur() ); renderer.setSeriesLinesVisible(i, false); renderer.setSeriesShapesVisible(i, true); renderer.setSeriesVisibleInLegend(i, false); renderer.setSeriesShape(i, point); } return chart; } }
     */

    /**
     *
     * @param locale
     * @param values
     * @param renderer
     * @param variableName
     * @param uniteName
     * @return
     */

    public JFreeChart buildChartTimeRepartitionParProfondeur(Locale locale, List<GenericSynthesisValue> values, XYLineAndShapeRenderer renderer,
            String variableName, String uniteName) {
        boolean isMensuel = values.get(0).getClass().getPackage().getName().contains("cmsm");
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_SOURCE_PATH, locale);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Iterator<GenericSynthesisValue> iterator = values.iterator();
        Map<String, TimeSeries> series = new HashMap();
        Map<String, Integer> maxRepetions = new HashMap();
        int timeSerieIndex = 0;
        boolean isInfra = false;
        boolean hasTreatment = false;
        if (!values.isEmpty()) {
            isInfra = ((ITypeGraphiqueSynthese) values.get(0)).isSemihoraire();
            hasTreatment = values.get(0).getClass().getPackage().getName().endsWith(".flcj");
        }
        while (iterator.hasNext()) {
            IGraphPresenceAbsence value = (IGraphPresenceAbsence) iterator.next();
            final String profondeur = value.getOrdonnee().isNumeriqueNatif() ? Integer.valueOf(value.getOrdonnee().getValeurNumerique()).toString() : value.getOrdonnee().getValeurAlphanumerique();
            if (!series.containsKey(profondeur)) {
                TimeSeries timeSeries = new TimeSeries("");
                series.put(profondeur, timeSeries);
                dataset.addSeries(timeSeries);
                //renderer.setSeriesShape(timeSerieIndex++, ShapeUtilities.createDiamond(2F));
                maxRepetions.put(profondeur, 0);
            }
            series.get(profondeur).add(new Day(Date.from(value.getDate().toInstant(ZoneOffset.UTC))), value.getValueFloat());
            maxRepetions.put(profondeur, Math.max(maxRepetions.get(profondeur), value.getRepetition().getValeurNumerique()));
            iterator.remove();
        }
        final String nRep = hasTreatment ? CHAMBER_N : REPETITIONS_N;
        final String noRep = hasTreatment ? CHAMBER_0 : REPETITION_0;
        final String Onerep = hasTreatment ? CHAMBER_1 : REPETITION_1;
        final String LegendInfo = hasTreatment ? TREATMENT_CHAMBER : PROFONDEUR_REPETITION;
        final String tendanceMensuelle = hasTreatment ? TENDANCE_MOYENNE_MENSUELLE_CHAMBERS : TENDANCE_MOYENNE_MENSUELLE;
        final String tendanceMoyenne = hasTreatment ? TENDANCE_MOYENNE_CHAMBERS : TENDANCE_MOYENNE;
        for (Map.Entry<String, Integer> entry : maxRepetions.entrySet()) {

            String profondeur = entry.getKey();
            Integer nbRepetition = entry.getValue();
            String repetitionString = profondeur;
            if (nbRepetition > 1) {
                repetitionString = String.format(bundle.getString(nRep), nbRepetition);
            } else {
                repetitionString = bundle.getString(nbRepetition == 0 ? noRep : Onerep);
            }
            String profondeurString = String.format(bundle.getString(LegendInfo), profondeur, repetitionString);
            series.get(profondeur).setKey(profondeurString);
        }
        synchronized (this) {
            final String legendTitle = isMensuel ? tendanceMensuelle : tendanceMoyenne;
            JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(bundle.getString(legendTitle), variableName),
                    null,
                    String.format(PATTERN_NAME_UNIT, variableName, uniteName),
                    dataset,
                    true,
                    false,
                    false
            );
            this.LOGGER.info("J'ai synchronisé");
            /*renderer.setBaseShapesVisible(true);//visibilite des points
            renderer.setBaseShapesFilled(true);//visibilite des points remplis
            renderer.setDrawOutlines(false);//visibilite des contour de points
            renderer.setBaseLinesVisible(false);//trace la ligne entre les points*/
//            Shape point = ShapeUtilities.createDiamond(3);
//            chart.setBackgroundPaint(Color.white);
//            XYPlot plot = chart.getXYPlot();
//            plot.setBackgroundPaint(Color.white);
//            plot.setDomainGridlinePaint(Color.BLACK);
//            plot.setRangeGridlinePaint(Color.BLACK);
//            ValueAxis abscisse = plot.getDomainAxis();
//            NumberAxis ordonnee = (NumberAxis) plot.getRangeAxis();
            return chart;
        }
    }

    /**
     *
     * @param locale
     * @param timeSeries
     * @param renderer
     * @param minMax
     * @param ordonnees
     * @return
     */
    public JFreeChart buildChartTimeRepartitionParProfondeur(Locale locale, List<MyTimeSeries> timeSeries, XYLineAndShapeRenderer renderer, List<Date> minMax, List<AbstractOrdonnee> ordonnees) {
        this.LOGGER.info("Je suis dans buildChartTimeRepartitionParProfondeur");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (MyTimeSeries myTimeSeries : timeSeries) {
            dataset.addSeries(myTimeSeries.getTimeSeries());
        }
        this.LOGGER.info("J'ai construire les séries");
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_SOURCE_PATH, locale);
        String tout = bundle.getString(TOUT);
        String aucun = bundle.getString(AUCUN);
        String partie = bundle.getString(PARTIE);
        synchronized (this) {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(bundle.getString(TITRE),
                    null,
                    bundle.getString(PROFONDEUR),
                    dataset,
                    true,
                    false,
                    false
            );
            this.LOGGER.info("J'ai synchronisé");
            //Shape point = ShapeUtilities.createDiamond(3);
            chart.setBackgroundPaint(Color.white);
            XYPlot plot = chart.getXYPlot();
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setRangeGridlinePaint(Color.BLACK);
            ValueAxis abscisse = plot.getDomainAxis();
            NumberAxis ordonnee = (NumberAxis) plot.getRangeAxis();
            /*
             * TickUnits ticks = new TickUnits(); for(Profondeur profondeur : profondeurs){ ticks.add(new NumberTickUnit(profondeur.getProfondeurReelle())); }
             */
            MyTickUnit tick = new MyTickUnit(5.0, ordonnees);
            TickUnits ticks = new TickUnits();
            ticks.add(tick);
            ordonnee.setStandardTickUnits(ticks);
            ordonnee.setTickLabelsVisible(true);
            LegendItemCollection chartLegend = new LegendItemCollection();
            /*chartLegend.add(new LegendItem(aucun, null, null, null, point, Color.red));
            chartLegend.add(new LegendItem(tout, null, null, null, point, new Color(0, 102, 0)));
            chartLegend.add(new LegendItem(partie, null, null, null, point, Color.orange));*/
            plot.setFixedLegendItems(chartLegend);
            abscisse.setRange(minMax.get(0).getTime(), minMax.get(1).getTime());
            this.LOGGER.info("Je dessine");
            for (int i = 0; i < timeSeries.size(); i++) {
                MyTimeSeries myTimeSeries = timeSeries.get(i);
                renderer.setSeriesPaint(i, myTimeSeries.getCouleur());
                renderer.setSeriesLinesVisible(i, false);
                renderer.setSeriesShapesVisible(i, true);
                renderer.setSeriesVisibleInLegend(i, false);
                //renderer.setSeriesShape(i, point);
            }
            this.LOGGER.info("J'ai  dessiné");
            return chart;
        }
    }
}
