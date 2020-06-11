package org.cnrs.osuc.snot.synthesis.impl.jfreechart;

import java.awt.Color;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import org.cnrs.osuc.snot.utils.Constantes;
import org.inra.ecoinfo.synthesis.entity.GenericSynthesisValue;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author ptcherniati
 */
public class FabriqueDatasetDefaut {

    /**
     *
     * @param synthesisVariables
     * @param renderer
     * @return
     */
    public XYDataset createDatasetRepartitionTimeVariable(List<GenericSynthesisValue> synthesisVariables, XYLineAndShapeRenderer renderer) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Integer count = 0;
        TimeSeries timeSeries = new TimeSeries(count);
        for (GenericSynthesisValue synthesisVariable : synthesisVariables) {
            timeSeries.addOrUpdate(new Day(Date.from(synthesisVariable.getDate().toInstant(ZoneOffset.UTC))), 5);
        }

        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesVisibleInLegend(0, false);
        renderer.setSeriesPaint(0, new Color(11, 53, 108));

        dataset.addSeries(timeSeries);
        return dataset;
    }

    /**
     *
     * @param synthesisValues
     * @param renderer
     * @return
     */
    public XYDataset createDatasetValuesVariable(List<GenericSynthesisValue> synthesisValues, XYLineAndShapeRenderer renderer) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Float badMesure = new Float(Constantes.PROPERTY_CST_INVALID_BAD_MEASURE);
        Integer count = 0;
        TimeSeries timeSeries = new TimeSeries(count);
        for (GenericSynthesisValue synthesisValue : synthesisValues) {
            if (synthesisValue.getValueFloat().byteValue() != badMesure.byteValue()) {
                timeSeries.addOrUpdate(new Day(Date.from(synthesisValue.getDate().toInstant(ZoneOffset.UTC))), synthesisValue.getValueFloat());
            }

        }
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesVisibleInLegend(0, false);
        renderer.setSeriesPaint(0, new Color(11, 53, 108));
        dataset.addSeries(timeSeries);
        return dataset;
    }
}
