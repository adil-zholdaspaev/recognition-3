package net.omsu.recognition.function;

/**
 *
 */
public class NeuronFunction implements Function {

    public NeuronFunction() {
    }

    @Override
    public double calculate(double argument) {
        return argument * Math.sin(argument) / 10;
    }

    @Override
    public double derivative(double argument) {
        return Math.cos(argument);
    }
}
