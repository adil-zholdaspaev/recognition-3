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
    private static final Double DELTA = 0.05;

    public static void main(String[] args) {
        Chart chart = new ChartBuilder().xAxisTitle("X").yAxisTitle("Y").width(600).height(400).build();
        chart.getStyleManager().setMarkerSize(6);

        NeuronFunction function = new NeuronFunction();
        NeuronNetwork network = new NeuronNetwork(new Tanh(1));

        List<Pair<Double, Double>> learningData = new ArrayList<>();
        for (double i = -RANGE; i < -RANGE + 2; i += DELTA) {
            Pair<Double, Double> value = new Pair<>(i, function.calculate(i));
            learningData.add(value);
        }

        network.learn(learningData);

        System.out.println(network.verify(-10d));
        System.out.println(Math.sin(-10d));

        drawFunction(function, chart);
        drawNeuronFunction(network, chart);

        List<Chart> charts = new ArrayList<>();
        charts.add(chart);

        new SwingWrapper(charts).displayChartMatrix();
    }

    private static void drawFunction(Function function, final Chart chart) {
        List<Double> arguments = new ArrayList<>();
        List<Double> results = new ArrayList<>();

        for (double i = -RANGE; i <= RANGE; i += DELTA) {
            arguments.add(i);
            results.add(function.calculate(i));
        }
        chart.addSeries("sin(x)", arguments, results);
    }

    private static void drawNeuronFunction(final NeuronNetwork network, final Chart chart) {
        List<Double> arguments = new ArrayList<>();
        List<Double> results = new ArrayList<>();

        for (double i = -RANGE; i <= RANGE; i += DELTA) {
            arguments.add(i);
            results.add(network.verify(i));
        }
        chart.addSeries("y", arguments, results);
    }
}
