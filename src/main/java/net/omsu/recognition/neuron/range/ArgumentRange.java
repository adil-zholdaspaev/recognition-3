package net.omsu.recognition.neuron.range;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 */
public class ArgumentRange implements Supplier<List<Double>>{

    private final double leftBorder;
    private final double rightBorder;
    private final double delta;

    public ArgumentRange(final double leftBorder, final double rightBorder, final double delta) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.delta = delta;
    }

    public List<Double> get() {
        final List<Double> arguments = new ArrayList<Double>();

        for (double temp = leftBorder; temp <= rightBorder; temp += delta) {
            arguments.add(temp);
        }
        return arguments;
    }
}
