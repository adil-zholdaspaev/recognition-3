package net.omsu.recognition.neuron;

import net.omsu.recognition.function.Function;
import net.omsu.recognition.neuron.range.ArgumentRange;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NeuronNetwork {

    private final Function activationFunction;
    private final Function learningFunction;
    private final ArgumentRange argumentRange;

    private final List<List<Perseptron>> network;

    public NeuronNetwork(Function activationFunction, Function learningFunction, ArgumentRange argumentRange) {
        this.activationFunction = activationFunction;
        this.learningFunction = learningFunction;
        this.argumentRange = argumentRange;

        this.network = new ArrayList<>();
        network.add(new ArrayList<>(1));
        network.add(new ArrayList<>(3));
        network.add(new ArrayList<>(1));
    }

    public List<Double> learn() {
        return null;
    }

    public List<Double> verify() {
        return null;
    }

}
