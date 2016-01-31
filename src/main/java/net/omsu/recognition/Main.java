package net.omsu.recognition;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.SwingWrapper;
import net.omsu.recognition.function.Function;
import net.omsu.recognition.function.NeuronFunction;
import net.omsu.recognition.function.Tanh;
import net.omsu.recognition.neuron.NeuronNetwork;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Main {

    private static final Double RANGE = 10d;
    private static final Double DELTA = 0.25;

    public static void main(String[] args) {
        Chart chart = new ChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        chart.getStyleManager().setMarkerSize(6);

        NeuronFunction function = new NeuronFunction();
        NeuronNetwork network = new NeuronNetwork(new Tanh(0.1));

        List<Pair<Double, Double>> learningData = new ArrayList<>();
        for (double i = 0; i < RANGE; i += DELTA) {
            Pair<Double, Double> value = new Pair<>(i, function.calculate(i));
            learningData.add(value);
        }

        List<Double> errors = network.learn(learningData);

        System.out.println(network.verify(0d));
        System.out.println(Math.sin(0d));

        drawFunction(function, chart);
        drawNeuronFunction(network, chart);
        Chart chart2 = drawErrors(errors);

        List<Chart> charts = new ArrayList<>();
        charts.add(chart);
        charts.add(chart2);

        new SwingWrapper(charts).displayChartMatrix();
    }

    private static Chart drawErrors(List<Double> errors) {
        Chart chart = new ChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        chart.getStyleManager().setMarkerSize(6);

        List<Double> arguments = new ArrayList<>();
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < errors.size(); i += 1000) {
            arguments.add((double) i);
            results.add(errors.get(i));
        }
        chart.addSeries("error(x)", arguments, results);
        return chart;
    }

    private static void drawFunction(Function function, final Chart chart) {
        List<Double> arguments = new ArrayList<>();
        List<Double> results = new ArrayList<>();

        for (double i = 0; i <= RANGE; i += DELTA) {
            arguments.add(i);
            results.add(function.calculate(i));
        }
        chart.addSeries("sin(x)", arguments, results);
    }

    private static void drawNeuronFunction(final NeuronNetwork network, final Chart chart) {
        List<Double> arguments = new ArrayList<>();
        List<Double> results = new ArrayList<>();

        for (double i = 0; i <= RANGE; i += DELTA) {
            arguments.add(i);
            results.add(network.verify(i));
        }
        chart.addSeries("y", arguments, results);
    }
}
