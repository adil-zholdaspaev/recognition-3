package net.omsu.recognition.neuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Perceptron {

    private List<Double> weights;
    private Double initialOffset;
    private Double outputValue;
    private Double initialWeightOffset;
    private List<Double> weightOffset;
    private Double arguments;
    private Double delta;

    public Perceptron(final int n) {
        this.weights = new ArrayList<>(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            weights.add(getRandomValue(random));
        }
        initialOffset = getRandomValue(random);
        weightOffset = Collections.emptyList();
        initialWeightOffset = 0d;
        outputValue = 0d;
        delta = 0d;
    }

    private Double getRandomValue(final Random random) {
        return random.nextGaussian() * 2 - 1;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public Double getInitialOffset() {
        return initialOffset;
    }

    public void setInitialOffset(Double initialOffset) {
        this.initialOffset = initialOffset;
    }

    public Double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Double outputValue) {
        this.outputValue = outputValue;
    }

    public List<Double> getWeightOffset() {
        return weightOffset;
    }

    public void setWeightOffset(List<Double> weightOffset) {
        this.weightOffset = weightOffset;
    }

    public Double getArguments() {
        return arguments;
    }

    public void setArguments(Double arguments) {
        this.arguments = arguments;
    }

    public Double getInitialWeightOffset() {
        return initialWeightOffset;
    }

    public void setInitialWeightOffset(Double initialWeightOffset) {
        this.initialWeightOffset = initialWeightOffset;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }
}
