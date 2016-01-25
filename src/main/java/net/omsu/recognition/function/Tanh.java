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
        return Math.tanh(argument);
    }

    public double derivative(double argument) {
        return 1 / Math.pow(Math.cosh(argument), 2);
    }
}
