package net.omsu.recognition.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Perceptron {

    private List<Double> weights;
    private double result;
    private double delta;

    public Perceptron(final int n) {
        this.weights = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            weights.add(1d);
        }
        randomiseWeights();
    }

    private void randomiseWeights() {
        final Random random = new Random();
        weights.forEach(value -> value = random.nextDouble());
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
