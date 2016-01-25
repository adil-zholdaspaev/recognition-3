package net.omsu.recognition.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Perseptron {

    private List<Double> weigths;
    private double result;
    private double delta;

    public Perseptron(final int n) {
        this.weigths = new ArrayList<Double>(n);
        for (int i = 0; i < n; i++) {
            weigths.add(1d);
        }
    }

    private void randomiseWeigths() {
        final Random random = new Random();
        weigths.forEach(value -> value = random.nextDouble());
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public List<Double> getWeigths() {
        return weigths;
    }

    public void setWeigths(List<Double> weigths) {
        this.weigths = weigths;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
