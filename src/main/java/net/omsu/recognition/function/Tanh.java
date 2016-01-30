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
        return (2 / (1 + Math.exp(-factor * argument))) - 1;
    }

    public double derivative(double argument) {
        return 0.5 * (1 + calculate(factor * argument)) * (1 - calculate(factor * argument));
    }
}
