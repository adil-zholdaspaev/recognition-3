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

    private final Function activationFunction;
    private final Function function;
    private final List<Layer> network;

    public NeuronNetwork(Function activationFunction,
                         Function function) {

        this.activationFunction = activationFunction;
        this.function = function;

        Integer[] networkLayers = {1, 2, 1};

        this.network = initNetwork(networkLayers);
    }

    public void learn(final List<Pair<Double, Double>> trainingData) {

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

        trainingData.forEach(data -> {
            Double argument = data.getKey();
            Double actualResult = data.getValue();

            Double error = 0d;
            do {
                Double result = calculateFunction(argument);
                error = Math.abs(actualResult - result);
                if (error < EPSILON) {
                    break;
                }

                calculateDelta(error);
                calculateWeight(argument);
                if (actualResult - result > 0) {
                    argument = argument - EPSILON * function.derivative(argument);
                } else {
                    argument = argument + EPSILON * function.derivative(argument);
                }
                actualResult = function.calculate(argument);
            } while (error > EPSILON);
        });
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
        Perceptron perceptron2 = new Perceptron(1);
        perceptron2.setResult(argument);

        Layer tempLayer = new Layer(Collections.singletonList(perceptron2));
        final List<Layer> previousLayer = new ArrayList<>();
        previousLayer.add(tempLayer);

        network.forEach(layer -> {
            Layer prevLayer = previousLayer.get(0);
            List<Perceptron> perceptrons = prevLayer.getPerceptrons();

            layer.getPerceptrons().forEach(perceptron -> {

                int size = perceptron.getWeights().size();
                Double arg = 0d;
                for (int i = 0; i < size; i++) {
                    arg += perceptron.getWeights().get(i) * perceptrons.get(i).getResult();
                }

                perceptron.setResult(activationFunction.calculate(arg));

            });

            previousLayer.clear();
            previousLayer.add(layer);
        });

        return network.get(network.size() - 1).getPerceptrons().get(0).getResult();
    }

    private void calculateDelta(final Double error) {
        network.get(network.size() - 1).getPerceptrons().forEach(p -> p.setDelta(error));

        for (int i = network.size() - 2; i >= 0; i--) {

            List<Perceptron> perceptrons = network.get(i).getPerceptrons();
            int index = 0;
            for(Perceptron perceptron : perceptrons) {

                double delta = 0d;
                for (Perceptron neuron : network.get(i + 1).getPerceptrons()) {
                    delta += (neuron.getDelta() * neuron.getWeights().get(index));
                }

                perceptron.setDelta(delta);
                index++;
            }
        }
    }

    private void calculateWeight(final Double argument) {
        Perceptron perceptron2 = new Perceptron(1);
        perceptron2.setResult(argument);

        Layer tempLayer = new Layer(Collections.singletonList(perceptron2));
        final List<Layer> previousLayer = new ArrayList<>();
        previousLayer.add(tempLayer);

        network.forEach(layer -> {
            Layer prevLayer = previousLayer.get(0);
            List<Perceptron> perceptrons = prevLayer.getPerceptrons();


            for (Perceptron perceptron : layer.getPerceptrons()) {

                List<Double> newWeight = new ArrayList<>();
                int index = 0;
                for (Double w : perceptron.getWeights()) {

                    double weight = w + 0.1 * perceptrons.get(index).getDelta() * activationFunction.derivative(perceptrons.get(index).getResult());
                    index++;
                    newWeight.add(weight);
                }
                perceptron.setWeights(newWeight);
            }

            previousLayer.clear();
            previousLayer.add(layer);
        });
    }
}
