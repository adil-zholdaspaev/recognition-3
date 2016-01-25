package net.omsu.recognition.function;

/**
 *
 */
public class NeuronFunction implements Function {

    public NeuronFunction() {
    }

    public double calculate(final double argument) {
        return Math.sin(argument);
    }

    public double derivative(final double argument) {
        return Math.cos(argument);
    }
}
