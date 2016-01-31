package net.omsu.recognition.function;

/**
 *
 */
public class Tanh implements Function {

    private final double factor;

    public Tanh(final double factor) {
        this.factor = factor;
    }

    public double calculate(double argument) {
        return Math.tanh(argument * factor);
    }

    public double derivative(double argument) {
        return factor * (1 - calculate(argument) * calculate(argument));
    }
}
