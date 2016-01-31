package net.omsu.recognition.neuron;

import net.omsu.recognition.function.Function;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 */
public class NeuronNetwork {

    private static final double LEARNING_SPEED = 1;

    private final Function activationFunction;
    private final List<Layer> network;

    public NeuronNetwork(Function activationFunction) {
        this.activationFunction = activationFunction;

        Integer[] networkLayers = {1, 7, 7, 1};

        this.network = initNetwork(networkLayers);
    }

    public List<Double> learn(final List<Pair<Double, Double>> trainingData) {

        final List<Double> errors = new ArrayList<>();

        for (int i = 0; i < 70000; i++) {

            trainingData.forEach(data -> {
                Double argument = data.getKey();
                Double actualResult = data.getValue();

                Double error = 0d;

                Double result = calculateFunction(argument);
                error = actualResult - result;

                calculateErrors(error);
                updateWeights();

                errors.add(Math.abs(error));
            });
        }

        return errors;
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

        final List<Layer> previousLayer = new ArrayList<>();
        previousLayer.add(network.get(0));

        network.subList(1, network.size())
                .forEach(layer -> {
                    layer.getPerceptrons().forEach(perceptron -> {
                        Double arguments = 0d;
                        for (int i = 0; i < perceptron.getWeights().size(); i++) {
                            arguments += previousLayer.get(0).getPerceptrons().get(i).getOutputValue() * perceptron.getWeights().get(i);
                        }
                        arguments += perceptron.getInitialOffset();

                        perceptron.setArguments(arguments);
                        perceptron.setOutputValue(activationFunction.calculate(arguments));
                    });

                    previousLayer.clear();
                    previousLayer.add(layer);
                });

        return network.get(network.size() - 1).getPerceptrons().get(0).getOutputValue();
    }

    private void calculateErrors(final Double error) {
        int layers = network.size();

        network.get(layers - 1).getPerceptrons()
                .forEach(perceptron -> {
                    final Double delta = error * activationFunction.derivative(perceptron.getArguments());
                    perceptron.setInitialWeightOffset(delta * LEARNING_SPEED);
                    perceptron.setDelta(delta);

                    final List<Double> weightOffset = new ArrayList<>();
                    network.get(layers - 2).getPerceptrons().forEach(p -> weightOffset.add(delta * p.getOutputValue()));

                    perceptron.setWeightOffset(weightOffset);
                });

        for (int j = layers - 2; j > 0; j--) {
            for (int i = 0; i < network.get(j).getPerceptrons().size(); i++) {
                Perceptron perceptron = network.get(j).getPerceptrons().get(i);

                Double tempDelta = 0d;
                for (Perceptron p : network.get(j + 1).getPerceptrons()) {
                    tempDelta += p.getDelta() * p.getWeights().get(i);
                }
                final Double delta = tempDelta * activationFunction.derivative(perceptron.getArguments());

                perceptron.setInitialWeightOffset(delta * LEARNING_SPEED);
                perceptron.setDelta(delta);

                final List<Double> weightOffset = new ArrayList<>();
                network.get(j - 1).getPerceptrons().forEach(per -> weightOffset.add(LEARNING_SPEED * delta * per.getOutputValue()));

                perceptron.setWeightOffset(weightOffset);
            }
        }
    }

    private void updateWeights() {
        for (int j = 1; j < network.size() - 1; j++) {
            network.get(j).getPerceptrons().forEach(perceptron -> {
                List<Double> weights = new ArrayList<>();
                for (int i = 0; i < perceptron.getWeights().size(); i++) {
                    weights.add(perceptron.getWeights().get(i) + perceptron.getWeightOffset().get(i));
                }
                perceptron.setWeights(weights);
                perceptron.setInitialOffset((perceptron.getInitialOffset() + perceptron.getInitialWeightOffset()));
            });
        }
    }
}
