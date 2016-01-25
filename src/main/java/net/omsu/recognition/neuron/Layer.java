package net.omsu.recognition.neuron;

import java.util.List;

/**
 *
 */
public class Layer {

    private final List<Perceptron> neurons;

    public Layer(final List<Perceptron> neurons) {
        this.neurons = neurons;
    }

    public List<Perceptron> getNeurons() {
        return neurons;
    }
}
