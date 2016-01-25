package net.omsu.recognition.neuron;

import net.omsu.recognition.function.Function;
import net.omsu.recognition.neuron.range.ArgumentRange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 */
public class NeuronNetwork {

    private final Function activationFunction;
    private final Function learningFunction;
    private final ArgumentRange argumentRange;

    private final List<Layer> network;

    public NeuronNetwork(Function activationFunction, Function learningFunction, ArgumentRange argumentRange) {
        this.activationFunction = activationFunction;
        this.learningFunction = learningFunction;
        this.argumentRange = argumentRange;

        Integer[] networkLayers = {1, 3, 1};

        this.network = initNetwork(networkLayers);
    }

    public List<Double> learn() {
        return null;
    }

    public List<Double> verify() {
        return null;
    }

    private List<Layer> initNetwork(final Integer[] networkLayers) {
        final List<Layer> network = new ArrayList<>();
        final List<Integer> previous = new ArrayList<>();
        previous.add(1);

        for (Integer layer : networkLayers) {
            List<Perceptron> neurons = new ArrayList<>(layer);
            IntStream.range(0, layer)
                    .forEach(value -> {
                        Perceptron perceptron = new Perceptron(previous.get(0));
                        neurons.add(perceptron);
                    });

            network.add(new Layer(neurons));
            previous.clear();
            previous.add(layer);
        }

        return network;
    }
}
