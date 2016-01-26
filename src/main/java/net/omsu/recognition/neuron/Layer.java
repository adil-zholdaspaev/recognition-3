package net.omsu.recognition.neuron;

import java.util.List;

/**
 *
 */
public class Layer {

    private final List<Perceptron> perceptrons;

    public Layer(final List<Perceptron> perceptrons) {
        this.perceptrons = perceptrons;
    }

    public List<Perceptron> getPerceptrons() {
        return perceptrons;
    }
}
