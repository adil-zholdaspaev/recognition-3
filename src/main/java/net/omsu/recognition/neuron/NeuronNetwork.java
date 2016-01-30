package net.omsu.recognition.neuron;

import net.omsu.recognition.function.Function;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 */
public class NeuronNetwork {

    private static final double EPSILON = 0.001;
    private static final double LEARNING_SPEED = 0.25;

    private final Function activationFunction;
    private final List<Layer> network;

    public NeuronNetwork(Function activationFunction) {
        this.activationFunction = activationFunction;

        Integer[] networkLayers = {1, 50, 1};

        this.network = initNetwork(networkLayers);
    }

    public void learn(final List<Pair<Double, Double>> trainingData) {

        for (int i = 0; i < 4000; i++) {

            trainingData.forEach(data -> {
                Double argument = data.getKey();
                Double actualResult = data.getValue();

                Double error = 0d;

                Double result = calculateFunction(argument);
                error = actualResult - result;

                System.out.println(error);

                calculateErrors(error);
                updateWeights();
            });
        }

        /*Pair<Double, Double> value = trainingData.get(0);
        Double argument = value.getKey();
        Double actualResult = value.getValue();

        Double error = 0d;
        do {
            Double result = calculateFunction(argument);
            error = Math.abs(actualResult - result);

            calculateDelta(error);
            calculateWeight(argument);

            argument = argument - EPSILON * function.derivative(argument);
            actualResult = function.calculate(argument);
        } while (error > EPSILON);*/
    }

    public Double verify(final Double argument) {
        return calculateFunction(argument);
    }

    private List<Layer> initNetwork(final Integer[] networkLayers) {
        final List<Layer> network = new ArrayList<>();
        final List<Integer> previous = new ArrayList<>();
        previous.add(1);

        for (Integer layer : networkLayers) {
            List<Perceptron> neurons = new ArrayList<>(layer);
            IntStream.range(0, layer)
                    .forEach(value -> {
                        Perceptron perceptron = new Perceptron(previous.get(0));
                        neurons.add(perceptron);
                    });

            network.add(new Layer(neurons));
            previous.clear();
            previous.add(layer);
        }

        return network;
    }

    private Double calculateFunction(final Double argument) {
        network.get(0).getPerceptrons().forEach(perceptron -> perceptron.setOutputValue(argument));

        network.get(1).getPerceptrons().forEach(perceptron -> {
            Double sum = perceptron.getInitialOffset() + argument * perceptron.getWeights().get(0);
            perceptron.setArguments(sum);
            perceptron.setOutputValue(activationFunction.calculate(sum));
        });

        network.get(2).getPerceptrons().forEach(perceptron -> {
            Double argumentsSum = 0d;
            for (int i = 0; i < perceptron.getWeights().size(); i++) {
                argumentsSum += network.get(1).getPerceptrons().get(i).getOutputValue() * perceptron.getWeights().get(i);
            }
            argumentsSum += perceptron.getInitialOffset();

            perceptron.setArguments(argumentsSum);
            perceptron.setOutputValue(activationFunction.calculate(argumentsSum));
        });

        return network.get(2).getPerceptrons().get(0).getOutputValue();
    }

    private void calculateErrors(final Double error) {
        network.get(2).getPerceptrons().forEach(perceptron -> {
            final Double delta = error * activationFunction.derivative(perceptron.getArguments());
            perceptron.setInitialWeightOffset(delta * LEARNING_SPEED);
            perceptron.setDelta(delta);

            final List<Double> weightOffset = new ArrayList<>();
            network.get(1).getPerceptrons().forEach(p -> weightOffset.add(delta * p.getOutputValue()));

            perceptron.setWeightOffset(weightOffset);
        });

        for (int i = 0; i < network.get(1).getPerceptrons().size(); i++) {
            Perceptron perceptron = network.get(1).getPerceptrons().get(i);

            Perceptron p = network.get(2).getPerceptrons().get(0);
            Double prevDelta = p.getDelta() * p.getWeights().get(i);
            final Double delta = prevDelta * activationFunction.derivative(perceptron.getArguments());

            perceptron.setInitialWeightOffset(delta * LEARNING_SPEED);
            perceptron.setDelta(delta);


            final List<Double> weightOffset = new ArrayList<>();
            network.get(0).getPerceptrons().forEach(per -> weightOffset.add(delta * per.getOutputValue()));

            perceptron.setWeightOffset(weightOffset);
        }
    }

    private void updateWeights() {
        network.get(1).getPerceptrons().forEach(perceptron -> {
            List<Double> weights = new ArrayList<>();
            for (int i = 0; i < perceptron.getWeights().size(); i++) {
                weights.add(perceptron.getWeights().get(i) + perceptron.getWeightOffset().get(i));
            }
            perceptron.setWeights(weights);
            perceptron.setInitialOffset((perceptron.getInitialOffset() + perceptron.getInitialWeightOffset()));
        });
    }
}
