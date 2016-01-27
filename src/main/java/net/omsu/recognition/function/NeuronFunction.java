package net.omsu.recognition.function;

/**
 *
 */
public class NeuronFunction implements Function {

    public NeuronFunction() {
    }

    @Override
    public double calculate(double argument) {
        return Math.sin(argument);
    }

    @Override
    public double derivative(double argument) {
        return Math.cos(argument);
    }
}
