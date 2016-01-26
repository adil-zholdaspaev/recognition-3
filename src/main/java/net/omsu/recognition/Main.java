package net.omsu.recognition;

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

    public static void main(String[] args) {

        NeuronFunction function = new NeuronFunction();
        NeuronNetwork network = new NeuronNetwork(new Tanh(0.8), function);

        List<Pair<Double, Double>> learningData = new ArrayList<>();
        for (double i = -10; i < 10; i += 0.05) {
            Pair<Double, Double> value = new Pair<>(i, function.calculate(i));
            learningData.add(value);
        }

        for (int i = 0; i < 100; i++) {
            network.learn(learningData);
        }

        System.out.println(network.verify(1d));
        System.out.println(Math.sin(1d));
    }
}
